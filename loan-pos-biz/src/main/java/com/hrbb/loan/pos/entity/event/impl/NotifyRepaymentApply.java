/**
 * 
 */
package com.hrbb.loan.pos.entity.event.impl;

import java.util.Map;

import com.google.common.collect.Maps;
import com.hrbb.loan.pos.dao.entity.TContractManagement;
import com.hrbb.loan.pos.dao.entity.TPaybackApplyInfo;
import com.hrbb.loan.pos.dao.entity.TReceiptInfo;
import com.hrbb.loan.pos.entity.SpringBeans;
import com.hrbb.loan.pos.service.LoanPosBusinessCodeService;
import com.hrbb.loan.pos.service.LoanPosContractManagementService;
import com.hrbb.loan.pos.service.LoanPosPaybacApplykService;
import com.hrbb.loan.pos.service.LoanPosPaybackService;
import com.hrbb.sh.framework.util.DateUtil;

/**
 * <p>Title: NotifyRepaymentApply.java </p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (C) 2015 Hrbb Ltd. Co.</p> 
 *     
 * @author linzhaolin@hrbb.com.cn
 * @version 
 * @date Sep 5, 2015
 *
 * logs: 1. 
 */
public class NotifyRepaymentApply extends AbsMailNotifyEvent {
	
	private String repaymentId;
	
	private LoanPosPaybacApplykService loanPosPaybacApplykService;
	
	private LoanPosPaybackService loanPosPaybackService;
	
	private LoanPosBusinessCodeService loanPosBusinessCodeService;
	
	private LoanPosContractManagementService loanPosContractManagementService;
	
	private TPaybackApplyInfo apply;
	
	private TReceiptInfo receipt;
	
	private TContractManagement contract;
	
	private String channleName;
	
	public NotifyRepaymentApply(String repaymentId){
		this.repaymentId = repaymentId;
		/*get service bean*/
		loanPosPaybacApplykService = (LoanPosPaybacApplykService)SpringBeans.getBean("loanPosPaybackApplyService");
		loanPosPaybackService = (LoanPosPaybackService)SpringBeans.getBean("loanPosPaybackService");
		loanPosBusinessCodeService = (LoanPosBusinessCodeService)SpringBeans.getBean("loanPosBusinessCodeService");
		loanPosContractManagementService = (LoanPosContractManagementService)SpringBeans.getBean("loanPosContractManagementService");
		
		/*get payback apply*/
		apply = loanPosPaybacApplykService.selectByPrimaryKey(repaymentId);
		receipt = loanPosPaybackService.getReceiptInfoByReceiptId(apply.getReceiptId());
		contract = loanPosContractManagementService.getContractInfoByContNo(receipt.getContNo());
		
		channleName = loanPosBusinessCodeService.getItemNameByNo("BizChannel", contract.getChannel());
	}

	public String getRepaymentId() {
		return repaymentId;
	}
	
	@Override
	public String getSubject() {
		return "[还款申请受理] "+channleName+" 渠道客户 "+receipt.getCustName()+" 申请还款 "+apply.getPaybackAmount();
	}

	@Override
	public String getMailTo() {
		// TODO 根据角色确定收件人--待完成
		return "linzhaolin@hrbb.com.cn;shenye@hrbb.com.cn";
	}

	@Override
	public String getMailCc() {
		// TODO 根据角色确定收件人--待完成
		return "machicheng@hrbb.com.cn";
	}

	@Override
	public String composeMail() {
		Map<String,Object> vars = Maps.newHashMap();
		vars.put("userName", "小伙伴");
		vars.put("channelName", channleName);
		vars.put("custName", contract.getCustName());
		vars.put("posCustName", contract.getPosCustName());
		vars.put("applyAmt", apply.getPaybackAmount());
		vars.put("beginDate", DateUtil.formatDate(apply.getCreateTime(), DateUtil.HRRB_LONG_DATETIME_FORMAT));
		
		return getMailText(vars, getTemplateId());
	}

	@Override
	public String appendAttachments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTemplateId() {
		return "notify/mail/repaymentApply.ftl";
	}

}
