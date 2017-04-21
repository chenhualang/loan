package com.hrbb.loan.pos.biz.backstage.inter.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import com.hrbb.loan.pos.biz.backstage.inter.ISendMessageBiz;
import com.hrbb.loan.pos.dao.entity.TMessage;
import com.hrbb.loan.pos.factory.msgr.MessengerFactory;
import com.hrbb.loan.pos.factory.msgr.MessengerService;
import com.hrbb.loan.pos.service.LoanPosMessageService;

@Component("sendMessageBiz")
public class SendMessageBizImpl implements ISendMessageBiz {

	private Logger logger = Logger.getLogger(SendMessageBizImpl.class);

	@Autowired
	private LoanPosMessageService loanPosMessageService;

//
//	private HService hServiceum;
//
//	public HService gethServiceum() {
//		return hServiceum;
//	}
//
//	public void sethServiceum(HService hServiceum) {
//		this.hServiceum = hServiceum;
//	}
//
//	@Autowired
//	private HttpRequestSender requestsender;
//	
//	@Value("#{biz[tcmessagesenturl]}")
//	private String tcurl;
//
//	@Value("#{biz[hcmessagesenturl]}")
//	private String hcurl;
//	private Map<String, Object> parseToMap(String message) throws Exception {
//		Assert.notNull(message);
//
//		if (StringUtils.isEmpty(message)) {
//			throw new Exception("unable to take empty parameter");
//		}
//
//		Map<String, Object> properties = new HashMap<String, Object>();
//		String[] tokens = message.split(",");
//		if (tokens == null || tokens.length < 1) {
//			throw new Exception(String.format("invalid token format %s",
//					message));
//		}
//
//		for (String token : tokens) {
//			String subTokens[] = token.split(":");
//			if (subTokens == null || subTokens.length != 2) {
//				throw new Exception(String.format("invalid subToken format %s",
//						token));
//			}
//
//			String key = subTokens[0].trim().replaceAll("\"", "");
//			String value = subTokens[1].trim().replaceAll("\"", "");
//			properties.put(key, value);
//		}
//
//		return properties;
//	}

