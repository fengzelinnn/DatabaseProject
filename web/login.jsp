<%--
  Created by IntelliJ IDEA.
  User: fengzelin
  Date: 2021/10/20
  Time: 8:20 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<style>
    body {
        margin: 0;
        background-color: white;
        font-family: 'PT Sans', Helvetica, Arial, sans-serif;
        text-align: center;
        color: rgba(39, 44, 53, 0.87);
    }
    div {

    }
    /*输入框样式，去掉背景阴影模仿原生应用的输入框*/

    input:focus {
        outline: none;
    }

    /*container*/
    #page_container {
        margin: 50px;
    }
</style>
<head>
    <meta charset="UTF-8">
    <title>login page</title>
</head>
<body style="background: url(backgound.png) no-repeat; height: 100%; width: 100%; background-size: 100%">
<h1>登录</h1>
<form action="login.do" method="post">
    <p>用户名:<br>
        <label>
            <input type="text" name="username" style="width: 15%;
    height: 50px;
    border: none;
    padding-left: 3px;
    font-size: 18px;">
        </label>
    </p>
    <p>密码:<br>
        <label>
            <input type="password" name="password"
                   style="width: 15%;
    height: 50px;
    border: none;
    padding-left: 3px;
    font-size: 18px;">
        </label>
    </p>
    <p>
        ${message}<br>
        <input type="submit" value="登录" style="width: 30%;
    height: 50px;
    border: none;
    padding-left: 3px;
    font-size: 18px;"><br>
    </p>
    <p>用户名为学号或工号，密码为号码后六位</p>
</form>
</body>
</html>
