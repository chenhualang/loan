package com.hrbb.loan.pos.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TContractInfo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3777292556176457886L;

	private String contNo;

    private String cnContNo;
    
    private String  acctContNo;
    
    private String approveId;

    private String contKind;

    private String loanId;
 
    private String custId;

    private String custName;

    private String prodId;

    private String posCustName;

    private String prodName;

    private String loanForm;

    private BigDecimal apprTotalAmt;

    private BigDecimal contTotalAmt;

    private String termUnit;

    private String applyTerm;

    private String contTerm;

    private Date beginDate;

    private Date endDate;

    private String assuKind;

    private String inChannelKind;

    private String paybackMethod;

    private String retuSourRemark;

    private BigDecimal amt;

    private BigDecimal moreInteRate;

    private BigDecimal inteRate;

    private String reckDay;

    private String riskRemark;

    private String approveMoneyKind;

    private String apprOperId;

    private String apprBankId;

    private String apprOperLev;

    private Date signDate;

    private String initOperId;

    private String bankId;

    private String operId;

    private String acBankId;

    private String contStatus;

    private Date apprDate;

    private Date apprEndDate;

    private String lastChanPerson;

    private String lastChanBankId;

    private Date lastChanDate;

    private String agentBankId;

    private String agentOperId;

    private String retuDay;

    private String fundSour;

    private String posCustId;

    private BigDecimal creditLine;

    private String creditInterest;

    private String agreementStatus;

    private String channel;

    private String approveTerm;

    private String approveTermUnit;

    private String freezeReason;

    private String unFreezeReason;

    private String freezePerson;

    private String unfreezePerson;

    private Date freezeDate;

    private Date unfreezeDate;

    private String editReason;

    private String editPerson;

    private Date editDate;

    private String acceptAccount;

    private BigDecimal approveInterest;

    private String accountOpenBank;

    private String accountBranchBank;

    private String accountSubBranchBank;

    private String loanNum;
   
    //chenhualang add 日期处理
    private String beginDateStr;
    
    private String endDateStr;
    
    private String signDateStr;
    
    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo == null ? null : contNo.trim();
    }

    public String getCnContNo() {
        return cnContNo;
    }

    public void setCnContNo(String cnContNo) {
        this.cnContNo = cnContNo == null ? null : cnContNo.trim();
    }
    
    public String getAcctContNo() {
        return acctContNo;
    }

    public void setAcctContNo(String acctContNo) {
        this.acctContNo = acctContNo;
    }

    public String getApproveId() {
        return approveId;
    }

    public void setApproveId(String approveId) {
        this.approveId = approveId;
    }

    public String getContKind() {
        return contKind;
    }

    public void setContKind(String contKind) {
        this.contKind = contKind == null ? null : contKind.trim();
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId == null ? null : loanId.trim();
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId == null ? null : custId.trim();
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName == null ? null : custName.trim();
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId == null ? null : prodId.trim();
    }

    public String getPosCustName() {
        return posCustName;
    }

    public void setPosCustName(String posCustName) {
        this.posCustName = posCustName == null ? null : posCustName.trim();
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName == null ? null : prodName.trim();
    }

    public String getLoanForm() {
        return loanForm;
    }

    public void setLoanForm(String loanForm) {
        this.loanForm = loanForm == null ? null : loanForm.trim();
    }

    public BigDecimal getApprTotalAmt() {
        return apprTotalAmt;
    }

    public void setApprTotalAmt(BigDecimal apprTotalAmt) {
        this.apprTotalAmt = apprTotalAmt;
    }

    public BigDecimal getContTotalAmt() {
        return contTotalAmt;
    }

    public void setContTotalAmt(BigDecimal contTotalAmt) {
        this.contTotalAmt = contTotalAmt;
    }

    public String getTermUnit() {
        return termUnit;
    }

    public void setTermUnit(String termUnit) {
        this.termUnit = termUnit == null ? null : termUnit.trim();
    }

    public String getApplyTerm() {
        return applyTerm;
    }

    public void setApplyTerm(String applyTerm) {
        this.applyTerm = applyTerm == null ? null : applyTerm.trim();
    }

    public String getContTerm() {
        return contTerm;
    }

    public void setContTerm(String contTerm) {
        this.contTerm = contTerm == null ? null : contTerm.trim();
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getAssuKind() {
        return assuKind;
    }

    public void setAssuKind(String assuKind) {
        this.assuKind = assuKind == null ? null : assuKind.trim();
    }

    public String getInChannelKind() {
        return inChannelKind;
    }

    public void setInChannelKind(String inChannelKind) {
        this.inChannelKind = inChannelKind == null ? null : inChannelKind.trim();
    }

    public String getPaybackMethod() {
        return paybackMethod;
    }

    public void setPaybackMethod(String paybackMethod) {
        this.paybackMethod = paybackMethod == null ? null : paybackMethod.trim();
    }

    public String getRetuSourRemark() {
        return retuSourRemark;
    }

    public void setRetuSourRemark(String retuSourRemark) {
        this.retuSourRemark = retuSourRemark == null ? null : retuSourRemark.trim();
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public BigDecimal getMoreInteRate() {
        return moreInteRate;
    }

    public void setMoreInteRate(BigDecimal moreInteRate) {
        this.moreInteRate = moreInteRate;
    }

    public BigDecimal getInteRate() {
        return inteRate;
    }

    public void setInteRate(BigDecimal inteRate) {
        this.inteRate = inteRate;
    }

    public String getReckDay() {
        return reckDay;
    }

    public void setReckDay(String reckDay) {
        this.reckDay = reckDay == null ? null : reckDay.trim();
    }

    public String getRiskRemark() {
        return riskRemark;
    }

    public void setRiskRemark(String riskRemark) {
        this.riskRemark = riskRemark == null ? null : riskRemark.trim();
    }

    public String getApproveMoneyKind() {
        return approveMoneyKind;
    }

    public void setApproveMoneyKind(String approveMoneyKind) {
        this.approveMoneyKind = approveMoneyKind == null ? null : approveMoneyKind.trim();
    }

    public String getApprOperId() {
        return apprOperId;
    }

    public void setApprOperId(String apprOperId) {
        this.apprOperId = apprOperId == null ? null : apprOperId.trim();
    }

    public String getApprBankId() {
        return apprBankId;
    }

    public void setApprBankId(String apprBankId) {
        this.apprBankId = apprBankId == null ? null : apprBankId.trim();
    }

    public String getApprOperLev() {
        return apprOperLev;
    }

    public void setApprOperLev(String apprOperLev) {
        this.apprOperLev = apprOperLev == null ? null : apprOperLev.trim();
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public String getInitOperId() {
        return initOperId;
    }

    public void setInitOperId(String initOperId) {
        this.initOperId = initOperId == null ? null : initOperId.trim();
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId == null ? null : bankId.trim();
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId == null ? null : operId.trim();
    }

    public String getAcBankId() {
        return acBankId;
    }

    public void setAcBankId(String acBankId) {
        this.acBankId = acBankId == null ? null : acBankId.trim();
    }

    public String getContStatus() {
        return contStatus;
    }

    public void setContStatus(String contStatus) {
        this.contStatus = contStatus == null ? null : contStatus.trim();
    }

    public Date getApprDate() {
        return apprDate;
    }

    public void setApprDate(Date apprDate) {
        this.apprDate = apprDate;
    }

    public Date getApprEndDate() {
        return apprEndDate;
    }

    public void setApprEndDate(Date apprEndDate) {
        this.apprEndDate = apprEndDate;
    }

    public String getLastChanPerson() {
        return lastChanPerson;
    }

    public void setLastChanPerson(String lastChanPerson) {
        this.lastChanPerson = lastChanPerson == null ? null : lastChanPerson.trim();
    }

    public String getLastChanBankId() {
        return lastChanBankId;
    }

    public void setLastChanBankId(String lastChanBankId) {
        this.lastChanBankId = lastChanBankId == null ? null : lastChanBankId.trim();
    }

    public Date getLastChanDate() {
        return lastChanDate;
    }

    public void setLastChanDate(Date lastChanDate) {
        this.lastChanDate = lastChanDate;
    }

    public String getAgentBankId() {
        return agentBankId;
    }

    public void setAgentBankId(String agentBankId) {
        this.agentBankId = agentBankId == null ? null : agentBankId.trim();
    }

    public String getAgentOperId() {
        return agentOperId;
    }

    public void setAgentOperId(String agentOperId) {
        this.agentOperId = agentOperId == null ? null : agentOperId.trim();
    }

    public String getRetuDay() {
        return retuDay;
    }

    public void setRetuDay(String retuDay) {
        this.retuDay = retuDay == null ? null : retuDay.trim();
    }

    public String getFundSour() {
        return fundSour;
    }

    public void setFundSour(String fundSour) {
        this.fundSour = fundSour == null ? null : fundSour.trim();
    }

    public String getPosCustId() {
        return posCustId;
    }

    public void setPosCustId(String posCustId) {
        this.posCustId = posCustId == null ? null : posCustId.trim();
    }

    public BigDecimal getCreditLine() {
        return creditLine;
    }

    public void setCreditLine(BigDecimal creditLine) {
        this.creditLine = creditLine;
    }

    public String getCreditInterest() {
        return creditInterest;
    }

    public void setCreditInterest(String creditInterest) {
        this.creditInterest = creditInterest == null ? null : creditInterest.trim();
    }

    public String getAgreementStatus() {
        return agreementStatus;
    }

    public void setAgreementStatus(String agreementStatus) {
        this.agreementStatus = agreementStatus == null ? null : agreementStatus.trim();
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public String getApproveTerm() {
        return approveTerm;
    }

    public void setApproveTerm(String approveTerm) {
        this.approveTerm = approveTerm == null ? null : approveTerm.trim();
    }

    public String getApproveTermUnit() {
        return approveTermUnit;
    }

    public void setApproveTermUnit(String approveTermUnit) {
        this.approveTermUnit = approveTermUnit == null ? null : approveTermUnit.trim();
    }

    public String getFreezeReason() {
        return freezeReason;
    }

    public void setFreezeReason(String freezeReason) {
        this.freezeReason = freezeReason == null ? null : freezeReason.trim();
    }

    public String getUnFreezeReason() {
        return unFreezeReason;
    }

    public void setUnFreezeReason(String unFreezeReason) {
        this.unFreezeReason = unFreezeReason == null ? null : unFreezeReason.trim();
    }

    public String getFreezePerson() {
        return freezePerson;
    }

    public void setFreezePerson(String freezePerson) {
        this.freezePerson = freezePerson == null ? null : freezePerson.trim();
    }

    public String getUnfreezePerson() {
        return unfreezePerson;
    }

    public void setUnfreezePerson(String unfreezePerson) {
        this.unfreezePerson = unfreezePerson == null ? null : unfreezePerson.trim();
    }

    public Date getFreezeDate() {
        return freezeDate;
    }

    public void setFreezeDate(Date freezeDate) {
        this.freezeDate = freezeDate;
    }

    public Date getUnfreezeDate() {
        return unfreezeDate;
    }

    public void setUnfreezeDate(Date unfreezeDate) {
        this.unfreezeDate = unfreezeDate;
    }

    public String getEditReason() {
        return editReason;
    }

    public void setEditReason(String editReason) {
        this.editReason = editReason == null ? null : editReason.trim();
    }

    public String getEditPerson() {
        return editPerson;
    }

    public void setEditPerson(String editPerson) {
        this.editPerson = editPerson == null ? null : editPerson.trim();
    }

    public Date getEditDate() {
        return editDate;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    public String getAcceptAccount() {
        return acceptAccount;
    }

    public void setAcceptAccount(String acceptAccount) {
        this.acceptAccount = acceptAccount == null ? null : acceptAccount.trim();
    }

    public BigDecimal getApproveInterest() {
        return approveInterest;
    }

    public void setApproveInterest(BigDecimal approveInterest) {
        this.approveInterest = approveInterest;
    }

    public String getAccountOpenBank() {
        return accountOpenBank;
    }

    public void setAccountOpenBank(String accountOpenBank) {
        this.accountOpenBank = accountOpenBank == null ? null : accountOpenBank.trim();
    }

    public String getAccountBranchBank() {
        return accountBranchBank;
    }

    public void setAccountBranchBank(String accountBranchBank) {
        this.accountBranchBank = accountBranchBank == null ? null : accountBranchBank.trim();
    }

    public String getAccountSubBranchBank() {
        return accountSubBranchBank;
    }

    public void setAccountSubBranchBank(String accountSubBranchBank) {
        this.accountSubBranchBank = accountSubBranchBank == null ? null : accountSubBranchBank.trim();
    }

    public String getBeginDateStr() {
        return beginDateStr;
    }

    public void setBeginDateStr(String beginDateStr) {
        this.beginDateStr = beginDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public String getSignDateStr() {
        return signDateStr;
    }

    public void setSignDateStr(String signDateStr) {
        this.signDateStr = signDateStr;
    }
    
    public String getLoanNum() {
        return loanNum;
    }

    public void setLoanNum(String loanNum) {
        this.loanNum = loanNum;
    }

}