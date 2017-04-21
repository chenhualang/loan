/**
 * 
 *	哈尔滨银行
 * Copyright (c) 2007-2015 HRBB,Inc.All Rights Reserved.
 */
package com.hrbb.loan.spi.KQ;

import org.springframework.stereotype.Service;

import com.hrbb.loan.spi.POSHService;
import com.hrbb.loan.spi.TC.TCAddInforServiceImpl;

/**
 * 补充资料上传
 * 
 * @author cjq
 * @version $Id: TCAddInforServiceImpl.java, v 0.1 2015年5月29日 上午11:51:34 cjq Exp $
 */
@Service("kqAddInfo")
public class KQAddInforServiceImpl extends TCAddInforServiceImpl {
	
	/* (non-Javadoc)
	 * @see com.hrbb.loan.spi.POSHService#getChannle()
	 */
	@Override
	public String getChannel() {
		return POSHService.进件渠道_快钱;
	}
}
