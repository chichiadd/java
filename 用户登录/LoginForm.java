import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField username;
    private JPasswordField passwd; // 用 JPasswordField 代替 JTextField让密码显示成*

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginForm frame = new LoginForm();
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
    public LoginForm() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        username = new JTextField();
        username.setBounds(137, 72, 183, 30);
        contentPane.add(username);
        username.setColumns(10);

        JLabel lblNewLabel = new JLabel("用户名");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(82, 71, 45, 30);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("密码");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(82, 116, 45, 30);
        contentPane.add(lblNewLabel_1);

        passwd = new JPasswordField();
        passwd.setBounds(137, 116, 183, 30);
        contentPane.add(passwd);
        passwd.setColumns(10);

        JButton loginButton = new JButton("登录");
        loginButton.setBounds(145, 167, 135, 52);
        contentPane.add(loginButton);

        JLabel lblNewLabel_2 = new JLabel("用户登录页面");
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 44));
        lblNewLabel_2.setBounds(10, 10, 403, 52);
        contentPane.add(lblNewLabel_2);

        // 添加登录按钮的事件监听器
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = username.getText();
                String pass = new String(passwd.getPassword()); // 使用 getPassword() 来获取密码

                UserDAO userDAO = new UserDAO();
                if (userDAO.isUserValid(user, pass)) {
                    JOptionPane.showMessageDialog(LoginForm.this, "登录成功", "提示", JOptionPane.INFORMATION_MESSAGE);

                    // 关闭当前窗口
                    LoginForm.this.dispose(); // 使用 LoginForm.this 关闭登录窗口

                    // 创建并显示主界面
                    MDIExample mdiExample = new MDIExample(); // 实例化主界面
                    mdiExample.setVisible(true); // 显示主界面
                } else {
                    JOptionPane.showMessageDialog(LoginForm.this, "用户名或密码错误", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
