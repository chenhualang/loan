package com.hrbb.loan.pos.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.hrbb.loan.pos.dao.TBizOperationRecordDao;
import com.hrbb.loan.pos.dao.entity.TBizOperationRecord;

@Repository("tbizOperationRecordDao")
public class TBizOperationRecordDaoImpl extends SqlSessionDaoSupport implements TBizOperationRecordDao {

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return getSqlSession().delete("TBizOperationRecordMapper.deleteByPrimaryKey",id);
    }

    @Override
    public int insert(TBizOperationRecord record) {
        return getSqlSession().insert("TBizOperationRecordMapper.insert", record);
    }

    @Override
    public int insertSelective(TBizOperationRecord record) {
        return getSqlSession().insert("TBizOperationRecordMapper.insertSelective", record);
    }

    @Override
    public TBizOperationRecord selectByPrimaryKey(Integer id) {
        return getSqlSession().selectOne("TBizOperationRecordMapper.selectByPrimaryKey", id);
    }

    @Override
    public int updateByPrimaryKeySelective(TBizOperationRecord record) {
        return getSqlSession().update("TBizOperationRecordMapper.updateByPrimaryKeySelective", record);
    }

    @Override
    public int updateByPrimaryKey(TBizOperationRecord record) {
        return getSqlSession().update("TBizOperationRecordMapper.updateByPrimaryKey",record);
    }
    
    @Override
    public int insertSelectiveMapBatch(List<Map<String, Object>> list) {
		return getSqlSession().insert("TBizOperationRecordMapper.insertSelectiveMapBatch", list);
	}

}
