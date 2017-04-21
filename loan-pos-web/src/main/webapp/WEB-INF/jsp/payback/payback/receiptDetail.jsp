<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="receipt" region="center" class="easyui-tabs" style="padding: 10px; background: #fff; border: 1px solid #ccc; maring: 0px auto;">
   	 <!-- 还款信息 -->
   	 <jsp:include page="../commonInfo/facilityUnifiedView.jsp"></jsp:include>
     <c:if test="${receipt.paybackWay eq '10' }">
     	<jsp:include page="../commonInfo/paybackPlanRecord.jsp"></jsp:include>
     </c:if>
     <!-- 还款计划 -->
     <jsp:include page="../commonInfo/paybackRecord.jsp"></jsp:include>
     <!-- 客户信息 -->
     <jsp:include page="../commonInfo/customerInfo.jsp"></jsp:include>
     <!-- 影响资料 -->
     <jsp:include page="../commonInfo/photoInfo.jsp"></jsp:include> 
</div>
<div region="south" border="false" style="text-align: center; height: 30px; line-height: 30px;margin-top: 20px">
	<c:if test="${mode=='create'}">
	<%-- 	<c:if test="${ overFlag != '0'}"> --%>
			<a id="btnConfirm" onclick="paybackConfirm()" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >确认还款</a>&nbsp;&nbsp;&nbsp;&nbsp;
   	<%-- 	</c:if> --%>
	</c:if>
    	<a id="btnClose" onclick="closeDetailWindow()" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" >关闭</a> 
</div>
<div id="tab-tools">
	<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'" onclick="closeDetailWindow()">关闭&nbsp;&nbsp; </a>
</div>

<script type="text/javascript">
$('#receipt').tabs({ 
    border:false, 
    onSelect:function(title,index){ 
       // #这里写你要怎么处理这个选中的tab 
       if(title=="还款记录"){		//还款记录
    	   $("#paybackRecord").datagrid({
       		url:"<%=request.getContextPath()%>/paybackImport/displayPaybackRunningRecord.do?receiptId=${receipt.receiptId}"
       	   })
       	}
       if(title=="还款计划"){        //还款计划
           $("#paybackPlanRecord").datagrid({
            url:"<%=request.getContextPath()%>/paybackApply/queryRePayPlansInPage.do?receiptId=${receipt.receiptId}"
           })
        }
	   	if(title=="影像资料"){		//影像资料
	   		var url = "<%=request.getContextPath()%>/navigation/loanPOSImageFiles.do?loanId=${contract.loanId}&custId=${receipt.custId}";
	   		$('#imageDataIframe1').attr("src", url);
	   	}
    },
    toolPosition:'left',
    tools: '#tab-tools'
})
</script>