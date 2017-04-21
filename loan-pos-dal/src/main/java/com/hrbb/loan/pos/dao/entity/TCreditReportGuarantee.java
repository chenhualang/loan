package com.hrbb.loan.pos.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class TCreditReportGuarantee implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3090915096601981095L;

	private String serialNo;

    private String reportNo;

    private String organName;

    private BigDecimal contractAmt;

    private String beginDate;

    private String endDate;

    private BigDecimal guaranteeAmt;

    private BigDecimal guaranteeBalance;

    private String class5State;

    private String billingDate;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo == null ? null : serialNo.trim();
    }

    public String getReportNo() {
        return reportNo;
    }

    public void setReportNo(String reportNo) {
        this.reportNo = reportNo == null ? null : reportNo.trim();
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName == null ? null : organName.trim();
    }

    public BigDecimal getContractAmt() {
        return contractAmt;
    }

    public void setContractAmt(BigDecimal contractAmt) {
        this.contractAmt = contractAmt;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate == null ? null : beginDate.trim();
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate == null ? null : endDate.trim();
    }

    public BigDecimal getGuaranteeAmt() {
        return guaranteeAmt;
    }

    public void setGuaranteeAmt(BigDecimal guaranteeAmt) {
        this.guaranteeAmt = guaranteeAmt;
    }

    public BigDecimal getGuaranteeBalance() {
        return guaranteeBalance;
    }

    public void setGuaranteeBalance(BigDecimal guaranteeBalance) {
        this.guaranteeBalance = guaranteeBalance;
    }

    public String getClass5State() {
        return class5State;
    }

    public void setClass5State(String class5State) {
        this.class5State = class5State == null ? null : class5State.trim();
    }

    public String getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(String billingDate) {
        this.billingDate = billingDate == null ? null : billingDate.trim();
    }
}