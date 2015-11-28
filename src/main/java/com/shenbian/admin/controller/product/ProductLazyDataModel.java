package com.shenbian.admin.controller.product;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.shenbian.admin.service.synchronize.product.ProductService;
import com.shenbian.admin.util.base.QueryResultType;
import com.shenbian.ng.model.Product;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.bean.ViewScoped;
import java.util.List;
import java.util.Map;

/**
 * Created by qomo on 15-10-29.
 */
//@Component
//@ViewScoped
public class ProductLazyDataModel extends LazyDataModel<ProductVo> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductLazyDataModel.class);

    @Getter
    @Setter
    private ProductService productService;

    @Getter
    @Setter
    private List<ProductVo> productVoList;

    @Setter
    @Getter
    private Map<String, Object> paramMap;

    public ProductLazyDataModel(ProductService productService, Map<String, Object> paramMap) {
        this.productService = productService;
        this.paramMap = paramMap;
        LOGGER.debug("lazy productVoList model inited");
    }


    @Override
    public List<ProductVo> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        LOGGER.info("[[[[[[[[[[first]]]]]]]]]..." + first);
        LOGGER.info("[[[[[[[[[[pageSize]]]]]]]]]..." + pageSize);

        List<Product> productList = (List<Product>) productService.paramsSearch(paramMap, QueryResultType.COLLECTION, first, pageSize);
        this.productVoList = Lists.newArrayList();
        for (Product product : productList) {
            ProductVo vo = new ProductVo(product);
            this.productVoList.add(vo);
        }

        // set the total number of students that have been found  
        if (super.getRowCount() <= 0) {
            Long total = (Long) productService.paramsSearch(paramMap, QueryResultType.TOTAL_ROWS, first, pageSize);
            this.setRowCount(total.intValue());
        }

        // set the page size  
        this.setPageSize(pageSize);

        LOGGER.debug("Total record match searching criteria: " + getRowCount());

        return productVoList;
    }
}
