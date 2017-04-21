<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- 还款详情 -->
	<div id="paybackApplyTabs" region="center" class="easyui-tabs"
		style="padding: 10px; background: #fff; border: 1px solid #ccc; height: 691px">
		<jsp:include page="../commonInfo/paybackApplyInfo.jsp"></jsp:include>
		<div title="借据信息" style="padding: 20px;">
			<table>
				<jsp:include page="../commonInfo/receiptInfo.jsp"></jsp:include>
			</table>
		</div>
		<jsp:include page="../commonInfo/customerInfo.jsp"></jsp:include>
		<jsp:include page="../commonInfo/photoInfo.jsp"></jsp:include>
	</div>
	<div region="south" style="text-align: center; height: 30px; line-height: 30px;">
		<a id="btnEp" onclick="closePaybackDetailWindow()" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)">关闭</a>
	</div>
<script type="text/javascript">
$('#paybackApplyTabs').tabs({ 
    border:false, 
    onSelect:function(title,index){ 
       // #这里写你要怎么处理这个选中的tab 
	   	if(title=="影像资料"){		//影像资料
	   		var url = "<%=request.getContextPath()%>/navigation/loanPOSImageFiles.do?loanId=${contract.loanId}&custId=${receipt.custId}";
	   		$('#imageDataIframe1').attr("src", url);
	   	}
    },
    toolPosition:'left',
    tools: '#tab-tools'
})
</script>