/**
 * 
 */
package com.hrbb.loan.controller;

import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hrbb.loan.pos.biz.backstage.inter.IGenericConfigBiz;
import com.hrbb.loan.pos.service.IGenericConfigService;
import com.hrbb.loan.pos.util.DateUtil;

/**
* <p>Title: GenericConfigController.java </p>
* <p>Description:  </p>
* <p>Copyright: Copyright (C) 2015 Hrbb Ltd. Co.</p> 
*     
* @author linzhaolin@hrbb.com.cn
* @version 
* @date 2015-4-16
*
* logs: 1. 
*/
@Controller
@RequestMapping("/genericConfig")
public class GenericConfigController {
	private Logger log = LoggerFactory.getLogger(GenericConfigController.class);
	
	@Autowired
	@Qualifier("genericConfigBiz")
	private IGenericConfigBiz gcBiz;
	
	@Autowired
    private IGenericConfigService genericConfigService;
	
	@RequestMapping("/getIssuerInfo")
	public ModelAndView getIssuerInfo(
			@RequestParam(value = "cardno", required = true) String cardno,
			PrintWriter out){
		
		/*execute db query*/
		Map<String,Object> issuer = gcBiz.getIssuerInfo(cardno);
		
		out.print(JSONObject.toJSON(issuer));
		
		return null;
	}
	
	@RequestMapping("/getRecInfo")
	public ModelAndView getRecInfo(
			@RequestParam(value = "contactNo", required = true) String contactNo,
			PrintWriter out){
		
		Map<String,Object> recInfo = null;
		if(contactNo!=null && contactNo.equals("13840095537")){
			recInfo = Maps.newHashMap();
			recInfo.put("recPerson", "直销业务");
			recInfo.put("recOrg", "融兴通达");
			recInfo.put("recManager", "");
		}else if(contactNo.indexOf("-")>=0){		//备案异常号码,以xxx-xxx的形式出现
			String[] var = contactNo.split("-",-1);		//机构号-手机号
			if(var[0].matches("[0-9]+")){	//数字校验
				int orgId = Integer.valueOf(var[0]);
				recInfo = gcBiz.getRecInfoById(orgId);
			}else{
				recInfo = Maps.newHashMap();
			}
		}else{
			/*execute db query*/
			recInfo = gcBiz.getRecInfo(contactNo);
		}
		
		out.print(JSONObject.toJSON(recInfo));
		
		return null;
	}
	
	@RequestMapping("/getWorkingDate")
	public ModelAndView getWorkingDate(
			@RequestParam(value = "sourceDate", required = true) String sourceDate,
			PrintWriter out){
		
		String workingDate = sourceDate;
		try {
			Date source = DateUtil.parse2Date(sourceDate);
			/*execute db query*/
			workingDate = genericConfigService.getNextWorkingDate(source);
			
		} catch (Exception e) {
			log.error("发生异常:" + e.getMessage());
		}finally{
			
			out.print(JSONObject.toJSON(workingDate));
		}
		return null;
	}
	
}
