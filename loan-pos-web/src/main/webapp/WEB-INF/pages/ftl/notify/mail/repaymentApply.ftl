<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>还款申请通知</title>
</head>

<body>
<p>Dear ${userName}:</p>
<p>一笔还款申请已经成功受理，请及时处理。</p>
<p>还款申请概况如下：</p>
<table width="800" border="1" cellspacing="0" cellpadding="0">
  <tr>
    <td width="9%">业务渠道</td>
    <td width="10%">客户姓名</td>
    <td width="23%">商户名称</td>
    <td width="14%">还款金额</td>
    <td width="14%">申请时间</td>
  </tr>
  <tr>
    <td>${channelName!'&nbsp;'}</td>
    <td>${custName!'&nbsp;'}</td>
    <td>${posCustName!'&nbsp;'}</td>
    <td>${applyAmt!'&nbsp;'}</td>
    <td>${beginDate!'&nbsp;'}</td>
  </tr>
</table>
<p>&nbsp;</p>
</body>
</html>

