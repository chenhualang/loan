<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'unClearedReceipt.jsp' starting page</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/themes/icon.css" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src='<%=request.getContextPath()%>/js/locale/easyui-lang-zh_CN.js'></script>
	<script type="text/javascript" src='<%=request.getContextPath()%>/js/common_uiext.js'></script>
	
    <script type="text/javascript">
    	function queryContractManagement(){
			var queryUrl="<%=request.getContextPath()%>/paybackApply/queryPaybackInfo.do?paybackStatus=${paybackStatus}";
			$('#tt').datagrid(
				{url:queryUrl,
			    queryParams:{
			    	ReceiptIdLike: $('#ReceiptId').val(),
					custNameLike: $('#custName').val(),
					merchantNameLike: $('#merchantName').val(),
					custIdNoLike: $('#custIdNo').val(),
				}
			});
					
		}
    	
    	function executeQeury(){
    		var receiptId = getTextValue("#ReceiptId");
    		var custName = getTextValue("#custName");
    		var merchantName = getTextValue("#merchantName");
    		var custIdNo = getTextValue("#custIdNo");
    		if(receiptId=="" 
    				&& custName==""
    				&& merchantName==""
    				&& custIdNo==""){
    			$.messager.alert("操作提示","请输入查询条件.","warning");
    			return ;
    		}
    		queryContractManagement();
    	}
    	
    	/* 重置查询条件*/
    	function clearInfo(){
    		$('#ReceiptId').val("");
    		$('#custName').val("");
    		$('#merchantName').val("");
    		$('#custIdNo').val("");
    	}
		
    	function openReceiptDetail(){
            var rows = $('#tt').datagrid('getSelections');
			var length = rows.length;
			if (length == 0){
			    alert("请选择一条记录！");
			    return;
			}else if(length > 1){ 
			    alert("请只选择一条记录！");
			    return;
			} 
        	$('#ReceiptRelatedInfoWindow').window('open'); 
        	var row = $('#tt').datagrid('getSelected');
        	initDetailTabReceipt();
	        initDetailTabContract(row.contNo);
			initDetailTabCustomer(row.contNo);
    }
        function initDetailTabReceipt(){
             var row = $('#tt').datagrid('getSelected');
        	 $('.insertSignReceiptId').val(row.receiptId); 
             $('.insertSignCustName').val(row.custName);
             $('.insertSignPosCustName').val(row.posCustName);
             $('.insertSignLoanAmount').val(row.payApplyAmt);
             $('.insertSignLoanInterest').val(row.loanInterest);
             $('.insertSignBeginDate').val(row.beginDateStr);
             $('.insertSignEndDate').val(row.endDateStr);
             $('.insertSignPaybackWay').val(row.paybackWay);
             $('.insertSignLoanUsage').val(row.paybackWay);
        }
         
        function initDetailTabContract(contNo){
        var reqUrl = "<%=request.getContextPath()%>/payback/getContractInfo.do";
			$.post(reqUrl, {contNo: contNo}, function(data){
				var obj = eval('(' +data+')');
				$('.insertSignChannelName').val(obj.channel);
				$('.insertSignImportWay').val(obj.inChannelKind);
				$('.insertSignCustName').val(obj.custName);
				$('.insertSignPosCustName').val(obj.posCustName);
				$('.insertSignProdName').val(obj.prodName);
				$('.insertSignPaybackMethod').val(obj.paybackMethod);
				$('.insertSignApproveAmount').val(obj.apprTotalAmt);
				$('.insertSignApproveMoneyKind').val(obj.approveMoneyKind);
				$('.insertSignApproveInterest').val(obj.approveInterest);
				$('.insertSignApproveTerm').val(obj.contTerm);
			    $('.insertSignAccountOpenBank').val(obj.accountOpenBank);
			    $('.insertSignAcceptAccount').val(obj.acceptAccount);
			    $('.insertSignAccountBranchBank').val(obj.accountBranchBank);
			    $('.insertSignAccountSubBranchBank').val(obj.accountSubBranchBank);
			    $('.insertSignContractBeginDate').val(obj.beginDateStr);
			    $('.insertSignContractEndDate').val(obj.endDateStr);
			    $('.insertSignDate').val(obj.signDateStr);
			});  	
    
        } 
      
  
	     
	      function initDetailTabCustomer(contNo){	
			var reqUrl = "<%=request.getContextPath()%>/payback/getCustomerInfo.do";
			$.post(reqUrl, {contNo: contNo}, function(data){
				var obj = eval('(' +data+')');
				$('.insertSignCustId').val(obj.custId);
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
				$('.insertSignDivision').val(obj.residentDivision); 
				$('.insertSignDetailedAddress').val(obj.residentDetail);
				$('.insertSignCellphoneNo').val(obj.mobilePhone);
				$('.insertSignOfficeTel').val(obj.tel);
				$('.insertSignWechatNo').val(obj.weixinNo);
				$('.insertSignChildrenNo').val(obj.childNum);
				$('.insertSignQQNo').val(obj.qqNo);
				$('.insertSignEmailAddress').val(obj.email);
			});  	
	    }
    
    
    function openPayback(){
     var rows = $('#tt').datagrid('getSelections');
			var length = rows.length;
			if (length == 0){
			    alert("请选择一条记录！");
			    return;
			}else if(length > 1){ 
			    alert("请只选择一条记录！");
			    return;
			} 
			$('#PaybackInfoWindow').window('open'); 
        	var row = $('#tt').datagrid('getSelected');
        	initPaybackApply();
        	initDetailTabReceipt();
			initDetailTabCustomer(row.contNo);
    }
    
		    function initPaybackApply(){
		    var row = $('#tt').datagrid('getSelected');
		    $('#insertLoanBalance').val(row.loanTotalBalance); 
		    $('#insertPaybackWay').val(row.paybackWay);
		    $('#insertLoanPaybackWay').val(row.loanPaybackWay);
		    var d= new Date();
		    var e=d.toLocaleDateString();
		    $('#insertExpectPaybackDate').val(e);
		    }
    
    function closePayback(){
    $('#PaybackInfoWindow').window('close'); 
    }
    	//修改批复详情窗口
		function modifyPaybackDetailWindow(){
			$('#PaybackDetailsInfoWindow').window({
                title: '还款详情',
                width: 1000,
                modal: true,
                shadow: true,
                closed: true,
                height: 500,
                resizable:false
            });
		}
		
		
		//修改批复详情窗口
		function modifyPaybackInfoWindow(){
			$('#PaybackInfoWindow').window({
                title: '还款详情',
                width: 1000,
                modal: true,
                shadow: true,
                closed: true,
                height: 500,
                resizable:false
            });
		}
		
		function openPaybackDetail(){
		  var rows = $('#tt').datagrid('getSelections');
			var length = rows.length;
			if (length == 0){
			    alert("请选择一条记录！");
			    return;
			}else if(length > 1){ 
			    alert("请只选择一条记录！");
			    return;
			} 
        	$('#PaybackDetailsInfoWindow').window('open'); 
        	var row = $('#tt').datagrid('getSelected');
        	initDetailTabPaybackApply();
        	initDetailTabReceipt(row.receiptId);
			initDetailTabCustomer(row.receiptId);
		}
		
		function initDetailTabPaybackApply(){
		 var row = $('#tt').datagrid('getSelected');
        	 $('.insertSignReceiptId').val(row.receiptId); 
		     $('.insertSignExpectPaybackDate').val(row.expectPaybackDate); 
		     $('.insertSignPaybackTotalAmount').val(row.paybackTotalAmount); 
		     $('.insertSignPaybackAmount').val(row.paybackAmount); 
		     $('.insertSignPaybackInterest').val(row.paybackInterest); 
		}
		
		function initDetailTabReceipt(receiptId){
             var reqUrl = "<%=request.getContextPath()%>/paybackApply/getReceiptInfo.do";
			$.post(reqUrl, {receiptId: receiptId}, function(data){
				var obj = eval('(' +data+')');
				$('.insertSignReceiptId').val(obj.receiptId);
				$('.insertSignCustName').val(obj.custName);
				
				$('.insertSignPosCustName').val(obj.posCustName);
				$('.insertSignLoanAmount').val(obj.payApplyAmt);
				$('.insertSignLoanInterest').val(obj.loanInterest);
				$('.insertSignBeginDate').val(obj.beginDate);
				$('.insertSignEndDate').val(obj.endDate);
				$('.insertSignPaybackWay').val(obj.paybackWay);
				$('.insertSignLoanUsage').val(obj.loanUsage);
			});  	
        }
        
        
	      function initDetailTabCustomer(receiptId){	
			var reqUrl = "<%=request.getContextPath()%>/paybackApply/getCustomerInfo.do";
			$.post(reqUrl, {receiptId: receiptId}, function(data){
				var obj = eval('(' +data+')');
				$('.insertSignCustId').val(obj.custId);
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
				$('.insertSignDivision').val(obj.residentDivision); 
				$('.insertSignDetailedAddress').val(obj.residentDetail);
				$('.insertSignCellphoneNo').val(obj.mobilePhone);
				$('.insertSignOfficeTel').val(obj.tel);
				$('.insertSignWechatNo').val(obj.weixinNo);
				$('.insertSignChildrenNo').val(obj.childNum);
				$('.insertSignQQNo').val(obj.qqNo);
				$('.insertSignEmailAddress').val(obj.email);
			});  	
	    }
    
        function openCancelPayback(){
        var rows = $('#tt').datagrid('getSelections');
			var length = rows.length;
			if (length == 0){
			    alert("请选择一条记录！");
			    return;
			}else if(length > 1){ 
			    alert("请只选择一条记录！");
			    return;
			} 
        	$('#CancelPaybackRecord').window('open'); 
        }
        
            //发送还款指令
		    function openSentPayback(){
		    	    var reqUrl = "<%=request.getContextPath()%>/paybackApply/sentPayback.do";
		 			var row = $('#tt').datagrid('getSelected');
		 			var paybackApplyId = row.paybackApplyId;
		 			var receiptId = row.receiptId;
		 			var paybackApplyDate = row.paybackApplyDate;
		 			var custName = row.custName;
		 			var expectPaybackDateStr = row.expectPaybackDateStr;
		 			var paybackTotalAmount = row.paybackTotalAmount;
		 			var paybackPrincipal = row.paybackAmount;
		 			var paybackInterest = row.paybackInterest;
		 			var loanPaybackWay = row.loanPaybackWay;
		 			
		 			$.post(reqUrl, {paybackApplyId: paybackApplyId,receiptId: receiptId,paybackApplyDate:paybackApplyDate,custName:custName,expectPaybackDateStr:expectPaybackDateStr,
		 				paybackTotalAmount:paybackTotalAmount,paybackPrincipal:paybackPrincipal,paybackInterest:paybackInterest,loanPaybackWay:loanPaybackWay}, function(data){
		 				alert(data);
		 			});
		    	
		    	
		    }    
        
		    function changePaybackStatus(){
		            var reqUrl = "<%=request.getContextPath()%>/paybackApply/updatePaybackStatus.do";
		 			var row = $('#tt').datagrid('getSelected');
		 			var receiptId = row.receiptId;
		 			$.post(reqUrl, {receiptId: receiptId}, function(data){
		 				alert(data);
		 			});
		 		 $('#CancelPaybackRecord').window('close'); 	 
		    }
		    
		    function CancelChange(){
		    $('#CancelPaybackRecord').window('close'); 
			    }
			    
			    
		function closeDetailWindow(){
			$('#PaybackDetailsInfoWindow').window('close'); 
		}
		
		$(function() {
			<%
	    	com.hrbb.loan.web.security.entity.AccessPrivilege access = (com.hrbb.loan.web.security.entity.AccessPrivilege)session.getAttribute("accessPrivilege");
	    	String paybackStatus = (String)session.getAttribute("paybackStatus");
	    	if(!access.hasAnyPrivilege("ROLE_REPAY_QUERY") || (paybackStatus!=null && !paybackStatus.equals("99"))){	//具有全量查询权限时,初始化不进行查询
	    	%>
	    	queryContractManagement();
	    	<%}%>
	    	
		    modifyPaybackDetailWindow();
        	$('#tt').datagrid({
	    		onClickCell: function (rowIndex, field, value) { 
	                if(field != 'id'){
	                	$(this).datagrid('unselectAll');
	                }
	            },
	    	});
        }); 
    
    </script>
  </head>
  
  <body>
       <div id="CancelPaybackRecord" class="easyui-window" title="信息提示" closed="true" collapsible="false" minimizable="false"
        maximizable="false" icon="icon-save"  style="width: 300px; height: 150px; padding: 5px;
        background: #fafafa;">
        <div class="easyui-layout" fit="true">
            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
                                   确定要取消所选用款申请记录吗?
            </div>
            <div region="south" border="false" style="text-align: center; height: 30px; line-height: 30px;">
                <a id="btnEp" onclick="changePaybackStatus()" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >
                    确定</a> 
                     <a id="btnc" onclick="CancelChange()" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >
                    取消</a>
            </div>
        </div>
       </div> 

	
	<div id="tb" style="padding:5px;height:auto">  
	<div id="tb">   
    <a href="javascript:void(0)" id="01" class="easyui-linkbutton" iconCls="icon-cut" plain="true" onclick="openPaybackDetail()">还款详情</a>
    <c:if test="${paybackStatus!='99'}">
    <a href="javascript:void(0)" id="02" class="easyui-linkbutton" iconCls="icon-cut" plain="true" onclick="openCancelPayback()">取消还款</a>
    <a href="javascript:void(0)" id="02" class="easyui-linkbutton" iconCls="icon-cut" plain="true" onclick="openSentPayback()">发送还款指令</a> 
    </c:if>
  </div>  
	
	
	
	
	<div id="tb" style="padding:3px">
		<span>借据编号:</span>
		<input id="ReceiptId" name="ReceiptId" style="line-height:20px;border:1px solid #ccc"/>
		<span>客户名称:</span>
		<input id="custName" name="custName" style="line-height:20px;border:1px solid #ccc"/>
		<span>商户名称:</span>
		<input id="merchantName" name="merchantName" style="line-height:20px;border:1px solid #ccc"/>
		<span>证件号码:</span>
		<input id="custIdNo" name="custIdNo" style="line-height:20px;border:1px solid #ccc"/>
		<br/>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="clearInfo()">清空</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="executeQeury()">查询</a>
	</div>
	</div>  
    <table id="tt" class="easyui-datagrid" style="width:1500px;height:800px"
			title="Searching" iconCls="icon-search" toolbar="#tb" onClickRow="clickRow"
			rownumbers="true" pagination="true">
		<thead>
			<tr>
			   <th field="paybackApplyId" hidden="true"/>
			    <th field="id" checkbox="true"></th>
			    <th field="receiptId" width="120">借据编号</th> 
			    <th field="paybackApplyDate" width="120" align="center" formatter="dateFormat">申请日期</th> 
			    <th field="custName" width="120">客户名称</th> 
			    <th field="expectPaybackDateStr" width="120">期望还款日期</th> 
			    <th field="paybackTotalAmount" width="120">还款总金额</th> 
			    <th field="paybackAmount" width="120" hidden="true">还款本金</th> 
			    <th field="paybackInterest" width="120">还款利息</th>
				<th field="loanPaybackWay" width="120">贷款偿还方式</th>
			</tr>
		</thead>
	</table> 
      <jsp:include page="PaybackDetail.jsp"></jsp:include> 
  </body>
</html>
