/**
 * 
 *	哈尔滨银行
 * Copyright (c) 2007-2015 HRBB,Inc.All Rights Reserved.
 */
package com.hrbb.loan.pos.facade.bean.customer;

import java.io.Serializable;
import java.util.Date;

import com.hrbb.loan.pos.facade.abs.AbstractRequest;

/**
 * 
 * @author XLY
 * @version $Id: CustomerInfoInsertReq.java, v 0.1 2015-2-16 下午4:52:07 XLY Exp $
 */
public class CustomerInfoReq  extends AbstractRequest implements Serializable{

    /**  */
    private static final long serialVersionUID = -1398080815109700637L;

    /**客户ID*/
    private String custId;
    /**客户名*/
    private String custName;
    /**证件类型*/
    private String paperKind;
    /**证件号*/
    private String paperId;
    /**生日*/
    private Date birtDate;
    /**性别*/
    private String sexSign;
    /**婚姻状况*/
    private String marrSign;

    private String educSign;

    private String childNum;

    private String regiSeat;

    private String natiSign1;

    private String natiFlag;

    private String resiYear;

    private String residentProv;

    private String residentCity;

    private String residentDetail;

    private String contactAddrFlag;

    private String inhabStatSign;

    private String workCorp;

    private String corpAddr;

    private String mobilePhone;

    private String tel;

    private String weixinNo;

    private String qqNo;

    private String email;

    private String remarks;

    private String lastChanPerson;

    private Date lastChanDate;

    private String loanPaperId;

    private Date loanPaperCheckDate;

    private String posCustName;

    private String posCustId;

    private String regiCode;

    private String posCustBusiScope;
    
    /**经营区划*/
    private String busiDivision;

    private String operAddrCode;

    private String operAddrDetail;

    private String industryTypeId;

    private String industryTypeId2;

    private String busiYear;

    private String localHouseNum;

    private String otherHouseNum;

    private String familyCustName;

    private String matePaperKind;

    private String matePaperId;

    private String mateMobilephone;

    private String relaCustName;

    private String relaKind;

    private String relaMobilephone;

    private String relaTel;

    private String bankAccno;

    private String bankName;

    private String bankBranName;

    private String bankSubbName;

    private String veriFication;
    
    private String relativeId;
    
    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getPaperKind() {
        return paperKind;
    }

