package com.hrbb.loan.pos.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.hrbb.loan.pos.dao.TActivityTypeInfoDao;
import com.hrbb.loan.pos.dao.entity.TActivityTypeInfo;

@Repository("tActivityTypeInfoDao")
public class TActivityTypeInfoDaoImpl extends SqlSessionDaoSupport implements TActivityTypeInfoDao {

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(TActivityTypeInfo record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(TActivityTypeInfo record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TActivityTypeInfo selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(TActivityTypeInfo record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(TActivityTypeInfo record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<TActivityTypeInfo> selectSelective(Map<String, Object> reqMap) {
		return getSqlSession().selectList("TActivityTypeInfoMapper.selectSelective", reqMap);
	}

}
