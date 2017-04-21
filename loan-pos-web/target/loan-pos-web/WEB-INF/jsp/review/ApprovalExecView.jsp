<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div title="审批业务概要" width="100%">
<br/>
<fieldset style="width:1152px; padding:5px; color:#333; border:#06c dashed 1px;">
<legend class='dialog-label' style="color:#06c; font-weight:800; background:#fff;">业务申请</legend>
<table width="1152">
  <tr>
    <td width="150" class="tdtitle">业务编号：</td>
    <td width="180">${applyDetail[0].bizLoanId}
    <input type="hidden" id="loanIdForReviewNote" name="loanIdForReviewNote" value="${applyDetail[0].loanId}" ></td>
    <td width="150" class="tdtitle">申请金额：</td>
    <td width="180">${applyDetail[0].applyAmt}（元）</td>
    <td width="150" class="tdtitle">申请状态：</td>
    <td width="180">${applyDetail[0].applyStatusName}
    <input type="hidden" id="applyStatusCodeForReviewNote" name="applyStatusCodeForReviewNote" value="${applyDetail[0].applyStatus}" ></td>
    <td width="130">&nbsp;</td>
  </tr>
  <tr>
    <td class="tdtitle">进件渠道：</td>
    <td>${applyDetail[0].channelName}</td>
    <td class="tdtitle">经营地区：</td>
    <td>${applyDetail[0].regionName}</td>
    <td class="tdtitle">产品名称：</td>
    <td>${applyDetail[0].prodName}</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td class="tdtitle">银行卡号：</td>
    <td>${applyDetail[4].bankAccno}</td>
    <td class="tdtitle">开户银行：</td>
    <td>${applyDetail[4].bankNameStr}</td>
    <td class="tdtitle">还款方式：</td>
    <td>${applyDetail[0].returnKindName}</td>
    <td>&nbsp;</td>
  </tr>
</table>
</fieldset>
<br/>
<fieldset style="width:1152px; padding:5px; color:#333; border:#06c dashed 1px;">
<legend class='dialog-label' style="color:#06c; font-weight:800; background:#fff;">申请人信息</legend>
<table width="1152">
  <tr>
    <td width="150" class="tdtitle">申请人姓名：</td>
    <td width="180">${applyDetail[1].custName}</td>
    <td width="150" class="tdtitle">身份证号：</td>
    <td width="180">${applyDetail[1].paperId}</td>
    <td width="150" class="tdtitle">证件归属地：</td>
    <td width="180">${applyDetail[1].paperIdBelong}</td>
    <td width="130" rowspan="6"><table style="width:110px; height:140px" border="1" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td><div id="photo" align="center"><img src="<%=request.getContextPath()%>/creditApplyForReview/viewPhoto.do?certId=${applyDetail[1].paperId}"></div></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td class="tdtitle">性别：</td>
    <td>${applyDetail[1].sexSignName}</td>
    <td class="tdtitle">受教育程度</td>
    <td>${applyDetail[1].educSignnName}</td>
    <td class="tdtitle">婚姻状况：</td>
    <td>${applyDetail[1].marrSignName}</td>
  </tr>
  <tr>
    <td class="tdtitle">手机号码：</td>
    <td>${applyDetail[1].mobilePhone}</td>
    <td class="tdtitle">号码归属地：</td>
    <td>${applyDetail[1].mobileBelong}</td>
    <td class="tdtitle">属相：</td>
    <td>${applyDetail[1].zodiacName}</td>
  </tr>
  <tr>
  	<td class="tdtitle">联系地址：</td>
    <td>${applyDetail[1].contactAddrName}</td>
    <td class="tdtitle">居住地址：</td>
    <td colspan="2">${applyDetail[1].residentAddress}</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td class="tdtitle">商户名称</td>
    <td>${applyDetail[3].posCustName}</td>
    <td class="tdtitle">经营年限：</td>
    <td>${applyDetail[1].busiYear}（年）</td>
    <td class="tdtitle">办公电话：</td>
    <td>${applyDetail[1].tel}</td>
  </tr>
  <tr>
    <td class="tdtitle">经营地址：</td>
    <td colspan="2">${applyDetail[3].bizAddress}</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
      
