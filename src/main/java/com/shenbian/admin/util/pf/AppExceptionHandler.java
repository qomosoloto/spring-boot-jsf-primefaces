package com.shenbian.admin.util.pf;

/**
 * 异常处理
 */

import org.apache.log4j.spi.LoggerFactory;
import org.slf4j.Logger;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import java.util.Iterator;

public class AppExceptionHandler extends ExceptionHandlerWrapper {

    private javax.faces.context.ExceptionHandler wrapped;

    public AppExceptionHandler(javax.faces.context.ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AppExceptionHandler.class);

    @Override
    public javax.faces.context.ExceptionHandler getWrapped() {
        return this.wrapped;
    }

    @Override
    public void handle() throws FacesException {
        Iterable<ExceptionQueuedEvent> events = this.wrapped.getUnhandledExceptionQueuedEvents();
        for (Iterator<ExceptionQueuedEvent> it = events.iterator(); it.hasNext(); ) {

            ExceptionQueuedEvent event = it.next();
            LOGGER.info("exceptionQueuedEvent phaseId ---------- > < -----------  " + event.getContext().getPhaseId());
            ExceptionQueuedEventContext exceptionQueuedEventContext = event.getContext();

            exceptionQueuedEventContext.getException().printStackTrace();

            //  视图未存储/视图过期异常
            if (exceptionQueuedEventContext.getException() instanceof ViewExpiredException) {
                FacesContext context = exceptionQueuedEventContext.getContext();
                if (!context.isReleased()) {
                    NavigationHandler navHandler = context.getApplication().getNavigationHandler();
                    navHandler.handleNavigation(context, null, "loginPage");
                }

            }
        }

        this.wrapped.handle();
    }

    private void setMessage(String head, String summary) {
        FacesMessage msg = new FacesMessage(head, summary);
        msg.setSeverity(FacesMessage.SEVERITY_FATAL);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}

