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
import com.hrbb.loan.pos.dao.entity.TApprovingScorecutUplimitConfig;
import com.hrbb.loan.pos.service.ModelParamConfigService;
import com.hrbb.loan.pos.util.constants.BusinessDictionaryConstants;
@Controller
@RequestMapping("/approvingScorecutUplimitConfig")
public class ApprovingScorecutUplimitConfigController {
    private Logger logger = LoggerFactory.getLogger(ApprovingScorecutUplimitConfigController.class);

    @Autowired
    private ModelParamConfigService ModelParamConfigService;

    @Autowired
    private ILoanPosBusinessCodeBiz loanPosBusinessCodeBiz;

    private List<Map<String, Object>> deleteflagList;
    
    //是否生效数据字典map<itemNo,itemName>
  	Map<String, String> deleteflagMap = Maps.newHashMap();
    
    @PostConstruct
    public void loadDictionary() {
        deleteflagList = loanPosBusinessCodeBiz.getItemNames(BusinessDictionaryConstants.Deleteflag);
        if(deleteflagList != null && deleteflagList.size() > 0){
    		for(Map<String, Object> map: deleteflagList){
    			deleteflagMap.put((String) map.get("itemNo"), (String) map.get("itemName"));
    		}
    	}
    }

    @RequestMapping("/queryApprovingScorecutUplimitConfig")
    public ModelAndView queryApprovingScorecutUplimitConfig(@RequestParam(value = "rows", required = false) Integer pageSize,
            @RequestParam(value = "page", required = false) Integer pageNo, HttpServletRequest request, PrintWriter out) {
        JSONObject json = new JSONObject();
        Map<String, Object> reqMap = Maps.newHashMap();
        if (StringUtil.isNotEmpty(request.getParameter("scorecut"))) {
            reqMap.put("scorecut", request.getParameter("scorecut"));
        }
        if (StringUtil.isNotEmpty(request.getParameter("deleted_flag"))) {
            reqMap.put("deleted_flag", request.getParameter("deleted_flag"));
        }
        reqMap.put("startPage", (pageNo - 1) * pageSize);
        reqMap.put("limit", pageSize);
        List<TApprovingScorecutUplimitConfig> lists = ModelParamConfigService.queryApprovingScorecutUplimitConfig(reqMap);
        if(lists != null && lists.size() > 0){
        	for(TApprovingScorecutUplimitConfig config: lists){
        		config.setDeleted_flag(deleteflagMap.get(config.getDeleted_flag()));
        	}
        }
        long total = ModelParamConfigService.countApprovingScorecutUplimitConfig(reqMap);
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

    @RequestMapping("/openAddApprovingScorecutUplimitConfig")
    public ModelAndView openAddApprovingScorecutUplimitConfigWindow() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("oprFlag", "0");// 0代表新增
        mav.addObject("deleteflagList", deleteflagList);
        mav.setViewName("paramconfig/detailApprovingScorecutUplimitConfigInfo");
        return mav;
    }

    /**
     * 新增机构
     * 
     * @param request
     * @param out
     * @return
     */
    @RequestMapping(value = "/addApprovingScorecutUplimitConfig", method = RequestMethod.POST)
    public ModelAndView addInstitutionWindow(HttpServletRequest request, PrintWriter out) {
        Map<String, Object> reqMap = Maps.newHashMap();
        if (StringUtil.isNotEmpty((String) request.getParameter("scorecut"))) {
            reqMap.put("scorecut", (String) request.getParameter("scorecut"));
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
            if (ModelParamConfigService.selectApprovingScorecutUplimitConfigByRegcode((String) reqMap.get("amt_uplimit")) != null) {
                out.print("该批复分数切割的记录已存在");
                return null;
            } else {
                int result = ModelParamConfigService.saveApprovingScorecutUplimitConfig(reqMap);
                if (result > 0) {
                    logger.info("批复分数切割配置信息新增成功,参数为[{}]", reqMap);
                    out.print("新增成功");
                } else {
                    logger.info("批复分数切割配置信息新增失败,参数为[{}]", reqMap);
                    out.print("系统异常，新增批复分数切割配置信息失败");
                }
            }
            return null;
        } catch (Exception e) {
            logger.error("新增批复分数切割配置信息失败", e);
            out.print("系统异常，新增批复分数切割配置信息失败");
            return null;
        }
    }

    /**
     * 修改风险区域配置信息窗口
     * 
     * @param orgId
     * @return
     */
    @RequestMapping("/openModifyApprovingScorecutUplimitConfigWindow")
    public ModelAndView openModifyApprovingScorecutUplimitConfigWindow(@RequestParam(value = "id", required = false) Integer id) {
        ModelAndView mav = new ModelAndView();
        if (id == null) {
            logger.info("记录编号为空");
            mav.addObject("oprFlag", "1");// 1代表修改
            mav.addObject("deleteflagList", deleteflagList);
            mav.setViewName("paramconfig/detailApprovingScorecutUplimitConfigInfo");
            return mav;
        }
        // 查询展业机构
        TApprovingScorecutUplimitConfig tApprovingScorecutUplimitConfig = ModelParamConfigService.selectApprovingScorecutUplimitConfigByID(id);
        mav.addObject("tApprovingScorecutUplimitConfig", tApprovingScorecutUplimitConfig);
        mav.addObject("oprFlag", "1");// 1代表修改
        mav.addObject("deleteflagList", deleteflagList);
        mav.setViewName("paramconfig/detailApprovingScorecutUplimitConfigInfo");
        return mav;
    }

    /**
     * 查询
     * 
     * @param id
     * @return
     */
    @RequestMapping("/approvingScorecutUplimitDetail")
    public ModelAndView approvingScorecutUplimitDetail(@RequestParam Integer id) {
        ModelAndView mav = new ModelAndView();
        // 查询展业机构
        TApprovingScorecutUplimitConfig tApprovingScorecutUplimitConfig = ModelParamConfigService.selectApprovingScorecutUplimitConfigByID(id);
        mav.addObject("tApprovingScorecutUplimitConfig", tApprovingScorecutUplimitConfig);
        mav.addObject("oprFlag", "3");
        mav.addObject("deleteflagList", deleteflagList);
        mav.setViewName("paramconfig/detailApprovingScorecutUplimitConfigInfo");
        return mav;
    }

    /**
     * 修改机构
     * 
     * @param request
     * @param out
     * @return
     */
    @RequestMapping(value = "/modifyApprovingScorecutUplimitConfigWindow", method = RequestMethod.POST)
    public ModelAndView modifyApprovingScorecutUplimitConfigWindow(HttpServletRequest request, PrintWriter out) {
        Map<String, Object> reqMap = Maps.newHashMap();
        if (StringUtil.isNotEmpty((String) request.getParameter("id"))) {
            reqMap.put("id", (String) request.getParameter("id"));
        }
        if (StringUtil.isNotEmpty((String) request.getParameter("scorecut"))) {
            reqMap.put("scorecut", (String) request.getParameter("scorecut"));
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
            int result = ModelParamConfigService.modifyApprovingScorecutUplimitConfig(reqMap);
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
    @RequestMapping(value = "/deleteApprovingScorecutUplimitConfig", method = RequestMethod.POST)
    public ModelAndView deleteApprovingScorecutUplimitConfig(@RequestParam(value = "id", required = false) String id, PrintWriter out) {
        Map<String, Object> reqMap = Maps.newHashMap();
        try {
            if (StringUtil.isNotEmpty(id)) {
                reqMap.put("id", Integer.parseInt(id));
            }
            int flag = ModelParamConfigService.deleteApprovingScorecutUplimitConfig(id);
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
