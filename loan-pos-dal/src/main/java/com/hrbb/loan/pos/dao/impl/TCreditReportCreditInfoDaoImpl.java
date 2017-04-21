package com.hrbb.loan.pos.dao.impl;

import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.hrbb.loan.pos.dao.TCreditReportCreditInfoDao;
import com.hrbb.loan.pos.dao.entity.TCreditReportCreditInfo;

@Repository("tCreditReportCreditInfoDao")
public class TCreditReportCreditInfoDaoImpl extends SqlSessionDaoSupport implements  TCreditReportCreditInfoDao{

	@Override
	public int deleteByPrimaryKey(String reportNo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(TCreditReportCreditInfo record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(TCreditReportCreditInfo record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TCreditReportCreditInfo selectByPrimaryKey(String reportNo) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("TCreditReportCreditInfoMapper.selectByPrimaryKey", reportNo);
	}

	@Override
	public int updateByPrimaryKeySelective(TCreditReportCreditInfo record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(TCreditReportCreditInfo record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelectiveMap(Map<String, Object> reqMap) {
		return getSqlSession().insert("TCreditReportCreditInfoMapper.insertSelectiveMap", reqMap);
	}
	
	@Override
	public TCreditReportCreditInfo selectByQueryId(String queryId) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("TCreditReportCreditInfoMapper.selectByQueryId", queryId);
	}
}