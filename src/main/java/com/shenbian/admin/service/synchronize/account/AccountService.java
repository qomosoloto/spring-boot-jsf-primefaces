package com.shenbian.admin.service.synchronize.account;

import com.shenbian.ng.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by qomo on 15-10-9.
 */
public interface AccountService extends JpaRepository<Account, Long> {

    Account findByCodeSn(@Param("codeSn") String codeSn);

    /**
     * 1401开头，预付费充值卡
     *
     * @return
     */
    List<Account> findByCodeSnStartsWith(@Param("codeSn") String codeSn);
}
