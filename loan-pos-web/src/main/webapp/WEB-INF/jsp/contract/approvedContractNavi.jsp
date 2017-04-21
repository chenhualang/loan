<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add search functionality in DataGrid - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/themes/icon.css" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src='<%=request.getContextPath()%>/js/locale/easyui-lang-zh_CN.js'></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/common_uiext.js"></script>
	<script type="text/javascript">
		function queryContractManagement(){
			$('#tt').datagrid('load',{		
				loanIdLike: $('#loanId').val(),
				custNameLike: $('#custName').val(),
				merchantNameLike: $('#merchantName').val(),
				custIdLike: $('#custId').val(),
				custIdNoLike: $('#custIdNo').val(),
			});
		}
		
        function formatInterest(val,row){
			
			return val+"%";
		}
		
		function formatApproveTerm(val,row){
			return val+"月";
		}
		
		function clearInfo(){
		       $('#loanId').val(""),
			   $('#custName').val(""),
			   $('#merchantName').val(""),
			   $('#custId').val(""),
			   $('#custIdNo').val("")
		}
		
		 function deleteContractManagement(){
			var reqUrl = "<%=request.getContextPath()%>/contractManagement/deleteContractManagement.do";
			var row = $('#tt').datagrid('getSelected');
			$.get(reqUrl, {deleteItem: row.approveId}, function(data){
				alert(data);
				queryContractManagement();
			});
		} 
		
		
         function executeQeury(){
             var bizLoanId = getTextValue("#bizLoanId");
             var custName = getTextValue("#custName");
             var custIdNo = getTextValue("#custIdNo");
             //var approveStatus = getTextValue("#approveStatus");
             if(bizLoanId=="" 
                     && custName==""
                     && custIdNo==""){
                 $.messager.alert("操作提示","请输入查询条件.","warning");
                 return ;
             }
             queryContractManagement();
         }
