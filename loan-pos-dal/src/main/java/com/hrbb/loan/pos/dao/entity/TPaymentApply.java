package com.hrbb.loan.pos.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TPaymentApply implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -845531997484414644L;

	private String payApplyId;

    private String contNo;

    private String custId;

    private String posCustId;

    private BigDecimal payApplyAmt;

    private String payApplyTerm;
    
    private BigDecimal payApplyInterest;
    
    private String payChannel;

    private String returnType;

    private String accNo;

    private Date expectedDate;
    
    private Date expectedEndDate;

    private String apply_memo;

    private Date applyDate;

    private String status;

    private String merchantNo;

    private String merchantDesp;

    private Date createDate;

    private Date modifiedDate;

    private String custName;
    private String paperId;
    private String channelPosCustId;
    
    private String operId;

    private String receiptFlag;
    
    /**
	 * Getter method for property <tt>channelPosCustId</tt>.
	 * 
	 * @return property value of channelPosCustId
	 */
	public String getChannelPosCustId() {
		return channelPosCustId;
	}

	/**
	 * Setter method for property <tt>channelPosCustId</tt>.
	 * 
	 * @param channelPosCustId value to be assigned to property channelPosCustId
	 */
	public void setChannelPosCustId(String channelPosCustId) {
		this.channelPosCustId = channelPosCustId;
	}

	/**
	 * Getter method for property <tt>custName</tt>.
	 * 
	 * @return property value of custName
	 */
	public String getCustName() {
		return custName;
	}

	/**
	 * Setter method for property <tt>custName</tt>.
	 * 
	 * @param custName value to be assigned to property custName
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}

	/**
	 * Getter method for property <tt>paperId</tt>.
	 * 
	 * @return property value of paperId
	 */
	public String getPaperId() {
		return paperId;
	}

	/**
	 * Setter method for property <tt>paperId</tt>.
	 * 
	 * @param paperId value to be assigned to property paperId
	 */
	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	public String getPayApplyId() {
        return payApplyId;
    }

    public void setPayApplyId(String payApplyId) {
        this.payApplyId = payApplyId == null ? null : payApplyId.trim();
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo == null ? null : contNo.trim();
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId == null ? null : custId.trim();
    }

    public String getPosCustId() {
        return posCustId;
    }

    public void setPosCustId(String posCustId) {
        this.posCustId = posCustId == null ? null : posCustId.trim();
    }

    public BigDecimal getPayApplyAmt() {
        return payApplyAmt;
    }

    public void setPayApplyAmt(BigDecimal payApplyAmt) {
        this.payApplyAmt = payApplyAmt;
    }

    public String getPayApplyTerm() {
        return payApplyTerm;
    }

    public void setPayApplyTerm(String payApplyTerm) {
        this.payApplyTerm = payApplyTerm == null ? null : payApplyTerm.trim();
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType == null ? null : returnType.trim();
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo == null ? null : accNo.trim();
    }

    public Date getExpectedDate() {
        return expectedDate;
    }

    public Date getExpectedEndDate() {
        return expectedEndDate;
    }

    public void setExpectedEndDate(Date expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }

    public void setExpectedDate(Date expectedDate) {
        this.expectedDate = expectedDate;
    }

    public String getApply_memo() {
        return apply_memo;
    }

    public void setApply_memo(String apply_memo) {
        this.apply_memo = apply_memo == null ? null : apply_memo.trim();
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo == null ? null : merchantNo.trim();
    }

    public String getMerchantDesp() {
        return merchantDesp;
    }

    public void setMerchantDesp(String merchantDesp) {
        this.merchantDesp = merchantDesp == null ? null : merchantDesp.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    
    public BigDecimal getPayApplyInterest() {
        return payApplyInterest;
    }
    
    public void setPayApplyInterest(BigDecimal payApplyInterest) {
        this.payApplyInterest = payApplyInterest;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }
    
    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }
    
    public String getReceiptFlag() {
        return receiptFlag;
    }

    public void setReceiptFlag(String receiptFlag) {
        this.receiptFlag = receiptFlag;
    }


}