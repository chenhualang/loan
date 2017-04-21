package com.hrbb.loan.pos.dao.entity;

import java.io.Serializable;

public class TBdInstitution implements Serializable{
    /**  */
    private static final long serialVersionUID = -7670918755415618530L;

    private Integer orgId;

    private String orgName;

    private String licenseNo;

    private String alias;

    private String nameLR;

    private String phoneNoLR;

    private String certNoLR;

    private String regAddress;

    private String remark;

    private String manager;

    private String active;
    
    private String email;
    
    private String mailCc;
    
    private String mailBcc;

    public String getMailCc() {
		return mailCc;
	}

	public void setMailCc(String mailCc) {
		this.mailCc = mailCc;
	}

	public String getMailBcc() {
		return mailBcc;
	}

	public void setMailBcc(String mailBcc) {
		this.mailBcc = mailBcc;
	}

	public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo == null ? null : licenseNo.trim();
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
    }

    public String getNameLR() {
        return nameLR;
    }

    public void setNameLR(String nameLR) {
        this.nameLR = nameLR == null ? null : nameLR.trim();
    }

    public String getPhoneNoLR() {
        return phoneNoLR;
    }

    public void setPhoneNoLR(String phoneNoLR) {
        this.phoneNoLR = phoneNoLR == null ? null : phoneNoLR.trim();
    }

    public String getCertNoLR() {
        return certNoLR;
    }

    public void setCertNoLR(String certNoLR) {
        this.certNoLR = certNoLR == null ? null : certNoLR.trim();
    }

    public String getRegAddress() {
        return regAddress;
    }

    public void setRegAddress(String regAddress) {
        this.regAddress = regAddress == null ? null : regAddress.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager == null ? null : manager.trim();
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active == null ? null : active.trim();
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}