package com.shenbian.admin.util.qiniu;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component("qiNiuService")
public class QiNiuServiceImpl implements QiNiuService {

    @Getter
    @Setter
    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Getter
    @Setter
    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Getter
    @Setter
    @Value("${qiniu.bucket}")
    private String bucket;

    @Getter
    @Setter
    @Value("${qiniu.domain}")
    private String domain;

    @Override
    public int uploadFile(String localFilePath, String key) throws QiNiuException {
        return this.uploadFile(null, localFilePath, key);
    }

    @Override
    public int uploadFile(String fileType, String localFilePath, String key) throws QiNiuException {
        return this.uploadFile("", new File(localFilePath), key);
    }

    @Override
    public int uploadFile(File localFile, String key) throws QiNiuException {
        return this.uploadFile("", localFile, key);
    }

    @Override
    public int uploadFile(String fileType, File localFile, String key) throws QiNiuException {
        UploadManager uploadManager = new UploadManager();

        Auth auth = Auth.create(accessKey, secretKey);
        String token = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFile, key, token);
            return response.statusCode;
        } catch (QiniuException e) {
            throw new QiNiuException(e.getMessage(), e);
        }

    }

    @Override
    public void delete(String key) throws QiNiuException {
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException e) {
            throw new QiNiuException(e.getMessage(), e);
        }
    }

}
