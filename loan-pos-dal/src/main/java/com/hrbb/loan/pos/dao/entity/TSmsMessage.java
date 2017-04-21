package com.hrbb.loan.pos.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class TSmsMessage implements Serializable{
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 6128471296670093480L;

	private Integer id;

    private String mobile;

    private String sendContent;

    private Date createTime;
    
    private String tempId;
    
    private String channel;
    
    private String notifyType;
    
    private boolean isRealtime = false;
    
    private Date schedSendTime;
    
    public Date getSchedSendTime() {
		return schedSendTime;
	}

	public void setSchedSendTime(Date schedSendTime) {
		this.schedSendTime = schedSendTime;
	}

	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getSendContent() {
        return sendContent;
    }

    public void setSendContent(String sendContent) {
        this.sendContent = sendContent == null ? null : sendContent.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}

	public boolean isRealtime() {
		return isRealtime;
	}

	public void setRealtime(boolean isRealtime) {
		this.isRealtime = isRealtime;
	}
    
    
}