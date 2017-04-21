<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>matchedPaybackImport</title>
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
    <script type="text/javascript">
   	function queryPaybackImport(){
		$('#tt').datagrid('load',{	
			paybackPersonName: $('#paybackPersonName').val(),
			paybackPersonAccount: $('#paybackPersonAccount').val(),
		});
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
		})	
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
		})	
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
		$('#paybackDetailsInfoWindow').window({
               title: '还款详情',
               width: 1000,
               modal: true,
               shadow: true,
               closed: true,
               height: 500,
               resizable:false
        })
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
      	$('#paybackDetailsInfoWindow').window('open'); 
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
	$('#paybackDetailsInfoWindow').window('close'); 
	}
	
    $(function() {
    	modifyPaybackDetailWindow();
      	$('#tt').datagrid({
  		onClickCell: function (rowIndex, field, value) { 
              if(field != 'id'){
              	$(this).datagrid('unselectAll');
              }
          },
  		})
    })
    
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
	             <a id="btnEp" onclick="changePaybackStatus()" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >确定</a> 
	             <a id="btnc" onclick="CancelChange()" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >取消</a>
	         </div>
	     </div>
    </div> 
	<div id="tb" style="padding:5px;height:auto">  
	<div id="tb">   
    	<a href="javascript:void(0)" id="01" class="easyui-linkbutton" iconCls="icon-cut" plain="true" onclick="openImportDetail()">进项详情</a> 
  	</div>  
	<div id="tb" style="padding:3px">
		<span>付款人名称:</span>
		<input id="paybackPersonName" name="paybackPersonName" style="line-height:20px;border:1px solid #ccc"/>
		<span>付款人账号:</span>
		<input id="paybackPersonAccount" name="paybackPersonAccount" style="line-height:20px;border:1px solid #ccc"/>
		<br/>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="clearInfo()">清空</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="queryPaybackImport()">查询</a>
	</div>
	</div>  
	    <table id="tt" class="easyui-datagrid" 
				url="<%=request.getContextPath()%>/paybackImport/matchedPaybackImport.do"
				title="Searching" iconCls="icon-search" toolbar="#tb" onClickRow="clickRow"
				rownumbers="true" pagination="true">
			<thead>
				<tr>
				   
				    <th field="id" checkbox="true"></th>
				    <th field="importRunningDate" width="120">进项日期</th> 
				    <th field="paybackPersonName" width="120">付款人名称</th> 
				    <th field="importAmount" width="120">进项金额</th> 
				    <th field="moneyKind" width="120">币种</th> 
				    <th field="paybackPersonAcount" width="120">付款人账号</th> 
				    <th field="delegateTime" width="120" hidden="true">委托时间</th> 
				    <th field="notAllocateAmount" width="120">未分配金额</th>
				</tr>
			</thead>
		 </table> 
		 <!-- 还款详情 -->
   	 <jsp:include page="../paybackApply/paybackDetail.jsp"></jsp:include> 
  </body>
</html>
