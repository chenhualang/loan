package com.hrbb.loan.pos.service;

import java.util.List;
import java.util.Map;

import com.hrbb.loan.pos.dao.entity.TBizOperationRecord;

/**
 * 业务操作记录
 * 
 * @author cjq
 * @version $Id: BizOperationRecordService.java, v 0.1 2015年7月22日 上午11:05:09 cjq Exp $
 */
public interface BizOperationRecordService {
    
    /**
     * 保存记录
     * 
     * @param record
     * @return
     */
    int saveBizOperationRecord(TBizOperationRecord record);
    
    public int insertSelectiveMapBatch(List<Map<String, Object>> records);
}
