package com.hrbb.loan.pos.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class TCreditReportOverdue implements Serializable{
    private String reportNo;

    private Integer count;

    private Integer months;

    private BigDecimal nplHighestAmt;

    private Integer maxDuration;

    private Integer count2;

    private Integer months2;

    private BigDecimal nplHighestAmt2;

    private Integer maxDuration2;

    private Integer count3;

    private Integer months3;

    private BigDecimal nplHighestAmt3;

    private Integer maxDuration3;

    public String getReportNo() {
        return reportNo;
    }

    public void setReportNo(String reportNo) {
        this.reportNo = reportNo == null ? null : reportNo.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getMonths() {
        return months;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }

    public BigDecimal getNplHighestAmt() {
        return nplHighestAmt;
    }

    public void setNplHighestAmt(BigDecimal nplHighestAmt) {
        this.nplHighestAmt = nplHighestAmt;
    }

    public Integer getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(Integer maxDuration) {
        this.maxDuration = maxDuration;
    }

    public Integer getCount2() {
        return count2;
    }

    public void setCount2(Integer count2) {
        this.count2 = count2;
    }

    public Integer getMonths2() {
        return months2;
    }

    public void setMonths2(Integer months2) {
        this.months2 = months2;
    }

    public BigDecimal getNplHighestAmt2() {
        return nplHighestAmt2;
    }

    public void setNplHighestAmt2(BigDecimal nplHighestAmt2) {
        this.nplHighestAmt2 = nplHighestAmt2;
    }

    public Integer getMaxDuration2() {
        return maxDuration2;
    }

    public void setMaxDuration2(Integer maxDuration2) {
        this.maxDuration2 = maxDuration2;
    }

    public Integer getCount3() {
        return count3;
    }

    public void setCount3(Integer count3) {
        this.count3 = count3;
    }

    public Integer getMonths3() {
        return months3;
    }

    public void setMonths3(Integer months3) {
        this.months3 = months3;
    }

    public BigDecimal getNplHighestAmt3() {
        return nplHighestAmt3;
    }

    public void setNplHighestAmt3(BigDecimal nplHighestAmt3) {
        this.nplHighestAmt3 = nplHighestAmt3;
    }

    public Integer getMaxDuration3() {
        return maxDuration3;
    }

    public void setMaxDuration3(Integer maxDuration3) {
        this.maxDuration3 = maxDuration3;
    }
}