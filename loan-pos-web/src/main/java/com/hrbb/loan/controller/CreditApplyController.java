package com.hrbb.loan.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hrbb.loan.constants.CreditApplyConstants;
import com.hrbb.loan.controller.helper.ControllerHelper;
import com.hrbb.loan.factory.CreditApplyFactory;
import com.hrbb.loan.hessian.inter.IAICHessianService;
import com.hrbb.loan.hessian.inter.IBankCardCheckHessianService;
import com.hrbb.loan.hessian.inter.IPoliceHessianService;
import com.hrbb.loan.hessian.inter.IUpsDataApiHessionService;
import com.hrbb.loan.pos.biz.backstage.inter.CreditApplyAprvInfoBiz;
import com.hrbb.loan.pos.biz.backstage.inter.ILoanPosBusinessCodeBiz;
import com.hrbb.loan.pos.biz.backstage.inter.ILoanPosCreditApplyBackStageBiz;
import com.hrbb.loan.pos.biz.backstage.inter.ILoanPosCreditApplyCheckRegBiz;
import com.hrbb.loan.pos.biz.backstage.inter.ILoanPosCustomerBackStageBiz;
import com.hrbb.loan.pos.biz.backstage.inter.IPoliceAndAICConnectBiz;
import com.hrbb.loan.pos.biz.backstage.inter.ImageDataViewBiz;
import com.hrbb.loan.pos.dao.TCreditApplyDao;
import com.hrbb.loan.pos.dao.entity.TCreditApply;
import com.hrbb.loan.pos.dao.entity.TCustomer;
import com.hrbb.loan.pos.dao.entity.TPosCustInfo;
import com.hrbb.loan.pos.dao.entity.TUpsBillcardstatSerial;
import com.hrbb.loan.pos.factory.SysConfigFactory;
import com.hrbb.loan.pos.service.LoanPosCreditApplyService;
import com.hrbb.loan.pos.service.LoanPosCustService;
import com.hrbb.loan.pos.service.LoanPosCustomerService;
import com.hrbb.loan.pos.service.UpsDataApiService;
import com.hrbb.loan.pos.util.DateUtil;
import com.hrbb.loan.pos.util.ExecutorUtil;
import com.hrbb.loan.pos.util.FileUtil;
import com.hrbb.loan.pos.util.IdUtil;
import com.hrbb.loan.pos.util.StringUtil;
import com.hrbb.loan.spiconstants.CreditApplyHServiceConstants;
import com.hrbb.loan.web.security.entity.AccessPrivilege;
import com.hrbb.loan.web.security.entity.User;

/**
 * <h1>dbAppController</h1>
 * 
 * @author Johnson Song
 * @version Id: DbAppController.java, v 1.0 2015-2-5 下午3:14:59 Johnson Song Exp
 */
@Controller
@RequestMapping("/creditApply")
public class CreditApplyController {
    private final Logger logger = LoggerFactory
            .getLogger(CreditApplyController.class);

	@Autowired
	private ILoanPosCreditApplyBackStageBiz loanPosCreditApplyBackStageBiz;
	
	@Autowired
	private ILoanPosCustomerBackStageBiz loanPosCustomerBackStageBiz;
	
	@Autowired
    private ILoanPosBusinessCodeBiz loanPosBusinessCodeBiz;
	
	@Autowired
	private ILoanPosCreditApplyCheckRegBiz loanPosCreditApplyCheckRegBiz;
	
	@Autowired
	private LoanPosCustomerService loanPosCustomerService;
	
	@Autowired
	private LoanPosCustService loanPosCustServiceImpl;
	 
	@Autowired
	private LoanPosCreditApplyService loanPosCreditApplyService;
	
	@Autowired
	private CreditApplyAprvInfoBiz creditApplyAprvInfoBiz;
	
	@Autowired
	private IBankCardCheckHessianService IbankCardCheckHessianService;
	
	@Autowired
	private IAICHessianService aicHessianService;
	
	@Autowired
	private IPoliceHessianService iPoliceHessianService;
	
	@Autowired
	private IPoliceAndAICConnectBiz policeAndAICConnectBiz;
	
	@Autowired
	private ImageDataViewBiz imageDataViewBiz;
	
	@Autowired
	private TCreditApplyDao tCreditApplyDao;
	
	@Autowired
	private IUpsDataApiHessionService upsDataApiHessionService;
	
	@Autowired
    UpsDataApiService upsDataApiService;
	
	Executor executor = null;
    
    @PostConstruct
    private void initExecutor(){
        executor = ExecutorUtil.getScheduledThreadExecutor(10);
    }
//	private static final String sourcePath = "/home/loan-pos-imagedata/imagedatawork";
	
//	@Value("#{systemInfo[imageUrl]}")
//	private String imageUrl;	
	
//	private final static String FILETYPE_PDF = ".pdf";
	
//	private static  List<Map<String, Object>> bizFileTypeList = null;

//	@PostConstruct
//    public void loadDictionary(){
	    
//	    Map<String,Object> reqMap = Maps.newHashMap();
//        reqMap.put("codeNo", BusinessDictionaryConstants.bizFileType);
//        reqMap.put(CreditApplyConstants.ITEMN_NO_LIKE, "01__");       
//        bizFileTypeList = loanPosBusinessCodeBiz.getSeletiveMap(reqMap);
//	}
	/**
	 * <h2>获取dbapp记录</h2>
	 * 
	 * @param pageNo
	 *            , pageSize
	 * @return modelAndView
	 */
	@RequestMapping("/queryCreditApply")
	public ModelAndView queryCreditApply(
			@RequestParam(value = "rows", required = false) Integer pageSize,
			@RequestParam(value = "page", required = false) Integer pageNo,
			HttpServletRequest request, PrintWriter out) {
		Map<String, Object> reqMap = Maps.newHashMap();
		reqMap.put(CreditApplyConstants.DELETE_FLAG, "0");
		if (!StringUtils.isEmpty(request
				.getParameter(CreditApplyConstants.LOAN_ID_LIKE))) {
			reqMap.put(CreditApplyConstants.LOAN_ID_LIKE, ControllerHelper
					.getLikeString(request
							.getParameter(CreditApplyConstants.LOAN_ID_LIKE)));
		}
		if (!StringUtils.isEmpty(request
				.getParameter(CreditApplyConstants.BIZ_LOAN_ID_LIKE))) {
			reqMap.put(CreditApplyConstants.BIZ_LOAN_ID_LIKE, ControllerHelper
					.getLikeString(request
							.getParameter(CreditApplyConstants.BIZ_LOAN_ID_LIKE)));
		}
		if (!StringUtils.isEmpty(request
				.getParameter(CreditApplyConstants.POS_CUST_NAME_LIKE))) {
			reqMap.put(
					CreditApplyConstants.POS_CUST_NAME_LIKE,
					ControllerHelper.getLikeString(request
							.getParameter(CreditApplyConstants.POS_CUST_NAME_LIKE)));
		}
		if (!StringUtils.isEmpty(request
				.getParameter(CreditApplyConstants.CUST_ID_LIKE))) {
			reqMap.put(CreditApplyConstants.CUST_ID_LIKE, ControllerHelper
					.getLikeString(request
							.getParameter(CreditApplyConstants.CUST_ID_LIKE)));
		}
		if (!StringUtils.isEmpty(request
				.getParameter(CreditApplyConstants.CUST_NAME_LIKE))) {
			reqMap.put(CreditApplyConstants.CUST_NAME_LIKE, ControllerHelper
					.getLikeString(request
							.getParameter(CreditApplyConstants.CUST_NAME_LIKE)));
		}
		if (!StringUtils.isEmpty(request
				.getParameter(CreditApplyConstants.PAPER_ID_LIKE))) {
			reqMap.put(CreditApplyConstants.PAPER_ID_LIKE, ControllerHelper
					.getLikeString(request
							.getParameter(CreditApplyConstants.PAPER_ID_LIKE)));
		}
		if (!StringUtils.isEmpty(request
				.getParameter(CreditApplyConstants.REC_ORG_LIKE))) {
			reqMap.put(CreditApplyConstants.REC_ORG_LIKE, ControllerHelper
					.getLikeString(request
							.getParameter(CreditApplyConstants.REC_ORG_LIKE)));
		}
		if (!StringUtils.isEmpty(request
				.getParameter(CreditApplyConstants.REC_PERSON_LIKE))) {
			reqMap.put(
					CreditApplyConstants.REC_PERSON_LIKE,
					ControllerHelper.getLikeString(request
							.getParameter(CreditApplyConstants.REC_PERSON_LIKE)));
		}
		if (!StringUtils.isEmpty(request.getParameter(CreditApplyConstants.APPLY_STATUS))) {
			reqMap.put(CreditApplyConstants.APPLY_STATUS, request.getParameter(CreditApplyConstants.APPLY_STATUS));
			if ("92".equals(request.getParameter(CreditApplyConstants.APPLY_STATUS).toString())){
				reqMap.put(CreditApplyConstants.APPLY_STATUS1, "91");
			}
		}
		//add channel query var at 2015-6-30
		if (!StringUtils.isEmpty(request.getParameter(CreditApplyConstants.channel))) {
			reqMap.put(CreditApplyConstants.channel, request.getParameter(CreditApplyConstants.channel));
		}
		if(!StringUtils.isEmpty(request.getParameter(CreditApplyConstants.region))){
		    String region = request.getParameter(CreditApplyConstants.region);
		    try {
                String first2Char = region.substring(0, 2);
                reqMap.put(CreditApplyConstants.region, first2Char+"____");
            } catch (Exception e) {
                logger.error("省份代码不合法", e);
                reqMap.put(CreditApplyConstants.region, null);
            }
		}
		if (!StringUtils.isEmpty(request.getParameter(CreditApplyConstants.APPLY_STATUS)) 
		        && "93".equals(request.getParameter(CreditApplyConstants.APPLY_STATUS))){
		    reqMap.put(CreditApplyConstants.DELETE_FLAG, "1");
		}
		if(!StringUtils.isEmpty(request.getParameter("isApplyStatus"))){
		    reqMap.put("isApplyStatus",request.getParameter("isApplyStatus"));
		}
		//---add by Lin,Zhaolin for data-range-view-control
//		String privileges = (String)request.getSession().getAttribute("assignedPrivileges");
//		if(privileges==null || privileges.indexOf("ROLE_ADMIN;")==-1) {
		AccessPrivilege access = (AccessPrivilege)request.getSession().getAttribute("accessPrivilege");
		if(!access.hasAnyPrivilege("ROLE_APPLY_QUERY;ROLE_ADMIN;ROLE_APPLY_VIEWALL")){
			//非管理员只能看到自身数据
			User user = (User)request.getSession().getAttribute("USER");
			reqMap.put("equalOperId",user.getUserName());
			
		}
		//---add end
		
		reqMap.put("startPage", (pageNo - 1) * pageSize);
		reqMap.put("limit", pageSize);
		List<Map<String, Object>> lists = loanPosCreditApplyBackStageBiz
				.queryCreditApply(reqMap);
			Map<String, Object> map = lists.get(lists.size() - 1);
			lists.remove(lists.size() - 1);
			JSONObject aaJson = new JSONObject();
			aaJson.put("total", map.get("total"));
			aaJson.put("rows", lists);
			out.write(aaJson.toJSONString());			
		return null;
	}

