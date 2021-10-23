package Project;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "selectT", urlPatterns = {"/selectT.do"})
public class SelectT extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String connectionUrl = "jdbc:jtds:sqlserver://39.106.229.210:1433;" +
                "databaseName=DBProject;" +
                "user=bill;password=010620";
        String[] projects = request.getParameterValues("project");
        StringBuilder message;
        HttpSession session = request.getSession();
        String[] Data = (String[]) session.getAttribute("Data");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Connection test = null;
        Statement statement = null;
        boolean flag = false;
        Connection test2 = null;
        PreparedStatement preparedStatement1 = null;
        int num = 0;
        try {
            test = DriverManager.getConnection(connectionUrl);
            statement = test.createStatement();
            String TestSql = "SELECT ID,SUM(flag) FROM DBProject.dbo.ProjectsTch GROUP BY ID";
            ResultSet resultSet = statement.executeQuery(TestSql);
            while (resultSet.next()) {
                String ID = resultSet.getString(1);
                if (ID.equals(Data[0])) {
                    num = resultSet.getInt(2);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(connectionUrl);
            String TestSql2 = "SELECT Project FROM DBProject.dbo.ProjectsTch WHERE ID=?";
            preparedStatement1 = connection.prepareStatement(TestSql2);
            preparedStatement1.setString(1, Data[0]);
            ResultSet resultSet = preparedStatement1.executeQuery();
            message = new StringBuilder("重复报名，重复项：\n");
            while (resultSet.next()) {
                String res = resultSet.getString(1);
                for (String project : projects) {
                    String[] PROJ = project.split(";", 0);
                    if (PROJ[0].equals(res)) {
                        message.append(res).append("\n");
                        flag = true;
                    }
                }
            }
            if (flag) {
                request.setAttribute("message", message);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/SelectTch.jsp");
                requestDispatcher.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (projects.length + num > 3) {
            System.out.println(projects[0]);
            flag = true;
            message = new StringBuilder("选择多于三项...");
            request.setAttribute("message", message);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/SelectTch.jsp");
            requestDispatcher.forward(request, response);
        }
        if (projects.length == 0) {
            flag = true;
            message = new StringBuilder("请选择项目...");
            request.setAttribute("message", message);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/SelectTch.jsp");
            requestDispatcher.forward(request, response);
        }
        try {
            connection = DriverManager.getConnection(connectionUrl);
            for (String project : projects) {
                String sql = "INSERT INTO DBProject.dbo.ProjectsTch(ProjectId, Project, ID, Age, flag) " +
                        "VALUES (" + "?,?,?,?,1)";
                preparedStatement = connection.prepareStatement(sql);
                String[] PROJ = project.split(";", 0);
                preparedStatement.setString(1, PROJ[1]);
                preparedStatement.setString(2, PROJ[0]);
                preparedStatement.setInt(3, Integer.parseInt(Data[0]));
                preparedStatement.setFloat(3, Float.parseFloat(Data[2]));
                if (!flag){
                    preparedStatement.execute();
                }
            }
            session.setAttribute("Data", Data);
            request.getRequestDispatcher("SelectResult.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
