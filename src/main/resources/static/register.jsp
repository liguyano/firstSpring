<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/04/22
  Time: 9:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path=request.getContextPath();
    String basePath= request.getScheme()+"://"+
            request.getServerName()+":"+
            request.getServerPort()+path;
%>
<html lang="zh_CN" xmlns="http://www.w3.org/1999/html">
<head>
    <meta http-equiv="content-type" charset="UTF-8">
    <title>註冊賬號</title>
    <script type="text/javascript" src="script/jquery-3.6.0.js"></script>
    <script type="text/javascript" src="register/registeCheck.js"></script>

</head>
<body>
<form action="<%=basePath%>/text3" method="post">
    <input type="hidden" name="action" value="login">
    <table align="center"><tr>
        <td>用戶名稱:</td>
        <td><input type="text" value="默認值" name="username"></td>
    </tr>
        <tr>
            <td>密碼:</td>
            <td><input type="password" value="默認值" maxlength="12" name="password"></td>
        </tr>
        <tr>
            <td>確認密碼:</td>
            <td><input type="password" value="默認值" maxlength="12" name="repassword" e></td>
            <td align="center"><input type="button" value="註冊"/></td>
        </tr>
        <tr>
            <td><a href="login.jsp">點這裡登錄</a> </td>
        </tr>
    </table>
</form>
</body>
</html>
