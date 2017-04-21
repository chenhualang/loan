package com.hrbb.loan.controller.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hrbb.loan.pos.biz.backstage.inter.IAsyncLoadPoliceInfoService;
import com.hrbb.loan.pos.service.PoliceInfoService;

/**
 * @author zhangwei5@hrbb.com.cn
 * @date 2015年9月24日下午2:06:48 
 */
@Controller
@RequestMapping("/info/police")
public class PoliceInfoController {

    @Autowired
    @Qualifier("asyncLoadPoliceInfoService")
    private IAsyncLoadPoliceInfoService asyncLoadPoliceInfoService;

    @Autowired
    private PoliceInfoService policeInfoService;

    

    /**
     * 查询db,获取客户公安认证信息
     * @param loanId 贷款申请编号
     * @return 公安信息
     */
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public ModelAndView load(@RequestParam String loanId) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("policeInfo", policeInfoService.queryPoliceInfoByLoadId(loanId));
        mav.setViewName("review/policeInfo");
        return mav;
    }

    /** 实时调用公安信息，并更新db公安信息
     * @param loanId 贷款申请编号
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.GET)
    public ModelAndView queryPoliceInfo(@RequestParam String loanId) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("policeInfo", asyncLoadPoliceInfoService.loadPoliceInfoInTime(loanId));
        mav.setViewName("review/policeInfo");
        return mav;
    }

}
