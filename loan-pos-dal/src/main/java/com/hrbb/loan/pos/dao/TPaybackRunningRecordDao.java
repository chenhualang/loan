package com.hrbb.loan.pos.dao;

import java.util.List;
import java.util.Map;

import com.hrbb.loan.pos.dao.entity.TPaybackRunningRecord;

public interface TPaybackRunningRecordDao {
    int deleteByPrimaryKey(String paybackRunningRecordId);

    int insert(TPaybackRunningRecord record);

    int insertSelective(TPaybackRunningRecord record);

    TPaybackRunningRecord selectByPrimaryKey(String paybackRunningRecordId);

    int updateByPrimaryKeySelective(TPaybackRunningRecord record);

    int updateByPrimaryKey(TPaybackRunningRecord record);
    
    List<Map<String, Object>> getPaybackRunningRecordList(Map<String, Object> reqMap);
    
    List<TPaybackRunningRecord> selectRecordInfoHB17();

	int insertPaybackRunningInfo(Map<String, Object> runnningMap);

	List<Map<String, Object>> queryPaybackRunningInfo(Map<String, Object> reqMap);

	List<Map<String, Object>> queryPaybackRunnigRecordByReceiptId(
			Map<String, Object> reqMap);

	long countNumber(Map<String, Object> reqMap);
	
	Map<String, Object> queryPdfInfo(String paybackRunningRecordId);
	
}