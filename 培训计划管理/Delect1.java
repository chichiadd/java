import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Delete1 extends JFrame {
    private JTable employeeTable; // 员工表格
    private DefaultTableModel tableModel; // 表格模型
    private EmployeeDAO employeeDAO; // 数据访问对象

    public Delete1() {
        setTitle("删除员工");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400); // 设置窗口初始大小
        setLocationRelativeTo(null); // 窗口居中显示
        setLayout(new BorderLayout()); // 使用 BorderLayout

        // 创建表格模型
        tableModel = new DefaultTableModel(new String[]{"姓名", "身份证号", "年龄", "入职日期", "性别", "电子邮件", "专业名称", "专业编号"}, 0);
        employeeTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        add(scrollPane, BorderLayout.CENTER); // 添加表格到窗口中心

        // 加载员工信息
        loadEmployeeData();

        // 删除按钮
        JButton deleteButton = new JButton("删除");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performDelete(); // 调用删除方法
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH); // 添加按钮在底部
    }

    // 加载员工信息
    private void loadEmployeeData() {
        tableModel.setRowCount(0); // 清空现有数据
        try {
            Connection connection = DBUtil.getConnection(); // 获取数据库连接
            employeeDAO = new EmployeeDAO(connection); // 创建 EmployeeDAO
            List<Employee> employees = employeeDAO.getAllEmployees(); // 获取所有员工信息
            for (Employee employee : employees) {
                tableModel.addRow(new Object[]{
                        employee.getName(),
                        employee.getIdCardNumber(),
                        employee.getAge(),
                        employee.getHireDate(),
                        employee.getGender(),
                        employee.getEmail(),
                        employee.getMajorName(),
                        employee.getMajorId()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "加载员工信息失败：" + e.getMessage());
        }
    }

    // 删除员工的方法
    private void performDelete() {
        int selectedRow = employeeTable.getSelectedRow(); // 获取选中行
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请先选择一条员工记录进行删除！");
            return;
        }

        String idCardNumber = (String) tableModel.getValueAt(selectedRow, 1); // 获取选中行的身份证号

        int confirm = JOptionPane.showConfirmDialog(this, "确定要删除该员工吗？", "确认", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                employeeDAO.delete(idCardNumber); // 调用 DAO 删除员工
                JOptionPane.showMessageDialog(this, "员工已删除！");
                loadEmployeeData(); // 刷新表格信息
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "删除失败：" + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Delete1 window = new Delete1();
            window.setVisible(true);
        });
    }
}
