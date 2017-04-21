/**
 * 
 *	哈尔滨银行
 * Copyright (c) 2007-2015 HRBB,Inc.All Rights Reserved.
 */
package com.hrbb.loan.pos.service.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 工商局查询信息
 * @author XLY
 * @version $Id: AICQueryBean.java, v 0.1 2015-3-13 下午4:01:59 XLY Exp $
 */
public class AICQueryBean implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3551138620788958523L;
	/**
     * 注册资本
     */
    private BigDecimal regCapital;

    /**
     * Getter method for property <tt>regCapital</tt>.
     * 
     * @return property value of regCapital
     */
    public BigDecimal getRegCapital() {
        return regCapital;
    }

    /**
     * Setter method for property <tt>regCapital</tt>.
     * 
     * @param regCapital value to be assigned to property regCapital
     */
    public void setRegCapital(BigDecimal regCapital) {
        this.regCapital = regCapital;
    }
    
    
}
