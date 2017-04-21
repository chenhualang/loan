package com.hrbb.loan.spi.RNT;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.hrbb.loan.pos.util.StringUtil;
import com.hrbb.loan.pos.util.ValidateUtil;
import com.hrbb.loan.spi.POSHService;
import com.hrbb.loan.spi.TC2.TC2ContractBackHserviceImpl;
import com.hrbb.loan.spiconstants.HServiceReturnCode;
import com.hrbb.sh.framework.HRequest;
import com.hrbb.sh.framework.HResponse;
import com.hrbb.sh.framework.HServiceException;

/**
 * T54电子协议回传
 * 
 * @author marco
 * 
 */
@Service("rnContractBack")
public class RNContractBackHserviceImpl extends TC2ContractBackHserviceImpl {
	@Override
	public String getChannel() {
		return POSHService.进件渠道_融360;
	}

	/**
	 * 前置加解密标签
	 * 
	 * @return
	 */
	@Override
	public String getEncryptedRender() {
		return "RNT";
	}
	

}
