<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>search</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/themes/icon.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/js/locale/easyui-lang-zh_CN.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/js/validator.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/js/calendar.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/js/common_uiext.js'></script>
<style type="text/css">
	.tdtitle {
	    background-color: #E0ECFF;
	}
</style>
<script type="text/javascript" >
	/* 根据条件查询 */
	function queryAgreement(){
		var queryUrl="<%=request.getContextPath()%>/agreementMrg/queryAgreement.do?agreementStatus=${agreementStatus}";
		$('#tt').datagrid(
			{url:queryUrl,
		    queryParams:{
		    	contNoLike : $('#searchContNo').val(),
		    	posCustNameLike : $('#searchPosCustName').val(),
		    	//custIdLike : $('#searchCustId').val(),
		    	custNameLike : $('#searchCustName').val(),
		    	paperIdLike : $('#searchPaperId').val()
			}
		});
		
	}
	
	function executeQeury(){
		var searchContNo = getTextValue("#searchContNo");
		var searchPosCustName = getTextValue("#searchPosCustName");
		//var searchCustId = getTextValue("#searchCustId");
		var searchCustName = getTextValue("#searchCustName");
		var searchPaperId = getTextValue("#searchPaperId");
		if(searchContNo=="" 
				&& searchPosCustName==""
				//&& searchCustId==""
				&& searchCustName==""
				&& searchPaperId==""){
			$.messager.alert("操作提示","请输入查询条件.","warning");
			return ;
		}
		queryAgreement();
	}
	
	/* 重置查询条件*/
	function queryBlank(){
		$('#searchContractNo').val("");
		$('#searchPosCustName').val("");
		$('#searchCustId').val("");
		$('#searchCustName').val("");
		$('#searchPaperId').val("");
		//queryAgreement();
	}
	/* 发起签约 */
	function openSignReply(){
		$("#signReplyWindow").openWin({
			title:'待签约批复',
			href:'openSignReply.do?approveStatus=02&direct2Path=agreementMrg/pendingAgreement/pendingAgreementMain',
		})
		
	}
	/* 查看协议详情 */
	function queryAgreementDetail(){
		var row = $('#tt').datagrid("getSelected");
		if(row==null){
			alert('请选择一条记录');
			return;
		}
		var contNo = row.contNo;
		
		$('#detailAgreementWindow').openWin({
			title:'授信协议详情',
			maximized:true,
			href:'openAgreementDetail.do?contractNo='+contNo,
		});
		
	}
	/* 生成协议pdf */
	function generatePdf(){
		var row = $('#tt').datagrid("getSelected");
		if(row==null){
			alert('请选择一条记录');
			return;
		}
		var contNo = row.contNo;
		var url = "<%=request.getContextPath()%>/pdf/generate.do?";
		$.post(url,{contNo : contNo}, function(msg){
		  alert(msg);
		});
		
	}
	/*打开冻结窗口*/
	function openFreeAgreement(){
		var reqUrl = "<%=request.getContextPath()%>/agreementMrg/queryAgreementDetail.do";
		var row = $('#tt').datagrid("getSelected");
		if(row==null){
			alert('请选择一条记录');
			return;
		}
		var contNo = row.contNo;
		$.post(reqUrl, {contNo : contNo}, function(obj){
			//协议信息
			var contractInfo = obj.contractInfo;
			//申请信息
			var creditApplyInfo = obj.creditApplyInfo;
			//银行信息
			var bankMap = obj.bankMap;
			//客户信息
			var customerInfo = obj.customerInfo;
		
			//银行信息格式化
			var accountBankDetail = renameBankInfo(contractInfo.accountOpenBank,contractInfo.accountBranchBank,contractInfo.accountSubBranchBank);
			//协议详情赋值
			$("#freezeCustName").val(contractInfo.custName);
			$("#freezeContNo").val(contractInfo.contNo);
			$("#freezePaperId1").val(customerInfo.paperId);
			$("#freezePaperKind").val(customerInfo.paperKind);
			$("#freezeCreditLine").val(contractInfo.creditLine);
			$("#freezeCreditInterest").val(contractInfo.creditInterest);
			$("#freezeContTerm").val(contractInfo.contTerm);
			var freezeAgreementBiginDate = (new Date(contractInfo.beginDate)).format("yyyy-MM-dd");
			$("#freezeAgreementBiginDate").val(freezeAgreementBiginDate);
			var freezeAgreementEndDate = (new Date(contractInfo.endDate)).format("yyyy-MM-dd")
			$("#freezeAgreementEndDate").val(freezeAgreementEndDate);
			//客户信息赋值
			$("#freezePaperId2").val(customerInfo.paperId);
			$("#freezePaperKindName2").val(customerInfo.paperKind);
			$("#freezeCustName2").val(contractInfo.custName);
			var freezeBirtDate = (new Date(customerInfo.birtDate)).format("yyyy-MM-dd");
			$("#freezeBirtDate").val(freezeBirtDate);
			$("#freezeNationality").val(customerInfo.nationality);
			$("#freezeNatiSign1").val(customerInfo.natiSign1);
			$("#freezeBusiYear").val(customerInfo.busiYear);
			$("#freezeSexSign").val(customerInfo.sexSign);
			$("#freezeMarrSign").val(customerInfo.marrSign);
			$("#freezeEducSign").val(customerInfo.educSign);
			$("#freezeChildNum").val(customerInfo.childNum);
			$("#freezeLocalHouseNum").val(customerInfo.localHouseNum);
			$("#freezeOtherHouseNum").val(customerInfo.otherHouseNum);
			$("#freezeMobilePhone").val(customerInfo.tel);
			$("#freezeResidentialAddress").val(customerInfo.residentProv+customerInfo.residentCity+customerInfo.residentDivision+customerInfo.residentDetail);
			$("#freezeWeixinNo").val(customerInfo.weixinNo);
			$("#freezeQQNo").val(customerInfo.qqNo);
			$("#freezeEmail").val(customerInfo.email);
			$(".editAddrFlag").val(customerInfo.addrFlag);
			//申请信息赋值
			$("#freezeLoanId").val(creditApplyInfo.loanId);
			$("#freezeChannel").val(creditApplyInfo.channel);
			$("#freezeBizLoanId").val(creditApplyInfo.bizLoanId);
			$("#freezeApplyAmt").val(creditApplyInfo.applyAmt);
			$("#freezeInChannelKind").val(creditApplyInfo.inChannelKind);
			$("#freezeRepayMethod").val(creditApplyInfo.repayMethod);
			$("#freezeCurrSign").val(creditApplyInfo.currSign);
			$("#freezeBankAccno").val(creditApplyInfo.bankAccno);
			$("#freezeReturnKind").val(creditApplyInfo.returnKind);
			$("#freezeBankName").val(accountBankDetail);
			$("#freezeRecOrg").val(creditApplyInfo.recOrg);
			$("#freezeRecPerson").val(creditApplyInfo.recPerson);
			$("#contractInfo").val(contractInfo);
		}, 'json');
		$("#freezeAgreementWindow").window("open");
	}
	/*打开解冻窗口*/
	function openUnFreezeAgreement(){
		var reqUrl = "<%=request.getContextPath()%>/agreementMrg/queryAgreementDetail.do";
		var row = $('#tt').datagrid("getSelected");
		if(row==null){
			alert('请选择一条记录');
			return;
		}
		var contNo = row.contNo;
		$.post(reqUrl, {contNo : contNo}, function(obj){
			//协议信息
			var contractInfo = obj.contractInfo;
			//申请信息
			var creditApplyInfo = obj.creditApplyInfo;
			//银行信息
			var bankMap = obj.bankMap;
			//客户信息
			var customerInfo = obj.customerInfo;
			//银行信息格式化
			var accountBankDetail = renameBankInfo(contractInfo.accountOpenBank,contractInfo.accountBranchBank,contractInfo.accountSubBranchBank);
			//协议详情赋值
			$("#unfreezeCustName1").val(contractInfo.custName);
			$("#unfreezeContNo").val(contractInfo.contNo);
			$("#unfreezePaperId1").val(customerInfo.paperId);
			$("#unfreezePaperKindName1").val(customerInfo.paperKind);
			$("#unfreezeCreditLine").val(contractInfo.creditLine);
			$("#unfreezeCreditInterest").val(contractInfo.creditInterest);
			$("#unfreezeContTerm").val(contractInfo.contTerm);
			var unfreezeAgreementBiginDate = (new Date(contractInfo.beginDate)).format("yyyy-MM-dd");
			$("#unfreezeAgreementBiginDate").val(unfreezeAgreementBiginDate);
			var unfreezeAgreementEndDate = (new Date(contractInfo.endDate)).format("yyyy-MM-dd")
			$("#unfreezeAgreementEndDate").val(unfreezeAgreementEndDate);
			$("#unfreezeAccountNo").val(contractInfo.acceptAccount);
			$("#unfreezeBankName1").val(accountBankDetail);
			$("#freezeReason2").val(contractInfo.freezeReason);
			$("#freezeDate2").val(contractInfo.freezeDate);
			$("#freezePerson2").val(contractInfo.freezePerson);
			//客户信息赋值
			$("#unfreezePaperId2").val(customerInfo.paperId);
			$("#unfreezePaperKindName2").val(customerInfo.paperKind);
			$("#unfreezeCustName2").val(contractInfo.custName);
			var unfreezeBirtDate = (new Date(customerInfo.birtDate)).format("yyyy-MM-dd");
			$("#unfreezeBirtDate").val(unfreezeBirtDate);
			$("#unfreezeNationality").val(customerInfo.nationality);
			$("#unfreezeNatiSign1").val(customerInfo.natiSign1);
			$("#unfreezeBusiYear").val(customerInfo.busiYear);
			$("#unfreezeSexSign").val(customerInfo.sexSign);
			$("#unfreezeMarrSign").val(customerInfo.marrSign);
			$("#unfreezeEducSign").val(customerInfo.educSign);
			$("#unfreezeChildNum").val(customerInfo.childNum);
			$("#unfreezeLocalHouseNum").val(customerInfo.localHouseNum);
			$("#unfreezeOtherHouseNum").val(customerInfo.otherHouseNum);
			$("#unfreezeMobilePhone").val(customerInfo.tel);
			$("#unfreezeResidentialAddress").val(customerInfo.residentProv+customerInfo.residentCity+customerInfo.residentDivision+customerInfo.residentDetail);
			$("#unfreezeWeixinNo").val(customerInfo.weixinNo);
			$("#unfreezeQQNo").val(customerInfo.qqNo);
			$("#unfreezeEmail").val(customerInfo.email);
			$("#editAddrFlag").val(customerInfo.addrFlag);
			//申请信息赋值
			$("#unfreezeLoanId").val(creditApplyInfo.loanId);
			$("#unfreezeChannel").val(creditApplyInfo.channel);
			$("#unfreezeBizLoanId").val(creditApplyInfo.bizLoanId);
			$("#unfreezeApplyAmt").val(creditApplyInfo.applyAmt);
			$("#unfreezeInChannelKind").val(creditApplyInfo.inChannelKind);
			$("#unfreezeRepayMethod").val(creditApplyInfo.repayMethod);
			$("#unfreezeCurrSign").val(creditApplyInfo.currSign);
			$("#unfreezeBankAccno").val(creditApplyInfo.bankAccno);
			$("#unfreezeReturnKind").val(creditApplyInfo.returnKind);
			$("#unfreezeBankName").val(accountBankDetail);
			$("#unfreezeRecOrg").val(creditApplyInfo.recOrg);
			$("#unfreezeRecPerson").val(creditApplyInfo.recPerson);
			$("#contractInfo").val(contractInfo);
		}, 'json');
		$("#unFreezeAgreementWindow").window("open");
	}
	
	/*打开调整窗口*/
	function openModifyAgreement(){
		var reqUrl = "<%=request.getContextPath()%>/agreementMrg/queryAgreementDetail.do";
		var row = $('#tt').datagrid("getSelected");
		if(row==null){
			alert('请选择一条记录');
			return;
		}
		var contNo = row.contNo;
		$.post(reqUrl, {contNo : contNo}, function(obj){
			//协议信息
			var contractInfo = obj.contractInfo;
			//申请信息
			var creditApplyInfo = obj.creditApplyInfo;
			//银行信息
			var bankMap = obj.bankMap;
			//银行code
			var accountOpenBank = contractInfo.accountOpenBank;
			//银行中文名称
			var accountOpenBank1 = obj.accountOpenBank;
			//客户信息
			var customerInfo = obj.customerInfo;
			//银行信息格式化
			var accountBankDetail = renameBankInfo(contractInfo.accountOpenBank,contractInfo.accountBranchBank,contractInfo.accountSubBranchBank);
			//协议详情赋值
			$("#editCustName1").val(contractInfo.custName);
			$("#editContNo").val(contractInfo.contNo);
			$("#editPaperId1").val(customerInfo.paperId);
			$("#paperKindName1").val(customerInfo.paperKind);
			$("#editCreditLine").val(contractInfo.creditLine);
			$("#beforeEditCreditLine").val(contractInfo.creditLine);
			$("#editCreditInterest").val(contractInfo.creditInterest);
			$("#editContTerm").val(contractInfo.contTerm);
			$("#newBankAccount").val(contractInfo.acceptAccount);
//			setInputValue("#newAccountOpenBank",accountOpenBank, "combo");
			$('#newAccountOpenBank').combobox('setValue',accountOpenBank);
			$("#newAccountBranchBank").val(contractInfo.accountBranchBank);
			$("#newAccountSubBranchBank").val(contractInfo.accountSubBranchBank);
			$("#beforeEditContTerm").val(contractInfo.contTerm);
			var editAgreementBigin = (new Date(contractInfo.beginDate)).format("yyyy-MM-dd");
			$("#editAgreementBiginDate").datebox('setValue',editAgreementBigin);
			var editAgreementEndDate = (new Date(contractInfo.endDate)).format("yyyy-MM-dd");
			$("#editAgreementEndDate").datebox('setValue',editAgreementEndDate);
			$("#beforeEditAgreementEndDate").val(editAgreementEndDate);
			$("#beforeEditBankAccount").val(contractInfo.acceptAccount);
			$("#beforeEditAccountOpenBank").val(accountOpenBank1);
			$("#beforeEditAccountBranchBank").val(contractInfo.accountBranchBank);
			$("#beforeEditAccountSubBranchBank").val(contractInfo.accountSubBranchBank);
			$("#editAccountNo").val(contractInfo.acceptAccount);
			$("#editBankName1").val(accountBankDetail);
			//客户信息赋值
			$("#editPaperId").val(customerInfo.paperId);
			$("#paperKindName2").val(customerInfo.paperKind);
			$("#editCustName2").val(contractInfo.custName);
			var editBirtDate = (new Date(customerInfo.birtDate)).format("yyyy-MM-dd");
			$("#editBirtDate").val(editBirtDate);
			$("#editNationality").val(customerInfo.nationality);
			$("#editNatiSign1").val(customerInfo.natiSign1);
			$("#editBusiYear").val(customerInfo.busiYear);
			$("#editSexSign").val(customerInfo.sexSign);
			$("#editMarrSign").val(customerInfo.marrSign);
			$("#editEducSign").val(customerInfo.educSign);
			$("#editChildNum").val(customerInfo.childNum);
			$("#editLocalHouseNum").val(customerInfo.localHouseNum);
			$("#editOtherHouseNum").val(customerInfo.otherHouseNum);
			$("#editMobilePhone").val(customerInfo.tel);
			$("#editResidentialAddress").val(customerInfo.residentProv+customerInfo.residentCity+customerInfo.residentDivision+customerInfo.residentDetail);
			$("#editWeixinNo").val(customerInfo.weixinNo);
			$("#editQQNo").val(customerInfo.qqNo);
			$("#editEmail").val(customerInfo.email);
			$("#editAddrFlag").val(customerInfo.addrFlag);
			//申请信息赋值
			$("#editLoanId").val(creditApplyInfo.loanId);
			$("#editChannel").val(creditApplyInfo.channel);
			$("#editBizLoanId").val(creditApplyInfo.bizLoanId);
			$("#editApplyAmt").val(creditApplyInfo.applyAmt);
			$("#editInChannelKind").val(creditApplyInfo.inChannelKind);
			$("#editRepayMethod").val(creditApplyInfo.repayMethod);
			$("#editCurrSign").val(creditApplyInfo.currSign);
			$("#editBankAccno").val(creditApplyInfo.bankAccno);
			$("#editReturnKind").val(creditApplyInfo.returnKind);
			$("#beforeEditReturnKind").val(creditApplyInfo.returnKind);
			$("#editBankName").val(accountBankDetail);
			$("#editRecOrg").val(creditApplyInfo.recOrg);
			$("#editRecPerson").val(creditApplyInfo.recPerson);
			$("#contractInfo").val(contractInfo);
		}, 'json');
		$("#editAgreementWindow").window("open");
	}
	
	/* 银行开户信息格式化*/
	function renameBankInfo(accountOpenBank,accountBranchBank,accountSubBranchBank){
		
		var accountBankDetail = '';
		if(typeof(accountOpenBank) == 'undefined'){
			accountOpenBank = '';
		}
		// 支行
		if(typeof(accountBranchBank) != 'undefined' && accountBranchBank.length > 0){
			if(accountBranchBank.indexOf('分行') == -1){
				accountBranchBank = accountBranchBank + '分行';
			}
		}else{
			accountBranchBank = '';
		}
	    // 分行
		if(typeof(accountSubBranchBank) != 'undefined' && accountSubBranchBank.length > 0 ){
			if(accountSubBranchBank.indexOf('支行') == -1){
				accountSubBranchBank = accountSubBranchBank + '支行';
			}
		}else{
			accountSubBranchBank = '';
			
		}
		accountBankDetail = accountOpenBank+accountBranchBank+accountSubBranchBank;
		return accountBankDetail;
	}
	
	/* 协议操作*/
	function modifyAgreement(flag){
		var reqUrl = "<%=request.getContextPath()%>/agreementMrg/modifyAgreement.do";
		var row = $('#tt').datagrid("getSelected");
		if(row==null){
			alert('请选择一条记录');
			return;
		}
		var contNo = row.contNo;
		//协议调整
		if(flag == '1'){
			var editReason = $("#editReason").val(); 
			if(typeof(editReason)=='undefined' || $.trim(editReason)==''){
				alert('调整原因不能为空');
				return;
			}
			//调整后数据
			var editCreditLine = $("#editCreditLine").val(); 
			var editContTerm = $("#editContTerm").val();
			var editReturnKind = $("#editReturnKind").val();
			var newBankAccount = $("#newBankAccount").val();
			var newAccountOpenBank = $('#newAccountOpenBank').combobox('getText');
			var accountOpenBank = $('#newAccountOpenBank').combobox('getValue');
			var newAccountBranchBank = $("#newAccountBranchBank").val();
			var newAccountSubBranchBank = $("#newAccountSubBranchBank").val();
			var editAgreementEndDate = $("#editAgreementEndDate").datebox('getValue');
			$('#editAgreementMassageWindow').window("open");
			$('#beforeAgreementCreditLine').val($("#beforeEditCreditLine").val());
			$('#afterAgreementCreditLine').val(editCreditLine);
			$('#beforeAgreementContTerm').val($("#beforeEditContTerm").val());
			$('#afterAgreementContTerm').val(editContTerm);
			$('#beforeAgreementReturnKind').val($("#beforeEditReturnKind").val());
			$('#afterAgreementReturnKind').val(editReturnKind);
			$('#beforeAgreementBankAccount').val($("#beforeEditBankAccount").val());
			$('#afterAgreementBankAccount').val(newBankAccount);
			$('#beforeAgreementAccountOpenBank').val($("#beforeEditAccountOpenBank").val());
			$('#afterAgreementAccountOpenBank').val(newAccountOpenBank);
			$('#hideAccountOpenBank').val(accountOpenBank);
			$('#beforeAgreementAccountBranchBank').val($("#beforeEditAccountBranchBank").val());
			$('#afterAgreementAccountBranchBank').val(newAccountBranchBank);
			$('#beforeAgreementAccountSubBranchBank').val($("#beforeEditAccountSubBranchBank").val());
			$('#afterAgreementAccountSubBranchBank').val(newAccountSubBranchBank);
			
			$('#beforeAgreementEndDate').datebox('setValue',$("#beforeEditAgreementEndDate").val());
			$('#afterAgreementEndDate').datebox('setValue',editAgreementEndDate);
		}
		//协议冻结
		if(flag == '2'){
			var freezeReason = $("#freezeReason").val();
			if(typeof(freezeReason)=='undefined' || $.trim(freezeReason) == '' ){
				alert('冻结原因不能为空');
				return;
			}
			if(confirm('确认要冻结该笔授信协议吗?')){
			$.post(reqUrl, {contNo:contNo,
				freezeReason : freezeReason,
				agreementStatus : '02'}, function(data){
					alert(data);
					$("#freezeAgreementWindow").window('close');
				}, 'text');
			}
		}
		//协议解冻
		if(flag == '3'){
			var unFreezeReason = $("#unFreezeReason").val();
			if(typeof(unFreezeReason)=='undefined' || $.trim(unFreezeReason) == '' ){
				alert('解冻原因不能为空');
				return;
			}
			if(confirm('确认要解冻该笔授信协议吗')){
			$.post(reqUrl, {contNo:contNo,
				unFreezeReason : unFreezeReason,
				agreementStatus : '01'}, function(data){
					alert(data);
					$("#unfreezeAgreementWindow").window('close');
				}, 'text');
			}
		}
		//协议生效
		if(flag == '4'){
			if(confirm('确认要生效该笔授信协议吗')){
			$.post(reqUrl, {contNo:contNo,
				eddectReason:"生效",
				agreementStatus : '01'}, function(data){
					alert(data);
					$('#tt').datagrid('reload');
				}, 'text');
			}
		}
		//协议调整确认
		if(flag == '5'){
			var editReason = $("#editReason").val(); 
			if(typeof(editReason)=='undefined' || $.trim(editReason)==''){
				alert('调整原因不能为空');
				return;
			}
			var editCreditLine = $('#afterAgreementCreditLine').val();
			var editContTerm = $('#afterAgreementContTerm').val();
			var editReturnKind = $('#afterAgreementReturnKind').val();
			
			var agreementstatus = ${agreementStatus};
			var editBankAccount = $('#afterAgreementBankAccount').val();
			var editAccountOpenBank = $('#hideAccountOpenBank').val();
			var editAccountBranchBank = $('#afterAgreementAccountBranchBank').val();
			var editAccountSubBranchBank = $('#afterAgreementAccountSubBranchBank').val();
			var editAgreementEndDate = $('#afterAgreementEndDate').datebox('getValue');
			 $.post(reqUrl, 
			  {contNo:contNo, //协议编号
			   creditLine :editCreditLine,//授信额度 
			   contTerm:editContTerm,//授信期限
			   agreementstatus:agreementstatus,//协议状态
			   editReturnKind:editReturnKind,//还款方式
			   BankAccount:editBankAccount,//银行账号
			   AccountOpenBank:editAccountOpenBank,//开户行
			   AccountBranchBank:editAccountBranchBank,//账户分行
			   AccountSubBranchBank:editAccountSubBranchBank,//账户支行
			   agreementEndDate : editAgreementEndDate,//协议到期日期
			   editReason : editReason}, //调整原因
			  function(data){
				alert(data);
				$('#editAgreementMassageWindow').window("close");
				$('#editAgreementWindow').window('close');
				$('#tt').datagrid('reload');
			  },
			  "text");   
		}
		if(flag == '6'){
			if(confirm('确认要激活该笔授信协议吗')){
				$.post(reqUrl, {contNo:contNo,
					activeReason:"激活",
					agreementStatus : '01'}, function(data){
						alert(data);
						$('#tt').datagrid('reload');
					}, 'text');
				}
		}
		if(flag == '7'){
			if(confirm('确认要退回重签该笔授信协议吗')){
				$.post(reqUrl, {contNo:contNo,
					signBack:"退回重签",
					agreementStatus : '05'}, function(data){
						alert(data);
						$('#tt').datagrid('reload');
					}, 'text');
				
			}
		}
		
		
		if(flag == '8'){
			if(confirm('确认要终止该笔授信协议吗')){
				$.post(reqUrl, {contNo:contNo,
					abandon:"终止",
					agreementStatus : '04'}, function(data){
						alert(data);
						$('#tt').datagrid('reload');
					}, 'text');
				}
		}
		
	}
	
	
	/*
	 Luhm校验规则：16位银行卡号（19位通用）:
	 
	1.将未带校验位的 15（或18）位卡号从右依次编号 1 到 15（18），位于奇数位号上的数字乘以 2。
	2.将奇位乘积的个十位全部相加，再加上所有偶数位上的数字。
	3.将加法和加上校验位能被 10 整除。
	//bankno为银行卡号 banknoInfo为显示提示信息的DIV或其他控件*/
	function luhmCheck(bankno){
	    var lastNum=bankno.substr(bankno.length-1,1);//取出最后一位（与luhm进行比较）
	 
	    var first15Num=bankno.substr(0,bankno.length-1);//前15或18位
	    var newArr=new Array();
	    for(var i=first15Num.length-1;i>-1;i--){    //前15或18位倒序存进数组
	        newArr.push(first15Num.substr(i,1));
	    }
	    var arrJiShu=new Array();  //奇数位*2的积 <9
	    var arrJiShu2=new Array(); //奇数位*2的积 >9
	     
	    var arrOuShu=new Array();  //偶数位数组
	    for(var j=0;j<newArr.length;j++){
	        if((j+1)%2==1){//奇数位
	            if(parseInt(newArr[j])*2<9)
	            arrJiShu.push(parseInt(newArr[j])*2);
	            else
	            arrJiShu2.push(parseInt(newArr[j])*2);
	        }
	        else //偶数位
	        arrOuShu.push(newArr[j]);
	    }
	     
	    var jishu_child1=new Array();//奇数位*2 >9 的分割之后的数组个位数
	    var jishu_child2=new Array();//奇数位*2 >9 的分割之后的数组十位数
	    for(var h=0;h<arrJiShu2.length;h++){
	        jishu_child1.push(parseInt(arrJiShu2[h])%10);
	        jishu_child2.push(parseInt(arrJiShu2[h])/10);
	    }        
	     
	    var sumJiShu=0; //奇数位*2 < 9 的数组之和
	    var sumOuShu=0; //偶数位数组之和
	    var sumJiShuChild1=0; //奇数位*2 >9 的分割之后的数组个位数之和
	    var sumJiShuChild2=0; //奇数位*2 >9 的分割之后的数组十位数之和
	    var sumTotal=0;
	    for(var m=0;m<arrJiShu.length;m++){
	        sumJiShu=sumJiShu+parseInt(arrJiShu[m]);
	    }
	     
	    for(var n=0;n<arrOuShu.length;n++){
	        sumOuShu=sumOuShu+parseInt(arrOuShu[n]);
	    }
	     
	    for(var p=0;p<jishu_child1.length;p++){
	        sumJiShuChild1=sumJiShuChild1+parseInt(jishu_child1[p]);
	        sumJiShuChild2=sumJiShuChild2+parseInt(jishu_child2[p]);
	    }      
	    //计算总和
	    sumTotal=parseInt(sumJiShu)+parseInt(sumOuShu)+parseInt(sumJiShuChild1)+parseInt(sumJiShuChild2);
	     
	    //计算Luhm值
	    var k= parseInt(sumTotal)%10==0?10:parseInt(sumTotal)%10;        
	    var luhm= 10-k;
	     
	    if(lastNum==luhm){
	   // $("#banknoInfo").html("Luhm验证通过");
	    return true;
	    }
	    else{
	   //$("#banknoInfo").html("银行卡号必须符合Luhm校验");
	    return false;
	    }        
	}
	
	function valiateBankCard(card){
		var cardno = $(card).val();
		if(typeof(cardno)!="undefined" && cardno.length >= 6){
		/*银行卡号luhm校验*/
		var flag = luhmCheck(cardno);
		if(!flag){
			$.messager.alert("操作提示",cardno + "不是合法的银行卡号","error");
			return;
		}
			//var bin = cardno.substring(0,6);
			var reqUrl="<%=request.getContextPath()%>/genericConfig/getIssuerInfo.do";
			$.post(reqUrl, {cardno: cardno}, function(data){
				var issueBankName = data.issueBankName;
				if(typeof(issueBankName)!="undefined" && issueBankName.length > 0){
					var bankAttr = data.bankAttr;
					if(typeof(bankAttr)!="undefined" && bankAttr==''){
						var cardName = data.cardName;
						//var cardType = data.cardType;
						$.messager.alert("操作提示",cardName+" ["+issueBankName+"] 不能作为有效的收款银行账号!","error");
						return;
					}
			//		$("#newAccountOpenBank").val(bankAttr);
					setInputValue("#newAccountOpenBank", bankAttr, "combo");
				}else{
		    //		$("#newAccountOpenBank").val(bankAttr);
					setInputValue("#newAccountOpenBank", "", "combo");
				}
			}, 'json');
		}
	}
	
 	//打开更换银行卡信息窗口
	function openChangeBankCard(){
		$('#editBankCardInfoWindow').window('open');
	}
	
	function changeBankCard(){
		var reqUrl = "<%=request.getContextPath()%>/agreementMrg/changebankaccountinfo.do";
		var row = $('#tt').datagrid("getSelected");
		if(row==null){
			alert('请选择一条记录');
			return;
		}
		var contNo = row.contNo;
		var agreementstatus = ${agreementStatus};
		var bankaccount = $('#newBankAccount').val();
		var accountopenbank = $('#newAccountOpenBank').combobox('getValue');
		var accountbranchbank = $('#newAccountBranchBank').val();
		var accountsubbranchbank = $('#newAccountSubBranchBank').val();
		$.post(reqUrl, {contNo:contNo,agreementstatus:agreementstatus,bankaccount:bankaccount,accountopenbank:accountopenbank,accountbranchbank:accountbranchbank,accountsubbranchbank:accountsubbranchbank}, function(data){
			alert(data);
		}, 'text');
		$('#editBankCardInfoWindow').window('close');
	}
	function closeChangeBankInfo(){
		$('#editBankCardInfoWindow').window('close');
	}
	//关闭窗口
	function closeThisWindow(flag){
		//关闭协议详情窗口
		if(flag == '1'){
			$('#detailAgreementWindow').window('close');
		}
		//关闭调整窗口
		if(flag == '2'){
			$('#editAgreementWindow').window('close');
			$("#editCreditLine").val('');
			$("#editAgreementEndDate").datebox("setValue", '');
			$("#editReason").val('');
		}
		//关闭冻结窗口
		if(flag == '3'){
			$('#freezeAgreementWindow').window('close');
			$("#freezeReason").val('');
		}
		//关闭解冻窗口
		if(flag == '4'){
			$('#unFreezeAgreementWindow').window('close');
			$('#unFreezeReason').val('');
		}
	}
	
	//日期格式化
	function dateFormat(value, row, index){
		return timeStamp2String(value);
	}
	//协议调整接口初始
  	function initAgreementWindow(){
		/*
  		$('#detailAgreementWindow').window({
            title: '授信协议详情',
            width: 900,
            modal: true,
            shadow: true,
            closed: true,
            height: 400,
            resizable:false
        });
  		*/
  		$('#editAgreementWindow').window({
            title: '授信协议调整',
            width: 900,
            modal: true,
            shadow: true,
            closed: true,
            height: 400,
            resizable:false
        });
  		
  		$('#freezeAgreementWindow').window({
            title: '授信协议冻结',
            width: 900,
            modal: true,
            shadow: true,
            closed: true,
            height: 400,
            resizable:false
        });
  		
  		$('#unFreezeAgreementWindow').window({
            title: '授信协议解冻',
            width: 900,
            modal: true,
            shadow: true,
            closed: true,
            height: 400,
            resizable:false
        });
  		
  		$('#editAgreementMassageWindow').window({
            title: '信息提示',
            width: 500,
            modal: true,
            shadow: true,
            closed: true,
            height: 270,
            resizable:false
        });
  		
  		$('#editBankCardInfoWindow').window({
            title: '更改银行信息',
            width: 900,
            modal: true,
            shadow: true,
            closed: true,
            height: 400,
            resizable:false
        });
  	}
	//协议调整提示窗口
	function openEditMassage(){
		$('#editAgreementMassageWindow').window('open');
	}
  	//页面加载执行方法
    $(function() {
    	<%
    	com.hrbb.loan.web.security.entity.AccessPrivilege access = (com.hrbb.loan.web.security.entity.AccessPrivilege)session.getAttribute("accessPrivilege");
    	String agreementStatus = (String)session.getAttribute("agreementStatus");
    	if(!access.hasAnyPrivilege("ROLE_CONTRACT_QUERY") || (agreementStatus!=null && !agreementStatus.equals("99"))){	//具有全量查询权限时,初始化不进行查询
    	%>
    		queryAgreement();
    	<%}%>
    	
    	initAgreementWindow();
    	$('input.easyui-validatebox').validatebox('disableValidation')
        .focus(function () { $(this).validatebox('enableValidation'); })
        .blur(function () { $(this).validatebox('validate')});
    	$('#tt').datagrid({
    		onClickCell: function (rowIndex, field, value) { 
                if(field != 'id'){
                	$(this).datagrid('unselectAll');
                }
                
            },
            onDblClickRow:function(rowIndex, rowData) {
            	queryAgreementDetail();
            },
            onCheck:function(index, row){
            	switchFunctions(row);
            },
            singleSelect:true
    	});
    });
  	
    function switchFunctions(row){
    	var channel = row["channel"];
    	if(typeof(channel)!="undefined" && (channel=="ZW" || channel=="ZY" || channel=="SQ" || channel=="UM")){
    		if($("#btnResignAgrmt").length>0){ $("#btnResignAgrmt").linkbutton("disable"); }
    	}else{
    		if($("#btnResignAgrmt").length>0){ $("#btnResignAgrmt").linkbutton("enable"); }
    	}
    }
  	/*退回重签*/
  	function resignAgrmt(){
  		modifyAgreement(7);
  	}
