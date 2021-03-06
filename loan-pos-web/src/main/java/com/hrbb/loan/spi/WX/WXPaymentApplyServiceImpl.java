package com.hrbb.loan.spi.WX;

import java.util.Date;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Executor;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.hrbb.loan.acct.facade2.MadeLoanProcessQueryBankAcctBalFacade;
import com.hrbb.loan.constants.PaymentApplyConstants;
import com.hrbb.loan.pos.biz.backstage.inter.CautionConfigBiz;
import com.hrbb.loan.pos.biz.backstage.inter.IMadeLoanAcctBiz;
import com.hrbb.loan.pos.biz.backstage.inter.IPaymentApplyBiz;
import com.hrbb.loan.pos.biz.backstage.inter.IReceiptManageBiz;
import com.hrbb.loan.pos.biz.backstage.inter.LoanPosContractManagementBiz;
import com.hrbb.loan.pos.dao.TBankAccnoInfoDao;
import com.hrbb.loan.pos.dao.TCfgFundingPoolDao;
import com.hrbb.loan.pos.dao.TMessageDao;
import com.hrbb.loan.pos.dao.TPaymentApplyDao;
import com.hrbb.loan.pos.dao.TReceiptInfoDao;
import com.hrbb.loan.pos.dao.entity.TBankAccnoInfo;
import com.hrbb.loan.pos.dao.entity.TContractManagement;
import com.hrbb.loan.pos.dao.entity.TReceiptInfo;
import com.hrbb.loan.pos.service.BankAccnoInfoService;
import com.hrbb.loan.pos.service.LoanPosCustomerService;
import com.hrbb.loan.pos.util.DateUtil;
import com.hrbb.loan.pos.util.ExecutorUtil;
import com.hrbb.loan.pos.util.StringUtil;
import com.hrbb.loan.pos.util.ValidateUtil;
import com.hrbb.loan.spiconstants.HServiceReturnCode;
import com.hrbb.loan.spiconstants.PaymentApplyHServiceConstants;
import com.hrbb.loan.thread.PayExtThread;
import com.hrbb.sh.framework.HRequest;
import com.hrbb.sh.framework.HResponse;
import com.hrbb.sh.framework.HServiceException;

@Service("wxPaymentApplyService")
public class WXPaymentApplyServiceImpl extends WXHService{
	
	private static final Object ISSUE_ID = "issueid";

	@Autowired
	private IPaymentApplyBiz paymentApplyBiz;

	@Autowired
	private LoanPosContractManagementBiz contBiz;
	
	@Autowired
	private LoanPosCustomerService loanPosCustomerService;

	@Autowired
	@Qualifier("tBankAccnoInfoDao")
	private TBankAccnoInfoDao tBankAccnoInfoDao;

	@Autowired
	private TMessageDao tMessageDao;

	@Autowired
	@Qualifier("tCfgFundingPoolDao")
	private TCfgFundingPoolDao tCfgFundingPoolDao;

	@Autowired
	private TPaymentApplyDao tPaymentApplyDao;
	
	@Autowired
	private CautionConfigBiz cautionConfigBiz;

	@Autowired
	private TReceiptInfoDao tReceiptInfoDao;
	
	@Autowired
	private BankAccnoInfoService bankAccnoInfoService;
	
	@Autowired
    IReceiptManageBiz receiprManageBiz;
	
	@Autowired
	private PayExtThread payExtThread;
	
	@Autowired
	private TReceiptInfoDao receiptInfoDao;
	
	 @Autowired
	 private LoanPosContractManagementBiz loanPosContractManagementBiz;

	@Autowired
	private MadeLoanProcessQueryBankAcctBalFacade queryBankAcctBal;
	
	@Autowired
    IMadeLoanAcctBiz madeLoanAcctBiz;
	
	
	Executor executor = null;
	
	@PostConstruct
	private void initExecutor(){
		executor = ExecutorUtil.getScheduledThreadExecutor(10);
	}

