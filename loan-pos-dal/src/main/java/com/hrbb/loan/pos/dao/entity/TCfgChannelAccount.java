package com.hrbb.loan.pos.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class TCfgChannelAccount implements Serializable{
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -307373160884888839L;

	private Integer id;

    private String channel;

    private String accountPayBack;

    private String accountIssue;

    private Date createDate;

    private String status;

    private Date expiredDate;
    
    private String acctIssueName;
    
    private String acctNamePayBack;

    public String getAcctIssueName() {
		return acctIssueName;
	}

    public void setAcctIssueName(String acctIssueName) {
		this.acctIssueName = acctIssueName;
	}
    
    public String getAcctNamePayBack() {
		return acctNamePayBack;
	}

    public void setAcctNamePayBack(String acctNamePayBack) {
		this.acctNamePayBack = acctNamePayBack;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public String getAccountPayBack() {
        return accountPayBack;
    }

    public void setAccountPayBack(String accountPayBack) {
        this.accountPayBack = accountPayBack == null ? null : accountPayBack.trim();
    }

    public String getAccountIssue() {
        return accountIssue;
    }

    public void setAccountIssue(String accountIssue) {
        this.accountIssue = accountIssue == null ? null : accountIssue.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }
}