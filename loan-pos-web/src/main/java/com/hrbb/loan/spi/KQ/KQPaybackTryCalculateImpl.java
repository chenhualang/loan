package com.hrbb.loan.spi.KQ;

import org.springframework.stereotype.Service;

import com.hrbb.loan.spi.POSHService;
import com.hrbb.loan.spi.TC.TCPaybackTryCalculateImpl;

@Service("kqPaybackTryCalculate")
public class KQPaybackTryCalculateImpl extends TCPaybackTryCalculateImpl {
	
	/* (non-Javadoc)
	 * @see com.hrbb.loan.spi.POSHService#getChannle()
	 */
	@Override
	public String getChannel() {
		return POSHService.进件渠道_快钱;
	}
}