	/**
	 * 查询申请明细
	 * 
	 * @return
	 */
	@RequestMapping("/queryCreditApplyDetail")
	public ModelAndView queryCreditApplyDetail(
			@RequestParam(value = "loanId", required = false) String loanId,
			PrintWriter out) {
		// 查询出
		List<Map<String, Object>> resList = loanPosCreditApplyBackStageBiz
				.getCreditApplyDetail(loanId);
		out.print(JSON.toJSONString(resList));
		return null;
	}

	/**
	 * 查询pos流水明细
	 */
	@RequestMapping("/queryCreditApplyPosSerialDetail")
	public ModelAndView queryCreditApplyPosSerialDetail(
			@RequestParam(value = "loanId", required = false) String loanId,
			@RequestParam(value = "rows", required = false) Integer pageSize,
			@RequestParam(value = "page", required = false) Integer pageNo,
			PrintWriter out) {
		if (StringUtil.isEmpty(loanId)) {
			return null;
		}
		JSONObject jsonObject = loanPosCreditApplyBackStageBiz
				.getPosSerialDetail(loanId, pageNo, pageSize);
		out.print(jsonObject.toJSONString());
		return null;
	}

	/**
	 * 查询银行流水汇总
	 * 
	 * @param loanId
	 * @param card
	 * @return
	 */
	@RequestMapping("/queryCreditApplyBankSerialDetailSummary")
	public ModelAndView queryCreditApplyBankSerialDetailSummary(
			@RequestParam(value = "loanId", required = false) String loanId,
			@RequestParam(value="card",required = false) String card) {
        ModelAndView mav = new ModelAndView();
        String fileUuid = "";
        if (!StringUtil.isEmpty(card)) {
            fileUuid = upsDataApiService.getFileUuid(card);
        } else {
            //如果card为空，默认取当前标使用的银行卡号
            if (StringUtil.isEmpty(loanId)) {
                return null;
            }
            Map<String, Object> resultMap = loanPosCreditApplyService
                .selectNameAndBankAccNoByLoanId(loanId);
            if (StringUtil.isEmpty((String) resultMap.get("custName"))) {
                logger.info("custName为空");
                return null;
            }
            if (StringUtil.isEmpty((String) resultMap.get("bankAccno"))) {
                logger.info("bankAccno为空");
                return null;
            }
            card = (String) resultMap.get("bankAccno");
            fileUuid = upsDataApiService.getFileUuid(card);
        }

        if (StringUtil.isEmpty(fileUuid)) {
            logger.info("fileUuid为空,该卡没有查询过银行流水");
            return null;
        }
        Map<String, Object> respMap = upsDataApiService.queryConsumeSummaryNew(fileUuid);
        mav.addAllObjects(respMap);
        mav.setViewName("review/detailCreditBankSerialInfoSummary");
        return mav;
	}

	/**
     * 查询银行流水明细
     * 
     * @param loanId
     * @param card
     * @return
     */
    @RequestMapping("/queryCreditApplyBankSerialDetail")
    public ModelAndView queryCreditApplyBankSerialDetail(@RequestParam(value="card",required = false) String card) {
        ModelAndView mav = new ModelAndView();
        String fileUuid = "";
        if (!StringUtil.isEmpty(card)) {
            fileUuid = upsDataApiService.getFileUuid(card);
        }
        
        if (StringUtil.isEmpty(fileUuid)) {
            logger.info("fileUuid为空,该卡没有查询过银行流水");
            return null;
        }
        List<TUpsBillcardstatSerial> respMap = upsDataApiService.selectListByFileUuid(fileUuid);
        mav.addObject("upsBillcardstatSerials", respMap);
        mav.setViewName("review/detailCreditBankSerialInfo");
        
        return mav;
    }
	/**
	 * 增加申请
	 * 
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping("/insertCreditApply")
	public ModelAndView insertCreditApply(HttpServletRequest request,
			PrintWriter out) {
		User user = (User)request.getSession().getAttribute("USER");
		try {

			// 获取参数值
			// 申请信息
			String loanId = request.getParameter(CreditApplyConstants.LOAN_ID);
			// 渠道
			String channel = request.getParameter(CreditApplyConstants.channel);
			//进件方式
			String inChannelKind = request.getParameter(CreditApplyConstants.inChannelKind);
			
			//偿还方式
			String repayMethod = request.getParameter(CreditApplyConstants.repayMethod);
			/*
			String posCustProText = request
					.getParameter(CreditApplyConstants.posCustProText);

			String posCustCityText = request
					.getParameter(CreditApplyConstants.posCustCityText);

			String posCustDivText = request
					.getParameter(CreditApplyConstants.posCustDivText);
			*/
			// 证件类型
			String paperKind = request
					.getParameter(CreditApplyConstants.paperKind);
			// 金额
			String applyAmt = request
					.getParameter(CreditApplyConstants.applyAmt);
			// 币种
			String currSign = request
					.getParameter(CreditApplyConstants.currSign);
			// 申请期限
			String applyTerm = request.getParameter(CreditApplyConstants.applyTerm);
			// 还款方式
			String returnKind = request
					.getParameter(CreditApplyConstants.returnKind);
			// 收款账号
			String bankAccno = request
					.getParameter(CreditApplyConstants.bankAccno);
			// 账户开户行
			String bankName = request
					.getParameter(CreditApplyConstants.bankName);
			// 账户分行
			String bankBranName = request
					.getParameter(CreditApplyConstants.bankBranName);
			// 账户支行
			String bankSubName = request
					.getParameter(CreditApplyConstants.bankSubName);
			// 机构号
			String recOrg = request.getParameter(CreditApplyConstants.recOrg);
			// 推荐人
			String recPerson = request
					.getParameter(CreditApplyConstants.recPerson);
			// 备注
 			String remark = request
					.getParameter(CreditApplyConstants.remark);
			// 推荐人手机号
			String recContactNo = request
					.getParameter(CreditApplyConstants.recContactNo);
			// /////////////////////////////////////////////////////////////////////////////////////
			// 商户信息
			// 商户名称
			String posCustName = request
					.getParameter(CreditApplyConstants.posCustName);
			// 主营范围
			String posCustBusiScope = request
					.getParameter(CreditApplyConstants.posCustBusiScope);
			// 经营区划
			String busiDivision = request
					.getParameter(CreditApplyConstants.busiDivision);
			// 详细地址
			String posCustAddress = request
					.getParameter(CreditApplyConstants.posCustAddress);
			// /////////////////////////////////////////////////////////////////////////////
			// 客户信息
			// 客户名
			String custName = request
					.getParameter(CreditApplyConstants.custName);
			// 证件号码
			String paperId = request.getParameter(CreditApplyConstants.paperId);
			// 生日
			String birtDate = request
					.getParameter(CreditApplyConstants.birtDate);
			// 性别
			String sexSign = request.getParameter(CreditApplyConstants.sexSign);
			// 从业年限
			String busiYear = request
					.getParameter(CreditApplyConstants.busiYear);
			// 婚姻状况
			String marrSign = request
					.getParameter(CreditApplyConstants.marrSign);
			// 受教育程度
			String educSign = request
					.getParameter(CreditApplyConstants.educSign);
			// 子女人数
			String childNum = request
					.getParameter(CreditApplyConstants.childNum);
			// 本地房产数
			String localHouseNum = request
					.getParameter(CreditApplyConstants.localHouseNum);
			// 非本地房产数
			String otherHouseNum = request
					.getParameter(CreditApplyConstants.otherHouseNum);
			// 手机号
			String mobilePhone = request
					.getParameter(CreditApplyConstants.mobilePhone);
			// 电话号码
			String tel = request.getParameter(CreditApplyConstants.tel);
			// 客户省
			String residentProv = request
					.getParameter(CreditApplyConstants.residentProv);
			// 客户市
			String residentCity = request
					.getParameter(CreditApplyConstants.residentCity);
			// 客户区
			String residentDivision = request
					.getParameter(CreditApplyConstants.residentDivision);
			// 客户详细地址
			String residentDetail = request
					.getParameter(CreditApplyConstants.residentDetail);
			// 微信号
			String weixinNo = request
					.getParameter(CreditApplyConstants.weixinNo);
			// qq号
			String qqNo = request.getParameter(CreditApplyConstants.qqNo);
			// email
			String email = request.getParameter(CreditApplyConstants.email);
			// 配偶姓名
			String familyCustName = request
					.getParameter(CreditApplyConstants.familyCustName);
			// 配偶证件类型
			String matePaperKind = request
					.getParameter(CreditApplyConstants.matePaperKind);
			// 配偶证件号
			String matePaperId = request
					.getParameter(CreditApplyConstants.matePaperId);
			// 配偶生日
			String mateBirtDate = request
					.getParameter(CreditApplyConstants.mateBirtDate);
			// 配偶性别
			String mateSexSign = request
					.getParameter(CreditApplyConstants.mateSexSign);
			// 配偶手机号
			String mateMobilePhone = request
					.getParameter(CreditApplyConstants.mateMobilePhone);
			// 亲属姓名
			String relaCustName = request
					.getParameter(CreditApplyConstants.relaCustName);
			// 亲属类型
			String relaKind = request
					.getParameter(CreditApplyConstants.relaKind);
			// 亲属手机号
			String relaMobilePhone = request
					.getParameter(CreditApplyConstants.relaMobilePhone);
			// 亲属电话号码
			String relaTel = request.getParameter(CreditApplyConstants.relaTel);
			//联系地址类型
			String contactAddrFlag = request.getParameter(CreditApplyConstants.contactAddrFlag);
			// /////////////////////////////////////////////////////////////////////////

