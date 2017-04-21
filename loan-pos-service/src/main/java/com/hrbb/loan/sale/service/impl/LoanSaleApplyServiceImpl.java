package com.hrbb.loan.sale.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hrbb.loan.pos.dao.TBankAccnoInfoDao;
import com.hrbb.loan.pos.dao.TBankSerialnoInfoDao;
import com.hrbb.loan.pos.dao.TChannelPoscustInfoDao;
import com.hrbb.loan.pos.dao.TCreditApplyDao;
import com.hrbb.loan.pos.dao.TPosCustInfoDao;
import com.hrbb.loan.pos.dao.TPosSerialnoInfoDao;
import com.hrbb.loan.pos.dao.entity.TBankAccnoInfo;
import com.hrbb.loan.pos.dao.entity.TCreditApply;
import com.hrbb.loan.pos.dao.entity.TPosSerialnoInfo;
import com.hrbb.loan.pos.util.ListUtil;
import com.hrbb.loan.pos.util.StringUtil;
import com.hrbb.loan.sale.service.LoanSaleApplyService;

/**
 * 消费贷贷款申请service
 * @author litengfeng
 * @version $Id: LoanSaleApplyServiceImpl.java, v 0.1 2015年5月13日 上午11:44:59 litengfeng Exp $
 */
@Service("loanSaleApplyService")
public class LoanSaleApplyServiceImpl implements LoanSaleApplyService{
	
	
	public static final String POS_CUST_NAME = "posCustName";
	
	public static final String LOAN_ID = "loanId";
	
	public static final String BANK_ACC_NO = "bankAccno";

	private static final String POS_CUST_ID = "posCustId";
	
	private static final String CHANNEL_POSCUST_ID = "channelPosCustId";
	
	private static final String CHANNEL = "channel";

	private static final String STDMERNO = "stdmerno";

	private static final String START_PAGE = "startPage";

	private static final String PAGE_SIZE = "pageSize";

	
	@Autowired
	private TCreditApplyDao tCreditApplyDao;
	
	@Autowired
	private TBankAccnoInfoDao bankAccnoInfoDao;
	
	@Autowired
	private TPosCustInfoDao posCustInfoDao;
	
	@Autowired
	private TPosSerialnoInfoDao posSerialnoInfoDao;
	
	@Autowired
	private TBankSerialnoInfoDao bankSerialnoInfoDao;
	
	@Autowired
	private TChannelPoscustInfoDao tChannelPoscustInfoDao;
	
	public LoanSaleApplyServiceImpl() {
	}
	/**
	 * <h2>根据条件获取记录</h2>
	 * @param Map
	 * @return List
	 */

	@Override
	public List<TCreditApply> getCreditApply(Map<String, Object> reqMap) {
		return tCreditApplyDao.selectSelective(reqMap);

	}

	/**
	 * <h2>更新记录</h2>
	 * @param TCreditApply
	 * @return int
	 */
	@Override
	public int updateCreditApply(TCreditApply tCreditApply) {
		return tCreditApplyDao.updateByPrimaryKeySelective(tCreditApply);
	}

	/**
	 * <h2>删除记录</h2>
	 * @param String
	 * @return int
	 */
	@Override
	public int delCreditApply(String loanId) {
		return tCreditApplyDao.deleteByPrimaryKey(loanId);
	}

	/**
	 * <h2>插入记录</h2>
	 * @param TCreditApply
	 * @return int
	 */
	@Override
	public int insertCreditApply(TCreditApply tCreditApply) {
		return tCreditApplyDao.insertSelective(tCreditApply);
	}
	@Override
	public List<Map<String, Object>> getCreditApplyMap(
			Map<String, Object> reqMap) {
		return tCreditApplyDao.selectSelectiveMap(reqMap);
	}
	
	@Override
	public Long countCreditApply(Map<String, Object> reqMap) {
		return tCreditApplyDao.countCreitApply(reqMap);
	}
	
	
	@Override
	public int deleteBatch(List<String> loanIds) {
		return tCreditApplyDao.deleteBatch(loanIds);
	}
	@Override
	public int updateCreditApplyMap(Map<String, Object> map) {
		return tCreditApplyDao.updateByPrimaryKeySelectiveMap(map);
	}
	@Override
	public int insertCreditApplyMap(Map<String, Object> reqMap) {
		tCreditApplyDao.insertSelectiveMap(reqMap);
		return 1;
	}
	@Override
	public List<Map<String, Object>> selectBankAccMap(Map<String, Object> reqMap) {
		return bankAccnoInfoDao.selectMap(reqMap);
	}
	@Override
	public int updateBankAccByPrimaryKeySelectiveMap(Map<String, Object> reqMap) {
		return bankAccnoInfoDao.updateByPrimaryKeySelectiveMap(reqMap);
	}
	@Override
	public int insertBankAccSelectiveMap(Map<String, Object> map) {
		return bankAccnoInfoDao.insertSelectiveMap(map);
	}
	@Override
	public List<Map<String, Object>> selectPosCustMap(Map<String, Object> reqMap) {
		return posCustInfoDao.selectMap(reqMap);
	}
	@Override
	public int updatePosCustByPrimaryKeySelectiveMap(Map<String, Object> reqMap) {
		return posCustInfoDao.updateByPrimaryKeySelectiveMap(reqMap);
	}
	@Override
	public int insertPosCustSelectiveMap(Map<String, Object> map) {
		return posCustInfoDao.insertSelectiveMap(map);
	}
	@Override
	public List<Map<String, Object>> selectPosSerialMap(
			Map<String, Object> reqMap) {
		return posSerialnoInfoDao.selectMap(reqMap);
	}
	@Override
	public int updatePosSerialByPrimaryKeySelectiveMap(
			Map<String, Object> reqMap) {
		return posSerialnoInfoDao.updateByPrimaryKeySelectiveMap(reqMap);
	}
	@Override
	public int insertPosSerialSelectiveMap(Map<String, Object> map) {
		return posSerialnoInfoDao.insertSelectiveMap(map);
	}
	
