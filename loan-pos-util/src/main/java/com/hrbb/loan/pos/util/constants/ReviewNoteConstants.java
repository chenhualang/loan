/**
 * 
 *哈尔滨银行
 * Copyright (c) 2007-2015 HRBB,Inc.All Rights Reserved.
 */
package com.hrbb.loan.pos.util.constants;

import com.google.common.collect.ImmutableSet;


/**
 * 
 * @author marco
 * @version $Id: ReviewNoteConstants.java, v 0.1 2015-3-9 上午9:53:51 marco Exp $
 */
public class ReviewNoteConstants {
	// 业务渠道
	public static final String CHANNEL_CODE_UP = "UP";
	public static final String CHANNEL_NAME_UP = "银联";
	public static final String CHANNEL_CODE_YB = "YB";
	public static final String CHANNEL_NAME_YB = "易宝";
	public static final String CHANNEL_CODE_KQ = "KQ";
	public static final String CHANNEL_NAME_KQ = "快钱";
	public static final String CHANNEL_CODE_SQ = "SQ";
	public static final String CHANNEL_NAME_SQ = "商圈";
	public static final String CHANNEL_CODE_UM = "UM";
	public static final String CHANNEL_NAME_UM = "银商";
	public static final String CHANNEL_CODE_AP = "AP";
	public static final String CHANNEL_NAME_AP = "APP";
	public static final String CHANNEL_CODE_PT = "PT";
	public static final String CHANNEL_NAME_PT = "Portal";
	public static final String CHANNEL_CODE_YS = "YS";
	public static final String CHANNEL_NAME_YS = "银盛";
	public static final String CHANNEL_CODE_ZY = "ZY";
	public static final String CHANNEL_NAME_ZY = "展业（下发）";
	public static final String CHANNEL_CODE_ZJ = "ZJ";
	public static final String CHANNEL_NAME_ZJ = "展业（自建）";
	public static final String CHANNEL_CODE_NA = "NA";
	public static final String CHANNEL_NAME_NA = "无";
	public static final String CHANNEL_CODE_58 = "58";
	public static final String CHANNEL_NAME_58 = "58金融";
	public static final String CHANNEL_CODE_ZW = "ZW";
	public static final String CHANNEL_NAME_ZW = "北龙中网";
	public static final String CHANNEL_CODE_HC = "HC";
	public static final String CHANNEL_NAME_HC = "慧聪";
	// 申请状态
	public static final String APPLYSTATUS_CODE_00 = "00";
	public static final String APPLYSTATUS_CODE_10 = "10";
	public static final String APPLYSTATUS_CODE_20 = "20";
	public static final String APPLYSTATUS_CODE_21 = "21";
	public static final String APPLYSTATUS_CODE_30 = "30";
	public static final String APPLYSTATUS_CODE_31 = "31";
	public static final String APPLYSTATUS_CODE_33 = "33";// 风险复审第2阶段
	public static final String APPLYSTATUS_CODE_34 = "34";// 风险复审第3阶段
	public static final String APPLYSTATUS_CODE_35 = "35";// 风险复审第4阶段
	public static final String APPLYSTATUS_CODE_32 = "32";
	public static final String APPLYSTATUS_CODE_40 = "40";
	public static final String APPLYSTATUS_CODE_60 = "60";
	public static final String APPLYSTATUS_CODE_41 = "41";
	public static final String APPLYSTATUS_CODE_90 = "90";
	public static final String APPLYSTATUS_CODE_91 = "91";
	public static final String APPLYSTATUS_CODE_92 = "92";
	public static final String APPLYSTATUS_CODE_93 = "93";
	public static final String APPLYSTATUS_TITLE_CODE_10 = "申请受理意见";
	public static final String APPLYSTATUS_TITLE_CODE_20 = "资料审核意见";
	public static final String APPLYSTATUS_TITLE_CODE_30 = "风险初审意见";
	public static final String APPLYSTATUS_TITLE_CODE_31 = "风险复审意见";
	public static final String APPLYSTATUS_TITLE_CODE_40 = "尽调审核意见";
	// 数据字典的代码编号
	public static final String BD_CODENO_APPLYSTATUS = "ApplyStatus";// 申请状态
	// 审批意见
	public static final String APPRRESULT_CODE_10 = "10";// 同意
	public static final String APPRRESULT_CODE_20 = "20";// 不同意
	public static final String APPRRESULT_CODE_30 = "30";// 回退前手
	public static final String APPRRESULT_CODE_31 = "31";// 退回补件
	public static final String APPRRESULT_CODE_40 = "40";// 尽职调查
	public static final String APPRRESULT_CODE_50 = "50";// 下一阶段
	// 拒绝代码
	public static final String REFUSE_CODE_901 = "901";// 模型自动拒绝
	// 权限
	public static final String ROLE_INFO = "ROLE_INFO;";// 资料录入
	public static final String ROLE_CODE_0 = "0";// 资料录入
	public static final String ROLE_NAME_0 = "资料录入 ";// 资料录入
	public static final String ROLE_INFO_APPR = "ROLE_INFO_APPR;";// 资料审核
	public static final String ROLE_CODE_1 = "1";// 资料审核
	public static final String ROLE_NAME_1 = "资料录入审批 ";// 资料审核
	// public static final String ROLE_AUDIT_ADMIN = "ROLE_AUDIT_ADMIN;";// 风险复审
	public static final String ROLE_APPR_LV1 = "ROLE_APPR_LV1;";// 风险复审
	public static final String ROLE_APPR_LV2 = "ROLE_APPR_LV2;";// 风险复审
	public static final String ROLE_APPR_LV3 = "ROLE_APPR_LV3;";// 风险复审
	public static final String ROLE_APPR_LV4 = "ROLE_APPR_LV4;";// 风险复审
	public static final String PRIVILEGE_ROLE_APPR_LV1 = "ROLE_APPR_LV1";// 风险复审
    public static final String PRIVILEGE_ROLE_APPR_LV2 = "ROLE_APPR_LV2";// 风险复审
    public static final String PRIVILEGE_ROLE_APPR_LV3 = "ROLE_APPR_LV3";// 风险复审
    public static final String PRIVILEGE_ROLE_APPR_LV4 = "ROLE_APPR_LV4";// 风险复审
	public static final String ROLE_CODE_2 = "2";// 风险复审
	public static final String ROLE_NAME_2 = "复审管理员 ";// 复审管理员
	public static final String ROLE_DUE_DILI = "ROLE_DUE_DILI;";// 尽调复审
	public static final String ROLE_CODE_3 = "3";// 尽调复审
	public static final String ROLE_NAME_3 = "尽调管理员";// 尽调管理员
	public static final String ROLE_ADMIN = "ROLE_ADMIN;";// 管理员
	public static final String ROLE_CODE_9 = "9";// 管理员
	public static final String ROLE_SYSTEM_ID = "system";// 管理员
	// 操作标志位
	// 查询
	public static final int OPERATION_QUERY_UNDO = 0;// 待处理
	public static final int OPERATION_QUERY_MYDO = 1;// 处理中（我的工作）
	public static final int OPERATION_QUERY_MYCOMPLETE = 9;// 已完成
	// 字符串分割符号
	public static final String STRING_SEPARATOR_CODE = "[|]";
	public static final String STRING_SEPARATOR_CODE_CONCAT = "|";
	public static final String STRING_SEPARATOR_KEY = ":";
	// 批复编号索引
	public static final String APPROVE_ID_INDEX = "AR";
	// 批复状态
	public static final String APPROVE_STATUS_CODE_01 = "01";// 01-未确认
	public static final String APPROVE_STATUS_CODE_02 = "02";// 02-已确认
	// 银行流水表map的key
	public static final String CUST_ID = "custId";
	public static final String CUST_NAME = "custName";
	public static final String LOAN_ID = "loanId";
	public static final String APPLYSTATUS = "applyStatus";
	public static final String CHANNEL = "channel";
	public static final String LOANTYPE = "loanType";
	// 补件阶段前缀命名
	public static final String MESSAGETYPE_NEEDMOREEVID_PREFIX = "APP";
	public static final String MESSAGETYPE_APPROVE = "1";
	public static final String MESSAGETYPE_REJECT = "2";
	public static final String MESSAGETYPE_NEEDMOREEVID = "3";
	// 风险预测
	public static final String RISKDETECTIO_MAP_KEY_LOAN_ID = "loanId";
	public static final String RISKDETECTIO_MAP_KEY_CUSTNAME = "custName";
	public static final String RISKDETECTIO_MAP_KEY_LEGALPERSONNAME = "legalPersonName";
	public static final String RISKDETECTIO_MAP_KEY_CHANNEL = "channel";
	public static final String RISKDETECTIO_MAP_KEY_BEGINDATE = "beginDate";
	public static final String RISKDETECTIO_MAP_KEY_REGISTDATE = "registDate";
	public static final String RISKDETECTIO_MAP_KEY_PAPERID = "paperId";
	public static final String RISKDETECTIO_MAP_KEY_MATEPAPERID = "matePaperId";
	public static final String RISKDETECTIO_MAP_KEY_REGICODE = "regiCode";
	public static final String RISKDETECTIO_MAP_KEY_MOBILEPHONE = "mobilePhone";
	public static final String RISKDETECTIO_MAP_KEY_MATEMOBILEPHONE = "mateMobilephone";
	public static final String RISKDETECTIO_MAP_KEY_POSCUSTBUSISCOPE = "posCustBusiScope";
	public static final String RISKDETECTIO_MAP_KEY_TEL = "tel";
	public static final String RISKDETECTIO_MAP_KEY_INDUSTRYTYPEID = "industryTypeId";
	public static final String RISKDETECTIO_MAP_KEY_INDUSTRYTYPEID2 = "industryTypeId2";
	public static final String RISKDETECTIO_MAP_KEY_POSCUSTADDRESS = "posCustAddress";
	public static final String RISKDETECTIO_MAP_KEY_POSCUSTNAME = "posCustName";
	public static final String RISKDETECTIO_MAP_KEY_OPERADDRCODE = "operAddrCode";
	public static final String RISKDETECTIO_MAP_KEY_RESIDENTDETAIL = "residentDetail";
	public static final String RISKDETECTIO_MAP_KEY_BUSIYEAR = "busiYear";
	public static final String RISKDETECTIO_MAP_KEY_FAMILYCUSTNAME = "familyCustName";
	public static final String RISKDETECTIO_MAP_KEY_BIRTDATE = "birtDate";
	public static final String RISKDETECTIO_KEY_CHECKINFOR = "checkInfor";
	public static final String RISKDETECTIO_KEY_CHECKREZULT = "checkRezult";
	public static final String RISKDETECTIO_KEY_NOTICEINFOR = "noticeInfor";
	public static final String RISKDETECTIO_VULUE_CHECKREZULT_0 = "0";// 通过
	public static final String RISKDETECTIO_VULUE_CHECKREZULT_1 = "1";// 提示
	public static final String RISKDETECTIO_VULUE_CHECKREZULT_9 = "9";// 拦截
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_1 = "申请人是否企业法人";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_2 = "企业经营年限是否符合准入";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_3 = "是否重复申请";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_3_CONTINUELENDING = "该笔申请是否续贷";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_4 = "电话号码多人使用";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_5 = "疑似限入行业";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_6 = "限制性商户校验";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_7 = "限制性行业校验";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_8 = "江浙银联建材校验";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_9 = "泰顺建材";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_10 = "申请人信用历史校验";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_11 = "申请人信用记录校验";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_12 = "申请人授信金额校验";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_13 = "申请人逾期笔数校验";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_14 = "申请人贷款逾期月份数校验";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_15 = "申请人贷记卡逾期月份数校验";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_16 = "申请人近6月查询数校验";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_17 = "申请人贷记卡额度使用率校验";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_18 = "申请人贷款逾期校验";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_19 = "申请人贷记卡逾期校验";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_20 = "申请人贷款展期校验";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_21 = "申请人负债非正常状态校验";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_22 = "申请人贷款最长逾期月份数校验";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_23 = "申请人贷记卡最长逾期月份数校验";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_24 = "申请人电话校验";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_25 = "民间借贷校验";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_26 = "近1年POS月份数校验";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_27 = "近6月POS月份数校验";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_28 = "最近1月POS流水校验";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_29 = "POS月均流水校验";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_30 = "申请人黑名单校验";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_31 = "成都风险探测";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_32 = "北京风险探测";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_34 = "从业年限探测";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_35 = "信用卡逾期情况";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_36 = "POS流水准入判断";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_37 = "疑似地区限入";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_38 = "征信负债情况探测";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_39 = "征信查询次数探测";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_40 = "配偶信息重复性校验";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_41 = "配偶信息一致性校验";
	public static final String RISKDETECTIO_VULUE_CHECKINFOR_42 = "配偶电话重复性校验";
	public static final String RISKDETECTIO_VULUE_NOTICEINFOR_X = "X";
	public static final String RISKDETECTIO_VULUE_NOTICEINFOR_1_NULL = "缺少企业法人信息";
	public static final String RISKDETECTIO_VULUE_NOTICEINFOR_1 = "申请人必须为企业法人";
	public static final String RISKDETECTIO_VULUE_NOTICEINFOR_2_NULL = "缺少企业经营年限信息";
	public static final String RISKDETECTIO_VULUE_NOTICEINFOR_2 = "企业不满足经营年限准入条件";
	public static final String RISKDETECTIO_VULUE_NOTICEINFOR_3_NULL = "缺少营业执照号码信息";
	public static final String RISKDETECTIO_VULUE_NOTICEINFOR_3 = "重复申请，同一X第X次申请。上次申请受理日期为X，审批结果为X。";
	public static final String RISKDETECTIO_VULUE_NOTICEINFOR_3_CONTINUELENDING_0 = "是";
	public static final String RISKDETECTIO_VULUE_NOTICEINFOR_3_CONTINUELENDING_1 = "否";
	public static final String RISKDETECTIO_VULUE_NOTICEINFOR_3_1 = "申请人";
	public static final String RISKDETECTIO_VULUE_NOTICEINFOR_3_2 = "家庭";
	public static final String RISKDETECTIO_VULUE_NOTICEINFOR_3_3 = "商户";
	public static final String RISKDETECTIO_VULUE_NOTICEINFOR_4_1 = "手机号码与他人重复（X的手机号码）";
	public static final String RISKDETECTIO_VULUE_NOTICEINFOR_4_2 = "办公电话与他人重复（X的办公电话）";
	public static final String RISKDETECTIO_VULUE_NOTICEINFOR_5_NULL = "缺少申请人所处行业信息";
	public static final String RISKDETECTIO_VULUE_NOTICEINFOR_5 = "申请人所处行业疑似限入行业";
	public static final String RISKDETECTIO_VULUE_NOTICEINFOR_6 = "申请人疑似我行慎贷客户：常州东南陶瓷城";
	public static final String RISKDETECTIO_VULUE_NOTICEINFOR_7_NULL = "缺少行内行业分类信息";
	public static final String RISKDETECTIO_VULUE_NOTICEINFOR_7 = "申请人疑似我行慎贷行业：制造，石材行业及II类建材";
	public static final String RISKDETECTIO_VULUE_NOTICEINFOR_8 = "申请人疑似我行慎贷行业：江浙银联建材";
	public static final String RISKDETECTIO_VULUE_NOTICEINFOR_9 = "申请人疑似我行慎贷行业：泰顺建材";
	public static final String RISKDETECTIO_VULUE_NOTICEINFOR_24 = "申请人和配偶电话号码相同";
	public static final String RISKDETECTIO_VULUE_NOTICEINFOR_30 = "申请人命中黑名单";
	public static final String RISKDETECTIO_VULUE_NOTICEINFOR_31 = "补件资料不完整，共补件X件，缺X件。";
	public static final String RISKDETECTIO_VULUE_NOTICEINFOR_34 = "从业年限不足";
	public static final String RISKDETECTIO_VULUE_NOTICEINFOR_36 = "POS流水不满足准入条件";
	public static final String RISKDETECTIO_VULUE_NOTICEINFOR_37 = "申请人经营地疑似限入地区";
	public static final String RISKDETECTIO_VULUE_NOTICEINFOR_40 = "申请人配偶证件号与其他申请人配偶相同";
	public static final String RISKDETECTIO_VULUE_NOTICEINFOR_41 = "申请人配偶证件号与其他申请人相同，但姓名不同";
	public static final String RISKDETECTIO_VULUE_NOTICEINFOR_42 = "申请人配偶手机号与他人相同";
	public static final String RISKDETECTIO_CHECK_POSCUSTBUSISCOPE_01 = "01";
	public static final String RISKDETECTIO_CHECK_POSCUSTBUSISCOPE_04 = "04";
	public static final String RISKDETECTIO_CHECK_POSCUSTBUSISCOPE_07 = "07";
	public static final String RISKDETECTIO_CHECK_POSCUSTBUSISCOPE_11 = "11";
	public static final String RISKDETECTIO_CHECK_POSCUSTBUSISCOPE_12 = "12";
	public static final String RISKDETECTIO_CHECK_POSCUSTBUSISCOPE_13 = "13";
	public static final String RISKDETECTIO_CHECK_INDUSTRYTYPEID_K = "K";
	public static final String RISKDETECTIO_CHECK_POSCUSTADDRESS_1 = "中吴大道";
	public static final String RISKDETECTIO_CHECK_POSCUSTADDRESS_2 = "东南陶瓷城";
	public static final String RISKDETECTIO_CHECK_POSCUSTADDRESS_3 = "天宁区雕庄";
	public static final String RISKDETECTIO_CHECK_POSCUSTNAME_1 = "天宁区雕庄";
	public static final String RISKDETECTIO_CHECK_POSCUSTNAME_2 = "石材";
	public static final String RISKDETECTIO_CHECK_POSCUSTNAME_3 = "石板";
	public static final String RISKDETECTIO_CHECK_POSCUSTNAME_4 = "石料";
	public static final String RISKDETECTIO_CHECK_POSCUSTNAME_5 = "大理石";
	public static final String RISKDETECTIO_CHECK_POSCUSTNAME_6 = "水泥";
	public static final String RISKDETECTIO_CHECK_POSCUSTNAME_7 = "铝合金";
	public static final String RISKDETECTIO_CHECK_POSCUSTNAME_8 = "涂料";
	public static final String RISKDETECTIO_CHECK_POSCUSTNAME_9 = "管材";
	public static final String RISKDETECTIO_CHECK_POSCUSTNAME_10 = "钢材";
	public static final String RISKDETECTIO_CHECK_POSCUSTNAME_11 = "生产";
	public static final String RISKDETECTIO_CHECK_INDUSTRYTYPEID2_01 = "01";
	public static final String RISKDETECTIO_CHECK_INDUSTRYTYPEID2_04 = "04";
	public static final String RISKDETECTIO_CHECK_OPERADDRCODE_32 = "32";// 江苏省
	public static final String RISKDETECTIO_CHECK_OPERADDRCODE_33 = "33";// 浙江省
	public static final String RISKDETECTIO_CHECK_OPERADDRCODE_43 = "43";// 湖南
	public static final String RISKDETECTIO_CHECK_OPERADDRCODE_46 = "46";// 海南
	public static final String RISKDETECTIO_CHECK_OPERADDRCODE_52 = "52";// 贵州
	public static final String RISKDETECTIO_CHECK_OPERADDRCODE_53 = "53";// 云南
	public static final String RISKDETECTIO_CHECK_OPERADDRCODE_62 = "62";// 甘肃
	public static final String RISKDETECTIO_CHECK_OPERADDRCODE_63 = "63";// 青海
	public static final String RISKDETECTIO_CHECK_OPERADDRCODE_64 = "64";// 宁夏
	public static final String RISKDETECTIO_CHECK_PAPERKIND = "330329";
	// ftp下载文件
	public static final String FTP_RESP_CODE_000 = "000";
	public static final String FTP_RESP_MSG_999 = "上传文件处理失败，请检查上传文件，重新上传。";
	// 外呼任务
	public static final String CALLING_TASK_KEY_PREFIX = "CT";
	public static final String CALLING_TASK_CALLINGTYPE_01 = "01";
	public static final String CALLING_TASK_CALLINGTYPE_05 = "05";
	public static final String CALLING_TASK_CALLINGTYPE_11 = "11";
	public static final String CALLING_TASK_CALLINGTYPE_12 = "12";
	public static final String CALLING_TASK_CALLINGTYPE_14 = "14";
	public static final String CALLING_TASK_RELABIZPHASE_APP = "APP";
	public static final String CALLING_TASK_RELABIZPHASE_APR = "APR";
	public static final String CALLING_TASK_RELABIZPHASE_ISS = "ISS";
	// 征信报告
	public static final String CREDIT_REPORT_MAP_KEY_CERTNO = "certNo";// 被查询者证件号码
	public static final String CREDIT_REPORT_MAP_KEY_CERTTYPE = "certType";// 被查询者证件类型
	public static final String CREDIT_REPORT_MAP_VALUE_CERTTYPE_0 = "身份证";// 身份证
	public static final int CREDIT_REPORT_RESULT_VALUE_0 = 0;// 征信报告正常
	public static final int CREDIT_REPORT_RESULT_VALUE_1 = 1;// 需要征信报告
	public static final int CREDIT_REPORT_RESULT_VALUE_2 = 2;// 征信报告不完整
	public static final int CREDIT_REPORT_RESULT_VALUE_3 = 3;// 征信报告没有身份信息
	public static final int CREDIT_REPORT_RESULT_VALUE_4 = 4;// 查询时间小于20天，询问
	public static final int CREDIT_REPORT_RESULT_VALUE_5 = 5;// 申请人姓名与征信报告不一致
	public static final String CREDIT_REPORT_COMPLETEFLAG_0 = "0";// 征信记录入库是否完整标识
																	// 0代表完整
																	// 1代表不完整
	// 风险初审
	public static final String RISK_CHECK_REZULT_0 = "0";// 风险初审执行结果(0;正常)
	public static final String RISK_CHECK_REZULT_1 = "1";// 风险初审执行结果(1;失败)
	public static final String RISK_CHECK_MODELVERSION = "1.5";// 最新使用风险初审模型的版本号
	public static final String N = "N";
	public static final String Y = "Y";
	public static final String SUGGEST_DILIGENCE_N = "建议不尽调";
	public static final String SUGGEST_DILIGENCE_Y = "建议尽调";
	public static final String MATCH_N = "不一致";
	public static final String MATCH_Y = "一致";
	public static final String MATCH_NULL = "人行手机为空";
	public static final String CALC_INDEX_MSG_05 = "申请人信用卡使用率较高";
	public static final String CALC_INDEX_MSG_07 = "申请人信用历史短";
	public static final String CALC_INDEX_MSG_08 = "申请人信用记录少";
	public static final String CALC_INDEX_MSG_09 = "申请人授信金额低";
	public static final String CALC_INDEX_MSG_10 = "申请人逾期笔数多";
	public static final String CALC_INDEX_MSG_11 = "申请人贷款逾期月份数多";
	public static final String CALC_INDEX_MSG_12 = "申请人贷记卡逾期月份数多";
	public static final String CALC_INDEX_MSG_13 = "申请人近6月查询数多";
	public static final String CALC_INDEX_MSG_14 = "申请人贷款现逾期";
	public static final String CALC_INDEX_MSG_15 = "申请人贷记卡现逾期";
	public static final String CALC_INDEX_MSG_16 = "申请人曾有贷款展期";
	public static final String CALC_INDEX_MSG_17 = "申请人负债非正常状态";
	public static final String CALC_INDEX_MSG_18 = "申请人贷款最长逾期月份数多";
	public static final String CALC_INDEX_MSG_19 = "申请人贷记卡最长逾期月份数多";
	public static final String CALC_INDEX_MSG_20_1 = "银联近1年POS月份数少于6";
	public static final String CALC_INDEX_MSG_20_2 = "易宝近1年POS月份数少于3";
	public static final String CALC_INDEX_MSG_20_3 = "易宝近1年POS月份数少于6";
	public static final String CALC_INDEX_MSG_20_4 = "快钱近1年POS月份数少于3";
	public static final String CALC_INDEX_MSG_20_5 = "四川烟草近1年POS月份数少于6";
	public static final String CALC_INDEX_MSG_20_6 = "四川烟草近1年POS月份数少于3";
	public static final String CALC_INDEX_MSG_21_1 = "近6月POS月份数少于4";
	public static final String CALC_INDEX_MSG_21_2 = "";
	public static final String CALC_INDEX_MSG_21_3 = "";
	public static final String CALC_INDEX_MSG_22 = "最近1月缺少POS流水记录";
	public static final String CALC_INDEX_MSG_23_1 = "银联POS月均少于2万";
	public static final String CALC_INDEX_MSG_23_2 = "快钱POS月均少于5万";
	public static final String CALC_INDEX_MSG_23_3 = "易宝POS月均少于5万";
	public static final String CALC_INDEX_MSG_23_4 = "四川烟草POS月均少于5万";
	public static final String CALC_INDEX_MSG_23 = "申请人疑似我行慎贷行业：泰顺建材";
	public static final String CALC_INDEX_MSG_24 = "可能是民间借贷";
	public static final String CALC_INDEX_MSG_28 = "该市场骗贷、包装公司猖獗";
	public static final String CALC_INDEX_MSG_29 = "建议1层额度10-50万，2层5-20万，3,4层拒绝";
	public static final String CALC_INDEX_MSG_31 = "申请人信用历史短";
	public static final String CALC_INDEX_MSG_32 = "贷记卡前6个月逾期次数大于1";
	public static final String CALC_INDEX_MSG_33 = "征信负债情况不满足准入";
	public static final String CALC_INDEX_MSG_34 = "征信查询情况不满足准入";
	// 流量贷
	public static final String POS_LOAN_ID = "1001020306";
	public static final String FLOW_LOAN_ID = "1001020305";
	public static final String FLOW_LOAN_NAME = "流量贷";
	// posKind
	public static final String POSKIND_1 = "1";
	public static final String POSKIND_4 = "4";
	// 交易类型=消费
	public static final String TRADETYPE_CONSUMPTION = "消费";
	// 信贷审批查询记录明细.查询原因
	public static final String REASON_LOAN = "贷款审批";
	public static final String REASON_CREDITCARD = "信用卡审批";
	// 贷款方式
	public static final String LOANTYPE_01 = "01";// 自营
	public static final String LOANTYPE_02 = "02";// 资金池
	
	//资金池模式
	public static final ImmutableSet<String> fundPoolSet = new ImmutableSet.Builder<String>().add("58").add("SM").add("RN").build();
	
	//是否有还款总期次这一字段
	public static final ImmutableSet<String> hasTermColumnSet = new ImmutableSet.Builder<String>().add("SM").build();
	
	//是否有下一可贷日
	public static final ImmutableSet<String> hasNextLoanDateSet = new ImmutableSet.Builder<String>().add("58").add("HC").build();
}
