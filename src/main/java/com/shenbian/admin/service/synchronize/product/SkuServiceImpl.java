package com.shenbian.admin.service.synchronize.product;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.shenbian.admin.controller.activities.SkuVo;
import com.shenbian.admin.controller.product.JsonInfo;
import com.shenbian.admin.service.synchronize.PrimaryCodeSn;
import com.shenbian.admin.util.base.QueryResultType;
import com.shenbian.admin.util.qiniu.QRCodeService;
import com.shenbian.ng.model.*;
import com.shenbian.ng.model.enums.EntityStatus;
import com.shenbian.ng.model.enums.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by qomo on 15-10-14.
 */
public class SkuServiceImpl implements SkuServiceExt {
    @Autowired
    private SkuService skuService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private QRCodeService qrCodeService;

    @Transactional(readOnly = true)
    @Override
    public List<SkuVo> findSkuVoList(String codeSn) {

        List<Sku> skuList = skuService.findByCodeSnStartsWith(codeSn);

        List<SkuVo> skuVoList = Lists.newArrayList();

        for (Sku sku : skuList) {

        }
        return skuVoList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Sku> findByProductCodeSn(String codeSn) {
        //        entityManager.getCriteriaBuilder().
        Query nativeQuery = entityManager.createNativeQuery("SELECT sku.* FROM ng_sku sku  WHERE sku.product_code_sn='" + codeSn + "'", Sku.class);
        return nativeQuery.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Sku> customSearch(Map<String, Object> parmas) {

        //        StringBuffer sql = new StringBuffer("SELECT mer.* " +
        //                "FROM ng_sku mer " +
        //                "LEFT JOIN ng_product pro ON mer.product_code_sn=pro.code_sn " +
        //                "LEFT JOIN ng_user usr ON mer.owner_code_sn=usr.code_sn " +
        //                "WHERE " +
        //                "pro.name=? " +
        //                "AND pro.code_sn=? " +
        //                "AND usr.name=? " +
        //                "AND pro.query_category=? " +
        //                "AND mer.entity_status=? " +
        //                "AND pro.product_type=? " +
        //                "AND mer.online_time >= ? " +
        //                "AND mer.online_time<=? ");
        //
        //        if ((int) parmas.get("ifVoucher") > 0) {
        //            sql.append("AND mer.max_voucher>0");
        //        } else {
        //            sql.append("AND mer.max_voucher IS NULL OR mer.max_voucher <=0");
        //        }
        //
        //        Query nativeQuery = entityManager.createNativeQuery(sql.toString(), Sku.class);
        //        nativeQuery.setParameter(1, parmas.get("merchandiseName"));
        //        nativeQuery.setParameter(2, parmas.get("productCodeSn"));
        //        nativeQuery.setParameter(3, parmas.get("merchantName"));
        //        nativeQuery.setParameter(4, parmas.get("category"));
        //        nativeQuery.setParameter(5, parmas.get("status"));
        //        nativeQuery.setParameter(6, parmas.get("productType"));
        //        nativeQuery.setParameter(7, parmas.get("onlineTimeStart"));
        //        nativeQuery.setParameter(8, parmas.get("onlineTimeEnd"));


        //        CriteriaBuilder cb = em.getCriteriaBuilder();
        //
        //        CriteriaQuery<Country> q = cb.createQuery(Country.class);
        //        Root<Country> c = q.from(Country.class);
        //        q.select(c);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Sku> query = criteriaBuilder.createQuery(Sku.class);
        Root<Sku> skuRoot = query.from(Sku.class);

        Join<Sku, Product> joinProduct = skuRoot.join(Sku_.product, JoinType.LEFT);
        Join<Sku, User> joinUser = skuRoot.join(Sku_.owner, JoinType.LEFT);

        query.select(skuRoot);

        List<Predicate> predicateList = new ArrayList<Predicate>();


        if (parmas.containsKey("merchandiseName")) {
            predicateList.add(criteriaBuilder.like(joinProduct.get(Product_.name), "%" + (String) parmas.get("merchandiseName") + "%"));
        }

        if (parmas.containsKey("productCodeSn")) {
            predicateList.add(criteriaBuilder.like(joinProduct.get(Product_.codeSn), "%" + (String) parmas.get("productCodeSn") + "%"));
        }

        if (parmas.containsKey("merchantName")) {
            predicateList.add(criteriaBuilder.like(joinUser.get(User_.name), "%" + (String) parmas.get("merchantName") + "%"));
        }

        if (parmas.containsKey("status")) {
            predicateList.add(criteriaBuilder.equal(skuRoot.get(Sku_.entityStatus), (Integer) parmas.get("status")));
        }

        if (parmas.containsKey("category")) {
            predicateList.add(criteriaBuilder.equal(joinProduct.get(Product_.queryCategory), (Integer) parmas.get("category")));
        }

        if (parmas.containsKey("productType")) {
            predicateList.add(criteriaBuilder.equal(joinProduct.get(Product_.productType), parmas.get("productType")));
        }

        if (parmas.containsKey("onlineTimeStart")) {
            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(skuRoot.get(Sku_.onlineTime), (LocalDateTime) parmas.get("onlineTimeStart1")));
        }

        if (parmas.containsKey("onlineTimeEnd")) {
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(skuRoot.get(Sku_.onlineTime), (LocalDateTime) parmas.get("onlineTimeStart2")));
        }

        if (parmas.containsKey("ifVoucher")) {
            if (new BigDecimal((String) parmas.get("ifVoucher")).compareTo(BigDecimal.ZERO) == 1) {
                predicateList.add(criteriaBuilder.greaterThanOrEqualTo(skuRoot.get(Sku_.maxVoucher), BigDecimal.ZERO));
            } else {
                predicateList.add(criteriaBuilder.or(criteriaBuilder.equal(skuRoot.get(Sku_.maxVoucher), BigDecimal.ZERO), criteriaBuilder.isNull(skuRoot.get(Sku_.maxVoucher))));
            }
        }

        //  活动
        if (parmas.containsKey("id")) {
            predicateList.add(criteriaBuilder.like(skuRoot.get(Sku_.codeSn), "%" + parmas.get("id") + "%"));
        }

        if (parmas.containsKey("name")) {
            predicateList.add(criteriaBuilder.like(joinProduct.get(Product_.name), "%" + parmas.get("name") + "%"));
        }

        //  活动部分！！！
        //  todo jsonInfo or info？
        if (parmas.containsKey("actType")) {
            predicateList.add(criteriaBuilder.like(joinProduct.get(Product_.jsonInfo), parmas.get("actType") + "%"));
        } else {
            predicateList.add(criteriaBuilder.like(joinProduct.get(Product_.codeSn), PrimaryCodeSn.SKU_ACTIVITY.getCode().toString() + "%"));
        }

        if (parmas.containsKey("actStartTime2")) {
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(skuRoot.get(Sku_.onlineTime), (LocalDateTime) parmas.get("actStartTime2")));
        }

