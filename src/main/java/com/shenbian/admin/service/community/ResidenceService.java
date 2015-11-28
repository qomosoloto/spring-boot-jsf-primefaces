package com.shenbian.admin.service.community;

import com.shenbian.ng.model.Residence;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by qomo on 15-10-19.
 */
public interface ResidenceService extends JpaRepository<Residence, Long>, ResidenceServiceExt {
}
