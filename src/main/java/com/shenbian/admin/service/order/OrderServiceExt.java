package com.shenbian.admin.service.order;

import com.shenbian.admin.util.base.QueryResultType;
import com.shenbian.ng.model.Order;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * Created by qomo on 15-10-19.
 */
public interface OrderServiceExt {
    /**
     * 多参数组合查询
     *
     * @param map
     * @param first
     * @param pageSize
     * @return
     */
    Object customSearch(Map<String, Object> map, QueryResultType resultType, Integer first, Integer pageSize);

    /**
     * 查询 parent_order_code_sn不为空的订单
     */
    List<Order> findOrders();


}
