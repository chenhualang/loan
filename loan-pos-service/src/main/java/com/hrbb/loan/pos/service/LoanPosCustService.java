/**
 * 
 *  哈尔滨银行
 * Copyright (c) 2007-2015 HRBB,Inc.All Rights Reserved.
 */
package com.hrbb.loan.pos.service;

import java.util.Map;

import com.hrbb.loan.pos.dao.entity.TPosCustInfo;

/**
 * 
 * 商户信息查询服务
 * @author XLY
 * @version $Id: LoanPosCustService.java, v 0.1 2015-2-13 上午10:21:28 XLY Exp $
 */
public interface LoanPosCustService {

	/**
	 * 根据posCustId查询t_posCust_info表
	 * 
	 * @param posCustId
	 * @return
	 */
	public TPosCustInfo selectOnePosCust(String posCustId);
	
	public int updateByPrimaryKeySelectiveMap(Map<String,Object> map);
}