	@Override
	public int insertPosSerialSelectiveMapBatch(List<Map<String, Object>> reqList){
		return posSerialnoInfoDao.insertSelectiveMapBatch(reqList);
	}
	@Override
	public Map<String, Object> getOneCreditApply(String loanId) {
		if(loanId != null){
			Map<String, Object> reqMap = Maps.newHashMap();
			reqMap.put(LOAN_ID, loanId);
			List<Map<String, Object>> resList = getCreditApplyMap(reqMap);
			if(resList != null && resList.size() != 0){
				return resList.get(0);
			}else{
				return Maps.newHashMap();
			}
		}else{
			return Maps.newHashMap();
		}
	}
	@Override
	public Map<String, Object> selectOnePosCustMap(String posCustName) {
		if(!StringUtil.isEmpty(posCustName)){
			Map<String, Object> reqMap = Maps.newHashMap();
			reqMap.put(POS_CUST_NAME, posCustName);
			List<Map<String, Object>> resList = selectPosCustMap(reqMap);
			if(resList != null && resList.size() != 0){
				return resList.get(0);
			}else{
				return Maps.newHashMap();
			}
		}else{
			return Maps.newHashMap();
		}
	}
	@Override
	public List<Map<String, Object>> selectBankSerialMap(
			Map<String, Object> reqMap) {
		return bankSerialnoInfoDao.selectMap(reqMap);
	}
	
	@Override
	public List<Map<String,Object>> selectMapByCurrMonth(Map<String,Object> reqMap){
	    return bankSerialnoInfoDao.selectMapByCurrMonth(reqMap);
	}
	@Override
	public int updateBankSerialMap(Map<String, Object> reqMap) {
		return bankSerialnoInfoDao.updateSelectiveMap(reqMap);
	}
	@Override
	public int insertBankSerialMap(Map<String, Object> reqMap) {
		return bankSerialnoInfoDao.insertSelectiveMap(reqMap);
	}
	@Override
	public Map<String, Object> selectOneBankAcc(String bankAccno) {
		if(!StringUtil.isEmpty(bankAccno)){
			Map<String, Object> reqMap = Maps.newHashMap();
			reqMap.put(BANK_ACC_NO, bankAccno);
			List<Map<String, Object>> resList = selectBankAccMap(reqMap);
			if(resList != null && resList.size() != 0){
				return resList.get(0);
			}else{
				return Maps.newHashMap();
			}
		}else{
			return Maps.newHashMap();
		}
	}

