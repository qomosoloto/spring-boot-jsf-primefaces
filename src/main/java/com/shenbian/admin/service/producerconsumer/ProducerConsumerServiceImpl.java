package com.shenbian.admin.service.producerconsumer;

import com.google.gson.Gson;
import com.shenbian.admin.service.SequenceService;
import com.shenbian.admin.service.synchronize.PrimaryCodeSn;
import com.shenbian.admin.service.synchronize.account.AccountService;
import com.shenbian.admin.service.synchronize.product.ProductService;
import com.shenbian.admin.service.synchronize.product.SkuService;
import com.shenbian.admin.service.user.UserService;
import com.shenbian.admin.util.base.Constant;
import com.shenbian.admin.util.base.QueryResultType;
import com.shenbian.ng.model.*;
import com.shenbian.ng.model.enums.*;
import org.jumpmind.symmetric.csv.CsvReader;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.sql.Types;
import java.util.List;
import java.util.Map;

/**
 * Created by qomo on 15-10-12.
 */
public class ProducerConsumerServiceImpl implements ProducerConsumerServiceExt {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerConsumerServiceImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ProducerConsumerService producerService;

    @Autowired
    private SkuService skuService;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SequenceService sequenceService;
    private static final String QUEYR_MERCHANDISE_BY_ID = "SELECT\n" +
            "  ifnull(mechd.content, ' ')                            AS info,\n" +
            "  ifnull(mechd.description, ' ')                        AS description,\n" +
            "  ifnull(mechd.name, ' ')                               AS longName,\n" +
            "  ifnull(mechd.name, ' ')                               AS NAME,\n" +
            "  mechd.seller_id                                       AS createrCodeSn,\n" +
            "  date_format(mechd.modified_time, '%Y-%m-%dT%H:%i:%s') AS modifiedTime,\n" +
            "  mechd.stock                                           AS inventory,\n" +
            "  mechd.stock                                           AS remainedInventory,\n" +
            "  mechd.price                                           AS unitPrice,\n" +
            "  date_format(mechd.begin_time, '%Y-%m-%dT%H:%i:%s')    AS beginTime,\n" +
            "  date_format(mechd.end_time, '%Y-%m-%dT%H:%i:%s')      AS endTime,\n" +
            "  mechd.id                                              AS merchandisesId,\n" +
            "  mechd.category_id                                     AS queryCategory,\n" +
            "  mechd.merchandise_label                               AS queryDiscount,\n" +
            "  mechd.merchandise_type                                AS merchandiseType,\n" +
            "  mechd.merchandise_status                              AS merchandiseStatus,\n" +
            "  mechdImg.url                                          AS picture,\n" +
            "  GROUP_CONCAT(\n" +
            "      CONCAT('{', '\\\"max_discount\\\":', '\\\"', ifnull(mechd.max_discount, 'null'), '\\\",', '\\\"merchandise_label\\\":', '\\\"',\n" +
            "             ifnull(mechd.merchandise_label, 'null'), '\\\",', '\\\"merchandise_status\\\":', '\\\"',\n" +
            "             ifnull(mechd.merchandise_status, 'null'), '\\\",', '\\\"merchandise_type\\\":', '\\\"',\n" +
            "             ifnull(mechd.merchandise_type, 'null'), '\\\",', '\\\"show_supplier\\\":', '\\\"',\n" +
            "             ifnull(bin(mechd.show_supplier), '0'), '\\\",', '\\\"notify_type\\\":', '\\\"', ifnull(mechd.notify_type, 'null'),\n" +
            "             '\\\",', '\\\"total_limit\\\":', '\\\"', ifnull(mechd.total_limit, 'null'), '\\\",', '\\\"transport_cost\\\":', '\\\"',\n" +
            "             ifnull(mechd.transport_cost, 'null'), '\\\",', '\\\"weight\\\":', '\\\"', ifnull(mechd.weight, 'null'), '\\\",',\n" +
            "             '\\\"category_id\\\":', '\\\"', ifnull(mechd.category_id, 'null'), '\\\",''\\\"seller_id\\\":', '\\\"',\n" +
            "             ifnull(mechd.seller_id, 'null'), '\\\",', '\\\"daily_limit\\\":', '\\\"', ifnull(mechd.daily_limit, 'null'),\n" +
            "             '\\\",'))                                    AS jsonInfo\n" +
            "FROM merchandises mechd LEFT JOIN merchandise_images mechdImg ON mechd.id = mechdImg.merchandise_id\n" +
            "WHERE mechd.id = ? AND mechdImg.image_type = 0;";


    @Transactional
    @Override
    public void synchronizeProducerConsumer() {
        Long now = System.currentTimeMillis();
        LOGGER.info("enter method synchronizeProducerConsumer ----------------------------- ");

        //  merchandise_sn_codes表
        synchronizeMerchandiseCodeSn();

        LOGGER.info("enter exit synchronizeProducerConsumer, cost time ----------------------------- " + (System.currentTimeMillis() - now));
    }

