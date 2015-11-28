package com.shenbian.admin.controller.activities;

import org.aspectj.apache.bcel.generic.RET;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qomo on 15-10-19.
 */
@Component(value = "activityEndTimeConverter")
public class ActivityEndTimeConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return value;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String regex = "\"floorAmount\":(.*?),\"(.*?)\"";
        String regex1 = "\"end_date\":(.*?),\"(.*?)\"";
        Matcher matcher1 = Pattern.compile(regex1).matcher(value.toString());
        while (matcher1.find()) {
            String ret = matcher1.group(1);
            return ret.replace("\"", "").substring(0, 10);
        }
        return "-";
    }
}
