package com.shenbian.admin.controller.order;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by qomo on 15-11-24.
 */
@Getter
@Setter
public class OrderInfo {
    private String address;

    private String addressStatus;

    private String id;

    private String phoneNumber;

    private String province;

    private String receiverName;

    private String zipCode;

    private Integer entityType;

    private Integer entityId;

    private Integer retcode;
}
