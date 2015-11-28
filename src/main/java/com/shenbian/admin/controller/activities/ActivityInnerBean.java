package com.shenbian.admin.controller.activities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by qomo on 15-10-23.
 */
@Getter
@Setter
public class ActivityInnerBean implements Serializable {

    //主办方
    private String sponor;
    //地址
    private String address;
    //
}
