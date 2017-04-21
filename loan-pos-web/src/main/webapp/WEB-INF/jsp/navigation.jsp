<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head id="Head1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>哈尔滨银行互联网金融业务管理系统</title>
    <link rel="icon" href="favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
    <link href="<%=request.getContextPath()%>/css/default.css" rel="stylesheet" type="text/css" />

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/themes/icon.css" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src='<%=request.getContextPath()%>/js/XiuCai.index.js'> </script>
	<script type="text/javascript" src='<%=request.getContextPath()%>/js/locale/easyui-lang-zh_CN.js'></script>
	<script type="text/javascript" src='<%=request.getContextPath()%>/js/common_uiext.js'></script>

    <script type="text/javascript">
    <% //String privileges = (String)session.getAttribute("assignedPrivileges"); 
    com.hrbb.loan.web.security.entity.AccessPrivilege access = (com.hrbb.loan.web.security.entity.AccessPrivilege)session.getAttribute("accessPrivilege");
    %>
	var _menus = {
	"menus": [{
		"menuid": "9",
		"menuname": "我的工作台",
		"icon": "icon-min-edit",
		"menus": [{
			"menuid": "91",
			"menuname": "我的任务",
			"icon": "icon-edit",
			"url": "<%=request.getContextPath()%>/workbench/myTasks.do?"
		}]
		},
		<%if (access.hasAnyPrivilege("ROLE_APPLY;ROLE_INFO;ROLE_APPLY_QUERY;ROLE_APPLY_VIEWALL;ROLE_AUDIT_ADMIN")) {%>
		{
		"menuid": "1",
		"icon": "icon-tip",
		"menuname": "授信申请管理",
		"menus": [
			<%if(access.hasAnyPrivilege("ROLE_APPLY;ROLE_INFO;")){		//申请管理权限%>
		    {
				"menuid": "11",
				"menuname": "待处理申请",
				"icon": "icon-min-edit",
				"url": "<%=request.getContextPath()%>/navigation/queryCreditApplyNav.do?viewMode=SPC&applyStatus=00"
			},
			{
				"menuid": "12",
				"menuname": "审批中申请",
				"icon": "icon-min-edit",
				"url": "<%=request.getContextPath()%>/navigation/queryCreditApplyNav.do?viewMode=SPC&isApplyStatus=999"
			},
			{
				"menuid": "13",
				"menuname": "审批通过申请",
				"icon": "icon-min-edit",
				"url": "<%=request.getContextPath()%>/navigation/queryCreditApplyNav.do?viewMode=SPC&applyStatus=90"
			},
			{
				"menuid": "14",
				"menuname": "被否决申请",
				"icon": "icon-min-edit",
				"url": "<%=request.getContextPath()%>/navigation/queryCreditApplyNav.do?viewMode=SPC&applyStatus=92"
			},
			{
				"menuid": "15",
				"menuname": "已取消申请",
				"icon": "icon-min-edit",
				"url": "<%=request.getContextPath()%>/navigation/queryCreditApplyNav.do?viewMode=SPC&applyStatus=93"
			},
			<%} %>
			<%if(access.hasAnyPrivilege("ROLE_APPLY_QUERY;ROLE_APPLY_VIEWALL;ROLE_AUDIT_ADMIN")){		//申请管理权限%>
				{
					"menuid": "19",
					"menuname": "所有申请",
					"icon": "icon-min-edit",
					"url": "<%=request.getContextPath()%>/navigation/queryCreditApplyNav.do?viewMode=ALL"
				}
			<%}%>
			]
	},
	<%} %>
	<% if (access.hasAnyPrivilege("ROLE_INFO_APPR;ROLE_APPR_LV1;ROLE_APPR_LV2;ROLE_APPR_LV3;ROLE_ISSUE_APPR;ROLE_DUE_DILI_ADMIN")) {%>
	{
		"menuid": "2",
		"menuname": "审查审批",
		"icon": "icon-edit",
		"url": "demo.html",
		"menus": [
		 <%
		 if(access.hasAnyPrivilege("ROLE_INFO_APPR;ROLE_APPR_LV1;ROLE_APPR_LV2;ROLE_APPR_LV3")){		//授信审批权限
		 %> 
		 {
			"menuid": "21",
			"menuname": "授信审批",
			"icon": "icon-min-edit",
			"child": [{
				"menuid": "211",
				"menuname": "当前工作",
				"icon": "icon-min-edit",
				"url": "<%=request.getContextPath()%>/navigation/queryCreditApplyForReview.do?opflag=1"
			},{
				"menuid": "212",
				"menuname": "已完成工作",
				"icon": "icon-min-edit",
				"url": "<%=request.getContextPath()%>/navigation/queryCreditApplyForReview.do?opflag=9"
			}]
		},
		<%
		}
		if( access.hasAnyPrivilege("ROLE_ISSUE_APPR")){			//用款审核权限
		%>
		{
			"menuid": "22",
			"menuname": "用款审核",
			"icon": "icon-min-edit",
			"child": [{
				"menuid": "221",
				"menuname": "当前审核工作",
				"icon": "icon-min-edit",
				"url": "<%=request.getContextPath()%>/navigation/queryPaymentReview.do?reviewStatus=0"
			 },
			 {
				"menuid": "222",
				"menuname": "已完成审核工作",
				"icon": "icon-min-edit",
				"url": "<%=request.getContextPath()%>/navigation/queryPaymentReview.do?reviewStatus=1"	 
			 }]
		}
		<%
		}
		%>
		]
	},
	<%} %>
	<% if (access.hasAnyPrivilege("ROLE_CONTRACT;ROLE_APPROVED;ROLE_APPROVED_QUERY;ROLE_CONTRACT_QUERY;ROLE_APPROVED_VIEWALL;ROLE_CONTRACT_VIEWALL")) {%>
	{
		"menuid": "3",
		"icon": "icon-tip",
		"menuname": "协议签约管理",
		"menus": [
		<%if(access.hasAnyPrivilege("ROLE_APPROVED;ROLE_APPROVED_QUERY;ROLE_APPROVED_VIEWALL;")){		//批复管理
		%> 
		{
			"menuid": "31",
			"menuname": "授信审批结果",
			"icon": "icon-users",
			"child": [
			<%if(access.hasAnyPrivilege("ROLE_APPROVED;")){		//批复管理
			%> 
			{
				"menuid": "311",
				"menuname": "待确认批复",
				"icon": "icon-min-edit",
				"url": "<%=request.getContextPath()%>/navigation/queryContractManagementNav1.do?approveStatus=01"
			},{
				"menuid": "312",
				"menuname": "已确认批复",
				"icon": "icon-min-edit",
				"url": "<%=request.getContextPath()%>/navigation/queryContractManagementNav2.do?approveStatus=02"
			},
			{
				"menuid": "313",
				"menuname": "已拒绝批复",
				"icon": "icon-min-edit",
				"url": "<%=request.getContextPath()%>/navigation/queryContractManagementNav3.do?approveStatus=03"
			},
			{
				"menuid": "314",
				"menuname": "已失效批复",
				"icon": "icon-min-edit",
				"url": "<%=request.getContextPath()%>/navigation/queryContractManagementNav4.do?approveStatus=09"
			},
			<%
			}
			if(access.hasAnyPrivilege("ROLE_APPROVED_QUERY;ROLE_APPROVED_VIEWALL")){		//运营查询权限
			%>
			{
				"menuid": "319",
				"menuname": "所有批复",
				"icon": "icon-min-edit",
				"url": "<%=request.getContextPath()%>/navigation/queryContractManagementNav1.do?approveStatus=99"
			}
			<%} %>
			]
		},
		<%
		}
		if(access.hasAnyPrivilege("ROLE_CONTRACT;ROLE_CONTRACT_QUERY;ROLE_CONTRACT_VIEWALL")){		//协议管理
		%>
		{
			"menuid": "32",
			"menuname": "协议管理",
			"icon": "icon-edit",
			"child": [
			<%
			if(access.hasAnyPrivilege("ROLE_CONTRACT")){		//协议管理
			%>
			{
				"menuid": "321",
				"menuname": "待处理协议",
				"icon": "icon-min-edit",
				"url": "<%=request.getContextPath()%>/navigation/queryAgreeMentNavi.do?agreementStatus=06"
			},{
				"menuid": "322",
				"menuname": "已生效协议",
				"icon": "icon-min-edit",
				"url": "<%=request.getContextPath()%>/navigation/queryAgreeMentNavi.do?agreementStatus=01"
			},
			{
				"menuid": "323",
				"menuname": "已冻结协议",
				"icon": "icon-min-edit",
				"url": "<%=request.getContextPath()%>/navigation/queryAgreeMentNavi.do?agreementStatus=02"
			},
			{
				"menuid": "324",
				"menuname": "已失效协议",
				"icon": "icon-min-edit",
				"url": "<%=request.getContextPath()%>/navigation/queryAgreeMentNavi.do?agreementStatus=09"
			},
			{
				"menuid": "325",
				"menuname": "已中止协议",
				"icon": "icon-min-edit",
				"url": "<%=request.getContextPath()%>/navigation/queryAgreeMentNavi.do?agreementStatus=03"
			},
			<%
			}
			if(access.hasAnyPrivilege("ROLE_CONTRACT_QUERY;ROLE_CONTRACT_VIEWALL")){
			%>
			{
				"menuid": "329",
				"menuname": "所有协议",
				"icon": "icon-min-edit",
				"url": "<%=request.getContextPath()%>/navigation/queryAgreeMentNavi.do?agreementStatus=99"
			}
			<%}%>
			]
		}
		<%}%>
		]
	},
	<%} %>
	<% if (access.hasAnyPrivilege("ROLE_ISSUE;ROLE_ISSUE_QUERY;ROLE_ISSUE_VIEWALL;")) {%>
	{
		"menuid": "4",
		"icon": "icon-tip",
		"menuname": "用款管理",
		"menus": [
				<%
				if(access.hasAnyPrivilege("ROLE_ISSUE")){
				%>
		         {
					 "menuid" : "42",
					 "menuname": "待处理用款",
					 "icon": "icon-min-edit",
					 "url": "<%=request.getContextPath()%>/navigation/queryPaymentApplyNav.do?paymentStatus=00"
				 },
				 {
					 "menuid": "43",
					 "menuname": "审核中用款",
					 "icon": "icon-min-edit",
					 "url": "<%=request.getContextPath()%>/navigation/queryPaymentApplyNav.do?paymentStatus=10"
				 },
				 {
					 "menuid": "44",
					 "menuname": "审核通过用款",
					 "icon": "icon-min-edit",
					 "child":[{
								 "menuid":"441",
								 "menuname":"待放款",
								 "icon": "icon-min-edit",
								 "url": "<%=request.getContextPath()%>/navigation/queryPaymentApplyNav.do?paymentStatus=90&excuteStatus=0"
					 	      },
					 	      {
					 	    	 "menuid":"442",
								 "menuname":"已放款",
								 "icon": "icon-min-edit",
								 "url": "<%=request.getContextPath()%>/navigation/queryPaymentApplyNav.do?paymentStatus=90&excuteStatus=1"
					 	      }]
				 },
				 {
					 "menuid": "45",
					 "menuname": "被否决用款",
					 "icon": "icon-min-edit",
					 "url": "<%=request.getContextPath()%>/navigation/queryPaymentApplyNav.do?paymentStatus=92"
				 },
				 {
					 "menuid": "46",
					 "menuname": "已取消用款",
					 "icon": "icon-min-edit",
					 "url": "<%=request.getContextPath()%>/navigation/queryPaymentApplyNav.do?paymentStatus=93"
				 },
				<%
				}
				if(access.hasAnyPrivilege("ROLE_ISSUE_QUERY;ROLE_ISSUE_VIEWALL")){
				%>
				{
					 "menuid": "49",
					 "menuname": "所有用款",
					 "icon": "icon-min-edit",
					 "url": "<%=request.getContextPath()%>/navigation/queryPaymentApplyNav.do?paymentStatus=99"
				 }
				<%
				}
				%>
				 ]
	},
	<%} 
	
	if (access.hasAnyPrivilege("ROLE_POSTED;ROLE_POSTED_QUERY;ROLE_REPAY;ROLE_REPAY_QUERY;ROLE_POSTED_VIEWALL;ROLE_REPAY_VIEWALL;ROLE_CDC_QUERY")) {		//授信后管理权限
	%>
	{
		"menuid": "5",
		"icon": "icon-tip",
		"menuname": "授信后管理",
		"menus": [
		<%if (access.hasAnyPrivilege("ROLE_POSTED;ROLE_POSTED_QUERY;ROLE_POSTED_VIEWALL")) {		//授信后管理权限
		%>
		{
			"menuid": "51",
			"menuname": "授信台账管理",
			"icon": "icon-tip",
			"child": [
			<%if (access.hasAnyPrivilege("ROLE_POSTED;ROLE_POSTED_VIEWALL")) {%>
			{
				"menuid": "511",
				"menuname": "未结清业务",
				"icon": "icon-min-edit",
				"url": "<%=request.getContextPath()%>/navigation/queryFacilityNav.do?bizStatus=02"
			},
			{
				"menuid": "512",
				"menuname": "已结清业务",
				"icon": "icon-min-edit",
				"url": "<%=request.getContextPath()%>/navigation/queryFacilityNav.do?bizStatus=01"
			},
			<%}
			if (access.hasAnyPrivilege("ROLE_POSTED_QUERY;ROLE_POSTED_VIEWALL")) {
			%>
			{
				"menuid": "519",
				"menuname": "所有业务",
				"icon": "icon-min-edit",
				"url": "<%=request.getContextPath()%>/navigation/queryFacilityNav.do?bizStatus=99"
			}
			<%}%>
			]
		},
		<%
		}
		if (access.hasAnyPrivilege("ROLE_REPAY;ROLE_REPAY_QUERY;ROLE_REPAY_VIEWALL")) {
		%>
		{
			"menuid": "52",
			"menuname": "还款申请管理",
			"icon": "icon-role",
			"child": [
			<%if (access.hasAnyPrivilege("ROLE_REPAY;")) {%>
			{
				"menuid": "521",
				"menuname": "未发送指令申请",
				"icon": "icon-set",
				"url": "<%=request.getContextPath()%>/navigation/paybackApplyNav.do?paybackStatus=00"
			},{
				"menuid": "522",
				"menuname": "待扣款还款 ",
				"icon": "icon-role",
				"url": "<%=request.getContextPath()%>/navigation/paybackApplyNav.do?paybackStatus=10"
			},{
				"menuid": "523",
				"menuname": "扣款成功还款 ",
				"icon": "icon-role",
				"url": "<%=request.getContextPath()%>/navigation/paybackApplyNav.do?paybackStatus=20"
			},{
				"menuid": "524",
				"menuname": "扣款失败还款 ",
				"icon": "icon-role",
				"url": "<%=request.getContextPath()%>/navigation/paybackApplyNav.do?paybackStatus=90"
			},{
				"menuid": "525",
				"menuname": "已取消还款 ",
				"icon": "icon-role",
				"url": "<%=request.getContextPath()%>/navigation/paybackApplyNav.do?paybackStatus=30"
			},
			{
				"menuid": "526",
				"menuname": "失效申请 ",
				"icon": "icon-role",
				"url": "<%=request.getContextPath()%>/navigation/paybackApplyNav.do?paybackStatus=50"
			},
			<%
			}
			if (access.hasAnyPrivilege("ROLE_REPAY_QUERY;ROLE_REPAY_VIEWALL")) {
			%>
			{
				"menuid": "529",
				"menuname": "所有还款 ",
				"icon": "icon-role",
				"url": "<%=request.getContextPath()%>/navigation/paybackApplyNav.do?paybackStatus=99"
			}
			<%}%>
			]
		},
		<%
		}
		if (access.hasAnyPrivilege("ROLE_REPAY;ROLE_REPAY_QUERY;ROLE_REPAY_VIEWALL;ROLE_CDC_QUERY")) {
		%>
		{
			"menuid": "53",
			"menuname": "还款进项管理",
			"icon": "icon-role",
			"child": [
			<%if (access.hasAnyPrivilege("ROLE_REPAY;ROLE_CDC_QUERY")) {%>
		    {
				"menuid": "531",
				"menuname": "未匹配还款进项 ",
				"icon": "icon-role",
				"url": "<%=request.getContextPath()%>/navigation/notMatchedPaybackImport.do?viewStatus=01"
			},{
				"menuid": "532",
				"menuname": "已匹配还款进项",
				"icon": "icon-role",
				"url": "<%=request.getContextPath()%>/navigation/matchedPaybackImport.do?viewStatus=02"
			},{
				"menuid": "533",
				"menuname": "下载进项数据文件",
				"icon": "icon-min-edit",
				"url": "<%=request.getContextPath()%>/navigation/downloadCDC.do"
			},
			<%
			}
			if (access.hasAnyPrivilege("ROLE_REPAY_QUERY;ROLE_REPAY_VIEWALL")) {
			%>
			{
					"menuid": "539",
					"menuname": "所有还款进项 ",
					"icon": "icon-role",
					"url": "<%=request.getContextPath()%>/navigation/notMatchedPaybackImport.do?viewStatus=99"
			}
			<%}%>
			]
		},
		<%
		}
		%>
		]
	},
	
	<%}
	
	if (access.hasAnyPrivilege("ROLE_CUST;ROLE_CUSTSERVICE;ROLE_ADMIN;ROLE_SERVICE_QUERY;ROLE_SERVICE_VIEWALL")) {			//客户管理/客服管理
	%>
	{
		"menuid": "6",
		"icon": "icon-tip",
		"menuname": "综合业务管理",
		"menus": [
		<% if (access.hasAnyPrivilege("ROLE_CUSTSERVICE;ROLE_SERVICE_QUERY;ROLE_SERVICE_VIEWALL;")) {%>         
		{
			"menuid": "61",
			"menuname": "客服外呼任务",
			"icon": "icon-tip",
			"child": [{
				"menuid": "612",
				"menuname": "待处理任务",
				"icon": "icon-min-edit",
				"url": "<%=request.getContextPath()%>/navigation/queryCallingTaskForReview.do?opflag=1"
			},{
				"menuid": "613",
				"menuname": "已处理任务",
				"icon": "icon-min-edit",
				"url": "<%=request.getContextPath()%>/navigation/queryCallingTaskForReview.do?opflag=0"
			}]
		},
	     {
            "menuid": "66",
            "menuname": "短信发送",
            "icon": "icon-role",
            "url": "<%=request.getContextPath()%>/navigation/createSmsTemplate.do?"
        },
		<% 
		}
		if (access.hasAnyPrivilege("ROLE_CUST;ROLE_ADMIN;")) {%>
		{
			"menuid": "62",
			"menuname": "客户管理",
			"icon": "icon-role",
			"child": [
			{
				"menuid": "621",
				"menuname": "客户管理",
				"icon": "icon-set",
				"url": "<%=request.getContextPath()%>/navigation/queryCustomerNav.do?"
			},{
				"menuid": "622",
				"menuname": "客户亲属 ",
				"icon": "icon-role",
				"url": "<%=request.getContextPath()%>/navigation/queryCustRelaNav.do?"
			},{
				"menuid": "623",
				"menuname": "客户商户 ",
				"icon": "icon-role",
				"url": "<%=request.getContextPath()%>/navigation/queryCustMerchantNav.do?"
			},{
				"menuid": "624",
				"menuname": "客户银行卡 ",
				"icon": "icon-role",
				"url": "<%=request.getContextPath()%>/navigation/queryCustBankNav.do?"
			}]
		},
		{
			"menuid": "63",
			"menuname": "黑名单管理",
			"icon": "icon-role",
			"url": "<%=request.getContextPath()%>/navigation/queryBlacklistNav.do?"
		},	
		<%} %>
		
		<% if (access.hasAnyPrivilege("ROLE_ADMIN;")) {%>
		{
			"menuid": "64",
			"menuname": "业务数据管理",
			"icon": "icon-role",
			"child": [
			{
				"menuid": "641",
				"menuname": "模型结果导入",
				"icon": "icon-role",
				"url": "<%=request.getContextPath()%>/navigation/modelResultImport.do?"
			},
			{
				"menuid": "642",
				"menuname": "渠道保证金查询",
				"icon": "icon-role",
				"url": "<%=request.getContextPath()%>/navigation/queryChannelFund.do?"
				
			},
			{
				"menuid": "643",
				"menuname": "外部审核消息重发",
				"icon": "icon-role",
				"url": "<%=request.getContextPath()%>/navigation/addInfoMessage.do?"
				
			},
			{
				"menuid": "644",
				"menuname": "代扣",
				"icon": "icon-role",
				"url": "<%=request.getContextPath()%>/navigation/collectionPage.do?"
				
			},
			]
		},
		<%} %>
		
		<% if (access.hasAnyPrivilege("ROLE_ADMIN;ROLE_CUST;ROLE_CUSTSERVICE;")) {%>
		{
			"menuid": "65",
			"menuname": "展业备案管理",
			"icon": "icon-role",
			"child": [
			{
				"menuid": "651",
				"menuname": "展业机构管理",
				"icon": "icon-role",
				"url": "<%=request.getContextPath()%>/navigation/bdInstitutionNav.do?"
			},{
				"menuid": "652",
				"menuname":"展业人员管理",
				"icon": "icon-role",
				"url": "<%=request.getContextPath()%>/navigation/bdExecutorNav.do"
			}]
		},
		<%} %>
		]
	},
	<%}%>
	
	<%if (access.hasAnyPrivilege("ROLE_STAT_POSTED;ROLE_STAT_RISK;ROLE_STAT_OPT;ROLE_STAT_HQ;ROLE_STAT_ADMIN;ROLE_STAT_SERVICE")) {%>
	{
		"menuid": "8",
		"icon": "icon-tip",
		"menuname": "统计查询",
		"menus": [
		<% if(access.hasAnyPrivilege("ROLE_STAT_RISK;")) {%>  
		    {
				"menuid": "81",
				"menuname": "风控报表",
				"icon": "icon-min-edit",
				"child": [
				<% if(access.hasAnyPrivilege("ROLE_APPR_LV1;ROLE_APPR_LV2;ROLE_APPR_LV3;ROLE_APPR_LV4;")){%>
				{
					"menuid": "811",
					"menuname": "申请审批情况表",
					"icon": "icon-min-edit",
					"url": "<%=request.getContextPath()%>/statistic/dataStatisticNav.do?qid=fr-risk-01"
				},
				<%}%>
				{
					"menuid": "812",
					"menuname": "申请审批情况简表",
					"icon": "icon-min-edit",
					"url": "<%=request.getContextPath()%>/statistic/dataStatisticNav.do?qid=fr-risk-01-lite"
				}]
			},
		<%}%>
		<% if(access.hasAnyPrivilege("ROLE_STAT_OPT;")) {%>  
			{
				"menuid": "82",
				"menuname": "运营报表",
				"icon": "icon-min-edit",
				"child": [
				{
					"menuid": "821",
					"menuname": "周报表",
					"icon": "icon-min-edit",
					"url": "<%=request.getContextPath()%>/navigation/synFileUM.do?"
				},
				{
					"menuid": "822",
					"menuname": "汇总报表",
					"icon": "icon-min-edit",
					"url": "<%=request.getContextPath()%>/navigation/synFileSummary.do?"
				},
				{
					"menuid": "823",
					"menuname": "贷款还款明细表 ",
					"icon": "icon-min-edit",
					"url": "<%=request.getContextPath()%>/statistic/dataStatisticNav.do?qid=fr-repay-01"
				},
				{
					"menuid": "824",
					"menuname": "全量贷款台账表（G11） ",
					"icon": "icon-min-edit",
					"url": "<%=request.getContextPath()%>/statistic/dataStatisticNav.do?qid=fr-g11"
				}]
			},
		<%}%>
		<% if(access.hasAnyPrivilege("ROLE_CUSTSERVICE;ROLE_SERVICE_QUERY;ROLE_SERVICE_VIEWALL;ROLE_STAT_SERVICE")) {%>  
			{
				"menuid": "84",
				"menuname": "客服报表",
				"icon": "icon-min-edit",
				"child": [
				{
					"menuid": "841",
					"menuname": "审批结果通知表",
					"icon": "icon-min-edit",
					"url": "<%=request.getContextPath()%>/statistic/dataStatisticNav.do?qid=fr-custservice-01"
				},{
					"menuid": "842",
					"menuname": "到期还款明细表 ",
					"icon": "icon-min-edit",
					"url": "<%=request.getContextPath()%>/statistic/dataStatisticNav.do?qid=fr-repay-02"
				},{
					"menuid": "843",
					"menuname": "客服查件",
					"icon": "icon-min-edit",
					"url": "<%=request.getContextPath()%>/statistic/dataStatisticNav.do?qid=qr-custservice-02"
				}]
			},
		<%}%>
		<% if(access.hasAnyPrivilege("ROLE_STAT_POSTED")) {%>  
		{
			"menuid": "85",
			"menuname": "贷后报表",
			"icon": "icon-min-edit",
			"child": [
			{
				"menuid": "851",
				"menuname": "应还未还贷款明细表",
				"icon": "icon-min-edit",
				"url": "<%=request.getContextPath()%>/statistic/dataStatisticNav.do?qid=fr-urge-01"
			}]
		},
	<%}%>
			]
		},
	<%} %>
	
	<%if(access.hasAnyPrivilege("ROLE_ADMIN;ROLE_SYS_ADMIN;ROLE_GOD_VIEW;ROLE_ACCT_ADMIN;ROLE_ADD_ACCT_RULE")) {%>
	{
		"menuid": "7",
		"icon": "icon-tip",
		"menuname": "系统管理",
		"menus": [
		<% if(access.hasAnyPrivilege("ROLE_ADMIN;")) {%>       
		   {
			"menuid": "71",
			"menuname": "用户管理",
			"icon": "icon-users",
			"child": [
			
			{
				"menuid": "711",
				"menuname": "用户管理",
				"icon": "icon-users",
				"url": "<%=request.getContextPath()%>/admin/user.do"

			},
			{
				"menuid": "712",
				"menuname": "用户组管理",
				"icon": "icon-role",
				"url": "<%=request.getContextPath()%>/admin/role.do"
			},
			]//end of 'menuid 21' 
		},
		 {
			"menuid": "74",
			"menuname": "小贷系统",
			"icon": "icon-users",
			"child": [
			{
				"menuid": "741",
				"menuname": "数据导入",
				"icon": "icon-role",
				"url": "<%=request.getContextPath()%>/navigation/dataTrans.do?"
			}
			]
		},
		<%} %>
		//活动管理
		<% if(access.hasAnyPrivilege("ROLE_ADD_ACCT_RULE")) {%>       
		{
			"menuid": "1211",
			"menuname": "活动管理",
			"icon": "icon-edit",
			"child": [
			
			{
				"menuid": "12111",
				"menuname": "活动维度管理",
				"icon": "icon-edit",
				"url": "<%=request.getContextPath()%>/navigation/queryActivityDimNavi.do"

			},
			{
				"menuid": "12122",
				"menuname": "活动内容管理",
				"icon": "icon-edit",
				"url": "<%=request.getContextPath()%>/navigation/queryActivityContentNavi.do"
			},
			{
				"menuid": "12123",
				"menuname": "添加活动",
				"icon": "icon-edit",
				"url": "<%=request.getContextPath()%>/navigation/queryActivityNavi.do"
			},
			]//end of 'menuid 21' 
		},
		<%} %>
		
		<% if(access.hasAnyPrivilege("ROLE_GOD_VIEW;")) {%>   
		{
			"menuid": "72",
			"menuname": "业务控制台",
			"icon": "icon-users",
			"url": ""
		},
		<%} %>
		<% if(access.hasAnyPrivilege("ROLE_ACCT_ADMIN;")) {%>   
		{
			"menuid": "73",
			"menuname": "核算管理控制台",
			"icon": "icon-users",
			"child": [			          
		{
			"menuid": "731",
			"menuname": "核算调账",
			"icon": "icon-users",
			"url": "<%=request.getContextPath()%>/acctManager/acctPageNav.do"
		
		},
		{
			"menuid": "732",
			"menuname": "分户账信息列表",
			"icon": "icon-role",
			"url": "<%=request.getContextPath()%>/acctManager/acctPageOfLedgerList.do"
		},
		{
			"menuid": "733",
			"menuname": "核算冲账管理",
			"icon": "icon-role",
			"url": "<%=request.getContextPath()%>/acctManager/strikeBalancePage.do"
		},
		{
			"menuid": "734",
			"menuname": "核算对账管理",
			"icon": "icon-role",
			"url": "<%=request.getContextPath()%>/acctManager/checkRecordPage.do"
		},
		{
			"menuid": "735",
			"menuname": "批量上送核心记账",
			"icon": "icon-role",
			"url": "<%=request.getContextPath()%>/acctManager/batchDataPage.do"
		},
		]},
		<%} %>
		<% if(access.hasAnyPrivilege("ROLE_SYS_ADMIN;")) {%>
		{
			"menuid": "79",
			"menuname": "权限管理",
			"icon": "icon-users",
			"url": "<%=request.getContextPath()%>/admin/privilege.do"
		}
		<%} %>
		
		]
	}//end of 'menuid 2'
	<% } %>
	]
};
        
        //关闭修改密码窗口
        function closePwd() {
            $('#w').window('close');
        }

        //修改密码
        function serverLogin() {
        	var $oldpass = $('#txtOldPass');
            var $newpass = $('#txtNewPass');
            var $rePass = $('#txtRePass');

            if ($oldpass.val() == '') {
                msgShow('系统提示', '请输入原密码！', 'warning');
                return false;
            }
            if ($newpass.val() == '') {
                msgShow('系统提示', '请输入新密码！', 'warning');
                return false;
            }
            if ($rePass.val() == '') {
                msgShow('系统提示', '请在一次输入密码！', 'warning');
                return false;
            }

            if ($newpass.val() != $rePass.val()) {
                msgShow('系统提示', '两次密码不一至！请重新输入', 'warning');
                return false;
            }

            var reqUrl = "<%=request.getContextPath()%>/user/modPassword.do";
            $.post(reqUrl,
            		{oldpass:$oldpass.val(),
            		newpass:$newpass.val()}, 
            	function(data) {
            	var obj = eval('(' +data+')');
            	var flg = obj.status;
            	if(flg){
            		msgShow('系统提示', "密码修改成功.", 'info');
            		closePwd();
            	}else{
            		msgShow('系统提示', obj.msg, 'warning');
            	}
            })
            
        }
        
        function openPwdWin(){
        	$('#w').openWin({
				title:'修改密码',
				width:350,
				height:200,
				href:'<%=request.getContextPath()%>/navigation/directAccess.do?srcpath=workbench/PwdModifyView'
				});
        }

        $(function() {
			$('#editpass').click(function() {
				openPwdWin();
			});
            
            $('#loginOut').click(function() {
                $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {

                    if (r) {
                        location.href = '<%=request.getContextPath()%>/j_spring_security_logout';
                    }
                });
            })
            
            //默认展示我的工作台
            addTab(_menus.menus[0].menus[0].menuname,_menus.menus[0].menus[0].url,_menus.menus[0].icon);
        });
		
		

    </script>

