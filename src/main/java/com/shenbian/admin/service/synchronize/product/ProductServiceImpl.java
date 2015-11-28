package com.shenbian.admin.service.synchronize.product;

import com.google.common.collect.Lists;
import com.shenbian.admin.service.synchronize.PrimaryCodeSn;
import com.shenbian.admin.service.synchronize.account.AccountService;
import com.shenbian.admin.service.user.UserService;
import com.shenbian.admin.util.base.QueryResultType;
import com.shenbian.ng.model.Product;
import com.shenbian.ng.model.Product_;
import com.shenbian.ng.model.enums.EntityStatus;
import com.shenbian.ng.model.enums.ProductType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

/**
 * Created by qomo on 15-10-8.
 */
public class ProductServiceImpl implements ProductServiceExt {

    public static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SkuService skuService;

    @Autowired
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    @Override
    public List<Product> findProducts() {
        Query nativeQuery = entityManager.createNativeQuery("select product.* from ng_product product where (product.code_sn like '401%' ) ", Product.class);
        List<Product> resultList = nativeQuery.getResultList();
        return resultList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> customSearch(Map<String, Object> map) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = criteriaBuilder.createQuery(Product.class);
        Root<Product> productRoot = query.from(Product.class);

        query.select(productRoot);

        List<Predicate> predicateList = Lists.newArrayList();

        if (map != null) {
            if (map.containsKey("productName")) {
                predicateList.add(criteriaBuilder.like(productRoot.get(Product_.name), "%" + map.get("productName") + "%"));
            }

            if (map.containsKey("productType")) {
                predicateList.add(criteriaBuilder.equal(productRoot.get(Product_.productType), ProductType.valueOf((String) map.get("productType"))));
            }

            if (map.containsKey("codeSn")) {
                predicateList.add(criteriaBuilder.like(productRoot.get(Product_.codeSn), "%" + map.get("codeSn") + "%"));
            } else {
                predicateList.add(criteriaBuilder.or(criteriaBuilder.like(productRoot.get(Product_.codeSn), PrimaryCodeSn.PRODUCT_MERCHANDISE.getCode().toString() + "%"), criteriaBuilder.like(productRoot.get(Product_.codeSn), PrimaryCodeSn.PRODUCT_COUPON.getCode().toString() + "%")));
            }

            // queryCategory or category ?
            if (map.containsKey("category")) {
                predicateList.add(criteriaBuilder.equal(productRoot.get(Product_.queryCategory), map.get("category")));
            }

        } else {
            predicateList.add(criteriaBuilder.or(criteriaBuilder.like(productRoot.get(Product_.codeSn), PrimaryCodeSn.PRODUCT_MERCHANDISE.getCode().toString() + "%"), criteriaBuilder.like(productRoot.get(Product_.codeSn), PrimaryCodeSn.PRODUCT_COUPON.getCode().toString() + "%")));
        }

        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        query.where(predicates);


        return entityManager.createQuery(query.orderBy(criteriaBuilder.desc(productRoot.get(Product_.lastModifiedTime)))).getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public Object paramsSearch(Map<String, Object> map, QueryResultType resultType, Integer start, Integer pageSize) {


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = criteriaBuilder.createQuery(Product.class);
        Root<Product> productRoot = query.from(Product.class);

        query.select(productRoot);

        List<Predicate> predicateList = Lists.newArrayList();

        predicateList.add(criteriaBuilder.equal(productRoot.get(Product_.entityStatus), EntityStatus.ACTIVE));

        if (map != null) {
            if (map.containsKey("productName")) {
                predicateList.add(criteriaBuilder.like(productRoot.get(Product_.name), "%" + map.get("productName") + "%"));
            }

            if (map.containsKey("productType")) {
                predicateList.add(criteriaBuilder.equal(productRoot.get(Product_.productType), ProductType.valueOf((String) map.get("productType"))));
            }

            if (map.containsKey("codeSn")) {
                predicateList.add(criteriaBuilder.like(productRoot.get(Product_.codeSn), "%" + map.get("codeSn") + "%"));
            } else {
                predicateList.add(criteriaBuilder.or(criteriaBuilder.like(productRoot.get(Product_.codeSn), PrimaryCodeSn.PRODUCT_MERCHANDISE.getCode().toString() + "%"), criteriaBuilder.like(productRoot.get(Product_.codeSn), PrimaryCodeSn.PRODUCT_COUPON.getCode().toString() + "%")));
            }

            // queryCategory or category ?
            if (map.containsKey("category")) {
                predicateList.add(criteriaBuilder.equal(productRoot.get(Product_.queryCategory), map.get("category")));
            }

        } else {
            predicateList.add(criteriaBuilder.or(criteriaBuilder.like(productRoot.get(Product_.codeSn), PrimaryCodeSn.PRODUCT_MERCHANDISE.getCode().toString() + "%"), criteriaBuilder.like(productRoot.get(Product_.codeSn), PrimaryCodeSn.PRODUCT_COUPON.getCode().toString() + "%")));
        }

        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        query.where(predicates);

        if (resultType.equals(QueryResultType.COLLECTION)) {
            return entityManager.createQuery(query.orderBy(criteriaBuilder.desc(productRoot.get(Product_.lastModifiedTime)))).setFirstResult(start).setMaxResults(pageSize).getResultList();
        } else if (resultType.equals(QueryResultType.TOTAL_ROWS)) {
            CriteriaQuery<Long> criteriaQueryTotal = criteriaBuilder.createQuery(Long.class);

            Root<Product> productCountRoot = criteriaQueryTotal.from(Product.class);
            criteriaQueryTotal.select(criteriaBuilder.count(productCountRoot));
            entityManager.createQuery(criteriaQueryTotal);
            criteriaQueryTotal.where(predicates);
            Long count = entityManager.createQuery(criteriaQueryTotal).getSingleResult();
            return count;
        }
        return null;
    }

    @Override
    public List<Object[]> productIdNameList() {


        return (List<Object[]>) entityManager.createNativeQuery("SELECT pro.id as id,pro.name as " +
                "name FROM ng_product pro WHERE pro.code_sn LIKE concat(?,'%') OR pro.code_sn LIKE concat(?,'%')")
                .setParameter(1, PrimaryCodeSn.PRODUCT_MERCHANDISE.getCode().toString()).setParameter(2, PrimaryCodeSn
                        .PRODUCT_COUPON.getCode().toString())
                .getResultList();
    }
}
