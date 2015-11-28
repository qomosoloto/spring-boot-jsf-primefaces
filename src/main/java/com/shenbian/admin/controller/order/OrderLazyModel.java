package com.shenbian.admin.controller.order;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.shenbian.admin.service.order.OrderService;
import com.shenbian.admin.service.synchronize.PrimaryCodeSn;
import com.shenbian.admin.util.base.QueryResultType;
import com.shenbian.ng.model.Order;
import com.shenbian.ng.model.OrderSkuEntry;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by qomo on 15-11-23.
 */
public class OrderLazyModel extends LazyDataModel<OrderDetailVo> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderLazyModel.class);
    @Getter
    @Setter
    private OrderService orderService;

    @Getter
    @Setter
    private List<OrderDetailVo> orderList;

    @Setter
    @Getter
    private Map<String, Object> paramMap;

    public OrderLazyModel(OrderService orderService, Map<String, Object> paramMap) {
        this.orderService = orderService;
        this.paramMap = paramMap;
        LOGGER.info("OrderLazyModel initialize.........");
    }

    @Override
    public List<OrderDetailVo> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        LOGGER.info("[[[[[[[[[[first]]]]]]]]]..." + first);
        LOGGER.info("[[[[[[[[[[pageSize]]]]]]]]]..." + pageSize);
        List<Order> orders = (List<Order>) orderService.customSearch(paramMap, QueryResultType.COLLECTION, first, pageSize);
        this.orderList = Lists.newArrayList();
        if (orders != null && orders.size() > 0) {
            Gson gson = new Gson();
            for (Order order : orders) {
                OrderDetailVo orderDetailVo = new OrderDetailVo();
                orderDetailVo.setOrder(order);
                orderDetailVo.setOrderSkuEntry((OrderSkuEntry) order.getSkuEntries().toArray()[0]);
                String jsonInfo = order.getJsonInfo();
                if (!StringUtils.isEmpty(jsonInfo) && order.getCodeSn().startsWith(PrimaryCodeSn.ORDER_MERCHANDISE.getCode().toString())) {
                    OrderJsonInfo orderJsonInfo = gson.fromJson(jsonInfo, OrderJsonInfo.class);
                    orderDetailVo.setOrderJsonInfo(orderJsonInfo);
                    if (!StringUtils.isEmpty(orderJsonInfo.getOrderInfo())) {
                        OrderInfo orderInfo = gson.fromJson(orderJsonInfo.getOrderInfo(), OrderInfo.class);
                        orderDetailVo.setOrderInfo(orderInfo);
                    } else {
                        orderDetailVo.setOrderInfo(new OrderInfo());
                    }
                } else {
                    orderDetailVo.setOrderJsonInfo(new OrderJsonInfo());
                }
                orderList.add(orderDetailVo);
            }
        }

        if (super.getRowCount() <= 0) {
            this.setRowCount((Integer) orderService.customSearch(paramMap, QueryResultType.TOTAL_ROWS, first, pageSize));
        }

        // set the page size
        this.setPageSize(pageSize);
        return orderList;
    }
}
