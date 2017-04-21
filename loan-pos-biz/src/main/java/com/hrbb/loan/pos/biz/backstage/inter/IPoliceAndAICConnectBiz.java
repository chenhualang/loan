package com.hrbb.loan.pos.biz.backstage.inter;

import java.util.List;
import java.util.Map;

public interface IPoliceAndAICConnectBiz {
	
	public Map<String, Object> getPoliceInfo(String reqXml, String custId);
	
	public Map<String, String> getPolicReqXmp(String idNo, String name);
	
	public boolean hasPoliceInfo(String idNo, String name);
	
	public boolean hasAICInfo(String posCustName);
	
	public Map<String, String> getAICReqXml(Map<String, Object> reqMap) throws Exception;
	
	public Map<String, String> getAICInfo(Map<String, String> reqMap) throws Exception;
	
	public String getBankCardCheckUrl(Map<String, String> reqMap);
	
	public boolean hasBankCardCheck(String bankAccno);
	
	public Map<String, String> updateBankAccnoStatus(String resultMessage, boolean success, String bankAccno, String channel, String checkChannel, Map<String, String> bankMap) throws Exception;
	
	/**
	 * 查询工商信息
	 * 
	 * @param posCustName
	 * @return
	 */
	public Map<String,List<Map<String,Object>>> queryAicInfo(String posCustName, String posCustId);
	
}
