package com.shenbian.admin.util.pf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
public class ExceptionHandlerFactory extends javax.faces.context.ExceptionHandlerFactory {
    private javax.faces.context.ExceptionHandlerFactory base;

    private AppExceptionHandler cached;

    public ExceptionHandlerFactory(javax.faces.context.ExceptionHandlerFactory base) {
        this.base = base;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerFactory.class);

    @Override
    public javax.faces.context.ExceptionHandler getExceptionHandler() {
        if (cached == null) {
            cached = new AppExceptionHandler(base.getExceptionHandler());
        }

        return cached;
    }


}
