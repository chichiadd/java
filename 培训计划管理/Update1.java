import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Update1 extends JFrame {
    private JTable employeeTable; // 员工表格
    private DefaultTableModel tableModel; // 表格模型
    private EmployeeDAO employeeDAO; // 数据访问对象

    public Update1() {
        setTitle("更新员工");
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

        // 更新按钮
        JButton updateButton = new JButton("更新");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    performUpdate(); // 调用更新方法
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(updateButton);
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

    // 更新员工的方法
    private void performUpdate() throws SQLException {
        int selectedRow = employeeTable.getSelectedRow(); // 获取选中行
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请先选择一条员工记录进行更新！");
            return;
        }

        String idCardNumber = (String) tableModel.getValueAt(selectedRow, 1); // 获取选中行的身份证号

        // 创建 Update1 窗口
        UpdateForm updateForm = new UpdateForm(idCardNumber);
        updateForm.setVisible(true);
        updateForm.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                loadEmployeeData(); // 刷新表格信息
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Update1 window = new Update1();
            window.setVisible(true);
        });
    }
}

// 更新员工信息的窗口
class UpdateForm extends JFrame {
    private JTextField nameField; // 姓名输入框
    private JTextField ageField; // 年龄输入框
    private JTextField emailField; // 电子邮件输入框
    private JTextField majorNameField; // 专业名称输入框
    private JTextField majorIdField; // 专业编号输入框
    private JTextField hireDateField; // 入职日期输入框
    private JComboBox<String> genderComboBox; // 性别下拉框
    private String idCardNumber; // 员工身份证号
    private EmployeeDAO employeeDAO; // 数据访问对象

    public UpdateForm(String idCardNumber) throws SQLException {
        this.idCardNumber = idCardNumber; // 获取当前员工身份证号
        this.employeeDAO = new EmployeeDAO(DBUtil.getConnection()); // 初始化 DAO

        setTitle("更新员工信息");
        setSize(400, 400); // 设置窗口初始大小
        setLocationRelativeTo(null); // 窗口居中显示
        setLayout(new GridBagLayout()); // 使用 GridBagLayout

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; // 水平方向填充
        gbc.weightx = 1.0; // 在水平方向上扩展

        // 姓名
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("姓名:"), gbc);
        nameField = new JTextField();
        gbc.gridx = 1;
        add(nameField, gbc);

        // 年龄
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("年龄:"), gbc);
        ageField = new JTextField();
        gbc.gridx = 1;
        add(ageField, gbc);

        // 电子邮件
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("电子邮件:"), gbc);
        emailField = new JTextField();
        gbc.gridx = 1;
        add(emailField, gbc);

        // 专业名称
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("专业名称:"), gbc);
        majorNameField = new JTextField();
        gbc.gridx = 1;
        add(majorNameField, gbc);

        // 专业编号
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("专业编号:"), gbc);
        majorIdField = new JTextField();
        gbc.gridx = 1;
        add(majorIdField, gbc);

        // 入职日期
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("入职日期:"), gbc);
        hireDateField = new JTextField(); // 输入格式为 yyyy-MM-dd
        gbc.gridx = 1;
        add(hireDateField, gbc);

        // 性别
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("性别:"), gbc);
        genderComboBox = new JComboBox<>(new String[]{"男", "女"});
        gbc.gridx = 1;
        add(genderComboBox, gbc);

        // 更新按钮
        JButton updateButton = new JButton("更新");
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2; // 跨越两列
        add(updateButton, gbc);

        // 加载员工信息
        loadEmployeeData();

        // 更新按钮事件监听
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performUpdate(); // 调用更新方法
            }
        });
    }

    // 加载员工信息
    private void loadEmployeeData() {
        try {
            Employee employee = employeeDAO.getEmployeeById(idCardNumber); // 根据身份证号获取员工信息
            if (employee != null) {
                nameField.setText(employee.getName());
                ageField.setText(String.valueOf(employee.getAge()));
                emailField.setText(employee.getEmail());
                majorNameField.setText(employee.getMajorName());
                majorIdField.setText(String.valueOf(employee.getMajorId()));
                hireDateField.setText(employee.getHireDate().toString());
                genderComboBox.setSelectedItem(employee.getGender());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "加载员工信息失败：" + e.getMessage());
        }
    }

    // 更新员工信息的方法
    private void performUpdate() {
        String name = nameField.getText();
        int age;
        String email = emailField.getText();
        String majorName = majorNameField.getText();
        int majorId;
        LocalDate hireDate;
        String gender = (String) genderComboBox.getSelectedItem();

        // 验证必填项
        if (name.isEmpty() || hireDateField.getText().isEmpty() || gender == null) {
            JOptionPane.showMessageDialog(this, "必填项不能为空！");
            return;
        }

        // 验证年龄
        try {
            age = Integer.parseInt(ageField.getText());
            if (age < 18) {
                JOptionPane.showMessageDialog(this, "员工必须年满18岁！");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "年龄必须为数字！");
            return;
        }

        // 验证专业编号
        try {
            majorId = Integer.parseInt(majorIdField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "专业编号必须为数字！");
            return;
        }

        // 验证入职日期
        try {
            hireDate = LocalDate.parse(hireDateField.getText()); // 确保日期格式正确
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "入职日期格式不正确，请使用 yyyy-MM-dd 格式！");
            return;
        }

        Employee employee = new Employee(name, idCardNumber, hireDate, gender);
        employee.setAge(age);
        employee.setEmail(email);
        employee.setMajorName(majorName);
        employee.setMajorId(majorId);

        // 调用 DAO 层更新数据
        try {
            employeeDAO.update(employee);
            JOptionPane.showMessageDialog(this, "员工信息已更新！");
            dispose(); // 关闭窗口
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "更新失败：" + e.getMessage());
        }
    }
}
