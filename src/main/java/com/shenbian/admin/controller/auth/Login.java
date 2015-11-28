package com.shenbian.admin.controller.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;

/**
 * 用户登陆
 * Created by qomo on 15-9-25.
 */
@Controller(value = "login")
@SessionScoped
public class Login implements Serializable {

    public static final Logger LOGGER = LoggerFactory.getLogger(Login.class);
    private String pwd;
    private String msg;
    private String user;


    @PostConstruct
    public void init() {
        this.pwd = "";
        this.user = "";
    }

    public void validateUsernamePassword() {
        try {
            if ("admin".equals(user.trim()) && "admin".equals(pwd.trim())) {
                LOGGER.info("login admin successfully ........ ");
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", user);
                FacesContext.getCurrentInstance().getExternalContext().redirect("/views/user/list.xhtml");
            } else {
                LOGGER.info("login admin failed ........ ");
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", "");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "用户名或者密码不正确",
                        "请输入正确的用户名和密码"));
                init();
            }
        } catch (IOException e) {

        }

        //        boolean valid = LoginDAO.validate(user, pwd);
        //        if (valid) {
        //            HttpSession session = SessionBean.getSession();
        //            session.setAttribute("username", user);
        //            return "admin";
        //        } else {
        //            FacesContext.getCurrentInstance().addMessage(
        //                    null,
        //                    new FacesMessage(FacesMessage.SEVERITY_WARN,
        //                            "Incorrect Username and Passowrd",
        //                            "Please enter correct username and Password"));
        //            return "login";
        //        }
    }

    public void logout() {

        try {
            init();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", "");
            FacesContext.getCurrentInstance().getExternalContext().redirect("/index.xhtml");

        } catch (IOException e) {
            e.printStackTrace();
        }

        LOGGER.info("exit admin success ........ ");
    }


    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
