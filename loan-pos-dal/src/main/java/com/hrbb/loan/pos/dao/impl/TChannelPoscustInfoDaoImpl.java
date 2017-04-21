package com.hrbb.loan.pos.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.hrbb.loan.pos.dao.TChannelPoscustInfoDao;
import com.hrbb.loan.pos.dao.entity.TChannelPoscustInfo;

@Repository("tChannelPoscustInfoDao")
public class TChannelPoscustInfoDaoImpl extends SqlSessionDaoSupport implements TChannelPoscustInfoDao{

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(TChannelPoscustInfo record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(TChannelPoscustInfo record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TChannelPoscustInfo selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(TChannelPoscustInfo record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(TChannelPoscustInfo record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelectvieMap(Map<String, Object> reqMap) {
		// TODO Auto-generated method stub
		return getSqlSession().insert("TChannelPoscustInfoMapper.insertSelectiveMap", reqMap);
	}

	@Override
	public List<Map<String, Object>> selectSelectiveMap(Map<String, Object> reqMap) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList("TChannelPoscustInfoMapper.selectSelectiveMap", reqMap);
	}

	@Override
	public List<Map<String, Object>> queryChannelDetail(
			Map<String, Object> reqMap) {
		 List<Map<String, Object>> infolist = getSqlSession().selectList("TChannelPoscustInfoMapper.queryChannelDetail", reqMap);
		 logger.info(infolist);
		 return infolist;
	}

	@Override
	public Long countChannelDetail(Map<String, Object> reqMap) {
		return getSqlSession().selectOne("TChannelPoscustInfoMapper.countChannelDetail", reqMap);
	}

	@Override
	public String getCustIdbyStdshno(String stdmerno) {
		return getSqlSession().selectOne("TChannelPoscustInfoMapper.getCustIdbyStdshno", stdmerno);
	}
	@Override
    public String selectchannelPosCustId(Map<String, Object> reqMap) {
        // TODO Auto-generated method stub
        return getSqlSession().selectOne("TChannelPoscustInfoMapper.selectchannelPosCustId", reqMap);
    }
	

}
