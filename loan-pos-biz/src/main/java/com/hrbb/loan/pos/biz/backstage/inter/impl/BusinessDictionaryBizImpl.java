package com.hrbb.loan.pos.biz.backstage.inter.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.hrbb.loan.pos.biz.backstage.inter.BusinessDictionaryBiz;
import com.hrbb.loan.pos.dao.entity.TBusinessDictionary;
import com.hrbb.loan.pos.service.BusinessDictionaryService;

/**
 *<h1></h1>
 *@author marco
 *@version Id: BusinessDictionaryBizImpl.java, v 1.0 2015-3-9 下午3:46:36 marco Exp
 */
@Component("businessDictionaryBiz")
public class BusinessDictionaryBizImpl implements BusinessDictionaryBiz{

	@Autowired
	@Qualifier("businessDictionaryService")
	private BusinessDictionaryService businessDictionaryService;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return 0;
	}

	@Override
	public int insert(TBusinessDictionary record) {
		return 0;
	}

	@Override
	public int insertSelective(TBusinessDictionary record) {
		return 0;
	}

	@Override
	public TBusinessDictionary selectByPrimaryKey(Integer id) {
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(TBusinessDictionary record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(TBusinessDictionary record) {
		return 0;
	}

	@Override
	public List<Map<String, Object>> getBusinessCode(Map<String, Object> reqMap) {
		return businessDictionaryService.getBusinessCode(reqMap);
	}
	
	@Override
	public TBusinessDictionary selectOne(TBusinessDictionary record) {
		return businessDictionaryService.selectOne(record);
	}
	
	@Override
	public String getApprInfoExtMsg(String apprInfoExt){
		return businessDictionaryService.getApprInfoExtMsg(apprInfoExt);
	}

	@Override
	public List<Map<String, Object>> selectRefuseReaonMap(Map<String, Object> reqMap) {
		return businessDictionaryService.selectRefuseReaonMap(reqMap);
	}
}
