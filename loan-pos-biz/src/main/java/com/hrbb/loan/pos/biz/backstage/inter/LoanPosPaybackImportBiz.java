package com.hrbb.loan.pos.biz.backstage.inter;

import java.util.List;
import java.util.Map;

import com.hrbb.loan.pos.dao.entity.TPaybackImportInfo;
import com.hrbb.loan.pos.dao.entity.TReceiptInfo;

public interface LoanPosPaybackImportBiz {

	List<TPaybackImportInfo> queryPaybackImportInfo(Map<String, Object> reqMap);

	List<TPaybackImportInfo> queryMatchedPaybackImportInfo(Map<String, Object> reqMap);
	
	/**
	 * 获取还款进项流水信息
	 * @param reqMap
	 * @return
	 */
	List<TPaybackImportInfo> queryTransferInRecord(Map<String, Object> reqMap);

	long countImportNumber(Map<String, Object> reqMap);

	Map<String, Object> addListRepayment(String receiptId,String actualTotalAmount, String actualCapital,String actualInterest);

	Map<String, Object> getImportInfobyId(String importRunningId);

}
