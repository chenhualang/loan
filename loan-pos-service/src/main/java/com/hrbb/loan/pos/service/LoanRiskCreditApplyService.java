package com.hrbb.loan.pos.service;

import java.util.List;
import java.util.Map;

import com.hrbb.loan.pos.dao.entity.TCreditApply;

/**
 *<h1>业务申请接口</h1>
 *@author Johnson Song
 *@version Id: CreditApplyService.java, v 1.0 2015-2-12 下午7:53:14 Johnson Song Exp
 */
public interface LoanRiskCreditApplyService {
	
	public List<TCreditApply> getCreditApply(Map<String, Object> reqMap);
	
	public List<Map<String, Object>> getCreditApplyMap(Map<String, Object> reqMap);
	
	public TCreditApply getCreditApplyById(String loanId);
	
	public int updateCreditApply(TCreditApply tCreditApply);
	
	public int updateCreditApplyMap(Map<String, Object> map);
	
//	public int delCreditApply(String loanId);
	
	public int insertCreditApply(TCreditApply tCreditApply);
	
	public int insertCreditApplyMap(Map<String, Object> reqMap);
	
	public Long countCreditApply(Map<String, Object> reqMap);
	
//	public int deleteBatch(List<String> loanIds);
	
	public Map<String, Object> getOneCreditApply(String loanId);
	
}
