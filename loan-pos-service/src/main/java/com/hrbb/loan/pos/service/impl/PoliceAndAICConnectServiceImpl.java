package com.hrbb.loan.pos.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.hrbb.loan.pos.dao.TAICAlidebtInfoDao;
import com.hrbb.loan.pos.dao.TAICAlterInfoDao;
import com.hrbb.loan.pos.dao.TAICBasicInfoDao;
import com.hrbb.loan.pos.dao.TAICCaseInfoDao;
import com.hrbb.loan.pos.dao.TAICEntinvInfoDao;
import com.hrbb.loan.pos.dao.TAICFrinvInfoDao;
import com.hrbb.loan.pos.dao.TAICFrpositionInfoDao;
import com.hrbb.loan.pos.dao.TAICLiguidationInfoDao;
import com.hrbb.loan.pos.dao.TAICMordetailInfoDao;
import com.hrbb.loan.pos.dao.TAICMorguaInfoDao;
import com.hrbb.loan.pos.dao.TAICOrderlistInfoDao;
import com.hrbb.loan.pos.dao.TAICPersonInfoDao;
import com.hrbb.loan.pos.dao.TAICPunishBreakInfoDao;
import com.hrbb.loan.pos.dao.TAICPunishedInfoDao;
import com.hrbb.loan.pos.dao.TAICShareHolderDao;
import com.hrbb.loan.pos.dao.TAICSharesFrostInfoDao;
import com.hrbb.loan.pos.dao.TAICSharesimpawnInfoDao;
import com.hrbb.loan.pos.dao.TPoliceInfoDao;
import com.hrbb.loan.pos.dao.entity.TAICAlterInfo;
import com.hrbb.loan.pos.dao.entity.TAICLiguidationInfo;
import com.hrbb.loan.pos.dao.entity.TAICMordetailInfo;
import com.hrbb.loan.pos.dao.entity.TAICMorguaInfo;
import com.hrbb.loan.pos.dao.entity.TAICSharesFrostInfo;
import com.hrbb.loan.pos.dao.entity.TAICSharesimpawnInfo;
import com.hrbb.loan.pos.service.PoliceAndAICConnectService;
import com.hrbb.loan.pos.util.ListUtil;
import com.hrbb.loan.pos.util.StringUtil;
@Service("policeAndAICConnectService")
public class PoliceAndAICConnectServiceImpl implements
		PoliceAndAICConnectService {
	
	private static final String POS_CUST_NAME = "posCustName";

	@Autowired
	private TPoliceInfoDao tPoliceInfoDao;
	
	@Autowired
	private TAICAlidebtInfoDao taicAlidebtInfoDao;
	
	@Autowired
	private TAICBasicInfoDao taicBasicInfoDao;
	
	@Autowired
	private TAICCaseInfoDao taicCaseInfoDao;
	
	@Autowired
	private TAICEntinvInfoDao taicEntinvInfoDao;
	
	@Autowired
	private TAICOrderlistInfoDao taicOrderlistInfoDao;
	
	@Autowired
	private TAICPersonInfoDao taicPersonInfoDao;
	
	@Autowired
	private TAICPunishBreakInfoDao taicPunishBreakInfoDao;
	
	@Autowired
	private TAICPunishedInfoDao taicPunishedInfoDao;
	
	@Autowired
	private TAICShareHolderDao taicShareHolderDao;
	
	@Autowired
	private TAICFrinvInfoDao taicFrinvInfoDao;
	
	@Autowired
	private TAICFrpositionInfoDao taicFrpositionInfoDao;
	
	@Autowired
	private TAICAlterInfoDao taicAlterInfoDao;
	
	@Autowired
	private TAICMordetailInfoDao taicMordetailInfoDao;
	
	@Autowired
	private TAICMorguaInfoDao taicMorguaInfoDao;
	
	
	@Autowired
	private TAICLiguidationInfoDao taicLiguidationInfoDao;
	
	@Autowired
	private TAICSharesFrostInfoDao taicSharesFrostInfoDao;
	
	@Autowired
	private TAICSharesimpawnInfoDao taicSharesimpawnInfoDao;

	@Override
	public List<Map<String, Object>> queryPoliceInfoList(
			Map<String, Object> reqMap) {
		return tPoliceInfoDao.selectSelectiveMap(reqMap);
	}

	@Override
	public Map<String, Object> queryOnePoliceInfo(String custName, String idNo,
			Date queryTime) {
		Map<String, Object> reqMap = Maps.newHashMap();
		reqMap.put("custName", custName);
		reqMap.put("idNo", idNo);
		reqMap.put("queryTime", queryTime);
		List<Map<String, Object>> reqMaps = queryPoliceInfoList(reqMap);
		if(ListUtil.isNotEmpty(reqMaps)){
			return reqMaps.get(0);
		}else{
			return Maps.newHashMap();
		}
	}

	@Override
	public void insertPoliceInfo(Map<String, Object> reqMap) {
		tPoliceInfoDao.insertSelectiveMap(reqMap);
	}

	@Override
	public void insertAICAlidebtInfo(Map<String, Object> reqMap) {
		taicAlidebtInfoDao.insertSelectiveMap(reqMap);
	}

	@Override
	public void insertAICBasicInfo(Map<String, Object> reqMap) {
		taicBasicInfoDao.insertSelectiveMap(reqMap);
	}

	@Override
	public void insertAICCaseInfo(Map<String, Object> reqMap) {
		taicCaseInfoDao.insertSelectiveMap(reqMap);
	}

	@Override
	public void insertAICEntinvInfo(Map<String, Object> reqMap) {
		taicEntinvInfoDao.insertSelectiveMap(reqMap);
	}

	@Override
	public void insertAICOrderlistInfo(Map<String, Object> reqMap) {
		taicOrderlistInfoDao.insertSelectiveMap(reqMap);
	}

	@Override
	public void insertAICPersonInfo(Map<String, Object> reqMap) {
		taicPersonInfoDao.insertSelectiveMap(reqMap);
	}

	@Override
	public void insertAICPunishBreakInfo(Map<String, Object> reqMap) {
		taicPunishBreakInfoDao.insertSelectiveMap(reqMap);
	}

	@Override
	public void insertAICPunishedInfo(Map<String, Object> reqMap) {
		taicPunishedInfoDao.insertSelectiveMap(reqMap);
		
	}

	@Override
	public void insertAICShareHolder(Map<String, Object> reqMap) {
		taicShareHolderDao.insertSelectiveMap(reqMap);
	}

	@Override
	public List<Map<String, Object>> queryAICOrderlist(
			Map<String, Object> reqMap) {
		return taicOrderlistInfoDao.queryMap(reqMap);
	}

	@Override
	public Map<String, Object> queryOneAICOrderlist(String posCustName) {
		Map<String, Object> reqMap = Maps.newHashMap();
		reqMap.put(POS_CUST_NAME, posCustName);
		List<Map<String, Object>> resList = queryAICOrderlist(reqMap);
		if(ListUtil.isNotEmpty(resList)){
			return resList.get(0);
		}else{
			return Maps.newHashMap();
		}
		
	}

	@Override
	public void insertAICFrinvInfo(Map<String, Object> reqMap) {
		
		taicFrinvInfoDao.insertFrinvInfoMap(reqMap);
	}

	@Override
	public void insertAICFrpositionInfo(Map<String, Object> reqMap) {
		
		taicFrpositionInfoDao.insertSelectiveMap(reqMap);
	}

	@Override
	public List<Map<String, Object>> selectByCertNo(Map<String, Object> reqMap) {
		return tPoliceInfoDao.selectByCertNo(reqMap);
	}

    @Override
    public Map<String, List<Map<String, Object>>> queryAicInfo(String posCustName, String posCustId) {
        Map<String,List<Map<String,Object>>> aicResultList = Maps.newHashMap();
        
        Map<String, Object> reqMap = Maps.newHashMap();
        reqMap.put("posCustName", posCustName);
        reqMap.put("posCustId", posCustId);
        //查询t_aic_orderlist_info表，获取商户最新工商信息
        List<Map<String,Object>> orderList = taicOrderlistInfoDao.queryMap(reqMap);
        if(ListUtil.isNotEmpty(orderList)){
        	Map<String,Object> orderListMap = orderList.get(0);
        	if(orderList.removeAll(orderList)){
        		orderList.add(orderListMap);
        	}
        	//如果工商信息为null，返回null
        	if(orderListMap ==null || orderListMap.get("orderNo") == null){
        		return null;
        	}
        	String orderNo = (String)orderListMap.get("orderNo");
        	//工商信息结果集定义
        	List<Map<String,Object>> alidebtMap = null;//阿里贷款
        	List<Map<String,Object>> basicMap = null;
        	List<Map<String,Object>> caseMap = null;
        	List<Map<String,Object>> entinvMap = null;
        	List<Map<String,Object>> frinvMap = null;
        	List<Map<String,Object>> frpositionMap = null;
        	List<Map<String,Object>> personMap = null;
        	List<Map<String,Object>> punishBreakMap = null;
        	List<Map<String,Object>> punishedMap = null;   
        	List<Map<String,Object>> shareholderMap = null;
        	
        	if(getReqMap(orderNo) != null){
        		//查询t_aic_alidebt_info
        		alidebtMap = taicAlidebtInfoDao.selectBySelectiveMap(getReqMap(orderNo));
        		//查询t_aic_basic_info
        		basicMap = taicBasicInfoDao.selectBySelectiveMap(getReqMap(orderNo));
        		//查询t_aic_case_info
        		caseMap = taicCaseInfoDao.selectBySelectiveMap(getReqMap(orderNo));
        		//查询t_aic_entinv_info
        		entinvMap = taicEntinvInfoDao.selectBySelectiveMap(getReqMap(orderNo));
        		//查询t_aic_frinv_info
        		frinvMap = taicFrinvInfoDao.selectBySelectiveMap(getReqMap(orderNo));
        		//查询t_aic_frposition_info
        		frpositionMap = taicFrpositionInfoDao.selectBySelectiveMap(getReqMap(orderNo));
        		//查询t_aic_person_info
        		personMap = taicPersonInfoDao.selectBySelectiveMap(getReqMap(orderNo));
        		//查询t_aic_punish_break_info
        		punishBreakMap = taicPunishBreakInfoDao.selectBySelectiveMap(getReqMap(orderNo));
        		//查询t_aic_punished_info
        		punishedMap = taicPunishedInfoDao.selectBySelectiveMap(getReqMap(orderNo));
        		//t_aic_shareholder_info
        		shareholderMap = taicShareHolderDao.selectBySelectiveMap(getReqMap(orderNo));
        		
        		List<Map<String, Object>> liquidationMaps = taicLiguidationInfoDao.selectSelective(getReqMap(orderNo));
        		
        		List<Map<String, Object>> shareFroMaps = taicSharesFrostInfoDao.selectSelective(getReqMap(orderNo));
        		
        		List<Map<String, Object>> sharesImpawnMaps = taicSharesimpawnInfoDao.selectSelective(getReqMap(orderNo));
        		
        		List<Map<String, Object>> alterMaps = taicAlterInfoDao.selectSelective(getReqMap(orderNo));
        		
        		List<Map<String, Object>> mordetailMaps = taicMordetailInfoDao.selectSelective(getReqMap(orderNo));
        		
        		List<Map<String, Object>> morguaInfoMaps = taicMorguaInfoDao.selectSelective(getReqMap(orderNo));
        		
        		//赋值
        		aicResultList.put("orderListMap", orderList);
        		aicResultList.put("alidebtMap", alidebtMap);
        		aicResultList.put("basicMap", basicMap);
        		aicResultList.put("caseMap",caseMap);
        		aicResultList.put("entinvMap", entinvMap);
        		aicResultList.put("frinvMap", frinvMap);
        		aicResultList.put("frpositionMap", frpositionMap);
        		aicResultList.put("personMap", personMap);
        		aicResultList.put("punishBreakMap", punishBreakMap);
        		aicResultList.put("punishedMap", punishedMap);
        		aicResultList.put("shareholderMap", shareholderMap);
        		aicResultList.put("liquidationMap", liquidationMaps);
        		aicResultList.put("shareFroMap", shareFroMaps);
        		aicResultList.put("sharesImpawnMap", sharesImpawnMaps);
        		aicResultList.put("alterMap", alterMaps);
        		aicResultList.put("mordetailMap", mordetailMaps);
        		aicResultList.put("morguaInfoMap", morguaInfoMaps);
        	}
        	return aicResultList;
        	
        }else{
        	return Maps.newHashMap();
        	
        }
    }

    /**
     * 构建请求参数map
     * 
     * @param orderNo
     * @return
     */
    private Map<String,Object> getReqMap(String orderNo) {
        Map<String, Object> reqMap = Maps.newHashMap();
        reqMap.put("orderNo", orderNo);
        return reqMap;
    }

	@Override
	public void insertShareFrost(TAICSharesFrostInfo taicSharesFrostInfo) {
		taicSharesFrostInfoDao.insertSelective(taicSharesFrostInfo);
		
	}

	@Override
	public List<Map<String, Object>> getSharesFrost(Map<String, Object> reqMap) {
		return taicSharesFrostInfoDao.selectSelective(reqMap);
	}

	@Override
	public void insertSharesimpaw(TAICSharesimpawnInfo taicSharesimpawnInfo) {
		 taicSharesimpawnInfoDao.insertSelective(taicSharesimpawnInfo);
	}

	@Override
	public List<Map<String, Object>> getSharesimpawnInfo(
			Map<String, Object> reqMap) {
		return taicSharesimpawnInfoDao.selectSelective(reqMap);
	}

	@Override
	public void insertMordetail(TAICMordetailInfo taicMordetailInfo) {
		taicMordetailInfoDao.insertSelective(taicMordetailInfo);
		
	}

	@Override
	public List<Map<String, Object>> getMordetail(Map<String, Object> reqMap) {
		return taicMordetailInfoDao.selectSelective(reqMap);
	}

	@Override
	public void insertMorguaInfo(TAICMorguaInfo taicMorguaInfo) {
		taicMorguaInfoDao.insertSelective(taicMorguaInfo);
		
	}

	@Override
	public List<Map<String, Object>> getMorguaInfo(Map<String, Object> reqMap) {
		return taicMorguaInfoDao.selectSelective(reqMap);
	}

	@Override
	public void insetAlterInfo(TAICAlterInfo taicAlterInfo) {

		 taicAlterInfoDao.insertSelective(taicAlterInfo);
	}

	@Override
	public List<Map<String, Object>> getAlterInfo(Map<String, Object> reqMap) {
		return taicAlterInfoDao.selectSelective(reqMap);
	}

	@Override
	public void insertLiquidationInfo(TAICLiguidationInfo taicLiguidationInfo) {

		taicLiguidationInfoDao.insertSelective(taicLiguidationInfo);
	}

	@Override
	public List<Map<String, Object>> getLiquidationInfo(
			Map<String, Object> reqMap) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 更新用户
	 */
	@Override
	public int updatePoliceInfo(Map<String, Object> reqMap) {
		return tPoliceInfoDao.updateSelectiveMap(reqMap);
	}
	
	
}
