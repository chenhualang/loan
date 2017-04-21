/**
 * 
 */
package com.hrbb.loan.pos.factory.msgr;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.hrbb.loan.pos.entity.SpringBeans;
import com.hrbb.loan.pos.util.DateUtil;
import com.hrbb.sh.framework.HttpRequestSender;

/**
 * <p>Title: AbsHttpMessener.java </p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (C) 2015 Hrbb Ltd. Co.</p> 
 *     用于通过http协议发送的Message
 * @author linzhaolin@hrbb.com.cn
 * @version 
 * @date Aug 26, 2015
 *
 * logs: 1. 
 */
public abstract class AbsHttpMessener extends AbsMessenger {

	private Map<String, Object> messmap = Maps.newHashMap();
	
//	private HttpRequestSender requestsender;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2157569970874489350L;
	
	protected Map<String, Object> getMessagVars(){
		return messmap;
	}
	
	@Override
	protected boolean initProcess() throws Exception{
		String messageInfo = getMessage().getMessageInfo();
		/**
		 * parse messageinfo to map format for json to convert
		 */
		Map<String, Object> messageInfoMap = null;
		try {
			messageInfoMap = parseToMap(messageInfo);
		} catch (Exception ex) {
			logger.warn("failed to parse messageinfo, empty will be filled in...", ex);
			return false;
		}
		
		logger.info("messageinfo为" + messageInfo);
		
		messmap.put("TransType", this.getTransCode());
		messmap.put("messageinfo", messageInfoMap);
		messmap.put("loanid", getMessage().getLoanId());
		messmap.put("pushdate", DateUtil.getNowTime(DateUtil.HRRB_SHORT_DATETIME_FORMAT_BRIEF));
		messmap.put("stdshno", getMessage().getStdshNo());
		messmap.put("messagetype", getMessage().getMessageType());
		
		if (getMessage().getMessageType().equals("3")) {
			// 补件通知
			messmap.put("messageaddi", getMessage().getMessageAddi());
		}else if (getMessage().getMessageType().matches("(5|7|8|9|13)")) {
			// 放款结果通知
			messmap.put(getListId(), getMessage().getListId());
		} else if (getMessage().getMessageType().matches("(6|12)")) {
			// 协议状态调整通知
			messmap.put("contno",getMessage().getContNo());
		}
		
		return true;
	}

	/* (non-Javadoc)
	 * @see com.hrbb.loan.pos.factory.msgr.AbsMessenger#postProcess()
	 */
	@Override
	protected boolean executeSend() throws Exception{
		/*getService*/
		HttpRequestSender httpRequestSender = (HttpRequestSender)SpringBeans.getBean("httpRequestSender");
//		/*getConfigService*/
//		ConfigService service = SysConfigFactory.getInstance().getService("");
		
		logger.info(">>>>. CHANNEL is : " + getChannel());
		
		try{
			String data = JSON.toJSONString(messmap);
			logger.debug("推送的消息为:" + data);
			logger.debug("往"+getChannel()+"推送的消息地址为:"+getUrl());
			httpRequestSender.sendEncryptedWithCompanion(getUrl(), data, getEncryptedRander());
		}catch(Exception ex){
			logger.error(getChannel()+" 推送消息失败!",ex);
			return false;
		}
		
		logger.info(">>>>>>往"+getChannel()+"推送消息["+getMessage().getId()+"]结束>>>>>");
		
		return true;
	}

}
