<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 还款记录 -->
<div title="还款记录" style="padding: 20px; width:90%;">
  <div style = "margin-bottom: 5px;">
    <a href="javascript:void(0)" id="03" class="easyui-linkbutton" iconCls="icon-ok" onclick="downloadPaymentCertificate()">下载还款凭证</a>&nbsp;&nbsp;
  </div>
	<table id="paybackRecord" class="easyui-datagrid" rownumbers="true" pagination="true">
		<thead>
			<tr>
                <th field="id" checkbox="true"></th>
				<th field="paybackRunningRecordId" hidden="true">还款记录编号</th>
				<th field="paybackApplyId" hidden="true">还款申请编号</th>
				<th field="receiptId" width="120">借据编号</th>
				<!-- <th field="supposedPaybackDate" width="80" formatter="dateFormat" align="center">应还日期</th>
				<th field="supposedTotalAmount" width="100" formatter="formatMoney" align="right">应还总金额</th>
				<th field="supposedCapital" width="80" formatter="formatMoney" align="right">应还本金</th>
				<th field="supposedInterest" width="80" formatter="formatMoney" align="right">应还利息</th> -->
				<th field="actualPaybackDate" width="80" formatter="dateFormat" align="center">实还日期</th>
				<th field="actualTotalAmount" width="100" formatter="formatMoney" align="right">实还总金额</th>
				<th field="actualCapital" width="80" formatter="formatMoney" align="right">实还本金</th>
				<th field="actualInterest" width="80" formatter="formatMoney" align="right">实还利息</th>
				<th field="balance" width="80"	formatter="formatMoney" align="right">剩余未还余额</th>
				<th field="paybackChannel" hidden="true">还款渠道</th>
				<th field="paybackImportId" hidden="true">还款进项编号</th>
				<th field="runningStatus" hidden="true">流水编号</th>
			</tr>
		</thead>
	</table>
</div>

<script type="text/javascript">
	
	//下载还款凭证
	function downloadPaymentCertificate(){
		if (!rowSelected('#paybackRecord')){
			return;
		}
		var row = $('#paybackRecord').datagrid('getSelected');
		var paybackRunningRecordId = row.paybackRunningRecordId;
		console.log("row = ",row);
		var url = "<%=request.getContextPath()%>/pdf/downloadPaymentCertificate.do?paybackRunningRecordId="+paybackRunningRecordId;
		download_file(url);
	}
</script>

