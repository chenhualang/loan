package com.hrbb.loan.pos.dao;

import java.util.Map;

import com.hrbb.loan.pos.dao.entity.TCreditReportOverdue;

public interface TCreditReportOverdueDao {
	int deleteByPrimaryKey(String reportNo);

	int insert(TCreditReportOverdue record);

	int insertSelective(TCreditReportOverdue record);

	TCreditReportOverdue selectByPrimaryKey(String reportNo);

	int updateByPrimaryKeySelective(TCreditReportOverdue record);

	int updateByPrimaryKey(TCreditReportOverdue record);

	public int insertSelectiveMap(Map<String, Object> reqMap);

	public TCreditReportOverdue selectByQueryId(String queryId);
}