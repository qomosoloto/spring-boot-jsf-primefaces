package com.shenbian.admin.service.synchronize.product;

import com.shenbian.admin.controller.activities.SkuVo;
import com.shenbian.admin.util.base.QueryResultType;
import com.shenbian.ng.model.Sku;

import java.util.List;
import java.util.Map;

/**
 * Created by qomo on 15-10-14.
 */
public interface SkuServiceExt {
    List<SkuVo> findSkuVoList(String codeSn);

    List<Sku> findByProductCodeSn(String codeSn);

    /**
     * KU_ACTIVITY(4121, "活动商品SKU", true),
     * SKU_CHECKINAWARD(4122, "签到sku", true),
     * SKU_SIGNUPAWARD(4123, "注册sku", true),
     * SKU_SHAREAWARD(4124, "分享sku", true),
     *
     * @param parmas
     * @return
     */
    List<Sku> customSearch(Map<String, Object> parmas);

    /**
     * 分页查询
     *
     * @param parmas
     * @param queryClass 0,查询商品包括劵类，1，活动
     * @param resultType 0,查询列表，1查询记录总行数
     * @param start
     * @param pageSize
     * @return
     */
    Object customSearch(Map<String, Object> parmas, Integer queryClass, QueryResultType resultType, Integer start, Integer pageSize);


    /**
     * 返回查询的总结果数
     *
     * @param parmas
     * @param queryClass 0,查询商品包括劵类，1，活动
     * @param start
     * @param pageSize
     * @return
     */
    Long customSearchResultRowsNumber(Map<String, Object> parmas, Integer queryClass, Integer start, Integer pageSize);

    /**
     * 根据merchandise id查找 sku。。。。老数据
     *
     * @param mechId
     */
    Sku findByMerchandisesId(Long mechId);
}
