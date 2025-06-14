package thuvien;

import java.sql.*;

public class DBConnect {
    private static String url = "jdbc:sqlserver://localhost:1433;databaseName=BTL;encrypt=false";
    private static String user = "btl";         
    private static String password = "12345678"; 

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(url, user, password);
    }

  
    public static ResultSet executeQuery(String sql) {
        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY
            );
            return st.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void executeUpdate(String sql) {
        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            st.executeUpdate(sql);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
