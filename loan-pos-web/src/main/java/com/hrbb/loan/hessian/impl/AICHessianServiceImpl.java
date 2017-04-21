package com.hrbb.loan.hessian.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import com.hrbb.loan.hessian.inter.IAICHessianService;
import com.hrbb.loan.pos.biz.backstage.inter.IPoliceAndAICConnectBiz;
import com.hrbb.loan.pos.util.DateUtil;
import com.hrbb.loan.pos.util.constants.CreditInvestigateConstants;
import com.hrbb.sh.framework.ConnectAICService;

@Component("aicHessianService")
public class AICHessianServiceImpl implements IAICHessianService{
	
	
	
	private final String POSCUSTNAME = "posCustName";
	
	private final String POSCUSTID = "posCustId";
	
	private final String URL = "url";
	
	private final String REQXML = "reqXml";
	
	private final String RESXML = "resXml";
	
	private final String respMsg = "respMsg";
	
	private final String respCode = "respCode";
	
	@Autowired
	private IPoliceAndAICConnectBiz policeAndAICConnectBiz;
	
	@Autowired
	private ConnectAICService connectAICService;
	
	private Logger logger = LoggerFactory.getLogger(AICHessianServiceImpl.class);

	@Override
	public Map<String, String> getCustAICInfo(
			Map<String, ? extends Object> reqMap)  throws Exception{
//		String posCustName = (String) reqMap.get(POSCUSTNAME);
		//走测试
		String posCustName = (String) reqMap.get(POSCUSTNAME);
		String posCustId = (String) reqMap.get(POSCUSTID);
		String confirmFlag = (String) reqMap.get("confirmFlag");
		//先查询是否查询过工商信息
		if("0".equals(confirmFlag)){
			if(!policeAndAICConnectBiz.hasAICInfo(posCustName)){
				logger.debug(posCustName + "没有调过工商");
				//如果没有查询过，则要查询
				Map<String, Object> rMap = Maps.newHashMap();
				rMap.put(POSCUSTNAME, posCustName);
				rMap.put(POSCUSTID, posCustId);
				//获取请求报文
				Map<String, String> postMap = policeAndAICConnectBiz.getAICReqXml(rMap);
				if(postMap.isEmpty()){
					logger.error("生成发送报文异常");
					//发生异常
					Map<String, String> resMap = Maps.newHashMap();
					resMap.put(respCode, "99");
					resMap.put(respMsg, "发生异常");
					return resMap;
				}
				//发送请求报文
				String queryTime = DateUtil.getNowTime();
				logger.debug("--调工商hessian前");
				//logger.debug("发送报文为：" + postMap.get(REQXML));
				String resXml = connectAICService.connectAIC(postMap.get(URL), postMap.get(REQXML));
//				logger.debug("返回报文为:"+resXml);
				//解析返回报文
				Map<String, String> getAicInfoMap = Maps.newHashMap();
				getAicInfoMap.put(POSCUSTNAME, posCustName);
				getAicInfoMap.put(POSCUSTID, posCustId);
				getAicInfoMap.put(RESXML, resXml);
				getAicInfoMap.put("queryTime", queryTime);
				return policeAndAICConnectBiz.getAICInfo(getAicInfoMap);
			}else{
				logger.debug(posCustName + "已经调过工商");
				Map<String, String> resMap = Maps.newHashMap();
				resMap.put(respCode, "00");
				resMap.put(respMsg, "0");
				return resMap;
			}
		}else if("1".equals(confirmFlag)){
			logger.debug(posCustName + "已查过工商信息，但是XX运营还想再查一遍");
			//如果没有查询过，则要查询
			Map<String, Object> rMap = Maps.newHashMap();
			rMap.put(POSCUSTNAME, posCustName);
			//获取请求报文
			Map<String, String> postMap = policeAndAICConnectBiz.getAICReqXml(rMap);
			if(postMap.isEmpty()){
				logger.error("生成发送报文异常");
				//发生异常
				Map<String, String> resMap = Maps.newHashMap();
				resMap.put(respCode, "99");
				resMap.put(respMsg, "发生异常");
				return resMap;
			}
			//发送请求报文
			logger.debug("--调工商hessian前");
			String resXml = connectAICService.connectAIC(postMap.get(URL), postMap.get(REQXML));
//			logger.debug("返回报文为:"+resXml);
			//解析返回报文
			Map<String, String> getAicInfoMap = Maps.newHashMap();
			getAicInfoMap.put(POSCUSTNAME, posCustName);
			getAicInfoMap.put(POSCUSTID, posCustId);
			getAicInfoMap.put(RESXML, resXml);
			getAicInfoMap.put("queryTime", DateUtil.getNowTime());		//add by Lin,Zhaolin at 20150731
			return policeAndAICConnectBiz.getAICInfo(getAicInfoMap);
		}else{
			logger.debug("confirmFlag=" + confirmFlag + "没有该状态");
			Map<String, String> resMap = Maps.newHashMap();
			resMap.put(respCode, "99");
			resMap.put(respMsg, "发生异常");
			return resMap;
		}
	}
	
	
	
}
