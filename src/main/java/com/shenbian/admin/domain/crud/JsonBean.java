package com.shenbian.admin.domain.crud;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 123 on 2015/9/22.
 */
public class JsonBean implements Serializable {

    private String jsonName;

    private List<Inner> jsonBeanList;

    private List<Photo> jsonPhotoList;


    public String getJsonName() {
        return jsonName;
    }

    public void setJsonName(String jsonName) {
        this.jsonName = jsonName;
    }

    public List<Inner> getJsonBeanList() {
        return jsonBeanList;
    }

    public void setJsonBeanList(List<Inner> jsonBeanList) {
        this.jsonBeanList = jsonBeanList;
    }

    public List<Photo> getJsonPhotoList() {
        return jsonPhotoList;
    }

    public void setJsonPhotoList(List<Photo> jsonPhotoList) {
        this.jsonPhotoList = jsonPhotoList;
    }
}
