package com.hrbb.loan.spi.UM;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Maps;
import com.hrbb.loan.pos.dao.entity.TContractManagement;
import com.hrbb.loan.pos.dao.entity.TCustomer;
import com.hrbb.loan.pos.service.LoanPosContractManagementService;
import com.hrbb.loan.pos.service.LoanPosCreditApplyService;
import com.hrbb.loan.pos.service.LoanPosCustomerService;
import com.hrbb.loan.pos.service.LoanPosPaybackService;
import com.hrbb.loan.pos.service.PaymentApplyService;
import com.hrbb.loan.spiconstants.HServiceReturnCode;
import com.hrbb.sh.framework.HRequest;
import com.hrbb.sh.framework.HResponse;
import com.hrbb.sh.framework.HService;
import com.hrbb.sh.framework.HServiceException;

@Service("umContractQuery")
public class UMContractQueryHserviceImpl implements HService {
	Logger logger = LoggerFactory.getLogger(UMContractQueryHserviceImpl.class);
	@Autowired
	private LoanPosCreditApplyService loanPosCreditApplyService;
	@Autowired
	private LoanPosContractManagementService loanPosContractManagementService;
	@Autowired
	private LoanPosCustomerService loanPosCustomerService;
	
	@Autowired
	private LoanPosPaybackService loanPosPaybackService;

	@Autowired
	private PaymentApplyService paymentApplyService;
	
