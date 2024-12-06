import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Update extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField idField; // 用于输入 TrainingPlan ID
    private JTextField planYearField;
    private JTextField startDateField;
    private JTextField nameField;
    private JTextField endDateField;
    private List<JTextField> majorIdFields = new ArrayList<>(); // 用于输入 MajorPlan ID
    private List<JTextField> majorNameFields = new ArrayList<>();
    private List<JTextField> trainingPurposeFields = new ArrayList<>();
    private List<JTextField> trainingContentFields = new ArrayList<>();
    private List<JTextField> classHoursFields = new ArrayList<>();
    private List<JTextField> teacherFields = new ArrayList<>();
    private JButton updateButton;
    private JButton turnButton;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Update frame = new Update();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public Update() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // ID 输入框
        JLabel idLabel = new JLabel("培训计划 ID:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPane.add(idLabel, gbc);

        idField = new JTextField(10);
        gbc.gridx = 1;
        contentPane.add(idField, gbc);

        // 年度输入框
        JLabel planYearLabel = new JLabel("年度:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPane.add(planYearLabel, gbc);

        planYearField = new JTextField(10);
        gbc.gridx = 1;
        contentPane.add(planYearField, gbc);

        // 开始时间输入框
        JLabel startDateLabel = new JLabel("开始时间:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPane.add(startDateLabel, gbc);

        startDateField = new JTextField(10);
        gbc.gridx = 1;
        contentPane.add(startDateField, gbc);

        // 名称输入框
        JLabel nameLabel = new JLabel("名称:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        contentPane.add(nameLabel, gbc);

        nameField = new JTextField(10);
        gbc.gridx = 1;
        contentPane.add(nameField, gbc);

        // 结束时间输入框
        JLabel endDateLabel = new JLabel("结束时间:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        contentPane.add(endDateLabel, gbc);

        endDateField = new JTextField(10);
        gbc.gridx = 1;
        contentPane.add(endDateField, gbc);

        // 专业计划输入框
        addMajorPlanFields(gbc);

        // 添加更新按钮
        updateButton = new JButton("修改");
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2; // 跨越两列
        contentPane.add(updateButton, gbc);

        // 事件监听
        updateButton.addActionListener(e -> performUpdate());
    }

    private void addMajorPlanFields(GridBagConstraints gbc) {
        JLabel majorIdLabel = new JLabel("专业计划 ID:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        contentPane.add(majorIdLabel, gbc);

        JLabel majorNameLabel = new JLabel("专业:");
        gbc.gridx = 1;
        contentPane.add(majorNameLabel, gbc);

        JLabel trainingPurposeLabel = new JLabel("培训目的:");
        gbc.gridx = 2;
        contentPane.add(trainingPurposeLabel, gbc);

        JLabel trainingContentLabel = new JLabel("培训内容:");
        gbc.gridx = 3;
        contentPane.add(trainingContentLabel, gbc);

        JLabel classHoursLabel = new JLabel("课时:");
        gbc.gridx = 4;
        contentPane.add(classHoursLabel, gbc);

        JLabel teacherLabel = new JLabel("教师:");
        gbc.gridx = 5;
        contentPane.add(teacherLabel, gbc);

        // 添加初始的专业计划输入框
        addMajorPlanRow(gbc, 6);
    }

    private void addMajorPlanRow(GridBagConstraints gbc, int row) {
        JTextField majorIdField = new JTextField(10);
        majorIdFields.add(majorIdField);
        gbc.gridx = 0;
        gbc.gridy = row;
        contentPane.add(majorIdField, gbc);

        JTextField majorNameField = new JTextField(10);
        majorNameFields.add(majorNameField);
        gbc.gridx = 1;
        contentPane.add(majorNameField, gbc);

        JTextField trainingPurposeField = new JTextField(10);
        trainingPurposeFields.add(trainingPurposeField);
        gbc.gridx = 2;
        contentPane.add(trainingPurposeField, gbc);

        JTextField trainingContentField = new JTextField(10);
        trainingContentFields.add(trainingContentField);
        gbc.gridx = 3;
        contentPane.add(trainingContentField, gbc);

        JTextField classHoursField = new JTextField(10);
        classHoursFields.add(classHoursField);
        gbc.gridx = 4;
        contentPane.add(classHoursField, gbc);

        JTextField teacherField = new JTextField(10);
        teacherFields.add(teacherField);
        gbc.gridx = 5;
        contentPane.add(teacherField, gbc);
    }

    private void performUpdate() {
        int trainingPlanId = Integer.parseInt(idField.getText().trim());
        int planYear = Integer.parseInt(planYearField.getText().trim());
        String name = nameField.getText().trim();
        String startDate = startDateField.getText().trim();
        String endDate = endDateField.getText().trim();

        // 创建 TrainingPlan 对象
        TrainingPlan trainingPlan = new TrainingPlan();
        trainingPlan.setId(trainingPlanId);
        trainingPlan.setPlanYear(planYear);
        trainingPlan.setName(name);
        trainingPlan.setStartDate(java.sql.Date.valueOf(startDate)); // 需要根据您输入的日期格式进行调整
        trainingPlan.setEndDate(java.sql.Date.valueOf(endDate)); // 需要根据您输入的日期格式进行调整

        // 创建 MajorPlan 列表
        List<MajorPlan> majorPlans = new ArrayList<>();
        for (int i = 0; i < majorIdFields.size(); i++) {
            Integer majorPlanId = null; // 初始化为 null
            try {
                majorPlanId = Integer.parseInt(majorIdFields.get(i).getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "专业计划 ID 必须是一个有效的整数！", "输入错误", JOptionPane.ERROR_MESSAGE);
                return; // 如果转换失败，返回以避免继续执行
            }

            String majorName = majorNameFields.get(i).getText().trim();
            String trainingPurpose = trainingPurposeFields.get(i).getText().trim();
            String trainingContent = trainingContentFields.get(i).getText().trim();

            // 将 classHours 从字符串转换为 Integer
            Integer classHours = null; // 初始化为 null
            try {
                classHours = Integer.parseInt(classHoursFields.get(i).getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "课时必须是一个有效的整数！", "输入错误", JOptionPane.ERROR_MESSAGE);
                return; // 如果转换失败，返回以避免继续执行
            }

            String teacher = teacherFields.get(i).getText().trim();

            // 创建 MajorPlan 对象并添加到列表
            MajorPlan majorPlan = new MajorPlan();
            majorPlan.setId(majorPlanId); // 设置专业计划 ID
            majorPlan.setMajorName(majorName);
            majorPlan.setTrainingPurpose(trainingPurpose);
            majorPlan.setTrainingContent(trainingContent);
            majorPlan.setClassHours(classHours);
            majorPlan.setTeacher(teacher);
            majorPlans.add(majorPlan);
        }

        // 调用 DAO 方法进行更新
        try (Connection connection = DBUtil.getConnection()) {
            TrainingPlanDAO dao = new TrainingPlanDAO(connection);
            dao.update(trainingPlan, majorPlans);
            // 提示成功
            JOptionPane.showMessageDialog(this, "更新成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "更新失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}
