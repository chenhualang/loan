package com.hrbb.loan.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hrbb.loan.pos.biz.backstage.inter.IPoliceAndAICConnectBiz;
import com.hrbb.loan.pos.util.ListUtil;

@Controller
@RequestMapping("/aic")
public class AicController {
	
	@Autowired
	private IPoliceAndAICConnectBiz policeAndAICConnectBiz;
	
	@RequestMapping("/getAicInfo")
	public ModelAndView getAicInfo(@RequestParam(value="posCustId", required = false)String posCustId){
		ModelAndView mav = new ModelAndView();
		//获取所有的工商信息
		Map<String, List<Map<String, Object>>> resMap = policeAndAICConnectBiz.queryAicInfo(null, posCustId);
		if(resMap != null && !resMap.isEmpty()){
			if(ListUtil.isNotEmpty(resMap.get("orderListMap")) && resMap.get("orderListMap").get(0) != null){
				
				mav.addObject("orderNo", resMap.get("orderListMap").get(0).get("orderNo"));
				if(ListUtil.isNotEmpty(resMap.get("orderListMap")) && resMap.get("orderListMap").get(0).get("queryTime") != null){
					mav.addObject("queryTime", resMap.get("orderListMap").get(0).get("queryTime"));
					mav.addObject("finishTime",resMap.get("orderListMap").get(0).get("finishTime"));
				}
			}
			if(ListUtil.isNotEmpty(resMap.get("basicMap"))){
				mav.addObject("basic", resMap.get("basicMap"));
				mav.addObject("poscustName", resMap.get("basicMap").get(0).get("entName"));
			}
			if(resMap.get("alidebtMap") != null){
				mav.addObject("alidebt", resMap.get("alidebtMap"));
			}
			if(resMap.get("caseMap") != null){
				mav.addObject("caseinfo", resMap.get("caseMap"));
			}
			if(resMap.get("entinvMap") != null){
				mav.addObject("entinv", resMap.get("entinvMap"));
			}
			if(resMap.get("frinvMap") != null){
				mav.addObject("frinv", resMap.get("frinvMap"));
			}
			if(resMap.get("frpositionMap") != null){
				mav.addObject("frpositionMap", resMap.get("frpositionMap"));
			}
			if(resMap.get("personMap") != null){
				mav.addObject("person", resMap.get("personMap"));
			}
			if(resMap.get("punishBreakMap") != null){
				mav.addObject("punishBreak", resMap.get("punishBreakMap"));
			}
			if(resMap.get("punishedMap") != null){
				mav.addObject("punished", resMap.get("punishedMap"));
			}
			if(resMap.get("shareholderMap") != null){
				mav.addObject("shareholder", resMap.get("shareholderMap"));
			}
			if(resMap.get("liquidationMap") != null){
				mav.addObject("liquidation", resMap.get("liquidationMap"));
			}
			if(resMap.get("shareFroMap") != null){
				mav.addObject("shareFro", resMap.get("shareFroMap"));
			}
			
			if(resMap.get("sharesImpawnMap") != null){
				mav.addObject("sharesImpawn", resMap.get("sharesImpawnMap"));
				
			}
			if(resMap.get("alterMap") != null){
				mav.addObject("alter", resMap.get("alterMap"));
			}
			if(resMap.get("mordetailMap") != null){
				mav.addObject("mordetai", resMap.get("mordetailMap"));
			}
			if(resMap.get("morguaInfoMap") != null){
				mav.addObject("morguaInfo", resMap.get("morguaInfoMap"));
			}
		}
		mav.setViewName("aicinfo/aicinfo");
		
		return mav;
	}
}
