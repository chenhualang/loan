<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
	<% //String privileges = (String)session.getAttribute("assignedPrivileges"); 
	 com.hrbb.loan.web.security.entity.AccessPrivilege access = (com.hrbb.loan.web.security.entity.AccessPrivilege)session.getAttribute("accessPrivilege");
	%>
	
	<div id="tb" style="padding:5px;height:auto">  
	<div id="tb" style="padding:3px">
	<fieldset>
	<legend class='dialog-label'>查询条件</legend>
	<table width="800" border="0" cellspacing="1" cellpadding="0">
	  <tr>
	    <td width="90">申请编号:</td>
	    <td width="180"><input id="bizLoanId" name="bizLoanId" style="line-height:20px;border:1px solid #ccc"/></td>
	    <td width="90">客户名称:</td>
	    <td width="180"><input id="custName" name="custName" style="line-height:20px;border:1px solid #ccc"/></td>
	    <td width="90">客户证件号码:</td>
	    <td width="180"><input id="custIdNo" name="custIdNo" style="line-height:20px;border:1px solid #ccc"/></td>
	  </tr>
	  <tr>
	    <td colspan="6" align="center">
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="executeQeury()">查询</a>&nbsp;&nbsp;
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="clearSearch()">清空</a>&nbsp;&nbsp;
	    </td>
	  </tr>
	</table>
	</fieldset>
	</div>
	<% if(access.hasAnyPrivilege("ROLE_APPROVED;")) {%>
	<div id="tb">
	<c:if test="${approveStatus=='01' }">
    <a href="javascript:void(0)" id="02" class="easyui-linkbutton" iconCls="icon-ok" onclick="openConfirmationSign()">确认审批结果</a>&nbsp;&nbsp;
    <a href="javascript:void(0)" id="05" class="easyui-linkbutton" iconCls="icon-edit" onclick="openChangeApprove()">调整批复</a>&nbsp;&nbsp;
    </c:if>
    <%}%>
  <%--   <% if(access.hasAnyPrivilege("ROLE_APPROVED_ADMIN;")) {%> --%>
    <c:if test="${approveStatus=='09' || '03' == approveStatus }">
    <a href="javascript:void(0)" id="04" class="easyui-linkbutton" iconCls="icon-redo" onclick="bbb()">重新激活</a>  
    </c:if>
  <%--   <% } %> --%>
    
    </div>  
	
	</div>  