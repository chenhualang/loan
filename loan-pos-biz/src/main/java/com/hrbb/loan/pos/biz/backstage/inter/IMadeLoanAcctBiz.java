package com.hrbb.loan.pos.biz.backstage.inter;

import java.util.Map;

import com.hrbb.loan.pos.dao.entity.TContractManagement;
import com.hrbb.loan.pos.dao.entity.TCustomer;
import com.hrbb.loan.pos.dao.entity.TReceiptInfo;

/**
 * 放款对接核算接口
 * 
 * @author cjq
 * @version $Id: IMadeLoanAcctBiz.java, v 0.1 2015年5月20日 下午1:09:16 cjq Exp $
 */
public interface IMadeLoanAcctBiz {

    /**
     * 放款第一步：添加合同
     * 
     * @param contNo
     * @param contract
     * @param customerInfo
     * @return
     */
    boolean addContractInfo(String contNo, TContractManagement contract, TCustomer customerInfo);

    /**
     * 放款第二步：添加借据
     * 
     * @param contract
     * @param customer
     * @param receipt
     * @return
     */
    boolean addCreditDueBill(TContractManagement contract, TCustomer customer, TReceiptInfo receipt);

    /**
     * 批量放款
     * 
     * @param contract
     * @param receipt
     * @return
     */
    boolean batchPayout(TContractManagement contract,TReceiptInfo receipt);

    /**
     * 放款
     * 
     * @param paymentApply
     * @param receipt
     * @return
     */
    Map<String, Object> madeLoan(TReceiptInfo receipt);

    /**
     * 发送放款指令
     * 
     * @param paymentApply
     * @param receipt
     */
    Map<String,Object> sendMadeLoanCommend(TReceiptInfo receipt);

}
