package com.shenbian.admin.util.pf;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

/**
 * Created by qomo on 15-9-28.
 */
public class LoginPhaseListener implements PhaseListener {
    public static final Logger log = LoggerFactory.getLogger(LogPhaseListener.class);

    @Override
    public void afterPhase(PhaseEvent phaseEvent) {
        FacesContext facesContext = phaseEvent.getFacesContext();
        String crtPage = facesContext.getViewRoot().getViewId();

        boolean isLoginPage = crtPage.lastIndexOf("index.xhtml") >= 0;
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        String crtUser = (String) session.getAttribute("username");
        if (!isLoginPage && (Strings.isNullOrEmpty(crtUser))) {
            NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
            //  FacesContext , fromAction[跳转的触发,页面/控件等] , outcome
            navigationHandler.handleNavigation(facesContext, null, "loginPage");
            log.info("user not login , redirect to login page ! ----------> <---------- " + crtPage);
        } else {

        }
    }

    @Override
    public void beforePhase(PhaseEvent phaseEvent) {

    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}