/* 			
	function rowformater(value, row, index) {    
		      return "<a href='javascript:modifyContractManagementDetail();'>修改</a> <a href='javascript:deleteContractManagement()'>删除</a>";
        }
		 */
	
		

		
			//修改协议详情窗口
		function modifyconfirmationSignWindow(){
			$('#confirmationSignWindow').window({
                title: '协议详情',
                width: 1000,
                modal: true,
                shadow: true,
                closed: true,
                height: 500,
                maximized:true,
                resizable:false
            });
			
		}
		
		

		
		function initDetailTabApprove1(){
				var row = $('#tt').datagrid('getSelected');
				$('.insertSignApproveId').val(row.approveId);
				$('.insertSignLoanId').val(row.loanId);
				$('.insertSignProductId').val(row.productId);
				$('.insertSignCustId').val(row.custId);
				$('.insertSignPosCustName').val(row.posCustName);
				$('.insertSignApproveMoneyKind').val(row.approveMoneyKind);
				$('.insertSignPaybackMethod').val(row.paybackMethod);
				$('.insertSignAccountBranchBank').val(row.accountBranchBank);
				$('.insertSignAccountSubBranchBank').val(row.accountSubBranchBank);
				$('.insertSignChannelName').val(row.businessSource);
				$('.insertSignApllyAmount').val(row.applyAmt);
				$('.insertSignApplyMoneyKind').val(row.applyMoneyKind);
				$('.insertSignApplyTerm').val(row.applyTerm);
				$('.insertSignTermUnit').val(row.termUnit);
				$('.insertSignApplyCommitDate').val(row.applyCommitDateStr);
				$('.insertSignApproveAmount').val(row.approveAmount);
				$('.insertSignApproveInterest').val(row.approveInterest);
				$('.insertSignApproveTerm').val(row.approveTerm);
				$('.insertSignApproveTermUnit').val(row.approveTermUnit);
				$('.insertSignApproveDate').val(row.approveDateStr);
				$('.insertSignAcceptAccount').val(row.acceptAccount);
				$('.insertSignAccountOpenBank').val(row.accountOpenBank);
				$('.insertSignApproveStatus').val(row.approveStatus);
				$('.insertSignCustId').val(row.custId);
		 }
	
     
         
         
        //打开签约窗口
        function openContractDetail(){
            var rows = $('#tt').datagrid('getSelections');
			var length = rows.length;
			if (length == 0){
			    alert("请选择一条记录！");
			    return;
			}else if(length > 1){ 
			    alert("请只选择一条记录！");
			    return;
			} 
	        $('#signEffect').hide();
        	$('#confirmationSignWindow').window('open'); 
        	var row = $('#tt').datagrid('getSelected');
        	 $('#insertSignCustName').val(row.custName); 
        	 $('#insertSignApproveAmount').val(row.approveAmount);
        	 $('#insertSignApproveInterest').val(row.approveInterest);
        	 $('#insertSignApproveTerm').val(row.approveTerm);
        	 $('#insertSignApproveTermUnit').val(row.approveTermUnit);
        	 $('#insertSignPaybackMethod').val(row.paybackMethod);
        	 $('#insertSignAcceptAccount').val(row.acceptAccount);
        	 $('#insertSignAccountOpenBank').val(row.accountOpenBank);
        	 $('#insertSignBusiSource').val(row.businessSource);
        	 $('#insertSignLoanId').val(row.loanId);
        	 $('#insertSignApproveId').val(row.approveId);
        	 $('#insertSignCustId').val(row.custId);
        	 $('#insertSignProductId').val(row.productId);
        	 $('#insertSignApproveMoneyKind').val(row.approveMoneyKind);
        	 $('#insertSignAccountBranchBank').val(row.accountBranchBank);
			 $('#insertSignAccountSubBranchBank').val(row.accountSubBranchBank); 
 
	        initDetailTabApprove1();
			initDetailTabCustomer(row.custId);
		    initDetailTabApply(row.loanId);
			initDateInfo();
        }
         
        function initDetailTabCustomer(custId){	
			var reqUrl = "<%=request.getContextPath()%>/contractManagement/queryContractManagement1.do";
			$.post(reqUrl, {custId: custId}, function(data){
				var obj = eval('(' +data+')');
				$('.insertSignCustId').val(custId);
				$('.insertSignCustName').val(obj.custName);
				$('.insertSignCredentialtype').val(obj.paperKind);
				$('.insertSignCredentialNo').val(obj.paperId);
				$('#insertSignIdType').val(obj.paperKind);
				$('#insertSignIdNo').val(obj.paperId);
				$('#insertRejectIdNo').val(obj.paperId);
				$('.insertSignBirthDate').val(obj.birtDate);
				$('.insertSignGender').val(obj.sexSign);
				$('.insertSignMarriageStatus').val(obj.marrSign);
				$('.insertSignHighestDegree').val(obj.educSign);
				$('.insertSignPermanentAddress').val(obj.regiSeat);
			 	$('.insertSignNation').val(obj.natiSign1);
				$('.insertSignProvince').val(obj.residentProv); 
				$('.insertSignCity').val(obj.residentCity); 
				$('.insertSignAddress').val(obj.residentDetail); 
				$('.insertSignCurrentAddress').val(obj.inhabStatSign);
				$('.insertSignCellphoneNo').val(obj.mobilePhone);
				$('.insertSignOfficeTel').val(obj.tel);
				$('.insertSignWechatNo').val(obj.weixinNo);
				$('.insertSignChildrenNo').val(obj.childNum);
				$('.insertSignQQNo').val(obj.qqNo);
				$('.insertSignEmailAddress').val(obj.email);
			});  	
	    }
	    
	    
	    
        function initDetailTabApply(loanId){    	
			var reqUrl = "<%=request.getContextPath()%>/contractManagement/queryContractManagement2.do";
			$.post(reqUrl, {loanId: loanId}, function(data){
				var obj = eval('(' +data+')');
				$('#insertSignImportWay').val(obj.inChannelKind);
				$('#insertSignChannelName').val(obj.channel);
				$('#insertSignPosCustName').val(obj.posCustName);
				$('#insertSignProdName').val(obj.prodName);
				$('.insertSignImportWay').val(obj.inChannelKind);
				$('.insertSignloanId').val(obj.loanId);
				$('.insertSignApplyAmount').val(obj.applyAmt);
				$('.insertSignProdName').val(obj.prodName);
				$('.insertSignPaybackMethod').val(obj.returnKind);			
				$('.insertSignTermUnit').val(obj.termUnit);
				$('.insertSignApplyTerm').val(obj.applyTerm);		
				$('.insertSignBeginDate').val(obj.beginDate);
				$('.insertSignAcceptDate').val(obj.acptDate);		
				$('.insertSignAssureKind').val(obj.assuKind);
				$('.insertSignAmt').val(obj.amt);	
				$('.insertSignReckDay').val(obj.reckDay);
				$('.insertSignReturnDay').val(obj.returnDay);	
				$('.insertSignloanUsage').val(obj.loanUsage);
				$('.insertSignloanUsageDesc').val(obj.loanUsageDesc);	
				$('.insertSignreturnSourceDesc').val(obj.returnSourceDesc);
			});   	
	    }
        
        function initDateInfo(){
            var reqUrl = "<%=request.getContextPath()%>/contractManagement/queryContractManagement4.do";
			var custId = $('.insertSignCustId').val();
			$.post(reqUrl, {custId:custId}, function(obj){
		//		$('#insertSignContractId').val(obj.contNo);
				$('#insertSignContractBeginDate').val(obj.bd);
				$('#insertSignContractEndDate').val(obj.ed);
				$('#insertSignDate').val(obj.sd);
		//		$('#insertSignInputPerson').val(obj.username);
			},'json');
        
        }
        
		function a(){
		    //$('#01').hide();
		    //$('#02').hide();
        	//$('#03').hide();
        	//$('#04').hide();
        	//$('#05').hide();
        	//$('#06').hide();
		    $('#signEffect').hide();
		}
		
		//关闭“协议详情”窗口
		function closeSignWindow() {
			$('#confirmationSignWindow').window('close');
			queryContractManagement();
		}

		$(function() {
			modifyconfirmationSignWindow();
			a();
			$('#tt').datagrid({
				onClickCell : function(rowIndex, field, value) {
					if (field != 'id') {
						$(this).datagrid('unselectAll');
					}

				},
			});

		});
	</script>
