package com.shenbian.admin.controller.activities;

import com.shenbian.admin.service.synchronize.PrimaryCodeSn;
import com.shenbian.ng.model.Sku;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Created by qomo on 15-10-19.
 */
@Component(value = "activityTypeConverter")
public class ActivityTypeConverter implements Converter {
    /**
     * http请求中传来的字符串转换
     *
     * @param context
     * @param component
     * @param value
     * @return
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return value;
    }

    /**
     * 后台某个bean转换
     *
     * @param context
     * @param component
     * @param value
     * @return
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        System.out.println("codeSn........................................" + value.toString());
        System.out.println("||||||||||||||||||||||||||||" + value.toString().substring(0, 4).startsWith(PrimaryCodeSn.SKU_ACTIVITY.getCode().toString()));
        return value.toString().substring(0, 4).startsWith(PrimaryCodeSn.SKU_ACTIVITY.getCode().toString()) ? "线下" : "";
    }
}
