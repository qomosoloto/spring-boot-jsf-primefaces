package com.shenbian.admin.service.globaldict;

import com.shenbian.admin.util.base.QueryResultType;

import java.util.Map;

/**
 * Created by qomo on 15-11-2.
 */
public interface GlobalDictServiceExt {
    /**
     * 查询广告和推广位
     *
     * @return
     */
    Object findAdsAndPromotions(Map<String, Object> params, QueryResultType type, Integer start, Integer pageSize);
}
