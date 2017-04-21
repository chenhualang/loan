/**
 * 
 *	哈尔滨银行
 * Copyright (c) 2007-2015 HRBB,Inc.All Rights Reserved.
 */
package com.hrbb.loan.pos.biz.backstage.inter;

import java.util.List;
import java.util.Map;

import com.hrbb.loan.pos.dao.entity.TCreditApplyAprvInfo;
import com.hrbb.loan.pos.dao.entity.TCreditApplyAprvInfoKey;

/**
 * 
 * @author marco
 * @version $Id: CreditApplyAprvInfoBiz.java, v 0.1 2015-3-10 下午5:56:19 marco
 *          Exp $
 */
public interface CreditApplyAprvInfoBiz {

	public int deleteByPrimaryKey(TCreditApplyAprvInfoKey key);

	public int insert(TCreditApplyAprvInfo record);

	public int insertSelective(TCreditApplyAprvInfo record);

	public TCreditApplyAprvInfo selectByPrimaryKey(TCreditApplyAprvInfoKey key);

	public List<TCreditApplyAprvInfo> selectBackToInfo(
			TCreditApplyAprvInfo record);

	public int updateByPrimaryKeySelective(TCreditApplyAprvInfo record);

	public int updateByPrimaryKey(TCreditApplyAprvInfo record);

	public Map<String, Object> update(TCreditApplyAprvInfo record);

	public TCreditApplyAprvInfo selectNotSubmit(TCreditApplyAprvInfoKey key);

	public int updateByBankSerialInfo(TCreditApplyAprvInfo record,
			List<Map<String, Object>> bankSerialList);

	public List<TCreditApplyAprvInfo> selectInforAuditList(
			TCreditApplyAprvInfo record);

	public int updateBackToReview(TCreditApplyAprvInfo record);

	public int sendMsg(String loanId);

	public TCreditApplyAprvInfo selectLastSubbmitted(TCreditApplyAprvInfoKey key);

	public Map<String, Object> updateCreditApplyForUpSign(
			TCreditApplyAprvInfo record);

	public int updateCreditApplyAprvInfo(Map<String, Object> reqMap);

	public Map<String, Object> updateCreditApplyForCashPooling(
			TCreditApplyAprvInfo record, Map<String, Object> resultMap);
	
	public int adjustApprovalInfo(String loanId, String applyStatus, String userName);
	
}
