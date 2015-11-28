package com.shenbian.admin.controller.activities;

import lombok.val;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qomo on 15-10-19.
 */
@Component(value = "activityStartTimeConverter")
public class ActivityStartTimeConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return value;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String regex = "\"start_date\":(.*?),\"end_date\"";
        String regex1 = "\"end_date\":(.*?),\"(.*?)\"";
        Matcher matcher = Pattern.compile(regex).matcher(value.toString());
        while (matcher.find()) {
            String ret = matcher.group(1);
            return ret.replace("\"", "").substring(0, 10);
        }
        return "-";
    }
}
