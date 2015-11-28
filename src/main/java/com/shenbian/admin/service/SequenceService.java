package com.shenbian.admin.service;

import com.shenbian.ng.model.AbstractSequenceEntity;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.Repository;

/**
 * Created by jason on 10/14/15.
 */
public interface SequenceService extends Repository<AbstractSequenceEntity, Long> {
    @Procedure(procedureName = "ng_proc_sequence")
    Long nextValue(String seqName);
}
