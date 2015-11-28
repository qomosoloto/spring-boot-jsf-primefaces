package com.shenbian.admin.controller.activities;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.shenbian.admin.controller.BaseBackBean;
import com.shenbian.admin.service.synchronize.PrimaryCodeSn;
import com.shenbian.admin.service.synchronize.product.SkuService;
import com.shenbian.ng.model.Sku;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qomo on 15-10-14.
 * 2015.10.29 活动产品重做，暂不做
 */
@Component(value = "activityBean")
@ManagedBean(name = "activityBean")
@ViewScoped
public class SkuActivities implements BaseBackBean {

    @Autowired
    private SkuService skuService;
    //    @Autowired
    //    private ActivityTypeConverter activityTypeConverter;

    @Setter
    @Getter
    private List<Sku> skuList;

    @Getter
    @Setter
    private List<SkuVo> list;

    @Setter
    @Getter
    private Sku selectedSku = new Sku();

    @Setter
    @Getter
    private SkuVo selectedSkuVo = new SkuVo(this.selectedSku);


    @Setter
    @Getter
    private Map<String, Object> selectedSkuJsonMap = Maps.newHashMap();

    private Gson gson = new Gson();

    @Override
    public void init() {
        this.skuList = skuService.findByCodeSnStartsWith(PrimaryCodeSn.SKU_ACTIVITY.getCode().toString());
        this.list = Lists.newArrayList();
        for (Sku sku : skuList) {

        }
        this.selectedSku = new Sku();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/views/activity/list.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewEntity(Integer type) {

        this.selectedSku = new Sku();
    }


    @Override
    public void selectEntity(Object o) {
        this.selectedSku = (Sku) o;
    }

    @Override
    public void deleteEntity() {
        skuService.delete(this.selectedSku);
        this.init();
    }

    public void paramSearch() {

        Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        HashMap<String, Object> map = Maps.newHashMap();
        if (!Strings.isNullOrEmpty(requestParameterMap.get("search_form:id"))) {
            map.put("id", requestParameterMap.get("search_form:id"));
        }

        if (!Strings.isNullOrEmpty(requestParameterMap.get("search_form:name"))) {
            map.put("name", requestParameterMap.get("search_form:name"));
        }

        if (!Strings.isNullOrEmpty(requestParameterMap.get("search_form:actType_input"))) {
            map.put("actType", requestParameterMap.get("search_form:actType_input"));
        }


        if (!Strings.isNullOrEmpty(requestParameterMap.get("search_form:actStartTime1_input"))) {
            map.put("actStartTime1", requestParameterMap.get("search_form:actStartTime1_input"));
        }


        if (!Strings.isNullOrEmpty(requestParameterMap.get("search_form:actStartTime2_input"))) {
            map.put("actStartTime2", requestParameterMap.get("search_form:actStartTime2_input"));
        }

        this.skuList = skuService.customSearch(map);
    }

    public void paramReset() {

    }
}
