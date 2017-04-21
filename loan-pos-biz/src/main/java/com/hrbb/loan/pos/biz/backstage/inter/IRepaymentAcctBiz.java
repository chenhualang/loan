package com.hrbb.loan.pos.biz.backstage.inter;

import java.util.Map;

/**
 * 还款试算
 * 
 * @author cjq
 * @version $Id: IPaybackCalcRAmt.java, v 0.1 2015年9月21日 下午2:41:45 cjq Exp $
 */
public interface IRepaymentAcctBiz {

    /**
     * 拖欠判断
     * 
     * @param loanAcNo
     * @return
     */
    Boolean checkOverDueForReceipt(String loanAcNo) throws Exception;
    
    /**
     * 还款试算
     * 
     * @param loanAcNo
     * @return
     */
    Map<String,Object> paybackCalc(String loanAcNo,String paybackAmt);
    
    /**
     * 还款
     * 
     * @param paybackApplyId
     * @return
     */
    Boolean repayment(String paybackApplyId);

    void updateRepaymentPlan(String receiptId);
    
    
    
}
