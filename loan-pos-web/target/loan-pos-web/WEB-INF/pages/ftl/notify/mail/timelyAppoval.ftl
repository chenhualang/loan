<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>审批结果通知</title>
</head>

<body>
<p> ${userName}:</p>
<p> 下表为 ${startTime}~${endTime}的审批结果，共计${totalNum}笔申请，请查收！ </p>
<table border="1" cellpadding="0" cellspacing="0" width="100%">
<tr>
  <td width="5%">申请日期</td>
      <td width="8%">渠道</td>
      <td width="10%">地区</td>
      <td width="10%">商户名称</td>
      <td width="6%">申请人姓名</td>
      <td width="6%">授信结果</td>
      <td width="8%">审批时间</td>
      <td width="6%">还款方式</td>
      <td width="6%">额度(万)</td>
      <td width="5%">利率(%)</td>
      <td width="10%">拒绝原因</td>
  </tr>
  <#list applyList as record> 
  <tr>
    <td>${record.beginDate?string("yyyy-MM-dd")}</td>
    <td><#if record.channel ? exists>${record.channel}<#else>&nbsp;</#if></td>
    <td><#if record.region ? exists>${record.region}<#else>&nbsp;</#if></td>
    <td><#if record.posCustName ? exists>${record.posCustName}<#else>&nbsp;</#if></td>
    <td><#if record.custName ? exists>${record.custName}<#else>&nbsp;</#if></td>
    <td><#if record.applyStatus ? exists>${record.applyStatus}<#else>&nbsp;</#if></td>
    <td><#if record.apprEndTime ? exists>${record.apprEndTime?string("yyyy-MM-dd HH:mm:ss")}<#else>&nbsp;</#if></td>
    <td><#if record.apprRepayMethod ? exists>${record.apprRepayMethod}<#else>&nbsp;</#if></td>
    <td><#if record.apprAmt ? exists>${record.apprAmt?string.number}<#else>&nbsp;</#if></td>
    <td><#if record.apprInt ? exists>${record.apprInt?string.number}<#else>&nbsp;</#if></td>
    <td><#if record.refuseReason ? exists>${record.refuseReason}<#else>&nbsp;</#if></td>
  </tr>
  </#list>
</table>
<p>&nbsp;</p>
</body>
</html>
