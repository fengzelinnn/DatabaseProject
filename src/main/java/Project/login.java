package Project;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

@WebServlet(name = "login", urlPatterns = {"/login.do"})
public class login extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String connectionUrl = "jdbc:jtds:sqlserver://39.106.229.210:1433;" +
                "databaseName=DBProject;" +
                "user=bill;password=010620";
        int id = Integer.parseInt(request.getParameter("username"));
        int password = Integer.parseInt(request.getParameter("password"));
        Connection connection = null;
        Statement statement = null;
        HttpSession session = request.getSession();
        try {
            // Load SQL Server JDBC driver and establish connection.
            System.out.print("Connecting to SQL Server ... ");
            connection = DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();
            String sql;
            sql = "SELECT * FROM DBProject.dbo.Users";
            ResultSet resultSet = statement.executeQuery(sql);
            String message = null;
            while (resultSet.next()) {
                int ID = resultSet.getInt("ID");
                int PASSWORD = resultSet.getInt("Password");
                String IDENTIFY = resultSet.getString("Identify");
                float AGE = resultSet.getFloat("age");
                String[] Data = new String[100];
                System.out.println(ID);
                if(Objects.equals(ID, id)) {
                    Data[0] = String.valueOf(ID);
                    Data[1] = String.valueOf(PASSWORD);
                    Data[2] = String.valueOf(AGE);
                    Data[3] = IDENTIFY;
                    System.out.println(Data[0] + Data[1]);
                    if(Objects.equals(password, PASSWORD)) {
                        session.setAttribute("Data", Data);
                        if(IDENTIFY.equals("Stu")) {
                            request.getRequestDispatcher("SelectStu.jsp").forward(request, response);
                        } else {
                            request.getRequestDispatcher("SelectTch.jsp").forward(request, response);
                        }
                    } else {
                        message = "密码错误，请重试...";
                        session.setAttribute("message", message);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login.jsp");
                        requestDispatcher.forward(request, response);
                    }
                }
            }
            message = "未找到用户名...";
            session.setAttribute("message", message);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login.jsp");
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
        }
    }
}
