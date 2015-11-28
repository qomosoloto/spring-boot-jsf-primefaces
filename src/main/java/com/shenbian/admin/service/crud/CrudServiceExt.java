package com.shenbian.admin.service.crud;

import com.shenbian.admin.domain.crud.Crud;

import java.util.List;
import java.util.Map;

/**
 * Created by qomo on 15-9-26.
 */
public interface CrudServiceExt {
    List<Crud> paramSearch(String name, Integer age);

    List<Map<String, Object>> selectAsJson();


    List<Map<String, Object>> selectMerchandisesJSON();
}