			//发生方式
			String occurType = request.getParameter(CreditApplyConstants.occurType);
			
			// t_credit_apply参数
			Map<String, Object> creMap = Maps.newHashMap();

			creMap.put(CreditApplyConstants.LOAN_ID,loanId);	//key-id 
			
			if (!StringUtil.isEmpty(applyAmt)) {
				creMap.put(CreditApplyConstants.APPLY_AMT, applyAmt);
			}
			creMap.put(CreditApplyConstants.prodId,
					CreditApplyConstants.posLoanFlag);
			creMap.put(CreditApplyConstants.prodName,
					CreditApplyConstants.posLoanName);
			creMap.put(CreditApplyConstants.beginDate, new Date());
			creMap.put(CreditApplyConstants.applyTerm, applyTerm);
			creMap.put(CreditApplyConstants.termUnit, CreditApplyConstants.MONTH);
			if (!StringUtil.isEmpty(channel)) {
				creMap.put(CreditApplyConstants.channel, channel);
			}
			if (!StringUtil.isEmpty(custName)) {
				creMap.put(CreditApplyConstants.custName, custName);
			}
			if (!StringUtil.isEmpty(posCustName)) {
				creMap.put(CreditApplyConstants.posCustName, posCustName);
			}
			/*  modefied by Lin,Zhaolin for 替换为区划代码
			if (!StringUtil.isEmpty(busiDivision)) {
				creMap.put(CreditApplyConstants.region, posCustProText
						+ posCustCityText + posCustDivText);
			}*/
			creMap.put(CreditApplyConstants.region, busiDivision);
			
			creMap.put(CreditApplyConstants.bankAccno,
					StringUtil.isEmpty(bankAccno) ? null : bankAccno);
			creMap.put(CreditApplyConstants.currSign,
					StringUtil.isEmpty(currSign) ? null : currSign);
			creMap.put(CreditApplyConstants.returnKind,
					StringUtil.isEmpty(returnKind) ? null : returnKind);
			creMap.put(CreditApplyConstants.recOrg,
					StringUtil.isEmpty(recOrg) ? null : recOrg);
			creMap.put(CreditApplyConstants.recPerson,
					StringUtil.isEmpty(recPerson) ? null : recPerson);
			creMap.put(CreditApplyConstants.remark,
					StringUtil.isEmpty(remark) ? null : remark);
			creMap.put(CreditApplyConstants.recContactNo,
					StringUtil.isEmpty(recContactNo) ? null : recContactNo);
	//		creMap.put(CreditApplyConstants.tradeArea, "上海");						//remove by Lin,Zhaolin
			creMap.put(CreditApplyConstants.APPLY_STATUS,
					CreditApplyConstants.STATUS_PENDING);
			/*creMap.put(CreditApplyConstants.inChannelKind,
					CreditApplyConstants.inChannelSelf);*/
			creMap.put(CreditApplyConstants.inChannelKind, inChannelKind);
			creMap.put(CreditApplyConstants.repayMethod, repayMethod);
	//		creMap.put(CreditApplyConstants.operName, "宋依麟");
			creMap.put(CreditApplyConstants.operName, user.getLoginName());			//modefied by Lin,Zhaolin
			creMap.put(CreditApplyConstants.operId, user.getUserName());			//add by Lin, Zhaolin
//			creMap.put(CreditApplyConstants.occurType, CreditApplyConstants.occurTypeAdd);
			creMap.put(CreditApplyConstants.occurType,occurType);
			//creMap.put(CreditApplyConstants.BIZ_LOAN_ID, IdUtil.getBizLoanId(channel, inChannelKind));	//add bizLoanId by Lin,Zhaolin

			// /////////////////////////////////////////////////////////////
			// t_customer_info参数
			Map<String, Object> custMap = Maps.newHashMap();
			if (!StringUtil.isEmpty(custName)) {
				custMap.put(CreditApplyConstants.custName, custName);
			}
			// 证件号码
			if (!StringUtil.isEmpty(paperId)) {
				custMap.put(CreditApplyConstants.PAPER_ID, paperId);
			}
			// 生日
			if (!StringUtil.isEmpty(birtDate)) {
				custMap.put(CreditApplyConstants.birtDate,
						DateUtil.getDatePattern3(birtDate));
			}
			// 性别
			if (!StringUtil.isEmpty(sexSign)) {
				custMap.put(CreditApplyConstants.sexSign, sexSign);
			}
			// 从业年限
			if (!StringUtil.isEmpty(busiYear)) {
				custMap.put(CreditApplyConstants.busiYear, busiYear);
			}
			// 婚姻状况
			if (!StringUtil.isEmpty(marrSign)) {
				custMap.put(CreditApplyConstants.marrSign, marrSign);
			}
			// 受教育程度
			if (!StringUtil.isEmpty(educSign)) {
				custMap.put(CreditApplyConstants.educSign, educSign);
			}
			// 子女人数
			if (!StringUtil.isEmpty(childNum)) {
				custMap.put(CreditApplyConstants.childNum, childNum);
			}

			custMap.put(CreditApplyConstants.paperKind,
					StringUtil.isEmpty(paperKind) ? null : paperKind);
			// 本地房产数
			custMap.put(CreditApplyConstants.localHouseNum,
					StringUtil.isEmpty(localHouseNum) ? null : localHouseNum);
			// 非本地房产数
			custMap.put(CreditApplyConstants.otherHouseNum,
					StringUtil.isEmpty(otherHouseNum) ? null : otherHouseNum);
			// 手机号
			custMap.put(CreditApplyConstants.mobilePhone,
					StringUtil.isEmpty(mobilePhone) ? null : mobilePhone);

			// 电话号码
			custMap.put(CreditApplyConstants.tel,
					StringUtil.isEmpty(tel) ? null : tel);
			// 客户省
			custMap.put(CreditApplyConstants.residentProv,
					StringUtil.isEmpty(residentProv) ? null : residentProv);
			// 客户市
			custMap.put(CreditApplyConstants.residentCity,
					StringUtil.isEmpty(residentCity) ? null : residentCity);
			// 地区
			custMap.put(CreditApplyConstants.residentDivision, StringUtil
					.isEmpty(residentDivision) ? null : residentDivision);
			// 客户详细地址
			custMap.put(CreditApplyConstants.residentDetail,
					StringUtil.isEmpty(residentDetail) ? null : residentDetail);
			// 微信号
			custMap.put(CreditApplyConstants.weixinNo,
					StringUtil.isEmpty(weixinNo) ? null : weixinNo);
			// qq号
			custMap.put(CreditApplyConstants.qqNo,
					StringUtil.isEmpty(qqNo) ? null : qqNo);
			// email
			custMap.put(CreditApplyConstants.email,
					StringUtil.isEmpty(email) ? null : email);
			// 配偶姓名
			custMap.put(CreditApplyConstants.familyCustName,
					StringUtil.isEmpty(familyCustName) ? null : familyCustName);
			// 配偶证件类型
			custMap.put(CreditApplyConstants.matePaperKind,
					StringUtil.isEmpty(matePaperKind) ? null : matePaperKind);
			// 配偶证件号
			custMap.put(CreditApplyConstants.matePaperId,
					StringUtil.isEmpty(matePaperId) ? null : matePaperId);
			// 配偶生日
			custMap.put(
					CreditApplyConstants.mateBirtDate,
					StringUtil.isEmpty(mateBirtDate) ? null : DateUtil
							.getDatePattern3(mateBirtDate));
			// 配偶性别
			custMap.put(CreditApplyConstants.mateSexSign,
					StringUtil.isEmpty(mateSexSign) ? null : mateSexSign);
			// 配偶手机号
			custMap.put(CreditApplyConstants.mateMobilePhone, StringUtil
					.isEmpty(mateMobilePhone) ? null : mateMobilePhone);
			//联系地址类型
			custMap.put(CreditApplyConstants.contactAddrFlag, 
					StringUtil.isEmpty(contactAddrFlag) ? null : contactAddrFlag);
			
			// 银行卡信息
			Map<String, Object> bankMap = Maps.newHashMap();
			// 银行卡号
			bankMap.put(CreditApplyConstants.bankAccno,
					StringUtil.isEmpty(bankAccno) ? null : bankAccno);
			// 银行名
			bankMap.put(CreditApplyConstants.bankName,
					StringUtil.isEmpty(bankName) ? null : bankName);
			// 分行名
			bankMap.put(CreditApplyConstants.bankBranName,
					StringUtil.isEmpty(bankBranName) ? null : bankBranName);
			// 支行名
			bankMap.put(CreditApplyConstants.bankSubName,
					StringUtil.isEmpty(bankSubName) ? null : bankSubName);
			// 亲属信息
			Map<String, Object> relMap = Maps.newHashMap();
			relMap.put(CreditApplyConstants.relaCustName,
					StringUtil.isEmpty(relaCustName) ? null : relaCustName);
			relMap.put(CreditApplyConstants.relaKind,
					StringUtil.isEmpty(relaKind) ? null : relaKind);
			relMap.put(CreditApplyConstants.relaMobilePhone, StringUtil
					.isEmpty(relaMobilePhone) ? null : relaMobilePhone);
			relMap.put(CreditApplyConstants.relaTel,
					StringUtil.isEmpty(relaTel) ? null : relaTel);
			// 商户信息
			Map<String, Object> posCustMap = Maps.newHashMap();
			// 商户名
			posCustMap.put(CreditApplyConstants.POS_CUST_NAME,
					StringUtil.isEmpty(posCustName) ? null : posCustName);
			if (!StringUtil.isEmpty(posCustName)) {
				posCustMap.put(CreditApplyConstants.industryTypeId2,
						CreditApplyFactory.getIndustryType(posCustName));
			}
			// 主营范围
			posCustMap.put(CreditApplyConstants.posCustBusiScope, StringUtil
					.isEmpty(posCustBusiScope) ? null : posCustBusiScope);
			// 经营区划
			posCustMap.put(CreditApplyConstants.operAddrCode,
					StringUtil.isEmpty(busiDivision) ? null : busiDivision);
			// 详细地址
			posCustMap.put(CreditApplyConstants.posCustAddress,
					StringUtil.isEmpty(posCustAddress) ? null : posCustAddress);
			// posCustMap.put(CreditApplyConstants.operName, value)
			// posCustMap.put(CreditApplyConstants.operDate, new Date());
			// pos流水信息

