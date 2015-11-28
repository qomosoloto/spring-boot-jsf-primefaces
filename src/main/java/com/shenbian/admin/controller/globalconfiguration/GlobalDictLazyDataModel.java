package com.shenbian.admin.controller.globalconfiguration;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.shenbian.admin.service.globaldict.GlobalDictService;
import com.shenbian.admin.util.base.QueryResultType;
import com.shenbian.ng.model.GlobalDict;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;

/**
 * Created by qomo on 15-11-2.
 */
public class GlobalDictLazyDataModel extends LazyDataModel<GlobalDictVo> {
    @Getter
    @Setter
    private GlobalDictService globalDictService;

    @Getter
    @Setter
    private List<GlobalDictVo> globalDictVoList;

    public GlobalDictLazyDataModel(GlobalDictService globalDictService, Map<String, Object> map) {
        this.globalDictService = globalDictService;
    }

    @Override
    public List<GlobalDictVo> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        List<GlobalDict> list = (List<GlobalDict>) globalDictService.findAdsAndPromotions(Maps.newHashMap(), QueryResultType.COLLECTION, first, pageSize);

        this.globalDictVoList = Lists.newArrayList();

        if (list != null && list.size() > 1) {
            for (GlobalDict globalDict : list) {
                GlobalDictVo globalDictVo = new GlobalDictVo(globalDict);
                this.globalDictVoList.add(globalDictVo);
            }
        }

        if (super.getRowCount() <= 0) {
            this.setRowCount(((Long) globalDictService.findAdsAndPromotions(Maps.newHashMap(), QueryResultType.TOTAL_ROWS, first, pageSize)).intValue());
        }
        this.setPageSize(pageSize);
        return this.globalDictVoList;
    }
}
