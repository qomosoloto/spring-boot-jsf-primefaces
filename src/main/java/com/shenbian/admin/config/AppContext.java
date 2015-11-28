package com.shenbian.admin.config;

/**
 * Created by qomo on 15-9-26.
 */
public class AppContext {

    // 上下文单例化 s
    private static AppContext INSTANCE = new AppContext();

    private AppContext() {
    }

    public static AppContext getInstance() {
        return INSTANCE;
    }
    //上下文单例化 e
}
