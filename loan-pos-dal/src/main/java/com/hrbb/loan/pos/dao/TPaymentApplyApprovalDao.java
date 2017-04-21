package com.hrbb.loan.pos.dao;

import java.util.List;
import java.util.Map;

import com.hrbb.loan.pos.dao.entity.TPaymentApplyApproval;

public interface TPaymentApplyApprovalDao {
    public int insert(TPaymentApplyApproval record) ;

    public int insertSelective(TPaymentApplyApproval record) ;
    
    public TPaymentApplyApproval selectOneByPayApplyIdAndBeforeStatus(Map<String, Object> map);
    
    public int updateSelectiveMap(Map<String,Object> map);
    
    /**
     * 当前工作
     * 
     * @param reqMap
     * @return
     */
    public List<Map<String,Object>> queryPaymentApplyCurr(Map<String,Object> reqMap);
    
    /**
     * 查询当前工作总条数
     * 
     * @param reqMap
     * @return
     */
    public Long countPaymentApplyCurr(Map<String,Object> reqMap);
    /**
     * 已完成工作
     * 
     * @param reqMap
     * @return
     */
    public List<Map<String, Object>> queryPaymentApplyFinished(Map<String, Object> reqMap); 
    
    /**
     * 查询已完成工作总条数
     * 
     * @param reqMap
     * @return
     */
    public Long countPaymentApplyFinished(Map<String, Object> reqMap);
    
    public TPaymentApplyApproval selectApprOpinion(Map<String, Object> map);
    
    /**
     * 
     * @param payApplyId
     * @return
     */
    public int deleteByPaymentApplyId(String payApplyId);

}
