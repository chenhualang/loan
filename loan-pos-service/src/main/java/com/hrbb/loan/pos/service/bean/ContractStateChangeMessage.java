/**
 * 
 *	哈尔滨银行
 * Copyright (c) 2007-2015 HRBB,Inc.All Rights Reserved.
 */
package com.hrbb.loan.pos.service.bean;

import java.io.Serializable;

/**
 * 
 * @author byp
 * @version $Id: ContractStateChangeMessage.java, v 0.1 2015年3月16日 下午6:44:52 byp Exp $
 */
public class ContractStateChangeMessage implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6117906866666983197L;

	private String cnContNo;
    
    private String contStatus;
    
    private String adjustReason;
    
    private String amt;
    
    private String interRate;
    
    private String crExpDate;
    
    private String retuKind;
    
    private String bankAcNo;
    
    private String adjustDate;
    
    
    public String getCnContNo() {
        return cnContNo;
    }

    public ContractStateChangeMessage setCnContNo(String cnContNo) {
        this.cnContNo = cnContNo;
        return this;
    }

    public String getContStatus() {
        return contStatus;
    }

    public ContractStateChangeMessage setContStatus(String contStatus) {
        this.contStatus = contStatus;
        return this;
    }

    public String getAdjustReason() {
        return adjustReason;
    }

    public ContractStateChangeMessage setAdjustReason(String adjustReason) {
        this.adjustReason = adjustReason;
        return this;
    }

    public String getAmt() {
        return amt;
    }

    public ContractStateChangeMessage setAmt(String amt) {
        this.amt = amt;
        return this;
    }

    public String getInterRate() {
        return interRate;
    }

    public ContractStateChangeMessage setInterRate(String interRate) {
        this.interRate = interRate;
        return this;
    }

    public String getCrExpDate() {
        return crExpDate;
    }

    public ContractStateChangeMessage setCrExpDate(String crExpDate) {
        this.crExpDate = crExpDate;
        return this;
    }

    public String getRetuKind() {
        return retuKind;
    }

    public ContractStateChangeMessage setRetuKind(String retuKind) {
        this.retuKind = retuKind;
        return this;
    }

    public String getBankAcNo() {
        return bankAcNo;
    }

    public ContractStateChangeMessage setBankAcNo(String bankAcNo) {
        this.bankAcNo = bankAcNo;
        return this;
    }

    public String getAdjustDate() {
        return adjustDate;
    }

    public ContractStateChangeMessage setAdjustDate(String adjustDate) {
        this.adjustDate = adjustDate;
        return this;
    }

    /**
     * 
     */
    public ContractStateChangeMessage() {
    }
    
    
    public String toString(){
        return new String("\"cncontno\":\"" + cnContNo + "\","
                + "\"contstatus\":\"" + contStatus + "\","
                + "\"adjustreason\":\"" + adjustReason + "\","
                + "\"amt\":\"" + amt + "\","
                + "\"interrate\":\"" + interRate + "\","
                + "\"crexpdate\":\"" + crExpDate + "\","
                + "\"retukind\":\"" + retuKind + "\","
                + "\"bankacno\":\"" + bankAcNo + "\","
                + "\"adjustdate\":\"" + adjustDate + "\"").replaceAll("[\\t\\n\\r]", "");

    }

}
