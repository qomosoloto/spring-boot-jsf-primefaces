package com.shenbian.admin.config;

import com.shenbian.admin.service.synchronize.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 *
 */
@Component
public class ApplicationInitializerListener implements ApplicationListener<ContextRefreshedEvent> {


    @Autowired
    private ProductService productService;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("................" + productService.getClass().getSimpleName());

    }
}
