package com.shenbian.admin.controller.product;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by qomo on 15-10-23.
 * {"startDate":2015-08-22 13:30:00,"endDate":2015-09-30 18:00:00,"shareTitle":      ,"shareImageUrl":"","status":4,"type":1,"weight":0,"qrcodeUrl":"","rule_description":"母乳喂养，公益讲座！ "}
 */
@Getter
@Setter
public class JsonInfo implements Serializable {
    /**
     * beginTime:上架时间
     *
     * @since Ver 1.1
     */
    private String begin_time;

    /**
     * dailyLimit:每日限购
     *
     * @since Ver 1.1
     */
    private Integer daily_limit;

    /**
     * description:描述
     *
     * @since Ver 1.1
     */
    private String description;

    private String online_time;

    private String offline_time;


    /**
     * endTime:下架时间
     *
     * @since Ver 1.1
     */
    private String end_time;

    /**
     * maxDiscount:最大抵扣数
     *
     * @since Ver 1.1
     */
    private Double max_discount;

    /**
     * 是否可用紅包[新增!!!!!!!!!!!!!!!!!!]
     */
    private boolean ifVoucher;
    /**
     * merchandiseLable:商品标签
     *
     * @since Ver 1.1
     */
    private Integer merchandise_lable;

    /**
     * merchandiseStatus:商品状态
     * <p/>
     * enum ： SkuStatus
     * SCRATCH(0, "草稿"),
     * AUDITING(1, "审核中"),
     * AUDIT_PASSED(2, "审核通过"),
     * AUDIT_REJECTED(3, "审核未通过"),
     * ONLINE(4, "上架"),
     * OFFLINE(5, "下架"),
     * DELETED(6, "已删除");
     *
     * @since Ver 1.1
     */
    private Integer merchandise_status;

    /**
     * merchandiseType:商品类型
     *
     * @since Ver 1.1
     */
    private Integer merchandise_type;

    /**
     * name:商品名
     *
     * @since Ver 1.1
     */
    private String name;

    /**
     * notifyType:通知方法类型
     *
     * @since Ver 1.1
     */
    private Integer notify_type;

    /**
     * price:单价
     *
     * @since Ver 1.1
     */
    private Double price;

    /**
     * rule:规则
     *
     * @since Ver 1.1
     */
    private String rule;

    /**
     * showSupplier:是否显示供应商
     *
     * @since Ver 1.1
     */
    private Integer show_supplier;

    /**
     * stock:库存
     *
     * @since Ver 1.1
     */
    private Integer stock;

    /**
     * totalLimit:每人限购数
     *
     * @since Ver 1.1
     */
    private Integer total_limit;

    /**
     * transportCost:快递费
     *
     * @since Ver 1.1
     */
    private Double transport_cost;

    /**
     * usesncode:是否使用sn码
     *
     * @since Ver 1.1
     */
    private Integer usesncode;

    /**
     * weight:排序权重
     *
     * @since Ver 1.1
     */
    private Integer weight;

    /**
     * categoryId:商品分类
     *
     * @since Ver 1.1
     */
    private Long categoryId;

    /**
     * sellerId:关联上架id
     *
     * @since Ver 1.1
     */
    private Long seller_id;


    /**
     * SN码有效期
     */

    private String snCodeExpireDate;

    /**
     * @since Ver 5.0 新增字段
     * 产品成本价格
     */
    private BigDecimal productCost;


    /**
     * @since Ver 5.0 新增字段
     * 商品扩展属性
     * json字段
     * 基本形式 键为 属性名,值为 属性值
     * Gson转换后使用的是 List<Map<String,Object>
     */
    private String merchandiseExtProperties = "[]";


    /**********************************************************************************/
    /************************************活动××××××××××××××××××××××××××××××××××××××。**/
    /**
     * 标题
     */
    private String title;

    /**
     * 开始时间
     */
    private String startDate;

    /**
     * 结束时间
     */
    private String endDate;
    /**
     * 规则述
     */
    private String ruleDescription;

    /**
     * 活动状态，0：初始化，1：上线，2：下线，3：可参加，4：不可参加
     */
    private Integer status;

    /**
     * 分享标题
     */
    private String shareTitle;

    /**
     * 分享内容
     */
    private String shareContent;

    /**
     * 分享图片url
     */
    private String shareImageUrl;

    /**
     * 详情规则以json形式存储，活动添加时候出此表内字段外的其他字段都以json存储
     */
    private String contentJson;

    /**
     * 二维码Url地址
     */
    private String qrcodeUrl;


    /***********************************
     * 相片
     *************************************************/
    private String photos;
}
