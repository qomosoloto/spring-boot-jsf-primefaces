package com.shenbian.admin.service.user;


import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shenbian.ng.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Created by qomo on 15-10-19.
 */
public class UserServiceImpl implements UserServiceExt {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserService userService;

    @Override
    public List<User> customSearch(Map<String, Object> map) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = query.from(User.class);

        CollectionJoin<User, UserAccountEntry> accountEntryCollectionJoin = userRoot.join(User_.userAccounts,
                JoinType.INNER);

        query.select(userRoot);

        List<Predicate> predicateList = Lists.newArrayList();

        if (map.containsKey("phoneNumber")) {
            predicateList.add(criteriaBuilder.like(userRoot.get(User_.phoneNumber), "%" + map.get("phoneNumber") + "%"));
        }

        if (map.containsKey("createTime1")) {
            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(userRoot.get(User_.createdTime), (LocalDateTime) map.get("createTime1")));
        }

        if (map.containsKey("createTime2")) {
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(userRoot.get(User_.createdTime), (LocalDateTime) map.get("createTime2")));
        }

        if (map.containsKey("lastLoginTime1")) {
            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(userRoot.get(User_.lastLoginTime), (LocalDateTime) map.get("lastLoginTime1")));
        }

        if (map.containsKey("lastLoginTime2")) {
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(userRoot.get(User_.lastLoginTime), (LocalDateTime) map.get("lastLoginTime2")));
        }


        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        query.where(predicates);

        return entityManager.createQuery(query).getResultList();
    }


    @Transactional(readOnly = true)
    @Override
    public List<AbstractBalance> findBalances(Class clz, String userCodeSn) {
        User byCodeSn = userService.findByCodeSn(userCodeSn);
        if (byCodeSn != null) {
            StringBuffer sqlbBuffer = new StringBuffer("SELECT nb.* FROM ");
            StringBuffer stringBuffer = clz.getSimpleName().equals(VoucherBalance.class.getSimpleName()) ? sqlbBuffer.append("ng_voucher_balance nb" +
                    " ") : (clz
                    .getSimpleName().equals(CashBalance.class.getSimpleName()) ? sqlbBuffer.append("ng_cash_balance nb")
                    : sqlbBuffer.append("ng_point_balance nb"));
            stringBuffer.append(" WHERE nb.from_user=?");
            String sql = stringBuffer.toString();
            //  from_user 查询流水
            Query nativeQuery = entityManager.createNativeQuery(sql, VoucherBalance.class);
            nativeQuery.setParameter(1, byCodeSn.getCodeSn());
            return nativeQuery.getResultList();

        }
        return null;
    }

    @Override
    public List<User> findMerchants() throws Exception {

        File file = new File(System.getProperty("user.dir") + "/merchants.json");
        Gson gson = new Gson();
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String msg = null;
        StringBuffer stringBuffer = new StringBuffer();
        while ((msg = bufferedReader.readLine()) != null) {
            System.out.println(msg);
            stringBuffer.append(msg);
        }
        bufferedReader.close();

        return gson.fromJson(stringBuffer.toString(), new TypeToken<List<User>>() {
        }.getType()) == null ? Lists.newArrayList() : gson.fromJson(stringBuffer.toString(), new TypeToken<List<User>>() {
        }.getType());
    }
}
