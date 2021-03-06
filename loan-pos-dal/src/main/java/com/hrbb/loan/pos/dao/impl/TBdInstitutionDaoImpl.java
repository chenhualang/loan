package com.hrbb.loan.pos.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.hrbb.loan.pos.dao.TBdInstitutionDao;
import com.hrbb.loan.pos.dao.entity.TBdExecutor;
import com.hrbb.loan.pos.dao.entity.TBdInstitution;

/**
 * 
 * @author cjq
 * @version $Id: TBdInstitutionDaoImpl.java, v 0.1 2015年9月10日 下午2:32:32 cjq Exp $
 */
@Repository("tBdInstitutionDao")
public class TBdInstitutionDaoImpl extends SqlSessionDaoSupport implements TBdInstitutionDao {

    @Override
    public int deleteByPrimaryKey(Integer orgId) {
        return 0;
    }

    @Override
    public int insert(TBdInstitution record) {
        return 0;
    }

    @Override
    public int insertSelective(TBdInstitution record) {
        return 0;
    }

    @Override
    public TBdInstitution selectByPrimaryKey(Integer orgId) {
        return getSqlSession().selectOne("TBdInstitutionMapper.selectByPrimaryKey", orgId);
    }

    @Override
    public int updateByPrimaryKeySelective(TBdInstitution record) {
        return getSqlSession().selectOne("TBdInstitutionMapper.updateByPrimaryKeySelective", record);
    }

    @Override
    public int updateByPrimaryKey(TBdInstitution record) {
        return getSqlSession().selectOne("TBdInstitutionMapper.updateByPrimaryKey", record);
    }

    @Override
    public List<TBdInstitution> selectList(){
        return getSqlSession().selectList("TBdInstitutionMapper.selectList");
    }
    
    @Override
    public List<TBdInstitution> selectListBySelective(Map<String, Object> reqMap) {
        return getSqlSession().selectList("TBdInstitutionMapper.selectListBySelective", reqMap);
    }
    
    @Override
    public long countListBySelective(Map<String, Object> reqMap) {
        return getSqlSession().selectOne("TBdInstitutionMapper.countListBySelective", reqMap);
    }

    @Override
    public int insertSelectiveMap(Map<String, Object> reqMap) {
        return getSqlSession().insert("TBdInstitutionMapper.insertSelectiveMap", reqMap);
    }
    
    @Override
    public int updateByUpdateMap(Map<String,Object> updateMap) {
        return getSqlSession().update("TBdInstitutionMapper.updateByUpdateMap", updateMap);
    }

    @Override
    public TBdInstitution selectByBelongOrgName(String belongOrgName) {
        return getSqlSession().selectOne("TBdInstitutionMapper.selectByBelongOrgName", belongOrgName);
    }
    
    @Override
    public TBdInstitution selectByAlias(String alias){
        return getSqlSession().selectOne("TBdInstitutionMapper.selectByAlias", alias);
    }

}
