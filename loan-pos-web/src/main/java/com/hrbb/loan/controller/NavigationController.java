package com.hrbb.loan.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hrbb.loan.constants.CreditApplyConstants;
import com.hrbb.loan.constants.PaybackApplyConstants;
import com.hrbb.loan.constants.PaymentApplyConstants;
import com.hrbb.loan.constants.black.BlacklistConstants;
import com.hrbb.loan.constants.customer.CustomerInfoConstants;
import com.hrbb.loan.controller.helper.ControllerHelper;
import com.hrbb.loan.pay.api.PayCollectQueryService;
import com.hrbb.loan.pay.api.request.SingleCollectRequest;
import com.hrbb.loan.pay.api.response.SingleCollectResponse;
import com.hrbb.loan.pay.constants.AccountType;
import com.hrbb.loan.pay.constants.CertType;
import com.hrbb.loan.pay.constants.PayChannel;
import com.hrbb.loan.pay.constants.ProductCode;
import com.hrbb.loan.pos.biz.backstage.inter.ActivityBiz;
import com.hrbb.loan.pos.biz.backstage.inter.BusinessDictionaryBiz;
import com.hrbb.loan.pos.biz.backstage.inter.CreditApplyAprvInfoBiz;
import com.hrbb.loan.pos.biz.backstage.inter.IGenericConfigBiz;
import com.hrbb.loan.pos.biz.backstage.inter.ILoanPosBusinessCodeBiz;
import com.hrbb.loan.pos.biz.backstage.inter.ILoanPosCreditApplyBackStageBiz;
import com.hrbb.loan.pos.biz.backstage.inter.IPaymentApplyBiz;
import com.hrbb.loan.pos.biz.backstage.inter.IReceiptManageBiz;
import com.hrbb.loan.pos.biz.backstage.inter.ImageDataViewBiz;
import com.hrbb.loan.pos.biz.backstage.inter.LoanPosContractManagementBiz;
import com.hrbb.loan.pos.biz.backstage.inter.LoanRiskCheckBiz;
import com.hrbb.loan.pos.biz.backstage.inter.UploadBizInter;
import com.hrbb.loan.pos.biz.backstage.inter.UserApprInfoBiz;
import com.hrbb.loan.pos.biz.bean.Notify4Archive;
import com.hrbb.loan.pos.dao.TCfgFundingPoolDao;
import com.hrbb.loan.pos.dao.entity.TActivityContentInfo;
import com.hrbb.loan.pos.dao.entity.TActivityDimInfo;
import com.hrbb.loan.pos.dao.entity.TActivityTypeInfo;
import com.hrbb.loan.pos.dao.entity.TApproveResult;
import com.hrbb.loan.pos.dao.entity.TBankAccnoInfo;
import com.hrbb.loan.pos.dao.entity.TBdInstitution;
import com.hrbb.loan.pos.dao.entity.TCfgChannelAccount;
import com.hrbb.loan.pos.dao.entity.TContractManagement;
import com.hrbb.loan.pos.dao.entity.TCreditApply;
import com.hrbb.loan.pos.dao.entity.TCreditApplyAprvInfo;
import com.hrbb.loan.pos.dao.entity.TCustomer;
import com.hrbb.loan.pos.dao.entity.TPaymentApply;
import com.hrbb.loan.pos.dao.entity.TRiskCheckResult;
import com.hrbb.loan.pos.dao.entity.TUserApprInfo;
import com.hrbb.loan.pos.factory.SysConfigFactory;
import com.hrbb.loan.pos.service.BDManagementService;
import com.hrbb.loan.pos.service.ChannelMapperDictionaryService;
import com.hrbb.loan.pos.service.LoanPosCreditApplyService;
import com.hrbb.loan.pos.service.LoanPosCustomerService;
import com.hrbb.loan.pos.service.RepaymentApplyService;
import com.hrbb.loan.pos.util.DateUtil;
import com.hrbb.loan.pos.util.FileUtil;
import com.hrbb.loan.pos.util.IdUtil;
import com.hrbb.loan.pos.util.ImageUtil;
import com.hrbb.loan.pos.util.StringUtil;
import com.hrbb.loan.pos.util.ZipUtils;
import com.hrbb.loan.pos.util.constants.BusinessDictionaryConstants;
import com.hrbb.loan.pos.util.constants.ReviewNoteConstants;
import com.hrbb.loan.spi.ZJ.ZJAddImageDataServiceImpl;
import com.hrbb.loan.web.security.entity.AccessPrivilege;
import com.hrbb.loan.web.security.entity.User;


/**
 * <h1></h1>
 * 
 * @author Johnson Song
 * @version Id: NavigationController.java, v 1.0 2015-2-26 下午3:27:47 Johnson
 *          Song Exp
 */
@Controller
@RequestMapping("/navigation")
public class NavigationController {

	private Logger logger = LoggerFactory.getLogger(NavigationController.class);

	private List<Map<String, Object>> channelDictionary;

	private List<Map<String, Object>> posTypeDictionary;

	private List<Map<String, Object>> provinceList;

	private List<Map<String, Object>> paperList;

	private List<Map<String, Object>> currSignList;
	

	private List<Map<String, Object>> returnKindList;

	private List<Map<String, Object>> sexList;

	private List<Map<String, Object>> maritalList;

	private List<Map<String, Object>> eduList;

	private List<Map<String, Object>> relList;

	private List<Map<String, Object>> cities;

	private List<Map<String, Object>> divisions;

	private List<Map<String, Object>> bankNoList;

	private List<Map<String, Object>> implTypeList;

	private List<Map<String, Object>> repayMethodList;

	private List<Map<String, Object>> paymentApplyList;

	private List<Map<String, Object>> loanPurposeList;

	private List<Map<String, Object>> contactFlags;

	private List<Map<String, Object>> callingTypeList;
	
	private List<Map<String, Object>> occurTypes;
	
	//区域风险等级
	private List<Map<String, Object>> regionRiskTypeList;
	//渠道风险等级
	private List<Map<String, Object>> channelRiskTypeList;
	//产品编码
	private List<Map<String, Object>> productNoList;
	//是否生效
	private List<Map<String, Object>> yesNoList;
	
	@Autowired
	@Qualifier("uploadBiz")
	private  UploadBizInter u2fs;

	private List<Map<String, Object>> HrrbIndustTypeList;
	// 拒绝代码
	private List<Map<String, Object>> refuseCodeList;
	// 通过代码
	private List<Map<String, Object>> passCodeList;

	@Autowired
	private ILoanPosBusinessCodeBiz loanPosBusinessCodeBiz;

	@Autowired
	private LoanPosCreditApplyService loanPosCreditApplyService;

	@Autowired
	private LoanPosCustomerService loanPosCustomerService;

	@Autowired
	private ILoanPosCreditApplyBackStageBiz loanPosCreditApplyBackStageBiz;

	@Autowired
	@Qualifier("zjAddImageDataService")
	private ZJAddImageDataServiceImpl zJAddImageDataServiceImpl;
	/*
	 * @Autowired private HFTPService hftpService;
	 */

	@Autowired
	private LoanPosContractManagementBiz loanPosContractManagementBiz;
	
	@Autowired
	private RepaymentApplyService repaymentApplyService;

	@Autowired
	private ImageDataViewBiz imageDataViewBiz;

	@Autowired
	private IPaymentApplyBiz paymentApplyBiz;
	
	@Autowired
	private UploadBizInter uploadBiz;
	
	@Autowired
	private TCfgFundingPoolDao tCfgFundingPoolDao;

	@Autowired
	private IReceiptManageBiz receiptManageBiz;
	@Autowired
	@Qualifier("loanRiskCheckBiz")
	private LoanRiskCheckBiz riskCheckBiz;

	@Autowired
	@Qualifier("genericConfigBiz")
	private IGenericConfigBiz configBiz;

	@Autowired
	@Qualifier("creditApplyAprvInfoBiz")
	private CreditApplyAprvInfoBiz apprBiz;

	@Autowired
	@Qualifier("userApprInfoBiz")
	private UserApprInfoBiz userApprInfoBiz;

	@Autowired
	private BusinessDictionaryBiz bdBiz;

//    @Value("#{systemInfo[imageUrl]}")
    private String imageUrl;
    
    @Autowired
    private ActivityBiz activityBiz;
    
    @Autowired
    private BDManagementService bdManagementService;
    
    @Autowired
    private PayCollectQueryService payCollectQueryHessian;
    
    @Autowired
    private ChannelMapperDictionaryService channelMapperDictionaryService;

//	@Value("#{systemInfo[url]}")
	private String webUrl;

	@PostConstruct
	public void loadDictionary() {
		/*加载系统参数配置*/
		imageUrl = SysConfigFactory.getInstance().getService("basicService").getPropertyValue("fsUrl");
		webUrl = SysConfigFactory.getInstance().getService("basicService").getPropertyValue("webUrl");

		// channelDictionary =
		// loanPosBusinessCodeBiz.getItemNames(BusinessDictionaryConstants.BizChannel);
		/*
		 * 对BizChannel进行过滤不显示ZZ, 对displayNo作为排序基准 add by Lin,Zhaolin at
		 * 2015-5-25
		 */
		Map<String, Object> reqChannel = Maps.newHashMap();
		reqChannel.put("codeNo", BusinessDictionaryConstants.BizChannel);
		reqChannel.put("extAttr", "Y");
		channelDictionary = loanPosBusinessCodeBiz.getItemNamesWithExtOrder(reqChannel);

		posTypeDictionary = loanPosBusinessCodeBiz.getItemNames(BusinessDictionaryConstants.POSType);
		loanPurposeList = loanPosBusinessCodeBiz.getItemNames(BusinessDictionaryConstants.Purpose);
		provinceList = loanPosBusinessCodeBiz.getProvinceCityOrDic("__0000");
		Map<String, Object> reqMap = Maps.newHashMap();
		reqMap.put("codeNo", BusinessDictionaryConstants.AdminDivision); // add by Lin,Zhaolin
		reqMap.put(CreditApplyConstants.ITEMN_NO_LIKE, "____00");
		reqMap.put(CreditApplyConstants.ITEM_NO_NOT_LIKE, "__0000");
		cities = loanPosBusinessCodeBiz.getSeletiveMap(reqMap);
		reqMap = Maps.newHashMap();
		reqMap.put("codeNo", BusinessDictionaryConstants.AdminDivision); // add by Lin,Zhaolin
		reqMap.put(CreditApplyConstants.ITEMN_NO_LIKE, "______");
		reqMap.put(CreditApplyConstants.ITEM_NO_NOT_LIKE, "__0000");
		reqMap.put(CreditApplyConstants.ITEM_NO_NOT_LIKE_2, "____00");
		divisions = loanPosBusinessCodeBiz.getSeletiveMap(reqMap);
		paperList = loanPosBusinessCodeBiz
				.getItemNames(BusinessDictionaryConstants.CertType);
		currSignList = loanPosBusinessCodeBiz
				.getItemNames(BusinessDictionaryConstants.Currency);
		returnKindList = loanPosBusinessCodeBiz
				.getItemNames(BusinessDictionaryConstants.AccrualType);
		sexList = loanPosBusinessCodeBiz
				.getItemNames(BusinessDictionaryConstants.Sex);
		maritalList = loanPosBusinessCodeBiz
				.getItemNames(BusinessDictionaryConstants.Marital);
		eduList = loanPosBusinessCodeBiz
				.getItemNames(BusinessDictionaryConstants.Education);
		relList = loanPosBusinessCodeBiz
				.getItemNames(BusinessDictionaryConstants.Relationship);
		bankNoList = loanPosBusinessCodeBiz
				.getItemNames(BusinessDictionaryConstants.BankNo);
		implTypeList = loanPosBusinessCodeBiz
				.getItemNames(BusinessDictionaryConstants.ImplType);
		repayMethodList = loanPosBusinessCodeBiz
				.getItemNames(BusinessDictionaryConstants.RepayMethod);
		paymentApplyList = loanPosBusinessCodeBiz
				.getItemNames(BusinessDictionaryConstants.IssueApplyStatus);
		contactFlags = loanPosBusinessCodeBiz
				.getItemNames(BusinessDictionaryConstants.CONTACT_ADDRESS);
		callingTypeList = loanPosBusinessCodeBiz
				.getItemNames(BusinessDictionaryConstants.CallingType);
		HrrbIndustTypeList = loanPosBusinessCodeBiz
				.getItemNames("HrrbIndustType");
		refuseCodeList = loanPosBusinessCodeBiz.getItemNames(BusinessDictionaryConstants.RefuseCode);
		passCodeList = loanPosBusinessCodeBiz.getItemNames(BusinessDictionaryConstants.PassCode);
		
		regionRiskTypeList = loanPosBusinessCodeBiz.getItemNames(BusinessDictionaryConstants.RegionRiskType);
		channelRiskTypeList = loanPosBusinessCodeBiz.getItemNames(BusinessDictionaryConstants.ChannelRiskType);
		productNoList = loanPosBusinessCodeBiz.getItemNames(BusinessDictionaryConstants.ProductNo);
		yesNoList = loanPosBusinessCodeBiz.getItemNames(BusinessDictionaryConstants.YesNo);
		
		occurTypes = loanPosBusinessCodeBiz.getItemNames(BusinessDictionaryConstants.OccurType);
//		Map<String, Object> queryPoolMap = Maps.newHashMap();

//		Map<String, Object> blank = Maps.newHashMap();
//		blank.put("itemNo", "");
//		blank.put("itemName", "");
//		refuseCodeList.add(0, blank);
//		passCodeList.add(0, blank);
	}

