package com.shenbian.admin.service.user;


import com.shenbian.ng.model.*;

import java.util.List;
import java.util.Map;

/**
 * Created by qomo on 15-10-19.
 */
public interface UserServiceExt {
    List<User> customSearch(Map<String, Object> map);

    /**
     * 查询用户红包/现金/积分账户的流水balance信息
     */

    List<AbstractBalance> findBalances(Class clz, String userCodeSn);

    /**
     * 查询商户，商户保存在 merchant.json中
     */
    List<User> findMerchants() throws Exception;
}
