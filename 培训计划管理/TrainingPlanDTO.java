import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainingPlanDAO {
    private Connection connection;

    public TrainingPlanDAO(Connection connection) {
        this.connection = connection;
    }

    // 添加培训计划
    public void add(TrainingPlan trainingPlan, List<MajorPlan> majorPlans) throws SQLException {
        String sql1 = "INSERT INTO training_plan (name, planYear, startDate, endDate) VALUES (?, ?, ?, ?)";
        String sql2 = "INSERT INTO major_plan (majorName, majorId, trainingPurpose, trainingContent, classHours, id, teacher) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // 启动事务
        try {
            connection.setAutoCommit(false); // 关闭自动提交

            // 插入培训计划
            try (PreparedStatement pstmt1 = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS)) {
                pstmt1.setString(1, trainingPlan.getName());
                pstmt1.setInt(2, trainingPlan.getPlanYear());
                pstmt1.setDate(3, new java.sql.Date(trainingPlan.getStartDate().getTime()));
                pstmt1.setDate(4, new java.sql.Date(trainingPlan.getEndDate().getTime()));
                pstmt1.executeUpdate();

                // 插入相关的专业计划
                try (PreparedStatement pstmt2 = connection.prepareStatement(sql2)) {
                    for (MajorPlan majorPlan : majorPlans) {
                        pstmt2.setString(1, majorPlan.getMajorName());
                        pstmt2.setInt(2, majorPlan.getMajorId());
                        pstmt2.setString(3, majorPlan.getTrainingPurpose());
                        pstmt2.setString(4, majorPlan.getTrainingContent());
                        pstmt2.setInt(5, majorPlan.getClassHours());
                        pstmt2.setInt(6, majorPlan.getId());
                        pstmt2.setString(7, majorPlan.getTeacher());
                        pstmt2.executeUpdate();
                    }
                }
            }

            // 提交事务
            connection.commit();
        } catch (SQLException e) {
            // 发生错误时回滚事务
            connection.rollback();
            throw e; // 重新抛出异常
        } finally {
            connection.setAutoCommit(true); // 恢复自动提交
        }
    }

    // 修改培训计划
    public void update(TrainingPlan trainingPlan, List<MajorPlan> majorPlans) throws SQLException {
        String sql1 = "UPDATE training_plan SET name = ?, planYear = ?, startDate = ?, endDate = ? WHERE id = ?";
        String sql2 = "UPDATE major_plan SET majorName = ?, majorId = ?, trainingPurpose = ?, trainingContent = ?, " +
                "classHours = ?, teacher = ? WHERE id = ?";

        // 启动事务
        try {
            connection.setAutoCommit(false); // 关闭自动提交

            // 更新培训计划
            try (PreparedStatement pstmt1 = connection.prepareStatement(sql1)) {
                pstmt1.setString(1, trainingPlan.getName());
                pstmt1.setInt(2, trainingPlan.getPlanYear());
                pstmt1.setDate(3, new java.sql.Date(trainingPlan.getStartDate().getTime()));
                pstmt1.setDate(4, new java.sql.Date(trainingPlan.getEndDate().getTime()));
                pstmt1.setInt(5, trainingPlan.getId()); // 确保使用正确的 ID
                pstmt1.executeUpdate();
            }

            // 更新相关的专业计划
            for (MajorPlan majorPlan : majorPlans) {
                try (PreparedStatement pstmt2 = connection.prepareStatement(sql2)) {
                    pstmt2.setString(1, majorPlan.getMajorName());
                    pstmt2.setInt(2, majorPlan.getMajorId());
                    pstmt2.setString(3, majorPlan.getTrainingPurpose());
                    pstmt2.setString(4, majorPlan.getTrainingContent());
                    pstmt2.setInt(5, majorPlan.getClassHours());
                    pstmt2.setString(6, majorPlan.getTeacher());
                    pstmt2.setInt(7, majorPlan.getId());
                    pstmt2.executeUpdate();
                }
            }

            // 提交事务
            connection.commit();
        } catch (SQLException e) {
            // 发生错误时回滚事务
            connection.rollback();
            throw e; // 重新抛出异常
        } finally {
            connection.setAutoCommit(true); // 恢复自动提交
        }
    }

    // 删除培训计划
    public void delete(int trainingPlanId, List<Integer> majorPlanIds) throws SQLException {
        String sql1 = "DELETE FROM training_plan WHERE id = ?";
        String sql2 = "DELETE FROM major_plan WHERE id = ?";

        // 启动事务
        try {
            connection.setAutoCommit(false); // 关闭自动提交

            // 删除培训计划
            try (PreparedStatement pstmt1 = connection.prepareStatement(sql1)) {
                pstmt1.setInt(1, trainingPlanId);
                pstmt1.executeUpdate();
            }

            // 删除相关的专业计划
            for (Integer majorPlanId : majorPlanIds) {
                try (PreparedStatement pstmt2 = connection.prepareStatement(sql2)) {
                    pstmt2.setInt(1, majorPlanId);
                    pstmt2.executeUpdate();
                }
            }

            // 提交事务
            connection.commit();
        } catch (SQLException e) {
            // 发生错误时回滚事务
            connection.rollback();
            throw e; // 重新抛出异常
        } finally {
            connection.setAutoCommit(true); // 恢复自动提交
        }
    }
    // 查询培训计划
    public TrainingPlan findTrainingPlanWithMajorPlans(int trainingPlanId) throws SQLException {
        String sql = "SELECT tp.*, mp.* FROM training_plan tp " +
                "LEFT JOIN major_plan mp ON tp.id = mp.id " + // 使用 id 进行连接
                "WHERE tp.id = ?";
        List<MajorPlan> majorPlans = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, trainingPlanId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    trainingPlan = new TrainingPlan();
                    trainingPlan.setId(rs.getInt("tp.id"));
                    trainingPlan.setName(rs.getString("tp.name"));
                    trainingPlan.setPlanYear(rs.getInt("tp.planYear"));
                    trainingPlan.setStartDate(rs.getDate("tp.startDate"));
                    trainingPlan.setEndDate(rs.getDate("tp.endDate"));

                    // 处理专业计划
                    do {
                        MajorPlan majorPlan = new MajorPlan();
                        majorPlan.setId(rs.getInt("mp.id")); // 假设 mp 表的 id 是外键
                        majorPlan.setMajorName(rs.getString("mp.majorName"));
                        majorPlan.setMajorId(rs.getInt("mp.majorId"));
                        majorPlan.setTrainingPurpose(rs.getString("mp.trainingPurpose"));
                        majorPlan.setTrainingContent(rs.getString("mp.trainingContent"));
                        majorPlan.setClassHours(rs.getInt("mp.classHours"));
                        majorPlan.setTeacher(rs.getString("mp.teacher"));

                        majorPlans.add(majorPlan);
                    } while (rs.next());
                }
            }
        }

        return trainingPlan;
    }
    }