	@RequestMapping("/roleOneNav")
	public ModelAndView roleOneNav() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(CreditApplyConstants.NAVIGATION);
		return mav;
	}

	@RequestMapping("/queryBlacklistNav")
	public ModelAndView queryBlacklistNav() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(BlacklistConstants.QUERY_BLACKLIST_NAV);
		return mav;
	}

	@RequestMapping("/queryCustomerNav")
	public ModelAndView queryCustomerNav() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(CustomerInfoConstants.QUERY_CUSTOMER_NAV);
		return mav;
	}

	@RequestMapping("/queryCustRelaNav")
	public ModelAndView queryCustRelaNav() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(CustomerInfoConstants.QUERY_CUST_RELA_NAV);
		return mav;
	}

	@RequestMapping("/queryCustMerchantNav")
	public ModelAndView queryCustMerchantNav() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(CustomerInfoConstants.QUERY_CUST_MERCHANT_NAV);
		return mav;
	}

	@RequestMapping("/queryCustBankNav")
	public ModelAndView queryCustBankNav() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(CustomerInfoConstants.QUERY_CUST_BANK_NAV);
		return mav;
	}

	@RequestMapping("/queryContractManagementNav")
	public ModelAndView queryContractManagementNav() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("queryContractManagementNavi");
		return mav;
	}

	@RequestMapping("/openCreditApplyAndCustomer")
	public ModelAndView openCreditApplyAndCustomer(
			@RequestParam(value = "loanId", required = false) String loanId,
			@RequestParam(value = "direct2Path", required = false) String direct2Path) {
		ModelAndView mav = new ModelAndView();
		if (loanId != null && loanId.trim().length() > 0) { // load detail when
															// loanId is null
			// 查询出
			List<Map<String, Object>> resList = loanPosCreditApplyBackStageBiz
					.getCreditApplyDetail(loanId);
			if (resList != null && resList.size() > 0) {
				// --------追加地址中的市和县一级代码名称,0业务申请信息;1客户信息;3商户信息;4银行账户信息;2亲属信息
				Object residentCity = resList.get(1).get("residentCity");
				if (residentCity != null)
					resList.get(1).put("residentCityName",
							getItemValue(cities, residentCity.toString()));
				Object residentDivision = resList.get(1)
						.get("residentDivision");
				if (residentDivision != null)
					resList.get(1)
							.put("residentDivisionName",
									getItemValue(divisions,
											residentDivision.toString()));

				Object posCustCity = resList.get(3).get("posCustCity");
				if (posCustCity != null)
					resList.get(3).put("posCustCityName",
							getItemValue(cities, posCustCity.toString()));
				Object posCustDivision = resList.get(3).get("operAddrCode");
				if (posCustDivision != null)
					resList.get(3)
							.put("posCustDivisionName",
									getItemValue(divisions,
											posCustDivision.toString()));
				// --------商户行业分类代码转换
				Object industryTypeId = resList.get(3).get("industryTypeId");
				if (industryTypeId != null) {
					String itemName = loanPosBusinessCodeBiz.getItemNameByNo(
							BusinessDictionaryConstants.IndustryType,
							(String) industryTypeId);
					if (itemName != null && itemName.trim().length() > 0) {
						resList.get(3).put("industryTypeName",
								"[" + industryTypeId + "] " + itemName);
					}
				}
				logger.info(JSON.toJSONString(resList));
				Object applyDetail = JSON.toJSON(resList);
				mav.addObject("applyDetail", applyDetail);
			}
		} else {
			// generate loadId
			loanId = IdUtil.getId("LO");
		}
		mav.addObject("loanId", loanId);
		mav.setViewName(direct2Path);
		return mav;
	}

	@RequestMapping("/queryContractManagementNav1")
	public ModelAndView queryContractManagementNav1(
			@RequestParam(value = "approveStatus", required = false) String approveStatus) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("loanPurposeList", loanPurposeList);
		mav.addObject("approveStatus", approveStatus);
		mav.addObject("bizChannel", channelDictionary);
		mav.addObject("implTypeList", implTypeList);
		mav.addObject("currSignList", currSignList);
		mav.addObject("returnKindList", returnKindList);
		mav.addObject("paperList", paperList);
		mav.addObject("sexList", sexList);
		mav.addObject("eduList", eduList);
		mav.addObject("maritalList", maritalList);
		mav.addObject("province", provinceList);
		mav.addObject("cities", cities);
		mav.addObject("divisions", divisions);
		mav.addObject("repayMethodList", repayMethodList);
		mav.setViewName("contract/unApprovedContractNavi");
		return mav;
	}

	@RequestMapping("/queryContractManagementNav2")
	public ModelAndView queryContractManagementNav2(
			@RequestParam(value = "approveStatus", required = false) String approveStatus) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("approveStatus", approveStatus);
		mav.addObject("bizChannel", channelDictionary);
		mav.addObject("implTypeList", implTypeList);
		mav.addObject("currSignList", currSignList);
		mav.addObject("returnKindList", returnKindList);
		mav.addObject("paperList", paperList);
		mav.addObject("sexList", sexList);
		mav.addObject("eduList", eduList);
		mav.addObject("maritalList", maritalList);
		mav.addObject("province", provinceList);
		mav.addObject("cities", cities);
		mav.addObject("divisions", divisions);
		mav.addObject("repayMethodList", repayMethodList);
		mav.setViewName("contract/approvedContractNavi");
		return mav;
	}

	@RequestMapping("/queryContractManagementNav3")
	public ModelAndView queryContractManagementNav3(
			@RequestParam(value = "approveStatus", required = false) String approveStatus) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("approveStatus", approveStatus);
		mav.addObject("bizChannel", channelDictionary);
		mav.addObject("implTypeList", implTypeList);
		mav.addObject("currSignList", currSignList);
		mav.addObject("returnKindList", returnKindList);
		mav.addObject("paperList", paperList);
		mav.addObject("sexList", sexList);
		mav.addObject("eduList", eduList);
		mav.addObject("maritalList", maritalList);
		mav.addObject("province", provinceList);
		mav.addObject("cities", cities);
		mav.addObject("divisions", divisions);
		mav.addObject("repayMethodList", repayMethodList);
		mav.setViewName("contract/refusedContractNavi");
		return mav;
	}

	@RequestMapping("/queryContractManagementNav4")
	public ModelAndView queryContractManagementNav4(
			@RequestParam(value = "approveStatus", required = false) String approveStatus) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("approveStatus", approveStatus);
		mav.addObject("bizChannel", channelDictionary);
		mav.addObject("implTypeList", implTypeList);
		mav.addObject("currSignList", currSignList);
		mav.addObject("returnKindList", returnKindList);
		mav.addObject("paperList", paperList);
		mav.addObject("sexList", sexList);
		mav.addObject("eduList", eduList);
		mav.addObject("maritalList", maritalList);
		mav.addObject("province", provinceList);
		mav.addObject("cities", cities);
		mav.addObject("divisions", divisions);
		mav.addObject("repayMethodList", repayMethodList);
		mav.setViewName("contract/invalidateContractNavi");
		return mav;
	}

	/**
	 * 授信台账管理
	 * 
	 * @param bizStatus
	 * @return
	 */
	@RequestMapping("/queryFacilityNav")
	public ModelAndView queryReceiptInfo1(
			@RequestParam(value = "bizStatus", required = true) String bizStatus) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("bizStatus", bizStatus);
		mav.setViewName("payback/payback/queryFacilityNav");
		return mav;
	}

	// @RequestMapping("/clearedBusiness")
	// public ModelAndView queryReceiptInfo2(@RequestParam(value="clearStatus",
	// required = false)String clearStatus){
	// ModelAndView mav = new ModelAndView();
	// mav.addObject("clearStatus",clearStatus);
	// mav.addObject("bizChannel", channelDictionary);
	// mav.addObject("implTypeList", implTypeList);
	// mav.addObject("currSignList", currSignList);
	// mav.addObject("returnKindList", returnKindList);
	// mav.addObject("paperList", paperList);
	// mav.addObject("sexList", sexList);
	// mav.addObject("eduList", eduList);
	// mav.addObject("maritalList", maritalList);
	// mav.addObject("province", provinceList);
	// mav.addObject("cities", cities);
	// mav.addObject("divisions", divisions);
	// mav.addObject("repayMethodList", repayMethodList);
	// mav.setViewName("payback/clearedReceipt");
	// return mav;
	// }

	/**
	 * 还款申请管理 
	 * 
	 * @param paybackStatus
	 * @return
	 */
	@RequestMapping("/paybackApplyNav")
	public ModelAndView paybackApplyNav(@RequestParam(value = "paybackStatus", required = false) String paybackStatus) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("paybackStatus", paybackStatus);
		mav.addObject("bizChannel", channelDictionary);
		mav.addObject("implTypeList", implTypeList);
		mav.addObject("currSignList", currSignList);
		mav.addObject("returnKindList", returnKindList);
		mav.addObject("paperList", paperList);
		mav.addObject("sexList", sexList);
		mav.addObject("eduList", eduList);
		mav.addObject("maritalList", maritalList);
		mav.addObject("province", provinceList);
		mav.addObject("cities", cities);
		mav.addObject("divisions", divisions);
		mav.addObject("repayMethodList", repayMethodList);
		mav.addObject("loanPurposeList", loanPurposeList);
		mav.setViewName(PaybackApplyConstants.QUERY_PAYBACK_APPLY_PAGE);
		return mav;
	}

	// 未匹配还款进项
	@RequestMapping("/notMatchedPaybackImport")
	public ModelAndView notMatchedPaybackImport(
			@RequestParam(value = "viewStatus", required = false) String viewStatus) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("bizChannel", channelDictionary);
		mav.addObject("implTypeList", implTypeList);
		mav.addObject("currSignList", currSignList);
		mav.addObject("returnKindList", returnKindList);
		mav.addObject("paperList", paperList);
		mav.addObject("sexList", sexList);
		mav.addObject("eduList", eduList);
		mav.addObject("maritalList", maritalList);
		mav.addObject("province", provinceList);
		mav.addObject("cities", cities);
		mav.addObject("divisions", divisions);
		mav.addObject("repayMethodList", repayMethodList);
		mav.addObject("loanPurposeList", loanPurposeList);
		mav.addObject("viewStatus", viewStatus); // add by Lin,Zhaolin
		mav.setViewName("payback/paybackImport/notMatchedPaybackImport");
		return mav;
	}

	// 已匹配还款进项
	@RequestMapping("/matchedPaybackImport")
	public ModelAndView matchedPaybackImport() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("bizChannel", channelDictionary);
		mav.addObject("implTypeList", implTypeList);
		mav.addObject("currSignList", currSignList);
		mav.addObject("returnKindList", returnKindList);
		mav.addObject("paperList", paperList);
		mav.addObject("sexList", sexList);
		mav.addObject("eduList", eduList);
		mav.addObject("maritalList", maritalList);
		mav.addObject("province", provinceList);
		mav.addObject("cities", cities);
		mav.addObject("divisions", divisions);
		mav.addObject("repayMethodList", repayMethodList);
		mav.addObject("loanPurposeList", loanPurposeList);
		mav.setViewName("payback/paybackImport/matchedPaybackImport");
		return mav;
	}

	// 待确认存疑流水
	@RequestMapping("/notConfirmedQuestionRunning")
	public ModelAndView notConfirmedQuestion(
			@RequestParam(value = "runningStatus", required = false) String runningStatus) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("runningStatus", runningStatus);
		mav.addObject("bizChannel", channelDictionary);
		mav.addObject("implTypeList", implTypeList);
		mav.addObject("currSignList", currSignList);
		mav.addObject("returnKindList", returnKindList);
		mav.addObject("paperList", paperList);
		mav.addObject("sexList", sexList);
		mav.addObject("eduList", eduList);
		mav.addObject("maritalList", maritalList);
		mav.addObject("province", provinceList);
		mav.addObject("cities", cities);
		mav.addObject("divisions", divisions);
		mav.addObject("repayMethodList", repayMethodList);
		mav.addObject("loanPurposeList", loanPurposeList);
		mav.setViewName("payback/paybackRunning/notConfirmedQuestion");
		return mav;
	}

	// 已匹配还款流水
	@RequestMapping("/matchedPaybackRunning")
	public ModelAndView matchedPaybackImport(
			@RequestParam(value = "runningStatus", required = false) String runningStatus) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("runningStatus", runningStatus);
		mav.addObject("bizChannel", channelDictionary);
		mav.addObject("implTypeList", implTypeList);
		mav.addObject("currSignList", currSignList);
		mav.addObject("returnKindList", returnKindList);
		mav.addObject("paperList", paperList);
		mav.addObject("sexList", sexList);
		mav.addObject("eduList", eduList);
		mav.addObject("maritalList", maritalList);
		mav.addObject("province", provinceList);
		mav.addObject("cities", cities);
		mav.addObject("divisions", divisions);
		mav.addObject("repayMethodList", repayMethodList);
		mav.addObject("loanPurposeList", loanPurposeList);
		mav.setViewName("payback/paybackRunning/matchedQuestion");
		return mav;
	}

	// 确认待冲销流水
	@RequestMapping("/confirmToOffsetRunning")
	public ModelAndView confirmToOffset(
			@RequestParam(value = "runningStatus", required = false) String runningStatus) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("runningStatus", runningStatus);
		mav.addObject("bizChannel", channelDictionary);
		mav.addObject("implTypeList", implTypeList);
		mav.addObject("currSignList", currSignList);
		mav.addObject("returnKindList", returnKindList);
		mav.addObject("paperList", paperList);
		mav.addObject("sexList", sexList);
		mav.addObject("eduList", eduList);
		mav.addObject("maritalList", maritalList);
		mav.addObject("province", provinceList);
		mav.addObject("cities", cities);
		mav.addObject("divisions", divisions);
		mav.addObject("repayMethodList", repayMethodList);
		mav.addObject("loanPurposeList", loanPurposeList);
		mav.setViewName("payback/paybackRunning/confirmToOffset");
		return mav;
	}

	// 确认已冲销流水
	@RequestMapping("/confirmedOffsetRunning")
	public ModelAndView confirmedOffset(
			@RequestParam(value = "runningStatus", required = false) String runningStatus) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("runningStatus", runningStatus);
		mav.addObject("bizChannel", channelDictionary);
		mav.addObject("implTypeList", implTypeList);
		mav.addObject("currSignList", currSignList);
		mav.addObject("returnKindList", returnKindList);
		mav.addObject("paperList", paperList);
		mav.addObject("sexList", sexList);
		mav.addObject("eduList", eduList);
		mav.addObject("maritalList", maritalList);
		mav.addObject("province", provinceList);
		mav.addObject("cities", cities);
		mav.addObject("divisions", divisions);
		mav.addObject("repayMethodList", repayMethodList);
		mav.addObject("loanPurposeList", loanPurposeList);
		mav.setViewName("payback/paybackRunning/confirmedOffset");
		return mav;
	}

	@RequestMapping("/queryCreditApplyForReview")
	public ModelAndView queryCreditApplyForReview() {
		ModelAndView mav = new ModelAndView();
		List<Map<String, String>> dateList = DateUtil
				.getOverNumMonths(CreditApplyConstants.monthNum);
		mav.addObject("overMonths", dateList);
		mav.addObject("province", provinceList);
		mav.addObject("bizChannel", channelDictionary);
		mav.addObject("paperList", paperList);
		mav.addObject("returnKindList", returnKindList);
		mav.addObject("currSignList", currSignList);
		mav.addObject("sexList", sexList);
		mav.addObject("maritalList", maritalList);
		mav.addObject("eduList", eduList);
		mav.addObject("relList", relList);
		mav.addObject("cities", cities);
		mav.addObject("divisions", divisions);
		mav.addObject("bankNoList", bankNoList);
		mav.addObject("implTypeList", implTypeList);
		mav.addObject("repayMethodList", repayMethodList);
		// 渠道列表（带空值）
		List<Map<String, Object>> channelDictionaryWithBlank = Lists
				.newArrayList();
		channelDictionaryWithBlank.addAll(channelDictionary);
		Map<String, Object> blankChannel = Maps.newHashMap();
		blankChannel.put("itemNo", "");
		blankChannel.put("itemName", "全部");
		channelDictionaryWithBlank.add(0, blankChannel);
		mav.addObject("bizChannelWithBlank", channelDictionaryWithBlank);
		// 发生方式列表（带空值）
		List<Map<String, Object>> occurTypeDictionary = loanPosBusinessCodeBiz
				.getItemNames(BusinessDictionaryConstants.OccurType);
		Map<String, Object> blankOccurType = Maps.newHashMap();
		blankOccurType.put("itemNo", "");
		blankOccurType.put("itemName", "全部");
		occurTypeDictionary.add(0, blankOccurType);
		mav.addObject("occurTypelWithBlank", occurTypeDictionary);

		mav.setViewName("review/queryCreditApplyForReviewNavi");
		return mav;
	}

	/**
	 * 导航至协议申请页面
	 * 
	 * @param applyStatus
	 * @return
	 */
	@RequestMapping("/queryCreditApplyNav")
	public ModelAndView queryCreditApplyNav(
			@RequestParam(value = "applyStatus", required = false) String applyStatus,
			@RequestParam(value = "isApplyStatus", required = false) String isApplyStatus,
			@RequestParam(value = "viewMode", required = false) String viewMode) {
		ModelAndView mav = new ModelAndView();
		List<Map<String, String>> dateList = DateUtil
				.getOverNumMonths(CreditApplyConstants.monthNum);
		mav.addObject("overMonths", dateList);
		mav.addObject("province", provinceList);
		mav.addObject("bizChannel", channelDictionary);
		mav.addObject("paperList", paperList);
		mav.addObject("returnKindList", returnKindList);
		mav.addObject("currSignList", currSignList);
		mav.addObject("sexList", sexList);
		mav.addObject("maritalList", maritalList);
		mav.addObject("eduList", eduList);
		mav.addObject("relList", relList);
		mav.addObject("cities", cities);
		mav.addObject("divisions", divisions);
		mav.addObject("bankNoList", bankNoList);
		mav.addObject("implTypeList", implTypeList);
		mav.addObject("repayMethodList", repayMethodList);
		if (!StringUtil.isEmpty(applyStatus)) {
			mav.addObject(CreditApplyConstants.APPLY_STATUS, applyStatus);
		}
		if (!StringUtil.isEmpty(isApplyStatus)) {
			mav.addObject("isApplyStatus", isApplyStatus);
		}
		if (!StringUtil.isEmpty(viewMode)) {
			mav.addObject("viewMode", viewMode);
		}
		mav.setViewName(CreditApplyConstants.QUERY_CREDIT_APPLY_NAV);
		return mav;
	}

	/**
	 * 导航到客服外呼页面
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping("/queryCallingTaskForReview")
	public ModelAndView queryCallingTaskForReview() {
		ModelAndView mav = new ModelAndView();

		List<Map<String, String>> dateList = DateUtil
				.getOverNumMonths(CreditApplyConstants.monthNum);
		mav.addObject("overMonths", dateList);
		mav.addObject("province", provinceList);
		mav.addObject("bizChannel", channelDictionary);
		mav.addObject("paperList", paperList);
		mav.addObject("returnKindList", returnKindList);
		mav.addObject("currSignList", currSignList);
		mav.addObject("sexList", sexList);
		mav.addObject("maritalList", maritalList);
		mav.addObject("eduList", eduList);
		mav.addObject("relList", relList);
		mav.addObject("cities", cities);
		mav.addObject("divisions", divisions);
		mav.addObject("bankNoList", bankNoList);
		mav.addObject("implTypeList", implTypeList);
		mav.addObject("repayMethodList", repayMethodList);
		mav.addObject("callingTypeList", callingTypeList);
		mav.setViewName("outBound/queryCallingTaskForReview");
		return mav;
	}

	/**
	 * 导航至协议管理界面
	 * 
	 * @param loanId
	 * @return
	 */
	@RequestMapping("/queryAgreeMentNavi")
	public ModelAndView queryAgreeMentNavi(
			@RequestParam(value = "agreementStatus") String agreementStatus) {
		ModelAndView mav = new ModelAndView();
		if (!StringUtil.isEmpty(agreementStatus)) {
			mav.addObject(CreditApplyConstants.AGREEMENT_STATUS,
					agreementStatus);
		}
		mav.addObject("bizChannel", channelDictionary);
		mav.addObject("paperList", paperList);
		mav.addObject("returnKindList", returnKindList);
		mav.addObject("agreementStatus", agreementStatus);
		mav.addObject("maritalList", maritalList);
		mav.addObject("sexList", sexList);
		mav.addObject("eduList", eduList);
		mav.addObject("currSignList", currSignList);
		mav.addObject("loanPurposeList", loanPurposeList);
		mav.addObject("implTypeList", implTypeList);
		mav.addObject("province", provinceList);
		mav.addObject("cities", cities);
		mav.addObject("divisions", divisions);
		mav.addObject("repayMethodList", repayMethodList);
		mav.addObject("bankNoList", bankNoList);
		mav.setViewName(CreditApplyConstants.QUERY_AGREEMENT_NAV);
		return mav;
	}

	/**
	 * 待签约协议
	 * 
	 * @param approveStatus
	 * @param direct2Path
	 * @param out
	 * @return
	 */
	@RequestMapping("/openSignReply")
	public ModelAndView openSignReply(
			@RequestParam(value = "approveStatus", required = false) String approveStatus,
			@RequestParam(value = "direct2Path", required = false) String direct2Path,
			PrintWriter out) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("approveStatus", approveStatus);
		mav.setViewName(direct2Path);
		;
		return mav;
	}

	/**
	 * 协议信息
	 * 
	 * @param approveId
	 * @param direct2Path
	 * @return
	 */
	@RequestMapping("/openContractWindow")
	public ModelAndView openContractWindow(
			@RequestParam(value = "approveId", required = false) String approveId,
			@RequestParam(value = "direct2Path", required = false) String direct2Path) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("bizChannel", channelDictionary);
		mav.addObject("paperList", paperList);
		mav.addObject("returnKindList", returnKindList);
		mav.addObject("maritalList", maritalList);
		mav.addObject("sexList", sexList);
		mav.addObject("eduList", eduList);
		mav.addObject("currSignList", currSignList);
		mav.addObject("loanPurposeList", loanPurposeList);
		mav.addObject("implTypeList", implTypeList);
		mav.addObject("province", provinceList);
		mav.addObject("cities", cities);
		mav.addObject("divisions", divisions);
		mav.addObject("repayMethodList", repayMethodList);
		TApproveResult approveResult = loanPosContractManagementBiz
				.getApproveInfo(approveId);
		Date beginDate = new Date();
		mav.addObject("beginDate", beginDate);
		
		TCreditApply apply = loanPosCreditApplyService.queryCreditApply(approveResult.getLoanId());
		mav.addObject("apply", apply);
		if(apply.getInChannelKind().equals("04")){	//对于自建渠道的业务，暂定协议期限为3年
			approveResult.setApproveTerm("36");
		}
		if(apply.getRepayMethod().equals("01")){	//主动还款展示还款账号
			TCfgChannelAccount repayAccount = repaymentApplyService.getChannelAccount(apply.getChannel());
			mav.addObject("repayAccount", repayAccount);
		}
		
		Date endDate;
		try {
			//
			endDate = DateUtil.getDatePattern3(DateUtil.getRelativeDate(beginDate, 0, Integer.parseInt(approveResult.getApproveTerm()), -1));
			mav.addObject("endDate", endDate);
		} catch (Exception e) {
			logger.error("日期转换错误", e);
		}
		TCreditApply creditApply = loanPosCreditApplyBackStageBiz
				.queryCreditApplyDetail(approveResult.getLoanId());
		mav.addObject("approveResult", approveResult);
		mav.addObject("inChannelKind", creditApply.getInChannelKind());
		String accountOpenBank = loanPosBusinessCodeBiz.getItemNameByNo(
				"BankNo", approveResult.getAccountOpenBank());
		mav.addObject("accountOpenBank", accountOpenBank);
		
		Map<String,Object> customer = loanPosCustomerService.selectOneCustomer(creditApply.getCustId());
		mav.addObject("customer", customer);
		
		mav.setViewName(direct2Path);
		
		return mav;
	}

	@RequestMapping("/openApprovalWin")
	public ModelAndView openApprovalWin(
			@RequestParam(value = "approveId", required = false) String approveId,
			@RequestParam(value = "direct2Path", required = false) String direct2Path) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("bankNoList", bankNoList);
		/* 批复对象 */
		TApproveResult approveResult = loanPosContractManagementBiz
				.getApproveInfo(approveId);
		if (approveResult.getApproveMoneyKind() != null) {
			approveResult.setApproveMoneyKind(getItemValue(currSignList,
					approveResult.getApproveMoneyKind()).toString());
		}
		if (approveResult.getPaybackMethod() != null) {
			approveResult.setPaybackMethod(loanPosBusinessCodeBiz
					.getItemNameByNo("AccrualType",
							approveResult.getPaybackMethod()));
		}

		mav.addObject("approval", approveResult);

		mav.setViewName(direct2Path);

		return mav;
	}

	@RequestMapping("/openAgreementDetail")
	public ModelAndView openAgreementView(
			@RequestParam(value = "contractNo", required = false) String contractNo) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("bizChannel", channelDictionary);
		mav.addObject("paperList", paperList);
		mav.addObject("returnKindList", returnKindList);
		mav.addObject("maritalList", maritalList);
		mav.addObject("sexList", sexList);
		mav.addObject("eduList", eduList);
		mav.addObject("currSignList", currSignList);
		mav.addObject("loanPurposeList", loanPurposeList);
		mav.addObject("implTypeList", implTypeList);
		mav.addObject("province", provinceList);
		mav.addObject("cities", cities);
		mav.addObject("divisions", divisions);
		mav.addObject("bankNoList", bankNoList);
		mav.addObject("repayMethodList", repayMethodList);
		mav.addObject("contactFlags", contactFlags);
		mav.addObject("HrrbIndustTypeList", HrrbIndustTypeList);

		/* 协议对象 */
		TContractManagement contract = loanPosContractManagementBiz
				.getContractForDisplay(contractNo);
		Object jsonContract = JSON.toJSON(contract);
		mav.addObject("contract", jsonContract);

		/* 批复对象 */
		TApproveResult approval = loanPosContractManagementBiz
				.getApproveInfo(contract.getApproveId());
		Object jsonApproval = JSON.toJSON(approval);
		mav.addObject("approveInfo", jsonApproval);

		Object applyDetail = null;
		if (approval == null) {
			List<Map<String, Object>> applyList = Lists.newArrayList();
			Map<String, Object> a1 = Maps.newHashMap();
			applyList.add(a1);
			applyList.add(a1);
			applyList.add(a1);
			applyList.add(a1);
			applyDetail = JSON.toJSON(applyList);

		} else {
			// 查询出申请详情中的各部分信息0业务申请信息;1客户信息;3商户信息;4银行账户信息;2亲属信息
			List<Map<String, Object>> applyList = loanPosCreditApplyBackStageBiz
					.getCreditApplyDetail(approval.getLoanId());
			if (applyList != null && applyList.size() > 0) {
				// --------追加地址中的市和县一级代码名称,0业务申请信息;1客户信息;3商户信息;4银行账户信息;2亲属信息
				Object residentCity = applyList.get(1).get("residentCity");
				if (residentCity != null)
					applyList.get(1).put("residentCityName",
							getItemValue(cities, residentCity.toString()));
				Object residentDivision = applyList.get(1).get(
						"residentDivision");
				if (residentDivision != null)
					applyList.get(1)
							.put("residentDivisionName",
									getItemValue(divisions,
											residentDivision.toString()));

				Object posCustCity = applyList.get(3).get("posCustCity");
				if (posCustCity != null)
					applyList.get(3).put("posCustCityName",
							getItemValue(cities, posCustCity.toString()));
				Object posCustDivision = applyList.get(3).get("operAddrCode");
				if (posCustDivision != null)
					applyList.get(3)
							.put("posCustDivisionName",
									getItemValue(divisions,
											posCustDivision.toString()));
				// --------商户行业分类代码转换
				Object industryTypeId = applyList.get(3).get("industryTypeId");
				if (industryTypeId != null) {
					String itemName = loanPosBusinessCodeBiz.getItemNameByNo(
							BusinessDictionaryConstants.IndustryType,
							(String) industryTypeId);
					if (itemName != null && itemName.trim().length() > 0) {
						applyList.get(3).put("industryTypeName",
								"[" + industryTypeId + "] " + itemName);
					}
				}
			}

			applyDetail = JSON.toJSON(applyList);
		}

		mav.addObject("applyDetail", applyDetail);

		mav.setViewName("agreementMrg/agreementDetail/detailAgreementMain");
		;
		return mav;
	}

	/**
	 * 用款申请
	 * 
	 * @param paymentStatus
	 * @return
	 */
	@RequestMapping("/queryPaymentApplyNav")
	public ModelAndView queryPaymentApplyNav(
			@RequestParam(value = "paymentStatus") String paymentStatus,
			@RequestParam(value = "excuteStatus", required = false) String excuteStatus) {
		ModelAndView mav = new ModelAndView();
		if (!StringUtil.isEmpty(paymentStatus)) {
			mav.addObject(PaymentApplyConstants.PAYMENT_APPLY_STATUS,
					paymentStatus);
		}
		if (!StringUtil.isEmpty(excuteStatus)) {
			mav.addObject("excuteStatus", excuteStatus);
		}
		mav.setViewName(PaymentApplyConstants.QUERY_PAYMENT_APPLY_PAGE);
		return mav;
	}

	@RequestMapping("/posSerialNav")
	public ModelAndView posSerialNav(
			@RequestParam(value = "loanId", required = false) String loanId,
			@RequestParam(value = "container", required = false) String container) {
		ModelAndView mav = new ModelAndView();
		mav.addObject(CreditApplyConstants.POS_SELECT_CHANNEL,
				channelDictionary);
		mav.addObject(CreditApplyConstants.POS_SERIAL_TYPE, posTypeDictionary);
		mav.addObject(CreditApplyConstants.LOAN_ID, loanId);
		mav.addObject("container", container); // 通过container指定刷新datagrid
		mav.setViewName(CreditApplyConstants.POS_SERIAL_NAV);
		return mav;
	}

	@RequestMapping("/insertCreditImageDataNav")
	public ModelAndView insertCreditImageDataNav(
			@RequestParam(value = "loanId", required = false) String loanId,
			@RequestParam(value = "currSign", required = false) String currSign) {
		ModelAndView mav = new ModelAndView();
		mav.addObject(CreditApplyConstants.LOAN_ID, loanId);
		mav.addObject(CreditApplyConstants.currSign, currSign);
		mav.setViewName(CreditApplyConstants.LOAN_POS_INSERT_IMAGE_DATA_NAV);
		return mav;
	}
    
   @RequestMapping("/insertCreditImageDataFromServerNav")
    public ModelAndView insertCreditImageDataFromServerNav(@RequestParam(value="loanId", required = false)String loanId,@RequestParam(value="currSign",required = false) String currSign){
        ModelAndView mav = new ModelAndView();
        List<String> folderNames = new ArrayList<String>();
//        String filePath = PropertyUtils.getProperty("filePath", "config/properties/systemInfo.properties");
        String filePath = SysConfigFactory.getInstance().getService("uploadService").getPropertyValue("syncFolder");
        File contentDirectory = new File(filePath);
        File[] folders = contentDirectory.listFiles();
        if(folders !=null){
        for(int i=0;i<folders.length;i++){
            if(folders[i].isDirectory()){
                folderNames.add(folders[i].getName());
            }
        }
        }
        mav.addObject("folderNames", folderNames);
        mav.addObject(CreditApplyConstants.LOAN_ID, loanId);
        mav.addObject(CreditApplyConstants.currSign, currSign);
        mav.setViewName(CreditApplyConstants.LOAN_POS_INSERT_IMAGE_DATA_NAV_TMP);
        return mav;
    }
	
	@RequestMapping("/insertCreditImageDataNavForReview")
	public ModelAndView insertCreditImageDataNavForReview(
			@RequestParam(value = "loanId", required = false) String loanId) {
		ModelAndView mav = new ModelAndView();
		mav.addObject(CreditApplyConstants.LOAN_ID, loanId);
		mav.setViewName(CreditApplyConstants.LOAN_POS_INSERT_IMAGE_DATA_NAV_REVIEW);
		return mav;
	}

	@RequestMapping("/loanPosImageDataNav")
	public ModelAndView loanPosImageDataNav(
			@RequestParam(value = "loanId", required = false) String loanId,
			@RequestParam(value = "custId", required = false) String custId) {
		ModelAndView mav = new ModelAndView();
		mav.addObject(CreditApplyConstants.LOAN_POS_IMAGE_DATA,
				imageDataViewBiz.getImageDataNames(loanId, custId));
		mav.addObject(CreditApplyConstants.LOAN_ID, loanId);
		mav.addObject(CreditApplyConstants.CUST_ID, custId);
		mav.addObject("imageUrl", imageUrl);
		mav.setViewName(CreditApplyConstants.LOAN_POS_IMAGE_DATA_NAV);
		return mav;
	}

	@RequestMapping("/loanPosImageDataNavBigger")
	public ModelAndView loanPosImageDataNavBigger(
			@RequestParam(value = "loanId", required = false) String loanId,
			@RequestParam(value = "custId", required = false) String custId) {
		ModelAndView mav = new ModelAndView();
		mav.addObject(CreditApplyConstants.LOAN_POS_IMAGE_DATA,
				imageDataViewBiz.getImageDataNames(loanId, custId));
		mav.addObject(CreditApplyConstants.LOAN_ID, loanId);
		mav.addObject(CreditApplyConstants.CUST_ID, custId);
		mav.addObject("imageUrl", imageUrl);
		mav.setViewName(CreditApplyConstants.LOAN_POS_IMAGE_DATA_NAV_BIGGER);
		return mav;
	}

	@RequestMapping("/loanPOSImageFiles")
	public ModelAndView loanPOSImageFiles(
			@RequestParam(value = "loanId", required = true) String loanId,
			@RequestParam(value = "custId", required = false) String custId,
			@RequestParam(value = "directTo", required = false) String directTo) {
		ModelAndView mav = new ModelAndView();
		/* 输出结果 */
		List<Map<String, Object>> listImgs = new ArrayList<Map<String, Object>>();
		try {
			
			/* 获取影像文件 */
			int id = 0;
			List<Map<String, Object>> imgFiles = imageDataViewBiz.getImageDataNames(loanId, custId);
			if (imgFiles != null && imgFiles.size() > 0) {
				//通过SortedMap对文件进行排序
				Map<String,Integer> sortVar = Maps.newTreeMap();
				for(int i=0; i<imgFiles.size(); i++){
					Map<String, Object> fileS = imgFiles.get(i);
					sortVar.put((String)fileS.get("fileName"), Integer.valueOf(i));
				}
				
				for(Map.Entry<String, Integer> en : sortVar.entrySet()){
					Map<String, Object> file = imgFiles.get(en.getValue());		//排序后的文件idx
					
					++id;
					Map<String, Object> img = Maps.newHashMap();
					img.put("image", imageUrl + "imagedatawork/" + loanId + "/"+ file.get("fileName"));
					//img.put("image", file.get("fileName"));
					img.put("title", file.get("fileDesc"));
					// img.put("thumb", value);
					img.put("url", "javascript:imgRotation(\"#img" + id+ "\");");
					img.put("id", "img" + id);
					listImgs.add(img);
				}
			}
		} catch (Exception e) {
			logger.warn("获取申请[" + loanId + "]的影像文件失败!", e);
		}

		mav.addObject("listImgFiles", JSON.toJSON(listImgs));

		mav.setViewName((directTo != null) ? directTo
				: "creditApply/ImageFilesView");
		return mav;
	}

	/*
	 * @RequestMapping("/testNav") public ModelAndView testNav(){ ModelAndView
	 * mav = new ModelAndView(); mav.addObject("testPro", provinceList);
	 * mav.setViewName("creditApplyz/testPage"); return mav; }
	 */

	/**
	 * 上传pos流水文件
	 */
	@RequestMapping("/uploadPosSerial")
	public ModelAndView uploadPosSerial(
			@RequestParam(value = "uploadFile", required = false) MultipartFile multipartFile,
			@RequestParam(value = "posChannel", required = false) String posChannel,
			@RequestParam(value = "posType", required = false) String posType,
			@RequestParam(value = "container", required = false) String container,
			@RequestParam(value = "loanId", required = false) String loanId)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(CreditApplyConstants.POS_SERIAL_NAV);
		if (multipartFile.isEmpty()) {
			mav.addObject("result", "文件不能为空");
			return mav;
		}
		String fileName = multipartFile.getOriginalFilename();
		File file = new File(fileName);
		multipartFile.transferTo(file);
		// 判断是否pos流水已存在
		Long count = loanPosCreditApplyBackStageBiz.countPosSerials(loanId);
		if (count > 0) {
			// 如果流水已存在,那么删除
			loanPosCreditApplyBackStageBiz.deletePosSerial(loanId);
		}
		boolean bol = loanPosCreditApplyBackStageBiz.uploadPosSerial(file,
				posChannel, posType, loanId);
		mav.addObject(CreditApplyConstants.POS_SELECT_CHANNEL,
				channelDictionary);
		mav.addObject(CreditApplyConstants.POS_SERIAL_TYPE, posTypeDictionary);
		mav.addObject(CreditApplyConstants.LOAN_ID, loanId);
		mav.addObject(CreditApplyConstants.posChannel, posChannel);
		mav.addObject("posType", posType);
		mav.addObject("container", container);
		if (file.exists()) {
			file.delete();
		}
		if (bol) {
			mav.addObject("result", "上传成功");
		} else {
			mav.addObject("result", "上传失败,您输入的excel格式不正确");
		}
		return mav;
	}

	/**
	 * 将上传的文件打包ftp到文档管理系统
	 * 
	 * @param loanId
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping("/uploadAndArchive")
	public ModelAndView uploadAndArchive(
			@RequestParam(value = "loanId", required = false) String loanId,
			HttpServletRequest request, PrintWriter out) {

		JSONObject aaJson = new JSONObject();

		logger.debug("第二步:影像件打包");
		/* 临时存放目录 */
		String basePath = System.getProperty("java.io.tmpdir") + "imag-upload/"
				+ loanId;
		String fileDir = basePath + "/" + "resize";
		String destFileName = basePath + "/" + loanId + "_"
				+ DateUtil.getNowTime("HHmmss") + ZipUtils.EXT;

		File sourceFile = new File(fileDir);
		File destFile = new File(destFileName);
		try {
			Long step2BeginTime = System.currentTimeMillis();
			// 影像资料打包
			ZipUtils.compress(sourceFile, destFile);
			Long step2EndTime = System.currentTimeMillis();
			logger.debug("影像件打包完成。耗时" + (step2EndTime - step2BeginTime) + "毫秒");
		} catch (Exception e) {
			logger.error("影像件打包异常", e);
			aaJson.put("resultCode", "999");
			aaJson.put("resultMsg", "影像件资料打包失败！");
			out.write(aaJson.toJSONString());
			return null;
		}

		/* 上传文档 */
		 InputStream is = null;
		try {
			is = new FileInputStream(destFile);
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        byte[] b= new byte[2048];
	        while((is.read(b)) != -1){
	            bos.write(b);
	        }
	        byte[] bytes = bos.toByteArray();
	        
	        bos.flush();
	        bos.close();
	        is.close();
			
			if (u2fs.uploadFile(destFile.getName(), bytes)) {
				/* 上传成功后，提示归档 */
				Notify4Archive n4a = new Notify4Archive();
				n4a.setLoanId(loanId);
				n4a.setImagefilepackagename(destFile.getName());
				n4a.setUrl(webUrl);
				if (n4a.execute()) {
					/* 暂时性文档归档备份, 运行稳定后去除 */
					FileUtil.renameDir(
							basePath,
							basePath
									+ "_"
									+ DateUtil
											.getNowTime(DateUtil.HRRB_LONG_DATETIME_FORMAT_BRIEF));

					logger.warn("影像件上传归档成功");
					aaJson.put("resultCode", "000");
					aaJson.put("resultMsg", "影像件上传归档成功.");
//					out.write(aaJson.toJSONString());

				} else {
					logger.warn("影像件提示归档失败");
					aaJson.put("resultCode", "997");
					aaJson.put("resultMsg", "影像件提示归档失败！");
//					out.write(aaJson.toJSONString());
				}

			} else {
				logger.error("影像件上传失败");
				aaJson.put("resultCode", "998");
				aaJson.put("resultMsg", "影像件上传失败！");
//				out.write(aaJson.toJSONString());
			}
		} catch (Exception e) {
			logger.error("影像件上传失败",e);
			aaJson.put("resultCode", "999");
			aaJson.put("resultMsg", "影像件上传失败！");
		}finally{
			if(is != null){
				try{
					is.close();
				}catch(Exception e){
					logger.error(destFileName + "关闭流异常", e);
				}
			}
		}
		out.write(aaJson.toJSONString());

		return null;
	}

	/**
	 * 上传单个文件
	 * 
	 * @param type
	 * @param fileName
	 * @return
	 */
	@RequestMapping("/uploadApplyImageFile")
	public ModelAndView uploadApplyImageFile(
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "sortId", required = false) String sortId,
			@RequestParam(value = "fileName", required = true) String fileName,
			@RequestParam(value = "loanId", required = false) String loanId,
			HttpServletRequest request, PrintWriter out) {

		double limit = 1260.00; /* 默认尺寸1260 */
		Map<String, Double> limitMapping = new HashMap<String, Double>() {
			private static final long serialVersionUID = 1L;
			{
				put("0101", 1680.00); // 申请表
				 put("0102",1024.00); //申请人身份证正面
				 put("0103",1024.00); //申请人身份证反面
				 put("0104",1024.00); //配偶身份证件正面
				 put("0105",1024.00); //配偶身份证反面
				 put("0106",1024.00); //结婚证
//				put("0107", 1260.00); // 营业执照
//				put("0117", 1260.00); // 税务登记证
//				put("0130", 1260.00); // 预签授信协议
//				put("0132", 1260.00); // 预签放款申请
				 put("0124", 1680.00); // 银行流水
			}
		};

		try {

			String decodeFileName = URLDecoder.decode(fileName, "utf-8"); // utf-8解码
			logger.info("uploadFile start....type=" + type + " |sortId="
					+ sortId + " | name=" + decodeFileName + " |loanId="
					+ loanId);
			int idxExt = decodeFileName.lastIndexOf(".");
			String fileExt = (idxExt >= 0) ? decodeFileName.substring(idxExt)
					: "";
			/* 重命名 */
			String renamedFile = "APP_" + loanId + "_" + type + "_"
					+ StringUtils.leftPad(sortId, 2, "0") + fileExt;
			logger.info("rename to : " + renamedFile);

			/* 临时存放目录 */
			String basePath = System.getProperty("java.io.tmpdir")
					+ "imag-upload/" + loanId;// this.getClass().getClassLoader().getResource("../../").toURI().getPath();
			/* resize folder */
			String resizePath = basePath + "/" + "resize";

			File folder = new File(resizePath);
			if (!folder.exists())
				folder.mkdirs(); // 建立临时目录

			/* 保存源文件 */
			File file = new File((basePath + "/" + decodeFileName)); // 保存图片在basePath

			BufferedInputStream fileIn = new BufferedInputStream(
					request.getInputStream());
			byte[] buf = new byte[1024]; // 每次读1k
			BufferedOutputStream fileOut = new BufferedOutputStream(
					new FileOutputStream(file));
			while (true) {
				// 读取数据
				int bytesIn = fileIn.read(buf, 0, 1024);
				if (bytesIn == -1) {
					break;
				} else {
					fileOut.write(buf, 0, bytesIn);
				}
			}
			fileOut.flush();
			fileOut.close();
			/* 源文件保存完毕 */

			if (fileExt.toLowerCase().matches("(.jpg|.jpeg|.bmp|.gif|.png)")) { // 针对图片做另存缩放处理
				if (limitMapping.containsKey(type)) {
					limit = limitMapping.get(type); // 获取特定影像的大小
				}
				ImageUtil.saveAsResize(file, (resizePath + "/" + renamedFile),
						limit);
			}

			logger.info("文件[" + decodeFileName + "]上传成功!");

			out.print("000"); // 上传成功

		} catch (IOException e) {
			e.printStackTrace();
			logger.error("文件上传失败!", e);
			out.print("999"); // 上传成功
		}

		return null;
	}

    @RequestMapping("/reloadFiles")
    public ModelAndView reloadFiles(HttpServletRequest request,PrintWriter out){

        //remoteFileSyncService.sync();
        JSONObject aaJson = new JSONObject();
        aaJson.put("success", "文件更新成功，请重新选择需要的用户资料");
        out.write(aaJson.toJSONString());
        return null;
    }
    /**
     * @param request
     * @param out
     * @return
     */
    @RequestMapping("/showFileUploadPage")
    public ModelAndView showFileUploadPage(@RequestParam(value = "folderName", required = false) String folderName,HttpServletRequest request,PrintWriter out){
        // file directory
//        String filePath = PropertyUtils.getProperty("filePath", "config/properties/systemInfo.properties"); 
    	String filePath = SysConfigFactory.getInstance().getService("uploadService").getPropertyValue("syncFolder");
        logger.debug(filePath);
        List<File> allFile = new ArrayList<File>();
        File contentDirectory = new File(filePath+folderName);
        logger.debug(contentDirectory.getPath());
        getAllFiles(contentDirectory,allFile);
        
        String[] suffix = { "jpg","png","gif","bmp","jpeg"};
        File file =null;
        if(allFile!=null){
            for(int i=0;i<allFile.size();i++){
                Boolean isNotImg = true;
                file = allFile.get(i);
                for (String aSuffix : suffix) {
                    if(file.getName().toLowerCase().endsWith(aSuffix.toLowerCase())){
                        isNotImg = false;
                        fileResize(file);
                        break;
                    }
                }
                if(isNotImg){
                    allFile.remove(i);
                }
            }
        }
        JSONObject aaJson = new JSONObject();
        try{
             if(allFile==null||allFile.size()<1){
                 aaJson.put("errorCode","444");
                 aaJson.put("errorMsg", "该用户下没有任何资料");
             }
         }
         catch(Exception e)
         {
          logger.error("读取用户资料错误",e);
         }
        aaJson.put("showFileUpload", tohtml(allFile, 6));
        out.write(aaJson.toJSONString());
        return null;
    }
    
	public void getAllFiles(File file, List<File> files){

	    if (file.isFile()) {
	        files.add(file);
	    }else{
	        File[] fileList = file.listFiles();
	        for(int i=0;i<fileList.length;i++){
	            getAllFiles(fileList[i],files);
	        }	        
	    }
	}
	
	/**
	 * @param pics
	 * @param beginIndex
	 * @param rowCount
	 * @return
	 */
	public String getARow(List<File> pics, int beginIndex, int rowCount) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<tr>");
        for (int count = 0; count < rowCount && beginIndex < pics.size(); count++) {
          //String fileName = pics[beginIndex].getName();
          //String hostFilePath = PropertyUtils.getProperty("imageFileUrl", "config/properties/systemInfo.properties");
          //buffer.append("<td><a href='"+hostFilePath+folderName+"/"+ fileName+"'><img src='"+hostFilePath+folderName+"/"+ fileName + "' width='150' height='150'/></a><select style='width:160px' onchange='selectPic(this)'><option><--请选择资料类型--></option><option id='File0101'>申请�?/option><option id='File0102'>申请人身份证正面</option><option id='File0103'>申请人身份证反面</option><option id='File0107'>营业执照</option><option id='File0108'>经营场所门口照片</option><option id='File0109'>经营场所门内照片</option><option id='File0104'>配偶身份证正�?/option><option id='File0105'>配偶身份证反�?/option><option id='File0106'>结婚�?/option><option id='File0110'>申请人征信授权书</option><option id='File0111'>申请人配偶征信授权书</option><option id='File0112'>协议亲签照片</option><option id='File0113'>房产�?/option><option id='File0114'>驾驶�?/option><option id='File0115'>离婚�?/option><option id='File0116'>单身证明</option><option id='File0117'>税务登记�?/option><option id='File0118'>组织机构代码�?/option><option id='File0119'>户口本或户籍证明</option><option id='File0120'>特许经营许可�?/option><option id='File0121'>经营场所租赁合同或水电煤发票</option><option id='File0122'>公司章程</option><option id='File0123'>欠款结清证明</option><option id='File0124'>银行流水</option><option id='File0125'>银行流水X季结�?/option><option id='File0126'>银行卡照�?/option><option id='File0127'>贷款卡正面</option><option id='File0128'>贷款卡反�?/option><option id='File0129'>法院判决�?/option><option id='File0130'>预签授信协议</option><option id='File0131'>授信协议骑缝照片</option><option id='File0132'>预签放款申请</option><option id='File0199'>其他材料</option></select><br><br></td>");
          int index = -1;
          String filePath = pics.get(beginIndex).getPath();
          index = filePath.indexOf(":");
          filePath = filePath.substring(index+1, filePath.length()).replace("\\", "/");
          //filePath = filePath.replace("\\", "/");
          buffer.append("<td><div>"+pics.get(beginIndex).getName()+"</div><a href='"+filePath+"'><img src='"+filePath + "' width='150' height='150'/></a><select style='width:160px' onchange='selectPic(this)'><option><--请选择资料类型--></option><option id='File0101' style='background-color:red'>申请表</option><option id='File0102' style='background-color:red'>申请人身份证正面</option><option id='File0103' style='background-color:red'>申请人身份证反面</option><option id='File0107' style='background-color:red'>营业执照</option><option id='File0108' style='background-color:red'>经营场所门口照片</option><option id='File0109' style='background-color:red'>经营场所门内照片</option><option id='File0104' style='background-color:yellow'>配偶身份证正面</option><option id='File0105' style='background-color:yellow'>配偶身份证反面</option><option id='File0106' style='background-color:yellow'>结婚证</option><option id='File0110'>申请人征信授权书</option><option id='File0111'>申请人配偶征信授权书</option><option id='File0112'>协议亲签照片</option><option id='File0113'>房产证</option><option id='File0114'>驾驶证</option><option id='File0115'>离婚证</option><option id='File0116'>单身证明</option><option id='File0117'>税务登记证</option><option id='File0118'>组织机构代码证</option><option id='File0119'>户口本或户籍证明</option><option id='File0120'>特许经营许可证</option><option id='File0121'>经营场所租赁合同或水电煤发票</option><option id='File0122'>公司章程</option><option id='File0123'>欠款结清证明</option><option id='File0124'>银行流水</option><option id='File0125'>银行的流水X季结息</option><option id='File0126'>银行卡照</option><option id='File0127'>贷款卡正面</option><option id='File0128'>贷款卡反面</option><option id='File0129'>法院判决书</option><option id='File0130'>预签授信协议</option><option id='File0131'>授信协议骑缝照片</option><option id='File0132'>预签放款申请</option><option id='File0133'>经营场所周边照片</option><option id='File0199'>其他材料</option></select><br><br></td>");
          beginIndex++;
        }
        buffer.append("</tr>");
        return buffer.toString();
    }
  
	/**
	 * @param pics
	 * @param rowCount
	 * @return
	 */
	public String tohtml(List<File> pics,int rowCount){
        StringBuffer buffer=new StringBuffer();
        if(rowCount>0)
        {
         buffer.append("<table id='allFiles' style='margin:0 0 0 55px'>");
         for(int beginIndex=0;beginIndex<pics.size();beginIndex+=rowCount)
         {
          buffer.append(getARow(pics, beginIndex, rowCount));
         }
         buffer.append("</table>");
        }
        else
        {
         logger.info("无效的rowCount");
        }
        return buffer.toString();
    }
	
    /**
     * @param file
     */
    public void fileResize(File file){
        try {
            ImageUtil.saveAsResize(file,file.getPath(),1680,true);
        } catch (IOException e) {
            logger.error("压缩图片失败",e);
        }
    }
	
	@RequestMapping("/openCreditMain")
	public ModelAndView openCreditMain(
			@RequestParam(value = "loanId", required = false) String loanId,
			@RequestParam(value = "applyStatus", required = false) String applyStatus,
			@RequestParam(value = "direct2Path", required = false) String direct2Path) {
		ModelAndView mav = new ModelAndView();
		List<Map<String, String>> dateList = DateUtil
				.getOverNumMonths(CreditApplyConstants.monthNum);
		mav.addObject("overMonths", dateList);
		mav.addObject("province", provinceList);
		mav.addObject("bizChannel", channelDictionary);
		mav.addObject("paperList", paperList);
		mav.addObject("returnKindList", returnKindList);
		mav.addObject("currSignList", currSignList);
		mav.addObject("sexList", sexList);
		mav.addObject("maritalList", maritalList);
		mav.addObject("eduList", eduList);
		mav.addObject("relList", relList);
		mav.addObject("cities", cities);
		mav.addObject("divisions", divisions);
		mav.addObject("bankNoList", bankNoList);
		mav.addObject("implTypeList", implTypeList);
		mav.addObject("repayMethodList", repayMethodList);
		mav.addObject("contactFlags", contactFlags);
		mav.addObject("HrrbIndustTypeList", HrrbIndustTypeList);
		mav.addObject("occurTypes",occurTypes);

		if (!StringUtil.isEmpty(applyStatus)) {
			mav.addObject(CreditApplyConstants.APPLY_STATUS, applyStatus);
		}
		/*
		 * if(!StringUtil.isEmpty(isApplyStatus)){
		 * mav.addObject("isApplyStatus",isApplyStatus); }
		 */
		if (loanId != null && loanId.trim().length() > 0) { // load detail when
															// loanId is null
			// 查询出
			List<Map<String, Object>> resList = loanPosCreditApplyBackStageBiz
					.getCreditApplyDetail(loanId);
			if (resList != null && resList.size() > 0) {
				// --------追加地址中的市和县一级代码名称,0业务申请信息;1客户信息;3商户信息;4银行账户信息;2亲属信息
				Object residentCity = resList.get(1).get("residentCity");
				if (residentCity != null)
					resList.get(1).put("residentCityName",
							getItemValue(cities, residentCity.toString()));
				Object residentDivision = resList.get(1)
						.get("residentDivision");
				if (residentDivision != null)
					resList.get(1)
							.put("residentDivisionName",
									getItemValue(divisions,
											residentDivision.toString()));

				Object posCustCity = resList.get(3).get("posCustCity");
				if (posCustCity != null)
					resList.get(3).put("posCustCityName",
							getItemValue(cities, posCustCity.toString()));
				Object posCustDivision = resList.get(3).get("operAddrCode");
				if (posCustDivision != null)
					resList.get(3)
							.put("posCustDivisionName",
									getItemValue(divisions,
											posCustDivision.toString()));
				// --------商户行业分类代码转换
				Object industryTypeId = resList.get(3).get("industryTypeId");
				if (industryTypeId != null) {
					String itemName = loanPosBusinessCodeBiz.getItemNameByNo(
							BusinessDictionaryConstants.IndustryType,
							(String) industryTypeId);
					if (itemName != null && itemName.trim().length() > 0) {
						resList.get(3).put("industryTypeName",
								"[" + industryTypeId + "] " + itemName);
					}
				}

				logger.info(JSON.toJSONString(resList));
				Object applyDetail = JSON.toJSON(resList);
				mav.addObject("applyDetail", applyDetail);
			}
		} else {
			// generate loadId
			loanId = IdUtil.getId("LO");
		}
		mav.addObject("loanId", loanId);

		mav.setViewName(direct2Path);
		return mav;
	}

	@RequestMapping("/openActivityContent")
	public ModelAndView openModifyActivityContent(@RequestParam(value="id", required = false)String id, @RequestParam(value="direct2Path", required=false)String direct2Path){
		ModelAndView mav = new ModelAndView();
		if(StringUtil.isNotEmpty(id)){
			TActivityContentInfo tActivityContentInfo = activityBiz.getActivityContentInfoById(id);
			if(tActivityContentInfo != null){
				mav.addObject("contentName", tActivityContentInfo.getContentName());
				mav.addObject("contentParamType", tActivityContentInfo.getContentParamType());
				mav.addObject("contentSql", tActivityContentInfo.getContentSql());
				mav.addObject("activityType", tActivityContentInfo.getActivityType());
				mav.addObject("id", id);
			}
			
		}
		List<TActivityTypeInfo> typeInfos = activityBiz.getTActivityTypeInfos();
		mav.addObject("typeInfos", typeInfos);
		mav.setViewName(direct2Path);
		return mav;
	}
	
	@RequestMapping("/openActivity")
	public ModelAndView openActivity(@RequestParam(value="actId", required = false)String actId,@RequestParam(value="direct2Path", required=false)String direct2Path){
		
		ModelAndView mav = new ModelAndView();
		List<TActivityTypeInfo> typeList = activityBiz.getTActivityTypeInfos();
		mav.addObject("typeInfos", typeList);
		mav.setViewName(direct2Path);
		return mav;
	}
	
	
	@RequestMapping("/openActivityDim")
	public ModelAndView openActvityDim(@RequestParam(value="id", required = false)String id, @RequestParam(value="direct2Path", required = false)String direct2Path){
		ModelAndView mav = new ModelAndView();
		if(StringUtil.isNotEmpty(id)){
			TActivityDimInfo info = activityBiz.getActivityDimById(id);
			if(info != null){
				mav.addObject("dimSql", info.getDimenSql());
				mav.addObject("dimName", info.getDimName());
				mav.addObject("dimParamType", info.getDimParamType());
				mav.addObject("activityType", info.getActivityType());
				mav.addObject("id", id);
			}
		}
		List<TActivityTypeInfo> typeInfos = activityBiz.getTActivityTypeInfos();
		mav.addObject("typeInfos", typeInfos);
		mav.setViewName(direct2Path);
		return mav;
	}
	@RequestMapping("/openAcceptResult")
	public ModelAndView openAcceptApprove(
			HttpServletRequest request,
			@RequestParam(value = "loanId", required = false) String loanId,
			@RequestParam(value = "direct2Path", required = false) String direct2Path,
			PrintWriter out) {
		ModelAndView mav = new ModelAndView();
		String loanId1 = request.getParameter("loanId");
		List<Map<String, Object>> resList = loanPosCreditApplyBackStageBiz
				.getCreditApplyDetail(loanId1);
		if (resList != null && resList.size() > 0) {
			// --------追加地址中的市和县一级代码名称,0业务申请信息;1客户信息;3商户信息;4银行账户信息;2亲属信息
			Object residentCity = resList.get(1).get("residentCity");
			if (residentCity != null)
				resList.get(1).put("residentCityName",
						getItemValue(cities, residentCity.toString()));
			Object residentDivision = resList.get(1).get("residentDivision");
			if (residentDivision != null)
				resList.get(1).put("residentDivisionName",
						getItemValue(divisions, residentDivision.toString()));

			Object posCustCity = resList.get(3).get("posCustCity");
			if (posCustCity != null)
				resList.get(3).put("posCustCityName",
						getItemValue(cities, posCustCity.toString()));
			Object posCustDivision = resList.get(3).get("operAddrCode");
			if (posCustDivision != null)
				resList.get(3).put("posCustDivisionName",
						getItemValue(divisions, posCustDivision.toString()));
			// --------商户行业分类代码转换
			Object industryTypeId = resList.get(3).get("industryTypeId");
			if (industryTypeId != null) {
				String itemName = loanPosBusinessCodeBiz.getItemNameByNo(
						BusinessDictionaryConstants.IndustryType,
						(String) industryTypeId);
				if (itemName != null && itemName.trim().length() > 0) {
					resList.get(3).put("industryTypeName",
							"[" + industryTypeId + "] " + itemName);
				}
			}

			Object applyDetail = JSON.toJSON(resList);
			mav.addObject("applyDetail", applyDetail);

			List<Map<String, Object>> approveDetail = loanPosContractManagementBiz
					.getApproveMap(loanId1);
			Object approveInfo = JSON.toJSON(approveDetail.get(0));
			mav.addObject("approveInfo", approveInfo);

			TBankAccnoInfo bankCard = loanPosCreditApplyService
					.selectByBankAccno((String) approveDetail.get(0).get(
							"acceptAccount"));
			mav.addObject("cardStatus", bankCard.getStatus()); // 01未验真//FIXME;

			/**/
			AccessPrivilege access = (AccessPrivilege) request.getSession()
					.getAttribute("accessPrivilege");
			boolean hasManageRole = access
					.hasAnyPrivilege("ROLE_APPROVED_ADMIN");
			mav.addObject("hasManageRole", hasManageRole);

			List<Map<String, String>> dateList = DateUtil
					.getOverNumMonths(CreditApplyConstants.monthNum);
			mav.addObject("overMonths", dateList);
			mav.addObject("province", provinceList);
			mav.addObject("bizChannel", channelDictionary);
			mav.addObject("paperList", paperList);
			mav.addObject("returnKindList", returnKindList);
			mav.addObject("currSignList", currSignList);
			mav.addObject("sexList", sexList);
			mav.addObject("maritalList", maritalList);
			mav.addObject("eduList", eduList);
			mav.addObject("relList", relList);
			mav.addObject("cities", cities);
			mav.addObject("divisions", divisions);
			mav.addObject("bankNoList", bankNoList);
			mav.addObject("implTypeList", implTypeList);
			mav.addObject("repayMethodList", repayMethodList);
			mav.addObject("contactFlags", contactFlags);
			mav.addObject("HrrbIndustTypeList", HrrbIndustTypeList);
			mav.setViewName(direct2Path);
		}
		return mav;
	}

	private Object getItemValue(List<Map<String, Object>> clib, String itemNo) {
		for (Map<String, Object> r : clib) {
			if (r.get("itemNo").equals(itemNo)) {
				return r.get("itemName");
			}
		}
		return null;
	}

	/**/
	@RequestMapping("/directAccess")
	public ModelAndView directAccess(
			@RequestParam(value = "srcpath", required = true) String srcpath) {
		int idx = srcpath.lastIndexOf(".");
		if (idx > 0)
			srcpath = srcpath.substring(0, idx);

		ModelAndView mav = new ModelAndView();
		mav.setViewName(srcpath);
		return mav;
	}

	/**
	 * 根据3级区划编码，转换为完整的区划名称:省+市+区
	 * 
	 * @param divisionCode
	 * @return
	 */
	private String getFullyDivisonName(String divisionCode) {
		if (divisionCode == null || divisionCode.trim().length() != 6) {
			logger.warn("行政区划代码 [" + divisionCode + "] 有误");
			return "";
		}
		String prov = divisionCode.substring(0, 2) + "0000";
		String provName = (String) getItemValue(provinceList, prov);
		String city = divisionCode.substring(0, 4) + "00";
		String cityName = (String) getItemValue(cities, city);
		String countryName = (String) getItemValue(divisions, divisionCode);

		return provName + " / " + cityName + " / " + countryName + " / "; // 省+市+区
	}

	/**
	 * 根据申请流水号获取审批相关的业务数据
	 * 
	 * @param loanId
	 * @param directTo
	 * @return
	 */
	@RequestMapping("/openApprovalView")
	public ModelAndView openApprovalView(
			@RequestParam(value = "loanId", required = false) String loanId,
			@RequestParam(value = "opflag", required = false) String opflag,
			@RequestParam(value = "directTo", required = false) String directTo,
			HttpServletRequest request) {
		if (loanId == null || loanId.trim().length() == 0) {
			logger.debug(this.getClass().getName() + " 传入的贷款申请流水号为空!");
			return null;
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("loanId", loanId);
		mav.addObject("province", provinceList);
		mav.addObject("bizChannel", channelDictionary);
		mav.addObject("paperList", paperList);
		mav.addObject("returnKindList", returnKindList);
		mav.addObject("currSignList", currSignList);
		mav.addObject("sexList", sexList);
		mav.addObject("maritalList", maritalList);
		mav.addObject("eduList", eduList);
		mav.addObject("relList", relList);
		mav.addObject("cities", cities);
		mav.addObject("divisions", divisions);
		mav.addObject("bankNoList", bankNoList);
		mav.addObject("implTypeList", implTypeList);
		mav.addObject("repayMethodList", repayMethodList);
		mav.addObject("contactFlags", contactFlags);
		mav.addObject("HrrbIndustTypeList", HrrbIndustTypeList);
		mav.addObject("occurTypes",occurTypes);
		mav.addObject("opflag", opflag);

		// 查询出申请详情中的各部分信息0业务申请信息;1客户信息;3商户信息;4银行账户信息;2亲属信息
		List<Map<String, Object>> applyList = loanPosCreditApplyBackStageBiz
				.getCreditApplyDetail(loanId);
		// 将需要显示的代码项转换成名称
		if (applyList != null && applyList.size() > 0) {
			// --------追加地址中的市和县一级代码名称,0业务申请信息;1客户信息;3商户信息;4银行账户信息;2亲属信息
			Object residentCity = applyList.get(1).get("residentCity");
			if (residentCity != null)
				applyList.get(1).put("residentCityName",
						getItemValue(cities, residentCity.toString()));
			Object residentDivision = applyList.get(1).get("residentDivision");
			if (residentDivision != null)
				applyList.get(1).put("residentDivisionName",
						getItemValue(divisions, residentDivision.toString()));

			Object posCustCity = applyList.get(3).get("posCustCity");
			if (posCustCity != null)
				applyList.get(3).put("posCustCityName",
						getItemValue(cities, posCustCity.toString()));
			Object posCustDivision = applyList.get(3).get("operAddrCode");
			if (posCustDivision != null)
				applyList.get(3).put("posCustDivisionName",
						getItemValue(divisions, posCustDivision.toString()));
			// --------商户行业分类代码转换
			Object industryTypeId = applyList.get(3).get("industryTypeId");
			if (industryTypeId != null) {
				String itemName = loanPosBusinessCodeBiz.getItemNameByNo(
						BusinessDictionaryConstants.IndustryType,
						(String) industryTypeId);
				if (itemName != null && itemName.trim().length() > 0) {
					applyList.get(3).put("industryTypeName",
							"[" + industryTypeId + "] " + itemName);
				}
			}

			// 渠道
			Object channel = applyList.get(0).get("channel");
			if (channel != null)
				applyList.get(0).put(
						"channelName",
						loanPosBusinessCodeBiz.getItemNameByNo(
								BusinessDictionaryConstants.BizChannel,
								channel.toString()));
			// 产品名称
			Object prodId = applyList.get(0).get("prodId");
			if (prodId != null) {
				String prodName = loanPosBusinessCodeBiz.getItemNameByNo(
						BusinessDictionaryConstants.ProductNo, (String) prodId);
				applyList.get(0).put("prodName", prodName);
			}
			// 申请状态
			Object applyStatus = applyList.get(0).get("applyStatus");
			// if(applyStatus!=null) applyList.get(0).put("applyStatusName",
			// getItemValue(xx,applyStatus.toString()));
			if (applyStatus != null) {
				String applyStatusName = loanPosBusinessCodeBiz
						.getItemNameByNo(
								BusinessDictionaryConstants.ApplyStatus,
								(String) applyStatus);
				applyList.get(0).put("applyStatusName", applyStatusName);
			}
			// 申请状态
			Object returnKind = applyList.get(0).get("returnKind");
			// if(applyStatus!=null) applyList.get(0).put("applyStatusName",
			// getItemValue(xx,applyStatus.toString()));
			if (returnKind != null) {
				String returnKindName = loanPosBusinessCodeBiz.getItemNameByNo(BusinessDictionaryConstants.AccrualType,(String) returnKind);
				applyList.get(0).put("returnKindName", returnKindName);
			}
			// 经营地
			Object region = applyList.get(0).get("region");
			if (region != null) {
				String regionId = (String) region;
				if (regionId.length() >= 2)
					regionId = regionId.substring(0, 2) + "0000"; // 显示省一级
				String regionName = loanPosBusinessCodeBiz.getItemNameByNo(
						"AdminDivision", (String) regionId);
				applyList.get(0).put("regionName", regionName);
			}

			// 性别
			Object sexSign = applyList.get(1).get("sexSign");
			if (sexSign != null)
				applyList.get(1).put("sexSignName",
						getItemValue(sexList, sexSign.toString()));
			// 受教育程度
			Object educSign = applyList.get(1).get("educSign");
			if (educSign != null)
				applyList.get(1).put("educSignnName",
						getItemValue(eduList, educSign.toString()));
			// 婚姻状况
			Object marrSign = applyList.get(1).get("marrSign");
			if (marrSign != null)
				applyList.get(1).put("marrSignName",
						getItemValue(maritalList, marrSign.toString()));

			// Object residentDivision =
			// applyList.get(1).get("residentDivision");
			// if(residentDivision!=null)
			// applyList.get(1).put("residentDivisionName",
			// getItemValue(divisions,residentDivision.toString()));
			String residentAddress = getFullyDivisonName((String) residentDivision)
					+ applyList.get(1).get("residentDetail");
			applyList.get(1).put("residentAddress", residentAddress);

			// 联系地址类型
			Object contactAddrFlag = applyList.get(1).get("contactAddrFlag");
			// if(contactAddrFlag!=null)
			// applyList.get(1).put("contactAddrFlagName",
			// getItemValue(xx,contactAddrFlag.toString()));
			if (contactAddrFlag != null) {
				String contactAddrFlagName = loanPosBusinessCodeBiz
						.getItemNameByNo("ContactAddress",
								(String) contactAddrFlag);
				applyList.get(1).put("contactAddrName", contactAddrFlagName);
			}

			// 证件所属地
			Object paperId = applyList.get(1).get("paperId");
			if (paperId != null && String.valueOf(paperId).length() >= 6) {
				applyList.get(1).put(
						"paperIdBelong",
						loanPosBusinessCodeBiz.getItemNameByNo("CertIDRegion",
								(String) String.valueOf(paperId)
										.substring(0, 6)));
			}
			Object matePaperId = applyList.get(1).get("matePaperId");
			if (matePaperId != null
					&& String.valueOf(matePaperId).length() >= 6) {
				applyList.get(1).put(
						"matePaperIdBelong",
						loanPosBusinessCodeBiz.getItemNameByNo(
								"CertIDRegion",
								(String) String.valueOf(matePaperId).substring(
										0, 6)));
			}
			// 手机归属地
			Object mobilePhone = applyList.get(1).get("mobilePhone");
			if (mobilePhone != null
					&& String.valueOf(mobilePhone).length() >= 7) {
				applyList.get(1).put("mobileBelong",
						configBiz.getMobileBelong((String) mobilePhone));
			}
			Object mateMobilephone = applyList.get(1).get("mateMobilephone");
			if (mateMobilephone != null
					&& String.valueOf(mateMobilephone).length() >= 7) {
				applyList.get(1).put("mateMobileBeolong",
						configBiz.getMobileBelong((String) mateMobilephone));
			}

			String bizAddress = getFullyDivisonName((String) applyList.get(3)
					.get("operAddrCode"))
					+ applyList.get(3).get("posCustAddress");
			applyList.get(3).put("bizAddress", bizAddress);

			if (applyStatus != null
					&& !String.valueOf(applyStatus).matches(
							"(00|10|20|21|30|93)")) { // 00|10|20|21|30 没有模型结果
				// PHASE02 初审模型结果
				TRiskCheckResult model = riskCheckBiz
						.queryRiskCheckResult(loanId);
				if (model != null) {
					// 属相
					String zodiac = StringUtils.leftPad(model.getResult33(), 2,
							"0");
					applyList.get(1).put(
							"zodiacName",
							loanPosBusinessCodeBiz.getItemNameByNo("Zodiac",
									zodiac));

					// if(model.getResult09()!=null){
					// model.setResult09(model.getResult09().multiply(new
					// BigDecimal(100.00)));
					// }
				}
				mav.addObject("RiskModel", model);
			}

			// 账户信息
			Map<String, Object> account = applyList.get(4);
			if (account != null && account.size() > 0) {
				String bankNo = (String) account.get("bankName");
				if (bankNo != null && bankNo.trim().length() > 0) {
					String bankName = loanPosBusinessCodeBiz.getItemNameByNo(
							"BankNo", bankNo);
					account.put("bankNameStr", bankName);
				}
				String branchName = (String) account.get("bankBranName");
				if (branchName != null && branchName.trim().length() > 0) {
					if (branchName.indexOf("分行") == -1)
						branchName += "分行";
					account.put("branchNameStr", branchName);
				}
				String subBranchName = (String) account.get("bankSubbName");
				if (subBranchName != null && subBranchName.trim().length() > 0) {
					if (subBranchName.indexOf("支行") == -1)
						subBranchName += "支行";
					account.put("subBranchNameStr", subBranchName);
				}
			}
			// 亲属信息
			Map<String, Object> relative = applyList.get(2);
			if (relative != null && relative.size() > 0) {
				String kind = (String) relative.get("relaKind");
				if (kind != null && kind.trim().length() > 0) {
					relative.put("relaKindName", getItemValue(relList, kind));
				}
			}

			Object applyDetail = JSON.toJSON(applyList);
			mav.addObject("applyDetail", applyDetail);
		}

		// PHASE03 暂存的审批信息
		User user = (User) request.getSession().getAttribute("USER");
		TCreditApplyAprvInfo record = new TCreditApplyAprvInfo();
		record.setLoanId(loanId);
		record.setApprvId(user.getUserName());
		// 当前工作
		if ("1".equals(opflag)) {
			// 查询保存的审批信息
			record = apprBiz.selectNotSubmit(record);
			// 没有审批意见时，默认显示上一次提交的意见
			// 认领任务时已经将上次提交的信息保存，这里不用再查询了
			// if (StringUtil.isEmpty(record.getApprInfo())){
			// TCreditApplyAprvInfo recordLast = new TCreditApplyAprvInfo();
			// recordLast.setLoanId(loanId);
			// // 查询最近一次提交的审批信息
			// recordLast = apprBiz.selectLastSubbmitted(recordLast);
			// record.setApprInfo(recordLast.getApprInfo());
			// record.setApprInfoExt(recordLast.getApprInfoExt());
			// }
			// 已完成工作
		} else {
			// 查询最近一次提交的审批信息
			record = apprBiz.selectLastSubbmitted(record);
		}
		mav.addObject("ApprOpinion", record);

		// PHASE04 审批记录中的回退人员列表
		// 申请状态设定
		String applyStatus = (String) applyList.get(0).get("applyStatus");
		if (applyStatus.matches("(31|33|41|34)")) { // 31复审1 33复审2 34复审3 41尽调审核 三阶段可见回退
			List<String> applyStatuses = new ArrayList<String>();
			applyStatuses.add(ReviewNoteConstants.APPLYSTATUS_CODE_20);
			// 复审2阶段
			if (ReviewNoteConstants.APPLYSTATUS_CODE_33.equals(applyStatus)) {
				applyStatuses.add(ReviewNoteConstants.APPLYSTATUS_CODE_31);
			// 复审3阶段
			}else if(ReviewNoteConstants.APPLYSTATUS_CODE_34.equals(applyStatus)){
				applyStatuses.add(ReviewNoteConstants.APPLYSTATUS_CODE_31);
				applyStatuses.add(ReviewNoteConstants.APPLYSTATUS_CODE_33);
			// 复审4阶段
			}else if(ReviewNoteConstants.APPLYSTATUS_CODE_35.equals(applyStatus)){
				applyStatuses.add(ReviewNoteConstants.APPLYSTATUS_CODE_31);
				applyStatuses.add(ReviewNoteConstants.APPLYSTATUS_CODE_33);
				applyStatuses.add(ReviewNoteConstants.APPLYSTATUS_CODE_34);
			// 尽调复审
			} else if (ReviewNoteConstants.APPLYSTATUS_CODE_41
					.equals(applyStatus)) {
				applyStatuses.add(ReviewNoteConstants.APPLYSTATUS_CODE_31);
				applyStatuses.add(ReviewNoteConstants.APPLYSTATUS_CODE_33);
				applyStatuses.add(ReviewNoteConstants.APPLYSTATUS_CODE_34);
				applyStatuses.add(ReviewNoteConstants.APPLYSTATUS_CODE_35);
			}
			record.setLoanId(loanId);

			record.setApplyStatuses(applyStatuses);
			List<TCreditApplyAprvInfo> l = apprBiz.selectBackToInfo(record);
			mav.addObject("BackRoller", l);
		}
		// 接受代码
		mav.addObject("passCodeList", passCodeList);
		// 拒绝代码
		mav.addObject("refuseCodeList", refuseCodeList);

		// 审批意见外部
		// 拒绝原因
		List<Map<String, Object>> refuseReaonList = new ArrayList<>();
		Map<String, Object> reqMap = Maps.newHashMap();
		reqMap.put("codeNo", BusinessDictionaryConstants.RefuseReaon);
		// 当前工作,出掉综合情况不符合哈行准入(自动)
		if ("1".equals(opflag)) {
			reqMap.put("opflag", opflag);
		}
		// 资料审核
		if (ReviewNoteConstants.APPLYSTATUS_CODE_20.equals(applyStatus)) {
			reqMap.put("extAttrLike", ControllerHelper.getLikeString("初审选项"));
			refuseReaonList = bdBiz.selectRefuseReaonMap(reqMap);
			// 31复审1 33复审3 41尽调审核
		} else if (applyStatus.matches("(31|33|34|41)")) {
			reqMap.put("extAttrLike", ControllerHelper.getLikeString("复审选项"));
			refuseReaonList = bdBiz.selectRefuseReaonMap(reqMap);
			// 拒绝状态
		} else if (applyStatus.matches("(91|92)")) {
			refuseReaonList = bdBiz.selectRefuseReaonMap(reqMap);
		}
		logger.debug("拒绝原因为"+refuseReaonList);
       //补件数据字典表数据查询存储
		List<Map<String, Object>> addInfoList = new ArrayList<>();
		Map<String, Object> reqMap2 = Maps.newHashMap();
		reqMap2.put("codeNo", BusinessDictionaryConstants.RefuseReaon);
		reqMap2.put("extAttr", BusinessDictionaryConstants.addInfo);
		addInfoList = bdBiz.selectRefuseReaonMap(reqMap2);
		
		
		String apprInfoExt = record.getApprInfoExt();
		logger.debug("申请编号为"+record.getLoanId());
		logger.debug("拒绝信息为"+apprInfoExt);
		
		if (StringUtil.isNotEmpty(apprInfoExt)) {
			logger.debug(apprInfoExt+"不为空");
			// 根据选择的选项，设置页面的选择按钮
			List<String> apprInfoExtCodes = Arrays.asList(apprInfoExt
					.split(ReviewNoteConstants.STRING_SEPARATOR_CODE));
			for (Map<String, Object> map : refuseReaonList) {
				if (apprInfoExtCodes.contains(map.get("itemNo"))) {
					map.put("checked", "1");
				} else {
					map.put("checked", "0");
				}
			}
		} else {
			logger.debug(apprInfoExt+"为空");
			for (Map<String, Object> map : refuseReaonList) {
				map.put("checked", "0");
			}
		}
		logger.debug("拒绝原因为"+refuseReaonList);
		// 审批意见外部
		mav.addObject("refuseReaonList", refuseReaonList);
		// 补件资料
		mav.addObject("addInfoList", addInfoList);
		logger.debug("补件信息为"+addInfoList);		
		// PHASE05 审批人审批限额
		// 查询保存的审批信息
		TUserApprInfo uai = userApprInfoBiz.selectByPrimaryKey(user
				.getUserName());
		// 查询操作者的最大审批额度
		if (uai == null || uai.getQuota() == null) {
			mav.addObject("apprQuota", 0);
		} else {
			mav.addObject("apprQuota", uai.getQuota());
		}

		mav.setViewName(directTo);

		return mav;
	}

	/**
	 * 用款申请
	 * 
	 * @param paymentStatus
	 * @return
	 */
	@RequestMapping("/queryPaymentInfo")
	public ModelAndView queryPaymentInfo(
			@RequestParam(value = "paymentStatus", required = false) String paymentStatus) {
		ModelAndView mav = new ModelAndView();
		if (!StringUtil.isEmpty(paymentStatus)) {
			mav.addObject(PaymentApplyConstants.PAYMENT_APPLY_STATUS,
					paymentStatus);
		}
		mav.addObject("paymentApplyList", paymentApplyList);
		mav.setViewName(PaymentApplyConstants.QUERY_PAYMENT_APPLY_PAGE);
		return mav;
	}

	/**
	 * 新增用款申请
	 * 
	 * @param loanId
	 * @param status
	 * @param direct2Path
	 * @return
	 */
	@RequestMapping("/openCreatePaymentApply")
	public ModelAndView openCreatePaymentApply(
			@RequestParam(value = "loanId", required = false) String loanId,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "direct2Path", required = false) String direct2Path) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(direct2Path);
		return mav;
	}

	@RequestMapping("/openAddInfo")
	public ModelAndView openAddInfo(PrintWriter out,@RequestParam(value = "direct2Path", required = false) String direct2Path) {
		ModelAndView mav = new ModelAndView();
		List<Map<String, Object>> addInfoList = new ArrayList<>();
		Map<String, Object> reqMap2 = Maps.newHashMap();
		reqMap2.put("codeNo", BusinessDictionaryConstants.RefuseReaon);
		reqMap2.put("extAttr", BusinessDictionaryConstants.addInfo);
		addInfoList = bdBiz.selectRefuseReaonMap(reqMap2);
		mav.addObject("addInfoList", addInfoList);
		mav.setViewName(direct2Path);
		return mav;
	}
	
	/**
	 * 用款申请
	 * 
	 * @param contNo
	 * @param direct2Path
	 * @return
	 */
	@RequestMapping("/openInsertPaymentApply")
	public ModelAndView openopenInsertPaymentApply(
			@RequestParam(value = "contNo", required = false) String contNo,
			@RequestParam(value = "direct2Path", required = false) String direct2Path) {
		ModelAndView mav = new ModelAndView();
		TContractManagement contract = loanPosContractManagementBiz.getContractForDisplay(contNo);
		// String paybackMethodName =
		// loanPosBusinessCodeBiz.getItemNameByNo("AccrualType",
		// contractManagement.getPaybackMethod());
		//FIXME; 可用余额算法有问题
		BigDecimal sum = (receiptManageBiz.sumLoanTotalBalance(contNo)) == null ? BigDecimal.ZERO
				: new BigDecimal(receiptManageBiz.sumLoanTotalBalance(contNo));
		BigDecimal availableBalance = contract.getCreditLine().subtract(sum);
		
		Date beginDate = new Date();
		if(contract.getPaybackMethod().equals("10")){
			Date endDate= null;;
			try {
				endDate = DateUtil.parse2Date(DateUtil.getRelativeDate(beginDate, 0, 12, 0));
			} catch (Exception e) {
				e.printStackTrace();
			}
			mav.addObject("endDate", endDate);
			mav.setViewName("paymentApply/createPaymentApply/insertFixedPaymentApply");
		}else{
			mav.setViewName("paymentApply/createPaymentApply/insertPaymentApply");
		}
		
		mav.addObject("contract", contract);
		mav.addObject("contNo", contNo);
		// mav.addObject("paybackMethodName",paybackMethodName);
		mav.addObject("returnKindList", returnKindList);
		mav.addObject("availableBalance", availableBalance);
		mav.addObject("beginDate", beginDate);
		return mav;
	}

	/**
	 * 取消用款申请
	 * 
	 * @param payApplyId
	 * @param direct2Path
	 * @return
	 */
	@RequestMapping("/openCancelPaymentApply")
	public ModelAndView openCancelPaymentApply(
			@RequestParam(value = "payApplyId", required = false) String payApplyId,
			@RequestParam(value = "direct2Path", required = false) String direct2Path) {
		ModelAndView mav = new ModelAndView();
		TPaymentApply paymentApply = new TPaymentApply();
		TContractManagement contractManagement = new TContractManagement();
		TCustomer customer = new TCustomer();
		List<Map<String, Object>> creditApplyMap = new ArrayList<Map<String, Object>>();
		String channelName = "";
		String paybackMethodName = "";
		String expectedEndDate = "";
		String openBankName = "";
		if (!StringUtil.isEmpty(payApplyId)) {
			paymentApply = paymentApplyBiz.queryPaymentApplyById(payApplyId);
			// 根据期望用款日计算用款结束日
			Date expectedDate = paymentApply.getExpectedDate();
			if (expectedDate != null) {
				expectedEndDate = DateUtil.getRelativeDate(expectedDate, 0,
						Integer.parseInt(paymentApply.getPayApplyTerm()), 0);
			}
		}
		String contNo = paymentApply.getContNo();
		if (!StringUtil.isEmpty(contNo)) {
			// 协议信息
			contractManagement = loanPosContractManagementBiz
					.getContractByContNo(contNo);
			contractManagement.getChannel();
			channelName = loanPosBusinessCodeBiz.getItemNameByNo("BizChannel",
					contractManagement.getChannel());
			paybackMethodName = loanPosBusinessCodeBiz.getItemNameByNo(
					"AccrualType", contractManagement.getPaybackMethod());
			openBankName = loanPosBusinessCodeBiz.getItemNameByNo("BankNo",
					contractManagement.getAccountOpenBank());
			// 申请信息
			if (!StringUtil.isEmpty(contractManagement.getLoanId())) {
				creditApplyMap = loanPosCreditApplyBackStageBiz
						.getCreditApplyDetail(contractManagement.getLoanId());
			}
		}
		mav.addObject("contract", contractManagement);
		mav.addObject("paymentApply", paymentApply);
		mav.addObject("customer", customer);
		mav.addObject("creditApplyMap", creditApplyMap);
		mav.addObject("payApplyId", payApplyId);
		mav.addObject("channelName", channelName);
		mav.addObject("paybackMethodName", paybackMethodName);
		mav.addObject("openBankName", openBankName);
		mav.addObject("expectedEndDate", expectedEndDate);
		mav.addObject("paperList", paperList);
		mav.addObject("returnKindList", returnKindList);
		mav.addObject("currSignList", currSignList);
		mav.addObject("sexList", sexList);
		mav.addObject("maritalList", maritalList);
		mav.addObject("eduList", eduList);
		mav.addObject("relList", relList);
		mav.addObject("cities", cities);
		mav.addObject("divisions", divisions);
		mav.addObject("bankNoList", bankNoList);
		mav.addObject("implTypeList", implTypeList);
		mav.addObject("repayMethodList", repayMethodList);
		mav.setViewName(direct2Path);
		return mav;
	}

	/**
	 * 查询用款审批
	 * 
	 * @return
	 */
	@RequestMapping("/queryPaymentReview")
	public ModelAndView queryPaymentReview(
			@RequestParam(value = "reviewStatus", required = false) String reviewStatus,
			PrintWriter out) {
		ModelAndView mav = new ModelAndView();
		List<Map<String, String>> dateList = DateUtil
				.getOverNumMonths(CreditApplyConstants.monthNum);
		mav.addObject("overMonths", dateList);
		mav.addObject("province", provinceList);
		mav.addObject("bizChannel", channelDictionary);
		mav.addObject("paperList", paperList);
		mav.addObject("returnKindList", returnKindList);
		mav.addObject("currSignList", currSignList);
		mav.addObject("sexList", sexList);
		mav.addObject("maritalList", maritalList);
		mav.addObject("eduList", eduList);
		mav.addObject("relList", relList);
		mav.addObject("cities", cities);
		mav.addObject("divisions", divisions);
		mav.addObject("bankNoList", bankNoList);
		mav.addObject("implTypeList", implTypeList);
		mav.addObject("repayMethodList", repayMethodList);
		mav.addObject("reviewStatus", reviewStatus);
		mav.setViewName(PaymentApplyConstants.QUERY_PAYMENT_FOR_REVIEW_PAGE);
		return mav;
	}

	/**
	 * 跳转签署意见
	 * 
	 * @param payApplyIds
	 * @param out
	 * @param request
	 * @return
	 */
	@RequestMapping("/sign")
	public ModelAndView sign(
			@RequestParam(value = "payApplyId", required = false) String payApplyId,
			PrintWriter out, HttpServletRequest request) {
		Map<String, Object> map = Maps.newHashMap();
		if (!StringUtil.isEmpty(payApplyId)) {
			map.put("payApplyId", payApplyId);
			List<Map<String, Object>> tp = paymentApplyBiz
					.queryPaymentApplyByPayApplyId(map);
			if (null != tp.get(0).get("expectedDate")) {
				String expectedDate = DateUtil.getDateToString3((Date) tp
						.get(0).get("expectedDate"));
				tp.get(0).put("expectedDate", expectedDate);
				try {
					String expectedEndDate = DateUtil.getRelativeDate(
							expectedDate,
							0,
							Integer.parseInt((String) tp.get(0).get(
									"payApplyTerm")), -1);
					tp.get(0).put("expectedEndDate", expectedEndDate);
					String accountOpenBank = (String) tp.get(0).get(
							"accountOpenBank");
					accountOpenBank = loanPosBusinessCodeBiz.getItemNameByNo(
							"BankNo", accountOpenBank);
					tp.get(0).put("accountOpenBank", accountOpenBank);
				} catch (ParseException e) {
					logger.error("期限类型转换错误", e);
				}
			}
			out.print(JSON.toJSONString(tp.get(0)));
		}
		return null;
	}

	@RequestMapping("/modelResultImport")
	public ModelAndView modelResultImport() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(CreditApplyConstants.MODEL_RESULT_IMPORT);
		return mav;
	}

	/**
	 * 上传模型结果文件
	 */
	@RequestMapping("/uploadModelResult")
	public ModelAndView uploadModelResult(
			@RequestParam(value = "uploadFile", required = false) MultipartFile multipartFile)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(CreditApplyConstants.MODEL_RESULT_IMPORT);
		if (multipartFile.isEmpty()) {
			mav.addObject("result", "文件不能为空");
			return mav;
		}
		String fileName = multipartFile.getOriginalFilename();
		File file = new File(fileName);
		boolean bol = false;
		try {
			multipartFile.transferTo(file);
			bol = riskCheckBiz.uploadModelResult(file);
		} catch (Exception e) {
			logger.error("上传文件出错。");
		} finally {
			if (file.exists()) {
				file.delete();
			}
		}

		if (bol) {
			mav.addObject("result", "上传成功");
		} else {
			mav.addObject("result", "上传失败,您输入的excel格式不正确");
		}
		return mav;
	}