</table>
</fieldset>
<br/>
<fieldset style="width:1152px; padding:5px; color:#333; border:#06c dashed 1px;">
<legend class='dialog-label' style="color:#06c; font-weight:800; background:#fff;">配偶/亲属信息</legend>
<table width="1152">
  <tr>
    <td width="150" class="tdtitle">配偶姓名</td>
    <td width="180">${applyDetail[1].familyCustName}</td>
    <td width="150" class="tdtitle">配偶身份证号：</td>
    <td width="180">${applyDetail[1].matePaperId}</td>
    <td width="150" class="tdtitle">证件归属地：</td>
    <td width="180">${applyDetail[1].matePaperIdBelong}</td>
    <td width="130">&nbsp;</td>
  </tr>
  <tr>
    <td class="tdtitle">配偶手机号码：</td>
    <td>${applyDetail[1].mateMobilephone}</td>
    <td class="tdtitle">号码归属地：</td>
    <td>${applyDetail[1].mateMobileBeolong}</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td width="150" class="tdtitle">亲属姓名</td>
    <td width="180">${applyDetail[2].relaCustName}</td>
    <td width="150" class="tdtitle">亲属类型：</td>
    <td width="180">${applyDetail[2].relaKindName}</td>
    <td width="150" class="tdtitle">联系电话：</td>
    <td width="180">${applyDetail[2].relaMobilePhone} / ${applyDetail[2].relaTel}</td>
    <td width="130">&nbsp;</td>
  </tr>