	@Override
	public HResponse serve(HRequest request) throws HServiceException {
		logger.debug("executing ContractQueryHserviceImpl...");
		HResponse response = new HResponse();
		Map<String, Object> propMap = request.getProperties();
		Object ssno = propMap.get("stdshno");
		Object smno = propMap.get("stdmerno");
		Object ctno = propMap.get("contno");
		SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd");
		
		
		
       if(ctno!=null){
    	    try{
    	    	 String contno = ctno.toString();
    		    	logger.info("合同号为" + contno);
    		    	
    		    	
    				TContractManagement cont = loanPosContractManagementService.getContractInfoByContNo(contno);
    		        BigDecimal totalPayApplyAmt = paymentApplyService.getPaymentApplyInfocontno(contno);
    		        BigDecimal zb = new BigDecimal("0.00");
    		        BigDecimal ta =  totalPayApplyAmt==null?zb:totalPayApplyAmt;
    				String ct = loanPosPaybackService.getReceiptTotalAmountByContNo(contno)==null?"0":loanPosPaybackService.getReceiptTotalAmountByContNo(contno);
    				BigDecimal bd = new BigDecimal(ct);
    		    	BigDecimal rt = cont.getContTotalAmt();
    		    	BigDecimal amt1 = rt.subtract(bd);
    		    	BigDecimal conttouseamt = amt1.subtract(ta);
    		    	
    				String custId = cont.getCustId();
    		   	    logger.info("客户编号为" + custId);
    		   	    TCustomer cust = loanPosCustomerService.getCustumerInfoById(custId);
    	        	Calendar a = Calendar.getInstance();
    	    		Date b = a.getTime();
    	    		double c = Double.parseDouble(cont.getCreditInterest());
    	    		double d = c/100;
    	    		String e = String.valueOf(d);
    	    		HashMap<String, Object> map = new HashMap<String, Object>(32);
    	    		map.put("custid",cont.getCustId());
    	    		map.put("custname", cont.getCustName());
    	    		map.put("paperkind", cust.getPaperKind());
    	    		map.put("paperid", cust.getPaperId());
    	    		map.put("loanid", cont.getLoanId());
    	    		map.put("contno", cont.getContNo());
    	    		map.put("cncontno", cont.getCnContNo());
    	    		map.put("begindate",sft.format(cont.getBeginDate()));
    	    		map.put("enddate",sft.format(cont.getEndDate()));
    	    		map.put("conttotalamt", cont.getContTotalAmt().toString());
    	    		map.put("conttouseamt", conttouseamt.toString());
    	    		map.put("interate", e);
    	    		map.put("retukind",cont.getPaybackMethod());
    	    		map.put("DEPESUBSACNO", cont.getAcceptAccount());
    	    		map.put("contstatus", cont.getAgreementStatus());
    	    		map.put("refreshdate",sft.format(b));
    	    		map.put("adjustreason",cont.getEditPerson());
    	    		map.put("stdshno", propMap.get("stdshno").toString());
    	    		map.put("stdmerno", propMap.get("stdmerno").toString());
    	    		response.setRespCode(HServiceReturnCode.SUCCESS.getReturnCode());
    	    		response.setRespMessage(HServiceReturnCode.SUCCESS.getReturnMessage());
    	    		response.setRespTime(new Date());
    	    		response.setProperties(map);
    	    		return response;
    	    }catch(Exception e){
    	    	
    	    	logger.error("协议查询出错",e);
    	    	response.setRespCode(HServiceReturnCode.POS_CONTRACT_QUERY_ERROR.getReturnCode());
    			response.setRespMessage(HServiceReturnCode.POS_CONTRACT_QUERY_ERROR
    						.getReturnMessage());
    			response.setRespTime(new Date());
    			return getBlankHResponse(response);
    	    	
    	    }
    	   
        }else if(ctno==null&&ssno!=null){
        	try{
        		String stdshno = ssno.toString();
            	logger.info("银联商务申请流水号为"+stdshno);
        		String loanId = loanPosCreditApplyService.getLoanIdbyStdshno(stdshno);
        		logger.info("申请编号为："+loanId);
        		TContractManagement cont1 =loanPosContractManagementService.getContractInfoByLoanId(loanId);
        		String custId1 = cont1.getCustId();
        		logger.info("客户编号为："+custId1);
        		TCustomer cust1 = loanPosCustomerService.getCustumerInfoById(custId1);
        		if (cust1 == null) {
        			logger.info("客户信息为空", cust1);
        		}
        		
        	    String contno = cont1.getContNo();
        	    logger.info("合同编号为"+contno);
    			String ct = loanPosPaybackService.getReceiptTotalAmountByContNo(contno)==null?"0":loanPosPaybackService.getReceiptTotalAmountByContNo(contno);
    			BigDecimal totalPayApplyAmt = paymentApplyService.getPaymentApplyInfocontno(contno);
  		        BigDecimal zb = new BigDecimal("0.00");
  		        BigDecimal ta =  totalPayApplyAmt==null?zb:totalPayApplyAmt;
    			BigDecimal bd = new BigDecimal(ct);
    	    	BigDecimal rt = cont1.getContTotalAmt();
    	    	BigDecimal amt1 = rt.subtract(bd);
    	    	BigDecimal conttouseamt = amt1.subtract(ta);
    	    	
    	    	
    	    	
    	    	
            	Calendar a = Calendar.getInstance();
        		Date b = a.getTime();
        		double c = Double.parseDouble(cont1.getCreditInterest());
	    		double d = c/100;
	    		String e = String.valueOf(d);
        		HashMap<String, Object> map = new HashMap<String, Object>(32);
        		map.put("custid",cont1.getCustId());
        		map.put("custname", cont1.getCustName());
        		map.put("paperkind", cust1.getPaperKind());
        		map.put("paperid", cust1.getPaperId());
        		map.put("loanid", cont1.getLoanId());
        		map.put("contno", cont1.getContNo());
        		map.put("cncontno", cont1.getCnContNo());
        		map.put("begindate",sft.format(cont1.getBeginDate()));
        		map.put("enddate",sft.format(cont1.getEndDate()));
        		map.put("conttotalamt", cont1.getContTotalAmt().toString());
        		map.put("conttouseamt", conttouseamt.toString());
        		map.put("interate", e);
        		map.put("retukind",cont1.getPaybackMethod());
        		map.put("DEPESUBSACNO", cont1.getAcceptAccount());
        		map.put("contstatus", cont1.getAgreementStatus());
        		map.put("adjustreason",cont1.getEditPerson());
        		map.put("refreshdate",sft.format(b));
        		map.put("stdshno", stdshno);
        		map.put("stdmerno", propMap.get("stdmerno").toString());
        		response.setRespCode(HServiceReturnCode.SUCCESS.getReturnCode());
        		response.setRespMessage(HServiceReturnCode.SUCCESS.getReturnMessage());
        		response.setRespTime(new Date());
        		response.setProperties(map);
        		return response;
        		
        	}catch(Exception e){
        		logger.error("协议查询出错",e);
        		response.setRespCode(HServiceReturnCode.POS_CONTRACT_QUERY_ERROR.getReturnCode());
      			response.setRespMessage(HServiceReturnCode.POS_CONTRACT_QUERY_ERROR
      					.getReturnMessage());
      			response.setRespTime(new Date());
      			return getBlankHResponse(response);
        		
        	}
        
       }else{
    	    response.setRespCode(HServiceReturnCode.POS_CONTRACT_QUERY_ERROR.getReturnCode());
			response.setRespMessage(HServiceReturnCode.POS_CONTRACT_QUERY_ERROR
					.getReturnMessage());
			response.setRespTime(new Date());
			return getBlankHResponse(response);
       }
		
	}

	
	

	private HResponse getBlankHResponse(HResponse response){
		Map<String, Object> respMap = Maps.newHashMap();
		respMap.put("custid", "");
		respMap.put("custname", "");
		respMap.put("paperkind", "");
		respMap.put("paperid", "");
		respMap.put("loanid", "");
		respMap.put("contno", "");
		respMap.put("cncontno", "");
		respMap.put("begindate", "");
		respMap.put("enddate", "");
		respMap.put("conttotalamt", "");
		respMap.put("conttouseamt", "");
		respMap.put("interate", "");
		respMap.put("retukind", "");
		respMap.put("DEPESUBSACNO", "");
		respMap.put("contstatus", "");
		respMap.put("adjustreason", "");
		respMap.put("refreshdate", "");
		respMap.put("stdshno", "");
		respMap.put("stdmerno", "");
		response.setProperties(respMap);
		return response;
	}
}
