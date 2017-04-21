package com.hrbb.loan.pos.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.hrbb.loan.pos.dao.TBusinessDictionaryDao;
import com.hrbb.loan.pos.dao.entity.TBusinessDictionary;
import com.hrbb.loan.pos.util.StringUtil;
import com.hrbb.loan.pos.util.constants.BusinessDictionaryConstants;
import com.hrbb.loan.pos.util.constants.ReviewNoteConstants;

/**
 * <h1></h1>
 * 
 * @author Johnson Song
 * @version Id: TBusinessDictionaryDaoImpl.java, v 1.0 2015-3-9 下午3:46:36
 *          Johnson Song Exp
 */
@Repository("tBusinessDictionaryDao")
public class TBusinessDictionaryDaoImpl extends SqlSessionDaoSupport implements
		TBusinessDictionaryDao {

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
		return getSqlSession().selectList(
				"TBusinessDictionaryMapper.selectMap", reqMap);
	}

	@Override
	public TBusinessDictionary selectOne(TBusinessDictionary record) {
		return getSqlSession().selectOne("TBusinessDictionaryMapper.selectOne",
				record);
	}

	@Override
	public List<Map<String, Object>> getItemNamesWithExtOrder(
			Map<String, Object> reqMap) {
		return getSqlSession().selectList(
				"TBusinessDictionaryMapper.selectMapWithExtOrder", reqMap);
	}

	@Override
	public String getApprInfoExtMsg(String apprInfoExt) {
		if (StringUtil.isEmpty(apprInfoExt)) {
			return "";
		}
		String apprInfoExtMsg = "";
		String[] apprInfoExtCodes = apprInfoExt
				.split(ReviewNoteConstants.STRING_SEPARATOR_CODE);
		TBusinessDictionary record = null;
		for (String apprInfoExtCode : apprInfoExtCodes) {
			record = new TBusinessDictionary();
			record.setCodeNo(BusinessDictionaryConstants.RefuseReaon);
			record.setItemNo(apprInfoExtCode);
			record = getSqlSession().selectOne(
					"TBusinessDictionaryMapper.selectOne", record);
			if (record != null && StringUtil.isNotEmpty(record.getItemName())) {
				apprInfoExtMsg = apprInfoExtMsg.concat(record.getItemName()).concat(";");
			}
		}
		return apprInfoExtMsg;
	}

	@Override
	public List<Map<String, Object>> selectRefuseReaonMap(Map<String, Object> reqMap) {
		return getSqlSession().selectList(
				"TBusinessDictionaryMapper.selectRefuseReaonMap", reqMap);
	}

	@Override
	public List<TBusinessDictionary> selectByIds(String codeNo, String[] ids) {
		Map<String,Object> reqMap = Maps.newHashMap();
		reqMap.put("codeNo", codeNo);
		reqMap.put("ids", ids);
		return getSqlSession().selectList("TBusinessDictionaryMapper.selectByIds", reqMap);
	}

	@Override
	public List<Map<String, Object>> selectAddInfoMap(Map<String, Object> reqMap) {
		return getSqlSession().selectList("TBusinessDictionaryMapper.selectAddInfoMap", reqMap);
	}
}
