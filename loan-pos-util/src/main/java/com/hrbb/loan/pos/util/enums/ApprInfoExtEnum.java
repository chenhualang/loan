/**
 * 
 *	哈尔滨银行
 * Copyright (c) 2007-2015 HRBB,Inc.All Rights Reserved.
 */
package com.hrbb.loan.pos.util.enums;

/**
 * 审批意见外部
 * 
 * @author marco
 * @version $Id: ApprInfoExtEnum.java, v 0.1 2015-7-28 下午6:18:44 marco Exp $
 */
public enum ApprInfoExtEnum {

	APPRINFOEXT_01("01", "贷记卡现逾期"),
	APPRINFOEXT_02("02", "贷款现逾期"),
	APPRINFOEXT_03("03", "贷记卡历史逾期次数多"),
	APPRINFOEXT_04("04", "贷款历史逾期次数多"),
	APPRINFOEXT_05("05", "综合情况不合哈行准入"),
	APPRINFOEXT_06("06", "申请人信用记录少"),
	APPRINFOEXT_08("08", "综合情况不合哈行准入(自动)"),
	APPRINFOEXT_91("91", "补件超时效拒绝"),
	;

	private String value = "";

	private String description = null;

	ApprInfoExtEnum(String value, String desc) {
		this.value = value;
		this.description = desc;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
