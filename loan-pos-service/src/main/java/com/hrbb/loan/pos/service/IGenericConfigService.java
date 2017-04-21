/**
 * 
 */
package com.hrbb.loan.pos.service;

import java.util.Date;
import java.util.Map;

import com.hrbb.loan.pos.dao.entity.TransactionLog;

/**
* <p>Title: IGenericConfigService.java </p>
* <p>Description:  </p>
* <p>Copyright: Copyright (C) 2015 Hrbb Ltd. Co.</p> 
*     
* @author linzhaolin@hrbb.com.cn
* @version 
* @date 2015-4-16
*
* logs: 1. 
*/
public interface IGenericConfigService {
	public Map<String, Object> getIssuerInfo(String bin);
	
	public String getMobileBelong(String mobile);
	
	/**
	 * 取得传入日期所对应的下一工作日，如果当日为工作日返回当日
	 * @param date
	 * @return
	 */
	public String getNextWorkingDate(Date date);
	/**
	 * 获取工作日
	 * @param date
	 * @return
	 */
	public Date getWorkingDate(Date date);
	
	/**
	 * 获取展业机构/推荐人信息
	 * @param contactNo
	 * @return
	 */
	public Map<String, Object> getRecInfo(String contactNo);
	
	public Map<String, Object> getRecInfoById(Integer id);
	
	/**
	 * 记录核算接口发送记录
	 * @param map
	 * @return
	 */
	public int insertTransactionLog(TransactionLog log);
}
