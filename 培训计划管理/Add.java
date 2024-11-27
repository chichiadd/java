import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class Add extends JFrame {
    private JPanel contentPane;
    private JTextField majorIdField; // 专业编号输入框
    private JTextField majorNameField; // 专业名称输入框
    private JTextField trainingPurposeField; // 培训目的输入框
    private JTextField trainingContentField; // 培训内容输入框
    private JTextField classHoursField; // 课时输入框
    private JTextField teacherField; // 教师输入框
    private JTextField planYearField; // 计划年份输入框
    private JTextField startDateField; // 开始日期输入框
    private JTextField endDateField; // 结束日期输入框
    private JTextField nameField; // 培训计划名称输入框

    public Add() {
        setTitle("添加培训计划");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 500); // 调整窗口高度以容纳更多输入框
        contentPane = new JPanel();
        contentPane.setLayout(null); // 设置为绝对布局
        setContentPane(contentPane);

        // 设置计划年份
        JLabel planYearLabel = new JLabel("计划年份:");
        planYearLabel.setBounds(30, 20, 100, 25);
        contentPane.add(planYearLabel);
        planYearField = new JTextField();
        planYearField.setBounds(150, 20, 200, 25);
        contentPane.add(planYearField);

        // 设置开始日期
        JLabel startDateLabel = new JLabel("开始日期:");
        startDateLabel.setBounds(30, 60, 100, 25);
        contentPane.add(startDateLabel);
        startDateField = new JTextField();
        startDateField.setBounds(150, 60, 200, 25);
        contentPane.add(startDateField);

        // 设置结束日期
        JLabel endDateLabel = new JLabel("结束日期:");
        endDateLabel.setBounds(30, 100, 100, 25);
        contentPane.add(endDateLabel);
        endDateField = new JTextField();
        endDateField.setBounds(150, 100, 200, 25);
        contentPane.add(endDateField);

        // 设置培训计划名称
        JLabel nameLabel = new JLabel("培训计划名称:");
        nameLabel.setBounds(30, 140, 120, 25);
        contentPane.add(nameLabel);
        nameField = new JTextField();
        nameField.setBounds(150, 140, 200, 25);
        contentPane.add(nameField);

        // 设置专业编号
        JLabel majorIdLabel = new JLabel("专业编号:");
        majorIdLabel.setBounds(30, 180, 100, 25);
        contentPane.add(majorIdLabel);
        majorIdField = new JTextField();
        majorIdField.setBounds(150, 180, 200, 25);
        contentPane.add(majorIdField);

        // 设置专业名称
        JLabel majorNameLabel = new JLabel("专业名称:");
        majorNameLabel.setBounds(30, 220, 100, 25);
        contentPane.add(majorNameLabel);
        majorNameField = new JTextField();
        majorNameField.setBounds(150, 220, 200, 25);
        contentPane.add(majorNameField);

        // 设置培训目的
        JLabel trainingPurposeLabel = new JLabel("培训目的:");
        trainingPurposeLabel.setBounds(30, 260, 100, 25);
        contentPane.add(trainingPurposeLabel);
        trainingPurposeField = new JTextField();
        trainingPurposeField.setBounds(150, 260, 200, 25);
        contentPane.add(trainingPurposeField);

        // 设置培训内容
        JLabel trainingContentLabel = new JLabel("培训内容:");
        trainingContentLabel.setBounds(30, 300, 100, 25);
        contentPane.add(trainingContentLabel);
        trainingContentField = new JTextField();
        trainingContentField.setBounds(150, 300, 200, 25);
        contentPane.add(trainingContentField);

        // 设置课时
        JLabel classHoursLabel = new JLabel("课时:");
        classHoursLabel.setBounds(30, 340, 100, 25);
        contentPane.add(classHoursLabel);
        classHoursField = new JTextField();
        classHoursField.setBounds(150, 340, 200, 25);
        contentPane.add(classHoursField);

        // 设置教师
        JLabel teacherLabel = new JLabel("教师:");
        teacherLabel.setBounds(30, 380, 100, 25);
        contentPane.add(teacherLabel);
        teacherField = new JTextField();
        teacherField.setBounds(150, 380, 200, 25);
        contentPane.add(teacherField);

        // 创建添加按钮
        JButton addButton = new JButton("添加");
        addButton.setBounds(150, 420, 100, 30);
        contentPane.add(addButton);

        // 创建返回按钮
        JButton turn = new JButton("返回");
        turn.setBounds(300, 420, 100, 30);
        contentPane.add(turn);

        // 添加按钮事件监听
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performAdd(); // 调用添加方法
            }
        });

        // 返回按钮事件监听
        turn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 关闭当前窗口
            }
        });
    }

    // 添加方法
    private void performAdd() {
        // 获取用户输入
        int planYear = Integer.parseInt(planYearField.getText().trim());
        String startDate = startDateField.getText().trim();
        String endDate = endDateField.getText().trim();
        String name = nameField.getText().trim();
        int majorId = Integer.parseInt(majorIdField.getText().trim());
        String majorName = majorNameField.getText().trim();
        String trainingPurpose = trainingPurposeField.getText().trim();
        String trainingContent = trainingContentField.getText().trim();
        int classHours = Integer.parseInt(classHoursField.getText().trim());
        String teacher = teacherField.getText().trim();

        // 创建 TrainingPlan 和 MajorPlan 对象
        TrainingPlan trainingPlan = new TrainingPlan();
        trainingPlan.setPlanYear(planYear);
        trainingPlan.setStartDate(parseDate(startDate)); // 解析日期
        trainingPlan.setEndDate(parseDate(endDate));
        trainingPlan.setName(name);

        // 创建 MajorPlan 对象
        MajorPlan majorPlan = new MajorPlan();
        majorPlan.setMajorId(majorId);
        majorPlan.setMajorName(majorName);
        majorPlan.setTrainingPurpose(trainingPurpose);
        majorPlan.setTrainingContent(trainingContent);
        majorPlan.setClassHours(classHours);
        majorPlan.setTeacher(teacher);

        // 调用 DAO 层添加数据
        try {
            Connection connection = DBUtil.getConnection(); // 获取数据库连接
            TrainingPlanDAO dao = new TrainingPlanDAO(connection);
            dao.add(trainingPlan, Arrays.asList(majorPlan)); // 使用 Arrays.asList 创建只包含一个元素的列表
            JOptionPane.showMessageDialog(this, "添加成功！"); // 提示用户添加成功
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "添加失败！" + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 日期解析方法
    private java.util.Date parseDate(String dateString) {
        // 实现简单的日期解析逻辑
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Add window = new Add();
            window.setVisible(true);
        });
    }
}
