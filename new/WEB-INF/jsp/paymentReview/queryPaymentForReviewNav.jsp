<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>searing...</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/themes/icon.css" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src='<%=request.getContextPath()%>/js/locale/easyui-lang-zh_CN.js'></script>
	<script type="text/javascript" src='<%=request.getContextPath()%>/js/validator.js'></script>
	<script type="text/javascript" src='<%=request.getContextPath()%>/js/calendar.js'></script>
	<script type="text/javascript" src='<%=request.getContextPath()%>/js/paymentReview/paymentClaim.js'></script>
	<script type="text/javascript" src='<%=request.getContextPath()%>/js/paymentReview/paymentSign.js'></script>
	<script type="text/javascript" src='<%=request.getContextPath()%>/js/review/reviewNote.js'></script>
	<script type="text/javascript" src='<%=request.getContextPath()%>/js/common_uiext.js'></script>
	<script type="text/javascript">
	//任务认领
	function claimPaymentApplyApprovals() {
		var rows = $('#tc').datagrid('getSelections');
		var length = rows.length;
		if (length == 0){
		    alert("请选择要认领的任务！");
		    return;
		}
	    var reqUrl = "<%=request.getContextPath()%>/paymentReview/claim.do";
		var payApplyIds = "";
		var statuss = "";
		for(var i=0; i<length; i++){
			var last = length-1;
			if(i==last){
				payApplyIds=payApplyIds+rows[i].payApplyId;
				statuss=statuss+rows[i].status;
			}else{
				payApplyIds=payApplyIds+rows[i].payApplyId+",";
				statuss=statuss+rows[i].status+",";
			}
		}
		$.post(reqUrl, 
			    {payApplyIds: payApplyIds,
				statuss: statuss},
			    function(data){
					alert(data);
					$('#tc').datagrid('reload');
					closeClaimWindow();
					$('#tt').datagrid('reload');
			    },'text'
			);
	}
	//关闭窗口
	function closeClaimWindow() {
	    $('#paymentClaimWindow').window('close');
	    
	}
	
	//申请详情
	function detailPaymentApply(){
		if(!checkSelected()){
			return;
		}
		var rows = $('#tt').datagrid('getSelections');
		var payApplyId = rows[0].payApplyId;
		$('#detailPaymentApply').openWin({
			title:'用款详情-基本信息',
			href:'openDetailPaymentApply.do?payApplyId='+payApplyId+'&direct2Path=paymentApply/detailPaymentApply/detailPaymentApplyMain'
		})
		
	}
	//关闭详情窗口
	function colseDetailPaymentApply(){
		$("#detailPaymentApply").window("close");
	}
	//保存签署意见
	function savePaymentSign(){
		var reqUrl = "<%=request.getContextPath()%>/paymentReview/savePaymentSign.do";
		var payApplyId = $('#payApplyIdSign').val();
		var custName = $('#custName').val();
		var posCustName = $('#posCustName').val();
		var payApplyAmt = $('#payApplyAmt').val();
		var payApplyTerm = $('#payApplyTerm').val();
		var returnType = $('#returnType').val();
		var expectedDate = $('#expectedDate').val();
		var expectedEndDate = $('#expectedEndDate').val();
		var approvalStatus = $("input[name='approvalStatus']:checked").val()
		var approvalContent = $('#approvalContent').val();
		$.post(reqUrl,
				{payApplyId: payApplyId,
			payApplyAmt: payApplyAmt,
			payApplyTerm: payApplyTerm,
			expectedDate: expectedDate,
			expectedEndDate:expectedEndDate,
			approvalStatus: approvalStatus,
			approvalContent: approvalContent},
		   function(data){
			alert(data);
			$('#paymentSignWindow').window('close');
			$('#tt').datagrid('reload');
		},'text')
		
	}
	//提交签署意见
	function submitPaymentSign(){
		var reqUrl = "<%=request.getContextPath()%>/paymentReview/submitPaymentSign.do";
		var payApplyId = $('#payApplyIdSign').val();
		var custName = $('#custName').val();
		var posCustName = $('#posCustName').val();
		var payApplyAmt = $('#payApplyAmt').val();
		var payApplyTerm = $('#payApplyTerm').val();
		var returnType = $('#returnType').val();
		var expectedDate = $('#expectedDate').val();
		var expectedEndDate = $('#expectedEndDate').val();
		var approvalStatus = $("input[name='approvalStatus']:checked").val()
		var approvalContent = $('#approvalContent').val();
		var bankInfo = $('#bankInfo').val();
		var bankNameInfo = $("#bankInfo").find("option:selected").text(); 
		if(approvalContent == null || approvalContent == ""){
			alert('审批意见不能为空');
			return;
		}
		if(approvalStatus == "1"){
		    if(bankInfo == null || bankInfo == ""){
				alert('行号不能为空');
				return;
			} 
		}
		$.messager.progress({
 	        text: '提交中，请等待...',
 	    }); 
		$.post(reqUrl,
				{payApplyId: payApplyId,
			payApplyAmt: payApplyAmt,
			payApplyTerm: payApplyTerm,
			expectedDate: expectedDate,
			expectedEndDate:expectedEndDate,
			approvalStatus: approvalStatus,
			approvalContent: approvalContent,
			bankInfo: bankInfo,
			bankNameInfo: bankNameInfo},
		   function(data){
			alert(data);
			$.messager.progress('close');
			$('#paymentSignWindow').window('close');
			window.location.reload();
			$('#tt').datagrid('reload');
		},'text')
	}
	//判断是否选中记录
	function checkSelected(){
    	var rows = $('#tt').datagrid('getSelections');
    	var length = rows.length;
		if (length == 0){
		    alert("请选择一条记录！");
		    return false;
		}else if(length > 1){ 
		    alert("请只选择一条记录！");
		    return false;
		}else{
		    return true;
		}
    }
	// 人工生成借据
	function rowformater(rowIndex, field, value) {
     	   return "<a href='javascript:generateReceiptButton();'>生成借据</a>";
    }
	// 生成借据
	function generateReceiptButton(){
		var reqUrl = "<%=request.getContextPath()%>/paymentReview/generateReceiptController.do";
		var row = $('#tt').datagrid("getSelected");
		var payApplyId = row.payApplyId;
		$.post(reqUrl,
				{payApplyId: payApplyId},
		   function(data){
			alert(data);
		},'text')
	}
	// 查询行号
	function queryBankNoByBankName(){
		// 清空行号列表内容
		$('#bankInfo').empty();
		var reqUrl = "<%=request.getContextPath()%>/paymentReview/queryBankNameNo.do";
		var ptcptnm = $('#ptcptnm').val();
		$.post(reqUrl,{ptcptnm : ptcptnm},function(data){
			var bankInfoSet = data.bankInfoSet;
			var length = bankInfoSet.length;
			var ptcptnmAcct = '';
			var bkcd = '';
			 if(length != null && length > 0){
				 for(var i=0;i<length;i++){
					var bankArray = bankInfoSet[i].split(':');
					bkcd = bankArray[0];
					ptcptnmAcct = bankArray[1];
					// 动态添加查询行号结果（名称+行号）
					$('#bankInfo').append('<option value="' + bkcd + '">' + '('+ bkcd +')' + ptcptnmAcct + '</option>'); 
				} 
			}else{
				alert('没有对应行号');
			} 
		},'json')
		
	}
	// 初始化
	 $(function() {
	    	$('#tt').datagrid({
	    		onClickCell: function (rowIndex, field, value) { 
	                if(field != 'id'){
	                	$(this).datagrid('unselectAll');
	                }
	                
	            },
	    	});
	    	$('#tc').datagrid({
	    		onLoadSuccess:function(){
		   			$('#tc').datagrid("hideColumn","payApplyId");
		   		},
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
	<table id="tt" class="easyui-datagrid" title="searing..." iconCls="icon-search" toolbar="#tb" width="100%" height="1000px" onClickRow="clickRow" rownumbers="true" pagination="true"
			<c:if test="${reviewStatus == 0 }">
			 url="<%=request.getContextPath()%>/paymentReview/queryPaymentApplyCurr.do"
			 </c:if>
			 <c:if test="${reviewStatus == 1 }">
			 url="<%=request.getContextPath()%>/paymentReview/queryPaymentApplyFinished.do"
			 </c:if>
	>
		<thead>
			<tr>
				<th field="id" checkbox="true"></th>
				<th field="payApplyId" width="110px">用款编号</th>
				<th field="custName" width="110px">客户名称</th>
				<th field="posCustName" width="110px">商户名称</th>
				<th field="payApplyAmt" formatter="formatdecimal" width="110px">用款金额(元)</th>
				<th field="payApplyInterest" width="110px">用款利率(%)</th>
				<th field="payApplyTerm" width="110px">用款期限(月)</th>
				<th field="expectedDate" width="110px">期望用款日</th>
				<th field="expectedEndDate" width="110px">用款到期日</th>
				<th field="statusName" width="110px">申请状态</th>
				<c:if test="${reviewStatus == 1 }">
					<th field="receiptFlag" align="center"  formatter="rowformater">操作</th>
				</c:if>
			</tr>
		</thead>
	</table>
	<jsp:include page="paymentClaim.jsp"/><!-- 任务认领 -->
	<div id="detailPaymentApply"></div>	<!-- 申请详情 -->
	<jsp:include page="paymentSign.jsp"/><!-- 意见签署 -->
	<jsp:include page="searchPaymentForReview.jsp" />
</body>
</html>