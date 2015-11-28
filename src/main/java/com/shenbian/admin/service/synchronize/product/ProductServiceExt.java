package com.shenbian.admin.service.synchronize.product;

import com.shenbian.admin.util.base.QueryResultType;
import com.shenbian.ng.model.Product;

import java.util.List;
import java.util.Map;

/**
 * Created by qomo on 15-10-8.
 */
public interface ProductServiceExt {


    List<Product> findProducts();

    @Deprecated
    List<Product> customSearch(Map<String, Object> map);

    /**
     * 参数化查询，根据不同参数组合进行动态查询
     *
     * @param map        查询参数，建议可以做成 queryBean
     * @param resultType 结果类型，如果是0，则返回分页后的一页List<Product>；如果是1，则返回Long,代表查询的结果总数
     * @param start      分页起始值
     * @param pageSize   分页每页数量
     * @return
     */
    Object paramsSearch(Map<String, Object> map, QueryResultType resultType, Integer start, Integer pageSize);


    /**
     * 查询产品名和 ID
     *
     * @return
     */
    List<Object[]> productIdNameList();
}
