package com.hrbb.loan.pos.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.hrbb.loan.pos.dao.TCDCImportDao;
import com.hrbb.loan.pos.dao.PubtrandefDao;
@Repository("tCDCImportDao")
public class TCDCImportDaoImpl implements TCDCImportDao {
	private SqlSessionFactory sqlSessionFactory;
	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	@Override
	public List<Map<String, Object>> getCDCImportListInfo() {
		return sqlSessionFactory.openSession().selectList("payin_batch_pubtrandefMapper.getCDCImportListInfo");
	}

}
