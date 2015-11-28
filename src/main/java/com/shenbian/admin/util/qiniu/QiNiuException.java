package com.shenbian.admin.util.qiniu;

/**
 * @项目名称：shenbian-qiniu
 * @类名称：QiNiuException
 * @类描述：
 * @创建人：wangyuxin
 * @创建时间：2015年6月8日 上午9:15:41
 * @修改人：wangyuxin
 * @修改时间：2015年6月8日 上午9:15:41
 * @修改备注：
 * @version
 * 
 */
public class QiNiuException extends Exception {
    private static final long serialVersionUID = -2972348474197736276L;

    public QiNiuException(String msg) {
        super(msg);
    }

    public QiNiuException(String msg, Throwable t) {
        super(msg, t);
    }
}
