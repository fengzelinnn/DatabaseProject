<%@ page import="java.sql.*" %><%--
  Created by IntelliJ IDEA.
  User: fengzelin
  Date: 2021/10/23
  Time: 3:41 下午
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
    <title>Result</title>
</head>
<body style="background: url(backgound.png) no-repeat; height: 100%; width: 100%; background-size: 100%">
<h1>报名以下项目</h1>
<%
    String[] Data = (String[]) session.getAttribute("Data");
%>
<h2>
    <%
        String connectionUrl = "jdbc:jtds:sqlserver://39.106.229.210:1433;" +
                "databaseName=DBProject;" +
                "user=bill;password=010620";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(connectionUrl);
            String sql;
            if(Data[3].equals("Stu")){
                sql = "SELECT Project FROM DBProject.dbo.ProjectsStu WHERE ID=?";
            } else {
                sql = "SELECT Project FROM DBProject.dbo.ProjectsTch WHERE ID=?";
            }
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,Data[0]);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String project = resultSet.getString(1);
    %>
    <%=project%><br>
    <%
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    %>
</h2>
<%
    session.setAttribute("Data", Data);
%>
<p><a href="index.jsp">返回首页</a> </p>
</body>
</html>
