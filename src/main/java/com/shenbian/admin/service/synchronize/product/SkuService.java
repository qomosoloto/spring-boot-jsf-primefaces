package com.shenbian.admin.service.synchronize.product;

import com.shenbian.ng.model.Sku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by qomo on 15-10-9.
 */
public interface SkuService extends JpaRepository<Sku, Long>, SkuServiceExt, JpaSpecificationExecutor<Sku> {

    /**
     * 4102
     *
     * @param codeSn
     * @return
     */
    List<Sku> findByCodeSnStartsWith(@Param("codeSn") String codeSn);

    Sku findByCodeSn(@Param("codeSn") String codeSn);
}
