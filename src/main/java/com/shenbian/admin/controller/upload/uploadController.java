package com.shenbian.admin.controller.upload;

import org.springframework.stereotype.Controller;

import javax.faces.bean.SessionScoped;

/**
 * Created by qomo on 15-9-29.
 */
@Controller(value = "upload")
@SessionScoped
public class uploadController {

    public void clickUploadBtn() {
        System.out.println("弹出选择图片弹窗dialog");
    }
}
