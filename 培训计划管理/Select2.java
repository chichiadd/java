import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Select2 extends JFrame {
    private JTextField yearField; // 年度输入框
    private JTextField majorField; // 专业名称输入框
    private JTable scoreTable; // 成绩表格
    private DefaultTableModel tableModel; // 表格模型
    private ScoreDAO scoreDAO; // ScoreDAO 的实例

    public Select2() {
        setTitle("成绩查询");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400); // 设置窗口初始大小
        setLocationRelativeTo(null); // 窗口居中显示
        setLayout(new BorderLayout()); // 使用 BorderLayout

        // 创建输入面板
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2)); // 使用 GridLayout
        inputPanel.add(new JLabel("年度:"));
        yearField = new JTextField();
        inputPanel.add(yearField);
        inputPanel.add(new JLabel("专业名称:"));
        majorField = new JTextField();
        inputPanel.add(majorField);

        add(inputPanel, BorderLayout.NORTH); // 将输入面板添加到窗口上方

        // 表格模型
        // 更新表格模型
        tableModel = new DefaultTableModel(new String[]{"id_1", "id_2", "年度", "培训计划名称", "专业名称", "开始时间", "结束时间"}, 0);
        scoreTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(scoreTable);
        add(scrollPane, BorderLayout.CENTER); // 添加表格到窗口中心

        // 查询按钮
        JButton searchButton = new JButton("查询");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch(); // 调用查询方法
            }
        });

        // 操作按钮
        JButton operateButton = new JButton("操作");
        operateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performOperate(); // 调用操作方法
            }
        });

        // 按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(searchButton); // 添加查询按钮
        buttonPanel.add(operateButton); // 添加操作按钮

        add(buttonPanel, BorderLayout.SOUTH); // 添加按钮在底部

        // 初始化 ScoreDAO
        try {
            Connection connection = DBUtil.getConnection(); // 假设 DBUtil 类存在
            scoreDAO = new ScoreDAO(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "数据库连接失败：" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 查询方法
    private void performSearch() {
        String yearText = yearField.getText().trim();
        String majorText = majorField.getText().trim();
        Integer year = yearText.isEmpty() ? null : Integer.valueOf(yearText);

        try {
            List<ScoreRecord> scoreRecords = scoreDAO.findScores(year, majorText.isEmpty() ? null : majorText);
            System.out.println("查询到的记录数量: " + scoreRecords.size());
            displayScoreRecords(scoreRecords);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "查询失败：" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 显示查询结果
    // 显示查询结果
    private void displayScoreRecords(List<ScoreRecord> scoreRecords) {
        tableModel.setRowCount(0); // 清空现有数据
        for (ScoreRecord record : scoreRecords) {
            Object[] row = new Object[]{
                    record.getId1(), // 培训计划 ID
                    record.getId2(), // 专业计划 ID
                    record.getPlanYear(), // 年度
                    record.getName(), // 培训计划名称
                    record.getMajorName(), // 专业名称
                    record.getStartDate(), // 开始日期
                    record.getEndDate(), // 结束日期
            };
            tableModel.addRow(row);
        }
    }

    // 操作方法
    private void performOperate() {
        int selectedRow = scoreTable.getSelectedRow(); // 获取选中行
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请先选择一条记录进行操作！");
            return;
        }

        // 获取选中行的相关信息
        Integer year = (Integer) tableModel.getValueAt(selectedRow, 2);
        String name = (String) tableModel.getValueAt(selectedRow, 3);
        String majorName = (String) tableModel.getValueAt(selectedRow, 4);
        System.out.println("当前选中记录: 年度=" + year + ", 培训计划名称=" + name + ", 专业名称=" + majorName);

        // 打开 Update2 窗口，并传递相关参数
        Update2 updateWindow = new Update2((Integer) tableModel.getValueAt(selectedRow, 0),
                (Integer) tableModel.getValueAt(selectedRow, 1));
        updateWindow.setVisible(true); // 显示更新窗口
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Select2 window = new Select2();
            window.setVisible(true);
        });
    }
}
