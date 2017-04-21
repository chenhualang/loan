package com.hrbb.loan.pos.dao;

import java.util.List;

import com.hrbb.loan.pos.dao.entity.TUpsIndexConsumeGategories;

public interface TUpsIndexConsumeGategoriesDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TUpsIndexConsumeGategories record);

    int insertSelective(TUpsIndexConsumeGategories record);
    
    int insertBatch(List<TUpsIndexConsumeGategories> record);

    TUpsIndexConsumeGategories selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TUpsIndexConsumeGategories record);

    int updateByPrimaryKey(TUpsIndexConsumeGategories record);
    
    /**
     * 查询汇总情况
     * 
     * @param fileUuid
     * @return
     */
    List<TUpsIndexConsumeGategories> selectListByFileUuid(String fileUuid);
}