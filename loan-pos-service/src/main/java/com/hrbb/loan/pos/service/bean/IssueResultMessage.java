/**
 * 
 *	哈尔滨银行
 * Copyright (c) 2007-2015 HRBB,Inc.All Rights Reserved.
 */
package com.hrbb.loan.pos.service.bean;

import java.io.Serializable;

/**
 *  "issueresult”:”01”,”listid”:”00000”,”issueamt”:”1000000”,”interrate”:”0.18”,”valuedate”:”2015-2-15”,  \n
 *  ”expdate”:”2015-5-15”,”intcalcmeth”:”00”,”retukind”:”00”,”bankacno”:”651200000000”,”bankname”:”…”, \n
 *  ”bankbranchname”:”…”,”banksubname”:”….”,”refusereason”,”….”
 * 
 * 
 * @author byp
 * @version $Id: IssueResultMessage.java, v 0.1 2015年3月16日 下午6:38:17 byp Exp $
 */
public class IssueResultMessage implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7127120856186805619L;

	private String issueResult;

    private String listId;

    private String issueAmt;

    private String interRate;

    private String valueDate;

    private String expDate;
    
    private String intCalcMeth;

    private String retuKind;

    private String bankAcNo;

    private String bankName;

    private String bankBranchName;

    private String bankSubName;

    private String refuseReason;

    /**
     * 
     */
    public IssueResultMessage() {
    }

    public String getIssueResult() {
        return issueResult;
    }

    public IssueResultMessage setIssueResult(String issueResult) {
        this.issueResult = issueResult;
        return this;
    }

    public String getListId() {
        return listId;
    }

    public IssueResultMessage setListId(String listId) {
        this.listId = listId;
        return this;
    }

    public String getIssueAmt() {
        return issueAmt;
    }

    public IssueResultMessage setIssueAmt(String issueAmt) {
        this.issueAmt = issueAmt;
        return this;
    }

    public String getInterRate() {
        return interRate;
    }

    public IssueResultMessage setInterRate(String interRate) {
        this.interRate = interRate;
        return this;
    }

    public String getValueDate() {
        return valueDate;
    }

    public IssueResultMessage setValueDate(String valueDate) {
        this.valueDate = valueDate;
        return this;
    }

    public String getExpDate() {
        return expDate;
    }

    public IssueResultMessage setExpDate(String expDate) {
        this.expDate = expDate;
        return this;
    }

    public String getIntCalcMeth() {
        return intCalcMeth;
    }

    public IssueResultMessage setIntCalcMeth(String intCalcMeth) {
        this.intCalcMeth = intCalcMeth;
        return this;
   }

    public String getRetuKind() {
        return retuKind;
    }

    public IssueResultMessage setRetuKind(String retuKind) {
        this.retuKind = retuKind;
        return this;
    }

    public String getBankAcNo() {
        return bankAcNo;
    }

    public IssueResultMessage setBankAcNo(String bankAcNo) {
        this.bankAcNo = bankAcNo;
        return this;
    }

    public String getBankName() {
        return bankName;
    }

    public IssueResultMessage setBankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public String getBankBranchName() {
        return bankBranchName;
    }

    public IssueResultMessage setBankBranchName(String bankBranchName) {
        this.bankBranchName = bankBranchName;
        return this;
    }

    public String getBankSubName() {
        return bankSubName;
    }

    public IssueResultMessage setBankSubName(String bankSubName) {
        this.bankSubName = bankSubName;
        return this;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public IssueResultMessage setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
        return this;
    }
    
    public String toString () {
        return new String("\"issueresult\":\"" + issueResult + "\","
                + "\"listid\":\"" + listId + "\","
                + "\"issueamt\":\"" + issueAmt + "\","
                + "\"interrate\":\"" + interRate + "\","
                + "\"valuedate\":\"" + valueDate + "\","
                + "\"expdate\":\"" + expDate + "\","
                + "\"intcalcmeth\":\"" + intCalcMeth + "\","
                + "\"retukind\":\"" + retuKind + "\","
                + "\"bankacno\":\"" + bankAcNo + "\","
                + "\"bankname\":\"" + bankName + "\","
                + "\"bankbranchname\":\"" + bankBranchName + "\","
                + "\"banksubname\":\"" + bankSubName + "\","
                + "\"refusereason\":\"" + refuseReason+"\"").replaceAll("[\\t\\n\\r]", "");

    }
}
