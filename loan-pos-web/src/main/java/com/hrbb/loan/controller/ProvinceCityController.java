package com.hrbb.loan.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.hrbb.loan.pos.biz.backstage.inter.ILoanPosBusinessCodeBiz;
import com.hrbb.loan.pos.util.constants.BusinessDictionaryConstants;

/**
 *<h1></h1>
 *@author Johnson Song
 *@version Id: ProvinceCityController.java, v 1.0 2015-3-11 下午5:27:03 Johnson Song Exp
 */
@Controller
@RequestMapping("/provinceCity")
public class ProvinceCityController {
	
	@Autowired
	private ILoanPosBusinessCodeBiz loanPosBusinessCodeBiz;
	
	@RequestMapping("/getCity")
	public ModelAndView getCity(@RequestParam(value="itemNo", required = false)String itemNo, PrintWriter out){
		Map<String, Object> reqMap = Maps.newHashMap();
		reqMap.put("itemNoLike", itemNo.trim().substring(0, 2)+"__00");
		if(!itemNo.matches("(441900|442000|620200)")) reqMap.put("notItemNo", itemNo);		//4个无辖区的地级市可见自身
		reqMap.put("codeNo", BusinessDictionaryConstants.AdminDivision);
		List<Map<String, Object>> resList = loanPosBusinessCodeBiz.getSeletiveMap(reqMap);
		out.print(JSON.toJSONString(resList));
		return null;
	}
	
	@RequestMapping("/getDistrict")
	public ModelAndView getDistrict(@RequestParam(value="itemNo", required = false)String itemNo, PrintWriter out){
		Map<String, Object> reqMap = Maps.newHashMap();
		//reqMap.put("itemNoLike", itemNo.trim().substring(0, 2)+"____");
		//reqMap.put("notItemNo", itemNo);
		//reqMap.put("notItemNo2", itemNo.trim().substring(0, 2)+"0000");
		//modefied by zhaolin for error codelen
		reqMap.put("itemNoLike", itemNo.trim().substring(0, 4)+"__");
		if(!itemNo.matches("(441900|442000|620200)")) {		//4个无辖区的地级市可见自身
			reqMap.put("notItemNo", itemNo);
			reqMap.put("notItemNo2", itemNo.trim().substring(0, 4)+"00");
		}
		reqMap.put("codeNo", BusinessDictionaryConstants.AdminDivision);
		List<Map<String, Object>> resList = loanPosBusinessCodeBiz.getSeletiveMap(reqMap);
		out.print(JSON.toJSONString(resList));
		return null;
	}
}
