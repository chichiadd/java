import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
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
        // 设置窗口关闭操作和初始大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 400));
        pack(); // 调整窗口至首选大小

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        
        // 筛选标签
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        contentPane.add(new JLabel("筛选"), gbc);

        // 年度标签
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPane.add(new JLabel("年度："), gbc);

        // 年度输入框
        select_planYear = new JTextField(10);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; // 使其可以填满
        gbc.weightx = 1.0; // 设置权重，允许扩展
        contentPane.add(select_planYear, gbc);

        // 查询按钮
        JButton select2 = new JButton("查询");
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE; // 不填满
        gbc.weightx = 0; // 不扩展
        gbc.anchor = GridBagConstraints.EAST; // 右对齐
        contentPane.add(select2, gbc);

        // 专业标签
        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPane.add(new JLabel("专业："), gbc);

        // 专业输入框
        textField = new JTextField(10);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; // 使其可以填满
        gbc.weightx = 1.0; // 设置权重，允许扩展
        contentPane.add(textField, gbc);

        // 返回按钮
        JButton turn = new JButton("返回");
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE; // 不填满
        gbc.weightx = 0; // 不扩展
        gbc.anchor = GridBagConstraints.EAST; // 右对齐
        contentPane.add(turn, gbc);

        // JTable 面板
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3; // 跨越三列
        gbc.fill = GridBagConstraints.BOTH; // 允许填充
        gbc.weightx = 1.0; // X方向的权重
        gbc.weighty = 1.0; // Y方向的权重
        contentPane.add(tablePanel, gbc);

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

        // 添加窗口调整大小监听器，保持长宽比
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // 获取当前窗口的大小
                int width = getWidth();
                int height = getHeight();
                // 设定长宽比，比如设定为3:2
                int newHeight = (int) (width * 2.0 / 3.0);
                // 调整窗口大小
                setSize(width, newHeight);
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
                for (MajorPlan majorPlan : trainingPlan.getMajorPlanes()) {
                    Object[] row = new Object[11];
                    row[0] = trainingPlan.getId();
                    row[1] = trainingPlan.getName();
                    row[2] = trainingPlan.getPlanYear();
                    row[3] = trainingPlan.getStartDate();
                    row[4] = trainingPlan.getEndDate();
                    row[5] = majorPlan.getTrainingPlanId();
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
