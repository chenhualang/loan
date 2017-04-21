/**
 * 
 */
package com.hrbb.loan.pos.factory.handler;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.hrbb.loan.pos.dao.entity.TApplyNotify;
import com.hrbb.loan.pos.entity.SpringBeans;
import com.hrbb.loan.pos.service.TNotifyAssistantService;
import com.hrbb.sh.framework.util.DateUtil;

/**
 * <p>Title: ApprovalNotifyHandler.java </p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (C) 2015 Hrbb Ltd. Co.</p> 
 *     
 * @author linzhaolin@hrbb.com.cn
 * @version 
 * @date Sep 5, 2015
 *
 * logs: 1. 
 */
public class ApprovalNotifyHandler extends AbsMailHandler {
	
	private Date[] range = new Date[2];
	
//	private TNotifyAssistantService tNotifyAssistantService;
	
	/* (non-Javadoc)
	 * @see com.hrbb.loan.pos.factory.handler.IHandler#getName()
	 */
	@Override
	public String getName() {
		return "审批结果阶段性通知";
	}

	/* (non-Javadoc)
	 * @see com.hrbb.loan.pos.factory.handler.AbsMailHandler#initHanler()
	 */
	@Override
	protected boolean initHanler() {
		try {
			String time = DateUtil.getNowTime("HH:mm");
			if(time.compareTo("08:00") > 0 && time.compareTo("10:00") <= 0){	//8~10 统计 20pm-8am
				range[0] = DateUtil.parse2Date(DateUtil.getRelativeDate(DateUtil.getToday(), 0, 0, -1)+" 20:00:00", DateUtil.HRRB_LONG_DATETIME_FORMAT);
				range[1] = DateUtil.parse2Date(DateUtil.getToday()+" 08:00:00", DateUtil.HRRB_LONG_DATETIME_FORMAT);
			}else if(time.compareTo("10:00") > 0 && time.compareTo("12:00") <= 0){
				range[0] = DateUtil.parse2Date(DateUtil.getToday()+" 08:00:00", DateUtil.HRRB_LONG_DATETIME_FORMAT);
				range[1] = DateUtil.parse2Date(DateUtil.getToday()+" 10:00:00", DateUtil.HRRB_LONG_DATETIME_FORMAT);
			}else if(time.compareTo("12:00") > 0 && time.compareTo("14:00") <= 0){
				range[0] = DateUtil.parse2Date(DateUtil.getToday()+" 10:00:00", DateUtil.HRRB_LONG_DATETIME_FORMAT);
				range[1] = DateUtil.parse2Date(DateUtil.getToday()+" 12:00:00", DateUtil.HRRB_LONG_DATETIME_FORMAT);
			}else if(time.compareTo("14:00") > 0 && time.compareTo("16:00") <= 0){
				range[0] = DateUtil.parse2Date(DateUtil.getToday()+" 12:00:00", DateUtil.HRRB_LONG_DATETIME_FORMAT);
				range[1] = DateUtil.parse2Date(DateUtil.getToday()+" 14:00:00", DateUtil.HRRB_LONG_DATETIME_FORMAT);
			}else if(time.compareTo("16:00") > 0 && time.compareTo("18:00") <= 0){
				range[0] = DateUtil.parse2Date(DateUtil.getToday()+" 14:00:00", DateUtil.HRRB_LONG_DATETIME_FORMAT);
				range[1] = DateUtil.parse2Date(DateUtil.getToday()+" 16:00:00", DateUtil.HRRB_LONG_DATETIME_FORMAT);
			}else if(time.compareTo("18:00") > 0 && time.compareTo("20:00") <= 0){
				range[0] = DateUtil.parse2Date(DateUtil.getToday()+" 16:00:00", DateUtil.HRRB_LONG_DATETIME_FORMAT);
				range[1] = DateUtil.parse2Date(DateUtil.getToday()+" 18:00:00", DateUtil.HRRB_LONG_DATETIME_FORMAT);
			}else{
				range[0] = DateUtil.parse2Date(DateUtil.getToday()+" 18:00:00", DateUtil.HRRB_LONG_DATETIME_FORMAT);
				range[1] = DateUtil.parse2Date(DateUtil.getToday()+" 20:00:00", DateUtil.HRRB_LONG_DATETIME_FORMAT);
			}
		} catch (ParseException e) {
			logger.error("初始化每日审批结果通知失败!",e);
			return false;
		}
		
		return true;
	}

	/* (non-Javadoc)
	 * @see com.hrbb.loan.pos.factory.handler.AbsMailHandler#executeHanler()
	 */
//	@Override
//	protected boolean executeHanler() {
//		
//		return true;
//	}
	
	@Override
	protected String groupName() {
		return "approvalNotify";
	}
	
	@Override
	public String getTemplateId() {
		return "notify/mail/timelyAppoval.ftl";
	}

	@Override
	public String getSubject() {
		String subject = "申请审批情况 "+DateUtil.formatDate(range[0], "HH:mm MM-dd")+" ~ "+DateUtil.formatDate(range[1], "HH:mm MM-dd");
		return subject;
	}

	@Override
	public String composeMail() {
		/*get Service*/
		TNotifyAssistantService tNotifyAssistantService = (TNotifyAssistantService)SpringBeans.getBean("tNotifyAssistantService");
		
		Map<String, Object> request = Maps.newHashMap();
		request.put("beginTime", range[0]);
		request.put("endTime", range[1]);
		List<TApplyNotify> records = tNotifyAssistantService.selectTimelyApproval(request);
		
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("userName", "小伙伴们");
		vars.put("today", DateUtil.getToday());
		vars.put("startTime", DateUtil.formatDate(range[0], "HH:mm MM-dd"));
		vars.put("endTime", DateUtil.formatDate(range[1], "HH:mm MM-dd"));
		vars.put("applyList", records);
		vars.put("totalNum", records.size());
		
		return getTemplateText(vars, getTemplateId());
	}

	@Override
	public String appendAttachments() {
		return null;
	}
	

}
