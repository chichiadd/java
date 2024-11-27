import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Select extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField select_planYear;
    private JTextField textField;
    private JTable results;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Select frame = new Select();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Select() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 441, 397);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel new_label = new JLabel("筛选");
        new_label.setBounds(29, 10, 134, 33);
        contentPane.add(new_label);

        JLabel select_planyear = new JLabel("年度：");
        select_planyear.setBounds(29, 42, 38, 20);
        contentPane.add(select_planyear);

        select_planYear = new JTextField();
        select_planYear.setBounds(63, 42, 97, 21);
        contentPane.add(select_planYear);
        select_planYear.setColumns(10);

        JButton select2 = new JButton("查询");
        select2.setBounds(279, 42, 93, 23);
        contentPane.add(select2);

        JLabel select_majorName = new JLabel("专业：");
        select_majorName.setBounds(29, 79, 38, 20);
        contentPane.add(select_majorName);

        textField = new JTextField();
        textField.setColumns(10);
        textField.setBounds(63, 79, 97, 21);
        contentPane.add(textField);

        JButton turn = new JButton("返回");
        turn.setBounds(279, 79, 93, 23);
        contentPane.add(turn);

        // 创建一个新面板用于存放 JTable
        JPanel tablePanel = new JPanel();
        tablePanel.setBounds(0, 112, 434, 246);
        contentPane.add(tablePanel);
        tablePanel.setLayout(new BorderLayout());

        // 创建 JTable
        results = new JTable();

        // 将 JTable 包装在 JScrollPane 中
        JScrollPane scrollPane = new JScrollPane(results);
        tablePanel.add(scrollPane, BorderLayout.CENTER); // 将 JScrollPane 添加到面板

        // 设置 JTable 自动调整模式
        results.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // 添加查询按钮的事件监听器
        select2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performQuery();
            }
        });
    }

    // 执行查询并更新 JTable
    private void performQuery() {
        Integer planYear = Integer.valueOf(select_planYear.getText().trim());
        String majorName = textField.getText().trim();

        try {
            Connection connection = DBUtil.getConnection();
            TrainingPlanDAO dao = new TrainingPlanDAO(connection);
            List<TrainingPlan> trainingPlans = dao.findTrainingPlanWithMajorPlans(planYear, majorName);

            // 清空 JTable
            DefaultTableModel model = new DefaultTableModel();
            String[] columnNames = {"培训计划 ID", "培训计划名称", "年度", "开始时间", "结束时间", "专业计划 ID", "专业名称", "培训目的", "培训内容", "课时", "教师"};
            model.setColumnIdentifiers(columnNames);
            System.out.println("输入的年度: " + planYear);
            System.out.println("输入的专业名称: " + majorName);
            if (trainingPlans.isEmpty()) {
                System.out.println("没有找到任何培训计划。");
                return;
            }

            for (TrainingPlan trainingPlan : trainingPlans) {
                for (MajorPlan majorPlan : trainingPlan.getMajorPlanes()) { // 使用您修改后的方法
                    Object[] row = new Object[11];
                    row[0] = trainingPlan.getId();
                    row[1] = trainingPlan.getName();
                    row[2] = trainingPlan.getPlanYear();
                    row[3] = trainingPlan.getStartDate();
                    row[4] = trainingPlan.getEndDate();
                    row[5] = majorPlan.getTrainingPlanId(); // 确保使用您更新后的方法
                    row[6] = majorPlan.getMajorName();
                    row[7] = majorPlan.getTrainingPurpose();
                    row[8] = majorPlan.getTrainingContent();
                    row[9] = majorPlan.getClassHours();
                    row[10] = majorPlan.getTeacher();
                    model.addRow(row);
                }
            }

            results.setModel(model);
            results.revalidate();
            results.repaint();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
