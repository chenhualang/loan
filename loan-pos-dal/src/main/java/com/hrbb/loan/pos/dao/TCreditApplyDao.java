package com.hrbb.loan.pos.dao;

import java.util.List;
import java.util.Map;

import com.hrbb.loan.pos.dao.entity.TCreditApply;

public interface TCreditApplyDao {
    public int deleteByPrimaryKey(String loanId);

    public int insert(TCreditApply record);

    public int insertSelective(TCreditApply record);
    
    public int insertSelectiveMap(Map<String, Object> map);

    public TCreditApply selectByPrimaryKey(String loanId);
    
    public List<TCreditApply> selectSelective(Map<String, Object> map);
    
    public List<Map<String, Object>> selectSelectiveMap(Map<String, Object> map);

    public int updateByPrimaryKeySelective(TCreditApply record);
    
    public int updateByPrimaryKeySelectiveMap(Map<String, Object> map);

    public int updateByPrimaryKey(TCreditApply record);
    
    public Long countCreitApply(Map<String, Object> map);
    
    public int deleteBatch(List<String> loanIds);

	public TCreditApply queryCreditApplyInfo(String loanId);

	public String getCustIdbyStdshno(String stdshno);
	
	public String getLoanIdbyStdshno(String stdshno);

	public String getLoanIdbyPosCustId(String posCustId);

	public String getCustIdbyPosCustId(String posCustId);

	public String getStdshnobyLoanId(String posCustId);
	
	public List<Map<String, Object>> selectDetailsByZzApp(Map<String, Object> map);

	public int updateApplyStatusForDownloadImages(TCreditApply ca);
	
	public int updateApplyStatus(Map<String, Object> map);
	
	//获取业务数据 ---导出excel时用
	public List<Map<String, Object>> getBizData(Map<String, Object> reqMap);
	
	/**
	 * 查询客户距今最近的一笔申请
	 * 
	 * @param custId
	 * @return
	 */
	Map<String,Object> selectLastTh(String custId);
	/**
	 * 消费贷贷款申请信息查询
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> selectDetailsBySaleApp(Map<String, Object> map);
	
	/**
	 * 通过loanId查询银行卡号
	 * 
	 * @param loanId
	 * @return
	 */
	public Map<String,Object> selectNameAndBankAccNoByLoanId(String loanId);

	
	/**
	 * 通过loanId查询进件方式
	 * 
	 * @param loanId
	 * @return
	 */
	public String selectInChannelKindByLoanId(String loanId);
	
	/**
	 * 通过loanId查询贷款模式
	 * 
	 * @param loanId
	 * @return
	 */
	public String selectLoanTypeByLoanId(String loanId);
	
	/**
	 * 通过loanId查询channel
	 * 
	 * @param loanId
	 * @return
	 */
	public String selectChannelByLoanId(String loanId);

    /**
     * 查询指定渠道和进件方式的当日业务量
     * 
     * @param reqMap
     * @return
     */
    Long countDailyApply(Map<String, Object> reqMap);

}