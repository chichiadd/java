import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainingPlanDAO {
    private Connection connection;

    public TrainingPlanDAO(Connection connection) {
        this.connection = connection;
    }

    // 添加培训计划
    public void add(TrainingPlan trainingPlan, List<MajorPlan> majorPlans) throws SQLException {
        String sql1 = "INSERT INTO training_plan (name, planYear, startDate, endDate) VALUES (?, ?, ?, ?)";
        String sql2 = "INSERT INTO major_plan (majorName, majorId, trainingPurpose, trainingContent, classHours, teacher, trainingPlanId) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // 启动事务
        try {
            connection.setAutoCommit(false); // 关闭自动提交

            // 插入培训计划
            try (PreparedStatement pstmt1 = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS)) {
                pstmt1.setString(1, trainingPlan.getName());
                pstmt1.setInt(2, trainingPlan.getPlanYear());

                // 处理可能为 null 的日期
                if (trainingPlan.getStartDate() != null) {
                    pstmt1.setDate(3, new java.sql.Date(trainingPlan.getStartDate().getTime()));
                } else {
                    pstmt1.setNull(3, java.sql.Types.DATE);
                }

                if (trainingPlan.getEndDate() != null) {
                    pstmt1.setDate(4, new java.sql.Date(trainingPlan.getEndDate().getTime()));
                } else {
                    pstmt1.setNull(4, java.sql.Types.DATE);
                }

                pstmt1.executeUpdate();

                // 获取插入的培训计划 ID
                int trainingPlanId = -1; // 初始化
                try (ResultSet generatedKeys = pstmt1.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        trainingPlanId = generatedKeys.getInt(1); // 获取新插入的 ID
                    }
                }

                // 插入相关的专业计划
                try (PreparedStatement pstmt2 = connection.prepareStatement(sql2)) {
                    for (MajorPlan majorPlan : majorPlans) {
                        // majorPlan 可以为空，这里不做判断
                        if (majorPlan != null) {
                            pstmt2.setString(1, majorPlan.getMajorName());
                            pstmt2.setInt(2, majorPlan.getMajorId()); // 假设 majorId 是用户输入的
                            pstmt2.setString(3, majorPlan.getTrainingPurpose());
                            pstmt2.setString(4, majorPlan.getTrainingContent());
                            pstmt2.setInt(5, majorPlan.getClassHours());

                            if (majorPlan.getTeacher() != null) {
                                pstmt2.setString(6, majorPlan.getTeacher());
                            } else {
                                pstmt2.setNull(6, java.sql.Types.VARCHAR); // 使用 setNull 处理 null 值
                            }

                            // 设置 trainingPlanId
                            pstmt2.setInt(7, trainingPlanId);
                            pstmt2.executeUpdate();
                        }
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
                "classHours = ?, teacher = ? WHERE trainingPlanId = ? AND id =?";

        // 启动事务
        try {
            connection.setAutoCommit(false); // 关闭自动提交

            // 更新培训计划
            try (PreparedStatement pstmt1 = connection.prepareStatement(sql1)) {
                pstmt1.setString(1, trainingPlan.getName());
                pstmt1.setInt(2, trainingPlan.getPlanYear());
                pstmt1.setDate(3, new java.sql.Date(trainingPlan.getStartDate().getTime()));
                pstmt1.setDate(4, new java.sql.Date(trainingPlan.getEndDate().getTime()));
                pstmt1.setInt(5, trainingPlan.getId());
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
                    pstmt2.setInt(7, majorPlan.getTrainingPlanId());
                    pstmt2.setInt(8, majorPlan.getId());
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

     //删除培训计划
    public void delete(int trainingPlanId, int id) throws SQLException {
        String sql = "DELETE FROM major_plan WHERE trainingPlanId = ? AND  id = ? ";
        // 启动事务
        try {
            connection.setAutoCommit(false); // 关闭自动提交
            // 删除专业计划
            try (PreparedStatement pstmt1 = connection.prepareStatement(sql)) {
                pstmt1.setInt(1, trainingPlanId);
                pstmt1.setInt(2, id);
                pstmt1.executeUpdate();
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

    public void delete(int trainingPlanId) throws SQLException {
        String sql = "DELETE FROM training_plan WHERE id = ? ";
        try {
            connection.setAutoCommit(false); // 关闭自动提交

            // 删除培训计划
            try (PreparedStatement pstmt1 = connection.prepareStatement(sql)) {
                pstmt1.setInt(1, trainingPlanId);
                pstmt1.executeUpdate();
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
    public List<TrainingPlan> findTrainingPlanWithMajorPlans(Integer planYear, String majorName) throws SQLException {
        String sql = "SELECT tp.*, mp.* FROM training_plan tp " +
                "LEFT JOIN major_plan mp ON tp.id = mp.trainingPlanId " +
                "WHERE (tp.planYear = ? OR ? IS NULL) " +
                "AND (mp.majorName LIKE ? OR ? IS NULL)"; // 根据年度和专业名称查询

        List<TrainingPlan> trainingPlans = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // 设置年度
            pstmt.setObject(1, planYear);
            pstmt.setObject(2, planYear); // 再次设置以检查是否为 NULL
            // 设置专业名称
            String majorNamePattern = (majorName != null && !majorName.isEmpty()) ? "%" + majorName + "%" : null;
            pstmt.setString(3, majorNamePattern);
            pstmt.setString(4, majorNamePattern); // 再次设置以检查是否为 NULL

            try (ResultSet rs = pstmt.executeQuery()) {
                // 使用一个 Map 来存储培训计划和对应的专业计划
                Map<Integer, TrainingPlan> planMap = new HashMap<>();

                while (rs.next()) {
                    int trainingPlanId = rs.getInt("tp.id");

                    // 如果培训计划不存在，则创建新的 TrainingPlan 对象
                    if (!planMap.containsKey(trainingPlanId)) {
                        TrainingPlan trainingPlan = new TrainingPlan();
                        trainingPlan.setId(trainingPlanId);
                        trainingPlan.setName(rs.getString("tp.name"));
                        trainingPlan.setPlanYear(rs.getInt("tp.planYear"));
                        trainingPlan.setStartDate(rs.getDate("tp.startDate"));
                        trainingPlan.setEndDate(rs.getDate("tp.endDate"));

                        // 将新的 TrainingPlan 放入 Map 中
                        planMap.put(trainingPlanId, trainingPlan);
                    }

                    // 获取对应的 MajorPlan
                    MajorPlan majorPlan = new MajorPlan();
                    majorPlan.setMajorName(rs.getString("mp.majorName"));
                    majorPlan.setMajorId(rs.getInt("mp.majorId"));
                    majorPlan.setTrainingPurpose(rs.getString("mp.trainingPurpose"));
                    majorPlan.setTrainingContent(rs.getString("mp.trainingContent"));
                    majorPlan.setClassHours(rs.getInt("mp.classHours"));
                    majorPlan.setTeacher(rs.getString("mp.teacher"));
                    majorPlan.setTrainingPlanId(trainingPlanId);

                    // 将 MajorPlan 添加到对应的 TrainingPlan
                    planMap.get(trainingPlanId).setMajorPlan(majorPlan); // 需要在 TrainingPlan 类中实现 addMajorPlan 方法
                }

                // 将 Map 的值添加到返回列表中
                trainingPlans.addAll(planMap.values());
            }
        }

        return trainingPlans; // 返回培训计划
    }}
