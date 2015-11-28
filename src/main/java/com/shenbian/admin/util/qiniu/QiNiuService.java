package com.shenbian.admin.util.qiniu;


import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @项目名称：shenbian-qiniu
 * @类名称：QiNiuTemplate
 * @类描述：
 * @创建人：wangyuxin
 * @创建时间：2015年6月8日 上午9:16:09
 * @修改人：wangyuxin
 * @修改时间：2015年6月8日 上午9:16:09
 * @修改备注：
 */
public interface QiNiuService {
    /**
     * 上传文件
     *
     * @param localFilePath 本地文件路径
     * @param key           文件Key
     * @return String 文件hash
     * @throws QiNiuException 当出现异常时抛出该异常
     */
    public int uploadFile(String localFilePath, String key) throws QiNiuException;

    /**
     * 上传文件
     *
     * @param fileType      文件类型 (在实例化时序自定义)
     * @param localFilePath 本地文件路径
     * @param key           文件Key
     * @return String 文件hash
     * @throws QiNiuException 当出现异常时抛出该异常
     */
    public int uploadFile(String fileType, String localFilePath, String key) throws QiNiuException;

    /**
     * 上传文件
     *
     * @param localFile 本地文件
     * @param key       文件Key
     * @return String 文件hash
     * @throws QiNiuException 当出现异常时抛出该异常
     */
    public int uploadFile(File localFile, String key) throws QiNiuException;

    /**
     * 上传文件
     *
     * @param fileType  文件类型 (在实例化时序自定义)
     * @param localFile 本地文件
     * @param key       文件Key
     * @return String 文件hash
     * @throws QiNiuException 当出现异常时抛出该异常
     */
    public int uploadFile(String fileType, File localFile, String key) throws QiNiuException;

    /**
     * 删除
     *
     * @param key key名
     * @throws QiNiuException 当出现异常时抛出该异常
     */
    public void delete(String key) throws QiNiuException;

}
