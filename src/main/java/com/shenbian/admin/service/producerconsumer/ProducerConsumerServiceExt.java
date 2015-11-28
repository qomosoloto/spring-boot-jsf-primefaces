package com.shenbian.admin.service.producerconsumer;

import com.shenbian.admin.util.base.QueryResultType;
import com.shenbian.ng.model.ProducerConsumer;
import org.primefaces.model.UploadedFile;

import java.util.List;

/**
 * Created by qomo on 15-10-12.
 */
public interface ProducerConsumerServiceExt {
    /**
     * 同步优惠券数据
     */
    void synchronizeProducerConsumer();

    /**
     * 插入新的优惠券
     */
    String importCouponsFile(UploadedFile file) throws Exception;

    /**
     * 分页查询
     *
     * @param queryResultType
     * @param first
     * @param pageSize
     */
    Object pageQuery(QueryResultType queryResultType, Integer first, Integer pageSize);
}
