package com.shenbian.admin.controller.initializeData;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.shenbian.admin.service.user.UserService;
import com.shenbian.admin.util.base.Constant;
import com.shenbian.ng.model.User;
import com.shenbian.ng.model.UserAccountEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.*;
import java.util.Collection;
import java.util.List;

/**
 * Created by qomo on 15-10-28.
 */

@Component(value = "initializeBean")
@ManagedBean(name = "initializeBean")
@ViewScoped
public class InitializeData implements Serializable {

    @Autowired
    private UserService userService;


    public void init() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/views/init/prepaid.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化商户 json文件
     */
    public void initializeMerchantJsonFile() {
        try {
            File file = new File("merchants.json");

            List<User> all = Lists.newArrayList();
            all.add(userService.findByCodeSn(Constant.CORP_DEFAULT_USER_CODE_SN));

            if (all != null && all.size() > 0) {
                for (User user : all) {
                    //failed to lazily initialize a collection of role: com.shenbian.ng.model.User.userAccounts, could not initialize proxy - no Session
                    //Collection<UserAccountEntry> userAccounts = user.getUserAccounts();
                    user.setUserAccounts(null);
                }
                Gson gson = new Gson();
                String s = gson.toJson(all);

                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferWritter = new BufferedWriter(fileWriter);
                bufferWritter.write(s);
                bufferWritter.close();

            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("操作成功", "初始化商户json数据成功"));

        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("操作失败", "请稍后重试！"));
        }
    }
}
