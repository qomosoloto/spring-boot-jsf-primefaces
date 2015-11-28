package com.shenbian.admin.controller.community;

import com.shenbian.admin.controller.BaseBackBean;
import com.shenbian.admin.service.community.ResidenceService;
import com.shenbian.ng.model.Residence;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.List;

/**
 * Created by qomo on 15-10-19.
 */
@Component(value = "residenceBean")
@ViewScoped
public class ResidenceBean implements BaseBackBean {

    @Autowired
    private ResidenceService residenceService;

    @Getter
    @Setter
    private List<Residence> residenceList;

    @Getter
    @Setter
    private Residence selecteResidence;


    @Override
    public void init() {
        this.residenceList = residenceService.findAll();
        this.selecteResidence = new Residence();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/views/community/list.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void addNewEntity(Integer type) {

    }

    @Override
    public void selectEntity(Object object) {
        this.selecteResidence = (Residence) object;
    }

    @Override
    public void deleteEntity() {

    }
}
