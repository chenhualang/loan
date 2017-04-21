package com.hrbb.loan.pos.service;

import java.util.List;
import java.util.Map;

public interface LoanPosPaybackRunningRecordService {
	public List<Map<String, Object>> getPaybackRunningRecordList(Map<String, Object> reqMap);

	public int insertPaybackRunningInfo(Map<String, Object> runnningMap);

	public List<Map<String, Object>> queryPaybackRunningInfo(Map<String, Object> reqMap);

	public List<Map<String, Object>> queryPaybackRunnigRecordByReceiptId(
			Map<String, Object> reqMap);

	public long countNumber(Map<String, Object> reqMap);
	
	public Map<String, Object> getPdfInfo(String paybackRunningRecordId);
}
