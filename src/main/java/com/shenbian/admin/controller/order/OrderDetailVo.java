package com.shenbian.admin.controller.order;

import com.shenbian.ng.model.Order;
import com.shenbian.ng.model.OrderSkuEntry;
import lombok.Getter;
import lombok.Setter;


/**
 * Created by qomo on 15-11-23.
 */
@Getter
@Setter
public class OrderDetailVo {

    private Order order;

    private OrderSkuEntry orderSkuEntry;

    /**
     * 来自于 json info的转换
     */
    private OrderJsonInfo orderJsonInfo;

    /**
     * 来自于 json info中的 orderInfo
     */
    private OrderInfo orderInfo;
}
