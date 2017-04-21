<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
					<tr>
						<td style="width:15%;word-break;" class="tdtitle"><input type="hidden" id="editPosCustId" name="editPosCustId" value="${applyDetail[3].posCustId}"/>商户名称:</td>
						<td style="width:35%;word-break;"><input type="text" id="editPosCustName" size="30"
							name="editPosCustName" class="easyui-validatebox" data-options="required:true" value="${applyDetail[3].posCustName}"></td>
						<td style="width:15%;word-break;" class="tdtitle">主营业务:</td>
						<td style="width:35%;word-break;"><input type="text" id="editPosCustBusiScope" size="30"
							name="editPosCustBusiScope" class="easyui-validatebox" data-options="required:true" value="${applyDetail[3].posCustBusiScope}"></td>
					</tr>
					<tr>
						<td class="tdtitle">实际经营地址:</td>
						<td colspan="3"><table><tr>
							<td>
							<select id="editPosCustProSelect" name="editPosCustProSelect"  class="easyui-combobox" disabled>
									<option value="">--请选择(省/市)--</option>
									<c:forEach items="${province}" var="obj">
										<option value="${obj.itemNo}" <c:if test="${obj.itemNo == applyDetail[3].posCustProv}">selected</c:if>>${obj.itemName}</option>
									</c:forEach>
							</select>
							&nbsp;
							<select id="editPosCustCitySelect" name="editPosCustCitySelect" class="easyui-combobox" disabled>
								<option value="${applyDetail[3].posCustCity}">${applyDetail[3].posCustCityName}</option>
							</select>
							&nbsp;
							<select id="editBusiDivision" name="editBusiDivision" class="easyui-combobox" disabled>
								<option value="${applyDetail[3].operAddrCode}">${applyDetail[3].posCustDivisionName}</option>
							</select>
							&nbsp;(详细)
							<input type="text" id="editPosCustAddress"
							name="editPosCustAddress" class="easyui-validatebox" data-options="required:true" style="width:280px" value="${applyDetail[3].posCustAddress}">
							</td>
							</tr></table>
						</td>
					</tr>
					<tr>
						<td class="tdtitle">营业执照注册号:</td>
						<td><input type="text" id="editRegiCode" size="30" name="editRegiCode" class="easyui-validatebox" value="${applyDetail[3].regiCode}"></td>
						<td class="tdtitle">行内行业分类:</td>
						<td><select id="editIndustryTypeId2" name="editIndustryTypeId2"  class="easyui-combobox">
									<c:forEach items="${HrrbIndustTypeList}" var="obj">
										<option value="${obj.itemNo}" <c:if test="${obj.itemNo == applyDetail[3].industryTypeId2}">selected</c:if>>${obj.itemName}</option>
									</c:forEach>
							</select></td>
					</tr>