</head>
<body>
	
	<table id="tt" class="easyui-datagrid" style="width:1500px;height:800px"
			url="<%=request.getContextPath()%>/contractManagement/queryContractManagement.do?searchApproveStatus=${approveStatus}"
			title="Searching" iconCls="icon-search" toolbar="#tb" onClickRow="clickRow"
			rownumbers="true" pagination="true">
		<thead>
			<tr>
			    <th field="id" checkbox="true"></th>
			    <th field="approveId" width="120">批复编号</th> 
			    <th field="custName" width="80">客户名称</th>
				<th field="businessSource" width="80">业务来源</th>
				<th field="applyAmt" width="120" hidden="true">申请额度</th>
			    <th field="applyMoneyKind" width="120" hidden="true">申请币种</th>
			    <th field="termUnit" width="120" hidden="true">期限单位</th>
			    <th field="applyTerm" width="120" hidden="true">申请期限</th>
			    <th field="applyCommitDateStr" width="120" hidden="true">申请提交日期</th>  
			    <th field="acceptAccount" width="120">收款账号</th>
			    <th field="accountOpenBank" width="120">账户开户行</th>
				<th field="loanId" hidden="true">申请编号</th>
				<th field="custId" hidden="true">客户编号</th>
				<th field="approveAmount" width="120" align="right" formatter="formatMoney">批复额度</th>
				<th field="approveInterest" width="80" align="right" formatter="formatInterest">批复利率</th>
				<th field="approveTerm" width="80" align="right" formatter="formatApproveTerm">批复期限</th>
				<th field="approveDateStr" width="120">批复日期</th>
				<th field="paybackMethod" width="120">还款方式</th>  
				<th field="approveStatusName" width="80">批复状态</th>
			<!-- 	<th field="hh" width="150"  align="center" formatter="rowformater">操作</th> -->
			</tr>
		</thead>
	</table>
	<jsp:include page="head.jsp"></jsp:include>	
</body>
</html>