/**
 * 
 *	哈尔滨银行
 * Copyright (c) 2007-2015 HRBB,Inc.All Rights Reserved.
 */
package com.hrbb.loan.spi.TC2;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.hrbb.loan.hessian.inter.IBankCardCheckHessianService;
import com.hrbb.loan.pos.dao.entity.TCustomer;
import com.hrbb.loan.pos.service.LoanPosCreditApplyService;
import com.hrbb.loan.pos.service.LoanPosCustomerService;
import com.hrbb.loan.pos.util.ListUtil;
import com.hrbb.loan.pos.util.StringUtil;
import com.hrbb.loan.spi.TC.TCService;
import com.hrbb.loan.spiconstants.CreditApplyHServiceConstants;
import com.hrbb.loan.spiconstants.HServiceReturnCode;
import com.hrbb.sh.framework.HRequest;
import com.hrbb.sh.framework.HResponse;
import com.hrbb.sh.framework.HServiceException;

/**
 * T64银行卡绑卡验证
 * 
 * @author marco
 * @version $Id: TC2BankCardCheckServiceImpl.java, v 0.1 2015-8-20 下午2:33:59
 *          marco Exp $
 */
@Service("tc2BankCardCheck")
public class TC2BankCardCheckServiceImpl extends TCService {

	@Autowired
	private IBankCardCheckHessianService IbankCardCheckHessianService;
	
	@Autowired
	private LoanPosCreditApplyService loanPosCreditApplyService;
	
	@Autowired
	private LoanPosCustomerService loanPosCustomerService;