			// 银行流水信息
			List<Map<String, Object>> bankSerList = Lists.newArrayList();
			for (int i = 0; i < CreditApplyConstants.monthNum; i++) {
				if(!StringUtil.isEmpty(request
						.getParameter(CreditApplyConstants.currMonth
								+ i))){
					
					Map<String, Object> map = Maps.newHashMap();
					map.put(CreditApplyConstants.currMonth,
							StringUtil.isEmpty(request
									.getParameter(CreditApplyConstants.currMonth
											+ i)) ? null : request
													.getParameter(CreditApplyConstants.currMonth
															+ i));
					map.put(CreditApplyConstants.bankName,
							StringUtil.isEmpty(request
									.getParameter(CreditApplyConstants.bankName + i)) ? null
											: request
											.getParameter(CreditApplyConstants.bankName
													+ i));
					map.put(CreditApplyConstants.bankAccno,
							StringUtil.isEmpty(request
									.getParameter(CreditApplyConstants.bankAccno
											+ i)) ? null : request
													.getParameter(CreditApplyConstants.bankAccno
															+ i));
					map.put(CreditApplyConstants.currMonthIn,
							StringUtil.isEmpty(request
									.getParameter(CreditApplyConstants.currMonthIn
											+ i)) ? null : request
													.getParameter(CreditApplyConstants.currMonthIn
															+ i));
					map.put(CreditApplyConstants.currMonthOut,
							StringUtil.isEmpty(request
									.getParameter(CreditApplyConstants.currMonthOut
											+ i)) ? null : request
													.getParameter(CreditApplyConstants.currMonthOut
															+ i));
					map.put(CreditApplyConstants.currSeaInterestAmt,
							StringUtil.isEmpty(request
									.getParameter(CreditApplyConstants.currSeaInterestAmt
											+ i)) ? null
													: request
													.getParameter(CreditApplyConstants.currSeaInterestAmt
															+ i));
					// map.put(CreditApplyConstants.regPersonId, userId);
					// map.put(CreditApplyConstants.regDate, regDate);
					bankSerList.add(map);
				}
			}
			loanPosCreditApplyBackStageBiz.creditApplyReg(creMap, custMap,
					posCustMap, bankMap, relMap, bankSerList);
			//工商
			out.print("新增授信成功");
		} catch (Exception e) {
		    logger.error("新增授信失败" , e);
			out.print("新增授信失败,系统异常！");
		}
		return null;
	}

	/**
	 * 获取loanId
	 */
	@RequestMapping("/getLoanId")
	public ModelAndView getLoanId(PrintWriter out) {
		out.print(IdUtil.getId("LO"));
		return null;
	}

	/**
	 * 逻辑删除
	 * 
	 * @param request
	 * @param out
	 * @param deleteItems
	 * @return
	 */
	@RequestMapping("/deleteCreditApply")
	public ModelAndView deleteDbApp(
			HttpServletRequest request,
			PrintWriter out,
			@RequestParam(value = "deleteItem", required = false) String deleteItems) {
		String[] loanIds = deleteItems.split("\\|");
		if (loanIds == null || loanIds.length == 0) {
			out.print("0");
			return null;
		}
		for (String loanId : loanIds) {
			Map<String, Object> map = Maps.newHashMap();
			map.put(CreditApplyConstants.LOAN_ID, loanId);
			map.put(CreditApplyConstants.APPLY_STATUS, "93");
			loanPosCreditApplyBackStageBiz.deleteCreditApplyInfo(map);
		}
		out.print("取消成功");
		// 逻辑删除
		return null;
	}

	/*
	 * @RequestMapping("/queryCreditApplyDetail") public ModelAndView
	 * queryCreditApplyDetail(@RequestParam(value = "loanId", required =
	 * false)String loanId){
	 * 
	 * }
	 */

	@RequestMapping("/updateCreditApplyWithTrack")
	public ModelAndView updateCreditApplyWithTrack(HttpServletRequest request,PrintWriter out) {
		/*[0-apply][1-cust][2-poscust][3-bankAcc][4-rela]*/
		List<Map<String,Object>> applyVars = fillVars4Update(request);
		
		if(applyVars!=null && applyVars.size()>0){
			User user = (User)request.getSession().getAttribute("USER");
			try{
				loanPosCreditApplyBackStageBiz.updateApplyWithTrack(applyVars.get(0),applyVars.get(1), applyVars.get(2), applyVars.get(3), applyVars.get(4), user.getUserName());
				out.print("业务申请更新成功");
			}catch(Exception e){
				logger.error("业务申请更新失败" , e);
				out.print("业务申请更新失败.ERRPR:"+e.getMessage());
			}
		}
		
		return null;
	}

    @RequestMapping("/updateCreditApply")
    public ModelAndView updateCreditApply(HttpServletRequest request, PrintWriter out) {
        
        List<Map<String, Object>> applyVars = fillVars4Update(request);
        if (applyVars != null && applyVars.size() > 0) {
            try {
                loanPosCreditApplyBackStageBiz.modifyCreditApplyInfo(applyVars.get(0), applyVars.get(1), applyVars.get(2), applyVars.get(3), applyVars.get(4), null);
                out.print("业务申请更新成功");
                return null;
            } catch (Exception e) {
                logger.error("业务申请更新失败", e);
                out.print("业务申请更新失败.ERROR:" + e.getMessage());
                return null;
            }
        }
        out.print("业务申请更新无效!");
        return null;
    }
	
	
	@RequestMapping("/updateApplyWithSync")
	public ModelAndView updateApplyWithSync(HttpServletRequest request,PrintWriter out) {
		/*[0-apply][1-cust][2-poscust][3-bankAcc][4-rela]*/
		List<Map<String,Object>> applyVars = fillVars4Update(request);
		
		if(applyVars!=null && applyVars.size()>0){
			User user = (User)request.getSession().getAttribute("USER");
			try{
				loanPosCreditApplyBackStageBiz.updateApplyWithSync(applyVars.get(0),applyVars.get(1), applyVars.get(2), applyVars.get(3), applyVars.get(4), user.getUserName());
				out.print("业务申请更新成功");
			}catch(Exception e){
				logger.error("业务申请更新失败" , e);
				out.print("业务申请更新失败.ERRPR:"+e.getMessage());
			}
		}
		
		return null;
	}
	
