package com.shenbian.admin.domain.vo.photo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by qomo on 15-10-28.
 */
@Getter
@Setter
public class PhotoVo implements Serializable {
    /**
     * imageType:图片类型
     *
     * @since Ver 1.1
     */
    private Integer imageType;

    /**
     * url:图片路径
     *
     * @since Ver 1.1
     */
    private String url;

    /**
     * merchandiseId:商品id
     * 兼容老数据
     *
     * @since Ver 1.1
     */
    private Long merchandiseId;

    /**
     * 关联product 的 codeSn
     */
    private String productCodeSn;
}

