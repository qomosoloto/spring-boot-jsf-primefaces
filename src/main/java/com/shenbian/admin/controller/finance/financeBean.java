package com.shenbian.admin.controller.finance;

import com.shenbian.admin.controller.BaseBackBean;
import com.shenbian.admin.service.synchronize.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;

/**
 * Created by qomo on 15-10-19.
 */
@Component(value = "financeBean")
@ViewScoped
public class financeBean implements BaseBackBean {

    @Autowired
    private AccountService accountService;

    @Override
    public void init() {

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/views/finance/list.xhtml");
        } catch (IOException e) {
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
