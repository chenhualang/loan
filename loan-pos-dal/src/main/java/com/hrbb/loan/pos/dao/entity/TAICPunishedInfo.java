package com.hrbb.loan.pos.dao.entity;

import java.io.Serializable;

public class TAICPunishedInfo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2902061982023003902L;

	private Integer id;

    private String posCustId;

    private String orderNo;

    private String caseCode;

    private String iNameClean;

    private String cardNumClean;

    private String sexyClean;

    private String areaNameClean;

    private String ysFzd;

    private String courtName;

    private String regDateClean;

    private String caseState;

    private String execMoney;

    private String focusNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPosCustId() {
        return posCustId;
    }

    public void setPosCustId(String posCustId) {
        this.posCustId = posCustId == null ? null : posCustId.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public String getiNameClean() {
        return iNameClean;
    }

    public void setiNameClean(String iNameClean) {
        this.iNameClean = iNameClean == null ? null : iNameClean.trim();
    }

    public String getCardNumClean() {
        return cardNumClean;
    }

    public void setCardNumClean(String cardNumClean) {
        this.cardNumClean = cardNumClean == null ? null : cardNumClean.trim();
    }

    public String getSexyClean() {
        return sexyClean;
    }

    public void setSexyClean(String sexyClean) {
        this.sexyClean = sexyClean == null ? null : sexyClean.trim();
    }

    public String getAreaNameClean() {
        return areaNameClean;
    }

    public void setAreaNameClean(String areaNameClean) {
        this.areaNameClean = areaNameClean == null ? null : areaNameClean.trim();
    }

    public String getYsFzd() {
        return ysFzd;
    }

    public void setYsFzd(String ysFzd) {
        this.ysFzd = ysFzd == null ? null : ysFzd.trim();
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName == null ? null : courtName.trim();
    }

    public String getRegDateClean() {
        return regDateClean;
    }

    public void setRegDateClean(String regDateClean) {
        this.regDateClean = regDateClean == null ? null : regDateClean.trim();
    }

    public String getCaseState() {
        return caseState;
    }

    public void setCaseState(String caseState) {
        this.caseState = caseState == null ? null : caseState.trim();
    }

    public String getExecMoney() {
        return execMoney;
    }

    public void setExecMoney(String execMoney) {
        this.execMoney = execMoney == null ? null : execMoney.trim();
    }

    public String getFocusNumber() {
        return focusNumber;
    }

    public void setFocusNumber(String focusNumber) {
        this.focusNumber = focusNumber == null ? null : focusNumber.trim();
    }
}