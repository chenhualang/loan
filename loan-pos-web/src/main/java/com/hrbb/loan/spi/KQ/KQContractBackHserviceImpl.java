package com.hrbb.loan.spi.KQ;

import org.springframework.stereotype.Service;

import com.hrbb.loan.spi.POSHService;
import com.hrbb.loan.spi.TC.TCContractBackHserviceImpl;

/**
 * 电子协议回传
 * 
 * @author cjq
 * @version $Id: TCContractBackHserviceImpl.java, v 0.1 2015年5月29日 下午2:35:36 cjq Exp $
 */
@Service("kqContractBack")
public class KQContractBackHserviceImpl extends TCContractBackHserviceImpl {
    
	/* (non-Javadoc)
	 * @see com.hrbb.loan.spi.POSHService#getChannle()
	 */
	@Override
	public String getChannel() {
		return POSHService.进件渠道_快钱;
	}
}
