package com.shenbian.admin.util.qiniu;

/**
 * @项目名称：shenbian-qiniu
 * @类名称：QiNiuConstants
 * @类描述：
 * @创建人：wangyuxin
 * @创建时间：2015年6月8日 上午9:15:48
 * @修改人：wangyuxin
 * @修改时间：2015年6月8日 上午9:15:48
 * @修改备注：
 * @version
 * 
 */
public interface QiNiuConstants {
    /**
     * 成功
     */
    public int SUCCESS_CODE = 200;

    /**
     * 错误请求
     */
    public int BAD_REQUEST_CODE = 400;

    /**
     * 未验证通过
     */
    public int UNAUTHORIZED_CODE = 401;

    /**
     * 请求方法不允许
     */
    public int METHOD_NOT_ALLOWED_CODE = 405;

    /**
     * 服务端错误
     */
    public int SERVER_ERROR_CODE = 599;

    /**
     * 文件不存在
     */
    public int FILE_NOT_EXIST_CODE = 612;

    /**
     * 文件已存在
     */
    public int FILE_EXIST_CODE = 614;

    /**
     * Bucket不存在
     */
    public int BUCKET_NOT_EXIST = 631;
}
