/**
 * 
 *	哈尔滨银行
 * Copyright (c) 2007-2015 HRBB,Inc.All Rights Reserved.
 */
package com.hrbb.loan.pos.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.hrbb.loan.pos.dao.TCfgFundingPoolDao;
import com.hrbb.loan.pos.dao.entity.TCfgFundingPool;

/**
 * 
 * @author marco
 * @version $Id: TCfgFundingPoolDaoImpl.java, v 0.1 2015-8-17 下午4:45:10 marco
 *          Exp $
 */
@Repository("tCfgFundingPoolDao")
public class TCfgFundingPoolDaoImpl extends SqlSessionDaoSupport implements
		TCfgFundingPoolDao {

	/**
	 * @see com.hrbb.loan.pos.dao.TCfgFundingPoolDao#deleteByPrimaryKey(java.lang.String)
	 */
	@Override
	public int deleteByPrimaryKey(String poolNo) {
		return 0;
	}

	/**
	 * @see com.hrbb.loan.pos.dao.TCfgFundingPoolDao#insert(com.hrbb.loan.pos.dao.entity.TCfgFundingPool)
	 */
	@Override
	public int insert(TCfgFundingPool record) {
		return 0;
	}

	/**
	 * @see com.hrbb.loan.pos.dao.TCfgFundingPoolDao#insertSelective(com.hrbb.loan.pos.dao.entity.TCfgFundingPool)
	 */
	@Override
	public int insertSelective(TCfgFundingPool record) {
		return 0;
	}

	/**
	 * @see com.hrbb.loan.pos.dao.TCfgFundingPoolDao#selectByPrimaryKey(java.lang.String)
	 */
	@Override
	public TCfgFundingPool selectByPrimaryKey(String poolNo) {
		return null;
	}

	/**
	 * @see com.hrbb.loan.pos.dao.TCfgFundingPoolDao#updateByPrimaryKeySelective(com.hrbb.loan.pos.dao.entity.TCfgFundingPool)
	 */
	@Override
	public int updateByPrimaryKeySelective(TCfgFundingPool record) {
		return 0;
	}

	/**
	 * @see com.hrbb.loan.pos.dao.TCfgFundingPoolDao#updateByPrimaryKey(com.hrbb.loan.pos.dao.entity.TCfgFundingPool)
	 */
	@Override
	public int updateByPrimaryKey(TCfgFundingPool record) {
		return 0;
	}

	/**
	 * @see com.hrbb.loan.pos.dao.TCfgFundingPoolDao#selectSelective(java.util.Map)
	 */
	@Override
	public List<TCfgFundingPool> selectSelective(Map<String, Object> p) {
		return getSqlSession().selectList(
				"TCfgFundingPoolMapper.selectSelective", p);
	}
	
	/**
	 * @see com.hrbb.loan.pos.dao.TCfgFundingPoolDao#selectOneSelective(java.util.Map)
	 */
	@Override
	public TCfgFundingPool selectOneSelective(Map<String, Object> p) {
		return getSqlSession().selectOne(
				"TCfgFundingPoolMapper.selectOneSelective", p);
	}

	@Override
	public List<Map<String, Object>> selectSelectiveMap(
			Map<String, Object> reqMap) {
		return getSqlSession().selectList("TCfgFundingPoolMapper.selectSelectiveMap", reqMap);
	}

	@Override
	public BigDecimal getInterestPriceByChannel(String channel) {
		return getSqlSession().selectOne("TCfgFundingPoolMapper.getInterestPriceByChannel", channel);
	}
}
