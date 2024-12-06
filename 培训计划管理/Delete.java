import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.sql.Connection;
import java.sql.SQLException;

public class Delete extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField trainingPlanIdField; // 用于输入 TrainingPlan ID
    private JTextField majorPlanIdField; // 用于输入 MajorPlan ID
    private JButton deleteButton;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Delete frame = new Delete();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Delete() {
        // 窗口设置
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300));
        pack(); // 调整窗口至首选大小

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        // 标签和输入框设置
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPane.add(new JLabel("删除培训计划和专业计划"), gbc);

        // TrainingPlan ID 标签
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPane.add(new JLabel("培训计划 ID:"), gbc);

        // TrainingPlan ID 输入框
        trainingPlanIdField = new JTextField(10);
        gbc.gridx = 1;
        contentPane.add(trainingPlanIdField, gbc);

        // MajorPlan ID 标签
        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPane.add(new JLabel("专业计划 ID:"), gbc);

        // MajorPlan ID 输入框
        majorPlanIdField = new JTextField(10);
        gbc.gridx = 1;
        contentPane.add(majorPlanIdField, gbc);

        // 删除按钮
        deleteButton = new JButton("删除");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // 跨越两列
        contentPane.add(deleteButton, gbc);

        // 删除按钮事件监听
        deleteButton.addActionListener(e -> performDelete());

        
        // 添加窗口调整大小监听器，维护长宽比
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                maintainAspectRatio();
            }
        });
    }

    private void maintainAspectRatio() {
        // 获取当前窗口的大小
        int width = getWidth();
        int height = getHeight();

        // 设定长宽比，比如设定为4:3
        int newHeight = (int) (width * 3.0 / 4.0);

        // 如果当前高度大于设定高度，则调整窗口大小
        if (newHeight > height) {
            setSize(width, newHeight);
        } else {
            // 否则，保持当前高度，调整宽度
            int newWidth = (int) (height * 4.0 / 3.0);
            setSize(newWidth, height);
        }
    }

    private void performDelete() {
        String trainingPlanIdText = trainingPlanIdField.getText().trim();
        String majorPlanIdText = majorPlanIdField.getText().trim();

        try (Connection connection = DBUtil.getConnection()) { // 获取数据库连接
            TrainingPlanDAO dao = new TrainingPlanDAO(connection); // 创建 DAO 实例

            // 判断输入情况
            if (!trainingPlanIdText.isEmpty() && majorPlanIdText.isEmpty()) {
                // 只输入了 TrainingPlan ID
                int trainingPlanId = Integer.parseInt(trainingPlanIdText);
                dao.delete(trainingPlanId); // 调用 DAO 方法删除培训计划
                JOptionPane.showMessageDialog(this, "培训计划已成功删除！", "成功", JOptionPane.INFORMATION_MESSAGE);
            } else if (!trainingPlanIdText.isEmpty() && !majorPlanIdText.isEmpty()) {
                // 同时输入了 TrainingPlan ID 和 MajorPlan ID
                int trainingPlanId = Integer.parseInt(trainingPlanIdText);
                int majorPlanId = Integer.parseInt(majorPlanIdText);
                dao.delete(trainingPlanId, majorPlanId); // 调用 DAO 方法删除专业计划
                JOptionPane.showMessageDialog(this, "专业计划已成功删除！", "成功", JOptionPane.INFORMATION_MESSAGE);
            } else if (majorPlanIdText.isEmpty()) {
                // 只输入了 MajorPlan ID，提示无效
                JOptionPane.showMessageDialog(this, "请输入有效的培训计划 ID 或专业计划 ID！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的数字！", "错误", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "删除失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}
