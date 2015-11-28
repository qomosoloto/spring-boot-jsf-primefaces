package com.shenbian.admin.controller.activities;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.shenbian.admin.controller.product.JsonInfo;
import com.shenbian.admin.controller.product.ProductVo;
import com.shenbian.admin.service.synchronize.PrimaryCodeSn;
import com.shenbian.admin.service.synchronize.product.SkuService;
import com.shenbian.admin.util.qiniu.QRCodeService;
import com.shenbian.ng.model.Sku;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.StringReader;
import java.math.BigDecimal;

/**
 * Created by qomo on 15-10-14.
 */
public class SkuVo {

    @Getter
    @Setter
    private Sku sku;

    @Setter
    @Getter
    private ProductVo productVo;

    @Setter
    @Getter
    private JsonInfo jsonInfo;


    public SkuVo(Sku sku) {
        if (sku == null) {
            this.sku = new Sku();
            this.jsonInfo = new JsonInfo();
            this.productVo = new ProductVo(null);
        } else {

            this.sku = sku;
            String jsonInfo = sku.getJsonInfo();

            Gson gson = new Gson();

            if (!Strings.isNullOrEmpty(jsonInfo)) {
                JsonReader reader = new JsonReader(new StringReader(jsonInfo));
                reader.setLenient(true);
                if (sku.getCodeSn().startsWith(PrimaryCodeSn.SKU_MERCHANDISE.getCode().toString()) || sku.getCodeSn().startsWith(PrimaryCodeSn.SKU_COUPON.getCode().toString())) {

                    System.out.println("...................." + jsonInfo);
                    this.jsonInfo = gson.fromJson(reader, JsonInfo.class);
                    this.jsonInfo.setIfVoucher(sku.getMaxVoucher() != null ? !sku.getMaxVoucher().equals(BigDecimal.ZERO)
                            : false);

                } else {
                    this.jsonInfo = new JsonInfo();
                    this.jsonInfo.setProductCost(BigDecimal.ZERO);
                }
            } else {
                this.jsonInfo = new JsonInfo();
            }
            this.productVo = new ProductVo(sku.getProduct());
        }
    }

}

