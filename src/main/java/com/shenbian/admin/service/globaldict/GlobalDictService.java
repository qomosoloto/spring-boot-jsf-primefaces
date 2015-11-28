package com.shenbian.admin.service.globaldict;

import com.shenbian.ng.model.GlobalDict;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by qomo on 15-11-2.
 */
public interface GlobalDictService extends JpaRepository<GlobalDict, Long>, GlobalDictServiceExt {
}
