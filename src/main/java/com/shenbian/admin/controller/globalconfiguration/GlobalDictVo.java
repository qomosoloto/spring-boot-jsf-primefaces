package com.shenbian.admin.controller.globalconfiguration;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shenbian.ng.model.GlobalDict;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by qomo on 15-11-2.
 */
@Getter
@Setter
public class GlobalDictVo {

    private GlobalDict globalDict;

    /**
     * 首页推广 / 广告 / 首单推荐
     */
    private List<GlobaldictJson> globaldictJsonList;


    public GlobalDictVo(GlobalDict globalDict) {
        this.globalDict = globalDict;

        //  首页推广，首页广告，首单推荐都存在value字段
        if (!Strings.isNullOrEmpty(globalDict.getValue())) {
            this.globaldictJsonList = new Gson().fromJson(globalDict.getValue(), new TypeToken<List<GlobaldictJson>>() {
            }.getType());
        } else {
            this.globaldictJsonList = Lists.newArrayList();
        }
    }
}
