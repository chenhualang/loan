package com.hrbb.loan.pos.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class TBdExecutor implements Serializable{
    /**  */
    private static final long serialVersionUID = 3677439583781059338L;

    /**  */

    private Integer menberId;

    private String menberName;

    private String certNo;

    private Date birthDate;

    private String sex;

    private String contactNo;

    private Integer belongOrg;
    
    private String belongOrgName;

    private String email;

    private String address;

    private String remark;

    private String active;

    private Date modifyTime;

    public Integer getMenberId() {
        return menberId;
    }

    public void setMenberId(Integer menberId) {
        this.menberId = menberId;
    }

    public String getMenberName() {
        return menberName;
    }

    public void setMenberName(String menberName) {
        this.menberName = menberName == null ? null : menberName.trim();
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo == null ? null : certNo.trim();
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo == null ? null : contactNo.trim();
    }

    public Integer getBelongOrg() {
        return belongOrg;
    }

    public void setBelongOrg(Integer belongOrg) {
        this.belongOrg = belongOrg;
    }

    public String getBelongOrgName() {
        return belongOrgName;
    }

    public void setBelongOrgName(String belongOrgName) {
        this.belongOrgName = belongOrgName;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active == null ? null : active.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}