    public void setPaperKind(String paperKind) {
        this.paperKind = paperKind;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public Date getBirtDate() {
        return birtDate;
    }

    public void setBirtDate(Date birtDate) {
        this.birtDate = birtDate;
    }

    public String getSexSign() {
        return sexSign;
    }

    public void setSexSign(String sexSign) {
        this.sexSign = sexSign;
    }

    public String getMarrSign() {
        return marrSign;
    }

    public void setMarrSign(String marrSign) {
        this.marrSign = marrSign;
    }

    public String getEducSign() {
        return educSign;
    }

    public void setEducSign(String educSign) {
        this.educSign = educSign;
    }

    public String getChildNum() {
        return childNum;
    }

    public void setChildNum(String childNum) {
        this.childNum = childNum;
    }

    public String getRegiSeat() {
        return regiSeat;
    }

    public void setRegiSeat(String regiSeat) {
        this.regiSeat = regiSeat;
    }

    public String getNatiSign1() {
        return natiSign1;
    }

    public void setNatiSign1(String natiSign1) {
        this.natiSign1 = natiSign1;
    }

    public String getNatiFlag() {
        return natiFlag;
    }

    public void setNatiFlag(String natiFlag) {
        this.natiFlag = natiFlag;
    }

    public String getResiYear() {
        return resiYear;
    }

    public void setResiYear(String resiYear) {
        this.resiYear = resiYear;
    }

    public String getResidentProv() {
        return residentProv;
    }

    public void setResidentProv(String residentProv) {
        this.residentProv = residentProv;
    }

    public String getResidentCity() {
        return residentCity;
    }

    public void setResidentCity(String residentCity) {
        this.residentCity = residentCity;
    }

    public String getResidentDetail() {
        return residentDetail;
    }

    public void setResidentDetail(String residentDetail) {
        this.residentDetail = residentDetail;
    }

    public String getContactAddrFlag() {
        return contactAddrFlag;
    }

    public void setContactAddrFlag(String contactAddrFlag) {
        this.contactAddrFlag = contactAddrFlag;
    }

    public String getInhabStatSign() {
        return inhabStatSign;
    }

    public void setInhabStatSign(String inhabStatSign) {
        this.inhabStatSign = inhabStatSign;
    }

    public String getWorkCorp() {
        return workCorp;
    }

    public void setWorkCorp(String workCorp) {
        this.workCorp = workCorp;
    }

    public String getCorpAddr() {
        return corpAddr;
    }

    public void setCorpAddr(String corpAddr) {
        this.corpAddr = corpAddr;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getWeixinNo() {
        return weixinNo;
    }

    public void setWeixinNo(String weixinNo) {
        this.weixinNo = weixinNo;
    }

    public String getQqNo() {
        return qqNo;
    }

    public void setQqNo(String qqNo) {
        this.qqNo = qqNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getLastChanPerson() {
        return lastChanPerson;
    }

    public void setLastChanPerson(String lastChanPerson) {
        this.lastChanPerson = lastChanPerson;
    }

    public Date getLastChanDate() {
        return lastChanDate;
    }

    public void setLastChanDate(Date lastChanDate) {
        this.lastChanDate = lastChanDate;
    }

    public String getLoanPaperId() {
        return loanPaperId;
    }

    public void setLoanPaperId(String loanPaperId) {
        this.loanPaperId = loanPaperId;
    }

    public Date getLoanPaperCheckDate() {
        return loanPaperCheckDate;
    }

    public void setLoanPaperCheckDate(Date loanPaperCheckDate) {
        this.loanPaperCheckDate = loanPaperCheckDate;
    }

    public String getPosCustName() {
        return posCustName;
    }

    public void setPosCustName(String posCustName) {
        this.posCustName = posCustName;
    }

    public String getPosCustId() {
        return posCustId;
    }

    public void setPosCustId(String posCustId) {
        this.posCustId = posCustId;
    }

    public String getRegiCode() {
        return regiCode;
    }

    public void setRegiCode(String regiCode) {
        this.regiCode = regiCode;
    }


    public String getPosCustBusiScope() {
        return posCustBusiScope;
    }

    public void setPosCustBusiScope(String posCustBusiScope) {
        this.posCustBusiScope = posCustBusiScope;
    }

    public String getOperAddrCode() {
        return operAddrCode;
    }

    public void setOperAddrCode(String operAddrCode) {
        this.operAddrCode = operAddrCode;
    }

    public String getOperAddrDetail() {
        return operAddrDetail;
    }

    public void setOperAddrDetail(String operAddrDetail) {
        this.operAddrDetail = operAddrDetail;
    }

    public String getIndustryTypeId() {
        return industryTypeId;
    }

    public void setIndustryTypeId(String industryTypeId) {
        this.industryTypeId = industryTypeId;
    }

    public String getIndustryTypeId2() {
        return industryTypeId2;
    }

    public void setIndustryTypeId2(String industryTypeId2) {
        this.industryTypeId2 = industryTypeId2;
    }

    public String getBusiYear() {
        return busiYear;
    }

    public void setBusiYear(String busiYear) {
        this.busiYear = busiYear;
    }

    public String getLocalHouseNum() {
        return localHouseNum;
    }

    public void setLocalHouseNum(String localHouseNum) {
        this.localHouseNum = localHouseNum;
    }

    public String getOtherHouseNum() {
        return otherHouseNum;
    }

    public void setOtherHouseNum(String otherHouseNum) {
        this.otherHouseNum = otherHouseNum;
    }

    public String getFamilyCustName() {
        return familyCustName;
    }

    public void setFamilyCustName(String familyCustName) {
        this.familyCustName = familyCustName;
    }

    public String getMatePaperKind() {
        return matePaperKind;
    }

    public void setMatePaperKind(String matePaperKind) {
        this.matePaperKind = matePaperKind;
    }

    public String getMatePaperId() {
        return matePaperId;
    }

    public void setMatePaperId(String matePaperId) {
        this.matePaperId = matePaperId;
    }

    public String getMateMobilephone() {
        return mateMobilephone;
    }

    public void setMateMobilephone(String mateMobilephone) {
        this.mateMobilephone = mateMobilephone;
    }

    public String getRelaCustName() {
        return relaCustName;
    }

    public void setRelaCustName(String relaCustName) {
        this.relaCustName = relaCustName;
    }

    public String getRelaKind() {
        return relaKind;
    }

    public void setRelaKind(String relaKind) {
        this.relaKind = relaKind;
    }

    public String getRelaMobilephone() {
        return relaMobilephone;
    }

    public void setRelaMobilephone(String relaMobilephone) {
        this.relaMobilephone = relaMobilephone;
    }

    public String getRelaTel() {
        return relaTel;
    }

    public void setRelaTel(String relaTel) {
        this.relaTel = relaTel;
    }

    public String getBankAccno() {
        return bankAccno;
    }

    public void setBankAccno(String bankAccno) {
        this.bankAccno = bankAccno;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankBranName() {
        return bankBranName;
    }

    public void setBankBranName(String bankBranName) {
        this.bankBranName = bankBranName;
    }

    public String getBankSubbName() {
        return bankSubbName;
    }

    public void setBankSubbName(String bankSubbName) {
        this.bankSubbName = bankSubbName;
    }

    public String getVeriFication() {
        return veriFication;
    }

    public void setVeriFication(String veriFication) {
        this.veriFication = veriFication;
    }

    public String getRelativeId() {
        return relativeId;
    }

    public void setRelativeId(String relativeId) {
        this.relativeId = relativeId;
    }

    public String getBusiDivision() {
        return busiDivision;
    }

    public void setBusiDivision(String busiDivision) {
        this.busiDivision = busiDivision;
    }

    @Override
    public String toString() {
        return "CustomerInfoInsertReq [custId=" + custId + ", custName=" + custName
               + ", paperKind=" + paperKind + ", paperId=" + paperId + ", birtDate=" + birtDate
               + ", sexSign=" + sexSign + ", marrSign=" + marrSign + ", educSign=" + educSign
               + ", childNum=" + childNum + ", regiSeat=" + regiSeat + ", natiSign1=" + natiSign1
               + ", natiFlag=" + natiFlag + ", resiYear=" + resiYear + ", residentProv="
               + residentProv + ", residentCity=" + residentCity + ", residentDetail="
               + residentDetail + ", contactAddrFlag=" + contactAddrFlag + ", inhabStatSign="
               + inhabStatSign + ", workCorp=" + workCorp + ", corpAddr=" + corpAddr
               + ", mobilePhone=" + mobilePhone + ", tel=" + tel + ", weixinNo=" + weixinNo
               + ", qqNo=" + qqNo + ", email=" + email + ", remarks=" + remarks
               + ", lastChanPerson=" + lastChanPerson + ", lastChanDate=" + lastChanDate
               + ", loanPaperId=" + loanPaperId + ", loanPaperCheckDate=" + loanPaperCheckDate
               + ", posCustName=" + posCustName + ", posCustId=" + posCustId + ", regiCode="
               + regiCode + ", posCustBusiScope=" + posCustBusiScope + ", operAddrCode=" + operAddrCode
               + ", operAddrDetail=" + operAddrDetail
               + ", industryTypeId=" + industryTypeId + ", industryTypeId2=" + industryTypeId2
               + ", busiYear=" + busiYear + ", localHouseNum=" + localHouseNum + ", otherHouseNum="
               + otherHouseNum + ", familyCustName=" + familyCustName + ", matePaperKind="
               + matePaperKind + ", matePaperId=" + matePaperId + ", mateMobilephone="
               + mateMobilephone + ", relaCustName=" + relaCustName + ", relaKind=" + relaKind
               + ", relaMobilephone=" + relaMobilephone + ", relaTel=" + relaTel + ", bankAccno="
               + bankAccno + ", bankName=" + bankName + ", bankBranName=" + bankBranName
               + ", bankSubbName=" + bankSubbName + ", veriFication=" + veriFication + "]";
    }
    
    
}
