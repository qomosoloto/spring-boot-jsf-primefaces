package com.shenbian.admin.util.base;

import lombok.Getter;

/**
 * Created by qomo on 15-10-30.
 * 供查询使用，分页查询的时候需要返回总记录数和一页数据
 * 用于区分操作所获得结果的类型
 */
public enum QueryResultType {
    COLLECTION("集合"),
    TOTAL_ROWS("结果总数");

    @Getter
    private Integer code;
    private String name;

    QueryResultType(String name) {
        this.code = this.ordinal();
        this.name = name;
    }
}
