<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML>
<html lang="zh-CN"><!-- 指定页面语言 -->
<head>
	<title>展业机构管理</title>
	<meta http-equiv="pragma" content="no-cache"><!-- 禁止本地缓冲 -->
	<meta http-equiv="cache-control" content="no-cache"><!-- 清除缓冲 -->
	<meta http-equiv="expires" content="0"><!-- 过期时间 -->
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"><!-- 关键字，给搜索引擎用 -->
	<meta http-equiv="description" content="This is my page"><!-- 页面描述 -->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/themes/icon.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src='<%=request.getContextPath()%>/js/locale/easyui-lang-zh_CN.js'></script>
	<script type="text/javascript" src='<%=request.getContextPath()%>/js/common_uiext.js'></script>
	<script type="text/javascript">
	/*打开展业人员列表窗口*/
	function openApprovedRulesConfigListWindow(){
		if(!checkSelected){
			return;
		}
		var row = $('#tt').datagrid('getSelected');
		var id = row.id;
		if(id == null || id==''){
			alert("请选择一条记录");
			return;
		}
		var reqUrl = "<%=request.getContextPath()%>/approvedRulesConfig/approvedRulesConfigDetail.do?id="+id;
		$('#listExecutorsWindow').openWin({
			title:'风险区域配置',
			maximized:false,
			width : 960,
			href:reqUrl
		})
	}
	/**打开新增风险区域配置窗口*/
	function openApprovedRulesConfigWindow(){
		var reqUrpl = '<%=request.getContextPath()%>/approvedRulesConfig/openAddApprovedRulesConfig.do';
		$('#addApprovedRulesConfigWindow').openWin({
			title:'新增风险区域配置',
			maximized:false,
			width:800,
			height:400,
			top:($(window).height()-400)/2,
			href:reqUrpl
		})
	}
	/**新增展业机构*/
	function addApprovedRulesConfigWindow(){
		var flag = validateForm("apprvRuleDiv");
		if (!flag){
			$.messager.alert("操作提示","还有未填写的必输项或输入不符合要求","error");
			return;
		}
		var reqUrl = '<%=request.getContextPath()%>/approvedRulesConfig/addApprovedRulesConfig.do';
		   var ruleNo =$('.ruleNo').val();
			var region = $('#rule_region').combobox('getValue');
			var age = $('.age').val();
			var amt_uplimit = $('.amt_uplimit').val();
			var prodId = $('#rule_prodId').combobox('getValue');
			var channel = $('#rule_channel').combobox('getValue');
			var deleted_flag = $('#rule_deleted_flag').combobox('getValue');
			var valid_date = $('.valid_date').datebox("getValue");
			var invalid_date = $('.invalid_date').datebox("getValue");
		$.post(reqUrl,{
			ruleNo:ruleNo,
			prodId:prodId,
			channel:channel,
			region:region,
			age:age,
			amt_uplimit:amt_uplimit,
			deleted_flag:deleted_flag,
			region:region,
			valid_date:valid_date,
			invalid_date:invalid_date
		},function(data){
			alert(data);
			$('#addApprovedRulesConfigWindow').window('close');
			clearForm("#apprvRuleDiv");
			$('#tt').datagrid('reload');
		},'text')	
	}
	/**打开修改展业机构窗口*/
	function openModifyApprovedRulesConfigWindow(){
		if(!checkSelected()){
			return;
		}
//		clearInstitutionWin();
		var row = $('#tt').datagrid('getSelected');
		var id = row.id;
		var reqUrpl = '<%=request.getContextPath()%>/approvedRulesConfig/openModifyApprovedRulesConfigWindow.do?id='+id;
		$('#modifyApprovedRulesConfigWindow').openWin({
			title:'修改批复规则配置信息',
			maximized:false,
			width:800,
			height:400,
			top:($(window).height()-400)/2,
			href:reqUrpl
		})
	}
	/**修改展业机构*/
	function modifyApprovedRulesConfigWindow(){
		var flag = validateForm("apprvRuleDiv");
		if (!flag){
			$.messager.alert("操作提示","还有未填写的必输项或输入不符合要求","error");
			return;
		}
		var row = $('#tt').datagrid('getSelected');
		var id = row.id;
		var reqUrl = '<%=request.getContextPath()%>/approvedRulesConfig/modifyApprovedRulesConfigWindow.do';
		var ruleNo =$('.ruleNo').val();
        var prodId = $('#rule_prodId').combobox('getValue');
		var channel = $('#rule_channel').combobox('getValue');
		var deleted_flag = $('#rule_deleted_flag').combobox('getValue');
		var region = $('#rule_region').val();
		var age = $('.age').val();
		var amt_uplimit = $('.amt_uplimit').val();
		var valid_date = $('.valid_date').datebox("getValue");
		var invalid_date = $('.invalid_date').datebox("getValue");
		$.post(reqUrl,{
			id:id,
			ruleNo:ruleNo,
			prodId:prodId,
			channel:channel,
			region:region,
			age:age,
			amt_uplimit:amt_uplimit,
			deleted_flag:deleted_flag,
			region:region,
			valid_date:valid_date,
			invalid_date:invalid_date
		},function(data){
			alert(data);
			$('#modifyApprovedRulesConfigWindow').window('close');
			clearForm("#apprvRuleDiv");
			$('#tt').datagrid('reload');
		},'text')	
	}
	
	function clearApprovedRulesConfigWin(){
		$('.orgName').val('');
		$('.licenseNo').val('');
		
	}
	
	function deleteApprovedRulesConfig(){
		if(!checkSelected()){
			return;
		}
		var row = $('#tt').datagrid('getSelected');
		var id = row.id;
		var reqUrl = "<%=request.getContextPath()%>/approvedRulesConfig/deleteApprovedRulesConfig.do";
		$.post(reqUrl,{id:id},function(data){
			alert(data);
			$('#tt').datagrid('reload');
		},'text')
	}


	/**保存*/
	function saveApprovedRulesConfig(data){
		if(data == '0'){//新增
			addApprovedRulesConfigWindow();
		}else{//修改
			modifyApprovedRulesConfigWindow();
		}
	}
	/**返回，关闭窗口*/	
	function cancelApprovedRulesConfig(data){
		if(data == '0'){//新增
			$('#addApprovedRulesConfigWindow').window('close');
		}else{//修改
			$('#modifyApprovedRulesConfigWindow').window('close');
		}
		clearForm("#apprvRuleDiv");
		$('#tt').datagrid('reload');
	}
	/**检查是否只选择了记录*/
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
	/**初始化*/
	$(function(){
		$("#tt").datagrid({
			url:"<%=request.getContextPath()%>/approvedRulesConfig/queryApprovedRulesConfig.do",
			onClickCell: function (rowIndex, field, value) { 
                if(field != 'orgId'){
                	$(this).datagrid('unselectAll');
                }
            },
            onDblClickRow:function(rowIndex, rowData) {
            	/*打开指定机构人员列表窗口*/
            	openApprovedRulesConfigListWindow();
            }
		})
	})
	
	/**根据省份获取下级市*/
	function provinceChange(){
		var provinceCode = $('#province').val();
		var url ="<%=request.getContextPath()%>/provinceCity/getCity.do";
		$.getJSON(url,{ itemNo: provinceCode },function(result){
			$("#city").empty();
			var html = "<option value=''>--请选择(市)--</option>"
		    $.each(result, function(i, field){
		    	var option = "<option value='"+ field.itemNo +"'>" + field.itemName + "</option>";
		    	html += option;
		    });
			$("#city").html(html);
		 });
	}
	
	/**查询*/
	function executeQeury(){
		var reqUrl = '<%=request.getContextPath()%>/approvedRulesConfig/queryApprovedRulesConfig.do';
			var ruleNo = $('#ruleNo').val();
			var prodId = $('#prodId').combobox("getValue");
			$('#tt').datagrid({
				url : reqUrl,
				queryParams : {
					ruleNo : ruleNo,
					prodId : prodId,
				}
			})
		}
	
	function saveRegion(orgId){
		var url="<%=request.getContextPath()%>/approvedRulesConfig/queryApprovedRulesConfig.do";
		var provinceCode = $('#province').val();
		var cityCode = $('#city').val();
		var sts = $('input[name="include"]:checked').val();
		var regionNo;
		if(provinceCode==''){
			alert("请选择省份！");
			return false;
		}
		if(sts==null){
			alert("请选择包含状态！");
			return false;
		}
		if(provinceCode!='' && cityCode==''){
			if(sts=='0'){
				alert("省份不可选择不包含！");
				return false;
			}
			regionNo = provinceCode;
		}
		
		if(cityCode!=''){
			regionNo = cityCode;
		}
		
		$.post(url,{regionNo: regionNo, include:sts,orgId:orgId},function(data){
			alert(data);
			$('#rt').datagrid('reload');
		},'text');
		
		
	}
	
	function delRegion(orgId){
		if(!checkRegionSelected()){
			return;
		}
		if(!confirm("确定要删除数据吗？")){
			return false;
		}
		var row = $('#rt').datagrid('getSelected');
		var region = row.region;
		var reqUrl = '<%=request.getContextPath()%>/approvedRulesConfig/queryApprovedRulesConfig.do';
		$.post(reqUrl,{region: region, orgId:orgId},function(data){
			alert(data);
			$('#rt').datagrid('reload');
		},'text');
	}
	
	
	function checkRegionSelected(){
    	var rows = $('#rt').datagrid('getSelections');
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
	function queryBlank(){
		$("#prodId").combobox("setValue", "");
		$("#ruleNo").val("");
	}
	
	</script>
</head>
<body>
<div id="tb">
<fieldset>
	<legend class='dialog-label'>查询条件</legend>
		<table width="800" border="0" cellspacing="1" cellpadding="0">
		  <tr>
		    <td width="80" align="center">规则编号：</td>
		    <td width="180"><input id="ruleNo" name="ruleNo" style="line-height:20px;border:1px solid #ccc"/></td>
		    <td width="80" align="center">产品代码：</td>
		    <td width="180">
		    	<select id="prodId" class="easyui-combobox" style="width:180px;">
		    		<option value="">--请选择--</option>
		    		<c:forEach items="${productNoList }" var="obj">
		    			<option value="${obj.itemNo }">${obj.itemName }</option>
		    		</c:forEach>
		    	</select>
		    </td>
		  </tr>
		  <tr><td colspan="6">&nbsp;</td></tr>
		  <tr>
		    <td colspan="6" align="center">
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="executeQeury()">查询</a>&nbsp;&nbsp;
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="queryBlank()">清空</a>&nbsp;&nbsp;
		    </td>
		  </tr>
		</table>
	</fieldset>
	<!-- 操作按钮 -->
		<div  style="padding: 5px; height: auto"> 
			<a href="javascript:void(0)" id="addExecutorBtn" width="100px" class="easyui-linkbutton" iconCls="icon-edit" onclick="openApprovedRulesConfigWindow()">新增</a>&nbsp;&nbsp;
	    	<a href="javascript:void(0)" id="modifyExecutorBtn" width="100px" class="easyui-linkbutton" iconCls="icon-ok" onclick="openModifyApprovedRulesConfigWindow()">修改</a>&nbsp;&nbsp;
	    	<a href="javascript:void(0)" id="deleteExecutorBtn" width="100px" class="easyui-linkbutton" iconCls="icon-ok" onclick="deleteApprovedRulesConfig()">删除</a>&nbsp;&nbsp;<!-- 逻辑删除 -->
		</div>
</div>
	<!-- 机构列表 -->
	<table id="tt" title="Searching"
		iconCls="scon-search" toolbar="#tb" onclickRow="clickRow"
		rownumbers="true" pagination="true" singleSelect="true">
		<thead>
			<tr>
			<th field="id" checkbox="true"></th>
			<th field="ruleNo" width="80px">规则编号</th>
			<th field="prodId" width="150px">产品代码</th>
			<th field="channel" width="100px">渠道</th>
			<th field="region" width="160px">地区</th>
			<th field="age" width="160px">年龄</th>
			<th field="amt_uplimit" width="160px">额度上限</th>
			<th field="deleted_flag" width="160px">是否生效</th>
			<th field="valid_date" width="100" formatter="dateFormat">生效时间</th>
			<th field="invalid_date" width="100" formatter="dateFormat">失效时间</th>
		</tr>
		</thead>
	</table>
	<!-- 新增窗口 -->
	<div id="addApprovedRulesConfigWindow"></div>
	<!-- 修改窗口 -->
	<div id="modifyApprovedRulesConfigWindow"></div>
	<!-- 删除窗口，逻辑删除 -->
	<div id="deleteApprovedRulesConfigWindow"></div>
	<!-- 展业人员列表窗口 -->
	<div id="listExecutorsWindow"></div>
	<!-- 展业机构管理窗口 -->
	<div id="regionWindow"></div>
	
</body>
</html>