package com.shenbian.admin.util.pf;

import org.primefaces.validate.ClientValidator;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.Date;
import java.util.Map;

/**
 * Created by qomo on 15-10-28.
 * 通用的日期范围校验器
 * <p:calendar id="dateStart" binding="#{dateStart}" showOn="button" pattern="yyyy-MM-dd"/>
 * 至
 * <p:calendar id="dateEnd" pattern="yyyy-MM-dd" validator="#{dateRangeValidator.validate}"
 * showOn="button">
 * <f:attribute name="dateStart" value="#{dateStart}"/>
 * </p:calendar>
 * <p>
 * 注意：两个日期的必须按照上面的格式来写，保持 起始时间的 binding="#{xxx}" 和结束时间中<f:attribute name="xxx" value="#{xxx}"一致
 * <p>
 * 同时，所在form的提交按钮中必须 设置 ajax="false",否则校验仅作用一次
 */
@Component(value = "dateRangeValidator")
@RequestScoped
@FacesValidator("dateRangeValidator")
public class DateRangeValidator implements Validator, ClientValidator {
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        UIInput startDateComponent = (UIInput) component.getAttributes().get("dateStart");

        Date startDate = new Date();
        if (null != startDateComponent && null != startDateComponent.getValue()) {
            startDate = (Date) startDateComponent.getValue();
        } else {
            return;
        }
        Date endDate = new Date();
        if (null != value) {
            endDate = (Date) value;
            if (startDate.after(endDate)) {
                startDateComponent.setValid(false);
                throw new ValidatorException(new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, "Validate Error", "起始日期必须小于结束日期！"));
            }
        } else {
            return;
        }


    }

    @Override
    public Map<String, Object> getMetadata() {
        return null;
    }

    @Override
    public String getValidatorId() {
        return "dateRangeValidator";
    }
}
