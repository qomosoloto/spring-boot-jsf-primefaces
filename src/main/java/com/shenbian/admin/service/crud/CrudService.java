package com.shenbian.admin.service.crud;

import com.shenbian.admin.domain.crud.Crud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by qomo on 15-9-26.
 */
@Service
public interface CrudService extends JpaRepository<Crud, Integer> ,CrudServiceExt {

}
