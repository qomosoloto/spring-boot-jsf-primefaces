package com.shenbian.admin.controller.merchandises;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by qomo on 15-10-18.
 */
@Setter
@Getter
public class MerchandisesSearchBean implements Serializable {


    private String merchandiseName;
    private String merchantName;
    private String productCodeSn;
    private boolean ifVoucher;

    private int category;
    private int status;
    private int productType;
    private LocalDateTime onlineTimeStart;
    private LocalDateTime onlineTimeEnd;

}
