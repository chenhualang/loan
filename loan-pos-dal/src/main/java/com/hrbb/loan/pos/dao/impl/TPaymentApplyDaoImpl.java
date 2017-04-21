package com.hrbb.loan.pos.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.hrbb.loan.pos.dao.TPaymentApplyDao;
import com.hrbb.loan.pos.dao.entity.TPaymentApply;

@Repository("tPaymentApplyDao")
public class TPaymentApplyDaoImpl extends SqlSessionDaoSupport implements TPaymentApplyDao{

	@Override
	public int deleteByPrimaryKey(String payApplyId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(TPaymentApply record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(TPaymentApply record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TPaymentApply selectByPrimaryKey(String payApplyId) {
		return getSqlSession().selectOne("TPaymentApplyMapper.selectByPrimaryKey",payApplyId);
	}

	@Override
	public int updateByPrimaryKeySelective(TPaymentApply record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(TPaymentApply record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelectiveMap(Map<String, Object> reqMap) {
		// TODO Auto-generated method stub
		return getSqlSession().insert("TPaymentApplyMapper.insertSelectiveMap", reqMap);
	}

	@Override
	public int updateSelectiveMap(Map<String, Object> reqMap) {
		// TODO Auto-generated method stub
		return getSqlSession().update("TPaymentApplyMapper.updateSelectiveMap", reqMap);
	}

	@Override
	public Long countPaymentApplyNum(Map<String, Object> reqMap) {
		return getSqlSession().selectOne("TPaymentApplyMapper.countPaymentApplyNum", reqMap);
	}

	@Override
	public List<Map<String, Object>> queryByStdmerno(Map<String, Object> reqMap) {
		return getSqlSession().selectList("TPaymentApplyMapper.queryByStdmerno", reqMap);
	}

    @Override
    public List<Map<String, Object>> queryPaymentApply(Map<String, Object> reqMap) {
        return getSqlSession().selectList("TPaymentApplyMapper.queryPaymentApply", reqMap);
    }

    @Override
    public List<Map<String, Object>> queryPaymentApply90(Map<String, Object> reqMap) {
        return getSqlSession().selectList("TPaymentApplyMapper.queryPaymentApply90", reqMap);
    }

    @Override
    public List<Map<String,Object>> queryAvailablePaymentApply(Map<String,Object> reqMap){
        return getSqlSession().selectList("TPaymentApplyMapper.queryAvailablePaymentApply",reqMap);
    }
    
    @Override
    public List<Map<String, Object>> queryPaymentApplyByPayApplyId(Map<String, Object> reqMap) {
        return getSqlSession().selectList("TPaymentApplyMapper.queryPaymentApplyByPayApplyId", reqMap);
    }
    @Override
    public List<Map<String, Object>> selectListByPrimaryKey(String payApplyId) {
        return getSqlSession().selectList("TPaymentApplyMapper.selectByPrimaryKey",payApplyId);
    }

    @Override
    public List<Map<String, Object>> selectListByZzApp(Map<String, Object> map) {
        return getSqlSession().selectList("TPaymentApplyMapper.selectPaymentByZzApp", map);
    }

	/** 
	 * @see com.hrbb.loan.pos.dao.TPaymentApplyDao#selectRecordInfoLending()
	 */
	@Override
	public List<TPaymentApply> selectRecordInfoLending() {
		return  getSqlSession().selectList("TPaymentApplyMapper.selectRecordInfoLending");
	}
	/** 
	 * @see com.hrbb.loan.pos.dao.TPaymentApplyDao#selectRecordInfoCommission()
	 */
	@Override
	public List<TPaymentApply> selectRecordInfoCommission() {
		return  getSqlSession().selectList("TPaymentApplyMapper.selectRecordInfoCommission");
	}

    @Override
    public Long countPaymentApplys(Map<String, Object> reqMap) {
        return  getSqlSession().selectOne("TPaymentApplyMapper.countPaymentApply",reqMap);
    }

    @Override
    public Long countPaymentApplys90(Map<String, Object> reqMap) {
        return  getSqlSession().selectOne("TPaymentApplyMapper.countPaymentApply90",reqMap);
    }
    /** 
     * @see com.hrbb.loan.pos.dao.TPaymentApplyDao#countAvailablePaymentApplys(java.util.Map)
     */
    @Override
    public Long countAvailablePaymentApplys(Map<String, Object> reqMap) {
        return  getSqlSession().selectOne("TPaymentApplyMapper.countAvailablePaymentApply",reqMap);
    }

	@Override
	public BigDecimal getPaymentApplyInfocontno(String contno) {
		 return  getSqlSession().selectOne("TPaymentApplyMapper.getPaymentApplyInfocontno",contno);
	}

    @Override
    public List<Map<String, Object>> selectListBySlApp(Map<String, Object> map) {
        return getSqlSession().selectList("TPaymentApplyMapper.selectPaymentBySlApp", map);
    }
    
    @Override
    public TPaymentApply selectPayApplyAmtSum(Map<String, Object> map) {
    	return getSqlSession().selectOne("TPaymentApplyMapper.selectPayApplyAmtSum", map);
    }

	@Override
	public Map<String, Object> queryPaymentApplyByIssueId(
			Map<String, Object> map) {
		return getSqlSession().selectOne("TPaymentApplyMapper.queryPaymentApplyByIssueId", map);
	}

	@Override
	public String getPaymentApplyIdByContno(String contno) {
		return getSqlSession().selectOne("TPaymentApplyMapper.getPaymentApplyIdByContno", contno);
	}

	@Override
	public String sumHasRepayedAmt(Map<String, Object> reqMap) {
		return getSqlSession().selectOne("TPaymentApplyMapper.sumHasRepayedAmt", reqMap);
	}

	@Override
	public Map<String, Object> queryByStdisno(Map<String, Object> reqMap) {
		return getSqlSession().selectOne("TPaymentApplyMapper.queryByStdisno", reqMap);
	}

	@Override
	public List<Map<String, Object>> selectPaymentAuto(List<String> list) {
		return getSqlSession().selectList("TPaymentApplyMapper.selectPaymentAuto", list);
	}
}