	@Override
	public void sendMessage() {
		// logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>发送任务启动...");

		try {
			// 判断是否连通？
			List<TMessage> msgList = loanPosMessageService.selectAll();
			logger.info(">>>>.Total number is : " + msgList.size());
			for (TMessage message : msgList) {
//				logger.info("发送消息["+message.getId()+"]开始");
				logger.debug("channel="+message.getChannel()+" |channelKind="+message.getInChannelKind());
				MessengerService messenger = MessengerFactory.getMessenger(message.getChannel(),message.getInChannelKind());
				messenger.addMessage(message);
				//count=5的时候表明已经发送失败了5次，不在发送该消息,最多发送5次
				int m = message.getSendCount();
				if(m<5){
				    if(!messenger.execute()){
	                    logger.error(message.getChannel() +" 渠道消息[id="+message.getId()+"]推送失败!");
	                    int a=m+1;
	                    logger.debug("消息[id="+message.getId()+"]第"+a+"次发送失败!");
	                    Map<String,Object> map = Maps.newHashMap();
	                    map.put("id",message.getId());
	                    map.put("sendCount", a);
	                    int flag = loanPosMessageService.updateCount(map);
	                    if(flag==1){
	                        logger.debug("更新消息发送次数成功");
	                    }
	                } 
				}
				
//				logger.info("消息["+message.getId()+"]推送成功");
				
//				HRequest req = new HRequest();
//				HResponse res = new HResponse();
//				int flag = 0;
//				if ("UM".equals(message.getChannel())) {
//					// header info
//					req.setBizType("HB13");
//				} else if ("58".equals(message.getChannel())) {
//					// header info
//					req.setBizType("58T13");
//				}
//				Date date = new Date();
//				req.setRequestTime(date);
//				StringBuffer buffer = new StringBuffer();
//
//				Map<String, Object> propMap = new HashMap<String, Object>();
//				req.setProperties(propMap);
//				propMap.put("messagetype", message.getMessageType());
//				propMap.put("messageinfo", message.getMessageInfo());
//				propMap.put("messageaddi", message.getMessageAddi());
//				propMap.put("custid", message.getCustId());
//				propMap.put("loanId", message.getLoanId());
//				propMap.put("contno", message.getContNo());
//				if(message.getChannel()!=null && message.getChannel().equals("UM")){
//					propMap.put("listid", message.getListId());
//				}else{
//					propMap.put("issueid", message.getListId());
//				}
//				propMap.put("loanacno", message.getLoanAcNo());
//				String dateStr = DateUtil.getCurrentTimePatterna();
//				propMap.put("pushdate", dateStr);
//				propMap.put("stdshno", message.getStdshNo());
//				propMap.put("stdmerno", message.getStdMerNo());
//
//				propMap.put("srcReqDate", DateUtil.getTodayDatePattern1());
//				propMap.put("srcReqTime", DateUtil.getTodayDatePattern1());
//				propMap.put("srcReqId", "1");
//				propMap.put("channelId", "002");
//
//				buffer.append(message.getMessageType()).append('|')
//						.append(message.getMessageInfo()).append('|')
//						.append(message.getMessageAddi()).append('|')
//						.append(message.getCustId()).append('|')
//						.append(message.getLoanId()).append('|')
//						.append(message.getListId()).append('|')
//						.append(message.getLoanAcNo()).append('|')
//						.append(dateStr).append('|')
//						.append(message.getStdshNo()).append('|')
//						.append(message.getStdMerNo());
//				propMap.put("mac", BankUtil.getMD5(buffer.toString(), "GBK"));
//				
//				
//				Date d = new Date();
//				SimpleDateFormat sdf = new SimpleDateFormat(
//						"yyyyMMddHHmmss");
//				String pd = sdf.format(d);
//				String messageInfo = message.getMessageInfo();
//
//				/**
//				 * parse messageinfo to map format for json to convert
//				 */
//				Map<String, Object> messageInfoMap = new HashMap<String, Object>();
//				try {
//					messageInfoMap = parseToMap(messageInfo);
//				} catch (Exception ex) {
//					logger.warn(
//							"failed to parse messageinfo, empty will be filled in...",
//							ex);
//				}
//
//				logger.info("messageinfo为" + messageInfo);
//				Map<String, Object> messmap = new HashMap<String, Object>();
//				
//				messmap.put("messageinfo", messageInfoMap);
//				messmap.put("loanid", message.getLoanId());
//				messmap.put("pushdate", pd);
//				messmap.put("stdshno", message.getStdshNo());
//
//				// 审核结果通知（通过）
//				if (message.getMessageType().equals("1")) {
//					messmap.put("messagetype", "1");
//				} else if (message.getMessageType().equals("2")) {
//					// 审核结果通知（拒绝）
//					messmap.put("messagetype", "2");
//				} else if (message.getMessageType().equals("3")) {
//					// 补件通知
//					messmap.put("messagetype", "3");
//					messmap.put("messageaddi", message.getMessageAddi());
//				} else if (message.getMessageType().equals("4")) {
//					// 协议签订通知
//					messmap.put("messagetype", "4");
//				} else if (message.getMessageType().equals("5")) {
//					// 放款结果通知
//					messmap.put("messagetype", "5");
//					messmap.put("issueid",message.getListId());
//				} else if (message.getMessageType().equals("6")) {
//					// 协议状态调整通知
//					messmap.put("messagetype", "6");
//					messmap.put("contno",message.getContNo());
//				} else if (message.getMessageType().equals("7")) {
//					// 还款结果通知
//					messmap.put("messagetype", "7");
//				} else if (message.getMessageType().equals("8")) {
//					// 到期还款提醒
//					messmap.put("messagetype", "8");
//				} else if (message.getMessageType().equals("9")) {
//					// 逾期催收通知
//					messmap.put("messagetype", "9");
//				} else{
//					messmap.put("messagetype", message.getMessageType());
//				}
//
//				
//				if ("UM".equals(message.getChannel())
//						&& "02".equals(message.getInChannelKind())) {
//					logger.info("-----进入银商渠道-------");
//					logger.info(">>>>. CHANNEL is : " + message.getChannel());
//					logger.info("hServiceum是"+hServiceum);
//					flag = 1;
//					res = hServiceum.serve(req);
//					logger.info(">>>>>>>>>>>发送结果为:" + res.getProperties());
//					
//					/*logger.info(">>>>>>发送结果:" + res.getRespCode() + "-"
//							+ res.getRespMessage());*/
//				} else if ("58".equals(message.getChannel())
//						&& "02".equals(message.getInChannelKind())) {
//					logger.info("------进入58渠道------");
//					logger.info(">>>>. CHANNEL is : " + message.getChannel());
//					
//					messmap.put("TransType", "58T15");
//					String data = JSON.toJSONString(messmap);
//					logger.info("推送的消息为" + data);
//
//				    requestsender.sendEncryptedWithCompanion(tcurl, data, "58tc");
//					flag = 1;
//					
//					logger.info("往58同城推送的消息地址为"+tcurl);
//					logger.info(">>>>>>往58同城推送消息结束>>>>>");
//
//				}else if ("HC".equals(message.getChannel())
//						&& "02".equals(message.getInChannelKind())){
//
//					logger.info("------进入慧聪渠道------");
//					logger.info(">>>>. CHANNEL is : " + message.getChannel());
//			    		
//					messmap.put("TransType", "HCT15");
//					String data = JSON.toJSONString(messmap);
//					logger.info("推送的消息为" + data);
//
//				    requestsender.sendEncryptedWithCompanion(hcurl, data, "hc");
//					flag = 1;
//					
//					logger.info("往慧聪同城推送的消息地址为"+hcurl);
//					logger.info(">>>>>>往慧聪推送消息结束>>>>>");
//					
//					
//				}
//				if (flag == 1) {
//					TMessageHist tMessageHist = new TMessageHist();
//					tMessageHist.setId(message.getId());
//					tMessageHist.setMessageType(message.getMessageType());
//					tMessageHist.setMessageInfo(message.getMessageInfo());
//					tMessageHist.setMessageAddi(message.getMessageAddi());
//					tMessageHist.setCustId(message.getCustId());
//					tMessageHist.setLoanId(message.getLoanId());
//					tMessageHist.setContNo(message.getContNo());
//					tMessageHist.setListId(message.getListId());
//					tMessageHist.setLoanAcNo(message.getLoanAcNo());
//					tMessageHist.setCreateTime(message.getCreateTime());
//					tMessageHist.setTimerDate(message.getTimerDate());
//					tMessageHist.setStdshNo(message.getStdshNo());
//					tMessageHist.setStdMerNo(message.getStdMerNo());
//					tMessageHist.setChannel(message.getChannel());
//					tMessageHist.setInChannelKind(message.getInChannelKind());
//					loanPosMessageService.insertMessageHist(tMessageHist);
//					loanPosMessageService.deleteById(message.getId());
//					logger.info(">>>>>>发送成功，删除消息：id=" + message.getId());
//				}
//
//				logger.debug(res.getRespMessage());
			} // for循环结束
				// logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>发送任务结束!");

		} catch (Exception e) {
			logger.error(e.getMessage(), (Throwable) e);
			return;
		}

	}
//
//	@Test
//	public void testJson() {
//		String messageInfo = "\"appmaxcred\":\"1\",\"apptterm\":\"3\",\"appttermunit\":\"M\",\"interrate\":\"2\"";
//		messageInfo = String.format("\"messageinfo\":{%s}", messageInfo);
//		logger.debug(messageInfo + "\n");
//
//		Map<String, Object> subProperty = new HashMap<String, Object>();
//		subProperty.put("appmaxcred", "1");
//		subProperty.put("apptterm", "3");
//
//		Map<String, Object> properties = new HashMap<String, Object>();
//		properties.put("TransType", "58T15");
//		// properties.put("messageinfo", new messageinfo());
//		properties.put("messageinfo", subProperty);
//
//		String data = JSON.toJSONString(properties);
//		logger.debug(data);
//	}
//
//	private class messageinfo {
//		private String appmaxcred = "1";
//		private String apptterm = "3";
//
//		public String getApptterm() {
//			return apptterm;
//		}
//
//		public String getAppmaxcred() {
//			return appmaxcred;
//		}
//
//	}

}
