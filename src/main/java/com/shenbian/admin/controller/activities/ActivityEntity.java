package com.shenbian.admin.controller.activities;

import com.shenbian.ng.model.Sku;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by qomo on 15-10-23.
 */
@Getter
@Setter
public class ActivityEntity extends Sku {

    public ActivityEntity() {
        super();

    }

    /**
     * 类型，0：钢镚宝，1：线下活动，2：周周疯，3：抽奖
     */
    private Integer type;

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
     * 权重排序
     */
    private Integer weight;

    /**
     * 二维码Url地址
     */
    private String qrcodeUrl;
}
