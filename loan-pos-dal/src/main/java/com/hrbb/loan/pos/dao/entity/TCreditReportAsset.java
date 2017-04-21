package com.hrbb.loan.pos.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class TCreditReportAsset implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -874963517088863056L;

	private String serialNo;

    private String reportNo;

    private String orgName;

    private String receiveDate;

    private BigDecimal receiveAmt;

    private String latestRepayDate;

    private BigDecimal balance;

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

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate == null ? null : receiveDate.trim();
    }

    public BigDecimal getReceiveAmt() {
        return receiveAmt;
    }

    public void setReceiveAmt(BigDecimal receiveAmt) {
        this.receiveAmt = receiveAmt;
    }

    public String getLatestRepayDate() {
        return latestRepayDate;
    }

    public void setLatestRepayDate(String latestRepayDate) {
        this.latestRepayDate = latestRepayDate == null ? null : latestRepayDate.trim();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}