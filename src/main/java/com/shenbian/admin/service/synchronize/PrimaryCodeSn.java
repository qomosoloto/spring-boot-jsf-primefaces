package com.shenbian.admin.service.synchronize;

import com.shenbian.admin.service.SequenceService;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Random;

/**
 * Created by jason on 9/29/15.
 */

public enum PrimaryCodeSn {

    //活动商品
    PRODUCT_ACTIVITY(4020, "活动商品", true),
    SKU_ACTIVITY(4120, "活动商品SKU", true),

    //实物商品
    PRODUCT_MERCHANDISE(4011, "实物商品", true),
    SKU_MERCHANDISE(4111, "实物商品SKU", true),

    //券类商品
    PRODUCT_COUPON(4012, "券类商品", true),
    SKU_COUPON(4112, "券类商品SKU", true),

    ACCOUNT_USER(1115, "标准用户账户", true),
    ACCOUNT_VENDOR(1203, "标准商户账户", true),
    ACCOUNT_PREPAID_CARD(1301, "充值卡", true),

    USER_STD(3107, "标准用户", true),
    USER_VENDOR(3203, "商户", true),
    USER_PREREGISTER(3302, "预注册用户", false),

    ORDER_PREPAID_CARD_ACTIVE(2041, "预付费卡激活", false),
    ORDER_PREPAID_CARD_AWARD(2013, "商品订单流水号", false),
    ORDER_PREPAID_CARD_RECHARGE(4202, "预付费卡充值", false),

    ORDER_RECHARGE(4201, "第三方充值订单流水号", false),

    ORDER_CHECKIN_AWARD(4212, "活动订单流水号", false),
    ORDER_SIGNUP_AWARD(4213, "活动订单流水号", false),
    ORDER_SHARE_AWARD(4214, "活动订单流水号", false),

    ORDER_ACTIVITY(4211, "活动订单流水号", false),

    ORDER_TELECOM_FEE_SKU_CODE_SN(4231, "话费充值订单流水号", false), // ----
    ORDER_TELECOM_DATA_SKU_CODE_SN(4232, "流量充值订单流水号", false), // ----
    ORDER_MERCHANDISE(4233, "商品订单流水号", false), // ----
    ORDER_COUPON(4234, "商品订单流水号", false), // ----

    SKU_TELECOM_DATA(4241, "sku话费", false),
    SKU_TELECOM_FEE(4242, "sku流量", false),


    PRODUCT_SERVICE(4031, "服务商品", true),
    SKU_SERVICE(4131, "服务商品SKU", true),

    BALANCE_CASH_NORMAL(5001, "正向资金流水", false),
    BALANCE_CASH_INVERSE(5002, "反向资金流水", false),
    BALANCE_VOUCHER_NORMAL(5003, "正向红包流水", false),
    BALANCE_VOUCHER_INVERSE(5004, "反向红包流水", false),
    BALANCE_POINT_NORMAL(5005, "正向积分流水", false),
    BALANCE_POINT_INVERSE(5006, "反向积分流水", false),

    PCM_COUPON(6001, "券号", true),
    PCM_PREPAID_CARD(6002, "预付费卡号", false),
    PCM_NOTIFICATION(6003, "", false),


    GLOBAL(7000, "全局参数", true),
    RESIDENCE(8000, "小区", false),


    // 需要
    SKU_RECHARGE(4101, "第三方充值sku", true),
//    PRODUCT_RESIDENCE(1111, "第三方充值产品", true),
//    USER_RESIDENCE(1112, "", true),
//    ACCOUNT_RESIDENCE(1113, "", true),
    ;

    private static String SEQUENCE_NAME = "ng_sn_sequence";

    @Getter
    private Integer code;
    @Getter
    private String name;
    @Getter
    private Boolean isSequence;

    PrimaryCodeSn(Integer code, String name, Boolean isSequence) {
        this.code = code;
        this.name = name;
        this.isSequence = isSequence;
    }

    public String nextValue(SequenceService sequenceRepo) {

        LocalDateTime currentDateTime = LocalDateTime.now();

        // Short year 00 ~ 99, %02d
        int shortYear = currentDateTime.getYear() % 100;
        // Week of year 01 ~ 53, %02d
        int weekOfYear = currentDateTime.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
        // Day of year 1 - 7 %1d
        int dayOfWeek = currentDateTime.getDayOfWeek().getValue();
        // Second of day 00000 - 86400 %05d
        int secondOfDay = currentDateTime.toLocalTime().toSecondOfDay();

        return isSequence ? String.format("%04d%02d%02d%1d10%09d", code, shortYear, weekOfYear, dayOfWeek,
                sequenceRepo.nextValue(SEQUENCE_NAME)) :
                String.format("%04d%02d%02d%1d0%05d%05d", code, shortYear, weekOfYear, dayOfWeek,
                        secondOfDay, new Random().nextInt(100000));

    }


    public static PrimaryCodeSn valueOf(Integer v) {
        for (PrimaryCodeSn gen : PrimaryCodeSn.values()) {
            if (gen.getCode().equals(v)) {
                return gen;
            }
        }
        throw new RuntimeException("not find primaryCodeSn");
    }


}