</table>
</fieldset>
<br/>
<c:if test="${'10'!=applyDetail[0].applyStatus 
	and '20'!=applyDetail[0].applyStatus 
	and '21'!=applyDetail[0].applyStatus
	and '30'!=applyDetail[0].applyStatus
	and '93'!=applyDetail[0].applyStatus}">
	<fieldset style="width:1152px; padding:5px; color:#333; border:#06c dashed 1px;">
	<legend class='dialog-label' style="color:#06c; font-weight:800; background:#fff;">模型结果</legend>
	<table width="1152">
	  <tr>
	    <td width="150" class="tdtitle">策略结果：</td>
	    <td width="180">${RiskModel.result07}</td>
	    <td width="150" class="tdtitle">建议额度（元）：</td>
	    <td width="180"><fmt:formatNumber value="${RiskModel.result08}" pattern="#,#00.00"/></td>
	    <td width="150" class="tdtitle">建议利率（%）：</td>
	    <td width="180"><fmt:formatNumber value="${RiskModel.result09}" pattern="#00.00"/></td>
	    <td width="130">&nbsp;</td>
	  </tr>
	  <tr>
	    <td class="tdtitle">是否尽调：</td>
	    <td>${RiskModel.result11}</td>
	    <td class="tdtitle">风险等级：</td>
	    <td>${RiskModel.result12}</td>
	    <td class="tdtitle">拒绝原因：</td>
	    <td colspan="3" word-break;>${RiskModel.result10}</td>
	  </tr>
	</table>
	</fieldset>
	<br/>
	<fieldset style="width:1152px; padding:5px; color:#333; border:#06c dashed 1px;">
	<legend class='dialog-label' style="color:#06c; font-weight:800; background:#fff;">经营、信用信息</legend>
	<table width="1152">
	  <tr>
	    <td width="150" class="tdtitle">POS月平均交易额(万)：</td>
	    <td width="180">${RiskModel.result24}</td>
	    <td width="150" class="tdtitle">人行最高授信额度(万)：</td>
	    <td width="180">${RiskModel.result15}</td>
	    <td width="150" class="tdtitle">近1个月负债(万)：</td>
	    <td width="180">${RiskModel.result18}</td>
	    <td width="130">&nbsp;</td>
	  </tr>
	  <tr>
	    <td class="tdtitle">总POS交易月份数：</td>
	    <td>${RiskModel.result26}</td>
	    <td class="tdtitle">未结清贷款余额(万)：</td>
	    <td>${RiskModel.result20}</td>
	    <td class="tdtitle">近2个月负债(万)：</td>
	    <td>${RiskModel.result34}</td>
	    <td>&nbsp;</td>
	  </tr>
	  <tr>
	  	<td>&nbsp;</td>
	    <td>&nbsp;</td>
	    <!-- 
	    <td class="tdtitle">POS月营业额(万)：</td>
	    <td>${RiskModel.result36}</td> -->
	    <td class="tdtitle">近半年新增信用贷款(万)：</td>
	    <td>${RiskModel.result40}</td>
	    <td class="tdtitle">近半年负债(万)：</td>
	    <td>${RiskModel.result35}</td>
	    <td>&nbsp;</td>
	  </tr>
	  <tr>
	    <td class="tdtitle">近6个月平均交易笔数：</td>
	    <td>${RiskModel.result42}</td>
	    <td class="tdtitle">未销户信用卡总额度(万)：</td>
	    <td>${RiskModel.result21}</td>
	    <td class="tdtitle">近一年负债(万)：</td>
	    <td>${RiskModel.result16}</td>
	    <td>&nbsp;</td>
	  </tr>
	  <tr>
	    <td class="tdtitle">近期交易月份数：</td>
	    <td>${RiskModel.result37}</td>
	    <td class="tdtitle">信用卡已用额度(万)：</td>
	    <td>${RiskModel.result22}</td>
	    <td class="tdtitle">月负债/月营业额:</td>
	    <td><fmt:formatNumber value="${RiskModel.result19}" pattern="#00.00"/></td>
	    <td>&nbsp;</td>
	  </tr>
	  <tr>
	    <td class="tdtitle">单笔最大金额(万)：</td>
	    <td>${RiskModel.result38}</td>
	    <td class="tdtitle">近半年新增信用卡额度(万)：</td>
	    <td>${RiskModel.result41}</td>
	    <td class="tdtitle">年负债/年营业额：</td>
	    <td><fmt:formatNumber value="${RiskModel.result17}" pattern="#00.00"/></td>
	    <td>&nbsp;</td>
	  </tr>
	  <tr>
	    <td class="tdtitle">波动率(%)：</td>
	    <td><fmt:formatNumber value="${RiskModel.result25 * 100}" pattern="#00.00"/> </td>
	    <td class="tdtitle">信用卡使用率(%)：</td>
	    <td><fmt:formatNumber value="${RiskModel.result23 * 100}" pattern="#00.00"/> </td>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
	  </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
	    <td class="tdtitle">近半年征信查询次数：</td>
	    <td>${RiskModel.result39}</td>
	    <td class="tdtitle">手机号码一致性：</td>
        <td>
          <c:choose>
            <c:when test="${RiskModel.result14 == 'Y'}">
                ${RiskModel.result14_Name}
            </c:when>
            <c:otherwise>
              <font color="red"><B>${RiskModel.result14_Name}</B></font>
            </c:otherwise>
          </c:choose>
        </td>
        <td>&nbsp;</td>
	  </tr>
	  <tr>
	    <td class="tdtitle">工作单位（最近）：</td>
	    <td>${RiskModel.result29}</td>
	    <td class="tdtitle">工作单位（次近）：</td>
	    <td>${RiskModel.result30}</td>
	    <td class="tdtitle">工作单位（次次近）：</td>
	    <td>${RiskModel.result31}</td>
	    <td>&nbsp;</td>
	  </tr>
	</table>
	</fieldset>
	<br/>
