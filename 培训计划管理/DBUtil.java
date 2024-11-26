import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/shieldhero?useSSL=false"; // 数据库 URL
    private static final String USER = "root"; // 数据库用户名
    private static final String PASSWORD = "123123qwe"; // 数据库密码

    // 创建数据库连接
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // 执行 INSERT 或 UPDATE 语句
    public static int executeUpdate(String sql, Object... params) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            setParameters(pstmt, params);
            return pstmt.executeUpdate();
        }
    }

    // 执行 SELECT 语句
    public static ResultSet executeQuery(String sql, Object... params) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement pstmt = connection.prepareStatement(sql);
        setParameters(pstmt, params);
        return pstmt.executeQuery(); // 返回 ResultSet，但需要在代码中处理连接的关闭
    }

    // 关闭连接
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 设置 PreparedStatement 的参数
    private static void setParameters(PreparedStatement pstmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]); // 设置参数，索引从 1 开始
        }
    }

    // 关闭数据库连接
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
}
}

