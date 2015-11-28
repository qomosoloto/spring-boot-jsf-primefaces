package com.shenbian.admin.controller.merchant;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shenbian.admin.controller.BaseBackBean;
import com.shenbian.admin.service.synchronize.account.AccountService;
import com.shenbian.admin.service.user.UserService;
import com.shenbian.ng.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.*;
import java.util.List;

/**
 * Created by qomo on 15-10-19.
 */
@Component(value = "merchantBean")
@ManagedBean(name = "merchantBean")
@ViewScoped
public class MerchantBean implements BaseBackBean {
    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Getter
    @Setter
    private List<User> userList;


    @Override
    public void init() {


        try {
            this.userList = userService.findMerchants();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/views/merchant/list.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewEntity(Integer type) {

    }

    @Override
    public void selectEntity(Object object) {

    }

    @Override
    public void deleteEntity() {

    }
}
