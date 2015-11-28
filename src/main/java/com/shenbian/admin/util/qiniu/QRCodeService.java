package com.shenbian.admin.util.qiniu;


import com.shenbian.ng.model.Sku;
import org.springframework.stereotype.Component;

/**
 * Created by qomo on 15-10-31.
 */

public interface QRCodeService {

    void createTwoDimensionCode(String codeSn);
}