//	@RequestMapping("/bizDataPageNav")
//	public ModelAndView bizDataPageNav() {
//		ModelAndView mav = new ModelAndView();
//		mav.setViewName(CreditApplyConstants.BIZ_DATA_PAGE);
//		return mav;
//	}
//
//	@RequestMapping("/exportBizData")
//	public ModelAndView exportBizData(HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		Map<String, Object> reqMap = Maps.newHashMap();
//		if (StringUtil.isNotEmpty(request.getParameter("beginDate"))) {
//			reqMap.put("beginDate", request.getParameter("beginDate"));
//		}
//		if (StringUtil.isNotEmpty(request.getParameter("endDate"))) {
//			reqMap.put("endDate", request.getParameter("endDate"));
//		}
//		if (StringUtil.isNotEmpty(request.getParameter("prodId"))) {
//			reqMap.put("prodId", request.getParameter("prodId"));
//		}
//		if (StringUtil.isNotEmpty(request.getParameter("channel"))) {
//			reqMap.put("channel", request.getParameter("channel"));
//		}
//		// reqMap.put("applyStatus", "20"); //目前只导出审核通过的数据
//		// reqMap.put("apprState", "20");
//		List<Map<String, Object>> list = loanPosCreditApplyService
//				.getBizData(reqMap);
//		OutputStream outputStream = null;
//		InputStream inStream = null;
//		File file = null;
//
//		try {
//			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
//			String fileName = sdf1.format(new Date());
//			file = new File(request.getRealPath("/") + fileName + ".xls");
//			ExcelExportUtil excelExportUtil = new ExcelExportUtil(
//					channelDictionary, provinceList, cities,
//					loanPosCreditApplyService);
//			excelExportUtil.exportBizData(list, file);
//			inStream = new FileInputStream(file);
//
//			response.setContentType("application/vnd.ms-excel");
//			response.setCharacterEncoding("UTF-8");
//			response.addHeader("Content-Disposition", "attachment; filename=\""
//					+ fileName + ".xls\"");
//			byte[] b = new byte[100];
//			int len;
//			outputStream = response.getOutputStream();
//			while ((len = inStream.read(b)) > 0) {
//				outputStream.write(b, 0, len);
//			}
//			outputStream.flush();
//		} catch (Exception e) {
//			logger.error("导出excel文件失败", e);
//		} finally {
//			if (inStream != null) {
//				inStream.close();
//			}
//			if (outputStream != null) {
//				outputStream.close();
//			}
//			if (file.exists()) {
//				file.delete();
//			}
//		}
//		return null;
//	}

	@RequestMapping("/downloadCDC")
	public ModelAndView downsLoadCDC() {
		ModelAndView maView = new ModelAndView();
		maView.setViewName("download/downloadCDC");
		return maView;
	}

	@RequestMapping("/synFileUM")
	public ModelAndView synFileUM() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("bizChannel", channelDictionary);
		mav.setViewName("synFile/synFileUMNavi");
		return mav;
	}
	@RequestMapping("/synFileSummary")
	public ModelAndView synFileSummary() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("bizChannel", channelDictionary);
		mav.setViewName("synFile/synFileSummaryNavi");
		return mav;
	}
    @RequestMapping("/queryActivityDimNavi")
    public ModelAndView queryActivityDimNavi(){
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("activity/queryActivityDimNavi");
    	return mav;
    }
    
    @RequestMapping("/queryActivityContentNavi")
    public ModelAndView queryActivityContentNavi(){
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("activity/queryActivityContentNavi");
    	return mav;
    }
    
    @RequestMapping("/queryActivityNavi")
    public ModelAndView queryActvityNavi(){
    	StringBuffer stringBuffer = new StringBuffer();
		Map<String, Object> reqMap = Maps.newHashMap();
		reqMap.put("actFlag", "0");
		List<TActivityDimInfo> dimList = activityBiz.getTActivityDimInfos(reqMap);
		List<TActivityContentInfo> contentList = activityBiz.geTActivityContentInfos(reqMap);
		for(TActivityDimInfo info: dimList){
			stringBuffer.append("dim"+info.getId()+":"+info.getDimName()+"|");
		}
		for(TActivityContentInfo info: contentList){
			stringBuffer.append("content"+info.getId()+":" +info.getContentName()+"|");
		}
		stringBuffer.replace(stringBuffer.lastIndexOf("|"), stringBuffer.length(), "");
    	ModelAndView mav = new ModelAndView();
    	mav.addObject("selectParam", stringBuffer.toString());
    	mav.setViewName("activity/queryActivityNavi");
    	return mav;
    }

    @RequestMapping("/dataTrans")
    public ModelAndView dataTransInit(){
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("dataTrans/dataTrans");
    	return mav;
    }
    
    /**
     * 展业机构
     * 
     * @return
     */
    @RequestMapping("/bdInstitutionNav")
    public ModelAndView bdInstitutionNav(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("bdManagement/bdInstitutionNav");
        return mav;
    }
    
    /**
     * 展业人员
     * 
     * @return
     */
    @RequestMapping("/bdExecutorNav")
    public ModelAndView bdExecutorNav(){
        ModelAndView mav = new ModelAndView();
        List<TBdInstitution> institutions = bdManagementService.queryTbdInstitutions();
        TBdInstitution tBdInstitution = new TBdInstitution();
        tBdInstitution.setOrgId(0);
        tBdInstitution.setAlias("其他");
        institutions.add(tBdInstitution);
        mav.addObject("institutions", institutions);
        mav.setViewName("bdManagement/bdExecutorNav");
        return mav;
    }
   
    /*
     *模型参数配置1 
     * 
    */
    @RequestMapping("/paramconfig1")
    public ModelAndView paramconfig1(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("provinceList", provinceList);
        mav.addObject("regionRiskTypeList", regionRiskTypeList);
        mav.setViewName("paramconfig/riskzoneConfigNav");
        return mav;
    }
    
    /*
     *模型参数配置2 
     * 
    */
    @RequestMapping("/paramconfig2")
    public ModelAndView paramconfig2(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("channelDictionary", channelDictionary);
        mav.setViewName("paramconfig/riskchannelConfigNav");
        return mav;
    }
    
    /*
     *模型参数配置3 
     * 
    */
    @RequestMapping("/paramconfig3")
    public ModelAndView paramconfig3(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("channelRiskTypeList", channelRiskTypeList);
        mav.addObject("productNoList", productNoList);
        mav.setViewName("paramconfig/approvingAmountRateUplimitConfigNav");
        return mav;
    }
    
    /*
     *模型参数配置4 
     * 
    */
    @RequestMapping("/paramconfig4")
    public ModelAndView paramconfig4(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("paramconfig/approvingRateUplimitConfigNav");
        return mav;
    }
    
    /*
     *模型参数配置5 
     * 
    */
    @RequestMapping("/paramconfig5")
    public ModelAndView paramconfig5(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("yesNoList", yesNoList);
        mav.setViewName("paramconfig/approvingScorecutUplimitConfigNav");
        return mav;
    }
    
    /*
     *模型参数配置6 
     * 
    */
    @RequestMapping("/paramconfig6")
    public ModelAndView paramconfig6(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("productNoList", productNoList);
        mav.setViewName("paramconfig/approvedRulesConfigNav");
        return mav;
    }
    
    
    
    @RequestMapping("/queryChannelFund")
    public ModelAndView queryChannelFund(HttpServletRequest request){
    	ModelAndView  mav = new ModelAndView();
    	mav.setViewName("channelpool/queryChannlePoolFundNavi");
    	return mav;
    }
    
    @RequestMapping("addInfoMessage")
    public ModelAndView addInfoMessage(){
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("addMessageInfo/addMessageInfo");
    	return mav;
    }
    
    @RequestMapping("createSmsTemplate")
    public ModelAndView createSmsTemplate(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("sms/smsTemplate");
        return mav;
    }
    
  /**
   * 跳转到代扣页面
   * 
   * @return
   */
  @RequestMapping("/collectionPage")
  public ModelAndView collectionPage() {
      ModelAndView mav = new ModelAndView();
      mav.setViewName("collection/collectionPage");
      return mav;
  }
    /**
     * 
     * @param cardNo 银行卡号
     * @param cardType 开户行
     * @param paperId 身份证号
     * @param name 姓名
     * @param amt 金额
     * @param payChannel 支付渠道
     * @param out
     * @return
     */
    @RequestMapping("/collection")
    public ModelAndView collection(@RequestParam(value = "cardNo", required = true) String cardNo,
                                   @RequestParam(value = "cardType", required = true) String cardType,
                                   @RequestParam(value = "paperId", required = true) String paperId,
                                   @RequestParam(value = "name", required = true) String name,
                                   @RequestParam(value = "amt", required = true) String amt,
                                   @RequestParam(value = "payChannel", required = true) String payChannel,
                                   PrintWriter out) {
        PayChannel.CPCN.getValue();
        // 请求
        SingleCollectRequest request = new SingleCollectRequest();
        request.setProductCode(ProductCode.CASH_FLOW.getValue());//产品编号
        request.setChannelNo(payChannel);//支付渠道
        request.setAccName((String) name);//账户名
        request.setAccNum((String) cardNo);//账号
        request.setAccType(AccountType.DEBIT.getValue());//银行卡类型
        request.setCertType(CertType.CIVI_ID_CARD.getValue());//证件类型
        request.setCertNo((String) paperId);//证件号
        request.setTransAmt(amt);//代扣金额
        request.setTransDate(DateUtil.getNowTime());
            Map<String, Object> bankReqMap = Maps.newHashMap();
            bankReqMap.put("type", "BankInfo");
            bankReqMap.put("channel", payChannel);
            bankReqMap.put("innerCode", cardType);
            List<Map<String, Object>> bankRespMap = channelMapperDictionaryService.getCode(bankReqMap);
            if (!bankRespMap.isEmpty() && bankRespMap.size() > 0) {
                String outerCode = (String) bankRespMap.get(0).get("outerCode");
                request.setBankCode(outerCode);
            } else {
                logger.info("bankRespMap为空或者长度为0");
            }

        //执行
        logger.info("开始代收，请求request=" + request);
        SingleCollectResponse response;
        try {
            response = payCollectQueryHessian.singleCollect(request);
            logger.info("完成代收,返回response=" + response);
            JSONObject json = new JSONObject();
            json.put("status", response.getStatus());
            json.put("resultMsg", response.getRespMessage());
            out.print(json.toJSONString());
            return null;
        } catch (Exception e) {
            logger.error("代扣异常", e);
            JSONObject json = new JSONObject();
            json.put("status", "EXCEPTION");
            json.put("resultMsg", "系统异常");
            out.print(json.toJSONString());
            return null;
        }
    }
}
