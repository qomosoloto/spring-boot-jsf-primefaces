package com.shenbian.admin.controller.crud;

import org.springframework.stereotype.Controller;

import javax.faces.bean.ViewScoped;

/**
 * Created by qomo on 15-9-25.
 */
@Controller(value = "crudController")
@ViewScoped
public class CrudController {

    private String value;

    public void test() {

        value = "test";
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
