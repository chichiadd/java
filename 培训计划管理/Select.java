import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class Select extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField select_planYear;
    private JTextField textField;
    private JTable results;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Select frame = new Select();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
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

        JPanel JPanel = new JPanel();
        JPanel.setToolTipText("");
        JPanel.setBorder(new TitledBorder(null, "\u57F9\u8BAD\u8BA1\u5212", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        JPanel.setBounds(0, 112, 434, 246);
        contentPane.add(JPanel);
        JPanel.setLayout(new CardLayout(0, 0));

        results = new JTable();
        JPanel.add(results, "name_49369695313100");
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
            model.addColumn("培训计划 ID");
            model.addColumn("培训计划名称");
            model.addColumn("年度");
            model.addColumn("开始时间");
            model.addColumn("结束时间");
            model.addColumn("专业计划 ID");
            model.addColumn("专业名称");
            model.addColumn("培训目的");
            model.addColumn("培训内容");
            model.addColumn("课时");
            model.addColumn("教师");
                for(TrainingPlan trainingPlan :trainingPlans)
                for (MajorPlan majorPlan : trainingPlan.getMjorPlanes()) {
                    Object[] row = new Object[11];
                    row[0] = trainingPlan.getId();
                    row[1] = trainingPlan.getName();
                    row[2] = trainingPlan.getPlanYear();
                    row[3] = trainingPlan.getStartDate();
                    row[4] = trainingPlan.getEndDate();
                    row[5] = majorPlan.gettrainingPlanId();
                    row[6] = majorPlan.getMajorName();
                    row[7] = majorPlan.getTrainingPurpose();
                    row[8] = majorPlan.getTrainingContent();
                    row[9] = majorPlan.getClassHours();
                    row[10] = majorPlan.getTeacher();
                    model.addRow(row);
                }


            results.setModel(model);
            System.out.println("更新后的 JTable 数据：");
            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    System.out.print(model.getValueAt(i, j) + "\t");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }

