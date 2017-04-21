package com.hrbb.loan.pos.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TPaybackApplyInfo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5432244235241046885L;

	private String paybackApplyId;

    private String receiptId;

    private BigDecimal loanBalance;

    private String paybackWay;

    private String loanPaybackWay;

    private Date expectPaybackDate;

    private String returnPrincipalType;

    private BigDecimal paybackAmount;

    private BigDecimal paybackInterest;
    
    private BigDecimal paybackPenalty;
    
    private String term;

    private BigDecimal paybackTotalAmount;

    private String advancedPaybackType;

    private String paybackStatus;

    private String operatePerson;

    private Date paybackApplyDate;
    
    private String custName;
    
    private String expectPaybackDateStr;
    
    //chenhualang add
    private String type;
    
    private String accNo;
    
    private String merchantNo;
    
    private String contNo;
    
    private String custId;
    
    private String paybackRunningRecordId;
    
    private String prepaymentId;
    
    private String stdrpno;
    
    private Date createTime;
    
    public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * Getter method for property <tt>stdrpno</tt>.
	 * 
	 * @return property value of stdrpno
	 */
	public String getStdrpno() {
		return stdrpno;
	}

	/**
	 * Setter method for property <tt>stdrpno</tt>.
	 * 
	 * @param stdrpno value to be assigned to property stdrpno
	 */
	public void setStdrpno(String stdrpno) {
		this.stdrpno = stdrpno;
	}

	public String getPrepaymentId() {
		return prepaymentId;
	}

	public void setPrepaymentId(String prepaymentId) {
		this.prepaymentId = prepaymentId;
	}

	public String getPaybackApplyId() {
        return paybackApplyId==null?"":paybackApplyId.trim();
    }

    public void setPaybackApplyId(String paybackApplyId) {
        this.paybackApplyId = paybackApplyId == null ? null : paybackApplyId.trim();
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId == null ? null : receiptId.trim();
    }

    public BigDecimal getLoanBalance() {
        return loanBalance;
    }

    public void setLoanBalance(BigDecimal loanBalance) {
        this.loanBalance = loanBalance;
    }

    public String getPaybackWay() {
        return paybackWay;
    }

    public void setPaybackWay(String paybackWay) {
        this.paybackWay = paybackWay == null ? null : paybackWay.trim();
    }

    public String getLoanPaybackWay() {
        return loanPaybackWay;
    }

    public void setLoanPaybackWay(String loanPaybackWay) {
        this.loanPaybackWay = loanPaybackWay == null ? null : loanPaybackWay.trim();
    }

    public Date getExpectPaybackDate() {
        return expectPaybackDate;
    }

    public void setExpectPaybackDate(Date expectPaybackDate) {
        this.expectPaybackDate = expectPaybackDate;
    }

    public String getReturnPrincipalType() {
        return returnPrincipalType;
    }

    public void setReturnPrincipalType(String returnPrincipalType) {
        this.returnPrincipalType = returnPrincipalType == null ? null : returnPrincipalType.trim();
    }

    public BigDecimal getPaybackAmount() {
        return paybackAmount;
    }

    public void setPaybackAmount(BigDecimal paybackAmount) {
        this.paybackAmount = paybackAmount;
    }

    public BigDecimal getPaybackInterest() {
        return paybackInterest;
    }

    public void setPaybackInterest(BigDecimal paybackInterest) {
        this.paybackInterest = paybackInterest;
    }

    public BigDecimal getPaybackTotalAmount() {
        return paybackTotalAmount;
    }

    public void setPaybackTotalAmount(BigDecimal paybackTotalAmount) {
        this.paybackTotalAmount = paybackTotalAmount;
    }

    public String getAdvancedPaybackType() {
        return advancedPaybackType;
    }

    public void setAdvancedPaybackType(String advancedPaybackType) {
        this.advancedPaybackType = advancedPaybackType == null ? null : advancedPaybackType.trim();
    }

 

    public String getOperatePerson() {
        return operatePerson;
    }

    public void setOperatePerson(String operatePerson) {
        this.operatePerson = operatePerson == null ? null : operatePerson.trim();
    }

    public Date getPaybackApplyDate() {
        return paybackApplyDate;
    }

    public void setPaybackApplyDate(Date paybackApplyDate) {
        this.paybackApplyDate = paybackApplyDate;
    }

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getExpectPaybackDateStr() {
		return expectPaybackDateStr;
	}

	public void setExpectPaybackDateStr(String expectPaybackDateStr) {
		this.expectPaybackDateStr = expectPaybackDateStr;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getContNo() {
		return contNo;
	}

	public void setContNo(String contNo) {
		this.contNo = contNo;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getPaybackStatus() {
		return paybackStatus;
	}

	public void setPaybackStatus(String statusInit) {
		this.paybackStatus = statusInit;
	}

	public String getPaybackRunningRecordId() {
		return paybackRunningRecordId;
	}

	public void setPaybackRunningRecordId(String paybackRunningRecordId) {
		this.paybackRunningRecordId = paybackRunningRecordId;
	}

    public BigDecimal getPaybackPenalty() {
        return paybackPenalty;
    }

    public void setPaybackPenalty(BigDecimal paybackPenalty) {
        this.paybackPenalty = paybackPenalty;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}