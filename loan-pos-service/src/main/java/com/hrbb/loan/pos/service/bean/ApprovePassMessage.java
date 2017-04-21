/**
 * 
 *	哈尔滨银行
 * Copyright (c) 2007-2015 HRBB,Inc.All Rights Reserved.
 */
package com.hrbb.loan.pos.service.bean;

import java.io.Serializable;

/**
 * 类似于： ”appmaxcred”:”1000000”,”apptterm”:”3”,”interrate”:”0.18”
 * 
 * @author byp
 * @version $Id: ApprovePassMessage.java, v 0.1 2015年3月16日 下午6:28:39 byp Exp $
 */
public class ApprovePassMessage implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -6946518272742002836L;

	public ApprovePassMessage(){}

    private String appMaxCred;
    
    private String apptTerm;
    
    private String apptTermUnit;
    
    private String interRate;
    
    /**
	 * Getter method for property <tt>apptTermUnit</tt>.
	 * 
	 * @return property value of apptTermUnit
	 */
	public String getApptTermUnit() {
		return apptTermUnit;
	}

	/**
	 * Setter method for property <tt>apptTermUnit</tt>.
	 * 
	 * @param apptTermUnit value to be assigned to property apptTermUnit
	 */
	public void setApptTermUnit(String apptTermUnit) {
		this.apptTermUnit = apptTermUnit;
	}

	public String getAppMaxCred() {
        return appMaxCred;
    }

    public ApprovePassMessage setAppMaxCred(String appMaxCred) {
        this.appMaxCred = appMaxCred;
        return this;
    }

    public String getApptTerm() {
        return apptTerm;
    }

    public ApprovePassMessage setApptTerm(String apptTerm) {
        this.apptTerm = apptTerm;
        return this;
    }

    public String getInterRate() {
        return interRate;
    }

    public ApprovePassMessage setInterRate(String interRate) {
        this.interRate = interRate;
        return this;
    }

    public String toString() {
        return new String("\"appmaxcred\":\"" + appMaxCred + "\","
                + "\"apptterm\":\"" + apptTerm + "\","
                + "\"appttermunit\":\"" + apptTermUnit + "\","
                + "\"interrate\":\"" + interRate+"\"").replaceAll("[\\t\\n\\r]", "");
    }
}
