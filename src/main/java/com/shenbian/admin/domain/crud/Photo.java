package com.shenbian.admin.domain.crud;

import java.io.Serializable;

/**
 * Created by qomo on 15-9-23.
 */
public class Photo implements Serializable {
    private String url;

    private String name;

    private String description;

    private boolean delete;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }
}
