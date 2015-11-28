package com.shenbian.admin.service.globaldict;

import com.google.common.collect.Lists;
import com.shenbian.admin.util.base.QueryResultType;
import com.shenbian.ng.model.GlobalDict;
import com.shenbian.ng.model.GlobalDict_;
import com.shenbian.ng.model.enums.GlobalDictKey;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

/**
 * Created by qomo on 15-11-2.
 */
public class GlobalDictServiceImpl implements GlobalDictServiceExt {

    @Autowired
    private GlobalDictService globalDictService;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Object findAdsAndPromotions(Map<String, Object> map, QueryResultType type, Integer start, Integer pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GlobalDict> query = criteriaBuilder.createQuery(GlobalDict.class);
        Root<GlobalDict> root = query.from(GlobalDict.class);

        query.select(root);

        List<Predicate> predicateList = Lists.newArrayList();

        predicateList.add(criteriaBuilder.or(criteriaBuilder.equal(root.get(GlobalDict_.dictName), GlobalDictKey.ADS_PAGEHOME), criteriaBuilder.equal(root.get(GlobalDict_.dictName), GlobalDictKey.PROMOTION_PAGEHOME)));

        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);

        query.where(predicates);

        if (QueryResultType.COLLECTION.equals(type)) {
            return entityManager.createQuery(query).setFirstResult(start).setMaxResults(pageSize).getResultList();
        } else {
            CriteriaQuery<Long> longCriteriaQuery = criteriaBuilder.createQuery(Long.class);

            longCriteriaQuery.select(criteriaBuilder.count(longCriteriaQuery.from(GlobalDict.class)));
            entityManager.createQuery(longCriteriaQuery);
            longCriteriaQuery.where(predicates);
            return entityManager.createQuery(longCriteriaQuery).getSingleResult();
        }
    }
}
