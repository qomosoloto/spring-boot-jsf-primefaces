package com.shenbian.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;

/**
 * Created by qomo on 15-9-25.
 */
@SpringBootApplication
@Configuration
//@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.shenbian"})
@EnableJpaRepositories(basePackages = {"com.shenbian"})
@EntityScan(basePackages = {"com.shenbian"})//实体扫描,todo,单独配置在dataSource
//@PropertySource(value = "classpath:application.properties")
public class AdmApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdmApplication.class, args);
    }

//    @Bean
//    public EmbeddedServletContainerCustomizer containerCustomizer() {
//
//        return (container -> {
//            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.xhtml");
//            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.xhtml");
//            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.xhtml");
//
//            container.addErrorPages(error401Page, error404Page, error500Page);
//        });
//    }

        //        return new EmbeddedServletContainerCustomizer() {
        //            @Override
        //            public void customize(ConfigurableEmbeddedServletContainer container) {
        //
        //                ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
        //                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
        //                ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
        //
        //                container.addErrorPages(error401Page, error404Page, error500Page);
        //            }
        //        };

}
