package com.shenbian.admin.domain.crud;

/**
 * Created by 123 on 2015/9/21.
 */
public enum OptionType implements EnumExt {
    Add("ADD"),
    Remove("REMOVE"),
    Update("UPDATE"),
    Query("QUERY"),;

    String name;
    Integer value;



    OptionType(String name) {
        this.name = name;
        this.value = this.ordinal();
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }


}
