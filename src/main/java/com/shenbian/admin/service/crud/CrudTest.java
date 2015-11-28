package com.shenbian.admin.service.crud;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by qomo on 15-9-26.
 */
public class CrudTest {
    @Autowired
    private CrudService crudService;

    public void test() {
        crudService.paramSearch("", 1);
    }
}
