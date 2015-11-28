package com.shenbian.admin.controller.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 对应Order中json_info字段
 * Created by qomo on 15-11-24.
 */
@Getter
@Setter
public class OrderJsonInfo {
    private BigDecimal actualPay;

    private BigDecimal actualPrice;

    private Integer amount;

    private String createdTime;

    private Object flowCheckWrapper = null;

    private Integer freight;

    private BigDecimal maxCash;

    private Integer maxVoucher;

    private Object mobileDataConvert = null;

    private String name;

    private String orderCode;

    private String orderInfo;

    private String orderPic;

    private String orderStatus;

    private String orderTitle;

    private String paidTime;

    private BigDecimal price;

    private String productType;

    private Integer remainedStork;

    private Integer ruleVoucher;

    private String sentTime;

    private String skuType;

    private String title;

    private BigDecimal unitPrice;

}
