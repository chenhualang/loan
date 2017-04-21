<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
	function openRiskzoneConfigListWindow(){
		if(!checkSelected){
			return;
		}
		var row = $('#tt').datagrid('getSelected');
		var id = row.id;
		if(id == null || id==''){
			alert("请选择一条记录");
			return;
		}
		var reqUrl = "<%=request.getContextPath()%>/modelParamConfig/riskzoneConfigdetail.do?id="+id;
		$('#listExecutorsWindow').openWin({
			title:'风险区域配置详情',
			maximized:false,
			width : 960,
			href:reqUrl
		})
	}
	/**打开新增风险区域配置窗口*/
	function openRiskzoneConfigWindow(){
		var reqUrpl = '<%=request.getContextPath()%>/modelParamConfig/openAddRiskZoneConfig.do';
		$('#addRiskzoneConfigWindow').openWin({
			title:'新增风险区域配置',
			maximized:false,
			width:800,
			height:400,
			top:($(window).height()-400)/2,
			href:reqUrpl
		})
	}
	/**新增展业机构*/
	function addRiskzoneConfigWindow(){
		var flag = validateForm("riskZoneDiv");
		if (!flag){
			$.messager.alert("操作提示","还有未填写的必输项或输入不符合要求","error");
			return;
		}
		var reqUrl = '<%=request.getContextPath()%>/modelParamConfig/addRiskZoneConfig.do';
		var region_code = $('#region_code').combobox('getValue');
		var risk_type = $('#risk_type').combobox('getValue');
		var deleted_flag_select = $('#deleted_flag_select').combobox('getValue');
//		var risk_type =$('#risk_type').val();
//		var deleted_flag = $('#deleted_flag_select').val();
		var valid_date = $('#valid_date').datebox("getValue");
		var invalid_date = $('#invalid_date').datebox("getValue");
		if(typeof(region_code) == 'undefined' || $.trim(region_code) == ''){
			alert('区域代码不能为空');
			return;
		}
		$.post(reqUrl,{
			region_code:region_code,
			risk_type:risk_type,
			deleted_flag:deleted_flag_select,
			valid_date:valid_date,
			invalid_date:invalid_date,
		},function(data){
			alert(data);
			$('#addRiskzoneConfigWindow').window('close');
			clearForm("#riskZoneDiv");
			$('#tt').datagrid('reload');
		},'text')	
	}
	/**打开修改展业机构窗口*/
	function openModifyriskzoneConfigWindow(){
		if(!checkSelected()){
			return;
		}
//		clearInstitutionWin();
		var row = $('#tt').datagrid('getSelected');
		var id = row.id;
		var reqUrpl = '<%=request.getContextPath()%>/modelParamConfig/openModifyRiskzoneConfigWindow.do?id='+id;
		$('#modifyRiskzoneConfigWindow').openWin({
			title:'修改风险区域配置信息',
			maximized:false,
			width:800,
			height:400,
			top:($(window).height()-400)/2,
			href:reqUrpl
		})
	}
	/**修改展业机构*/
	function modifyRiskzoneConfigWindow(){
		var flag = validateForm("riskZoneDiv");
		if (!flag){
			$.messager.alert("操作提示","还有未填写的必输项或输入不符合要求","error");
			return;
		}
		var row = $('#tt').datagrid('getSelected');
		var id = row.id;
		var reqUrl = '<%=request.getContextPath()%>/modelParamConfig/modifyRiskzoneConfigWindow.do';
		var region_code = $('#region_code').combobox('getValue');
		var risk_type = $('#risk_type').combobox('getValue');
		var deleted_flag_select = $('#deleted_flag_select').combobox('getValue');
		var valid_date = $('#valid_date').datebox("getValue");
		var invalid_date = $('#invalid_date').datebox("getValue");
		if(typeof(region_code) == 'undefined' || $.trim(region_code) == ''){
			alert('区域代码不能为空');
			return;
		}
		$.post(reqUrl,{
			id:id,
			region_code:region_code,
			risk_type:risk_type,
			deleted_flag:deleted_flag_select,
			valid_date:valid_date,
			invalid_date:invalid_date,
		},function(data){
			alert(data);
			$('#modifyRiskzoneConfigWindow').window('close');
			clearForm("#riskZoneDiv");
			$('#tt').datagrid('reload');
		},'text')	
	}
	
	function clearRiskzoneConfigWin(){
		$('.orgName').val('');
		$('.licenseNo').val('');
		
	}
	/**逻辑删除,将active状态改成失效(0)*/
	function deleteRiskzoneConfig(){
		if(!checkSelected()){
			return;
		}
		var row = $('#tt').datagrid('getSelected');
		var id = row.id;
		var reqUrl = "<%=request.getContextPath()%>/modelParamConfig/deleteRiskzoneConfig.do";
		$.post(reqUrl,{id:id},function(data){
			alert(data);
			$('#tt').datagrid('reload');
		},'text')
	}


	/**保存*/
	function saveRiskzoneConfig(data){
		if(data == '0'){//新增
			addRiskzoneConfigWindow();
		}else{//修改
			modifyRiskzoneConfigWindow();
		}
	}
	/**返回，关闭窗口*/	
	function cancelRiskzoneConfig(data){
		if(data == '0'){//新增
			$('#addRiskzoneConfigWindow').window('close');
		}else{//修改
			$('#modifyRiskzoneConfigWindow').window('close');
		}
		clearForm("#riskZoneDiv");
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
			url:"<%=request.getContextPath()%>/modelParamConfig/queryRiskZoneConfig.do",
			onClickCell: function (rowIndex, field, value) { 
                if(field != 'orgId'){
                	$(this).datagrid('unselectAll');
                }
            },
            onDblClickRow:function(rowIndex, rowData) {
            	/*打开指定机构人员列表窗口*/
            	openRiskzoneConfigListWindow();
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
		var reqUrl = '<%=request.getContextPath()%>/modelParamConfig/queryRiskZoneConfig.do';
			var regioncode = $('#regioncode').combobox('getValue');
			var risktype = $('#risktype').combobox('getValue');
			$('#tt').datagrid({
				url : reqUrl,
				queryParams : {
					region_code : regioncode,
					risk_type : risktype,
				}
			})
		}
	
	function saveRegion(orgId){
		var url="<%=request.getContextPath()%>/modelParamConfig/queryRiskZoneConfig.do";
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
		var reqUrl = '<%=request.getContextPath()%>/modelParamConfig/queryRiskZoneConfig.do';
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
		$('#regioncode').combobox('setValue', '');
		$('#risktype').combobox('setValue', '');
	}
	
	</script>
</head>
<body>
<div id="tb">
<fieldset>
	<legend class='dialog-label'>查询条件</legend>
		<table width="800" border="0" cellspacing="1" cellpadding="0">
		  <tr>
		    <td width="80" align="center">区域代码：</td>
		    <td width="180">
		    	<select id="regioncode" class="easyui-combobox" style="width:180px;">
		    		<option value="">--请选择--</option>
	    			<c:forEach items="${provinceList}" var="obj">
		    			<option value="${obj.itemNo}">${obj.itemName}</option>
		    		</c:forEach>
		    	</select>
		    	</td>
		    <td width="80" align="center">风险等级：</td>
		    <td width="180">
		    	<select id="risktype" class="easyui-combobox" style="width:180px;">
		    		<option value="">--请选择--</option>
		    		<c:forEach items="${regionRiskTypeList}" var="obj">
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
			<a href="javascript:void(0)" id="addExecutorBtn" width="100px" class="easyui-linkbutton" iconCls="icon-edit" onclick="openRiskzoneConfigWindow()">新增</a>&nbsp;&nbsp;
	    	<a href="javascript:void(0)" id="modifyExecutorBtn" width="100px" class="easyui-linkbutton" iconCls="icon-ok" onclick="openModifyriskzoneConfigWindow()">修改</a>&nbsp;&nbsp;
	    	<a href="javascript:void(0)" id="deleteExecutorBtn" width="100px" class="easyui-linkbutton" iconCls="icon-ok" onclick="deleteRiskzoneConfig()">删除</a>&nbsp;&nbsp;<!-- 逻辑删除 -->
		</div>
</div>
	<!-- 机构列表 -->
	<table id="tt" title="Searching"
		iconCls="scon-search" toolbar="#tb" onclickRow="clickRow"
		rownumbers="true" pagination="true" singleSelect="true">
		<thead>
			<tr>
			<th field="id" checkbox="true"></th>
			<th field="region_code" width="80px">地区</th>
			<th field="risk_type" width="150px">风险等级</th>
			<th field="deleted_flag" width="160px">是否生效</th>
			<th field="valid_date" width="100" formatter="dateFormat">生效时间</th>
		</tr>
		</thead>
	</table>
	<!-- 新增窗口 -->
	<div id="addRiskzoneConfigWindow"></div>
	<!-- 修改窗口 -->
	<div id="modifyRiskzoneConfigWindow"></div>
	<!-- 删除窗口，逻辑删除 -->
	<div id="deleteRiskzoneConfigWindow"></div>
	<!-- 展业人员列表窗口 -->
	<div id="listExecutorsWindow"></div>
	<!-- 展业机构管理窗口 -->
	<div id="regionWindow"></div>
	
</body>
</html>