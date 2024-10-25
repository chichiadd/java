import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainingPlanDAO {
    private Connection connection;

    public TrainingPlanDAO(Connection connection) {
        this.connection = connection;
    }

    // 添加培训计划
    public void add(TrainingPlan plan) throws SQLException {
        String sql = "INSERT INTO training_plans (name, planYear, startDate, endDate, majorPlane) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, plan.getName());
            pstmt.setInt(2, plan.getPlanYear());
            pstmt.setDate(3, new java.sql.Date(plan.getStartDate().getTime()));
            pstmt.setDate(4, new java.sql.Date(plan.getEndDate().getTime()));
            pstmt.setString(5, plan.getMajorPlane());
            pstmt.executeUpdate();
        }
    }

    // 修改培训计划
    public void update(TrainingPlan plan) throws SQLException {
        String sql = "UPDATE training_plans SET name = ?, planYear = ?, startDate = ?, endDate = ?, majorPlane = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, plan.getName());
            pstmt.setInt(2, plan.getPlanYear());
            pstmt.setDate(3, new java.sql.Date(plan.getStartDate().getTime()));
            pstmt.setDate(4, new java.sql.Date(plan.getEndDate().getTime()));
            pstmt.setString(5, plan.getMajorPlane());
            pstmt.setInt(6, plan.getId());
            pstmt.executeUpdate();
        }
    }

    // 删除培训计划
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM training_plans WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    // 查询培训计划
    public TrainingPlan search(int id) throws SQLException {
        String sql = "SELECT * FROM training_plans WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new TrainingPlan(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("planYear"),
                        rs.getDate("startDate"),
                        rs.getDate("endDate"),
                        rs.getString("majorPlane")
                );
            }
        }
        return null; // 如果没有找到，则返回 null
    }

    // 查询所有培训计划
    public List<TrainingPlan> searchAll() throws SQLException {
        List<TrainingPlan> plans = new ArrayList<>();
        String sql = "SELECT * FROM training_plans";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                TrainingPlan plan = new TrainingPlan(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("planYear"),
                        rs.getDate("startDate"),
                        rs.getDate("endDate"),
                        rs.getString("majorPlane")
                );
                plans.add(plan);
            }
        }
        return plans;
    }
}
