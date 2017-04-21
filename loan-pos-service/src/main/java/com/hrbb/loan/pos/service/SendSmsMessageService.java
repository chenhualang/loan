package com.hrbb.loan.pos.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hrbb.loan.pos.dao.entity.TSmsMessage;


public interface SendSmsMessageService {
	
	
	public void insertSmsMessage(String tempId, String mobile, String sendContent);
	
	public List<Map<String, Object>> selectAllMessage();
	
	public void insertSmsMessageHist(String tempId, String mobile, String sendContent);
	
	public void deleteSmsMessage(Integer id);
	
	void batchInsertSmsMessage(List<TSmsMessage> messages);
	
	public void insertSmsMessage(String tempId, String mobile, String sendContent,String channel,String notifyType);

	/**
	 * 短信发送统一接口
	 * 
	 * @param tSmsMessage
	 */
	public void sendSmsMessage(TSmsMessage tSmsMessage);

    void insertSmsMessage(String tempId, String mobile, String sendContent, String channel,
                          String notifyType, Date schedSendTime);

}
