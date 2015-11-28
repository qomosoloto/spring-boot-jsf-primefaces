package com.shenbian.admin.service.user;

import com.shenbian.ng.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

/**
 * Created by qomo on 15-10-8.
 */
@Service
public interface UserService extends JpaRepository<User, Long> ,UserServiceExt{
    User findByCodeSn(@Param("codeSn") String codeSn);
}
