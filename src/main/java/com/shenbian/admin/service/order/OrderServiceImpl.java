package com.shenbian.admin.service.order;

import com.google.common.collect.Lists;
import com.shenbian.admin.controller.order.OrderDetailVo;
import com.shenbian.admin.service.synchronize.PrimaryCodeSn;
import com.shenbian.admin.util.base.QueryResultType;
import com.shenbian.ng.model.*;
import com.shenbian.ng.model.Order;
import com.shenbian.ng.model.enums.ProductType;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * Created by qomo on 15-10-19.
 */
public class OrderServiceImpl implements OrderServiceExt {

    @Autowired
    EntityManager entityManager;

    /**
     * json_info 字段示例
     * {
     * "actualPay": "88.00",
     * "actualPrice": "88.00",
     * "amount": "1",
     * "createdTime": "",
     * "flowCheckWrapper": null,
     * "freight": "0",
     * "maxCash": "144.00",
     * "maxVoucher": "0",
     * "mobileDataConvert": null,
     * "name": "香杏酒",
     * "orderCode": "42331544103714308528",
     * "orderInfo": "{\"address\":\"上海市 虹口区 dd\",\"id\":\"\",\"phoneNumber\":\"12345678909\",\"province\":\"\",\"receiverName\":\"12345678909\",\"entityType\":0,\"entityId\":0,\"retcode\":0}",
     * "orderPic": "http://7xkmd0.com2.z0.glb.qiniucdn.com/26335672d0a24b3399f0fe91d9473b32",
     * "orderStatus": "CREATED",
     * "orderTitle": "香杏酒",
     * "paidTime": "",
     * "price": "88.00",
     * "productType": "",
     * "remainedStork": "9",
     * "ruleVoucher": "0",
     * "sentTime": "",
     * "skuType": "MERCHANDISE",
     * "title": "购物消费",
     * "unitPrice": "88.00"
     * }
     *
     * @param map
     * @param first
     * @param pageSize
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Object customSearch(Map<String, Object> map, QueryResultType resultType, Integer first, Integer pageSize) {

        String selectCollection = "select " +
                "nod.* " +
                "from ng_order nod  ";
        String selectTotalCount = "select count(nod.code_sn) from ng_order nod ";
        //  先查询 OrderSkuEntry 表
        StringBuffer sqlBuffer = new StringBuffer()
                .append(resultType == QueryResultType.COLLECTION ? selectCollection : selectTotalCount)
                .append("left join ng_order_order_sku noos on noos.order_code_sn=nod.code_sn ")
                .append("left join ng_user user on nod.buyer_code_sn=user.code_sn ")
                .append("left join ng_sku sku on noos.sku_code_sn=sku.code_sn ")
                .append("left join ng_product product on sku.product_code_sn=product.code_sn ")
                .append("where 1=1 ");


        if (map.containsKey("codeSn")) {
            sqlBuffer.append(" and noos.code_sn like '" + (String) map.get("codeSn") + "%'");
        } else {
            sqlBuffer.append(" and (noos.order_code_sn like '4233%' or noos.order_code_sn like '4234%')");
        }

        if (map.containsKey("buyerPhone")) {
            sqlBuffer.append(" and user.phoneNumber like '%" + map.get("buyerPhone") + "%'");
        }

        if (map.containsKey("orderType")) {
            sqlBuffer.append(" and product.product_type=" + Integer.valueOf((String) map.get("orderType")));
        }

        if (map.containsKey("merchandiseName")) {
            sqlBuffer.append(" and sku.name like '%" + map.get("merchandiseName") + "%'");
        }
        //  是否处理
        if (map.containsKey("ifHandled")) {
            sqlBuffer.append("");
        }
        //  物流信息是否填写
        if (map.containsKey("transportInfo")) {
            sqlBuffer.append("");
        }
        //  物流单号
        if (map.containsKey("transportNumber")) {
            sqlBuffer.append("");
        }

        if (map.containsKey("dateStart")) {
            sqlBuffer.append(" and DATE_FORMAT(nod.created_time,'yyyy-MM-dd')>=DATE_FORMAT(" + map.get("dateStart") + ",'yyyy-MM-dd')");
        }

        if (map.containsKey("dateEnd")) {
            sqlBuffer.append(" and DATE_FORMAT(nod.created_time,'yyyy-MM-dd')<=DATE_FORMAT(" + map.get("dateEnd") + ",'yyyy-MM-dd')");
        }

        if (map.containsKey("orderStatus") && null != map.get("orderStatus")) {
            sqlBuffer.append(" and nod.order_status=" + (Integer) map.get("orderStatus"));
        }

        sqlBuffer.append(" order by nod.code_sn asc ");
        Object object = resultType == QueryResultType.COLLECTION ? (List<Order>) entityManager.createNativeQuery(sqlBuffer.toString(), Order.class).setFirstResult(first).setMaxResults(pageSize).getResultList() : ((BigInteger) entityManager.createNativeQuery(sqlBuffer.toString()).getSingleResult()).intValue();

        return object;

    }

    @Transactional(readOnly = true)
    @Override
    public List<Order> findOrders() {

        //        PrimaryCodeSn.ORDER_MERCHANDISE 4233
        //        PrimaryCodeSn.ORDER_COUPON 4244

        return entityManager.createNativeQuery("SELECT ord.* FROM ng_order ord WHERE (ord.code_sn LIKE '4233%' OR ord.code_sn LIKE '4244%')AND ord.order_status IN (1,2,4)", Order.class).getResultList();
    }
}
