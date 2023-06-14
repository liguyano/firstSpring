<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/05/07
  Time: 20:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path=request.getContextPath();
    String basePath= request.getScheme()+"://"+
            request.getServerName()+":"+
            request.getServerPort()+path;
%>
<html xmlns="http://www.w3.org/1999/html" lang="zh_CN">
<head>
    <meta charset="UTF-8">
    <script type="text/javascript" src="script/jquery-3.1.0.min.js"></script>
    <script type="text/javascript" src="register/login.js"></script>
    <title>登錄</title>
</head>
<body>
<span style="align-content: center">歡迎登錄</span>
<%--<form action="<%=basePath%>/checkp" method="post" id="loginform">--%>
<form id="loginform">
    <input type="hidden" action="login"/>
    <table align="center">
        <tr>
            <td>用戶名</td>
            <td><input type="text" name="username"/> </td>
        </tr>
        <tr>
            <td>密碼</td>
            <td><input type="password" name="password"> </td>
        </tr>
        <tr>
            <td><input type="button" value="登錄"/></td>
            <td><a href="register.jsp">沒有賬號，點這裡註冊</a> </td>
        </tr>
        <tr>
            <td id="gobyh" hidden="hidden"><a href="register.html">如未自動跳轉，點此手動跳轉</a></td>
        </tr>
    </table>
</form>

</body>
</html>