/**
 * 
 */
package com.hrbb.loan.pos.entity.event.impl;

import java.util.Map;

import com.google.common.collect.Maps;
import com.hrbb.loan.pos.dao.entity.TCreditApply;
import com.hrbb.loan.pos.dao.entity.TCreditApplyAprvInfo;
import com.hrbb.loan.pos.dao.entity.TCreditApplyAprvInfoKey;
import com.hrbb.loan.pos.entity.SpringBeans;
import com.hrbb.loan.pos.service.CreditApplyAprvInfoService;
import com.hrbb.loan.pos.service.LoanPosBusinessCodeService;
import com.hrbb.loan.pos.service.LoanPosCreditApplyService;
import com.hrbb.sh.framework.util.DateUtil;

/**
 * <p>Title: NotifyLevel3Approval.java </p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (C) 2015 Hrbb Ltd. Co.</p> 
 *     
 * @author linzhaolin@hrbb.com.cn
 * @version 
 * @date Sep 5, 2015
 *
 * logs: 1. 
 */
public class NotifyLevel3Approval extends AbsMailNotifyEvent {
	
	private LoanPosCreditApplyService loanPosCreditApplyService;
	
	private LoanPosBusinessCodeService loanPosBusinessCodeService;
	
	private CreditApplyAprvInfoService creditApplyAprvInfoService;
	
	private TCreditApply creditApply;
	
	private TCreditApplyAprvInfo approval;
	
	private String channelName;
	
	public NotifyLevel3Approval(String loanId){
		/*get service bean*/
		loanPosCreditApplyService = (LoanPosCreditApplyService)SpringBeans.getBean("loanPosCreditApplyService");
		loanPosBusinessCodeService = (LoanPosBusinessCodeService)SpringBeans.getBean("loanPosBusinessCodeService");
		creditApplyAprvInfoService = (CreditApplyAprvInfoService)SpringBeans.getBean("creditApplyAprvInfoService");
		
		/*biz object*/
		creditApply = loanPosCreditApplyService.queryCreditApply(loanId);
		
		TCreditApplyAprvInfoKey aprvKey = new TCreditApplyAprvInfoKey();
		aprvKey.setLoanId(loanId);
		approval = creditApplyAprvInfoService.selectLastSubbmitted(aprvKey);
		
		channelName = loanPosBusinessCodeService.getItemNameByNo("BizChannel", creditApply.getChannel());
	}
	
	/* (non-Javadoc)
	 * @see com.hrbb.loan.pos.entity.event.impl.AbsMailNotifyEvent#getSubject()
	 */
	@Override
	public String getSubject() {
		return "[审批任务提醒] "+channelName+"客户 "+creditApply.getCustName()+" 申请金额为"+creditApply.getApplyAmt()+" 的业务申请需要您的审批";
	}

	/* (non-Javadoc)
	 * @see com.hrbb.loan.pos.entity.event.impl.AbsMailNotifyEvent#getMailTo()
	 */
	@Override
	public String getMailTo() {
		return "linzhaolin@hrbb.com.cn";
	}

	/* (non-Javadoc)
	 * @see com.hrbb.loan.pos.entity.event.impl.AbsMailNotifyEvent#getMailCc()
	 */
	@Override
	public String getMailCc() {
		return "shenye@hrbb.com.cn";
	}

	/* (non-Javadoc)
	 * @see com.hrbb.loan.pos.entity.event.impl.AbsMailNotifyEvent#composeMail()
	 */
	@Override
	public String composeMail() {
		Map<String,Object> vars = Maps.newHashMap();
		vars.put("userName", sayHello());
		vars.put("channelName", channelName);
		vars.put("custName", creditApply.getCustName());
		vars.put("posCustName", creditApply.getPosCustName());
		vars.put("applyAmt", creditApply.getApplyAmt());
		vars.put("beginDate", DateUtil.formatDate(creditApply.getBeginDate(), DateUtil.HRRB_LONG_DATETIME_FORMAT));
		vars.put("prvAppover", approval.getApprvId());
		vars.put("prvAprvTime", DateUtil.formatDate(approval.getAppEndTime(), DateUtil.HRRB_LONG_DATETIME_FORMAT));
		
		return getMailText(vars, getTemplateId());
	}

	/* (non-Javadoc)
	 * @see com.hrbb.loan.pos.entity.event.impl.AbsMailNotifyEvent#appendAttachments()
	 */
	@Override
	public String appendAttachments() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.hrbb.loan.pos.entity.event.impl.AbsMailNotifyEvent#getTemplateId()
	 */
	@Override
	public String getTemplateId() {
		return "notify/mail/level3Approval.ftl";
	}

}
