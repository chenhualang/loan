package com.hrbb.loan.pos.biz.backstage.inter;

import java.util.List;
import java.util.Map;

import com.hrbb.loan.pos.dao.entity.TPaymentApply;

/**
 * 用款申请
 * 
 * @author jianQing
 * @version $Id: IPaymentApplyBiz.java, v 0.1 2015年4月27日 下午5:53:08 jianQing Exp $
 */
public interface IPaymentApplyBiz {
	
	Map<String, Object> insertPaymentApply(Map<String, Object> reqMap);
	
	void updatePaymentApply(Map<String, Object> reqMap);

	
	Map<String, Object> queryPaymentByStdmerno(String custId, String payApplyId, String contNo,String stdshno, String stdmerno, String channel, String startnum, String recnum);

	
	/**
	 * 用款申请查询
	 * 
	 * @param reqMap
	 * @return
	 */
	List<Map<String, Object>> queryPaymentApply(Map<String,Object> reqMap);
	

    /**
     * 用款申请查询(主键查询)
     * 
     * @param reqMap
     * @return
     */
    List<Map<String, Object>> queryPaymentApplyByPayApplyId(Map<String, Object> reqMap);
	
	/**
	 * 通过Id查询用款详情
	 * 
	 * @param id
	 * @return
	 */
	TPaymentApply queryPaymentApplyById(String id);
	
	/**
	 * 新增用款申请
	 * 
	 * @param reqMap
	 * @return
	 */
	Map<String, Object> insertPaymentApplyZJ(Map<String, Object> reqMap);
	
	Map<String, Object> insertPaymentApplyByZzApp(Map<String, Object> reqMap);
	
	List<Map<String, Object>> queryPaymentApplyDetailsByZzApp(Map<String,Object> queryMap);
	
	List<Map<String, Object>> queryPaymentApplyDetailsBySlApp(Map<String,Object> queryMap);

    /**
     * 查询可认领用款列表
     * 
     * @param reqMap
     * @return
     */
    List<Map<String, Object>> queryAvailablePaymentApply(Map<String, Object> reqMap);
    
    public int savePaymentSign(Map<String, Object> reqApply,Map<String, Object> reqAcc);

	String getPaymentApplyIdByContno(String contno);
    
}
