/**
 * 
 */
package com.hrbb.loan.pos.factory.msgr;

import com.hrbb.loan.pos.factory.SysConfigFactory;

/**
 * <p>Title: HCMessenger.java </p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (C) 2015 Hrbb Ltd. Co.</p> 
 *     
 * @author linzhaolin@hrbb.com.cn
 * @version 
 * @date Aug 26, 2015
 *
 * logs: 1. 
 */
public class HCMessenger extends AbsHttpMessener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1610670827480209627L;
	
	private String messageUrl;
	
	public HCMessenger(){
		messageUrl = SysConfigFactory.getInstance().getService(getChannel()).getPropertyValue("messageUrl");
	}

	/* (non-Javadoc)
	 * @see com.hrbb.loan.pos.factory.msgr.MessengerService#getChannel()
	 */
	@Override
	public String getChannel() {
		return "HC";
	}

	/* (non-Javadoc)
	 * @see com.hrbb.loan.pos.factory.msgr.MessengerService#getEncryptedRander()
	 */
	@Override
	public String getEncryptedRander() {
		return "hc";
	}

	/* (non-Javadoc)
	 * @see com.hrbb.loan.pos.factory.msgr.MessengerService#getTransCode()
	 */
	@Override
	public String getTransCode() {
		return "HCT15";
	}

	/* (non-Javadoc)
	 * @see com.hrbb.loan.pos.factory.msgr.MessengerService#getUrl()
	 */
	@Override
	public String getUrl() {
		return messageUrl;
	}

}
