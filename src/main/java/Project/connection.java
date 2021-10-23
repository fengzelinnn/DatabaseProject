package Project;

import java.sql.Connection;
import java.sql.DriverManager;

public class connection {

    public static void main(String[] args) {

        String connectionUrl = "jdbc:jtds:sqlserver://39.106.229.210:1433;" +
                "databaseName=SPJ;" +
                "user=bill;password=010620";

        try {
            // Load SQL Server JDBC driver and establish connection.
            System.out.print("Connecting to SQL Server ... ");
            try (Connection connection = DriverManager.getConnection(connectionUrl)) {
                System.out.println("Done.");
            }
        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
        }
    }
}