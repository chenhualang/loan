<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>银商同步文件下载</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/themes/icon.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/js/locale/easyui-lang-zh_CN.js'></script>
<script type="text/javascript">
        $(function(){
        	initSynFileList();
        	initExcuteDay();
        });
        
        function initSynFileList(){
			var queryUrl = '<%=request.getContextPath()%>/synFileUM/initSynFileList.do';
			$('#synFileT').datagrid({url:queryUrl,
        		onClickCell: function (rowIndex, field, value) { 
                    $(this).datagrid('unselectAll');
                }
			});
        }
        function initExcuteDay(){
	       var curr_time = new Date();
	  	   var strDate = curr_time.getFullYear()+"-";
	  	   strDate += curr_time.getMonth()+1+"-";
	  	   strDate += curr_time.getDate();
	  	   $("#excuteDay").datebox("setValue", strDate); 
        }
		function uploadSynFile() {
        	if (!checkSelected()){
        		return;
        	}
		    var row = $('#synFileT').datagrid('getSelected');
		    var fileName = row.fileName;
        	if (fileName.indexOf('_UM_') == -1){
        		alert("请选择银商报表！");
        		return;
        	}
			var reqUrl = '<%=request.getContextPath()%>/synFileUM/uploadSynFile.do';
		    $.messager.progress({
	 	        text: '上传中，请等待......',
	 	    }); 
			$.post(reqUrl, 
				{fileName: row.fileName}, 
				function(data){
					$.messager.progress('close');
					alert(data);
				}
			);
		}
		
        //检查是否只选择了记录
        function checkSelected(){
        	var rows = $('#synFileT').datagrid('getSelections');
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
        
    	function rowformater(value, row, index) {
			var values= value.split('|');
			var fileName = values[0];
			var fileURL = '<%=request.getContextPath()%>/'+values[1];
            return "<a href='"+fileURL+"'>"+fileName+"</a>";
        }
    	
		function makeSynFile() {
			var excuteDay = $('#excuteDay').datebox("getValue");
        	if (excuteDay == ''){
        		alert("请选择报表日期！");
        		return;
        	}
			var reqUrl = '<%=request.getContextPath()%>/synFileUM/makeSynFile.do';
			$.messager.progress({
				text : '生成中，请等待......',
			});
			$.post(reqUrl, {
				channel : $('#channel').combobox("getValue"),
				excuteDay : $('#excuteDay').datebox("getValue")
			}, function(data) {
				$.messager.progress('close');
				alert(data);
				initSynFileList();
			});
		}
</script>
</head>
<body>
	<table>
		<tr>
			<td>渠道:</td>
			<td><select id="channel" name="channel" data-options="width:100" class="easyui-combobox" editable=false>
					<c:forEach items="${bizChannel}" var="obj">
						<option value="${obj.itemNo}"
							<c:if test="${obj.itemNo == 'UM'}">selected</c:if>>${obj.itemName}</option>
					</c:forEach>
			</select></td>
			<td>报表日期:</td>
			<td><input type="text" id="excuteDay" name="excuteDay" class="easyui-datebox" size="10" editable=false /></td>
			<td><a href="javascript:void(0)" onclick="makeSynFile()" class="easyui-linkbutton" iconCls="icon-save" plain="true">生成报表</a></td>
		</tr>
	</table>
	<br />
	<a href="javascript:void(0)" onclick="uploadSynFile()" class="easyui-linkbutton" iconCls="icon-reload" plain="true">发送银商</a>
	<table id="synFileT" style="height:500px; width: 700px" toolbar="#tb" rownumbers="true">
		<thead>
			<tr>
				<th field="id" checkbox="true"></th>
				<th field="fileName" hidden=true>文件名</th>
				<th field="fileURL" width="250px" formatter="rowformater">文件名</th>
			</tr>
		</thead>
	</table>
</body>
</html>