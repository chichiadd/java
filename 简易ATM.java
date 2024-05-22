import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleATM extends JFrame {
    // 简化示例，使用静态变量代表账户信息
    private static double balance = 1000; // 初始余额
    private static String password = "1234"; // 初始密码
    private boolean isLoggedIn = false; // 追踪登录状态，默认为未登录

    // GUI 组件
    private JPasswordField pwdField;
    private JButton btnBalance, btnWithdraw, btnChangePwd, btnExit;
    private JTextArea displayArea;

    public SimpleATM() {
        // 设置窗口标题
        super("简易 ATM 模拟");

        // 创建组件
        pwdField = new JPasswordField(20);
        displayArea = new JTextArea(10, 30);
        displayArea.setEditable(false);

        // 创建按钮
        btnBalance = new JButton("1. 查询余额");
        btnWithdraw = new JButton("2. 取款");
        btnChangePwd = new JButton("3. 修改密码");
        btnExit = new JButton("4. 退出");

        // 安置组件
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("输入密码:"));
        panel.add(pwdField);
        panel.add(btnBalance);
        panel.add(btnWithdraw);
        panel.add(btnChangePwd);
        panel.add(btnExit);
        panel.add(new JScrollPane(displayArea));

        getContentPane().add(panel);

        // 添加事件监听器
        pwdField.addActionListener(this::login);
        btnBalance.addActionListener(e -> {
            if (checkLoggedIn()) {
                displayBalance();
            }
        });
        btnWithdraw.addActionListener(e -> {
            if (checkLoggedIn()) {
                withdraw();
            }
        });
        btnChangePwd.addActionListener(e -> {
            if (checkLoggedIn()) {
                changePassword();
            }
        });
        btnExit.addActionListener(e -> System.exit(0));

        pack(); // 自动调整大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // 登录操作
    private void login(ActionEvent e) {
        String inputPwd = new String(pwdField.getPassword());
        if (inputPwd.equals(password)) {
            isLoggedIn = true; // 更新登录状态
            displayArea.append("登录成功！\n");
        } else {
            displayArea.append("密码错误！\n");
            pwdField.setText("");
        }
    }

    // 检查是否已登录
    private boolean checkLoggedIn() {
        if (!isLoggedIn) {
            displayArea.append("请先登录！\n");
            return false;
        }
        return true;
    }

    // 查询余额
    private void displayBalance() {
        displayArea.append("当前余额: " + balance + "\n");
    }

    // 取款操作
    private void withdraw() {
        // 假定取款金额为100
        if (balance >= 100) {
            balance -= 100;
            displayArea.append("取款成功，当前余额: " + balance + "\n");
        } else {
            displayArea.append("余额不足！\n");
        }
    }

    // 修改密码
    private void changePassword() {
        // 实际情况中应弹出对话框让用户输入新密码，此处简化处理
        password = "4321"; // 假定新密码为此
        displayArea.append("密码已更改。\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimpleATM::new); // 使用事件调度线程创建并显示ATM界面
    }
}
