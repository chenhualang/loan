package com.hrbb.loan.pos.service;

import java.util.List;
import java.util.Map;

import com.hrbb.loan.pos.dao.entity.TPaybackImportInfo;
public interface LoanPosPaybackImportService {

	List<TPaybackImportInfo> queryPaybackImportInfo(Map<String, Object> reqMap);

	List<TPaybackImportInfo> queryMatchedPaybackImportInfo(Map<String, Object> reqMap);

	TPaybackImportInfo getPaybackImportInfo(String importRunningId);

	int updateImportInfo(Map<String, Object> updateImportMap);
	
	/**
	 * 获取还款进项流水信息
	 * @param reqMap
	 * @return
	 */
	List<TPaybackImportInfo> queryTransferInRecord(Map<String, Object> reqMap);

	long countImportNumber(Map<String, Object> reqMap);

	Map<String, Object> getImportInfobyId(String importRunningId);

}
