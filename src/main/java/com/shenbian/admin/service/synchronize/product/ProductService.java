package com.shenbian.admin.service.synchronize.product;

import com.shenbian.ng.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qomo on 15-10-8.
 */
@Service
public interface ProductService extends JpaRepository<Product, Long>, ProductServiceExt {


    /**
     * 实物商品　４００２
     *
     * @param codeSn
     * @return
     */
    List<Product> findByCodeSnStartsWith(@Param("codeSn") String codeSn);
}
