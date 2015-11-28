package com.shenbian.admin.controller.product;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.shenbian.admin.service.synchronize.PrimaryCodeSn;
import com.shenbian.ng.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.io.StringReader;
import java.math.BigDecimal;

/**
 * Created by qomo on 15-10-23.
 */
public class ProductVo implements Serializable {
    public ProductVo(Product product) {
        if (product == null) {
            this.product = new Product();
            this.jsonInfo = new JsonInfo();
            this.jsonInfo.setProductCost(BigDecimal.ZERO);
        } else {
            this.product = product;
            String jsonInfo = product.getJsonInfo();
            Gson gson = new Gson();

            if (!Strings.isNullOrEmpty(jsonInfo)) {
                JsonReader reader = new JsonReader(new StringReader(jsonInfo));
                reader.setLenient(true);
                if (product.getCodeSn().startsWith(PrimaryCodeSn.PRODUCT_MERCHANDISE.getCode().toString()) || product.getCodeSn().startsWith(PrimaryCodeSn.PRODUCT_COUPON.getCode().toString())) {

                    this.jsonInfo = gson.fromJson(reader, JsonInfo.class);
                    if (this.jsonInfo.getProductCost() == null) {
                        this.jsonInfo.setProductCost(BigDecimal.ZERO);
                    }
                }
            } else {
                this.jsonInfo = new JsonInfo();
                this.jsonInfo.setProductCost(BigDecimal.ZERO);
            }
        }
    }


    @Getter
    @Setter
    private JsonInfo jsonInfo;

    @Getter
    @Setter
    private Product product;
}