	@Override
	public HResponse serve(HRequest request) throws HServiceException {
		HResponse resp = new HResponse();
		try{

		Map<String, Object> props = request.getProperties();
		Map<String, Object> reqMap = Maps.newHashMap();
		/*String scheduleTimes = (String)props.get("scheduletimes");
		if(StringUtil.isEmpty(scheduleTimes) || !ValidateUtil.checkInteger(scheduleTimes)){
			resp.setRespCode(HServiceReturnCode.SCHEDULETIMES_ERROR.getReturnCode());
			resp.setRespMessage(HServiceReturnCode.SCHEDULETIMES_ERROR.getReturnMessage());
			return getBlankResponse(resp);
		}*/
		//用款申请流水号（合作机构）
		String stdisno = (String)props.get(PaymentApplyHServiceConstants.stdisno);
		if(StringUtil.isEmpty(stdisno)){
			resp.setRespCode(HServiceReturnCode.STDISNO_ERROR.getReturnCode());
			resp.setRespMessage(HServiceReturnCode.STDISNO_ERROR.getReturnMessage());
			resp.setRespTime(new Date());
			return getBlankResponse(resp);
		}
		reqMap.put(PaymentApplyConstants.stdisno, stdisno);
		
		String contNo = (String)props.get(PaymentApplyHServiceConstants.contno);
		if(StringUtil.isEmpty(contNo)){
			resp.setRespCode(HServiceReturnCode.CONTNO_ERROR.getReturnCode());
			resp.setRespMessage(HServiceReturnCode.CONTNO_ERROR.getReturnMessage());
			resp.setRespTime(new Date());
			return getBlankResponse(resp);
		}
		reqMap.put("channelType", this.getChannel());
		reqMap.put(PaymentApplyConstants.contNo, contNo);;
		//用款额度
		String payApplyAmt = (String)props.get(PaymentApplyHServiceConstants.tcapi);
		if(StringUtil.isEmpty(payApplyAmt) || !(ValidateUtil.checkInteger(payApplyAmt) || ValidateUtil.checkDouble(payApplyAmt))){
			resp.setRespCode(HServiceReturnCode.TCAPI_ERROR.getReturnCode());
			resp.setRespMessage(HServiceReturnCode.TCAPI_ERROR.getReturnMessage());
			resp.setRespTime(new Date());
			return getBlankResponse(resp);
		}
		reqMap.put(PaymentApplyConstants.payApplyAmt, payApplyAmt);
		//用款期限
		String payApplyTerm = (String)props.get(PaymentApplyHServiceConstants.tterm);
		if(StringUtil.isEmpty(payApplyTerm) || !ValidateUtil.checkInteger(payApplyTerm)){
			resp.setRespCode(HServiceReturnCode.TTERM_ERROR.getReturnCode());
			resp.setRespMessage(HServiceReturnCode.TTERM_ERROR.getReturnMessage());
			resp.setRespTime(new Date());
			return getBlankResponse(resp);
		}
		if(payApplyTerm.startsWith("0")){
			payApplyTerm = payApplyTerm.replaceFirst("0", "");
		}
		reqMap.put(PaymentApplyConstants.payApplyTerm, payApplyTerm);
		/*  期限单位
		String termunit = (String)props.get(PaymentApplyHServiceConstants.termunit);
		if(StringUtil.isEmpty(termunit)){
			resp.setRespCode(HServiceReturnCode.TERMUNIT_ERROR.getReturnCode());
			resp.setRespMessage(HServiceReturnCode.TERMUNIT_ERROR.getReturnMessage());
			resp.setRespTime(new Date());
			return getBlankResponse(resp);
		}*/
		//用款利率
		String inteRate = (String)props.get("interate");
		if(StringUtil.isEmpty(inteRate)){
			resp.setRespCode(HServiceReturnCode.INTERATE_ERROR.getReturnCode());
			resp.setRespMessage(HServiceReturnCode.INTERATE_ERROR.getReturnMessage());
			return getBlankResponse(resp);
		}
		reqMap.put("payApplyInterest", inteRate);
		//还款方式
		String returnType = (String)props.get(PaymentApplyHServiceConstants.retukind);
		if(StringUtil.isEmpty(returnType)){
			resp.setRespCode(HServiceReturnCode.RETUKIND_ERROR.getReturnCode());
			resp.setRespMessage(HServiceReturnCode.RETUKIND_ERROR.getReturnMessage());
			resp.setRespTime(new Date());
			return getBlankResponse(resp);
		}
		reqMap.put(PaymentApplyConstants.returnType, returnType);
		
		reqMap.put("payChannel", "2");
		/*   放款账号
		String accNo	= (String)props.get(PaymentApplyHServiceConstants.INDESUBSACNO);
		if(StringUtil.isEmpty(accNo)){
			resp.setRespCode(HServiceReturnCode.INDESUBSACNO_ERROR.getReturnCode());
			resp.setRespMessage(HServiceReturnCode.INDESUBSACNO_ERROR.getReturnMessage());
			resp.setRespTime(new Date());
			return getBlankResponse(resp);
		}*/
		String accNo = (String)props.get(PaymentApplyHServiceConstants.loanbankacno);
		reqMap.put(PaymentApplyConstants.accNo, accNo);
		//银行账户开户行
		String loanbankname = (String)props.get(PaymentApplyHServiceConstants.loanbankname); 
		if(StringUtil.isEmpty(loanbankname)){
			resp.setRespCode(HServiceReturnCode.BANKNAME_ERROR.getReturnCode());
			resp.setRespMessage(HServiceReturnCode.BANKNAME_ERROR.getReturnMessage());
			resp.setRespTime(new Date());
			return getBlankResponse(resp);
		}
		reqMap.put("bankName", loanbankname);
		reqMap.put("loanbankname", loanbankname);
		//
		Date expectDateStr = new Date();
		try{
			//到期日
			reqMap.put(PaymentApplyConstants.expectedEndDate, DateUtil.getDatePattern3(DateUtil.getRelativeDate(expectDateStr, 0, Integer.valueOf(payApplyTerm), 0)));
			reqMap.put(PaymentApplyConstants.expectedDate, expectDateStr);
		}catch(Exception e){
			logger.error("日期转换异常[]", e.getMessage());
		}
		//申请日期
		String applyDate = (String)props.get(PaymentApplyHServiceConstants.begindate);
		if(StringUtil.isEmpty(applyDate)){
			resp.setRespCode(HServiceReturnCode.BEGINDATE_ERROR.getReturnCode());
			resp.setRespMessage(HServiceReturnCode.BEGINDATE_ERROR.getReturnMessage());
			resp.setRespTime(new Date());
			return getBlankResponse(resp);
		}
		try{
			reqMap.put(PaymentApplyConstants.applyDate, DateUtil.getStrToDate1(applyDate));
		}catch(Exception e){
			logger.error("日期转换异常[]", e.getMessage());
		}
		reqMap.put("status", "10");

		Map<String, Object> respMap = paymentApplyBiz.insertPaymentApply(reqMap);
		if(respMap.isEmpty() || respMap.get(ISSUE_ID) == null){
			resp.setRespCode(HServiceReturnCode.OTHER_ERROR.getReturnCode());
			resp.setRespMessage(HServiceReturnCode.OTHER_ERROR.getReturnMessage());
			return getBlankResponse(resp);
		}
		logger.debug("用款申请记录入库成功。。。");
		
		String payApplyId = (String) respMap.get("issueid");
		reqMap.put("payApplyId", payApplyId);
		
		TContractManagement contract = loanPosContractManagementBiz.getContractByContNo(contNo);
		if(contract == null){
			resp.setRespCode(HServiceReturnCode.HASNO_CONT.getReturnCode());
			resp.setRespMessage(HServiceReturnCode.HASNO_CONT.getReturnMessage());
			resp.setRespTime(new Date());
			return resp;
		}
		
		//银行卡信息入库
				TBankAccnoInfo bankInfo = new TBankAccnoInfo();
				//先查询，没有的话就插入新记录
				TBankAccnoInfo queryRes = loanPosCustomerService.getBanAccnoById(accNo);
				if(queryRes == null){
					logger.debug(accNo+"没有该银行卡信息");
					bankInfo.setBankAccno(accNo);
					bankInfo.setBankName(loanbankname);
					bankInfo.setCustId(contract.getCustId());
					bankInfo.setStatus("03");
					bankAccnoInfoService.insertSelective(bankInfo);
					logger.debug("银行卡信息入库成功");
				}else{
					logger.debug(accNo+"有该银行卡信息");
				}
		
		resp.setProperties(respMap);
		resp.setRespCode(HServiceReturnCode.SUCCESS.getReturnCode());
		resp.setRespMessage(HServiceReturnCode.SUCCESS.getReturnMessage());
		resp.setRespTime(new Date());
		//新起线程完成放款，如果已经有借据，则不起线程
		TReceiptInfo receiptInfo = receiptInfoDao.selectByPayApplyId(payApplyId);
		logger.debug("receiptInfo:" + receiptInfo != null ? receiptInfo.getReceiptId() : "没有该借据");
		if(receiptInfo == null){
			payExtThread.setReqMap(reqMap);
			payExtThread.setContNo(contNo);
			payExtThread.setPayApplyTerm(payApplyTerm);
			payExtThread.setExpectDateStr(expectDateStr);
			payExtThread.setRespMap(respMap);
			payExtThread.setPayApplyId(payApplyId);
			payExtThread.setChannel(getChannel());
			payExtThread.setInChannelKind(getInChannelKind());
			executor.execute(payExtThread);
		}
		return resp;
		
	}catch(Exception e){
		logger.error("萨摩耶发生异常:", e);
		return resp;
	}		
               
		
	}

	
	
	private HResponse getBlankResponse(HResponse resp){
		Map<String, Object> reqMap = Maps.newHashMap();
		reqMap.put(PaymentApplyConstants.ISSUE_ID, "");
		resp.setProperties(reqMap);
		return resp;
	}
	


}
