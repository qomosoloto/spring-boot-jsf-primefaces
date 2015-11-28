package com.shenbian.admin.controller.globalconfiguration;

import com.google.common.collect.Maps;
import com.shenbian.admin.controller.BaseBackBean;
import com.shenbian.admin.service.globaldict.GlobalDictService;
import com.shenbian.ng.model.GlobalDict;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;

/**
 * Created by qomo on 15-11-2.
 * 首页配置，广告/推广
 */
@Component(value = "globalDictBean")
@ManagedBean(name = "globalDictBean")
@ViewScoped
public class GlobalConfigurationBean implements BaseBackBean {
    @Autowired
    private GlobalDictService globalDictService;


    @Getter
    @Setter
    private GlobalDict selectedGlobalDict;

    @Getter
    @Setter
    private GlobalDictLazyDataModel lazyDataModel;

    @Override
    public void init() {
        this.lazyDataModel = new GlobalDictLazyDataModel(globalDictService, Maps.newHashMap());

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/views/globaldict/list.xhtml");
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
