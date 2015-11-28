package com.shenbian.admin.controller.merchant;

import com.google.common.collect.Lists;
import com.shenbian.admin.service.synchronize.PrimaryCodeSn;
import com.shenbian.admin.service.synchronize.account.AccountService;
import com.shenbian.admin.util.base.Constant;
import com.shenbian.ng.model.User;
import com.shenbian.ng.model.enums.GenderType;
import com.shenbian.ng.model.enums.UserStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qomo on 15-10-28.
 */
@Getter
@Setter
public class MerchantList implements Serializable {

    @Autowired
    private AccountService accountService;

    private List<User> merchants;

    public MerchantList() {
        this.merchants = Lists.newArrayList();
        //  公司商户
        User igyUser = new User();

        igyUser.setCodeSn(Constant.CORP_DEFAULT_USER_CODE_SN);
        igyUser.setCodeSn(Constant.CORP_DEFAULT_USER_CODE_SN);
        igyUser.setUserStatus(UserStatus.ACTIVE);
        igyUser.setName("igy");
        igyUser.setPassword("admin");
        igyUser.setPasswordSalt("admin");
        igyUser.setRole(0);
        igyUser.setGender(GenderType.UNKNOW);
        igyUser.setPhoneNumber("4001699518");
        igyUser.setPrimaryAccount(accountService.findByCodeSn(Constant.CORP_DEFAULT_USER_CODE_SN));
        this.merchants.add(igyUser);


        //............
    }
}
