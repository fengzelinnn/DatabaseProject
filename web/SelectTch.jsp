<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.ResultSet" %>
<%--
  Created by IntelliJ IDEA.
  User: fengzelin
  Date: 2021/10/23
  Time: 9:31 上午
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
    <title>报名</title>
</head>
<body style="background: url(backgound.png) no-repeat; height: 100%; width: 100%; background-size: 100%">
<form action="selectT.do" method="post">
    <%
        String[] Data = (String[]) session.getAttribute("Data");
    %>
    <h1>请选择您要报名的项目</h1>
    <h2>不得多于三项</h2>
    <h3>当前选择情况</h3>
    <%
        String connectionUrl = "jdbc:jtds:sqlserver://39.106.229.210:1433;" +
                "databaseName=DBProject;" +
                "user=bill;password=010620";
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();
            String sql;
            sql = "SELECT Project,SUM(flag) FROM DBProject.dbo.ProjectsTch GROUP BY Project";
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                String PROJECT = resultSet.getString("Project");
                int SUM = resultSet.getInt(2);
                System.out.println(PROJECT);
    %>
    <br><th><%=PROJECT%>有</th>
    <th><%=SUM%>人报名</th>
    <%
            }
        }catch (Exception e) {
            System.out.println();
            e.printStackTrace();
        }
    %>
    <h3>选择你的项目</h3>
    <label>
        <%
            Connection connection1 = null;
            Statement statement1 = null;
            try {
                connection1 = DriverManager.getConnection(connectionUrl);
                statement1 = connection1.createStatement();
                String sql1;
                sql1 = "SELECT ProjectName FROM DBProject.dbo.ProjInfo";
                ResultSet resultSet1 = statement1.executeQuery(sql1);
                while (resultSet1.next()) {
                    String PRJ = resultSet1.getString("ProjectName");

        %>
        <p><input type="checkbox" name="project" value="<%=PRJ%>"><%=PRJ%></p>
    </label>
    <%
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    %>
    <%
        session.setAttribute("Data", Data);
    %>
    <input type="submit" value="提交" style="width: 30%;
    height: 50px;
    border: none;
    padding-left: 3px;
    font-size: 18px;"><br>
</form>
</body>
</html>
