package com.hrbb.loan.pos.biz.backstage.inter.impl.withholder;

import java.util.List;
import java.util.Map;

import com.hrbb.loan.pos.dao.entity.TPaybackApplyInfo;

public interface IWithholderService {
	
	public Map<String, Object> withholder(TPaybackApplyInfo apply);
}
