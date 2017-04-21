package com.hrbb.loan.pos.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hrbb.loan.pos.dao.entity.TPaymentApply;

public interface PaymentApplyService {
	
	public void insertPaymentApply(Map<String, Object> reqMap);
	
	public void updatePaymentApply(Map<String, Object> reqMap);
	
	public Long countPaymentApplyMap(Map<String, Object> reqMap);
	
	public List<Map<String, Object>> queryByStdmernoMap(Map<String, Object> reqMap);
	
	public Long countPaymentApply(String custId, String stdshno, String stdmerno, String channel);
	
	public List<Map<String, Object>> queryByStdmerno(String custId, String payApplyId, String contNo,String stdshno, String stdmerno, String channel, String startnum, String endnum);
	
	/**
	 * 查询用款申请列表
	 * 
	 * @param reqMap
	 * @return
	 */
	public List<Map<String, Object>> queryPaymentApply(Map<String, Object> reqMap);
	
	/**
	 * 查询用款申请总数
	 * 
	 * @param reqMap
	 * @return
	 */
	public Long countPaymentApplys(Map<String, Object> reqMap);
	

    /**
     * 查询可认领用款申请列表
     * 
     * @param reqMap
     * @return
     */
    public List<Map<String, Object>> queryAvailablePaymentApply(Map<String, Object> reqMap);
    
    /**
     * 查询可认领用款申请总数
     * 
     * @param reqMap
     * @return
     */
    public Long countAvailablePaymentApplys(Map<String, Object> reqMap);
	
	
	/**
     * 查询用款申请(主键查询)
     * 
     * @param reqMap
     * @return
     */
    public List<Map<String, Object>> queryPaymentApplyByPayApplyId(Map<String, Object> reqMap);
	
	/**
	 * 通过id查询用款申请
	 * 
	 * @param id
	 * @return
	 */
	public TPaymentApply queryById(String id);
	
	public List<Map<String, Object>> queryPaymentListByZzApp(Map<String, Object> queryMap);
	
	public List<Map<String, Object>> queryPaymentListBySlApp(Map<String, Object> queryMap);
	/**
	 * 当日放款明细同步接口
	 * 
	 * @return
	 */
	public List<TPaymentApply> selectRecordInfoLending();
	/**
	 * 佣金代收明细文件
	 * 
	 * @return
	 */
	public List<TPaymentApply> selectRecordInfoCommission();

    /**
     * 已审核通过放款申请
     * 
     * @param reqMap
     * @return
     */
    List<Map<String, Object>> queryPaymentApply90(Map<String, Object> reqMap);

    /**
     * 已审核通过用款申请条数
     * 
     * @param reqMap
     * @return
     */
    Long countPaymentApplys90(Map<String, Object> reqMap);

	public BigDecimal getPaymentApplyInfocontno(String contno);

	public String getPaymentApplyIdByContno(String contno);
	
	public Map<String, Object> queryByStdisno(Map<String, Object> reqMap);
}
