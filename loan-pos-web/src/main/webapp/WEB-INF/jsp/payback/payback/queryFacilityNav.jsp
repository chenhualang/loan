<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>贷款业务台账管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/themes/icon.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common.css" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src='<%=request.getContextPath()%>/js/locale/easyui-lang-zh_CN.js'></script>
	<script type="text/javascript" src='<%=request.getContextPath()%>/js/common_uiext.js'></script>
    <script type="text/javascript">
    
    	function queryFacilityList(){
			var queryUrl="<%=request.getContextPath()%>/payback/queryPaybackInfo.do?bizStatus=${bizStatus}";
			$('#tt').datagrid(
				{url:queryUrl,
			    queryParams:{
			    	ReceiptIdLike: $('#ReceiptId').val(),
					custNameLike: $('#custName').val(),
					merchantNameLike: $('#merchantName').val(),
					custIdNoLike: $('#custIdNo').val(),
				},
				singleSelect:true
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
    		queryFacilityList();
    	}

    	function openReceiptDetail(){
    		if (!rowSelected('#tt')){
				return;
			}
        	var row = $('#tt').datagrid('getSelected');
			var reqUrl = '<%=request.getContextPath()%>/payback/openReceiptMain.do?receiptId='+row.receiptId;
			$('#receiptRelatedInfoWindow').openWin({
				title:'借据详情',
				maximized:false,
				width : 960,
				href:reqUrl
			})
    	}
    
    	function createPaybackApply(){
			if (!rowSelected('#tt')){
				return;
			}
			var row = $('#tt').datagrid('getSelected');
			var receiptId = row.receiptId;
			var reqUrl = '<%=request.getContextPath()%>/payback/openPaybackApply.do?receiptId='+receiptId;
			$('#receiptRelatedInfoWindow').openWin({
				title:'还款申请',
				maximized:false,
				width : 960,
				href:reqUrl
				});
    	}
    	
        function paybackConfirm(){
        	$.messager.confirm('系统提示', '确认发起还款申请?', function(r) {
            	if (r) {
            		confirm();
                }
            });
		}
        
       //下载结清证明
       function downloadFinishedCertificate(){
       	if (!rowSelected('#tt')){
  				return;
  			}
       	var row = $('#tt').datagrid('getSelected');
		var receiptId = row.receiptId;
		var url = "<%=request.getContextPath()%>/pdf/downloadFinishedCertificate.do?receiptId="+receiptId;
		download_file(url);
       }
       

          function confirm() {
            var paybackDate = $('#expectPaybackDate').datebox("getValue");
            var receiptId = getTextValue("#receiptId");
            var schKind = getRadioValue("kind");
            var paybackPrincipal = getNumberValue("#paybackPrincipal");
            var paybackInterest = getNumberValue("#paybackInterest");
            var paybackPenalty = getNumberValue("#paybackPenalty");
            var paybackTotalAmount = getNumberValue("#paybackTotalAmount");
            var term = $("#term").val();
            var loanpaybackway = $("#loanpaybackway").val();
            var reqUrl = "<%=request.getContextPath()%>/paybackApply/savePaybackApply.do";
    	   $.post(reqUrl,
   			   {receiptId: receiptId,
    		   expectPaybackDate: paybackDate,
    		   kind: schKind,
    		   paybackPrincipal: paybackPrincipal,
    		   paybackInterest: paybackInterest,
    		   paybackPenalty: paybackPenalty,
    		   paybackTotalAmount: paybackTotalAmount,
    		   term:term,
    		   loanpaybackway:loanpaybackway
    		   },
   			   function(data){
    			   if(data.returnCode=="000"){
    				   $.messager.alert("操作提示",data.returnMsg,"info");
    				   closeDetailWindow();
    			   }else{
    				   $.messager.alert("操作提示",data.returnMsg,"warning");
    			   }
   			   },'json');
       	}
          
   		function changeDate(newVal){
   			var schKind = getRadioValue("kind");
       		if(typeof(schKind)=="undefined" || schKind.trim().length==0){
       			return;
       		}
       		var schAmnt = getNumberValue("#paybackAmount");
       		if(typeof(schAmnt)=="undefined" || schAmnt.trim().length==0){
       			return;
       		}
       		calcSchedual(schKind,schAmnt,newVal);
   		}
   		
   		function changeAmnt(schAmnt){
   			var schKind = getRadioValue("kind");
       		if(typeof(schKind)=="undefined" || schKind.trim().length==0){
       			return;
       		}
       		if(typeof(schAmnt)=="undefined" || schAmnt.trim().length==0){
       			return;
       		}
       		var paybackDate = $('#expectPaybackDate').datebox("getValue");
       		calcSchedual(schKind,schAmnt,paybackDate);
   		}
   		
   		function changeKind(schKind){
   			if(schKind=="2"){		//剩余本金  
       			var bal = getNumberValue("#LoanTotalBalance");
       			setNumberValue("#paybackAmount",bal);
       		}
       		var schAmnt = getNumberValue("#paybackAmount");
       		if(typeof(schAmnt)=="undefined" || schAmnt.trim().length==0){
       			return;
       		}
       		var paybackDate = $('#expectPaybackDate').datebox("getValue");
       		calcSchedual(schKind,schAmnt,paybackDate);
   		}
   
	    function calcSchedual(schKind,schAmnt,paybackDate){
	    	var reqUrl = "<%=request.getContextPath()%>/payback/paybackCalculate.do";
	    	var loanAcNo = getTextValue("#loanAcNo");
	    	var receiptId = getTextValue("#receiptId");
	    	var paybackOverFlag = getTextValue("#paybackOverFlag");
			//disable button
			$("#btnConfirm").linkbutton("disable");
			$.post(reqUrl, 
				{
				paybackType: schKind,
				loanAcNo: loanAcNo,
				paybackAmt: schAmnt,
				receiptId: receiptId,
				paybackDate:paybackDate,
				paybackOverFlag:paybackOverFlag
				}, 
				function(data){
					var obj = data;
					setNumberValue("#paybackPrincipal",obj.capital);
					setNumberValue("#paybackInterest",obj.interest);
					setNumberValue("#paybackPenalty",obj.unpaidInterest);
					setNumberValue("#paybackTotalAmount",obj.totalAmount);
					$("#term").val(obj.term);
					$("#btnConfirm").linkbutton("enable");
				},'json');
	    
	    }
	    /*关闭窗口*/
		function closeDetailWindow(){
			$('#receiptRelatedInfoWindow').window('close'); 
		}
		/*查询还款计划*/
		function queryRepayPlan(type){
			if(type == 'single'){//归还最近分期
				var reqUrl = "<%=request.getContextPath()%>/paybackApply/getRecentRepayPlan.do";
				var receiptId = getTextValue("#receiptId");
				$.post(reqUrl, 
						{receiptId: receiptId}, 
						function(data){
							var obj = data;
							if(obj.respCode=="000"){
								$('#expectPaybackDate').datebox("setValue",obj.Scheddate);
								setNumberValue("#paybackPrincipal",obj.capital);
								setNumberValue("#paybackInterest",obj.interest);
								setNumberValue("#paybackPenalty",obj.unpaidInterest);
								setNumberValue("#paybackTotalAmount",obj.totalAmount);
								$("#term").val(obj.term);
							}else{
								$.messager.alert("操作提示","缺少还款计划.","warning");
							}
						},'json');
			}
			if(type == 'batch'){//等额本息提前结清
				var reqUrl = "<%=request.getContextPath()%>/paybackApply/getBatchRecentRepayPlan.do";
				var receiptId = getTextValue("#receiptId");
				$.post(reqUrl, 
						{receiptId: receiptId}, 
						function(data){
							var obj = data;
							if(obj.respCode=="000"){
								$('#expectPaybackDate').datebox("setValue",obj.Scheddate);
								setNumberValue("#paybackPrincipal",obj.capital);
								setNumberValue("#paybackInterest",obj.interest);
								setNumberValue("#paybackPenalty",obj.unpaidInterest);
								setNumberValue("#paybackTotalAmount",obj.totalAmount);
								$("#term").val(obj.term);
							}else{
								$.messager.alert("操作提示","缺少还款计划.","warning");
							}
						},'json');
			}
		}
    
		$(function() {
			<%
	    	com.hrbb.loan.web.security.entity.AccessPrivilege access = (com.hrbb.loan.web.security.entity.AccessPrivilege)session.getAttribute("accessPrivilege");
	    	String clearStatus = (String)session.getAttribute("clearStatus");
	    	if(!access.hasAnyPrivilege("ROLE_POSTED_QUERY") || (clearStatus!=null && !clearStatus.equals("99"))){	//具有全量查询权限时,初始化不进行查询
	    	%>
	    	queryFacilityList();
	    	<%}%>
	    	
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
	<div id="tb" style="padding:5px;height:auto">  
		<fieldset><legend class='dialog-label'>查询条件</legend>
		<div id="search" style="padding:3px">
			<span>业务编号:</span>
			<input id="ReceiptId" name="ReceiptId" style="line-height:20px;border:1px solid #ccc"/>
			<span>客户名称:</span>
			<input id="custName" name="custName" style="line-height:20px;border:1px solid #ccc"/>
			<span>商户名称:</span>
			<input id="merchantName" name="merchantName" style="line-height:20px;border:1px solid #ccc"/>
			<span>证件号码:</span>
			<input id="custIdNo" name="custIdNo" style="line-height:20px;border:1px solid #ccc"/>
			<br/>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="executeQeury()">查询</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="clearInfo()">清空</a>
		</div>
		</fieldset>
		<br/>
		<div id="func">   
	    	<a href="javascript:void(0)" id="01" class="easyui-linkbutton" iconCls="icon-edit" onclick="openReceiptDetail()">借据详情</a>&nbsp;&nbsp;
	    	<c:if test="${bizStatus=='02'}">
	    	<a href="javascript:void(0)" id="02" class="easyui-linkbutton" iconCls="icon-ok" onclick="createPaybackApply()">发起还款</a>&nbsp;&nbsp;
	    	</c:if>
          <c:if test="${bizStatus=='01'}">
        <a href="javascript:void(0)" id="03" class="easyui-linkbutton" iconCls="icon-ok" onclick="downloadFinishedCertificate()">下载结清证明</a>&nbsp;&nbsp;
        </c:if>
  		</div>  
	</div>  
    <table id="tt" class="easyui-datagrid"
			title="Searching" iconCls="icon-search" toolbar="#tb" onClickRow="clickRow"
			rownumbers="true" pagination="true">
		<thead>
			<tr>
			    <th field="id" checkbox="true"></th>
			    <th field="receiptId" width="120" hidden="true">借据编号</th> 
			    <th field="loanAcNo" width="120" >核心借据号</th>
			    <th field="contNo" width="120" hidden="true">合同编号</th> 
			    <th field="custName" width="80">客户名称</th>
				<th field="posCustName" width="180">商户名称</th>
				<th field="payApplyAmt" width="80" formatter="formatMoney" align="right">借据金额</th>
			    <th field="loanInterest" width="80" formatter="formatRate" align="right">贷款利率</th>
			    <th field="actualIssueDate" width="80" align="center" formatter="dateFormat">实际放款日期</th>
			    <th field="actualMaturity" width="80" align="center" formatter="dateFormat">实际到期日</th>
			    <th field="loanTotalBalance" width="80" formatter="formatMoney" align="right">贷款余额</th>
			    <th field="overdueBalance" width="80" formatter="formatMoney" align="right">逾期余额</th>
			    <th field="innerOwnedInterest" width="80" formatter="formatMoney" align="right">表内欠息</th>
			    <th field="outterOwnedInterest" width="80" formatter="formatMoney" align="right">表外欠息</th>
			    <th field="paybackWay" width="120" hidden="true">还款方式</th> 
			    <th field="paybackWayName" width="80" >还款方式</th> 
			    <th field="loanPaybackWay" width="80" hidden="true">贷款偿还方式</th> 
			    <th field="payApplyId" width="120" hidden="true">用款申请号</th>
			    <th field="custId" width="120" hidden="true">客户编号</th>
			    <c:if test="${bizStatus!='02'}">
			    <th field="finishDate" width="80" align="center" formatter="dateFormat">结清日期</th>
			    </c:if>
			</tr>
		</thead>
	</table> 
    <div id="receiptRelatedInfoWindow"></div>	<!-- 借据详情 -->
  </body>
</html>
