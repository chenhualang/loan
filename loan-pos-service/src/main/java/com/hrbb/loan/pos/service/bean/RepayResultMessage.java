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
 * @version $Id: RepayResultMessage.java, v 0.1 2015年3月16日 下午6:52:24 byp Exp $
 */
public class RepayResultMessage implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5212009044826181230L;

	private String listId;

    private String repayListId;

    private String retuKind;

    private String retuKind2;

    private String bankAcNo;

    private String repayDate;

    private String repayTotalAmt;

    private String repayPrincipal;

    private String repayInt;

    /**
     * 
     */
    public RepayResultMessage() {
    }

    public String getListId() {
        return listId;
    }

    public RepayResultMessage setListId(String listId) {
        this.listId = listId;
        return this;
    }

    public String getRepayListId() {
        return repayListId;
    }

    public RepayResultMessage setRepayListId(String repayListId) {
        this.repayListId = repayListId;
        return this;
    }

    public String getRetuKind() {
        return retuKind;
    }

    public RepayResultMessage setRetuKind(String retuKind) {
        this.retuKind = retuKind;
        return this;
    }

    public String getRetuKind2() {
        return retuKind2;
    }

    public RepayResultMessage setRetuKind2(String retuKind2) {
        this.retuKind2 = retuKind2;
        return this;
    }

    public String getBankAcNo() {
        return bankAcNo;
    }

    public RepayResultMessage setBankAcNo(String bankAcNo) {
        this.bankAcNo = bankAcNo;
        return this;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public RepayResultMessage setRepayDate(String repayDate) {
        this.repayDate = repayDate;
        return this;
    }

    public String getRepayTotalAmt() {
        return repayTotalAmt;
    }

    public RepayResultMessage setRepayTotalAmt(String repayTotalAmt) {
        this.repayTotalAmt = repayTotalAmt;
        return this;
    }

    public String getRepayPrincipal() {
        return repayPrincipal;
    }

    public RepayResultMessage setRepayPrincipal(String repayPrincipal) {
        this.repayPrincipal = repayPrincipal;
        return this;
    }

    public String getRepayInt() {
        return repayInt;
    }

    public RepayResultMessage setRepayInt(String repayInt) {
        this.repayInt = repayInt;
        return this;
    }

    public String toString() {
        return new String("\"listid\":\"" + listId + "\","
               + "\"repaylistid\":\"" + repayListId + "\","
               + "\"retukind\":\"" + retuKind + "\","
               + "\"retukind2\":\"" + retuKind2 + "\","
               + "\"bankacno\":\"" + bankAcNo + "\","
               + "\"repaydate\":\"" + repayDate + "\","
               + "\"repaytotalamt\":\"" + repayTotalAmt + "\","
               + "\"repayprincipal\":\"" + repayPrincipal + "\","
               + "\"repayint\":\"" + repayInt+"\"").replaceAll("[\\t\\n\\r]", "");
    }
}
