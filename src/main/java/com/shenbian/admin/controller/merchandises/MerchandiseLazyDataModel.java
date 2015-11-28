package com.shenbian.admin.controller.merchandises;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.shenbian.admin.controller.activities.SkuVo;
import com.shenbian.admin.service.synchronize.product.SkuService;
import com.shenbian.admin.util.base.QueryResultType;
import com.shenbian.ng.model.Sku;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;

/**
 * Created by qomo on 15-10-30.
 */
public class MerchandiseLazyDataModel extends LazyDataModel<SkuVo> {

    @Getter
    @Setter
    private SkuService skuService;

    /**
     * 不要在这里初始化 skuVoList!
     */
    @Getter
    @Setter
    private List<SkuVo> skuVoList;

    @Getter
    @Setter
    private Map<String, Object> searchMap;

    public MerchandiseLazyDataModel(SkuService skuService, Map<String, Object> map) {
        this.searchMap = map;
        this.skuService = skuService;
    }

    @Override
    public List<SkuVo> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        List<Sku> skus = (List<Sku>) this.skuService.customSearch(searchMap, 0, QueryResultType.COLLECTION, first, pageSize);
        this.skuVoList = Lists.newArrayList();
        if (skus != null && skus.size() > 0) {
            for (Sku sku : skus) {
                SkuVo skuVo = new SkuVo(sku);
                this.skuVoList.add(skuVo);
            }
        }

        // 设置总记录数
        if (super.getRowCount() <= 0) {
            Long total = (Long) skuService.customSearch(searchMap, 0, QueryResultType.TOTAL_ROWS, first, pageSize);
            this.setRowCount(total.intValue());
        }

        // 设置每页展示数
        this.setPageSize(pageSize);
        return this.skuVoList;
    }


}

