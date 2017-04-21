package com.hrbb.loan.pos.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hrbb.loan.pos.dao.entity.TCfgFundingPool;

public interface TCfgFundingPoolDao {
	int deleteByPrimaryKey(String poolNo);

	int insert(TCfgFundingPool record);

	int insertSelective(TCfgFundingPool record);

	TCfgFundingPool selectByPrimaryKey(String poolNo);

	int updateByPrimaryKeySelective(TCfgFundingPool record);

	int updateByPrimaryKey(TCfgFundingPool record);

	List<TCfgFundingPool> selectSelective(Map<String, Object> p);

	public TCfgFundingPool selectOneSelective(Map<String, Object> p);
	
	public List<Map<String, Object>> selectSelectiveMap(Map<String, Object> reqMap);

	BigDecimal getInterestPriceByChannel(String channel);
}