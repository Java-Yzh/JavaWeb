<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
  <title>银行账户转账</title>
</head>
<body>
<form action="transfer" method="post">
  转出账户：<input type="text" name="fromActno"><br>
  转入账户：<input type="text" name="toActno"><br>
  转账金额：<input type="text" name="money"><br>
  <input type="submit" value="转账">
</form>
</body>
</html>

