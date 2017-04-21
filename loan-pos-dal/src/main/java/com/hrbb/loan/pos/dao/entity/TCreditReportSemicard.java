package com.hrbb.loan.pos.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class TCreditReportSemicard implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7141226773957375059L;

	private String serialNo;

    private BigDecimal shareLimit;

    private BigDecimal usedLimit;

    private BigDecimal usedAvgAmt6m;

    private BigDecimal usedAmtMax;

    private String scheduledDate;

    private BigDecimal actualAmt;

    private String recentPayDate;

    private BigDecimal overdueAmt;

    private String reportNo;

    private String bizType;

    private String account;

    private String note;

    private String financeOrg;

    private String currency;

    private String openDate;

    private BigDecimal limitAmt;

    private String guarantyType;

    private String state;

    private BigDecimal badBalance;

    private String stateEndDate;

    private String beginMonth;

    private String endMonth;

    private String latest24State;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo == null ? null : serialNo.trim();
    }

    public BigDecimal getShareLimit() {
        return shareLimit;
    }

    public void setShareLimit(BigDecimal shareLimit) {
        this.shareLimit = shareLimit;
    }

    public BigDecimal getUsedLimit() {
        return usedLimit;
    }

    public void setUsedLimit(BigDecimal usedLimit) {
        this.usedLimit = usedLimit;
    }

    public BigDecimal getUsedAvgAmt6m() {
        return usedAvgAmt6m;
    }

    public void setUsedAvgAmt6m(BigDecimal usedAvgAmt6m) {
        this.usedAvgAmt6m = usedAvgAmt6m;
    }

    public BigDecimal getUsedAmtMax() {
        return usedAmtMax;
    }

    public void setUsedAmtMax(BigDecimal usedAmtMax) {
        this.usedAmtMax = usedAmtMax;
    }

    public String getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(String scheduledDate) {
        this.scheduledDate = scheduledDate == null ? null : scheduledDate.trim();
    }

    public BigDecimal getActualAmt() {
        return actualAmt;
    }

    public void setActualAmt(BigDecimal actualAmt) {
        this.actualAmt = actualAmt;
    }

    public String getRecentPayDate() {
        return recentPayDate;
    }

    public void setRecentPayDate(String recentPayDate) {
        this.recentPayDate = recentPayDate == null ? null : recentPayDate.trim();
    }

    public BigDecimal getOverdueAmt() {
        return overdueAmt;
    }

    public void setOverdueAmt(BigDecimal overdueAmt) {
        this.overdueAmt = overdueAmt;
    }

    public String getReportNo() {
        return reportNo;
    }

    public void setReportNo(String reportNo) {
        this.reportNo = reportNo == null ? null : reportNo.trim();
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType == null ? null : bizType.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getFinanceOrg() {
        return financeOrg;
    }

    public void setFinanceOrg(String financeOrg) {
        this.financeOrg = financeOrg == null ? null : financeOrg.trim();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate == null ? null : openDate.trim();
    }

    public BigDecimal getLimitAmt() {
        return limitAmt;
    }

    public void setLimitAmt(BigDecimal limitAmt) {
        this.limitAmt = limitAmt;
    }

    public String getGuarantyType() {
        return guarantyType;
    }

    public void setGuarantyType(String guarantyType) {
        this.guarantyType = guarantyType == null ? null : guarantyType.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public BigDecimal getBadBalance() {
        return badBalance;
    }

    public void setBadBalance(BigDecimal badBalance) {
        this.badBalance = badBalance;
    }

    public String getStateEndDate() {
        return stateEndDate;
    }

    public void setStateEndDate(String stateEndDate) {
        this.stateEndDate = stateEndDate == null ? null : stateEndDate.trim();
    }

    public String getBeginMonth() {
        return beginMonth;
    }

    public void setBeginMonth(String beginMonth) {
        this.beginMonth = beginMonth == null ? null : beginMonth.trim();
    }

    public String getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth == null ? null : endMonth.trim();
    }

    public String getLatest24State() {
        return latest24State;
    }

    public void setLatest24State(String latest24State) {
        this.latest24State = latest24State == null ? null : latest24State.trim();
    }
}