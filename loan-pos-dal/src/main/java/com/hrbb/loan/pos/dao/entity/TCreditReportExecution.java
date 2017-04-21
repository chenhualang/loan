package com.hrbb.loan.pos.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class TCreditReportExecution implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -645325721645540849L;

	private String serialNo;

    private String reportNo;

    private String court;

    private String caseReason;

    private String registerDate;

    private String closedType;

    private String caseState;

    private String closedDate;

    private String enforceObj;

    private BigDecimal enforceAmt;

    private String executedObj;

    private BigDecimal executedAmt;

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

    public String getCourt() {
        return court;
    }

    public void setCourt(String court) {
        this.court = court == null ? null : court.trim();
    }

    public String getCaseReason() {
        return caseReason;
    }

    public void setCaseReason(String caseReason) {
        this.caseReason = caseReason == null ? null : caseReason.trim();
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate == null ? null : registerDate.trim();
    }

    public String getClosedType() {
        return closedType;
    }

    public void setClosedType(String closedType) {
        this.closedType = closedType == null ? null : closedType.trim();
    }

    public String getCaseState() {
        return caseState;
    }

    public void setCaseState(String caseState) {
        this.caseState = caseState == null ? null : caseState.trim();
    }

    public String getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(String closedDate) {
        this.closedDate = closedDate == null ? null : closedDate.trim();
    }

    public String getEnforceObj() {
        return enforceObj;
    }

    public void setEnforceObj(String enforceObj) {
        this.enforceObj = enforceObj == null ? null : enforceObj.trim();
    }

    public BigDecimal getEnforceAmt() {
        return enforceAmt;
    }

    public void setEnforceAmt(BigDecimal enforceAmt) {
        this.enforceAmt = enforceAmt;
    }

    public String getExecutedObj() {
        return executedObj;
    }

    public void setExecutedObj(String executedObj) {
        this.executedObj = executedObj == null ? null : executedObj.trim();
    }

    public BigDecimal getExecutedAmt() {
        return executedAmt;
    }

    public void setExecutedAmt(BigDecimal executedAmt) {
        this.executedAmt = executedAmt;
    }
}