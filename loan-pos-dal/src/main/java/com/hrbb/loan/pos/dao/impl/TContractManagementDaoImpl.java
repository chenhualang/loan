package com.hrbb.loan.pos.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.hrbb.loan.pos.dao.TContractManagementDao;
import com.hrbb.loan.pos.dao.entity.TApproveReject;
import com.hrbb.loan.pos.dao.entity.TApproveResult;
import com.hrbb.loan.pos.dao.entity.TContractManagement;

@Repository("tContractManagementDao")
public class TContractManagementDaoImpl extends SqlSessionDaoSupport implements TContractManagementDao {

	@Override
	public Long countContractManagement(Map<String, Object> map) {
		return getSqlSession().selectOne("TApproveResultMapper.countSelective", map);
	}

	@Override
	public List<Map<String, Object>> selectSelectiveMap(Map<String, Object> map) {
		return getSqlSession().selectList("TApproveResultMapper.selectSelectiveMap", map);
	}

	@Override
	public int insertSelectiveMap(Map<String, Object> map) {
		return getSqlSession().insert("TContractManagementMapper.insertSelectiveMap", map);
	}
	
	@Override
	public int deleteByPrimaryKey(String loanId) {
		return getSqlSession().delete("TApproveResultMapper.deleteByPrimaryKey", loanId);
	}

	@Override
	public int updateByPrimaryKey(Map<String, Object> updateMap) {
		return getSqlSession().update("TApproveResultMapper.updateByPrimaryKey", updateMap);
	}

/*	@Override
	public int insertRejectReason(Map<String, Object> reqMap) {
		// TODO Auto-generated method stub
		return getSqlSession().insert("TContractManagementMapper.insertSelectiveMap", reqMap);
	}
*/
//	@Override
//	public TContractManagement getContractInfoById(String contNo) {
//		return getSqlSession().selectOne("TContractManagementMapper.getContractInfoById",contNo);
//	}

	@Override
	public int insertApproveReject(Map<String, Object> map) {
		return getSqlSession().insert("TApproveRejectMapper.insertApproveReject", map);
	}

	@Override
	public int selectRejectMaxApproveNumByApproveId(String approveId) {
		int reviewId = getSqlSession().selectOne(
				"TApproveRejectMapper.selectRejectMaxApproveNumByApproveId", approveId);
		return reviewId + 1;
	}
	
	@Override
	public int selectAdjustMaxApproveNumByApproveId(String approveId) {
		int reviewId = getSqlSession().selectOne(
				"TApproveAdjustMapper.selectAdjustMaxApproveNumByApproveId", approveId);
		return reviewId + 1;
	}

	@Override
	public TApproveReject selectByPrimaryKey(Map<String, Object> updateMap) {
		return getSqlSession().selectOne("TApproveRejectMapper.selectByPrimaryKey", updateMap);
	}

	@Override
	public int updateByPrimaryKeySelective(Map<String, Object> updateMap) {
		return getSqlSession().update("TApproveResultMapper.updateByPrimaryKeySelective", updateMap);
	}

	@Override
	public int insertRejectReason(Map<String, Object> reqMap) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateApproveChange(Map<String, Object> updateMap) {
		return getSqlSession().update("TApproveChangeMapper.updateApproveChange", updateMap);
	}

	@Override
	public int insertApproveAdjust(Map<String, Object> map) {
		return getSqlSession().insert("TApproveAdjustMapper.insertApproveAdjust", map);
	}

	@Override
	public TApproveResult getApproveInfo(String approveId) {
		return getSqlSession().selectOne("TApproveResultMapper.getApproveInfo", approveId);
	}

	@Override
	public TContractManagement getContractInfoByLoanId(String loanId) {
		return getSqlSession().selectOne("TContractManagementMapper.getContractInfoByLoanId", loanId);
	}
	
	@Override
	public TContractManagement getContractInfoByPayApplyId(String payApplyId){
		return getSqlSession().selectOne("TContractManagementMapper.getContractInfoByPayApplyId", payApplyId);
	}

	@Override
	public String getcustIdByContNo(String contNo) {
		return getSqlSession().selectOne("TContractManagementMapper.getcustIdByContNo", contNo);
	}

	@Override
	public TContractManagement getContractInfoByContNo(String contNo) {
		return getSqlSession().selectOne("TContractManagementMapper.getContractInfoByContNo", contNo);
	}

	@Override
	public int insert(TContractManagement record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(TContractManagement record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TContractManagement selectByPrimaryKey(String contNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(TContractManagement record) {
		return getSqlSession().update("TContractManagementMapper.updateByPrimaryKeySelective", record);
	}

	@Override
	public int updateByPrimaryKey(TContractManagement record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TApproveResult getApproveResultInfoByLoanId(String loanId) {
		return getSqlSession().selectOne("TApproveResultMapper.getApproveResultInfoByLoanId",loanId);
	}

	@Override
	public int updateContractStatus(Map<String, Object> updateMap) {
		return getSqlSession().update("TContractManagementMapper.updateContractStatus", updateMap);
	}

//	@Override
//	public TContractManagement selectContractFetchReceipt(String contno) {
//		return getSqlSession().selectOne("TContractManagementMapper.selectContractFetchReceipt",contno);
//	}


    @Override
    public List<TApproveResult> selectAvailableSelectiveMap(Map<String, Object> map) {
        return getSqlSession().selectList("TApproveResultMapper.selectAvailableSelectiveMap", map);
    }

    @Override
    public Long countAvailableContractManagement(Map<String, Object> map) {
        return getSqlSession().selectOne("TApproveResultMapper.countAvailableSelective", map);
    }

	@Override
	public int updateContractInfo(Map<String, Object> contractMap) {
		return getSqlSession().update("TContractManagementMapper.updateContractInfo", contractMap);
	}

	@Override
	public long countApprove(Map<String, Object> reqMap) {
		return getSqlSession().selectOne("TApproveResultMapper.countApprove", reqMap);
	}

	@Override
	public List<TContractManagement> selectContractForPaymentAuto(
			List<String> list) {
		return getSqlSession().selectList("TApproveResultMapper.selectContractForPaymentAuto", list);
	}


	
}
