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
			url="<%=request.getContextPath()%>/contractManagement/queryContractManagement.do?searchApproveStatus=${approveStatus}>"
			title="Searching" iconCls="icon-search" toolbar="#tb" onClickRow="clickRow"
			rownumbers="true" pagination="true">
		<thead>
			<tr>
			   
			    <th field="id" checkbox="true"></th>
			    <th field="approveId" width="120">批复编号</th> 
			    <th field="custName" width="120">客户名称</th>
				<th field="businessSource" width="120">业务来源</th>
				<th field="applyAmt" width="120" hidden="true">申请额度</th>
			    <th field="applyMoneyKind" width="120" hidden="true">申请币种</th>
			    <th field="termUnit" width="120" hidden="true">期限单位</th>
			    <th field="applyTerm" width="120" hidden="true">申请期限</th>
			    <th field="applyCommitDateStr" width="120" hidden="true">申请提交日期</th>  
			    <th field="acceptAccount" width="120">收款账号</th>
			    <th field="accountOpenBank" width="120">账户开户行</th>
				<th field="loanId" width="120" hidden="true">申请编号</th>
				<th field="custId" width="120">客户编号</th>
				<th field="approveAmount" width="120">批复额度</th>
				<th field="approveInterest" width="120" align="right" formatter="formatInterest">批复利率</th>
				<th field="approveTerm" width="120" align="right" formatter="formatApproveTerm">批复期限</th>
				<th field="approveDateStr" width="120">批复日期</th>
				<th field="paybackMethod" width="120">还款方式</th>  
			<!-- 	<th field="hh" width="150"  align="center" formatter="rowformater">操作</th> -->
			</tr>
		</thead>
	</table>
	<!-- 
	 <div id="confirmationSignWindow" class="easyui-window" title="批复详情" collapsible="false" minimizable="false"
        maximizable="true" icon="icon-save"  style="width: 1000px; height: 500px; padding: 5px;
        background: #fafafa;">
        <div class="easyui-layout" fit="true">
            <div region="center" class="easyui-tabs" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
                 <div title="协议信息" style="padding:20px;">
                	<table>
                		<tr>
                			<td>客户名称:<input type="text" id="insertSignCustName1" readonly= "true " name="insertSignCustName1"/></td>
                			<td>合同编号:<input type="text" id="insertSignContractId" readonly= "true " name="insertSignContractId"/></td>
                			
                		</tr>
                		<tr>
                		    <td>证件类型:<input type="text" id="insertSignIdType" readonly= "true " name="insertSignIdType"/></td>
                			<td>证件号码:<input type="text" id="insertSignIdNo" readonly= "true " name="insertSignIdNo"/></td> 
                		</tr>
                		<tr>
                		<td>授信额度:<input type="text" id="insertSignApproveAmount1" readonly= "true " name="insertvApproveAmount"/></td> 
                		<td>授信利率:<input type="text" id="insertSignApproveInterest1" readonly= "true " name="insertSignApproveInterest1"/></td> 
                		</tr>
                		<tr>
                        <td>授信期限:<input type="text" id="insertSignApproveTerm1" readonly= "true " name="insertSignApproveTerm1"/></td> 
                		<td>还款方式:<input type="text" id="insertSignPaybackMethod1" readonly= "true " name="insertSignPaybackMethod1"/></td> 
                		</tr>
                		<tr>
                		<td>协议生效日期:<input type="text"  id="insertSignContractBeginDate"  name="insertSignContractBeginDate"/></td> 
                		<td>协议到期日期:<input type="text"  id="insertSignContractEndDate" readonly= "true " name="insertSignContractEndDate"/></td>
                		</tr>
                		<tr>
                			<td>收款账号:<input type="text" id="insertSignAcceptAccount1" readonly= "true " name="insertSignAcceptAccount1"/></td>
                			<td>账户开户行:<input type="text" id="insertSignAccountOpenBank1" readonly= "true " name="insertSignAccountOpenBank1"/></td>
                			
                		</tr>
                	</table>
                </div>
                 <div title="批复信息" style="padding:20px;">
                	<table>
                	    <th>批复信息</th>
                		<tr>
                		<td>业务渠道:<input type="text" id="insertSignChannelName" readonly= "true " name="insertSignChannelName" readonly="readonly"value="展业"/></td>
                		<td>期限单位:<input type="text" id="insertSignTermUnit1" readonly= "true " name="insertSignTermUnit1"/></td>	
                		</tr>
                		<tr>
                		<td>申请额度:<input type="text" id="insertSignApllyAmount" readonly= "true " name="insertSignApllyAmount"/></td>
                		<td>申请币种:<input type="text" id="insertSignApplyMoneyKind" readonly= "true " name="insertSignApplyMoneyKind"/></td>	
                		</tr>
                		<tr>
                		<td>申请期限:<input type="text" id="insertSignApplyTerm1" readonly= "true " name="insertSignApplyTerm1"/></td>
                		<td>申请提交日期:<input type="text" id="insertSignApplyCommitDate" readonly= "true " name="insertSignApplyCommitDate"/></td>
                		</tr>
                		<tr>
                		<td>批复额度:<input type="text" id="insertSignApproveAmount2" readonly= "true " name="insertSignApproveAmount"/></td>
                		<td>批复利率:<input type="text" id="insertSignApproveInterest2" readonly= "true " name="insertSignApproveInterest2"/></td>
                		</tr>
                		<tr>
                		<td>批复期限:<input type="text" id="insertSignApproveTerm2" readonly= "true " name="insertSignApproveTerm2"/></td>
                		<td>批复日期:<input type="text" id="insertSignApproveDate" readonly= "true " name="insertSignApproveDate"/></td>
                		</tr>
                		<tr>
                		<td>收款账号:<input type="text" id="insertSignAcceptAccount2" readonly= "true " name="insertSignAcceptAccount2"/></td>
                		</tr>
                		<tr>
                		<td>账户开户行:<input type="text" id="insertSignAccountOpenBank2" readonly= "true " name="insertSignAccountOpenBank2"/></td>
                		</tr>
                		<tr>
                		<td>批复状态:<input type="text" id="insertSignApproveStatus" readonly= "true " name="insertSignApproveStatus"/></td>
                		</tr>
                	</table>
                </div>
                 <div title="客户基本信息" style="padding:20px;">
                	<table>
                		<tr>
                			<td>客户编号:<input type="text" id="insertSignCustId" readonly= "true " name="insertSignCustId"></td>
                			<td>客户名称:<input type="text" id="insertSignCustName2" readonly= "true " name="insertSignCustName2"></td>
  
                		</tr>
                		<tr>
                			<td>证件类型:<input type="text" id="insertSignCredentialtype" readonly= "true " name="insertSignCredentialtype"></td>
                			<td>证件号码:<input type="text" id="insertSignCredentialNo" readonly= "true " name="insertSignCredentialNo"></td>
                		</tr>
                		<tr>
                			<td>出生日期:<input type="text" id="insertSignBirthDate" readonly= "true " name="insertSignBirthDate"></td>
                			<td>性别:<input type="text" id="insertSignGender" readonly= "true " name="insertSignGender"></td>
                		</tr>
                			<tr>
                			<td>最高学历:<input type="text" id="insertSignHighestDegree" readonly= "true " name="insertSignHighestDegree"></td>
                			<td>婚姻状况:<input type="text" id="insertSignMarriageStatus" readonly= "true " name="insertSignMarriageStatus"></td>
                		</tr>
                			<tr>
                			<td>居住地址-省:<input type="text" id="insertSignProvince" readonly= "true " name="insertSignProvince"></td>
                			<td>居住地址-市:<input type="text" id="insertSignCity" readonly= "true " name="insertSignCity"></td>
                			<td>居住地址-具体:<input type="text" id="insertSignAddress" readonly= "true " name="insertSignAddress"></td>
                		</tr>
                			<tr>
                			<td>民族:<input type="text" id="insertSignNation" readonly= "true " name="insertSignNation"></td>
                			<td>子女人数:<input type="text" id="insertSignChildrenNo" readonly= "true " name="insertSignChildrenNo"></td>
                		</tr>
                			<tr>
                			
                			<td>现居住地址:<input type="text" id="insertSignCurrentAddress" readonly= "true " name="insertSignCurrentAddress"></td>
                		</tr>
                			<tr>
                			<td>手机号码:<input type="text" id="insertSignCellphoneNo" readonly= "true " name="insertSignCellphoneNo"></td>
                			<td>办公电话:<input type="text" id="insertSignOfficeTel" readonly= "true " name="insertSignOfficeTel"></td>
                		</tr>
                			<tr>
                			<td>微信号:<input type="text" id="insertSignWechatNo" readonly= "true " name="insertSignWechatNo"></td>
                			<td>QQ号:<input type="text" id="insertSignQQNo" readonly= "true " name="insertSignQQNo"></td>
                		</tr>
                			<tr>
                			<td>电子邮件:<input type="text" id="insertSignEmailAddress" readonly= "true " name="insertSignEmailAddress"></td> 		
                	</table>
                </div>
                 <div title="申请信息" style="padding:20px;">
                	<table>
                		<tr>
                			<td>申请编号:<input type="text" id="insertSignloanId" readonly= "true " name="insertSignloanId"/></td>
                			<td>申请额度:<input type="text" id="insertSignApplyAmount2" readonly= "true " name="insertSignApplyAmount2"/></td>
                			
                		</tr>
                		<tr>
                			<td>还款方式:<input type="text" id="insertSignPaybackMethod2" readonly= "true " name="insertSignPaybackMethod2"/></td>
                			<td>分期还款额:<input type="text" id="insertSignAmt" readonly= "true " name="insertSignAmt"/></td>  
                		</tr>
                		<tr>
                			<td>期限单位:<input type="text" id="insertSignTermUnit2" readonly= "true " name="insertSignTermUnit2"/></td> 
                			<td>申请期限:<input type="text" id="insertSignApplyTerm2" readonly= "true " name="insertSignApplyTerm2"/></td>             			
                		</tr>
                		
                		<tr>
                		<td>申请日期:<input type="text" id="insertSignBeginDate" readonly= "true " name="insertSignBeginDate"/></td>
                		<td>受理日期:<input type="text" id="insertSignAcceptDate" readonly= "true " name="insertSignAcceptDate"/></td>
                		</tr>
                		<tr>
                		<td>担保方式:<input type="text" id="insertSignAssureKind" readonly= "true " name="insertSignBeginDate"/></td>
                		<td>产品名称:<input type="text" id="insertSignProdName" readonly= "true " name="insertSignloanId"/></td>
                		</tr>
                		<tr>
                	
                		<td>账单日:<input type="text" id="insertSignReckDay" readonly= "true " name="insertvSignReckDay"/></td> 
                		<td>每期还款日:<input type="text" id="insertSignReturnDay" readonly= "true " name="insertSignReturnDay"/></td> 
                		</tr>
                		<tr>
                		<td>贷款用途:<input type="text" id="insertSignloanUsage" readonly= "true " name="insertSignloanUsage"/></td> 
                		<td>贷款用途说明:<input type="text" id="insertSignloanUsageDesc" readonly= "true " name="insertSignloanUsageDesc"/></td> 
                		<td>还款来源说明:<input type="text" id="insertSignreturnSourceDesc" readonly= "true " name="insertSignreturnSourceDesc"/></td> 
                		</tr>
                	</table>
                </div>
                 <div title="影像材料" style="padding:20px;">
                	<table>
                		<tr>
                			<td>配偶姓名:<input type="text" id="insertFamilyCustName" name="insertFamilyCustName"/></td>
                			<td><input type="hidden" id="insertMatePaperKind" name="insertMatePaperKind">证件类型:<input type="text" value="身份证" readonly="readonly"/></td>
                			<td>证件号码:<input class="easyui-validtebox" validtype="idcard" type="text" id="insertMatePaperId" name="insertMatePaperId"/></td>
                		</tr>
                		<tr>
                			<td>出生日期:<input class="easyui-datebox" type="text" id="insertMateBirtDate" name="insertMateBirtDate"/></td>
                			<td>性别:<select id="insertMateSexSign" name="insertMateSexSign">
                				<option value="01">
                					男
                				</option>
                				<option value="02">
                					女
                				</option>
                			</select></td>
                			<td>手机号:<input class="easyui-validatebox" validType="mobilePhone" invalidMessage="请输入正确的手机号" type="text" id="insertMateMobilePhone" name="insertMateMobilePhone"></td>
                		</tr>
                	</table>
                </div>
                
  
            </div>
            <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                <a id="btnEp" onclick="returnToMainWindow()" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >
                    确定</a> <a id="btnCancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)">取消</a>
            </div>
        </div>
    </div>
	
	 -->
	
	<!-- 
	<div id="tb" style="padding:5px;height:auto">  
	<div id="tb">   
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cut" plain="true" onclick="openConfirmationSign()">批复详情</a>
    </div>  
	
	
	
	
	<div id="tb" style="padding:3px">
		<span>申请编号:</span>
		<input id="loanId" name="loanId" style="line-height:20px;border:1px solid #ccc"/>
		<span>客户名称:</span>
		<input id="custName" name="custName" style="line-height:20px;border:1px solid #ccc"/>
		<span>商户名称:</span>
		<input id="merchantName" name="merchantName" style="line-height:20px;border:1px solid #ccc"/>
		<br/><br/>
		<span>客户编号:</span>
		<input id="custId" name="custId" style="line-height:20px;border:1px solid #ccc"/>
		<span>客户证件号码:</span>
		<input id="custIdNo" name="custIdNo" style="line-height:20px;border:1px solid #ccc"/>
		<span>批复状态:</span>
		<select id="searchApproveStatus" name="searchApproveStatus">
			<option value="01">未确认</option>
			<option value="02" selected="true">已确认</option>
			<option value="03">已拒绝</option>
			<option value="09">已失效</option>
		</select>
		<br/>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="queryContractManagement()">清空</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="queryContractManagement()">恢复</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="queryContractManagement()">查询</a>
	</div>
	</div>  
 -->
	<%-- <jsp:include page="sign/signcontract.jsp"></jsp:include> --%>
	<jsp:include page="head.jsp"></jsp:include>	
</body>
</html>