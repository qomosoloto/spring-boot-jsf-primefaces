package com.shenbian.admin.controller.user;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.shenbian.admin.controller.BaseBackBean;
import com.shenbian.admin.service.synchronize.account.AccountService;
import com.shenbian.admin.service.user.UserService;
import com.shenbian.ng.model.*;
import com.shenbian.ng.model.enums.EntityStatus;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.TabChangeEvent;
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
@Component(value = "userBean")
@ManagedBean(name = "userBean")
@ViewScoped
public class UserBean implements BaseBackBean {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Getter
    @Setter
    private List<User> userList;

    @Getter
    @Setter
    private User selectedUser;

    @Getter
    @Setter
    private int tabIndex = 0;


    /**
     * 0，拉灰，拉黑，恢复
     */
    @Getter
    @Setter
    private int op;

    @Getter
    @Setter
    private List<AbstractBalance> balances;

    @Override
    public void init() {

        this.userList = userService.findAll();
        this.selectedUser = new User();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/views/user/list.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void initGreyList() {
        this.tabIndex = 1;
        this.userList = userService.findAll();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/views/user/grey.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initBlackList() {
        this.tabIndex = 2;
        this.userList = userService.findAll();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/views/user/black.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewEntity(Integer type) {

    }

    @Override
    public void selectEntity(Object object) {
        this.selectedUser = (User) object;
    }

    /**
     * @param o
     * @param op 0,恢复，1，拉灰，2，拉黑
     */
    public void changeBlackGrey(Object o, Integer op) {
        this.selectedUser = (User) o;
        this.op = op;

    }

    /**
     * 拉黑，拉灰，恢复
     */
    @Override
    public void deleteEntity() {
        //        this.selectedUser.setEntityStatus(EntityStatus.DELETED);
        //        //  该用户所有的订单，活动状态也改变？
        //        userService.save(this.selectedUser);
        this.init();
    }

    public void paramSearch() {
        Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Map<String, Object> map = Maps.newHashMap();
        if (!Strings.isNullOrEmpty(requestParameterMap.get("form_search:phoneNumber"))) {
            map.put("phoneNumber", requestParameterMap.get("form_search:phoneNumber"));
        }
        if (!Strings.isNullOrEmpty(requestParameterMap.get("form_search:createTime1_input"))) {
            map.put("createTime1", requestParameterMap.get("form_search:createTime1_input"));
        }
        if (!Strings.isNullOrEmpty(requestParameterMap.get("form_search:createTime2_input"))) {
            map.put("createTime2", requestParameterMap.get("form_search:createTime2_input"));
        }
        if (!Strings.isNullOrEmpty(requestParameterMap.get("form_search:lastLoginTime1_input"))) {
            map.put("lastLoginTime1", requestParameterMap.get("form_search:lastLoginTime1_input"));
        }
        if (!Strings.isNullOrEmpty(requestParameterMap.get("form_search:lastLoginTime2_input"))) {
            map.put("lastLoginTime2", requestParameterMap.get("form_search:lastLoginTime2_input"));
        }

        this.userList = userService.customSearch(map);
    }

    public void paramReset() {

    }

    /**
     * tab title 我的详情，红包账户，现金账户，积分账户
     */
    public void onTabChange(TabChangeEvent event) {
        FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab: " + event.getTab().getTitle());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        if ("红包账户".equals(event.getTab().getTitle())) {
            this.balances = userService.findBalances(VoucherBalance.class, this.selectedUser.getCodeSn());
            System.out.println("红包账户..............................................................." + this.balances.size());
        } else if ("现金账户".equals(event.getTab().getTitle())) {
            this.balances = userService.findBalances(CashBalance.class, this.selectedUser.getCodeSn());
            System.out.println("现金账户..............................................................." + this.balances.size());
        } else if ("积分账户".equals(event.getTab().getTitle())) {
            this.balances = userService.findBalances(PointBalance.class, this.selectedUser.getCodeSn());
            System.out.println("积分账户..............................................................." + this.balances.size());
        }
    }
}
