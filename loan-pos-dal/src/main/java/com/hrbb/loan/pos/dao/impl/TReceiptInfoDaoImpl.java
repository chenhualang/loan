package com.hrbb.loan.pos.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.hrbb.loan.pos.dao.TReceiptInfoDao;
import com.hrbb.loan.pos.dao.entity.TReceiptInfo;
@Repository("tReceiptInfoDao")
public class TReceiptInfoDaoImpl extends SqlSessionDaoSupport implements TReceiptInfoDao {

	@Override
	public int deleteByPrimaryKey(String receiptId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(TReceiptInfo record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(TReceiptInfo record) {
		return getSqlSession().insert("TReceiptInfoMapper.insertSelective", record);
	}

	@Override
	public TReceiptInfo selectByPrimaryKey(String receiptId) {
		return getSqlSession().selectOne("TReceiptInfoMapper.selectByPrimaryKey",receiptId);
	}
	
	@Override
    public TReceiptInfo selectByPayApplyId(String payApplyId) {
        return getSqlSession().selectOne("TReceiptInfoMapper.selectByPayApplyId",payApplyId);
    }
	
	@Override
	public int updateByPrimaryKeySelective(TReceiptInfo record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(TReceiptInfo record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<TReceiptInfo> selectSelectiveMap(Map<String, Object> map) {
		return getSqlSession().selectList("TReceiptInfoMapper.selectSelectiveMap", map);
	}

	@Override
	public long countSelectiveMap(Map<String, Object> map){
	    return getSqlSession().selectOne("TReceiptInfoMapper.countSelectiveMap",map);
	}
	@Override
	public String getContNoByReceiptId(String receiptId) {
		return getSqlSession().selectOne("TReceiptInfoMapper.getContNoByReceiptId",receiptId);
	}

	@Override
	public String getReceiptTotalAmountByContNo(String contno) {
		return getSqlSession().selectOne("TReceiptInfoMapper.getReceiptTotalAmountByContNo",contno);
	}

	@Override
	public Double getLoanTotalBalanceByReceiptId(String listid) {
		return getSqlSession().selectOne("TReceiptInfoMapper.getLoanTotalBalanceByReceiptId",listid);
	}

	@Override
	public String getReceiptIdByPayApplyId(String listid) {
		return getSqlSession().selectOne("TReceiptInfoMapper.getReceiptIdByPayApplyId",listid);
	}

	@Override
	public String getBeginDateByPayApplyId(String listid) {
		return getSqlSession().selectOne("TReceiptInfoMapper.getBeginDateByPayApplyId",listid);
	}
	
	@Override
	public List<TReceiptInfo> selectRecordInfoHB16() {
		return getSqlSession().selectList("TReceiptInfoMapper.selectRecordInfoHB16");
	}
	
	@Override
	public List<TReceiptInfo> selectRecordInfoHB18() {
		return getSqlSession().selectList("TReceiptInfoMapper.selectRecordInfoHB18");
	}
	
	@Override
	public List<TReceiptInfo> selectRecordInfoHB19() {
		return getSqlSession().selectList("TReceiptInfoMapper.selectRecordInfoHB19");
	}

	@Override
	public List<TReceiptInfo> getReceiptList(Map<String, Object> reqMap) {
		return getSqlSession().selectList("TReceiptInfoMapper.getReceiptList",reqMap);
	}

	@Override
	public List<TReceiptInfo> getAllReceipts(Map<String, Object> reqMap) {
		return getSqlSession().selectList("TReceiptInfoMapper.getAllReceipts",reqMap);
	}
	
	@Override
	public int updateReceiptSelective(Map<String,Object> reqMap){
	    return getSqlSession().update("TReceiptInfoMapper.updateReceiptSelective", reqMap);
	}

    @Override
    public List<TReceiptInfo> getReceiptList10() {
        return getSqlSession().selectList("TReceiptInfoMapper.getReceiptList10");
    }

    /**
     * 
     * @see com.hrbb.loan.pos.dao.TReceiptInfoDao#selectListMapsByTimer(java.util.Map)
     */
    @Override
    public List<Map<String, Object>> selectListMapsByTimer(Map<String, Object> queryMap) {
        return getSqlSession().selectList("TReceiptInfoMapper.selectReceiptByTimer", queryMap);
    }

	@Override
	public Map<String, Object> getReceiptMapByReceiptId(String receiptId) {
		  return getSqlSession().selectOne("TReceiptInfoMapper.getReceiptMapByReceiptId",receiptId);
	}

	@Override
	public TReceiptInfo selectByExeSeq(String exeSeq) {
		return getSqlSession().selectOne("TReceiptInfoMapper.selectByExeSeq",exeSeq);
	}

	@Override
	public List<TReceiptInfo> getAllUnClearedReceipts() {
		  return getSqlSession().selectList("TReceiptInfoMapper.getAllUnClearedReceipts");
	}

	@Override
	public long countNumber(Map<String, Object> reqMap) {
		return getSqlSession().selectOne("TReceiptInfoMapper.countNumber",reqMap);
	}

	@Override
	public int updateReceipt(Map<String, Object> receiptMap) {
		return getSqlSession().update("TReceiptInfoMapper.updateReceipt",receiptMap);
	}

	
	@Override
	public List<Map<String, String>> selectSumForUM(Map<String, String> p) {
		  return getSqlSession().selectList("TReceiptInfoMapper.selectSumForUM",p);
	}

	@Override
	public long countCustRelatedReceiptNumber(Map<String, Object> reqMap) {
		return getSqlSession().selectOne("TReceiptInfoMapper.countCustRelatedReceiptNumber",reqMap);
	}
	
	@Override
	public List<Map<String, Object>> selectSummaryForCont(Map<String, Object> reqMap) {
		return getSqlSession().selectList("TReceiptInfoMapper.selectSummaryForCont",reqMap);
	}
	
	@Override
	public TReceiptInfo selectTotalBalanceSum(Map<String, Object> map) {
		return getSqlSession().selectOne("TReceiptInfoMapper.selectTotalBalanceSum",map);
	}
	
	@Override
	public TReceiptInfo selectPayApplyAmtSum(Map<String, Object> map) {
		return getSqlSession().selectOne("TReceiptInfoMapper.selectPayApplyAmtSum",map);
	}

    public List<TReceiptInfo> selectUnFinishedReceipt() {
        return getSqlSession().selectList("TReceiptInfoMapper.selectUnFinishedReceipt");
    }

    public Map<String, Object> selectByReceiptId(String receiptId) {
        return getSqlSession().selectOne("TReceiptInfoMapper.selectByReceiptId", receiptId);
    }
}