</c:if>
<%
	com.hrbb.loan.web.security.entity.AccessPrivilege access = (com.hrbb.loan.web.security.entity.AccessPrivilege)session.getAttribute("accessPrivilege");
	if(access.hasAnyPrivilege("ROLE_APPR_LV1;ROLE_APPR_LV2;ROLE_APPR_LV3;ROLE_APPR_LV4;ROLE_DUE_DILI")){		//只有复审人员才能看到意见和照会记录
%>
<table width="1152" style="height:200px">
  <tr>
    <td width="50%" valign="top">
	<fieldset style="width:563px;padding:5px; color:#333; border:#06c dashed 1px;">
		<legend class='dialog-label' style="color:#06c; font-weight:800; background:#fff;">审批意见</legend>
			<jsp:include page="OpinionSignView.jsp"/>		<!-- 意见签订视图 -->
	</fieldset>
	</td>
    <td width="50%" valign="top">
	<fieldset style="width:563px; padding:5px; color:#333; border:#06c dashed 1px;">
		<legend class='dialog-label' style="color:#06c; font-weight:800; background:#fff;">照会记录</legend>
			<table id="tReviewNote" class="easyui-datagrid" toolbar="#funcs"
			style="height:250px" iconCls="icon-search" onClickRow="clickRow" pagination="true">
				<thead>
					<tr>
						<th field="rn_id" checkbox="true"></th>
						<th field="reviewid" hidden="true">照会编号</th>
						<th field="teltype" width="80px">照会电话类型</th>
						<th field="tel" width="80px">照会电话号码</th>
						<th field="relationtype" hidden="true">照会对象</th>
						<th field="result" width="60px">核实结果</th>
						<th field="note" width="160px">情况说明</th>
						<th field="reviewdayStr" width="120px">照会时间</th>
						<th field="registrarname" width="60px">照会人</th>
					</tr>
				</thead>
			</table>
			<% 
			if(access.hasAnyPrivilege("ROLE_APPR_LV1;ROLE_APPR_LV2;ROLE_APPR_LV3;ROLE_APPR_LV4")){
			%>
		    <c:if test="${'31'==applyDetail[0].applyStatus 
							or '32'==applyDetail[0].applyStatus 
							or '33'==applyDetail[0].applyStatus
							or '34'==applyDetail[0].applyStatus
							or '35'==applyDetail[0].applyStatus
							or '40'==applyDetail[0].applyStatus
							or '41'==applyDetail[0].applyStatus}">
			<div id="funcs">
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="reviewNote(1)" iconCls="icon-add" plain="true">新增</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="reviewNote(2)" iconCls="icon-edit" plain="true">查看/修改</a>
				<a href="javascript:void(0)" onclick="delReviewNote()" class="easyui-linkbutton" iconCls="icon-cancel" plain="true">删除</a>
			</div>
			</c:if>
			<%} %>
	</fieldset>
	</td>
  </tr>
</table>
<%} %>
</div>
<script type="text/javascript">
	$(function() {
		queryReviewNote();
	  });
	//复审登录
	function reviewNote(flag){
		//更新
		if (flag == 2){
			var rows = $('#tReviewNote').datagrid('getSelections');
			var length = rows.length;
			if (length == 0){
			    alert("请选择要更新的记录！");
			    return;
			}else if (length > 1){
			    alert("请只选择一条要更新的记录！");
			    return;
			}
		}
		//var row = $('#t').datagrid('getSelected');
		var url = '<%=request.getContextPath()%>/reviewNote/openReviewEditView.do?loanId=${loanId}&direct2Path=review/reviewNote&flag=';
		var title = '照会记录';
		//新增
		if (flag == 1){
			url = url+'1';
			title = '添加'+title;
			//更新
		}else{
			var rowRN = $('#tReviewNote').datagrid('getSelected');
			url = url+'2&reviewId='+rowRN.reviewid;
			title = '更新'+title;
		}
		$('#reviewNoteWindow').openWin({
			title: title,
			modal : false,
			width: 550,
			height: 350,
			href: url,
			});
	}
	//检索复审照会
	function queryReviewNote(){
		var queryUrl="<%=request.getContextPath()%>/reviewNote/queryReviewNoteAll.do";
		//var row = $('#t').datagrid('getSelected');
		$('#tReviewNote').datagrid({url:queryUrl,
		    queryParams:{
			loanid: '${loanId}'
			},
			onDblClickRow:function(rowIndex, rowData) {
	        	openReviewNoteWindow(rowData);
	        }
		});
	}
	function openReviewNoteWindow(rowData){
		var applyStatusCode = $('#applyStatusCodeForReviewNote').val();
		if (applyStatusCode == '31' || applyStatusCode == '32' || applyStatusCode == '33' || applyStatusCode == '34'|| applyStatusCode == '40' || applyStatusCode == '41'){
			var url = "<%=request.getContextPath()%>/reviewNote/openReviewEditView.do?loanId=${loanId}&direct2Path=review/reviewNote&flag=2&reviewId="+rowData.reviewid;
			$('#reviewNoteWindow').openWin({
				title: '更新照会记录',
				modal : false,
				width: 550,
				height: 350,
				href: url,
			});
		}
	}
</script>
