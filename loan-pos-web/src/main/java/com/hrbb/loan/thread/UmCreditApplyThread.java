package com.hrbb.loan.thread;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.hrbb.loan.hessian.inter.IAICHessianService;
import com.hrbb.loan.hessian.inter.IBankCardCheckHessianService;
import com.hrbb.loan.hessian.inter.IPoliceHessianService;
import com.hrbb.loan.pos.biz.backstage.inter.ILoanPosCreditApplyBackStageBiz;
import com.hrbb.loan.pos.biz.backstage.inter.ILoanPosCreditApplyCheckRegBiz;
import com.hrbb.loan.pos.service.LoanPosCreditApplyService;
import com.hrbb.loan.pos.util.StringUtil;

@Scope("prototype")
@Component("umCreditApplyThread")
public class UmCreditApplyThread implements Runnable {

	@Autowired
	private ILoanPosCreditApplyBackStageBiz iLoanPosCreditApplyBackStageBiz;
	
	@Autowired
	private IAICHessianService aicHessianService;
	
	@Autowired
	private IPoliceHessianService iPoliceHessianService;
	
	@Autowired
	private IBankCardCheckHessianService iBankCardCheckService;
	
	@Autowired
	private LoanPosCreditApplyService loanPosCreditApplyService;
	
	
	@Autowired
	private ILoanPosCreditApplyCheckRegBiz loanPosCreditApplyCheckRegBiz;
	
	private Logger logger = LoggerFactory.getLogger(UmCreditApplyThread.class);
	
	private Map<String, Object> reqMap;
	
	private Map<String, String> bankCheckMap;
	
	private Map<String, String> aicCheckMap;
	
	private Map<String, String> policeMap;
	
	
	
	
	
	




	public Map<String, String> getPoliceMap() {
		return policeMap;
	}




	public void setPoliceMap(Map<String, String> policeMap) {
		this.policeMap = policeMap;
	}




	public IAICHessianService getAicHessianService() {
		return aicHessianService;
	}




	public void setAicHessianService(IAICHessianService aicHessianService) {
		this.aicHessianService = aicHessianService;
	}




	public Map<String, String> getBankCheckMap() {
		return bankCheckMap;
	}




	public void setBankCheckMap(Map<String, String> bankCheckMap) {
		this.bankCheckMap = bankCheckMap;
	}




	public Map<String, String> getAicCheckMap() {
		return aicCheckMap;
	}




	public void setAicCheckMap(Map<String, String> aicCheckMap) {
		this.aicCheckMap = aicCheckMap;
	}




	public Map<String, Object> getReqMap() {
		return reqMap;
	}




	public void setReqMap(Map<String, Object> reqMap) {
		this.reqMap = reqMap;
	}


	@Override
	public void run() {
		//账户验真
		/*try{
			Map<String, String> resultMap = iBankCardCheckService.bankCardCheck(bankCheckMap);
			logger.debug("账户验证结果:"+resultMap);
		}catch(Exception e){
			logger.error("账户验真发生异常:" + e.getMessage());
		}
		
		//工商
		try{
			Map<String, String> resultMap = aicHessianService.getCustAICInfo(aicCheckMap);
			logger.debug("调工商结果为:" + resultMap);
		}catch(Exception e){
			logger.error("调工商发生异常:" + e.getMessage());
		}*/
			boolean admCheckBol = true;
			boolean bankCheckBol = true;
			boolean aicCheckBol = true;
			boolean policeCheckBol = true;
			String loanId = (String)reqMap.get("loanid");
			if(StringUtil.isEmpty(loanId)){
				loanId = (String)reqMap.get("loanId");
			}
				try{
					Map<String, Object> result = loanPosCreditApplyCheckRegBiz.creditApplyCheck(loanId);
					logger.debug("准入性校验结果为:" + result.get("checkMessage"));
					if("999".equals(result.get("checkCode"))){
						//不做后续处理
						return;
					}
				}catch(Exception e){
					logger.error("准入性校验异常:", e);
					admCheckBol = false;
				}
				logger.debug(loanId + "准入性校验通过,开始调用账户验真接口");			

			//调用工商，公安，账户验真
			
			//先调公安
			/*if(policeMap != null && policeMap.size() != 0){
				try{
					Map<String, String> policeResMap = iPoliceHessianService.getCustPoliceInfo(policeMap);
					logger.debug("调公安返回结果为:" + policeResMap);
				}catch(Exception e){
					logger.error("调公安发生异常", e);
					policeCheckBol = false;
				}
				
			}*/
		//	logger.debug("调公安完成");

			
			//账户验真
			if(bankCheckMap != null && bankCheckMap.size() != 0){
				try{
					Map<String, String> resultMap = iBankCardCheckService.bankCardCheck(bankCheckMap);
					logger.debug("返回结果为：" + resultMap);
					if("99".equals(resultMap.get("respCode"))){
						bankCheckBol = false;
					}
				}catch(Exception e){
					logger.error("账户验真发生异常", e);
					bankCheckBol = false;
				}
				
			}
			/*logger.debug("账户验真完成，接下来调用工商接口");
			//
			try{
				Map<String, String> resultMap = aicHessianService.getCustAICInfo(aicCheckMap);
				logger.debug("调用工商结果为:" + resultMap);
			}catch(Exception e){
				logger.error(loanId + "调用工商异常", e);
				aicCheckBol = false;
			}*/
			try{
				boolean uploadImg = iLoanPosCreditApplyBackStageBiz.getUmImg(reqMap);
				if(!(uploadImg && bankCheckBol && aicCheckBol && admCheckBol && policeCheckBol)){
					Map<String, Object> statusMap = Maps.newHashMap();
					statusMap.put("loanId", reqMap.get("loanid"));
					statusMap.put("applyStatus", "10");
					loanPosCreditApplyService.updateApplyStatus(statusMap);
				}						
			}catch(Exception e){
				logger.error("调影像资料下载异常:", e);
				Map<String, Object> statusMap = Maps.newHashMap();
				statusMap.put("loanId", reqMap.get("loanid"));
				statusMap.put("applyStatus", "10");
				loanPosCreditApplyService.updateApplyStatus(statusMap);
			}

		}

}