	@Override
	public Map<String, Object> selectOnePosCustMapById(String posCustId) {
		if(!StringUtil.isEmpty(posCustId)){
			Map<String, Object> reqMap = Maps.newHashMap();
			reqMap.put(POS_CUST_ID, posCustId);
			List<Map<String, Object>> resList = selectPosCustMap(reqMap);
			if(resList != null && resList.size() != 0){
				return resList.get(0);
			}else{
				return Maps.newHashMap();
			}
		}else{
			return Maps.newHashMap();
		}
	}
	@Override
	public Long countBankSerialNum(Map<String, Object> reqMap) {
		return bankSerialnoInfoDao.countMap(reqMap);
	}
	@Override
	public Long countPosSerialNum(Map<String, Object> reqMap) {
		return posSerialnoInfoDao.countMap(reqMap);
	}

	
	
	
	public TCreditApply queryCreditApply(String loanId){
		return tCreditApplyDao.queryCreditApplyInfo(loanId);	
	}
	@Override
	public List<Map<String, Object>> selectChannelPosCustSelectiveMap(
			Map<String, Object> reqMap) {
		return tChannelPoscustInfoDao.selectSelectiveMap(reqMap);
	}
	@Override
	public void insertChannelPosCustSelectiveMap(Map<String, Object> reqMap) {
		
		tChannelPoscustInfoDao.insertSelectvieMap(reqMap);
	}
	@Override
	public Map<String, Object> selectOneChannelPosCustSelectiveMap(
			Map<String, Object> reqMap) {
		List<Map<String, Object>> list = tChannelPoscustInfoDao.selectSelectiveMap(reqMap);
		if(ListUtil.isNotEmpty(list)){
			return list.get(0);
		}else{
			return Maps.newHashMap();
		}
	}
	@Override
	public Map<String, Object> selectOneChannelPosCustByEle(
			String channelPoscustId, String channel) {
		Map<String, Object> reqMap = Maps.newHashMap();
		if(!StringUtil.isEmpty(channelPoscustId)){
			reqMap.put(CHANNEL_POSCUST_ID, channelPoscustId);
		}
		if(!StringUtil.isEmpty(channel)){
			reqMap.put(CHANNEL, channel);
		}
		
		return selectOneChannelPosCustSelectiveMap(reqMap);
	}
	@Override
	public String selectLoanId(String stdshno, String channel) {
		Map<String, Object> reqMap = Maps.newHashMap();
		if(!StringUtil.isEmpty(stdshno) && !StringUtil.isEmpty(channel)){
			reqMap.put("channel", channel);
			reqMap.put("stdshno", stdshno);
			List<Map<String, Object>> creMap = tCreditApplyDao.selectSelectiveMap(reqMap);
			if(ListUtil.isNotEmpty(creMap)){
				return (String)creMap.get(0).get("loanId");
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	@Override
	public String getCustIdbyStdshno(String stdshno) {
		return tCreditApplyDao.getCustIdbyStdshno(stdshno);	
	}
	@Override
	public String getLoanIdbyStdshno(String stdshno) {
		return tCreditApplyDao.getLoanIdbyStdshno(stdshno);	
	}

	@Override
	public List<Map<String, Object>> queryChannelDetail(String stdshno, String stdmerno,
			String channel, Long startPage, Long pageSize) {
		if(StringUtil.isEmpty(stdmerno) || StringUtil.isEmpty(channel)){
			return Lists.newArrayList();
		}
		Map<String, Object> reqMap = Maps.newHashMap();
		if(!StringUtil.isEmpty(stdshno)){
			reqMap.put("stdshno", stdshno);
		}
		reqMap.put(STDMERNO, stdmerno);
		reqMap.put(CHANNEL, channel);
		reqMap.put(START_PAGE, startPage);
		reqMap.put(PAGE_SIZE, pageSize);
		return queryChannlDetailMap(reqMap);
	}
	@Override
	public List<Map<String, Object>> queryChannlDetailMap(
			Map<String, Object> reqMap) {
		return tChannelPoscustInfoDao.queryChannelDetail(reqMap);
	}
	@Override
	public Long countChannelDetail(String stdshno, String stdmerno, String channel) {
		Map<String, Object> reqMap = Maps.newHashMap();
		reqMap.put(STDMERNO, stdmerno);
		reqMap.put(CHANNEL, channel);
		if(!StringUtil.isEmpty(stdshno)){
			reqMap.put("stdshno", stdshno);
		}
		return countChannelDetailMap(reqMap);
	}
	@Override
	public Long countChannelDetailMap(Map<String, Object> reqMap) {
		return tChannelPoscustInfoDao.countChannelDetail(reqMap);
	}

	@Override
	public String getCustIdbyPosCustId(String posCustId) {
		return tCreditApplyDao.getCustIdbyPosCustId(posCustId);	
	}
	@Override
	public String getLoanIdbyPosCustId(String posCustId) {
		return tCreditApplyDao.getLoanIdbyPosCustId(posCustId);	
	}
	@Override
	public String getStdshnobyPosCustId(String loanId) {
		return tCreditApplyDao.getStdshnobyLoanId(loanId);	
	}
    @Override
    public int deletePosSerial(String loanId) {
        return posSerialnoInfoDao.deleteByLoanId(loanId);
    }
    @Override
    public Long countPosSerials(String loanId) {
        return posSerialnoInfoDao.countPosSerials(loanId);
    }

	public List<Map<String, Object>> queryListBySaleApp(Map<String, Object> queryMap) {
	    return tCreditApplyDao.selectDetailsBySaleApp(queryMap);
	}
    @Override
    public int updateApplyStatus(Map<String, Object> updateMap) {
        return tCreditApplyDao.updateApplyStatus(updateMap);
    }
    
    
    //获取业务数据--导出excel时使用
    @Override
    public List<Map<String, Object>> getBizData(Map<String, Object> reqMap){        
        return tCreditApplyDao.getBizData(reqMap);
    }
    
    public List<TPosSerialnoInfo> selectTPosByMap(Map<String, Object> reqMap){
        return posSerialnoInfoDao.selectTPosByMap(reqMap);
    }
    public TBankAccnoInfo selectByBankAccno(String bankAccno){
        return bankAccnoInfoDao.selectByPrimaryKey(bankAccno);
    }

}
