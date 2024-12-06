import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreDAO {
    private Connection connection;

    public ScoreDAO(Connection connection) {
        this.connection = connection;
    }

    public List<ScoreRecord> findScores(Integer planYear, String majorName) throws SQLException {
        List<ScoreRecord> scoreRecords = new ArrayList<>();

        // SQL 查询语句
        String sql = "SELECT tp.id AS trainingPlanId, tp.planYear, tp.name AS trainingPlanName, mp.majorName, tp.startDate, tp.endDate, mp.id AS majorPlanId " +
                "FROM training_plan tp " +
                "JOIN major_plan mp ON tp.id = mp.trainingPlanId " +
                "WHERE (tp.planYear = ? OR ? IS NULL) " +
                "AND (mp.majorName = ? OR ? IS NULL)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setObject(1, planYear); // 设置年度
            pstmt.setObject(2, planYear); // 设置年度（用于处理 NULL）
            pstmt.setString(3, majorName); // 设置专业名称
            pstmt.setString(4, majorName); // 设置专业名称（用于处理 NULL）

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // 创建 ScoreRecord 对象并添加到列表
                    ScoreRecord record = new ScoreRecord(
                            rs.getInt("trainingPlanId"), // 培训计划 ID
                            rs.getInt("majorPlanId"), // 专业计划 ID
                            rs.getInt("planYear"), // 年度
                            rs.getString("trainingPlanName"), // 培训计划名称
                            rs.getString("majorName"), // 专业名称
                            rs.getDate("startDate"), // 开始日期
                            rs.getDate("endDate") // 结束日期
                    );
                    scoreRecords.add(record);
                }
            }
        }

        return scoreRecords;
    }
    public void update(int id1, int id2, int id3, double attendanceScore, double examScore) throws SQLException {
        String sql = "UPDATE scores SET attendance_score = ?, exam_score = ?, total_score = ? WHERE id_1 = ? AND id_2" +
                " " +
                "=" +
                " ? AND id_3 = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // 计算总成绩
            double totalScore = attendanceScore * 0.2 + examScore * 0.8;

            // 设置参数
            pstmt.setDouble(1, attendanceScore);
            pstmt.setDouble(2, examScore);
            pstmt.setDouble(3, totalScore);
            pstmt.setInt(4, id1);
            pstmt.setInt(5, id2);
            pstmt.setInt(6, id3);

            // 执行更新
            pstmt.executeUpdate();
        }
    }

    public TrainingPlan getTrainingPlanById(int id) throws SQLException {
        String sql = "SELECT * FROM training_plan WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // 假设 TrainingPlan 类有一个合适的构造函数
                return new TrainingPlan(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("planYear"),
                        rs.getDate("startDate"),
                        rs.getDate("endDate")
                );
            }
        }
        return null; // 如果没有找到相应的记录，返回 null
    }

    public List<Employee> getEmployeesByTrainingPlanIdAndMajorPlan(int id1,int id2) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT e.id, e.name, e.major_name FROM employee e " +
                "JOIN scores cs ON cs.id_3 = e.id " + // 在这里加上空格
                "JOIN major_plan mp ON cs.id_2 = mp.id " + // 在这里加上空格
                "JOIN training_plan tp ON cs.id_1 = tp.id " +
                "WHERE tp.id = ? AND mp.id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id1);
            pstmt.setInt(2, id2);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("major_name")
                );
                employees.add(employee);
            }
        }
        return employees; // 返回所有与培训计划相关的员工
    }

    public Score getScoreByEmployeeIdAndTrainingPlanId(int employeeId, int trainingPlanId) throws SQLException {
        String sql = "SELECT * FROM scores WHERE id_3 = ? AND id_1 = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, employeeId);
            statement.setInt(2, trainingPlanId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Score score = new Score();
                score.setAttendanceScore(resultSet.getDouble("attendance_score"));
                score.setExamScore(resultSet.getDouble("exam_score"));
                score.setTotalScore(resultSet.getDouble("total_score"));
                return score;
            }
        }
        return null; // 如果没有找到成绩则返回 null
    }

    public List<Score> getScoresByTrainingPlanId(int trainingPlanId) throws SQLException {
        List<Score> scores = new ArrayList<>();
        String sql = "SELECT * FROM scores WHERE id_1 = ?";
        String employeeSql = "SELECT name FROM employee WHERE id = ?"; // 获取员工姓名的 SQL

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, trainingPlanId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Score score = new Score();
                score.setId3(resultSet.getInt("id_3"));
                score.setAttendanceScore(resultSet.getDouble("attendance_score"));
                score.setExamScore(resultSet.getDouble("exam_score"));
                score.setTotalScore(resultSet.getDouble("total_score"));

                // 获取员工姓名
                int employeeId = resultSet.getInt("id_3"); // 假设有 employee_id 字段
                try (PreparedStatement employeeStatement = connection.prepareStatement(employeeSql)) {
                    employeeStatement.setInt(1, employeeId);
                    ResultSet employeeResultSet = employeeStatement.executeQuery();
                    if (employeeResultSet.next()) {
                        score.setName(employeeResultSet.getString("name"));
                    } else {
                        score.setName("未知"); // 如果没有找到姓名，设置为"未知"
                    }
                }

                scores.add(score);
            }
        }
        return scores;
    }
}
