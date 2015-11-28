package com.shenbian.admin.service.producerconsumer;

import com.shenbian.ng.model.ProducerConsumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qomo on 15-10-12.
 */
@Service
public interface ProducerConsumerService extends JpaRepository<ProducerConsumer, Long>, ProducerConsumerServiceExt {

    List<ProducerConsumer> findByCodeSnStartsWith(@Param("codeSn") String codeSn);
}
