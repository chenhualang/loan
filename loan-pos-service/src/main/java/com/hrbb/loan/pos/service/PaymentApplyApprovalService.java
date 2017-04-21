package com.hrbb.loan.pos.service;

import java.util.List;
import java.util.Map;

import com.hrbb.loan.pos.dao.entity.TPaymentApply;
import com.hrbb.loan.pos.dao.entity.TPaymentApplyApproval;

/**
 * 用款审批接口
 * 
 * @author jianQing
 * @version $Id: PaymentApplyApprovalService.java, v 0.1 2015年4月26日 上午10:27:56 jianQing Exp $
 */
public interface PaymentApplyApprovalService {
    /**
     * 查询用款申请列表
     * 
     * @param reqMap
     * @return
     */
    public List<Map<String, Object>> queryPaymentApplyCurr(Map<String, Object> reqMap);
    
    /**
     * 查询用款申请总数
     * 
     * @param reqMap
     * @return
     */
    public Long countPaymentApplyCurr(Map<String, Object> reqMap);
    
    /**
     * 已完成工作
     * 
     * @param reqMap
     * @return
     */
    public List<Map<String, Object>> queryPaymentApplyFinished(Map<String, Object> reqMap);
    
    /**
     * 已完成工作总数
     * 
     * @param reqMap
     * @return
     */
    public Long countPaymentApplyFinished(Map<String, Object> reqMap);
    
    /**
     * 
     * @param payApplyId
     * @param status
     * @return
     */
    public TPaymentApplyApproval selectOneByPayApplyIdAndBeforeStatus(Map<String, Object> map);
    
    /**
     * 挑选最新一笔审批意见
     * @param map
     * @return
     */
    public TPaymentApplyApproval selectApprOpinion(Map<String, Object> map,TPaymentApply paymentApply);
    
    /**
     * 
     * @param tp
     * @return
     */
    public int insert(TPaymentApplyApproval tp);
    
    /**
     * 
     * @param tp
     * @return
     */
    public int insertSelective(TPaymentApplyApproval tp);
    
    /**
     * 
     * @param tp
     * @return
     */
    public int updateByPrimaryKeySelective(TPaymentApplyApproval tp);

    /**
     * 
     * @param tp
     * @return
     */
    public int updateByPrimaryKey(TPaymentApplyApproval tp);

    /**
     * 
     * @param tp
     * @return
     */
    public int update(TPaymentApplyApproval tp);
    
    public int updateSelectiveMap(Map<String,Object> map);
    
    public int deleteByPayApplyId(String payApplyId);
    
}