        if (parmas.containsKey("actStartTime1")) {
            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(skuRoot.get(Sku_.onlineTime), (LocalDateTime) parmas.get("actStartTime1")));
        }
        //通过Sku_元模型类info属性，称之为路径表达式。若info属性与24数字比较，编译器会抛出错误，这在JPQL中无法做到的
        //Predicate like = criteriaBuilder.like(skuRoot.get(Sku_.info), "24");
        //query.where(like);
        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);

        query.where(predicates);

        return entityManager.createQuery(query).getResultList();
    }


    @Transactional
    @Override
    public Object customSearch(Map<String, Object> parmas, Integer queryClass, QueryResultType resultType, Integer start, Integer pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Sku> query = criteriaBuilder.createQuery(Sku.class);
        Root<Sku> skuRoot = query.from(Sku.class);

        Join<Sku, Product> joinProduct = skuRoot.join(Sku_.product, JoinType.LEFT);
        Join<Sku, User> joinUser = skuRoot.join(Sku_.owner, JoinType.LEFT);

        query.select(skuRoot);

        List<Predicate> predicateList = new ArrayList<Predicate>();

        //  只查询 EntityStatus.ACTIVE。。。。。。。。EntityStatus.DEACTIVE，EntityStatus.DELETED不查询！
        predicateList.add(criteriaBuilder.equal(skuRoot.get(Sku_.entityStatus), EntityStatus.ACTIVE));
        if (0 == queryClass) {
            predicateList.add(criteriaBuilder.or(criteriaBuilder.like(skuRoot.get(Sku_.codeSn), PrimaryCodeSn.SKU_MERCHANDISE.getCode().toString() + "%"), criteriaBuilder.like(skuRoot.get(Sku_.codeSn), PrimaryCodeSn.SKU_COUPON.getCode().toString() + "%")));
        } else {
            predicateList.add(criteriaBuilder.like(skuRoot.get(Sku_.codeSn), PrimaryCodeSn.SKU_ACTIVITY.toString() + "%"));
        }

        if (parmas.containsKey("merchandiseName")) {
            predicateList.add(criteriaBuilder.like(joinProduct.get(Product_.name), "%" + (String) parmas.get("merchandiseName") + "%"));
        }

        if (parmas.containsKey("productCodeSn")) {
            predicateList.add(criteriaBuilder.like(joinProduct.get(Product_.codeSn), "%" + (String) parmas.get("productCodeSn") + "%"));
        }

        if (parmas.containsKey("merchantName")) {
            predicateList.add(criteriaBuilder.like(joinUser.get(User_.name), "%" + (String) parmas.get("merchantName") + "%"));
        }

        if (parmas.containsKey("status")) {
            predicateList.add(criteriaBuilder.equal(skuRoot.get(Sku_.entityStatus), (Integer) parmas.get("status")));
        }

        if (parmas.containsKey("category")) {
            predicateList.add(criteriaBuilder.equal(joinProduct.get(Product_.queryCategory), (Integer) parmas.get("category")));
        }

        if (parmas.containsKey("productType")) {
            predicateList.add(criteriaBuilder.equal(joinProduct.get(Product_.productType), parmas.get("productType")));
        }

        if (parmas.containsKey("onlineTimeStart")) {
            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(skuRoot.get(Sku_.onlineTime), (LocalDateTime) parmas.get("onlineTimeStart")));
        }

        if (parmas.containsKey("onlineTimeEnd")) {
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(skuRoot.get(Sku_.onlineTime), (LocalDateTime) parmas.get("onlineTimeEnd")));
        }

        if (parmas.containsKey("ifVoucher")) {
            if (new BigDecimal((String) parmas.get("ifVoucher")).compareTo(BigDecimal.ZERO) == 1) {
                predicateList.add(criteriaBuilder.greaterThan(skuRoot.get(Sku_.maxVoucher), BigDecimal.ZERO));
            } else {
                predicateList.add(criteriaBuilder.or(criteriaBuilder.equal(skuRoot.get(Sku_.maxVoucher), BigDecimal.ZERO), criteriaBuilder.isNull(skuRoot.get(Sku_.maxVoucher))));
            }
        }

        //  活动
        if (parmas.containsKey("id")) {
            predicateList.add(criteriaBuilder.like(skuRoot.get(Sku_.codeSn), "%" + parmas.get("id") + "%"));
        }

        if (parmas.containsKey("name")) {
            predicateList.add(criteriaBuilder.like(joinProduct.get(Product_.name), "%" + parmas.get("name") + "%"));
        }

        //  活动部分！！！
        //  todo jsonInfo or info？
        if (parmas.containsKey("actType")) {
            predicateList.add(criteriaBuilder.like(joinProduct.get(Product_.jsonInfo), parmas.get("actType") + "%"));
        }

        if (parmas.containsKey("actStartTime2")) {
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(skuRoot.get(Sku_.onlineTime), (LocalDateTime) parmas.get("actStartTime2")));
        }

        if (parmas.containsKey("actStartTime1")) {
            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(skuRoot.get(Sku_.onlineTime), (LocalDateTime) parmas.get("actStartTime1")));
        }


        /**
         * SCRATCH(Integer.valueOf(0), "草稿"),
         AUDITING(Integer.valueOf(1), "审核中"),  ------ 商品保存后就处于 审核中状态 ？ 可直接上架？
         AUDIT_PASSED(Integer.valueOf(2), "审核通过"), -----  直接上架
         AUDIT_REJECTED(Integer.valueOf(3), "审核未通过"), --------- 可再审核？
         ONLINE(Integer.valueOf(4), "上架"),
         OFFLINE(Integer.valueOf(5), "下架"),
         DELETED(Integer.valueOf(6), "已删除");
         */

        //通过Sku_元模型类info属性，称之为路径表达式。若info属性与24数字比较，编译器会抛出错误，这在JPQL中无法做到的
        //Predicate like = criteriaBuilder.like(skuRoot.get(Sku_.info), "24");
        //query.where(like);
        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        query.where(predicates);


        if (0 == resultType.ordinal()) {
            List<Sku> resultList = entityManager.createQuery(query.orderBy(criteriaBuilder.desc(skuRoot.get(Sku_.lastModifiedTime)))).setFirstResult(start).setMaxResults(pageSize).getResultList();
            List<Sku> finalList = Lists.newArrayList();
            // 老数据商品没有二维码，查询后如果没有则生成,这个时候，transactional（readOnly=true)需要改掉
            //  这部分也可以单独拿出来做
            if (resultList != null && resultList.size() > 0) {
                for (Sku sku : resultList) {
                    String jsonInfo = sku.getJsonInfo();
                    JsonReader reader = null;
                    if (!Strings.isNullOrEmpty(jsonInfo)) {
                        reader = new JsonReader(new StringReader(jsonInfo));
                        reader.setLenient(true);
                        JsonInfo fromJson = new Gson().fromJson(reader, JsonInfo.class);
                        if (Strings.isNullOrEmpty(fromJson.getQrcodeUrl())) {
                            qrCodeService.createTwoDimensionCode(sku.getCodeSn());
                            finalList.add(skuService.findByCodeSn(sku.getCodeSn()));
                        } else {
                            finalList.add(sku);
                        }
                    } else {
                        qrCodeService.createTwoDimensionCode(sku.getCodeSn());
                        finalList.add(skuService.findByCodeSn(sku.getCodeSn()));
                    }
                }
            }
            return finalList;
        } else if (1 == resultType.ordinal()) {
            CriteriaQuery<Long> criteriaQueryTotal = criteriaBuilder.createQuery(Long.class);

            Root<Sku> skuCountRoot = criteriaQueryTotal.from(Sku.class);
            Join<Sku, Product> skuProductJoin = skuCountRoot.join(Sku_.product, JoinType.LEFT);
            Join<Sku, User> skuUserJoin = skuCountRoot.join(Sku_.owner, JoinType.LEFT);
            criteriaQueryTotal.select(criteriaBuilder.count(skuCountRoot));
            entityManager.createQuery(criteriaQueryTotal);
            criteriaQueryTotal.where(predicates);
            Long count = entityManager.createQuery(criteriaQueryTotal).getSingleResult();
            return count;
        }

        return null;
    }

    @Override
    public Long customSearchResultRowsNumber(Map<String, Object> parmas, Integer queryClass, Integer start, Integer pageSize) {
        return null;
    }

    @Override
    public Sku findByMerchandisesId(Long mechId) {
        return (Sku) entityManager.createNativeQuery("SELECT * FROM ng_sku WHERE merchandises_id=?", Sku.class).setParameter(1, mechId).getSingleResult();
    }
}
