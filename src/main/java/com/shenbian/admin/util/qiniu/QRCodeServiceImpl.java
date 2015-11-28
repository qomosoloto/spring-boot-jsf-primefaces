package com.shenbian.admin.util.qiniu;

import com.google.gson.Gson;
import com.shenbian.admin.controller.activities.SkuVo;
import com.shenbian.admin.controller.product.JsonInfo;
import com.shenbian.admin.service.synchronize.product.SkuService;
import com.shenbian.ng.model.Sku;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by qomo on 15-10-31.
 */
@Component("qrCodeService")
public class QRCodeServiceImpl implements QRCodeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QRCodeServiceImpl.class);

    @Autowired
    private QiNiuService qiNiuService;


    @Autowired
    private SkuService skuService;

    private String apiUrl = "";

    /**
     * 商品页地址是什么
     *
     * @param codeSn
     */
    @Override
    public void createTwoDimensionCode(String codeSn) {

        try {
            String url = apiUrl + "/merchandise/" + codeSn + "/checkin";
            String path = System.getProperty("user.dir") + "/QRCode.png";

            TwoDimensionCode handler = new TwoDimensionCode();
            OutputStream output = new FileOutputStream(path);
            handler.encoderQRCode(url, output, "png", 5);

            File streamFile = new File(path);
            List<String> fileNameList = new ArrayList<String>();
            String qrcodeUrl = getUUID();

            int status = qiNiuService.uploadFile(streamFile, qrcodeUrl);
            if (status != 200) {
                fileNameList.add(codeSn);
            }

            //  保存 qrcodeUrl
            Sku byCodeSn = skuService.findByCodeSn(codeSn);
            SkuVo skuVo = new SkuVo(byCodeSn);
            JsonInfo jsonInfo = skuVo.getJsonInfo() == null ? new JsonInfo() : skuVo.getJsonInfo();
            jsonInfo.setQrcodeUrl(qrcodeUrl);
            byCodeSn.setJsonInfo(new Gson().toJson(jsonInfo));
            skuService.save(byCodeSn);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    public static File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File convFile = new File(multipart.getOriginalFilename());
        multipart.transferTo(convFile);
        return convFile;
    }

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        str = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23)
                + str.substring(24);
        return str;
    }
}
