import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class Add1 extends JFrame {
    private JTextField nameField; // 姓名输入框
    private JTextField idCardField; // 身份证号输入框
    private JTextField ageField; // 年龄输入框
    private JTextField emailField; // 电子邮件输入框
    private JTextField majorNameField; // 专业名称输入框
    private JTextField majorIdField; // 专业编号输入框
    private JTextField hireDateField; // 入职日期输入框
    private JComboBox<String> genderComboBox; // 性别下拉框

    public Add1() {
        setTitle("添加员工");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        nameField.setMinimumSize(new Dimension(200, 25)); // 最小尺寸
        gbc.gridx = 1;
        add(nameField, gbc);

        // 身份证号
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("身份证号:"), gbc);
        idCardField = new JTextField();
        idCardField.setMinimumSize(new Dimension(200, 25)); // 最小尺寸
        gbc.gridx = 1;
        add(idCardField, gbc);

        // 年龄
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("年龄:"), gbc);
        ageField = new JTextField();
        ageField.setMinimumSize(new Dimension(200, 25)); // 最小尺寸
        gbc.gridx = 1;
        add(ageField, gbc);

        // 电子邮件
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("电子邮件:"), gbc);
        emailField = new JTextField();
        emailField.setMinimumSize(new Dimension(200, 25)); // 最小尺寸
        gbc.gridx = 1;
        add(emailField, gbc);

        // 专业名称
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("专业名称:"), gbc);
        majorNameField = new JTextField();
        majorNameField.setMinimumSize(new Dimension(200, 25)); // 最小尺寸
        gbc.gridx = 1;
        add(majorNameField, gbc);

        // 专业编号
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("专业编号:"), gbc);
        majorIdField = new JTextField();
        majorIdField.setMinimumSize(new Dimension(200, 25)); // 最小尺寸
        gbc.gridx = 1;
        add(majorIdField, gbc);

        // 入职日期
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("入职日期:"), gbc);
        hireDateField = new JTextField(); // 输入格式为 yyyy-MM-dd
        hireDateField.setMinimumSize(new Dimension(200, 25)); // 最小尺寸
        gbc.gridx = 1;
        add(hireDateField, gbc);

        // 性别
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(new JLabel("性别:"), gbc);
        genderComboBox = new JComboBox<>(new String[]{"男", "女"});
        gbc.gridx = 1;
        add(genderComboBox, gbc);

        // 添加按钮
        JButton addButton = new JButton("保存");
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2; // 跨越两列
        add(addButton, gbc);

        // 添加按钮事件监听
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performAdd(); // 调用添加方法
            }
        });
    }

    // 添加方法
    private void performAdd() {
        String name = nameField.getText();
        String idCardNumber = idCardField.getText();
        int age = Integer.parseInt(ageField.getText());
        String email = emailField.getText();
        String majorName = majorNameField.getText();
        int majorId = Integer.parseInt(majorIdField.getText());
        LocalDate hireDate = LocalDate.parse(hireDateField.getText());
        String gender = (String) genderComboBox.getSelectedItem();

        // 验证必填项
        if (name.isEmpty() || idCardNumber.isEmpty() || hireDate == null || gender == null) {
            JOptionPane.showMessageDialog(this, "必填项不能为空！");
            return;
        }

        // 验证年龄
        if (age < 18) {
            JOptionPane.showMessageDialog(this, "员工必须年满18岁！");
            return;
        }

        Employee employee = new Employee(name, idCardNumber, hireDate, gender);
        employee.setAge(age);
        employee.setEmail(email);
        employee.setMajorName(majorName);
        employee.setMajorId(majorId);

        // 调用 DAO 层添加数据
        try {
            Connection connection = DBUtil.getConnection(); // 获取数据库连接
            EmployeeDAO employeeDAO = new EmployeeDAO(connection); // 创建 EmployeeDAO
            employeeDAO.add(employee);
            JOptionPane.showMessageDialog(this, "员工信息已保存！");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "保存失败：" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Add1 window = new Add1();
            window.setVisible(true);
        });
    }
}