//	@RequestMapping("/updateCreditApply")
//	private boolean updateCreditApply(HttpServletRequest request) {
	private List<Map<String,Object>> fillVars4Update(HttpServletRequest request){
		try {

			String loanId = request.getParameter(CreditApplyConstants.LOAN_ID);

			String custId = request.getParameter(CreditApplyConstants.CUST_ID);

			String posCustId = request.getParameter(CreditApplyConstants.POS_CUST_ID);

			String relativeId = request.getParameter(CreditApplyConstants.RELA_ID);
			// 获取参数值
			// 申请信息
			// 渠道
			String channel = request.getParameter(CreditApplyConstants.channel);
			// 证件类型
			String paperKind = request
					.getParameter(CreditApplyConstants.paperKind);
			// 金额
			String applyAmt = request
					.getParameter(CreditApplyConstants.applyAmt);
			// 币种
			String currSign = request
					.getParameter(CreditApplyConstants.currSign);
			// 还款方式
			String returnKind = request
					.getParameter(CreditApplyConstants.returnKind);
			// 收款账号
			String bankAccno = request
					.getParameter(CreditApplyConstants.bankAccno);
			// 账户开户行
			String bankName = request
					.getParameter(CreditApplyConstants.bankName);
			// 账户分行
			String bankBranName = request
					.getParameter(CreditApplyConstants.bankBranName);
			// 账户支行
			String bankSubName = request
					.getParameter(CreditApplyConstants.bankSubName);
			// 机构号
			String recOrg = request.getParameter(CreditApplyConstants.recOrg);
			// 推荐人
			String recPerson = request
					.getParameter(CreditApplyConstants.recPerson);
			// 推荐人手机号
			String recContactNo = request
					.getParameter(CreditApplyConstants.recContactNo);
			// 备注
			String remark = request
					.getParameter(CreditApplyConstants.remark);
			// /////////////////////////////////////////////////////////////////////////////////////
			// 商户信息
			// 商户名称
			String posCustName = request
					.getParameter(CreditApplyConstants.posCustName);
			// 主营范围
			String posCustBusiScope = request
					.getParameter(CreditApplyConstants.posCustBusiScope);
			// 经营区划
			String busiDivision = request
					.getParameter(CreditApplyConstants.busiDivision);
			// 详细地址
			String posCustAddress = request
					.getParameter(CreditApplyConstants.posCustAddress);
			// /////////////////////////////////////////////////////////////////////////////
			// 客户信息
			// 客户名
			String custName = request
					.getParameter(CreditApplyConstants.custName);
			// 证件号码
			String paperId = request.getParameter(CreditApplyConstants.paperId);
			// 生日
			String birtDate = request
					.getParameter(CreditApplyConstants.birtDate);
			// 性别
			String sexSign = request.getParameter(CreditApplyConstants.sexSign);
			// 从业年限
			String busiYear = request
					.getParameter(CreditApplyConstants.busiYear);
			// 婚姻状况
			String marrSign = request
					.getParameter(CreditApplyConstants.marrSign);
			// 受教育程度
			String educSign = request
					.getParameter(CreditApplyConstants.educSign);
			// 子女人数
			String childNum = request
					.getParameter(CreditApplyConstants.childNum);
			// 本地房产数
			String localHouseNum = request
					.getParameter(CreditApplyConstants.localHouseNum);
			// 非本地房产数
			String otherHouseNum = request
					.getParameter(CreditApplyConstants.otherHouseNum);
			// 手机号
			String mobilePhone = request
					.getParameter(CreditApplyConstants.mobilePhone);
			// 电话号码
			String tel = request.getParameter(CreditApplyConstants.tel);
			// 客户省
			String residentProv = request
					.getParameter(CreditApplyConstants.residentProv);
			// 客户市
			String residentCity = request
					.getParameter(CreditApplyConstants.residentCity);

			String residentDivision = request
					.getParameter(CreditApplyConstants.residentDivision);
			// 客户详细地址
			String residentDetail = request
					.getParameter(CreditApplyConstants.residentDetail);
			// 微信号
			String weixinNo = request
					.getParameter(CreditApplyConstants.weixinNo);
			// qq号
			String qqNo = request.getParameter(CreditApplyConstants.qqNo);
			// email
			String email = request.getParameter(CreditApplyConstants.email);
			// 配偶姓名
			String familyCustName = request
					.getParameter(CreditApplyConstants.familyCustName);
			// 配偶证件类型
			String matePaperKind = request
					.getParameter(CreditApplyConstants.matePaperKind);
			// 配偶证件号
			String matePaperId = request
					.getParameter(CreditApplyConstants.matePaperId);
			// 配偶生日
			String mateBirtDate = request
					.getParameter(CreditApplyConstants.mateBirtDate);
			// 配偶性别
			String mateSexSign = request
					.getParameter(CreditApplyConstants.mateSexSign);
			// 配偶手机号
			String mateMobilePhone = request
					.getParameter(CreditApplyConstants.mateMobilePhone);
			// 亲属姓名
			String relaCustName = request
					.getParameter(CreditApplyConstants.relaCustName);
			// 亲属类型
			String relaKind = request
					.getParameter(CreditApplyConstants.relaKind);
			// 亲属手机号
			String relaMobilePhone = request
					.getParameter(CreditApplyConstants.relaMobilePhone);
			// 亲属电话号码
			String relaTel = request.getParameter(CreditApplyConstants.relaTel);
			//联系地址类型
			String contactAddrFlag = request.getParameter(CreditApplyConstants.contactAddrFlag);
			//注册登记号
			String regiCode = request.getParameter("regiCode");
			//行内行业分类
			String industryTypeId2 = request.getParameter("industryTypeId2");
			//发生方式
			String occurType = request.getParameter(CreditApplyConstants.occurType);
			// //////////////////////////////////////////////////////////////////////////////////////
			// 银行流水信息
			 // 银行流水信息
//            List<Map<String, Object>> bankSerList = Lists.newArrayList();
            
			// t_credit_apply参数
			Map<String, Object> creMap = Maps.newHashMap();

			if (!StringUtil.isEmpty(applyAmt)) {
				creMap.put(CreditApplyConstants.APPLY_AMT, applyAmt);
			}
			creMap.put(CreditApplyConstants.LOAN_ID,
					StringUtil.isEmpty(loanId) ? null : loanId);
			//creMap.put(CreditApplyConstants.prodId,						//产品确定后不变更
			//		CreditApplyConstants.posLoanFlag);
			//creMap.put(CreditApplyConstants.prodName,						//产品确定后不变更
			//		CreditApplyConstants.posLoanName);
			//creMap.put(CreditApplyConstants.beginDate, new Date());		//beginDate 不更新
			//creMap.put(CreditApplyConstants.applyTerm, "12");				//POS贷无变更UI
			//creMap.put(CreditApplyConstants.termUnit, "M");				//POS贷无变更UI
			if (!StringUtil.isEmpty(channel)) {
				creMap.put(CreditApplyConstants.channel, channel);
			}
			if (!StringUtil.isEmpty(custName)) {
				creMap.put(CreditApplyConstants.custName, custName);
			}
			if (!StringUtil.isEmpty(posCustName)) {
				creMap.put(CreditApplyConstants.posCustName, posCustName);
			}
			if (!StringUtil.isEmpty(busiDivision)) {
				creMap.put(CreditApplyConstants.region, busiDivision);
			}
			if (!StringUtil.isEmpty(currSign)) {
				creMap.put(CreditApplyConstants.currSign,
						StringUtil.isEmpty(currSign) ? null : currSign);
			}
			if (!StringUtil.isEmpty(returnKind)) {
				creMap.put(CreditApplyConstants.returnKind,
						StringUtil.isEmpty(returnKind) ? null : returnKind);
			}
			creMap.put(CreditApplyConstants.recOrg,
					StringUtil.isEmpty(recOrg) ? null : recOrg);
			creMap.put(CreditApplyConstants.recPerson,
					StringUtil.isEmpty(recPerson) ? null : recPerson);
			creMap.put(CreditApplyConstants.recContactNo,
					StringUtil.isEmpty(recContactNo) ? null : recContactNo);
			creMap.put(CreditApplyConstants.remark,
					StringUtil.isEmpty(remark) ? null : remark);
			creMap.put("occurType", occurType);
			// /////////////////////////////////////////////////////////////
			// t_customer_info参数
			Map<String, Object> custMap = Maps.newHashMap();
			if (!StringUtil.isEmpty(custName)) {
				custMap.put(CreditApplyConstants.custName, custName);
			}
			// 证件号码
			if (!StringUtil.isEmpty(paperId)) {
				custMap.put(CreditApplyConstants.PAPER_ID, paperId);
			}
			// 生日
			if (!StringUtil.isEmpty(birtDate)) {
				custMap.put(CreditApplyConstants.birtDate,
						DateUtil.getDatePattern3(birtDate));
			}
			// 性别
			if (!StringUtil.isEmpty(sexSign)) {
				custMap.put(CreditApplyConstants.sexSign, sexSign);
			}
			// 从业年限
			if (!StringUtil.isEmpty(busiYear)) {
				custMap.put(CreditApplyConstants.busiYear, busiYear);
			}
			// 婚姻状况
			if (!StringUtil.isEmpty(marrSign)) {
				custMap.put(CreditApplyConstants.marrSign, marrSign);
			}
			// 受教育程度
			if (!StringUtil.isEmpty(educSign)) {
				custMap.put(CreditApplyConstants.educSign, educSign);
			}
			// 子女人数
			if (!StringUtil.isEmpty(childNum)) {
				custMap.put(CreditApplyConstants.childNum, childNum);
			}
			
			//更新状态下当custId为null时,更新操作是无效的,当custId为null时应视为新增客户  comment by Lin,Zhaolin
			custMap.put(CreditApplyConstants.CUST_ID,
					StringUtil.isEmpty(custId) ? null : custId);
			// 本地房产数
			custMap.put(CreditApplyConstants.localHouseNum,
					StringUtil.isEmpty(localHouseNum) ? null : localHouseNum);
			// 非本地房产数
			custMap.put(CreditApplyConstants.otherHouseNum,
					StringUtil.isEmpty(otherHouseNum) ? null : otherHouseNum);
			// 手机号
			custMap.put(CreditApplyConstants.mobilePhone,
					StringUtil.isEmpty(mobilePhone) ? null : mobilePhone);
			// 电话号码
			custMap.put(CreditApplyConstants.tel,
					StringUtil.isEmpty(tel) ? null : tel);
			// 客户省
			custMap.put(CreditApplyConstants.residentProv,
					StringUtil.isEmpty(residentProv) ? null : residentProv);
			// 客户市
			custMap.put(CreditApplyConstants.residentCity,
					StringUtil.isEmpty(residentCity) ? null : residentCity);

			custMap.put(CreditApplyConstants.residentDivision, StringUtil
					.isEmpty(residentDivision) ? null : residentDivision);
			// 客户详细地址
			custMap.put(CreditApplyConstants.residentDetail,
					StringUtil.isEmpty(residentDetail) ? null : residentDetail);
			// 微信号
			custMap.put(CreditApplyConstants.weixinNo,
					StringUtil.isEmpty(weixinNo) ? null : weixinNo);
			// qq号
			custMap.put(CreditApplyConstants.qqNo,
					StringUtil.isEmpty(qqNo) ? null : qqNo);
			// email
			custMap.put(CreditApplyConstants.email,
					StringUtil.isEmpty(email) ? null : email);
			//custMap.put(CreditApplyConstants.paperKind, StringUtil.isEmpty(paperKind) ? null : paperKind);
			// 配偶姓名
			custMap.put(CreditApplyConstants.familyCustName,
					StringUtil.isEmpty(familyCustName) ? null : familyCustName);
			// 配偶证件类型
			custMap.put(CreditApplyConstants.matePaperKind,
					StringUtil.isEmpty(matePaperKind) ? null : matePaperKind);
			// 配偶证件号
			custMap.put(CreditApplyConstants.matePaperId,
					StringUtil.isEmpty(matePaperId) ? null : matePaperId);
			// 配偶生日
			custMap.put(
					CreditApplyConstants.mateBirtDate,
					StringUtil.isEmpty(mateBirtDate) ? null : DateUtil
							.getDatePattern3(mateBirtDate));
			// 配偶性别
			custMap.put(CreditApplyConstants.mateSexSign,
					StringUtil.isEmpty(mateSexSign) ? null : mateSexSign);
			// 配偶手机号
			custMap.put(CreditApplyConstants.mateMobilePhone, StringUtil
					.isEmpty(mateMobilePhone) ? null : mateMobilePhone);
			//联系地址类型
			custMap.put(CreditApplyConstants.contactAddrFlag, 
					StringUtil.isEmpty(contactAddrFlag) ? null : contactAddrFlag);
			
			// 银行卡信息				
			Map<String, Object> bankMap = Maps.newHashMap();
			// 银行卡号
			bankMap.put(CreditApplyConstants.bankAccno,
					StringUtil.isEmpty(bankAccno) ? null : bankAccno);
			// 银行名
			bankMap.put(CreditApplyConstants.bankName,
					StringUtil.isEmpty(bankName) ? null : bankName);
			// 分行名
			bankMap.put(CreditApplyConstants.bankBranName,
					StringUtil.isEmpty(bankBranName) ? null : bankBranName);
			// 支行名
			bankMap.put(CreditApplyConstants.bankSubName,
					StringUtil.isEmpty(bankSubName) ? null : bankSubName);
			// 录入时间
			bankMap.put(CreditApplyConstants.createTime, new Date());
			
			// 亲属信息				
			Map<String, Object> relMap = Maps.newHashMap();
			relMap.put(CreditApplyConstants.RELA_ID,
					StringUtil.isEmpty(relativeId) ? null : relativeId);
			relMap.put(CreditApplyConstants.relaCustName,
					StringUtil.isEmpty(relaCustName) ? null : relaCustName);
			relMap.put(CreditApplyConstants.relaKind,
					StringUtil.isEmpty(relaKind) ? null : relaKind);
			relMap.put(CreditApplyConstants.relaMobilePhone, StringUtil
					.isEmpty(relaMobilePhone) ? null : relaMobilePhone);
			relMap.put(CreditApplyConstants.relaTel,
					StringUtil.isEmpty(relaTel) ? null : relaTel);
			// 商户信息
			Map<String, Object> posCustMap = Maps.newHashMap();
			posCustMap.put(CreditApplyConstants.POS_CUST_ID,
					StringUtil.isEmpty(posCustId) ? null : posCustId);
			// 商户名
			posCustMap.put(CreditApplyConstants.POS_CUST_NAME,
					StringUtil.isEmpty(posCustName) ? null : posCustName);
			// 主营范围
			posCustMap.put(CreditApplyConstants.posCustBusiScope, StringUtil
					.isEmpty(posCustBusiScope) ? null : posCustBusiScope);
			// 经营区划
			posCustMap.put(CreditApplyConstants.operAddrCode,
					StringUtil.isEmpty(busiDivision) ? null : busiDivision);
			// 详细地址
			posCustMap.put(CreditApplyConstants.posCustAddress,
					StringUtil.isEmpty(posCustAddress) ? null : posCustAddress);
			//注册登记号
			posCustMap.put("regiCode", regiCode);
			//行内行业分类
			posCustMap.put("industryTypeId2", industryTypeId2);
			// posCustMap.put(CreditApplyConstants.operName, value)
			// posCustMap.put(CreditApplyConstants.operDate, new Date());

//			loanPosCreditApplyBackStageBiz.modifyCreditApplyInfo(creMap,custMap, posCustMap, bankMap, relMap,bankSerList);
			List<Map<String,Object>> reqVars = Lists.newArrayList();
			reqVars.add(creMap);
			reqVars.add(custMap);
			reqVars.add(posCustMap);
			reqVars.add(bankMap);
			reqVars.add(relMap);
//			reqVars.add(bankSerList);
			return reqVars;
//			out.print("申请更新成功");
		} catch (Exception e) {
		    logger.error("业务申请更新失败" , e);
//			out.print("业务申请更新失败.ERRPR:"+e.getMessage());
//		    return false;
		}
		return null;
//		return true;

	}

	/**
	 * 返回流水月份
	 */
	@RequestMapping("/getSerialMonth")
	public ModelAndView getSerialMonth(
			@RequestParam(value = "startMonth", required = false) Integer startMonth,
			@RequestParam(value = "endMonth", required = false) Integer endMonth,
			PrintWriter out) {
		List<Map<String, String>> monthList = DateUtil.getDurOverNumMonths(
				startMonth, endMonth, 12);
		out.print(JSON.toJSONString(monthList));
		return null;
	}
	
	/**
	 * 修改申请状态
	 */
	@RequestMapping("/updateApplyStatus")
	public ModelAndView updateApplyStatus(@RequestParam(value="loanStr", required=false)String loanStr,
			@RequestParam(value = "applyStatus", required=false)String applyStatus, PrintWriter out){
		Map<String,Object> respMap = Maps.newHashMap();
		Map<String,Object> checkResultMap = Maps.newHashMap();
		List<Map<String, Object>> detailCredit = new ArrayList<Map<String,Object>>();
		String channel = "";
		if(!StringUtil.isEmpty(loanStr)){
			for(String loanId : loanStr.trim().split("\\|")){
			    detailCredit = loanPosCreditApplyBackStageBiz.getCreditApplyDetail(loanId);
			    if(detailCredit != null){
			        channel = (String)detailCredit.get(0).get("channel");
			    }
			    // 业务申请校验
	            try {
	                respMap = loanPosCreditApplyCheckRegBiz.creditApplyCheck(loanId);
	                if("000".equals(respMap.get("checkCode"))){
	                    logger.info("校验返回码[{}],返回消息[{}]" , respMap.get("checkCode"),respMap.get("checkMessage"));
	                    checkResultMap = outerInterfaceCheck(loanId, channel);
	                    logger.info("外部接口校验结果："+checkResultMap);
	                    if(checkResultMap != null){
	                    	 Map<String, Object> creMap2 = Maps.newHashMap();
		                       creMap2.put("applyStatus", applyStatus);
		                       creMap2.put("loanId", loanId);
		                       loanPosCreditApplyBackStageBiz.modifyCreditApplyInfo(creMap2, null, null, null, null, null);
	                    }
	                }
	            } catch (Exception e) {
	                logger.error("业务规则校验异常", e);
	                out.print("系统异常，提交失败");
	                return null;
	            }
			}
			
		}
		out.print("提交成功");
		return null;
	}
	
	@RequestMapping("/queryExistCustomer")
	public ModelAndView queryExistCustomer(@RequestParam(value="paperId", required = false)String paperId, PrintWriter out){
		List<TCustomer> cusList = loanPosCustomerBackStageBiz.queryCustomerByPaperId(paperId);
		out.print(JSON.toJSONString(cusList));
		return null;
	}

    /**
     * 影像资料回调处理
     * 
     * @param loanid
     * @param respCode
     * @param request
     * @param out
     * @return
     */
    @RequestMapping("/uploadImageDataBackController")
    public ModelAndView updateBackToReview(
            @RequestParam(value = "LocalSubFolderNameDef", required = false) String loanid,
            @RequestParam(value = "RespCode", required = false) String respCode,
            HttpServletRequest request, PrintWriter out) {
        
        logger.debug("loanid="+ loanid);
        logger.debug("respCode="+ respCode);
        
        // 处理成功
        if (CreditApplyHServiceConstants.ftp_resp_code_000.equals(respCode)) {
            logger.debug("影像件上传成功");
        } else {
            logger.debug("影像件上传失败");
            //TODO 记录  推送

        }
        return null;
    }
    
    @RequestMapping("/queryImageDataList")
    public ModelAndView queryImageDataList(@RequestParam(value = "rows", required = false) Integer pageSize,
                                          @RequestParam(value = "page", required = false) Integer pageNo,
                                          HttpServletRequest request, PrintWriter out){
         Map<String, Object> reqMap = Maps.newHashMap();

         if(!StringUtils.isEmpty(request.getParameter("custName"))){
             reqMap.put("custNameLike", ControllerHelper.getLikeString(request.getParameter("custName")));
         }
         if(!StringUtils.isEmpty(request.getParameter("bizLoanId"))){
             reqMap.put("bizLoanIdLike", ControllerHelper.getLikeString(request.getParameter("bizLoanId")));
         }
         reqMap.put("applyStatus", "20");
         reqMap.put("startPage", (pageNo - 1)*pageSize);
         reqMap.put("limit", pageSize);
         List<Map<String, Object>> lists = loanPosCreditApplyBackStageBiz.selectSelectiveMap(reqMap);
         // 查询记录总件数
         long count = loanPosCreditApplyBackStageBiz.selectSelectiveCount(reqMap);
         JSONObject aaJson =  new JSONObject();
         aaJson.put("total", count);
         aaJson.put("rows", lists);      
         out.write(aaJson.toJSONString());
         return null;
     }
    
