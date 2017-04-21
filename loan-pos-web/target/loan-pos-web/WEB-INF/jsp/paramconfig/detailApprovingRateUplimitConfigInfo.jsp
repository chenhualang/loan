<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- 展业机构信息 -->
<div id="apprvRateUplimitDiv" title="机构信息"
  style="padding: 20px; margin: auto; width: 90%">
  <table style="width: 100%">
    <tr>
      <td style="width: 15%;" class="tdtitle">额度上限:</td>
      <td style="width: 35%;"><input type="text" class="amt_uplimit"
        name="amt_uplimit"
        <c:if test="${oprFlag=='3'}">readonly="readonly"</c:if>
        value="${tApprovingRateUplimitConfig.amt_uplimit}" size="30"></td>
      <td class="tdtitle">利率上限:</td>
      <td><input type="text" class="rate_uplimit easyui-validatebox" validType="amt" invalidMessage="必须填写数字"
        <c:if test="${oprFlag=='3'}">readonly="readonly"</c:if>
        value="${tApprovingRateUplimitConfig.rate_uplimit}" size="30"></td>
    </tr>
    <tr>
      <td class="tdtitle">是否生效:</td>
      <td><select id="rate_deleted_flag" style="width: 243px;"
        <c:if test="${oprFlag=='3'}">readonly="readonly"</c:if>
        class="easyui-combobox" validType="selectedRequired" required=true>
          <option value="">--请选择是否有效--</option>
          <c:forEach items="${deleteflagList}" var="obj">
            <option value="${obj.itemNo}"
              <c:if test="${obj.itemNo == tApprovingRateUplimitConfig.deleted_flag}">selected</c:if>>${obj.itemName}</option>
          </c:forEach>
      </select></td>
      <td class="tdtitle">生效时间:</td>
      <td><input type="text" class="valid_date easyui-datebox" data-options="required:true"
        name="valid_date"
        <c:if test="${oprFlag=='3'}">readonly="readonly"</c:if>
        value='<fmt:formatDate value="${tApprovingRateUplimitConfig.valid_date}" pattern="yyyy-MM-dd"/>'
        size="30"></td>
    </tr>
    <tr>
      <td class="tdtitle">失效时间:</td>
      <td><input type="text" class="invalid_date easyui-datebox" data-options="required:true"
        name="invalid_date"
        <c:if test="${oprFlag=='3'}">readonly="readonly"</c:if>
        value='<fmt:formatDate value="${tApprovingRateUplimitConfig.invalid_date}" pattern="yyyy-MM-dd"/>'
        size="30" /></td>
    </tr>
  </table>
  <br /> <br />
  <div style="text-align: center;display:${oprFlag=='3' ? 'none' : 'block'}">
    <input type="hidden" id="orgId" value="${institution.orgId}" /> <a
      href="javascript:void(0)" id="save" width="100px"
      class="easyui-linkbutton" iconCls="icon-ok"
      onclick="saveApprovingRateUplimitConfig(${oprFlag})">保存</a>&nbsp;&nbsp; <a
      href="javascript:void(0)" id="cancel" width="100px"
      class="easyui-linkbutton" iconCls="icon-no"
      onclick="cancelApprovingRateUplimitConfig(${oprFlag})">返回</a>
  </div>
</div>