	@Override
	public HResponse serve(HRequest request) throws HServiceException {
		HResponse response = new HResponse();

		logger.debug("TC2BankCardCheckServiceImpl begin");

		Map<String, Object> props = request.getProperties();

		String custId = (String) props.get(CreditApplyHServiceConstants.custid);
		logger.debug("custId=" + custId);
		String custname = (String) props
				.get(CreditApplyHServiceConstants.custname);
		logger.debug("custname=" + custname);

		String paperid = (String) props
				.get(CreditApplyHServiceConstants.paperid);
		logger.debug("paperid=" + paperid);
		String bankaccno = (String) props
				.get(CreditApplyHServiceConstants.bankaccno);
		logger.debug("bankaccno=" + bankaccno);

		String bankname = (String) props
				.get(CreditApplyHServiceConstants.bankName);
		logger.debug("账户开户行为"+bankname);
		String bankbrandname = (String) props
				.get(CreditApplyHServiceConstants.bankBrandName);
		logger.debug("账户分行为"+bankbrandname);
		String banksubname = (String) props
				.get(CreditApplyHServiceConstants.bankSubName);
		logger.debug("账户支行为"+banksubname);
		String reservedno = (String) props
				.get(CreditApplyHServiceConstants.reservedno);
		logger.debug("reservedno=" + reservedno);

		if (StringUtil.isEmpty(custId)) {
			logger.error("客户编号为空");
			response.setRespCode(HServiceReturnCode.CUSTID_1_ERROR
					.getReturnCode());
			response.setRespMessage(HServiceReturnCode.CUSTID_1_ERROR
					.getReturnMessage());
			response.setRespTime(new Date());
			return response;
		}

		if (StringUtil.isEmpty(custname)) {
			logger.error("客户姓名为空");
			response.setRespCode(HServiceReturnCode.CUSTNAME_ERROR
					.getReturnCode());
			response.setRespMessage(HServiceReturnCode.CUSTNAME_ERROR
					.getReturnMessage());
			response.setRespTime(new Date());
			return response;
		}

		if (StringUtil.isEmpty(paperid)) {
			logger.error("证件号码为空");
			response.setRespCode(HServiceReturnCode.PAPERID_ERROR
					.getReturnCode());
			response.setRespMessage(HServiceReturnCode.PAPERID_ERROR
					.getReturnMessage());
			response.setRespTime(new Date());
			return response;
		}

		if (StringUtil.isEmpty(bankaccno)) {
			logger.error("银行卡号为空");
			response.setRespCode(HServiceReturnCode.BANKACCNO_ERROR
					.getReturnCode());
			response.setRespMessage(HServiceReturnCode.BANKACCNO_ERROR
					.getReturnMessage());
			response.setRespTime(new Date());
			return response;
		}

		if (StringUtil.isEmpty(bankname)) {
			logger.error("账户开户行为空");
			response.setRespCode(HServiceReturnCode.BANKNAME_ERROR
					.getReturnCode());
			response.setRespMessage(HServiceReturnCode.BANKNAME_ERROR
					.getReturnMessage());
			response.setRespTime(new Date());
			return response;
		}

		if (StringUtil.isEmpty(bankbrandname)) {
			logger.error("账户分行为空");
			response.setRespCode(HServiceReturnCode.BANKBRANNAME_ERROR
					.getReturnCode());
			response.setRespMessage(HServiceReturnCode.BANKBRANNAME_ERROR
					.getReturnMessage());
			response.setRespTime(new Date());
			return response;
		}

		if (StringUtil.isEmpty(banksubname)) {
			logger.error("账户支行为空");
			response.setRespCode(HServiceReturnCode.BANKSUBNAME_ERROR
					.getReturnCode());
			response.setRespMessage(HServiceReturnCode.BANKSUBNAME_ERROR
					.getReturnMessage());
			response.setRespTime(new Date());
			return response;
		}

		if (StringUtil.isEmpty(reservedno)) {
			logger.error("预留手机号码为空");
			response.setRespCode(HServiceReturnCode.MOBILEPHONE_ERROR
					.getReturnCode());
			response.setRespMessage(HServiceReturnCode.MOBILEPHONE_ERROR
					.getReturnMessage());
			response.setRespTime(new Date());
			return response;
		}

		// 易极付请求参数
		Map<String, String> bankCheckMap = Maps.newHashMap();
		bankCheckMap.put("cardNo", bankaccno);
		bankCheckMap.put("cardName", custname);
		bankCheckMap.put("certNo", paperid);
		
//		bankCheckMap.put("bankName", bankname);
//		bankCheckMap.put("bankBranName", bankbrandname);
//		bankCheckMap.put("bankSubbName", banksubname);
//		bankCheckMap.put("custId", custId);
//		bankCheckMap.put("paperid", paperid);
//		bankCheckMap.put("custname", custname);
		
		Map<String, Object> map = Maps.newHashMap();
		Map<String, Object> bankmap = Maps.newHashMap();
		bankmap.put("bankAccno",bankaccno);
		List<Map<String, Object>> resList = loanPosCreditApplyService
				.selectBankAccMap(bankmap);
		TCustomer cust = loanPosCustomerService.getCustumerInfoById(custId);
		if(!ListUtil.isNotEmpty(resList)||cust==null){
			logger.debug("数据库中不存在该银行卡信息");
			response.setRespCode("1");
			response.setRespMessage("不一致");
			map.put("verifyresult","1");
			response.setProperties(map);
			return response;
		}
		//数据库里保存的客户银行卡相关信息
		String custid1 = (String) resList.get(0).get("custid");
		String bankName1 = (String) resList.get(0).get("bankName");
		String bankBranName1 = (String) resList.get(0).get("bankBranName");
		String banksubname1 = (String) resList.get(0).get("bankSubbName");
		String custname1 = cust.getCustName();
		String paperid1 = cust.getPaperId();
	
		if(!ListUtil.isNotEmpty(resList)||!custId.equals(custid1)||!bankname.equals(bankName1)||!bankbrandname.equals(bankBranName1)||!banksubname.equals(banksubname1)||!paperid.equals(paperid1)||!custname.equals(custname1)){
			logger.debug("报文中的客户账户信息与数据库中的账户信息不同");
			response.setRespCode("1");
			response.setRespMessage("不一致");
			map.put("verifyresult","1");
			response.setProperties(map);
			return response;
		}
		
		
		// 账户验真
		Map<String, String> resultMap = IbankCardCheckHessianService
				.bankCardCheck(bankCheckMap);

		response.setRespTime(new Date());
		
		if (resultMap.get("respCode") == null) {
			logger.info("9：验卡失败");
			response.setRespCode("9");
			response.setRespMessage("验卡失败");
		} else {
			if ("00".equals(resultMap.get("respCode"))) {
				logger.info("0：一致");
				response.setRespCode("0");
				response.setRespMessage("一致");
				map.put("verifyresult","0");
				response.setProperties(map);
			} else if ("01".equals(resultMap.get("respCode"))) {
				logger.info("1：不一致");
				response.setRespCode("1");
				response.setRespMessage("不一致");
				map.put("verifyresult","1");
				response.setProperties(map);
			} else {
				logger.info("9：验卡失败");
				response.setRespCode("9");
				response.setRespMessage("验卡失败");
				map.put("verifyresult","9");
				response.setProperties(map);
			}
		}
		logger.info("TC2BankCardCheckServiceImpl end");
		return response;
	}
}