//    @RequestMapping("/downLoadimgData")
//    public ModelAndView downLoadimgData(HttpServletRequest request,HttpServletResponse response) throws IOException{
//        Map<String,Object> reqMap = Maps.newHashMap();
//        reqMap.put("codeNo", BusinessDictionaryConstants.bizFileType);
//        reqMap.put(CreditApplyConstants.ITEMN_NO_LIKE, "01__");
//        List<Map<String, Object>> bizFileTypeList = loanPosBusinessCodeBiz.getSeletiveMap(reqMap);
//        String loanId = request.getParameter("loanId");
//        String custName = request.getParameter("custName");
//        custName = new String(custName.getBytes("ISO8859-1"),"UTF-8");
//        if(StringUtils.isEmpty(loanId)){
//            logger.info("申请编号为空");
//            return null;
//        }
//        OutputStream outputStream = null;                                                                       
//        InputStream inStream = null;
//        
//        StringBuffer filePath = new StringBuffer(sourcePath);
//        String path = filePath.append("/").append(loanId).toString(); 
//        File file = new File(path);
//        File[] imgDataFiles = file.listFiles();
//        String tempPath = path+"/temp";   //临时存放文件的目录,在这个目录转换影像文件的名称
//        File zipfile = new File(tempPath+"/temp.zip");// 压缩文件
//        FileUtil fileUtil = new FileUtil();
//        if(null == imgDataFiles || imgDataFiles.length<1){
//            logger.info("待压缩的文件目录：" + path + "里面不存在文件."); 
//        }else{
//            try {               
//                fileUtil.setBizFileTypeList(bizFileTypeList);
//                File[] TempFileList = copyFileList(imgDataFiles,tempPath);  //将原始目录下所有的文件复制到临时目录下
//                File[] destFiles = fileUtil.renameFile(TempFileList);   //重命名文件
//                ZipUtils.ZipFiles(destFiles, zipfile);
//                inStream = new FileInputStream(zipfile);
//                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");                                                   
//                String filename = custName;                
//                if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") >0) {
//                    filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");//firefox浏览器
//                } else {
//                    filename = URLEncoder.encode(filename, "utf-8");
//                }
//                
//                response.setContentType("application/octet-stream");                                                
//                response.setCharacterEncoding("UTF-8");                                                             
//                response.setHeader("Cache-Control", "no-cache");                                                    
//                response.addHeader("Content-Disposition", "attachment; filename=\"" + filename + sdf1.format(new Date())+".zip\"");         
//                byte[] b = new byte[100];                                                                           
//                int len;                                                                                            
//                outputStream = response.getOutputStream();                                                          
//                while ((len = inStream.read(b)) > 0) {                                                              
//                    outputStream.write(b, 0, len);                                                                  
//                } 
//             }catch (Exception e) {
//                logger.error("下载影像文件失败", e);
//             }finally{                                                                                               
//                 if (inStream != null) {                                                                             
//                     inStream.close();                                                                               
//                 }                                                                                                   
//                 if (outputStream != null) {                                                                         
//                     outputStream.close();                                                                           
//                 }
//                 fileUtil.deleteFile(new File(tempPath));
//             } 
//        }
//
//         return null;
//     }
      
    public File[] copyFileList(File[] fileArray,String tempPath) throws IOException{
        File[] tempFileList = null;
        try {
            if(FileUtil.createDirectory(tempPath)){
                for(File file : fileArray){
                    File tempFile = new File(tempPath+"/"+file.getName());                    
                    FileCopyUtils.copy(file, tempFile);                                            
                  }
                tempFileList = new File(tempPath).listFiles();
                }
          }catch (IOException e) {
            logger.error("复制文件至temp目录失败", e);
            throw e;
        }
        
        return tempFileList;
    }
    
    @RequestMapping("/viewProtocol")
    public ModelAndView viewProtocol(HttpServletRequest request,HttpServletResponse response) throws IOException{
        String loanId = request.getParameter("loanId");
        if(StringUtils.isEmpty(loanId)){
            logger.info("申请编号为空");
            return null;
        }        
        String protocolName = imageDataViewBiz.getProtocolName(loanId);
        logger.debug("看协议protocolName:" + protocolName);
        InputStream in = null;
        OutputStream out = null;
        URL url = null;
        HttpURLConnection con = null;
        try {
            if(StringUtils.isNotEmpty(protocolName)){
            	String imageUrl = SysConfigFactory.getInstance().getService("basicService").getPropertyValue("fsUrl");
            	logger.debug("看协议imageUrl:" + imageUrl);
                url = new URL(imageUrl+"imagedatawork/"+loanId+"/"+protocolName);
                logger.debug("看协议url：" + url);
                con = (HttpURLConnection) url.openConnection();
                in = con.getInputStream();
                out = response.getOutputStream();
                byte[] b = new byte[1024];
                int len = 0;
                response.reset();
                response.setContentType("application/pdf");
                response.setCharacterEncoding("UTF-8");  
                response.setHeader("Content-Disposition", "inline;filename="+ protocolName);           
                while((len=in.read(b))!=-1){
                    out.write(b, 0, len);
                }
                out.flush();
            }else{
                logger.error("此目录下没有协议文件");
            }
        }catch (IOException e) {
            logger.error("无法连接到服务器的协议文件", e);
        }finally{
            con.disconnect();
            if (in != null) {
                in.close();                
            }
            if(out !=null){
                out.close();
            }
        }
        
        return null;       
    }
    
    /**
     * 外部接口检查
     * 
     * @param loanId
     * @param channel
     * @return
     */
    public Map<String, Object> outerInterfaceCheck(String loanId, String channel) {

        Map<String, Object> respMap = Maps.newHashMap();

        if (StringUtil.isEmpty(loanId) || StringUtil.isEmpty(channel)) {
            logger.info("申请编号或业务渠道为空");
            return null;
        }

        boolean bankCheckFlag = false;
//        boolean aicCheckFlag = false;
        boolean imageCheckFlag = false;

        try {
            List<Map<String, Object>> loanDetails = loanPosCreditApplyBackStageBiz
                .getCreditApplyDetail(loanId);
            Map<String, Object> reqMap = Maps.newHashMap();
            reqMap.put("loanid", loanId);
            reqMap.put("channel", channel);
            reqMap.put("imagefilepackagename", loanDetails.get(0).get("imagefilepackagename"));

            // 易极付请求参数
            Map<String, String> bankCheckMap = Maps.newHashMap();
            bankCheckMap.put("cardNo", (String) loanDetails.get(0).get("bankAccno"));
            bankCheckMap.put("cardName", (String) loanDetails.get(0).get("custName"));
            bankCheckMap.put("certNo", (String) loanDetails.get(1).get("paperId"));

            // 工商请求参数
            Map<String, String> aicCheckMap = Maps.newHashMap();
            aicCheckMap.put("posCustId", (String) loanDetails.get(3).get("posCustId"));
            aicCheckMap.put("posCustName", (String) loanDetails.get(3).get("posCustName"));

            logger.debug(loanId + "准入性校验通过,开始调用账户验真接口");
            // 账户验真
            try {
                String status = (String) loanDetails.get(4).get("status");
                if (!"01".equals(status)) {//账户验证不通过
                    Map<String, String> resultMap = IbankCardCheckHessianService
                        .bankCardCheck(bankCheckMap);
                    logger.debug("返回结果为:" + resultMap);
                    if (resultMap.get("respCode") != null && "00".equals(resultMap.get("respCode")))
                        bankCheckFlag = true;
                } else {
                    bankCheckFlag = true;
                    logger.info("账户验证接口已经查询过");
                }
            } catch (Exception e) {
                logger.error("账户验真发生异常", e.getMessage());
            }
            logger.debug("账户验真完成，接下来调用工商接口");
            /*// 工商
            try{
                if(!policeAndAICConnectBiz.hasAICInfo(aicCheckMap.get("posCustName"))){
                    Map<String, String> resultMap = aicHessianService.getCustAICInfo(aicCheckMap);
                    logger.debug("调用工商结果为:" + resultMap);
                    if(resultMap.get("respCode")!=null && "00".equals(resultMap.get("respCode")))
                    aicCheckFlag = true;
                }else{
                    aicCheckFlag = true;
                    logger.info("工商信息已经查询过");
                }
            }catch(Exception e){
                logger.error(loanId + "调用工商异常" + e.getMessage());
            }*/

            // 影像件下载
            try {
                String custId = (String) loanDetails.get(1).get("custId");
                List<Map<String, Object>> images = imageDataViewBiz.getImageDataNames(loanId,
                    custId);
                if (images == null || images.size() < 1) {
                    boolean uploadImg = loanPosCreditApplyBackStageBiz.getImgSync(reqMap);
                    if (uploadImg) {
                        logger.info("影像件下载成功");
                        imageCheckFlag = true;
                    } else {
                        logger.info("影像件下载失败");
                    }
                }
            } catch (Exception e) {
                logger.error("影像件下载异常", e);
            }

        } catch (Exception e) {
            logger.error("接口调用失败", e);
        }
        respMap.put("bankCheckFlag", bankCheckFlag);
//        respMap.put("aicCheckFlag", aicCheckFlag);
        respMap.put("imageCheckFlag", imageCheckFlag);
        return respMap;
    }
    
    /**
     * 手动执行外部接口
     * 
     * @author cjq
     * @param loanId
     * @param channelName
     * @param out
     * @return
     */
    @RequestMapping("/outerInterfaceProcess")
    public ModelAndView outerInterfaceProcess(@RequestParam(value="loanId",required=false)String loanId , 
                                              @RequestParam(value="channel",required=false)String channel,
                                              HttpServletRequest request,
                                              PrintWriter out){
        logger.debug("申请编号loanId[{}],业务渠道channel[{}]" , loanId , channel);
        
        Map<String,Object> respMap = Maps.newHashMap();
        
        if(StringUtil.isEmpty(loanId) || StringUtil.isEmpty(channel)){
            out.print("申请编号或业务渠道为空");
            return null;
        }
        boolean bankCheckFlag = false;
//        boolean aicCheckFlag = false;
        boolean imageCheckFlag = false;
        
        try {
            List<Map<String,Object>> loanDetails = loanPosCreditApplyBackStageBiz.getCreditApplyDetail(loanId);
            Map<String, Object> reqMap = Maps.newHashMap();
            reqMap.put("loanid", loanId);
            reqMap.put("channel", channel);
            reqMap.put("imagefilepackagename",loanDetails.get(0).get("imagefilepackagename"));
            
            // 易极付请求参数
            Map<String, String> bankCheckMap =Maps.newHashMap();
            bankCheckMap.put("cardNo", (String)loanDetails.get(0).get("bankAccno"));
            bankCheckMap.put("cardName", (String)loanDetails.get(0).get("custName"));
            bankCheckMap.put("certNo", (String)loanDetails.get(1).get("paperId"));
            
            // 工商请求参数
            Map<String, String> aicCheckMap = Maps.newHashMap();
            aicCheckMap.put("posCustId", (String)loanDetails.get(3).get("posCustId"));
            aicCheckMap.put("posCustName", (String)loanDetails.get(3).get("posCustName"));
            
            logger.debug(loanId + "准入性校验通过,开始调用账户验真接口");
            // 账户验真
           try{
                String status = (String)loanDetails.get(4).get("status");
                if(!"01".equals(status)){//账户验证不通过
                    Map<String, String> resultMap = IbankCardCheckHessianService.bankCardCheck(bankCheckMap);
                    logger.debug("返回结果为:" + resultMap);
                    if(resultMap.get("respCode")!=null && "00".equals(resultMap.get("respCode")))
                    bankCheckFlag = true;
                    respMap.put("bankCheckRespMap", resultMap);
                }else{
                    logger.info("账户验证接口已经查询过");
                    bankCheckFlag = true;
                    respMap(respMap, "00", "账户验真成功", "bankCheckRespMap");
                }
            }catch(Exception e){
                logger.error("账户验真发生异常",e.getMessage());
                Map<String, String> resultMap = Maps.newHashMap();
                resultMap.put("respCode", "01");
                resultMap.put("respMsg", "账户验真异常");
                respMap.put("bankCheckRespMap", resultMap);
            }
            logger.debug("账户验真完成，接下来调用工商接口");
            /*// 工商
            try{
                if(!policeAndAICConnectBiz.hasAICInfo(aicCheckMap.get("posCustName"))){
                    Map<String, String> resultMap = aicHessianService.getCustAICInfo(aicCheckMap);
                    logger.debug("调用工商结果为:" + resultMap);
                    if(resultMap.get("respCode")!=null && "00".equals(resultMap.get("respCode")))
                    aicCheckFlag = true;
                    respMap.put("airCheckRespMap", resultMap);
                }else{
                    aicCheckFlag = true;
                    logger.info("工商信息已经查询过");
                }
            }catch(Exception e){
                logger.error(loanId + "调用工商异常" + e.getMessage());
                respMap(respMap,"01","解析工商返回报文发生异常","airCheckRespMap");
            }*/

            // 影像件下载
            try {
                String custId = (String)loanDetails.get(1).get("custId");
                List<Map<String,Object>> images = imageDataViewBiz.getImageDataNames(loanId, custId);
                if(images ==null || images.size() < 1){
                    boolean uploadImg = loanPosCreditApplyBackStageBiz.getImgSync(reqMap);
                    if(uploadImg){
                        logger.info("影像件下载成功");
                        imageCheckFlag = true;
                        respMap(respMap, "00", "影像件下载成功", "imgCheckRespMap");
                    }else{
                        logger.info("影像件下载失败");
                        respMap(respMap, "01", "影像件下载失败", "imgCheckRespMap");
                    }
                }
            } catch (Exception e) {
                logger.error("影像件下载异常", e);
                respMap(respMap, "01", "影像件下载异常", "imgCheckRespMap");
            }
            
            JSONObject json = new JSONObject();
            json.putAll(respMap);
            out.print(json.toJSONString());
            
            User user = (User)request.getSession().getAttribute("USER");
            
            //如果外部接口以及影像件资料通过，更改标的状态为资料审核中，applyStatus = 20
            if(bankCheckFlag /*&& aicCheckFlag */ && imageCheckFlag){
                //step1：更新申请表
                Map<String,Object> updataCreditMap = Maps.newHashMap();
                updataCreditMap.put("loanId", loanId);
                updataCreditMap.put("applyStatus", "20");
                updataCreditMap.put("lastOperId", user.getUserName());
                updataCreditMap.put("lastOperTime", new Date());
                int flag1 = loanPosCreditApplyService.updateCreditApplyMap(updataCreditMap);
                //step2：更新审批记录表
                Map<String,Object> updataCreditAprvMap = Maps.newHashMap();
                updataCreditAprvMap.put("loanId", loanId);
                updataCreditAprvMap.put("apprResult", "10");
                updataCreditAprvMap.put("appEndTime", new Date());
                int flag2 = creditApplyAprvInfoBiz.updateCreditApplyAprvInfo(updataCreditAprvMap);
                if(flag1 > 0)
                    logger.debug("更新申请信息成功");
                if(flag2 > 0)
                    logger.debug("更新审批记录成功");
                if(flag1 > 0 && flag2 > 0){
                    logger.debug("业务提交到审批更新成功");
                }
            }
            return null;
        } catch (Exception e) {
            logger.error("执行外部接口异常", e.getMessage());
            respMap(respMap, "01", "系统异常", "systemRespMap");
            JSONObject json = new JSONObject();
            json.putAll(respMap);
            out.print(json.toJSONString());
            return null;
        }
    }
    
    /**
     * 手动执行外部接口返回信息
     * 
     * @author cjq
     * @param respMap
     * @param resCode
     * @param resMsg
     * @param checkRespMapName
     * @return
     */
    private void respMap(Map<String, Object> respMap, String resCode, String resMsg,String checkRespMapName) {
        Map<String, String> resultMap = Maps.newHashMap();
        resultMap.put("respCode", resCode);
        resultMap.put("respMsg", resMsg);
        respMap.put(checkRespMapName, resultMap);
    }
    
    /**
     * 查询工商信息
     * 
     * @author cjq
     * @param posCustName
     * @param out
     * @return
     */
    @RequestMapping("/queryAicInfo")
    public ModelAndView queryAicInfo(@RequestParam(value="posCustId",required=false)String posCustId,
                                     @RequestParam(value="directTo", required = false)String directTo,
                                     PrintWriter out){
        try {
            ModelAndView mav = new ModelAndView();
            TPosCustInfo posCustInfo = loanPosCustServiceImpl.selectOnePosCust(posCustId);
            logger.debug("商户信息为"+posCustInfo);
            if(posCustInfo != null ){
                Map<String,List<Map<String,Object>>> aicInfo = policeAndAICConnectBiz.queryAicInfo(posCustInfo.getPosCustName(), posCustId);
                logger.debug("工商信息为"+aicInfo);
                mav.addObject("aicInfo",JSON.toJSON(aicInfo));
                mav.setViewName((directTo!=null)?directTo:"creditApply/aicInfoPage");
                return mav;
            }
        } catch (Exception e) {
            logger.error("查询工商信息异常", e);
            return null;
        }
        return null;
    }
    
    /**
     * 获取工商信息
     */
    @RequestMapping("/getAicInfo")
    public ModelAndView getAicInfo(@RequestParam(value="loanId", required = false)String loanId, @RequestParam(value="confirmFlag", required = false)String confirmFlag, PrintWriter out){
    	try{
    		TCreditApply apply = tCreditApplyDao.queryCreditApplyInfo(loanId);
    		if(apply == null){
    			out.print("没有该申请");
    			return null;
    		}
    		Map<String, String> aicCheckMap = Maps.newHashMap();
    		aicCheckMap.put("posCustId", apply.getPosCustId());
    		aicCheckMap.put("posCustName", apply.getPosCustName());
    		aicCheckMap.put("confirmFlag", confirmFlag);
    		Map<String, String> resultMap = aicHessianService.getCustAICInfo(aicCheckMap);
			logger.debug("调用工商结果为:" + resultMap);
			out.print((String)resultMap.get("respMsg"));
    	}catch(Exception e){
    		logger.error("获取工商信息异常", e);
    		out.print("获取工商信息异常");
    		return null;
    	}
    	return null;
    }
    
    @RequestMapping("/getBankSerialInfo")
    public ModelAndView getBankSerialInfo(@RequestParam(value="loanId",required=false)String loanId,
                                          PrintWriter out){
        if (StringUtil.isEmpty(loanId)) {
            return null;
        }
        Map<String, Object> resultMap = loanPosCreditApplyService
            .selectNameAndBankAccNoByLoanId(loanId);
        
        if (StringUtil.isEmpty((String) resultMap.get("bankAccno"))) {
            logger.info("bankAccno为空");
            return null;
        }
        
        if(StringUtil.isEmpty((String)resultMap.get("custName"))){
            logger.info("custName为空");
            return null;
        }
        
        String card = (String) resultMap.get("bankAccno");
        String name = (String) resultMap.get("custName");
        JSONObject json = new JSONObject();
        json.put("searchCard", card);
        json.put("searchName", name);
        out.print(json.toJSONString());
        return null;
    }


    /**
     * 申请撤回
     * 
     * @param loanId
     * @param out
     * @return
     */
    @RequestMapping("/withdrawCreditApply")
    public ModelAndView withdrawCreditApply(@RequestParam(value="loanId",required=false)String loanId,PrintWriter out){
        if(StringUtil.isEmpty(loanId)){
           logger.info("loanId为空,撤回失败"); 
           out.print("该笔申请不存在");
           return null;
        }
        // 判断该笔申请是否已被认领
        TCreditApply creditApply =  loanPosCreditApplyBackStageBiz.queryCreditApplyDetail(loanId);
        if(creditApply == null){
            logger.info("该笔申请[{}]"+loanId+"不存在"); 
            out.print("该笔申请不存在");
            return null;
        }else{
            String applyStatus = creditApply.getApplyStatus();
            String person = creditApply.getInfoCheckPerson();
            if(!StringUtil.isEmpty(applyStatus) && ("20".equals(applyStatus))){
                if(StringUtil.isEmpty(person)){
                    logger.info("该笔申请[loanId = "+loanId+ " ]未被认领,可以撤回修改");
                    // 如果未被认领，则可以撤回（更改状态到待处理）
                    Map<String, Object> creMap = Maps.newHashMap();
                    creMap.put("applyStatus", "00");
                    creMap.put("loanId", loanId);
                    loanPosCreditApplyBackStageBiz.modifyCreditApplyInfo(creMap, null, null, null, null, null);
                    out.print("撤回成功");
                    return null;
                }else{
                    logger.info("该笔申请[loanId = "+loanId+ " ]已被认领,不可以撤回修改");
                    out.print("该笔申请[loanId = "+loanId+ " ]已被认领,不可以撤回修改");
                    return null;
                }
            }else{
                logger.info("该笔申请[loanId = "+loanId+ " ]不在资料审核阶段，不可以撤回");
                out.print("该笔申请[loanId = "+loanId+ " ]不在资料审核阶段，不可以撤回");
                return null;
            }
        }
    }
    
    /**
     * 补件完成（将申请状态有21-20）
     * 
     * @param loanId
     * @param out
     * @return
     */
    @RequestMapping("/finishAddInfo")
    public ModelAndView finishAddInfo(@RequestParam(value="loanId",required=false)String loanId,
                                      PrintWriter out){
        if(StringUtil.isEmpty(loanId)){
            out.print("该笔申请不存在,完成补件失败");
            return null;
        }
        // 更改申请状态
        Map<String, Object> creMap = Maps.newHashMap();
        creMap.put("applyStatus", "20");
        creMap.put("loanId", loanId);
        loanPosCreditApplyBackStageBiz.modifyCreditApplyInfo(creMap, null, null, null, null, null);
        out.print("操作成功");
        return null;
    }
}
