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
import com.hrbb.loan.pos.dao.entity.TRiskZoneConfig;
import com.hrbb.loan.pos.service.ModelParamConfigService;
import com.hrbb.loan.pos.util.constants.BusinessDictionaryConstants;
@Controller
@RequestMapping("/modelParamConfig")
public class ParamConfigController {
    private Logger logger = LoggerFactory.getLogger(ParamConfigController.class);

    @Autowired
    private ModelParamConfigService ModelParamConfigService;

    @Autowired
    private ILoanPosBusinessCodeBiz loanPosBusinessCodeBiz;

    private List<Map<String, Object>> provinceList;

    private List<Map<String, Object>> risktypeList;

    private List<Map<String, Object>> risktypecodeList;

    private List<Map<String, Object>> deleteflagList;

    // 地区数据字典map<itemNo,itemName>
    Map<String, String> provinceMap = Maps.newHashMap();

    // 风险等级数据字典map<itemNo,itemName>
    Map<String, String> risktypeMap = Maps.newHashMap();

    // 是否生效数据字典map<itemNo,itemName>
    Map<String, String> deleteflagMap = Maps.newHashMap();

    @PostConstruct
    public void loadDictionary() {
        provinceList = loanPosBusinessCodeBiz.getProvinceCityOrDic("__0000");
        risktypeList = loanPosBusinessCodeBiz.getItemNames(BusinessDictionaryConstants.RegionRiskType);
        risktypecodeList = loanPosBusinessCodeBiz.getItemNames(BusinessDictionaryConstants.AdminDivision);
        deleteflagList = loanPosBusinessCodeBiz.getItemNames(BusinessDictionaryConstants.YesNo);
        if (provinceList != null && provinceList.size() > 0) {
            for (Map<String, Object> map : provinceList) {
                provinceMap.put((String) map.get("itemNo"), (String) map.get("itemName"));
            }
        }
        if (risktypeList != null && risktypeList.size() > 0) {
            for (Map<String, Object> map : risktypeList) {
                risktypeMap.put((String) map.get("itemNo"), (String) map.get("itemName"));
            }
        }
        if (deleteflagList != null && deleteflagList.size() > 0) {
            for (Map<String, Object> map : deleteflagList) {
                deleteflagMap.put((String) map.get("itemNo"), (String) map.get("itemName"));
            }
        }
    }

    @RequestMapping("/queryRiskZoneConfig")
    public ModelAndView queryRiskZoneConfig(@RequestParam(value = "rows", required = false) Integer pageSize,
            @RequestParam(value = "page", required = false) Integer pageNo, HttpServletRequest request, PrintWriter out) {
        JSONObject json = new JSONObject();
        Map<String, Object> reqMap = Maps.newHashMap();
        if (StringUtil.isNotEmpty(request.getParameter("region_code"))) {
            reqMap.put("region_code", request.getParameter("region_code"));
        }
        if (StringUtil.isNotEmpty(request.getParameter("risk_type"))) {
            reqMap.put("risk_type", request.getParameter("risk_type"));
        }
        reqMap.put("startPage", (pageNo - 1) * pageSize);
        reqMap.put("limit", pageSize);
        List<TRiskZoneConfig> lists = ModelParamConfigService.queryRiskZoneConfig(reqMap);
        if (lists != null && lists.size() > 0) {
            for (TRiskZoneConfig config : lists) {
                config.setRisk_type(risktypeMap.get(config.getRisk_type()));
                config.setDeleted_flag(deleteflagMap.get(config.getDeleted_flag()));
                config.setRegion_code(provinceMap.get(config.getRegion_code()));
            }
        }
        long total = ModelParamConfigService.countRiskZoneConfig(reqMap);
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

    @RequestMapping("/openAddRiskZoneConfig")
    public ModelAndView openAddRiskZoneConfigWindow() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("oprFlag", "0");// 0代表新增
        mav.addObject("provinceList", provinceList);
        mav.addObject("risktypeList", risktypeList);
        mav.addObject("risktypecodeList", risktypecodeList);
        mav.addObject("deleteflagList", deleteflagList);
        mav.setViewName("paramconfig/detailRiskzoneConfigInfo");
        return mav;
    }

