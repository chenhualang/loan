package com.hrbb.loan.pos.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TPosCustInfo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7993822902833045088L;

	private String posCustId;

    private String stdmerno;
    
    private String custId;

    private String posCustName;

    private String busiDivision;

    private String posCustKind;

    private String operName;

    private Date operDate;
    
    private String posCustBusiScope;
    private String posCustAddress;
    private String operAddrCode;
    private String industryTypeId;
    private String industryTypeId2;
    private String regiCode;
    private BigDecimal regCapital;
    private String registDate;
    private String isPosInstall;
    
    /**
	 * Getter method for property <tt>isPosInstall</tt>.
	 * 
	 * @return property value of isPosInstall
	 */
	public String getIsPosInstall() {
		return isPosInstall;
	}

	/**
	 * Setter method for property <tt>isPosInstall</tt>.
	 * 
	 * @param isPosInstall value to be assigned to property isPosInstall
	 */
	public void setIsPosInstall(String isPosInstall) {
		this.isPosInstall = isPosInstall;
	}

	/**
	 * Getter method for property <tt>registDate</tt>.
	 * 
	 * @return property value of registDate
	 */
	public String getRegistDate() {
		return registDate;
	}

	/**
	 * Setter method for property <tt>registDate</tt>.
	 * 
	 * @param registDate value to be assigned to property registDate
	 */
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}

	public BigDecimal getRegCapital() {
		return regCapital;
	}

	public void setRegCapital(BigDecimal regCapital) {
		this.regCapital = regCapital;
	}

	/**
	 * Getter method for property <tt>posCustBusiScope</tt>.
	 * 
	 * @return property value of posCustBusiScope
	 */
	public String getPosCustBusiScope() {
		return posCustBusiScope;
	}

	/**
	 * Setter method for property <tt>posCustBusiScope</tt>.
	 * 
	 * @param posCustBusiScope value to be assigned to property posCustBusiScope
	 */
	public void setPosCustBusiScope(String posCustBusiScope) {
		this.posCustBusiScope = posCustBusiScope;
	}

	/**
	 * Getter method for property <tt>posCustAddress</tt>.
	 * 
	 * @return property value of posCustAddress
	 */
	public String getPosCustAddress() {
		return posCustAddress;
	}

	/**
	 * Setter method for property <tt>posCustAddress</tt>.
	 * 
	 * @param posCustAddress value to be assigned to property posCustAddress
	 */
	public void setPosCustAddress(String posCustAddress) {
		this.posCustAddress = posCustAddress;
	}

	/**
	 * Getter method for property <tt>operAddrCode</tt>.
	 * 
	 * @return property value of operAddrCode
	 */
	public String getOperAddrCode() {
		return operAddrCode;
	}

	/**
	 * Setter method for property <tt>operAddrCode</tt>.
	 * 
	 * @param operAddrCode value to be assigned to property operAddrCode
	 */
	public void setOperAddrCode(String operAddrCode) {
		this.operAddrCode = operAddrCode;
	}

	/**
	 * Getter method for property <tt>industryTypeId</tt>.
	 * 
	 * @return property value of industryTypeId
	 */
	public String getIndustryTypeId() {
		return industryTypeId;
	}

	/**
	 * Setter method for property <tt>industryTypeId</tt>.
	 * 
	 * @param industryTypeId value to be assigned to property industryTypeId
	 */
	public void setIndustryTypeId(String industryTypeId) {
		this.industryTypeId = industryTypeId;
	}

	/**
	 * Getter method for property <tt>industryTypeId2</tt>.
	 * 
	 * @return property value of industryTypeId2
	 */
	public String getIndustryTypeId2() {
		return industryTypeId2;
	}

	/**
	 * Setter method for property <tt>industryTypeId2</tt>.
	 * 
	 * @param industryTypeId2 value to be assigned to property industryTypeId2
	 */
	public void setIndustryTypeId2(String industryTypeId2) {
		this.industryTypeId2 = industryTypeId2;
	}

	/**
	 * Getter method for property <tt>regiCode</tt>.
	 * 
	 * @return property value of regiCode
	 */
	public String getRegiCode() {
		return regiCode;
	}

	/**
	 * Setter method for property <tt>regiCode</tt>.
	 * 
	 * @param regiCode value to be assigned to property regiCode
	 */
	public void setRegiCode(String regiCode) {
		this.regiCode = regiCode;
	}

	/**
	 * Getter method for property <tt>stdmerno</tt>.
	 * 
	 * @return property value of stdmerno
	 */
	public String getStdmerno() {
		return stdmerno;
	}

	/**
	 * Setter method for property <tt>stdmerno</tt>.
	 * 
	 * @param stdmerno value to be assigned to property stdmerno
	 */
	public void setStdmerno(String stdmerno) {
		this.stdmerno = stdmerno;
	}

	public String getPosCustId() {
        return posCustId;
    }

    public void setPosCustId(String posCustId) {
        this.posCustId = posCustId == null ? null : posCustId.trim();
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId == null ? null : custId.trim();
    }

    public String getPosCustName() {
        return posCustName;
    }

    public void setPosCustName(String posCustName) {
        this.posCustName = posCustName == null ? null : posCustName.trim();
    }

    public String getBusiDivision() {
        return busiDivision;
    }

    public void setBusiDivision(String busiDivision) {
        this.busiDivision = busiDivision == null ? null : busiDivision.trim();
    }

    public String getPosCustKind() {
        return posCustKind;
    }

    public void setPosCustKind(String posCustKind) {
        this.posCustKind = posCustKind == null ? null : posCustKind.trim();
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName == null ? null : operName.trim();
    }

    public Date getOperDate() {
        return operDate;
    }

    public void setOperDate(Date operDate) {
        this.operDate = operDate;
    }
}