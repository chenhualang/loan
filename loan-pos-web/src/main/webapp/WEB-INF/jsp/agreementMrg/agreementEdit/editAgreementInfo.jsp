<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div title="协议详情" style="padding:</td><td>20px;">
						<input type="hidden" id="beforeEditCreditLine" />
						<input type="hidden" id="beforeEditContTerm" />
						<input type="hidden" id="beforeEditReturnKind" />
						<input type="hidden" id="beforeEditAgreementEndDate" />
						<input type="hidden" id="beforeEditBankAccount" />
						<input type="hidden" id="beforeEditAccountOpenBank" />
						<input type="hidden" id="beforeEditAccountBranchBank" />
						<input type="hidden" id="beforeEditAccountSubBranchBank" />
						<input type="hidden" id="hideAccountOpenBank" />
                	<table width="100%">
                		<tr>
                			<td style="width:15%;word-break;" class="tdtitle">客户名称:</td><td style="width:35%;word-break;"><input type="text" id="editCustName1" readonly="readonly" class="detailCustName"/></td>
                			<td class="tdtitle">合同编号:</td><td><input type="text" id="editContNo" readonly="readonly" class="detailContNo"/></td>
                		</tr>
                		<tr>
                		    <td class="tdtitle">证件类型:</td><td><select disabled="disabled" id="editPaperKind1"
							name="editPaperKind" class="detailPaperKind">
								<c:forEach items="${paperList}" var="obj">
									<option value="${obj.itemNo}">${obj.itemName}</option>
								</c:forEach>
							</select></td>
                			<td class="tdtitle">证件号码:</td><td><input type="text" id="editPaperId1" readonly="readonly" class="detailPaperId"/></td> 
                		</tr>
                		<tr>
                		<td class="tdtitle">授信额度:</td><td><input type="text" id="editCreditLine" class="detailCreditLine"/></td> 
                		<td class="tdtitle">授信利率:</td><td><input type="text" id="editCreditInterest" readonly class="detailCreditInterest"/></td> 
                		</tr>
                		<tr>
                        <td class="tdtitle">授信期限:</td><td><input type="text" id="editContTerm"  class="detailContTerm" onblur="calculateStdMaturity()"/></td> 
                		<td class="tdtitle">还款方式:</td><td><select id="editReturnKind" name="editReturnKind" class="detailReturnKind">
								<c:forEach items="${returnKindList}" var="obj">
									<option value="${obj.itemNo}">${obj.itemName}</option>
								</c:forEach>
							</select></td> 
                		</tr>
                		<tr>
                		<td class="tdtitle">协议生效日期:</td><td><input type="text"  id="editAgreementBiginDate"  class="easyui-datebox" disabled/></td> 
                		<td class="tdtitle">协议到期日期:</td><td><input type="text"  id="editAgreementEndDate"  class="easyui-datebox" readonly/></td>
                		</tr>
                		<tr>
			       		<td class="tdtitle">银行账号:</td>
			       		<!-- <td><input type="text" id="newBankAccount" name="newBankAccount"/></td> -->
			       		<td><input id="newBankAccount" name="newBankAccount" class="easyui-validatebox" 
							size="30" maxlength="30" data-options="required:true" onChange="javascript:valiateBankCard(this);"
							validType="number[8,20]" invalidMessage="账号填写错误"/></td>
			       		<td class="tdtitle">开户行:</td>
			       		<td>
			       		<!-- <input type="text" id="newAccountOpenBank" name="newAccountOpenBank"/> -->
			       		<select	id="newAccountOpenBank" name="newAccountOpenBank" 
								data-options="width:150"
								class="easyui-combobox" validType="selectedRequired" required=true editable=false>
								<option value="">--请选择开户行--</option>
								<c:forEach items="${bankNoList}" var="obj">
									<option value="${obj.itemNo}">${obj.itemName}</option>
								</c:forEach>
			           </select>
			       		</td>
      				 	</tr>
						<tr>
			       		<td class="tdtitle">账户分行:</td><td><input type="text" id="newAccountBranchBank" name="newAccountBranchBank"/></td>
			       		<td class="tdtitle">账户支行:</td><td><input type="text" id="newAccountSubBranchBank" name="newAccountSubBranchBank"/></td>
				       	</tr>
                		<tr class="tdtitle"><td>调整原因:</td><td colspan="3"><input type="text" id="editReason" name="editReason"/></td></tr>
                	</table>
                </div>