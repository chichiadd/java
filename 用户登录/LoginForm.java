package test;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;

public class LoginForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField username;
	private JTextField passwd;

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
		
		passwd = new JTextField();
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
	}
}
