package com.hrbb.loan.pos.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class TAICOrderlistInfo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3126494700135691751L;

	private Integer id;

    private String queryUid;

    private String orderNo;

    private String posCustId;

    private String posCustName;

    private String keyType;

    private String status;

    private Date queryTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQueryUid() {
        return queryUid;
    }

    public void setQueryUid(String queryUid) {
        this.queryUid = queryUid == null ? null : queryUid.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getPosCustId() {
        return posCustId;
    }

    public void setPosCustId(String posCustId) {
        this.posCustId = posCustId == null ? null : posCustId.trim();
    }

    public String getPosCustName() {
        return posCustName;
    }

    public void setPosCustName(String posCustName) {
        this.posCustName = posCustName == null ? null : posCustName.trim();
    }

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType == null ? null : keyType.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(Date queryTime) {
        this.queryTime = queryTime;
    }
}