</script>
</head>

<body>
	<table id="tt" title="授信协议管理" iconCls="icon-search" toolbar="#tb"
		onClickRow="clickRow" rownumbers="true" pagination="true">
		<thead>
			<tr>
				<th field="id" checkbox="true"></th>
				<th field="contNo" width="200px">合同编号</th>
				<th field="channel" hidden="true">业务渠道</th>
				<th field="channelName" width="80px">渠道名称</th>
				<th field="custName" width="80px">客户名称</th>
				<th field="posCustName" width="180px">商户名称</th>
				<th field="creditLine" formatter="formatMoney" width="100px" align="right">授信额度</th>
				<th field="creditInterest" width="80px" formatter="formatRate" align="right">授信利率</th>
				<th field="beginDate" formatter="dateFormat" width="80px">协议起始日</th>
				<th field="endDate" formatter="dateFormat" width="80px">协议到期日</th>
				<th field="agmtStatusName" width="80px">协议状态</th>
			</tr>
		</thead>
	</table>
		<div id="detailAgreementWindow"></div>
	
	<c:if test="${agreementStatus == '06'}">
		<jsp:include page="agreementEdit/editBankCardInfo.jsp"></jsp:include>
	<div id="signReplyWindow"></div>
	</c:if>
	<c:if test="${agreementStatus == '01'}">
	    <jsp:include page="agreementFreeze/freezeAgreementMain.jsp"></jsp:include>
	    <jsp:include page="agreementEdit/editAgreementMain.jsp"></jsp:include>
	    <jsp:include page="agreementEdit/editmassage.jsp"></jsp:include>
    </c:if>
    <c:if test="${agreementStatus == '02'}">
	    <jsp:include page="agreementUnfreeze/unFreezeAgreementMain.jsp"></jsp:include>
	    <jsp:include page="agreementEdit/editAgreementMain.jsp"></jsp:include>
    </c:if>
    <c:if test="${agreementStatus == '09'}">
    </c:if>
    <c:if test="${agreementStatus == '03'}">
    </c:if>
	<jsp:include page="searchAgreement.jsp"/>
	<div id="contractWindow"></div>	<!-- 生成协议信息 -->
</body>
</html>