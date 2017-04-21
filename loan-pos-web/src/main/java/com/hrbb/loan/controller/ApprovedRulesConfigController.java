package com.hrbb.loan.controller;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hrbb.loan.pos.biz.backstage.inter.ILoanPosBusinessCodeBiz;
import com.hrbb.loan.pos.dao.entity.TApprovedRulesConfig;
import com.hrbb.loan.pos.service.ModelParamConfigService;
import com.hrbb.loan.pos.util.constants.BusinessDictionaryConstants;
@Controller
@RequestMapping("/approvedRulesConfig")
public class ApprovedRulesConfigController {
    private Logger logger = LoggerFactory.getLogger(ApprovedRulesConfigController.class);

    @Autowired
    private ModelParamConfigService ModelParamConfigService;

    @Autowired
    private ILoanPosBusinessCodeBiz loanPosBusinessCodeBiz;

    private List<Map<String, Object>> provinceList;
    
    private List<Map<String, Object>> productList;
    
    private List<Map<String, Object>> deleteflagList;

    //渠道
    private List<Map<String, Object>> channelList;
    
    //地区数据字典map<itemNo,itemName>
  	Map<String, String> provinceMap = Maps.newHashMap();
    
    //产品代码字典map<itemNo,itemName>
  	Map<String, String> productNoMap = Maps.newHashMap();
  	
    //渠道数据字典map<itemNo,itemName>
  	Map<String, String> channelMap = Maps.newHashMap();
  	
    //是否生效数据字典map<itemNo,itemName>
  	Map<String, String> deleteflagMap = Maps.newHashMap();
    
    @PostConstruct
    public void loadDictionary() {
        provinceList = loanPosBusinessCodeBiz.getProvinceCityOrDic("__0000");
        productList = loanPosBusinessCodeBiz.getItemNames(BusinessDictionaryConstants.ProductNo);
        channelList = loanPosBusinessCodeBiz.getItemNames(BusinessDictionaryConstants.BizChannel);
        deleteflagList = loanPosBusinessCodeBiz.getItemNames(BusinessDictionaryConstants.Deleteflag);
        if(provinceList != null && provinceList.size() > 0){
    		for(Map<String, Object> map: provinceList){
    			provinceMap.put((String) map.get("itemNo"), (String) map.get("itemName"));
    		}
    	}
        if(productList != null && productList.size() > 0){
        	for(Map<String, Object> map: productList){
        		productNoMap.put((String) map.get("itemNo"), (String) map.get("itemName"));
        	}
        }
        if(channelList != null && channelList.size() > 0){
        	for(Map<String, Object> map: channelList){
        		channelMap.put((String) map.get("itemNo"), (String) map.get("itemName"));
        	}
        }
        if(deleteflagList != null && deleteflagList.size() > 0){
    		for(Map<String, Object> map: deleteflagList){
    			deleteflagMap.put((String) map.get("itemNo"), (String) map.get("itemName"));
    		}
    	}
    }

    @RequestMapping("/queryApprovedRulesConfig")
    public ModelAndView queryApprovedRulesConfig(@RequestParam(value = "rows", required = false) Integer pageSize,
            @RequestParam(value = "page", required = false) Integer pageNo, HttpServletRequest request, PrintWriter out) {
        JSONObject json = new JSONObject();
        Map<String, Object> reqMap = Maps.newHashMap();
        if (StringUtil.isNotEmpty(request.getParameter("ruleNo"))) {
            reqMap.put("ruleNo", "%" + request.getParameter("ruleNo").trim() + "%");
        }
        if (StringUtil.isNotEmpty(request.getParameter("prodId"))) {
            reqMap.put("prodId", request.getParameter("prodId"));
        }
        reqMap.put("startPage", (pageNo - 1) * pageSize);
        reqMap.put("limit", pageSize);
        List<TApprovedRulesConfig> lists = ModelParamConfigService.queryApprovedRulesConfig(reqMap);
        if(lists != null && lists.size() > 0){
        	for(TApprovedRulesConfig config: lists){
        		config.setRegion(provinceMap.get(config.getRegion()));
        		config.setChannel(channelMap.get(config.getChannel()));
        		config.setProdId(productNoMap.get(config.getProdId()));
        		config.setDeleted_flag(deleteflagMap.get(config.getDeleted_flag()));
        	}
        }
        long total = ModelParamConfigService.countApprovedRulesConfig(reqMap);
        if (null != lists && lists.size() > 0) {
            json.put("total", total);
            json.put("rows", lists);
        } else {
            json.put("total", 0);
            json.put("rows", lists);
        }
        out.write(json.toJSONString());
        return null;
    }

