package com.hrbb.loan.pos.dao.entity;

import java.io.Serializable;

public class TAICAlterInfo implements Serializable{
    private Integer id;

    private String posCustId;

    private String orderNo;

    private String altDate;

    private String altItem;

    private String altBe;

    private String altAf;

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

    public String getAltDate() {
        return altDate;
    }

    public void setAltDate(String altDate) {
        this.altDate = altDate == null ? null : altDate.trim();
    }

    public String getAltItem() {
        return altItem;
    }

    public void setAltItem(String altItem) {
        this.altItem = altItem == null ? null : altItem.trim();
    }

    public String getAltBe() {
        return altBe;
    }

    public void setAltBe(String altBe) {
        this.altBe = altBe == null ? null : altBe.trim();
    }

    public String getAltAf() {
        return altAf;
    }

    public void setAltAf(String altAf) {
        this.altAf = altAf == null ? null : altAf.trim();
    }
}