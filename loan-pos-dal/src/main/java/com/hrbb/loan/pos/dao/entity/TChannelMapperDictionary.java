package com.hrbb.loan.pos.dao.entity;

import java.math.BigDecimal;

public class TChannelMapperDictionary {
    private Integer id;

    private String type;

    private String channel;

    private String innerCode;

    private String outerCode;

    private BigDecimal dailyLimit;
    
    private BigDecimal singleLimit;
    
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public String getInnerCode() {
        return innerCode;
    }

    public void setInnerCode(String innerCode) {
        this.innerCode = innerCode == null ? null : innerCode.trim();
    }

    public String getOuterCode() {
        return outerCode;
    }

    public void setOuterCode(String outerCode) {
        this.outerCode = outerCode == null ? null : outerCode.trim();
    }
    public BigDecimal getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(BigDecimal dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public BigDecimal getSingleLimit() {
        return singleLimit;
    }

    public void setSingleLimit(BigDecimal singleLimit) {
        this.singleLimit = singleLimit;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}