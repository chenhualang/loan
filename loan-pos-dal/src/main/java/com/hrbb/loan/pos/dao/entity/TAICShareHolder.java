package com.hrbb.loan.pos.dao.entity;

import java.io.Serializable;

public class TAICShareHolder implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5384861201567919452L;

	private Integer id;

    private String posCustId;

    private String orderNo;

    private String shaName;

    private String subConAm;

    private String regCapCur;

    private String conDate;

    private String cdId;

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

    public String getShaName() {
        return shaName;
    }

    public void setShaName(String shaName) {
        this.shaName = shaName == null ? null : shaName.trim();
    }

    public String getSubConAm() {
        return subConAm;
    }

    public void setSubConAm(String subConAm) {
        this.subConAm = subConAm == null ? null : subConAm.trim();
    }

    public String getRegCapCur() {
        return regCapCur;
    }

    public void setRegCapCur(String regCapCur) {
        this.regCapCur = regCapCur == null ? null : regCapCur.trim();
    }

    public String getConDate() {
        return conDate;
    }

    public void setConDate(String conDate) {
        this.conDate = conDate == null ? null : conDate.trim();
    }

    public String getCdId() {
        return cdId;
    }

    public void setCdId(String cdId) {
        this.cdId = cdId == null ? null : cdId.trim();
    }
}