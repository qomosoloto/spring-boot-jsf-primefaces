package com.shenbian.admin.config;

import org.primefaces.webapp.filter.FileUploadFilter;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.UrlBasedViewResolverRegistration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Created by qomo on 15-9-25.
 */
@Configuration
public class JsfConfiguration implements ServletContextAware, ServletContextInitializer {

    /**
     * FaceServlet
     *
     * @return
     */
    @Bean
    public FacesServlet facesServlet() {
        return new FacesServlet();
    }

    /**
     * 注册 FaceServlet
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean facesServletRegistration() {

        ServletRegistrationBean registration = new ServletRegistrationBean(facesServlet(), "*.xhtml", "*.jsf");
        registration.setName("FacesServlet");
        registration.setLoadOnStartup(1);
        return registration;
    }

    @Bean
    public FileUploadFilter fileUploadFilter() {
        return new FileUploadFilter();
    }

    @Bean
    public FilterRegistrationBean uplodaFilterRegistration() {

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(fileUploadFilter());
        filterRegistrationBean.addServletNames("FacesServlet");
        System.out.println("register file upload filter .......................................");
        return filterRegistrationBean;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
    }

    /**
     * 设置模板文件
     *
     * @return
     */
//    @Bean
//    public ViewResolver getJsfViewResolver() {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        //Files in /WEB-INF folder are indeed not publicly accessible by enduser
//        resolver.setPrefix("/views/");
//        //resolver.setSuffix(".jsf");
//        resolver.setSuffix(".xhtml");
//
//
//        return resolver;
//    }

//    @Bean
//    public UrlBasedViewResolver urlBasedViewResolver() {
//        UrlBasedViewResolver urlBasedViewResolver = new UrlBasedViewResolver();
//        urlBasedViewResolver.setViewClass(FaceletViewResolver.class);
//        urlBasedViewResolver.setPrefix("/views/");
//        urlBasedViewResolver.setSuffix(".xhtml");
//        //  urlBasedViewResolver.setSuffix(".jsf");
//        return urlBasedViewResolver;
//    }
//
//    @Bean
//    public UrlBasedViewResolverRegistration urlBasedViewResolverRegistration() {
//        UrlBasedViewResolverRegistration urlBasedViewResolverRegistration = new UrlBasedViewResolverRegistration
//                (urlBasedViewResolver());
//
//        return urlBasedViewResolverRegistration;
//    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        //  设置主题
//        servletContext.setInitParameter("primefaces.THEME", "bootstrap");
        servletContext.setInitParameter("primefaces.THEME", "#{userSettings.currentTheme.name}");
        //  客户端校验
        servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", "true");
        //  空字符串
        servletContext.setInitParameter("javax.faces.INTERPRET_EMPTY_STRING_SUBMITTED_VALUES_AS_NULL", Boolean.TRUE.toString());
        //  是否在应用范围内启用或禁用增量视图状态，默认为true
        servletContext.setInitParameter("javax.faces.PARTIAL_STATE_SAVING", Boolean.FALSE.toString());
        //  servletContext.setInitParameter("com.sun.faces.expressionFactory", "com.sun.el.ExpressionFactoryImpl");
        //  Development
        servletContext.setInitParameter("javax.faces.PROJECT_STAGE", "Production");
        servletContext.setInitParameter("primefaces.UPLOADER", "commons");
        //  使用 psring security 标签库
        //  servletContext.setInitParameter("javax.faces.FACELETS_LIBRARIES", "/WEB-INF/springsecurity.taglib.xml");
        //	servletContext.setInitParameter("primefaces.PUBLIC_CAPTCHA_KEY", "6Lcq0goTAAAAAMMGB4sfVn157Acy9bUA5FmrPECL");
        //	servletContext.setInitParameter("primefaces.PRIVATE_CAPTCHA_KEY", "6Lcq0goTAAAAAKOxVyBOdRfZRcUSM23aFJtP13MX");

    }
}
