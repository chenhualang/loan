package com.hrbb.loan.pos.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.hrbb.loan.pos.dao.TUpsIndexConsumeGategoriesDao;
import com.hrbb.loan.pos.dao.entity.TUpsIndexConsumeGategories;

@Repository("tUpsIndexConsumeGategoriesDao")
public class TUpsIndexConsumeGategoriesDaoImpl extends SqlSessionDaoSupport implements TUpsIndexConsumeGategoriesDao {

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return getSqlSession().delete("TUpsIndexConsumeGategoriesMapper.deleteByPrimaryKey",id);
    }

    @Override
    public int insert(TUpsIndexConsumeGategories record) {
        return getSqlSession().insert("TUpsIndexConsumeGategoriesMapper.insert",record);
    }

    @Override
    public int insertSelective(TUpsIndexConsumeGategories record) {
        return getSqlSession().insert("TUpsIndexConsumeGategoriesMapper.insertSelective",record);
    }

    @Override
    public TUpsIndexConsumeGategories selectByPrimaryKey(Integer id) {
        return getSqlSession().selectOne("TUpsIndexConsumeGategoriesMapper.selectByPrimaryKey",id);
    }

    @Override
    public int updateByPrimaryKeySelective(TUpsIndexConsumeGategories record) {
        return getSqlSession().update("TUpsIndexConsumeGategoriesMapper.updateByPrimaryKeySelective",record);
    }

    @Override
    public int updateByPrimaryKey(TUpsIndexConsumeGategories record) {
        return getSqlSession().update("TUpsIndexConsumeGategoriesMapper.updateByPrimaryKey",record);
    }

    @Override
    public int insertBatch(List<TUpsIndexConsumeGategories> record) {
        return getSqlSession().insert("TUpsIndexConsumeGategoriesMapper.insertBatch",record);
    }

    @Override
    public List<TUpsIndexConsumeGategories> selectListByFileUuid(String fileUuid) {
        return getSqlSession().selectList("TUpsIndexConsumeGategoriesMapper.selectByFileUuid", fileUuid);
    }

}
