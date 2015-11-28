package com.shenbian.admin.util.base;

import com.shenbian.ng.model.AbstractSequenceEntity;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.Repository;

/**
 * Created by jason on 9/12/15.
 */
public interface SequenceRepository extends Repository<AbstractSequenceEntity, Long> {
    @Procedure(procedureName = "ng_proc_sequence")
    Long nextValue(String seqName);
}