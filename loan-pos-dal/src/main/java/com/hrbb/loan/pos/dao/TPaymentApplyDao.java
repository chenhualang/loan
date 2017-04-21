package com.hrbb.loan.pos.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hrbb.loan.pos.dao.entity.TPaymentApply;

public interface TPaymentApplyDao {
    int deleteByPrimaryKey(String payApplyId);

    int insert(TPaymentApply record);

    int insertSelective(TPaymentApply record);

    TPaymentApply selectByPrimaryKey(String payApplyId);

    int updateByPrimaryKeySelective(TPaymentApply record);

    int updateByPrimaryKey(TPaymentApply record);
    
    public int insertSelectiveMap(Map<String, Object> reqMap);
    
    public int updateSelectiveMap(Map<String, Object> reqMap);
    
    public Long countPaymentApplyNum(Map<String, Object> reqMap);
    
    public List<Map<String, Object>> queryByStdmerno(Map<String, Object> reqMap);
    
    /**
     * 查询用款申请
     * 
     * @param reqMap
     * @return
     */
    public List<Map<String,Object>> queryPaymentApply(Map<String,Object> reqMap);
    /**
     * 查询用款总数
     * 
     * @param reqMap
     * @return
     */
    public Long countPaymentApplys(Map<String,Object> reqMap);
    
    /**
     * 查询用款申请(主键查询)
     * 
     * @param reqMap
     * @return
     */
    public List<Map<String, Object>> queryPaymentApplyByPayApplyId(Map<String, Object> reqMap);
    
    /**
     * 
     * @param payApplyId
     * @return
     */
    public List<Map<String, Object>> selectListByPrimaryKey(String payApplyId);
    
    public List<Map<String, Object>> selectListByZzApp(Map<String, Object> map);
    
    public List<Map<String, Object>> selectListBySlApp(Map<String, Object> map);
    
	/**
	 * 当日放款明细同步接口
	 * 
	 * @return
	 */
	public List<TPaymentApply> selectRecordInfoLending();
	/**
	 * 当日放款明细同步接口
	 * 
	 * @return
	 */
	public List<TPaymentApply> selectRecordInfoCommission();

    /**
     * 可认领用款审核
     * 
     * @param reqMap
     * @return
     */
    List<Map<String, Object>> queryAvailablePaymentApply(Map<String, Object> reqMap);

    /**
     * 可认领用款审核总数
     * 
     * @param reqMap
     * @return
     */
    Long countAvailablePaymentApplys(Map<String, Object> reqMap);

    /**
     * 查询审批通过用款申请
     * 
     * @param reqMap
     * @return
     */
    List<Map<String, Object>> queryPaymentApply90(Map<String, Object> reqMap);

    /**
     * 
     * @param reqMap
     * @return
     */
    Long countPaymentApplys90(Map<String, Object> reqMap);

	BigDecimal getPaymentApplyInfocontno(String contno);
	
	public TPaymentApply selectPayApplyAmtSum(Map<String, Object> map);
	
	public Map<String, Object> queryPaymentApplyByIssueId(Map<String, Object> map);

	String getPaymentApplyIdByContno(String contno);
	
	public String sumHasRepayedAmt(Map<String, Object> reqMap);
	
	public Map<String, Object> queryByStdisno(Map<String, Object> reqMap);
	
	public List<Map<String, Object>> selectPaymentAuto(List<String> list);
}