    @RequestMapping("/openAddApprovedRulesConfig")
    public ModelAndView openAddApprovedRulesConfigWindow() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("oprFlag", "0");// 0代表新增
        mav.addObject("productList", productList);
        mav.addObject("deleteflagList", deleteflagList);
        mav.addObject("provinceList", provinceList);
        mav.addObject("channelList", channelList);
        mav.setViewName("paramconfig/detailApprovedRulesConfigInfo");
        return mav;
    }

    /**
     * 新增机构
     * 
     * @param request
     * @param out
     * @return
     */
    @RequestMapping(value = "/addApprovedRulesConfig", method = RequestMethod.POST)
    public ModelAndView addInstitutionWindow(HttpServletRequest request, PrintWriter out) {
        Map<String, Object> reqMap = Maps.newHashMap();
        if (StringUtil.isNotEmpty((String) request.getParameter("ruleNo"))) {
            reqMap.put("ruleNo", (String) request.getParameter("ruleNo"));
        }
        if (StringUtil.isNotEmpty((String) request.getParameter("prodId"))) {
            reqMap.put("prodId", (String) request.getParameter("prodId"));
        }
        if (StringUtil.isNotEmpty((String) request.getParameter("channel"))) {
            reqMap.put("channel", (String) request.getParameter("channel"));
        }
        if (StringUtil.isNotEmpty((String) request.getParameter("region"))) {
            reqMap.put("region", (String) request.getParameter("region"));
        }
        if (StringUtil.isNotEmpty((String) request.getParameter("age"))) {
            reqMap.put("age", (String) request.getParameter("age"));
        }
        if (StringUtil.isNotEmpty((String) request.getParameter("amt_uplimit"))) {
            reqMap.put("amt_uplimit", (String) request.getParameter("amt_uplimit"));
        }
        if (StringUtil.isNotEmpty((String) request.getParameter("deleted_flag"))) {
            reqMap.put("deleted_flag", (String) request.getParameter("deleted_flag"));
        }
        if (StringUtil.isNotEmpty((String) request.getParameter("valid_date"))) {
            reqMap.put("valid_date", (String) request.getParameter("valid_date"));
        }
        if (StringUtil.isNotEmpty((String) request.getParameter("invalid_date"))) {
            reqMap.put("invalid_date", (String) request.getParameter("invalid_date"));
        }
        // 插入创建时间和更新时间
        reqMap.put("create_date", new Date());
        reqMap.put("modify_date", new Date());
        try {
            if (ModelParamConfigService.selectApprovedRulesConfigByRegcode((String) reqMap.get("amt_uplimit")) != null) {
                out.print("该批复规则的记录已存在");
                return null;
            } else {
                int result = ModelParamConfigService.saveApprovedRulesConfig(reqMap);
                if (result > 0) {
                    logger.info("批复规则配置信息新增成功,参数为[{}]", reqMap);
                    out.print("新增成功");
                } else {
                    logger.info("批复规则配置信息新增失败,参数为[{}]", reqMap);
                    out.print("系统异常，新增批复规则配置信息失败");
                }
            }
            return null;
        } catch (Exception e) {
            logger.error("新增批复规则配置信息失败", e);
            out.print("系统异常，新增批复规则配置信息失败");
            return null;
        }
    }

    /**
     * 修改风险区域配置信息窗口
     * 
     * @param orgId
     * @return
     */
    @RequestMapping("/approvedRulesConfigDetail")
    public ModelAndView approvedRulesConfigDetail(@RequestParam Integer id) {
        ModelAndView mav = new ModelAndView();
        // 查询展业机构
        TApprovedRulesConfig tApprovedRulesConfig = ModelParamConfigService.selectApprovedRulesConfigByID(id);
        mav.addObject("tApprovedRulesConfig", tApprovedRulesConfig);
        mav.addObject("oprFlag", "3");
        mav.addObject("productList", productList);
        mav.addObject("deleteflagList", deleteflagList);
        mav.addObject("provinceList", provinceList);
        mav.setViewName("paramconfig/detailApprovedRulesConfigInfo");
        return mav;
    }

    /**
     * 修改风险区域配置信息窗口
     * 
     * @param orgId
     * @return
     */
    @RequestMapping("/openModifyApprovedRulesConfigWindow")
    public ModelAndView openModifyApprovedRulesConfigWindow(@RequestParam(value = "id", required = false) Integer id) {
        ModelAndView mav = new ModelAndView();
        if (id == null) {
            logger.info("记录编号为空");
            mav.addObject("oprFlag", "1");// 1代表修改
            mav.addObject("productList", productList);
            mav.addObject("deleteflagList", deleteflagList);
            mav.addObject("provinceList", provinceList);
            mav.setViewName("paramconfig/detailApprovedRulesConfigInfo");
            return mav;
        }
        // 查询展业机构
        TApprovedRulesConfig tApprovedRulesConfig = ModelParamConfigService.selectApprovedRulesConfigByID(id);
        mav.addObject("tApprovedRulesConfig", tApprovedRulesConfig);
        mav.addObject("oprFlag", "1");// 1代表修改
        mav.addObject("productList", productList);
        mav.addObject("deleteflagList", deleteflagList);
        mav.addObject("provinceList", provinceList);
        mav.addObject("channelList", channelList);
        mav.setViewName("paramconfig/detailApprovedRulesConfigInfo");
        return mav;
    }

    /**
     * 修改机构
     * 
     * @param request
     * @param out
     * @return
     */
    @RequestMapping(value = "/modifyApprovedRulesConfigWindow", method = RequestMethod.POST)
    public ModelAndView modifyApprovedRulesConfigWindow(HttpServletRequest request, PrintWriter out) {
        Map<String, Object> reqMap = Maps.newHashMap();
        if (StringUtil.isNotEmpty((String) request.getParameter("id"))) {
            reqMap.put("id", (String) request.getParameter("id"));
        }
        if (StringUtil.isNotEmpty((String) request.getParameter("ruleNo"))) {
            reqMap.put("ruleNo", (String) request.getParameter("ruleNo"));
        }
        if (StringUtil.isNotEmpty((String) request.getParameter("prodId"))) {
            reqMap.put("prodId", (String) request.getParameter("prodId"));
        }
        if (StringUtil.isNotEmpty((String) request.getParameter("channel"))) {
            reqMap.put("channel", (String) request.getParameter("channel"));
        }
        if (StringUtil.isNotEmpty((String) request.getParameter("region"))) {
            reqMap.put("region", (String) request.getParameter("region"));
        }
        if (StringUtil.isNotEmpty((String) request.getParameter("age"))) {
            reqMap.put("age", (String) request.getParameter("age"));
        }
        if (StringUtil.isNotEmpty((String) request.getParameter("amt_uplimit"))) {
            reqMap.put("amt_uplimit", (String) request.getParameter("amt_uplimit"));
        }
        if (StringUtil.isNotEmpty((String) request.getParameter("deleted_flag"))) {
            reqMap.put("deleted_flag", (String) request.getParameter("deleted_flag"));
        }
        if (StringUtil.isNotEmpty((String) request.getParameter("valid_date"))) {
            reqMap.put("valid_date", (String) request.getParameter("valid_date"));
        }
        if (StringUtil.isNotEmpty((String) request.getParameter("invalid_date"))) {
            reqMap.put("invalid_date", (String) request.getParameter("invalid_date"));
        }
        reqMap.put("modify_date", new Date());
        try {
            int result = ModelParamConfigService.modifyApprovedRulesConfig(reqMap);
            if (result > 0) {
                logger.info("修改成功,参数为[{}]", reqMap);
                out.print("修改成功");
            } else {
                logger.info("修改失败,参数为[{}]", reqMap);
                out.print("系统异常，修改失败");
            }
        } catch (Exception e) {
            logger.error("修改失败", e);
            out.print("系统异常，修改失败");
        }
        return null;
    }

    /**
     * 删除
     * 
     * @param orgId
     * @param out
     * @return
     */
    @RequestMapping(value = "/deleteApprovedRulesConfig", method = RequestMethod.POST)
    public ModelAndView deleteApprovedRulesConfig(@RequestParam(value = "id", required = false) String id, PrintWriter out) {
        Map<String, Object> reqMap = Maps.newHashMap();
        try {
            if (StringUtil.isNotEmpty(id)) {
                reqMap.put("id", Integer.parseInt(id));
            }
            int flag = ModelParamConfigService.deleteApprovedRulesConfig(id);
            if (flag > 0) {
                out.print("删除成功");
                return null;
            } else {
                out.print("删除失败");
                return null;
            }
        } catch (NumberFormatException e) {
            logger.error("系统异常", e);
            out.print("删除失败");
            return null;
        }
    }

}
