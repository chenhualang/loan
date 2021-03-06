package com.hrbb.loan.spi.HC;

import org.springframework.stereotype.Service;

import com.hrbb.loan.spi.POSHService;
import com.hrbb.loan.spi.TC.TCCreditApplyHServiceImpl;
import com.hrbb.loan.spi.UM.UMCreditApplyHServiceImpl;

/**
 * 银商业务申请
 * 
 * @author Johnson
 * 
 */
@Service("hcCreditApply")
public class HCCreditApplyHServiceImpl extends TCCreditApplyHServiceImpl {

	/* (non-Javadoc)
	 * @see com.hrbb.loan.spi.POSHService#getChannle()
	 */
	@Override
	public String getChannel() {
		return POSHService.进件渠道_慧聪网;
	}

}
