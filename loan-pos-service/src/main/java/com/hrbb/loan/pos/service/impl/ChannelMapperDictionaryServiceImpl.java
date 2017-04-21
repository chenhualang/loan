package com.hrbb.loan.pos.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hrbb.loan.pos.dao.TChannelMapperDictionaryDao;
import com.hrbb.loan.pos.dao.entity.TChannelMapperDictionary;
import com.hrbb.loan.pos.service.ChannelMapperDictionaryService;

@Service("channelMapperDictionaryService")
public class ChannelMapperDictionaryServiceImpl implements ChannelMapperDictionaryService {
    
    @Autowired
    TChannelMapperDictionaryDao tChannelMapperDictionaryDao;
    
    @Override
    public List<Map<String, Object>> getCode(Map<String, Object> reqMap) {
        return tChannelMapperDictionaryDao.getCode(reqMap);
    }

    @Override
    public BigDecimal getMinLimit(String innerCode) {
        return tChannelMapperDictionaryDao.getMinLimit(innerCode);
    }

    @Override
    public TChannelMapperDictionary getTChannelMapperDictionaryByCardno(String cardno) {
        return tChannelMapperDictionaryDao.selectByInnerCode(cardno);
    }

}
