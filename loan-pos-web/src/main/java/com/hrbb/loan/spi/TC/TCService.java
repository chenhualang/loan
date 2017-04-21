/**
 * 
 */
package com.hrbb.loan.spi.TC;

import com.hrbb.loan.spi.AbstractPOSService;
import com.hrbb.loan.spi.POSHService;

/**
 * <p>Title: HCService.java </p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (C) 2015 Hrbb Ltd. Co.</p> 
 *     
 * @author linzhaolin@hrbb.com.cn
 * @version 
 * @date 2015年7月21日
 *
 * logs: 1. 
 */
public abstract class TCService extends AbstractPOSService {

	/* (non-Javadoc)
	 * @see com.hrbb.loan.spi.POSHService#getChannle()
	 */
	@Override
	public String getChannel() {
		return POSHService.进件渠道_58金融;
	}
	
	/**
	 * 前置加解密标签
	 * @return
	 */
	public String getEncryptedRender(){
		return "58tc";
	}
	
	
	/**
	 * 报文中保持银行代码
	 * @return
	 */
	public boolean keepBankNo(){
		return false;
	}
	
	
}
