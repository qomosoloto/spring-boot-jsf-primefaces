package com.shenbian.admin.util.pf;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;

/**
 * 扩展，判断用户是否登录
 * Created by qomo on 15-9-27.
 * The phases are as follows, in the order of execution:
 * RESTORE_VIEW
 * APPLY_REQUEST_VALUES
 * PROCESS_VALIDATIONS
 * UPDATE_MODEL_VALUES
 * INVOKE_APPLICATION
 * RENDER_RESPONSE
 * - See more at: http://www.itcuties.com/j2ee/jsf-phaselistener/#sthash.XDBhyFlN.dpuf
 */
public class LogPhaseListener implements PhaseListener {

    public long startTime;
    public static final Logger log = LoggerFactory.getLogger(LogPhaseListener.class);

    @Override
    public void afterPhase(PhaseEvent event) {
        log.info("n++++++ AFTER PHASE " + event.getPhaseId() + " ++++++");

        if (event.getPhaseId() == PhaseId.RENDER_RESPONSE) {
            long endTime = System.nanoTime();
            long diffMs = (long) ((endTime - startTime) * 0.000001);
            log.info("render_response total time = " + diffMs + "ms");
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        HttpServletRequest request = (HttpServletRequest) event.getFacesContext().getExternalContext().getRequest();
        log.info("beforePhase <------ " + event.getPhaseId() + " <--request url------------->" + request.getRequestURL());
        if (event.getPhaseId() == PhaseId.RESTORE_VIEW) {
            startTime = System.nanoTime();
        }

    }


    @Override
    public PhaseId getPhaseId() {

        return PhaseId.ANY_PHASE;
    }

}