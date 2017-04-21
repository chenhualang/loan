/**
 * 
 *	哈尔滨银行
 * Copyright (c) 2007-2015 HRBB,Inc.All Rights Reserved.
 */
package com.hrbb.loan.pos.biz.backstage.inter;

import java.util.Map;

/**
 * 
 * @author marco
 * @version $Id: SynFileSummaryBiz.java, v 0.1 2015-4-24 下午4:18:52 marco Exp $
 */
public interface SynFileSummaryBiz {
	public Map<String, String> makeSynFile(String channel, String fromDay,
			String toDay);

	public Map<String, String> upload(String fileName);
}
