package com.shenbian.admin.controller.globalconfiguration;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by qomo on 15-11-2.
 * 首页配置的JSON信息
 */
@Getter
@Setter
public class GlobaldictJson {

    private String image;

    private ProductModelType productModelType;

    private String id;

    private Props props;

    private String composing;

    private List<GlobaldictJson> composingContent;

    @Getter
    @Setter
    class Props {
        private String title;
        private String image;
        private BigDecimal voucher;
        private String id;
        private String url;
    }

    enum ProductModelType {
        SERVE_EXTERNAL("SERVE_EXTERNAL"),
        MERCHANDISE_LIST("MERCHANDISE_LIST"),
        SERVE_MERCHANDISE("SERVE_MERCHANDISE");

        @Getter
        private String name;
        @Getter
        private Integer value;

        ProductModelType(String name) {
            this.name = name;
            this.value = this.ordinal();
        }


    }
}
