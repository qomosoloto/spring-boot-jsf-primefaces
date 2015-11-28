package com.shenbian.admin.domain.user;

import java.io.Serializable;

/**
 * Created by qomo on 15-9-25.
 */
public class User implements Serializable {

    private String name;

    private String password;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
