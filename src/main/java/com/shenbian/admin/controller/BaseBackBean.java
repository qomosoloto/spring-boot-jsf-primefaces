package com.shenbian.admin.controller;


import java.io.Serializable;

/**
 * Created by qomo on 15-10-14.
 */
public interface BaseBackBean extends Serializable {
    /**
     * 点击对应模块的时候，初始化列表
     */
    void init();

    /**
     * 添加新的实体
     * type 0 ,对于product而言，实物商品 ，1，对于product而言，虚拟商品
     */
    void addNewEntity(Integer type);

    /**
     * 点击列表的时候更新被选中的实体
     */
    void selectEntity(Object object);


    /**
     * 删除实体
     */
    void deleteEntity();

}
