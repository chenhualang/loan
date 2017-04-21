package com.hrbb.loan.pos.dao;

import java.util.List;
import java.util.Map;

import com.hrbb.loan.pos.dao.entity.TBdInstitution;

public interface TBdInstitutionDao {
    int deleteByPrimaryKey(Integer orgId);

    int insert(TBdInstitution record);

    int insertSelective(TBdInstitution record);
    
    int insertSelectiveMap(Map<String,Object> reqMap);

    TBdInstitution selectByPrimaryKey(Integer orgId);

    int updateByPrimaryKeySelective(TBdInstitution record);

    int updateByPrimaryKey(TBdInstitution record);
    
    /**
     * 查询所有产业机构列表
     * 
     * @return
     */
    List<TBdInstitution> selectList();
    
    /**
     * 分页查询列表
     * 
     * @param reqMap
     * @return
     */
    List<TBdInstitution> selectListBySelective(Map<String,Object> reqMap);
    
    /**
     * 列表条数
     * 
     * @param reqMap
     * @return
     */
    long countListBySelective(Map<String,Object> reqMap);

    /**
     * 修改展业机构
     * 
     * @param updateMap
     * @return
     */
    int updateByUpdateMap(Map<String, Object> updateMap);

    /**
     * 
     * @param belongOrgName
     * @return
     */
    TBdInstitution selectByBelongOrgName(String belongOrgName);

    /**
     * 通过简称查询展业机构
     * 
     * @param alias
     * @return
     */
    TBdInstitution selectByAlias(String alias);

}