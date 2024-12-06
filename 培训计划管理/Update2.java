import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Update2 extends JFrame {
    private JTextField attendanceScoreField; // 出勤成绩输入框
    private JTextField examScoreField; // 考试成绩输入框
    private JTextField totalScoreField; // 总成绩显示框
    private JTextArea trainingPlanInfoArea; // 培训计划信息显示区域
    private JComboBox<Employee> employeeComboBox; // 员工选择下拉框
    private JTable scoreTable; // 用于显示成绩的表格
    private int id1; // 培训计划 ID
    private int id2; // 专业计划 ID
    private ScoreDAO scoreDAO; // 数据访问对象

    public Update2(int id1, int id2) {
        this.id1 = id1;
        this.id2 = id2;

        setTitle("成绩修改");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 1000); // 设置窗口初始大小
        setLocationRelativeTo(null); // 窗口居中显示
        setLayout(new GridBagLayout()); // 使用 GridBagLayout

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; // 水平方向填充
        gbc.weightx = 1.0; // 在水平方向上扩展

        // 培训计划信息显示区域
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // 跨越两列
        trainingPlanInfoArea = new JTextArea(5, 50);
        trainingPlanInfoArea.setEditable(false);
        add(new JScrollPane(trainingPlanInfoArea), gbc);
        loadTrainingPlanInfo(); // 加载培训计划信息

        // 员工选择
        gbc.gridwidth = 1; // 重置为一列
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("选择员工:"), gbc);
        employeeComboBox = new JComboBox<>();
        loadEmployees(); // 加载员工信息
        gbc.gridx = 1;
        add(employeeComboBox, gbc);

        // 出勤成绩
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("出勤成绩:"), gbc);
        attendanceScoreField = new JTextField();
        gbc.gridx = 1;
        add(attendanceScoreField, gbc);

        // 考试成绩
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("考试成绩:"), gbc);
        examScoreField = new JTextField();
        gbc.gridx = 1;
        add(examScoreField, gbc);

        // 总成绩
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("总成绩:"), gbc);
        totalScoreField = new JTextField();
        totalScoreField.setEditable(false);
        gbc.gridx = 1;
        add(totalScoreField, gbc);

        // 计算总成绩按钮
        JButton calculateButton = new JButton("计算总成绩");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2; // 跨越两列
        add(calculateButton, gbc);
        calculateButton.addActionListener(e -> calculateTotalScore());

        // 保存成绩按钮
        JButton saveButton = new JButton("保存成绩");
        gbc.gridy = 6;
        add(saveButton, gbc);
        saveButton.addActionListener(e -> performUpdate());

        // 成绩显示表格
        gbc.gridy = 7;
        gbc.gridwidth = 2; // 跨越两列
        scoreTable = new JTable();
        loadScoreTable(); // 加载成绩表格
        add(new JScrollPane(scoreTable), gbc);

        // 监听员工选择变化
        employeeComboBox.addActionListener(e -> loadEmployeeInfo());
    }

    // 加载培训计划信息
    private void loadTrainingPlanInfo() {
        try {
            Connection connection = DBUtil.getConnection(); // 获取数据库连接
            scoreDAO = new ScoreDAO(connection); // 创建 DAO
            TrainingPlan trainingPlan = scoreDAO.getTrainingPlanById(id1); // 假设有该方法
            if (trainingPlan != null) {
                trainingPlanInfoArea.setText("编号: " + trainingPlan.getId() +
                        "\n名称: " + trainingPlan.getName() +
                        "\n年度: " + trainingPlan.getPlanYear() +
                        "\n开始时间: " + trainingPlan.getStartDate() +
                        "\n结束时间: " + trainingPlan.getEndDate());
            } else {
                trainingPlanInfoArea.setText("未找到培训计划信息");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "加载培训计划信息失败：" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 加载员工信息到下拉框
    private void loadEmployees() {
        employeeComboBox.removeAllItems(); // 清空现有的员工项
        try {
            Connection connection = DBUtil.getConnection(); // 获取数据库连接
            scoreDAO = new ScoreDAO(connection); // 创建 DAO
            List<Employee> employees = scoreDAO.getEmployeesByTrainingPlanIdAndMajorPlan(id1,id2);
            for (Employee employee : employees) {
                employeeComboBox.addItem(employee);
            }
            if (employeeComboBox.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "未找到与培训计划相关的员工", "提示", JOptionPane.INFORMATION_MESSAGE);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "加载员工信息失败：" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 加载选中员工信息
    private void loadEmployeeInfo() {
        Employee selectedEmployee = (Employee) employeeComboBox.getSelectedItem();
        if (selectedEmployee != null) {
            attendanceScoreField.setText(""); // 清空出勤成绩输入框
            examScoreField.setText(""); // 清空考试成绩输入框
            totalScoreField.setText(""); // 清空总成绩输入框

            // 查询并显示已有成绩
            try {
                Score score = scoreDAO.getScoreByEmployeeIdAndTrainingPlanId(selectedEmployee.getId(), id1); // 获取成绩

                if (score != null) {
                    attendanceScoreField.setText(String.valueOf(score.getAttendanceScore()));
                    examScoreField.setText(String.valueOf(score.getExamScore()));
                    totalScoreField.setText(String.valueOf(score.getTotalScore()));
                } else {
                    // 如果没有成绩记录
                    attendanceScoreField.setText("");
                    examScoreField.setText("");
                    totalScoreField.setText("");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "加载成绩失败：" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // 计算总成绩
    private void calculateTotalScore() {
        try {
            double attendanceScore = Double.parseDouble(attendanceScoreField.getText());
            double examScore = Double.parseDouble(examScoreField.getText());
            double totalScore = attendanceScore * 0.2 + examScore * 0.8;
            totalScoreField.setText(String.valueOf(totalScore));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的成绩", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 执行更新
    private void performUpdate() {
        try {
            Employee selectedEmployee = (Employee) employeeComboBox.getSelectedItem();
            if (selectedEmployee == null) {
                JOptionPane.showMessageDialog(this, "请选择员工", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double attendanceScore = Double.parseDouble(attendanceScoreField.getText());
            double examScore = Double.parseDouble(examScoreField.getText());

            // 调用 DAO 层更新数据
            try (Connection connection = DBUtil.getConnection()) { // 获取数据库连接
                scoreDAO.update(id1, id2, selectedEmployee.getId(), attendanceScore, examScore);
                JOptionPane.showMessageDialog(this, "成绩更新成功！");
                loadScoreTable(); // 更新表格
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "成绩更新失败：" + e.getMessage());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的成绩", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 加载成绩表格
    private void loadScoreTable() {
        try {
            List<Score> scores = scoreDAO.getScoresByTrainingPlanId(id1);
            String[] columnNames = {"员工ID", "姓名", "出勤成绩", "考试成绩", "总成绩"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            for (Score score : scores) {
                Object[] row = {score.getId3(), score.getName(), score.getAttendanceScore(),
                        score.getExamScore(), score.getTotalScore()};
                model.addRow(row);
            }
            scoreTable.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "加载成绩失败：" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Update2 window = new Update2(17, 2); // 假设 ID 为 17
            window.setVisible(true);
        });
    }
}
