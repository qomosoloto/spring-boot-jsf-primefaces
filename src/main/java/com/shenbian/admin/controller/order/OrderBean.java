package com.shenbian.admin.controller.order;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.shenbian.admin.controller.BaseBackBean;
import com.shenbian.admin.service.order.OrderService;
import com.shenbian.admin.util.base.QueryResultType;
import com.shenbian.ng.model.Order;
import com.shenbian.ng.model.enums.OrderStatus;
import com.shenbian.ng.model.enums.ProductType;
import lombok.Getter;
import lombok.Setter;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by qomo on 15-10-19.
 */
@Component(value = "orderBean")
@ManagedBean(name = "orderBean")
@ViewScoped
public class OrderBean extends LazyDataModel<Order> implements BaseBackBean {
    @Autowired
    private OrderService orderService;


    @Getter
    @Setter
    private List<Order> orderList;

    @Getter
    @Setter
    private List<Order> paidOrderList = Lists.newArrayList();

    @Getter
    @Setter
    private List<Order> notPaidOrderList = Lists.newArrayList();

    @Getter
    @Setter
    private Order selectedOrder;

    @Getter
    @Setter
    private OrderLazyModel lazyModel;

    @Getter
    @Setter
    private Integer orderStatus = 0;

    //  查询参数
    Map<String, Object> queryParamsMap = Maps.newHashMap();

    @Override
    public void init() {
        this.queryParamsMap.put("orderStatus", this.orderStatus == 0 ? null : this.orderStatus);
        this.lazyModel = new OrderLazyModel(this.orderService, queryParamsMap);
        RequestContext.getCurrentInstance().update(":form1:infoTable");
        this.selectedOrder = new Order();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/views/order/list.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查看 全部0/已支付1/未支付2 订单
     *
     * @param type
     */
    public void changeOrders(Integer type) {

        this.orderStatus = type;
        switch (type) {
            case 0:
                this.queryParamsMap.put("orderStatus", null);
                break;
            case 1:
                this.queryParamsMap.put("orderStatus", OrderStatus.SUCCESS.ordinal());
                break;
            case 2:
                this.queryParamsMap.put("orderStatus", OrderStatus.PAYING.ordinal());
                break;
            default:
                this.queryParamsMap.put("orderStatus", null);
                break;
        }

        this.lazyModel = new OrderLazyModel(this.orderService, this.queryParamsMap);
        RequestContext.getCurrentInstance().update(":fom1:infoTable");
    }

    @Override
    public void addNewEntity(Integer type) {

    }

    @Override
    public void selectEntity(Object object) {
        this.selectedOrder = (Order) object;
    }

    @Override
    public void deleteEntity() {

    }

    /**
     * @see {PrimaryCodeSn}
     */
    public void paramSearch() {
        Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        //  订单编号，订单编号规则参考
        if (!Strings.isNullOrEmpty(requestParameterMap.get("form_search:codeSn"))) {
            queryParamsMap.put("codeSn", requestParameterMap.get("form_search:codeSn"));
        }

        if (!Strings.isNullOrEmpty(requestParameterMap.get("form_search:buyerPhone"))) {
            queryParamsMap.put("buyerPhone", requestParameterMap.get("form_search:buyerPhone"));
        }

        if (!Strings.isNullOrEmpty(requestParameterMap.get("form_search:orderType_input")) && !"-1".equals(requestParameterMap.get("form_search:orderType_input"))) {
            queryParamsMap.put("orderType", requestParameterMap.get("form_search:orderType_input"));
        }

        if (!Strings.isNullOrEmpty(requestParameterMap.get("form_search:merchandiseName"))) {
            queryParamsMap.put("merchandiseName", requestParameterMap.get("form_search:merchandiseName"));
        }

        if (!Strings.isNullOrEmpty(requestParameterMap.get("form_search:ifHandled_input"))) {
            queryParamsMap.put("ifHandled", requestParameterMap.get("form_search:ifHandled_input"));
        }

        if (!Strings.isNullOrEmpty(requestParameterMap.get("form_search:transportInfo_input")) && !"-1".equals(requestParameterMap.get("form_search:transportInfo_input"))) {
            queryParamsMap.put("transportInfo", requestParameterMap.get("form_search:transportInfo_input"));
        }

        if (!Strings.isNullOrEmpty(requestParameterMap.get("form_search:transportNumber"))) {
            queryParamsMap.put("transportNumber", requestParameterMap.get("form_search:transportNumber"));
        }

        if (!Strings.isNullOrEmpty(requestParameterMap.get("form_search:merchantName_input"))) {
            queryParamsMap.put("merchantName", requestParameterMap.get("form_search:merchantName_input"));
        }

        if (!Strings.isNullOrEmpty(requestParameterMap.get("form_search:dateStart"))) {
            queryParamsMap.put("dateStart", requestParameterMap.get("form_search:dateStart"));
        }

        if (!Strings.isNullOrEmpty(requestParameterMap.get("form_search:dateEnd"))) {
            queryParamsMap.put("dateEnd", requestParameterMap.get("form_search:dateEnd"));
        }
        queryParamsMap.put("orderStatus", this.orderStatus == 0 ? null : this.orderStatus);
        this.lazyModel = new OrderLazyModel(orderService, queryParamsMap);
        RequestContext.getCurrentInstance().update(":form1:infoTable");
    }

    public void paramReset() {
        this.orderStatus = 0;
    }

}