    /**
     * 新增机构
     * 
     * @param request
     * @param out
     * @return
     */
    @RequestMapping(value = "/addRiskZoneConfig", method = RequestMethod.POST)
    public ModelAndView addInstitutionWindow(HttpServletRequest request, PrintWriter out) {
        Map<String, Object> reqMap = Maps.newHashMap();
        if (StringUtil.isNotEmpty((String) request.getParameter("region_code"))) {
            reqMap.put("region_code", (String) request.getParameter("region_code"));
        }
        if (StringUtil.isNotEmpty((String) request.getParameter("risk_type"))) {
            reqMap.put("risk_type", (String) request.getParameter("risk_type"));
        }
        if (StringUtil.isNotEmpty((String) request.getParameter("risk_type_code"))) {
            reqMap.put("risk_type_code", (String) request.getParameter("risk_type_code"));
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
        reqMap.put("create_date", new Date());
        reqMap.put("modify_date", new Date());
        try {
            if (ModelParamConfigService.selectRiskzoneConfigByRegcode((String) reqMap.get("region_code")) != null) {
                out.print("该区域代码的记录已存在");
                return null;
            } else {
                int result = ModelParamConfigService.saveRiskzoneConfig(reqMap);
                if (result > 0) {
                    logger.info("风险区域配置信息新增成功,参数为[{}]", reqMap);
                    out.print("新增成功");
                } else {
                    logger.info("风险区域配置信息新增失败,参数为[{}]", reqMap);
                    out.print("系统异常，新增风险区域配置信息失败");
                }
            }
            return null;
        } catch (Exception e) {
            logger.error("新增风险区域配置信息失败", e);
            out.print("系统异常，新增风险区域配置信息失败");
            return null;
        }
    }

    /**
     * 修改风险区域配置信息窗口
     * 
     * @param orgId
     * @return
     */
    @RequestMapping("/openModifyRiskzoneConfigWindow")
    public ModelAndView openModifyRiskzoneConfigWindow(@RequestParam(value = "id", required = false) Integer id) {
        ModelAndView mav = new ModelAndView();
        if (id == null) {
            logger.info("记录编号为空");
            mav.addObject("oprFlag", "1");// 1代表修改
            mav.addObject("provinceList", provinceList);
            mav.addObject("risktypeList", risktypeList);
            mav.addObject("risktypecodeList", risktypecodeList);
            mav.addObject("deleteflagList", deleteflagList);
            mav.setViewName("paramconfig/detailRiskzoneConfigInfo");
            return mav;
        }
        // 查询展业机构
        TRiskZoneConfig tRiskZoneConfig = ModelParamConfigService.selectRiskZoneConfigByID(id);
        mav.addObject("tRiskZoneConfig", tRiskZoneConfig);
        mav.addObject("oprFlag", "1");// 1代表修改
        mav.addObject("provinceList", provinceList);
        mav.addObject("risktypeList", risktypeList);
        mav.addObject("risktypecodeList", risktypecodeList);
        mav.addObject("deleteflagList", deleteflagList);
        mav.setViewName("paramconfig/detailRiskzoneConfigInfo");
        return mav;
    }

    /**
     * 风险区域配置详情
     * 
     * @param orgId
     * @return
     */
    @RequestMapping("/riskzoneConfigdetail")
    public ModelAndView riskzoneConfigdetail(@RequestParam(value = "id") Integer id) {
        ModelAndView mav = new ModelAndView();
        // 查询展业机构
        TRiskZoneConfig tRiskZoneConfig = ModelParamConfigService.selectRiskZoneConfigByID(id);
        mav.addObject("tRiskZoneConfig", tRiskZoneConfig);
        mav.addObject("oprFlag", "3");// 3代表查看详情，不能修改
        mav.setViewName("paramconfig/detailRiskzoneConfigInfo");
        return mav;
    }

    /**
     * 修改机构
     * 
     * @param request
     * @param out
     * @return
     */
    @RequestMapping(value = "/modifyRiskzoneConfigWindow", method = RequestMethod.POST)
    public ModelAndView modifyRiskzoneConfigWindow(HttpServletRequest request, PrintWriter out) {
        Map<String, Object> reqMap = Maps.newHashMap();
        if (StringUtil.isNotEmpty((String) request.getParameter("id"))) {
            reqMap.put("id", (String) request.getParameter("id"));
        }
        // if (StringUtil.isNotEmpty((String)
        // request.getParameter("region_code"))) {
        // reqMap.put("region_code", (String)
        // request.getParameter("region_code"));
        // }
        if (StringUtil.isNotEmpty((String) request.getParameter("risk_type"))) {
            reqMap.put("risk_type", (String) request.getParameter("risk_type"));
        }
        if (StringUtil.isNotEmpty((String) request.getParameter("risk_type_code"))) {
            reqMap.put("risk_type_code", (String) request.getParameter("risk_type_code"));
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
            int result = ModelParamConfigService.modifyRiskzoneConfig(reqMap);
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
    @RequestMapping(value = "/deleteRiskzoneConfig", method = RequestMethod.POST)
    public ModelAndView deleteRiskzoneConfig(@RequestParam(value = "id", required = false) String id, PrintWriter out) {
        Map<String, Object> reqMap = Maps.newHashMap();
        try {
            if (StringUtil.isNotEmpty(id)) {
                reqMap.put("id", Integer.parseInt(id));
            }
            int flag = ModelParamConfigService.deleteRiskzoneConfig(id);
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