    @Transactional
    @Override
    public String importCouponsFile(UploadedFile file) throws Exception {
        //  默认活动、商品、券product.creater, sku.owner
        User creater = userService.findByCodeSn(Constant.CORP_DEFAULT_USER_CODE_SN);
        //  creater的默认主账号
        Account account = accountService.findByCodeSn(Constant.CORP_DEFAULT_USER_CODE_SN);
        //  默认活动Sku.chargeAccount 资产类
        Account assetAccount = accountService.findByCodeSn(Constant.ACTIVITY_AWARD_ACCOUNT_CODE_SN);
        //  默认商品/券Sku.chargeAccount 负债类
        Account liabilityAccount = accountService.findByCodeSn(Constant.VENDOR_CORP_ACCOUNT_CODE_SN);

        InputStream input = file.getInputstream();
        int i = 0;
        CsvReader csvReader = new CsvReader(input, ',', Charset.forName("UTF-8"));

        Sku sku1 = null;

        while (csvReader.readRecord()) {
            String[] values = csvReader.getValues();
            System.out.println("values....." + values[0]);
            if (i == 0) {
                String value = values[0];
                sku1 = skuService.findByCodeSn(value);
                if (sku1 == null) {
                    return "商品库中并不存在编号为" + value + "的商品，首先确认您的CSV文件第一行是否为券类商品编号【开头四位为4112】，若不是，那则是数据有问题;如果是，请到商品中搜索该商品；如果搜寻不到请到数据导入中导入老表中的数据后再上传相关优惠券";
                } else {
                    //  如果数据库中存在该券商品sku，结束本次循环
                    continue;
                }
            }

            ProducerConsumer producerConsumer = new ProducerConsumer();

            producerConsumer.setTinyValue(values[0]);

            producerConsumer.setSku(sku1);

            producerConsumer.setFlag(ProducerConsumerType.PRODUCE);
            producerConsumer.setOrder(null);
            producerConsumer.setCodeSn(PrimaryCodeSn.PCM_COUPON.nextValue(sequenceService));
            producerConsumer.setSizeType(SizeType.TINY);
            producerConsumer.setHashCode(producerConsumer.hashCode());

            producerService.save(producerConsumer);
            i++;

        }

        return "导入成功，一共导入" + (i - 1) + "条数据";
    }

    @Override
    public Object pageQuery(QueryResultType queryResultType, Integer first, Integer pageSize) {
        if (queryResultType == QueryResultType.COLLECTION) {

            return entityManager.createNativeQuery("SELECT pc.* FROM ng_producer_consumer pc WHERE pc.code_sn LIKE '" + PrimaryCodeSn.PCM_COUPON.getCode().toString() + "%'", ProducerConsumer.class).setFirstResult(first).setMaxResults(pageSize).getResultList();
        } else {
            return ((BigInteger) entityManager.createNativeQuery("SELECT COUNT(pc.code_sn)FROM ng_producer_consumer pc  WHERE pc.code_sn LIKE '" + PrimaryCodeSn.PCM_COUPON.getCode().toString() + "%'").getSingleResult()).intValue();
        }
    }


    private void synchronizeMerchandiseCodeSn() {
        //  merchandise_sn_codes 列表
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList("SELECT msc.*  FROM merchandise_sn_codes msc LEFT JOIN merchandises mechd ON msc.merchandise_id=mechd.id WHERE msc.sn_code_status=0 AND mechd.merchandise_status=4 AND msc.id>?", new Object[]{0}, new int[]{Types.BIGINT});

        if (mapList != null && mapList.size() > 0) {
            for (Map<String, Object> merchandiseSnCodeMap : mapList) {
                ProducerConsumer producerConsumer = new ProducerConsumer();

                producerConsumer.setTinyValue((String) merchandiseSnCodeMap.get("code"));

                Sku sku = skuService.findByMerchandisesId((Long) merchandiseSnCodeMap.get("merchandise_id"));

                producerConsumer.setSku(sku);

                producerConsumer.setFlag(ProducerConsumerType.PRODUCE);
                producerConsumer.setOrder(null);
                producerConsumer.setCodeSn(PrimaryCodeSn.PCM_COUPON.nextValue(sequenceService));
                producerConsumer.setSizeType(SizeType.TINY);
                producerConsumer.setHashCode(producerConsumer.hashCode());

                producerService.save(producerConsumer);
            }
        }


        List<Map<String, Object>> merchandiseInvetory = jdbcTemplate.queryForList("SELECT DISTINCT msc.merchandise_id as merchandiseId,count(msc.id) as inventory FROM merchandise_sn_codes msc LEFT JOIN merchandises mechd ON msc.merchandise_id=mechd.id  WHERE msc.sn_code_status = 0 AND mechd.merchandise_status=4 GROUP BY msc.merchandise_id;");

        if (merchandiseInvetory != null && merchandiseInvetory.size() > 0) {
            for (Map<String, Object> map : merchandiseInvetory) {
                Sku sku = skuService.findByMerchandisesId((Long) map.get("merchandiseId"));
                sku.setInventory(BigDecimal.valueOf((Long) map.get("inventory")));
                sku.setRemainedInventory(BigDecimal.valueOf((Long) map.get("inventory")));
                skuService.save(sku);

            }
        }
    }


}
