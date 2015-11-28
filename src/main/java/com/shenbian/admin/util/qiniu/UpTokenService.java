package com.shenbian.admin.util.qiniu;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

/**
 * Created by 123 on 2015/9/22.
 * <p>
 * <p>
 * qiniu.accessKey=zFokh2N7F2ykJSHvE2KK3fo04xAKDnodj6Mxkn-D
 * qiniu.secretKey=TRgMl5eMLGTMcN_twiaXrq1oSqNQyGM_6gNLAIUv
 * qiniu.bucket=wangyuxin
 * qiniu.domain=http://wangyuxin.qiniudn.com/
 */
@Component
public class UpTokenService {

    private static String token = "wangyuxin";

    /**
     * 生成上传token
     *
     * @param bucket  空间名
     * @param key     key，可为 null
     * @param expires 有效时长，单位秒。默认3600s
     * @param policy  上传策略的其它参数，如 new StringMap().put("endUser", "uid").putNotEmpty("returnBody", "")。
     *                scope通过 bucket、key间接设置，deadline 通过 expires 间接设置
     * @param strict  是否去除非限定的策略字段，默认true
     * @return 生成的上传token
     */
    public void uploadToken(String bucket, String key, long expires, StringMap policy, boolean strict) {


    }


    public static String returnToken() {
        Auth auth = Auth.create("zFokh2N7F2ykJSHvE2KK3fo04xAKDnodj6Mxkn-D", "TRgMl5eMLGTMcN_twiaXrq1oSqNQyGM_6gNLAIUv");
        return auth.uploadToken(token, null, 36000, null);

    }
}
