<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- 展业人员信息 -->
<div title="人员信息" style="padding:20px; margin:auto;width: 90%">
   	<table style = "width:100%">
   		<tr>
	   		<td style="width:15%;word-break;" class="tdtitle">人员名称:</td>
	   		<td style="width:35%;word-break;"><input type="text" class="menberName" name="menberName" value="${executor.menberName}" size="30"></td>
	   		<td class="tdtitle">身份证号:</td>
	   		<td><input type="text" class="certNo" name="certNo" value="${executor.certNo}" size="30"></td>
   		</tr>
   		<tr>
	   		<td class="tdtitle">出生日期:</td>
	   		<td><input type="text" class="birthDate" name="birthDate" value="<fmt:formatDate value='${executor.birthDate}' pattern='yyyy-MM-dd'/>" size="30"></td>
	   		<td class="tdtitle">性别:</td>
	   		<td><input type="text" class="sex" name="sex" value="${executor.sex}" size="30"></td>
   		</tr>
   		<tr>
	   		<td class="tdtitle">联系电话:</td>
	   		<td><input type="text" class="contactNo" name="contactNo"  value="${executor.contactNo}"/></td>
	   		<td class="tdtitle">所属机构:</td>
	   		<td>
	   		<select class="belongOrg" name="belongOrg">
	   				<c:if test="${executor.belongOrg eq 0 }">
	   					<option value="${executor.belongOrg}" selected>${executor.belongOrgName}</option>
	   				</c:if>
		    		<c:forEach items="${institutions}" var="i">
		    			<option value="${i.orgId}" <c:if test="${executor.belongOrg eq i.orgId}">selected</c:if>>${i.alias}</option>
		    		</c:forEach>
		   	 	</select>
	   		</td>
   		</tr>
   		<tr>
	   		<td class="tdtitle">电子邮箱:</td>
	   		<td><input type="text" class="email" name="email" value="${executor.email}" size="30"></td>
	   		<td class="tdtitle">家庭住址:</td>
	   		<td><input type="text" class="address" name="address" value="${executor.address}" size="30"></td>
    	</tr>
    	<tr>
	      	<td class="tdtitle">备注:</td>
			<td><input type="text" class="remark" name="remark" value="${executor.remark}" size="30"></td>
	   		<td class="tdtitle">状态:</td>
	   		<td>
	   		<c:if test="${oprFlagExecutor eq 0}"><!-- 新增 -->
	   		<input type="text" class="active" name="active" value="Y" size="30" readonly>
	   		</c:if>
	   		<c:if test="${oprFlagExecutor eq 1}"><!-- 修改 -->
	   		<input type="text" class="active" name="active" value="${executor.active}" size="30" readonly>
	   		</c:if>
	   		</td>
   		</tr>
   		<tr>
	   		<td class="tdtitle">最近修改时间:</td>
	   		<td><input type="text" class="modifyTime" name="modifyTime" value="<fmt:formatDate value='${executor.modifyTime}' pattern='yyyy-MM-dd'/>" size="30"></td>
    	</tr>
   	</table>
   	<br/><br/>
   	<div style="text-align: center">
   		<input type="hidden" id="orgId" value="${executor.menberId}"/>
	   	<a href="javascript:void(0)" id="save" width="100px" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveExecutor(${oprFlagExecutor})">保存</a>&nbsp;&nbsp;
	   	<a href="javascript:void(0)" id="cancel" width="100px" class="easyui-linkbutton" iconCls="icon-no" onclick="cancelExecutor(${oprFlagExecutor})">返回</a>
   	</div>
</div>