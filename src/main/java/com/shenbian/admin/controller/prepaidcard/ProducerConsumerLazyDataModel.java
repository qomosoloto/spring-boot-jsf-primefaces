package com.shenbian.admin.controller.prepaidcard;

import com.google.common.collect.Lists;
import com.shenbian.admin.service.producerconsumer.ProducerConsumerService;
import com.shenbian.admin.service.synchronize.product.ProductService;
import com.shenbian.admin.util.base.QueryResultType;
import com.shenbian.ng.model.ProducerConsumer;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;

/**
 * Created by qomo on 15-11-25.
 */
public class ProducerConsumerLazyDataModel extends LazyDataModel<ProducerConsumer> {

    @Getter
    @Setter
    private ProducerConsumerService producerConsumerService;

    @Setter
    @Getter
    private List<ProducerConsumer> producerConsumerList;

    public ProducerConsumerLazyDataModel(ProducerConsumerService producerConsumerService) {
        this.producerConsumerService = producerConsumerService;
    }

    @Override
    public List<ProducerConsumer> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        this.producerConsumerList = (List<ProducerConsumer>) producerConsumerService.pageQuery(QueryResultType.COLLECTION, first, pageSize);

        if (super.getRowCount() <= 0) {
            this.setRowCount((Integer) producerConsumerService.pageQuery(QueryResultType.TOTAL_ROWS, first, pageSize));
        }

        this.setPageSize(pageSize);
        return this.producerConsumerList;
    }
}
