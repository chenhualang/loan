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
 * @version $Id: ExpireNotifyMessage.java, v 0.1 2015年3月16日 下午6:57:58 byp Exp $
 */
public class ExpireNotifyMessage implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1990000070093400917L;

	private String listId;

    private String retuKind;

    private String retuKind2;

    private String bankAcNo;

    private String repayExpDate;

    private String repayTotalAmt;

    private String repayPrincipal;

    private String repayInt;

    private String alertDate;

    /**
     * 
     */
    public ExpireNotifyMessage() {
    }

    public String getListId() {
        return listId;
    }

    public ExpireNotifyMessage setListId(String listId) {
        this.listId = listId;
        return this;
    }

    public String getRetuKind() {
        return retuKind;
    }

    public ExpireNotifyMessage setRetuKind(String retuKind) {
        this.retuKind = retuKind;
        return this;
    }

    public String getRetuKind2() {
        return retuKind2;
    }

    public ExpireNotifyMessage setRetuKind2(String retuKind2) {
        this.retuKind2 = retuKind2;
        return this;
    }

    public String getBankAcNo() {
        return bankAcNo;
    }

    public ExpireNotifyMessage setBankAcNo(String bankAcNo) {
        this.bankAcNo = bankAcNo;
        return this;
    }

    public String getRepayExpDate() {
        return repayExpDate;
    }

    public ExpireNotifyMessage setRepayExpDate(String repayExpDate) {
        this.repayExpDate = repayExpDate;
        return this;
    }

    public String getRepayTotalAmt() {
        return repayTotalAmt;
    }

    public ExpireNotifyMessage setRepayTotalAmt(String repayTotalAmt) {
        this.repayTotalAmt = repayTotalAmt;
        return this;
    }

    public String getRepayPrincipal() {
        return repayPrincipal;
    }

    public ExpireNotifyMessage setRepayPrincipal(String repayPrincipal) {
        this.repayPrincipal = repayPrincipal;
        return this;
    }

    public String getRepayInt() {
        return repayInt;
    }

    public ExpireNotifyMessage setRepayInt(String repayInt) {
        this.repayInt = repayInt;
        return this;
    }

    public String getAlertDate() {
        return alertDate;
    }

    public ExpireNotifyMessage setAlertDate(String alertDate) {
        this.alertDate = alertDate;
        return this;
    }
    
    public String toString(){
        return  new String("\"listid\":\"" + listId + "\","
                + "\"retukind\":\"" + retuKind + "\","
                + "\"retukind2\":\"" + retuKind2 + "\","
                + "\"bankacno\":\"" + bankAcNo + "\","
                + "\"repayexpdate\":\"" + repayExpDate + "\","
                + "\"repaytotalamt\":\"" + repayTotalAmt + "\","
                + "\"repayprincipal\":\"" + repayPrincipal + "\","
                + "\"repayint\":\"" + repayInt + "\","
                + "\"alertdate\":\"" + alertDate +"\"").replaceAll("[\\t\\n\\r]", "");

    }

}
