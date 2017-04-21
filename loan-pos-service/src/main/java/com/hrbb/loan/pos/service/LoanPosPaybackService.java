package com.hrbb.loan.pos.service;

import java.util.List;
import java.util.Map;

import com.hrbb.loan.pos.dao.entity.TPaybackApplyInfo;
import com.hrbb.loan.pos.dao.entity.TReceiptInfo;

public interface LoanPosPaybackService {

	List<TReceiptInfo> getPaybackMap(Map<String, Object> reqMap);

	TReceiptInfo getReceiptInfoByReceiptId(String receiptId);

	String getContNoByReceiptId(String receiptId);

	String getReceiptTotalAmountByContNo(String contno);

	Double getLoanTotalBalanceByReceiptId(String listid);

	String getReceiptIdByPayApplyId(String listid);

	String getBeginDateByPayApplyId(String listid);

	List<TReceiptInfo> getReceiptList(Map<String, Object> reqMap);

	List<TReceiptInfo> getAllReceipts(Map<String, Object> reqMap);

	Map<String, Object> getReceiptMapByReceiptId(String receiptId);
	
	int savePaybackApply(TPaybackApplyInfo paybackApplyInfo);

	List<TReceiptInfo> getAllUnClearedReceipts();

	/**
	 * 查询还款申请信息
	 * @param reqMap
	 * @return
	 */
	public List<TPaybackApplyInfo> queryPaybackApplyList(Map<String, Object> reqMap);

	long countNumber(Map<String, Object> reqMap);

	int updateReceipt(Map<String, Object> receiptMap);

	long countCustRelatedReceiptNumber(Map<String, Object> reqMap);

    long countPaybackInfo(Map<String, Object> reqMap);

}
