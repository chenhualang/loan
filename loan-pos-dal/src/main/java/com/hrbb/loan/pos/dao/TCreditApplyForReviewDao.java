package com.hrbb.loan.pos.dao;

import java.util.List;
import java.util.Map;

import com.hrbb.loan.pos.dao.entity.TCreditApply;

public interface TCreditApplyForReviewDao {

	public TCreditApply selectOne(String loanid);

	public Map<String, Object> selectForRiskDetection(String loanid);

	public List<TCreditApply> selectSelective(TCreditApply ca);

	public long selectSelectiveCount(TCreditApply ca);

	public List<TCreditApply> selectHistory(Map<String, Object> m);

	public int updateByPrimaryKeySelective(TCreditApply record);

	public int updateForReview(TCreditApply record);

	public int updateForReviewBackTo(TCreditApply record);

	public int updateForReviewAddInfo(TCreditApply record);

	public int claim(TCreditApply ca);

	public int doNotClaim(TCreditApply ca);

	public long selectCheckClaimed(TCreditApply ca);

	public int updateApplyStatus(Map<String, Object> map);

	public int updateForReconsider(TCreditApply ca);

	public long selectContinueLending(String loanId);

	public List<Map<String, String>> selectNewCustCountForUM(
			Map<String, String> p);

	public List<Map<String, Object>> selectSummaryBase(Map<String, String> p);

	public List<Map<String, Object>> selectSummary0(Map<String, String> p);

	public List<Map<String, Object>> selectSummary1(Map<String, String> p);

	public List<Map<String, Object>> selectSummary2(Map<String, String> p);

	public List<Map<String, Object>> selectSummary3(Map<String, String> p);

	public List<Map<String, Object>> selectSummary4(Map<String, String> p);

	public List<Map<String, Object>> selectSummary5(Map<String, String> p);
	
	public int updateByPrimaryKeySelectiveMap(Map<String, Object> map);
}