</head>
<body class="easyui-layout" style="overflow-y: hidden"  fit="true"   scroll="no">
<noscript>
<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
    <img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
</div></noscript>

<div id="loading-mask" style="position:absolute;top:0px; left:0px; width:100%; height:100%; background:#D2E0F2; z-index:20000">
<div id="pageloading" style="position:absolute; top:50%; left:50%; margin:-120px 0px 0px -120px; text-align:center;  border:2px solid #8DB2E3; width:200px; height:40px;  font-size:14px;padding:10px; font-weight:bold; background:#fff; color:#15428B;"> 
    <img src="images/loading.gif" align="absmiddle" /> 正在加载中,请稍候...</div>
</div>
	
	<!-- 顶部欢迎页 -->
    <div region="north" split="true" border="false" style="overflow: hidden; height: 30px;
        background: url(images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
        <span style="float:right; padding-right:20px;" class="head">欢迎 
        <%
            Object obj = session.getAttribute("USER");
            if(obj!=null && obj instanceof com.hrbb.loan.web.security.entity.User){
            com.hrbb.loan.web.security.entity.User user = (com.hrbb.loan.web.security.entity.User)obj;
        %>
        <%= user.getLoginName() %>
        <%  } %>
        <a href="#" id="editpass">修改密码</a> <a href="#" id="loginOut">安全退出</a></span>
        <span style="padding-left:10px; font-size: 16px; "><img src="images/blocks.gif" width="20" height="20" align="absmiddle" /> 哈尔滨银行互联网金融业务管理系统</span>
    </div>
    
    <div region="south" split="true" style="height: 30px; background: #D2E0F2; ">
        
    </div>
    
    <!-- 左侧导航 -->
    <div region="west" split="true"  title="导航菜单" style="width:180px;" id="west">
			<!--  导航内容 -->
			<div id="nav">
				
			</div>
    </div>
    
    <!-- 内容面板 -->
    <div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
        <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
			
		</div>
    </div>
    
	<!-- 密码修改框 -->
	<div id="w"></div>
	
	<!-- 顶部菜单 -->
	<div id="mm" class="easyui-menu" style="width:150px;">
		<div id="refresh">刷新</div>
		<div class="menu-sep"></div>
		<div id="close">关闭</div>
		<div id="closeall">全部关闭</div>
		<div id="closeother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="closeright">当前页右侧全部关闭</div>
		<div id="closeleft">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
		<div id="exit">退出</div>
	</div>

</body>
</html>