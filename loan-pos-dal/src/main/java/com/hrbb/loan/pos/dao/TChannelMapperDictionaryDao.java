package com.hrbb.loan.pos.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hrbb.loan.pos.dao.entity.TChannelMapperDictionary;

public interface TChannelMapperDictionaryDao {
    
    int deleteByPrimaryKey(Integer id);

    int insert(TChannelMapperDictionary record);

    int insertSelective(TChannelMapperDictionary record);

    TChannelMapperDictionary selectByPrimaryKey(Integer id);
    
    TChannelMapperDictionary selectByInnerCode(String innerCode);

    int updateByPrimaryKeySelective(TChannelMapperDictionary record);

    int updateByPrimaryKey(TChannelMapperDictionary record);

    List<Map<String, Object>> getCode(Map<String, Object> reqMap);
    
    BigDecimal getMinLimit(String innerCode);
}