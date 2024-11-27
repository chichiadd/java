package test;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Add extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField planYear1;
	private JTextField startDate1;
	private JTextField name1;
	private JTextField endDate1;
	private JTextField majorName1;
	private JTextField trainingPurpose1;
	private JTextField trainingContent1;
	private JTextField classHours1;
	private JTextField teacher1;
	private JButton turn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Add frame = new Add();
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
	public Add() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 591, 416);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel planYear_JLabel = new JLabel("年度");
		planYear_JLabel.setToolTipText("");
		planYear_JLabel.setBounds(37, 0, 78, 35);
		contentPane.add(planYear_JLabel);
		
		JLabel startDate_JLabel = new JLabel("开始时间");
		startDate_JLabel.setBounds(37, 42, 78, 35);
		contentPane.add(startDate_JLabel);
		
		JLabel name_JLabel = new JLabel("名称");
		name_JLabel.setBounds(325, 0, 83, 35);
		contentPane.add(name_JLabel);
		
		JLabel endDate_JLabel = new JLabel("结束时间");
		endDate_JLabel.setBounds(325, 45, 78, 28);
		contentPane.add(endDate_JLabel);
		
		planYear1 = new JTextField();
		planYear1.setBounds(125, 7, 118, 21);
		contentPane.add(planYear1);
		planYear1.setColumns(10);
		
		JLabel majorName = new JLabel("专业");
		majorName.setBounds(37, 131, 54, 15);
		contentPane.add(majorName);
		
		JLabel trainingPurpose = new JLabel("培训目的");
		trainingPurpose.setBounds(139, 131, 54, 15);
		contentPane.add(trainingPurpose);
		
		JLabel trainingContent = new JLabel("培训内容");
		trainingContent.setBounds(265, 131, 54, 15);
		contentPane.add(trainingContent);
		
		JLabel classHours = new JLabel("课时");
		classHours.setBounds(364, 131, 54, 15);
		contentPane.add(classHours);
		
		startDate1 = new JTextField();
		startDate1.setColumns(10);
		startDate1.setBounds(125, 49, 118, 21);
		contentPane.add(startDate1);
		
		name1 = new JTextField();
		name1.setColumns(10);
		name1.setBounds(418, 7, 118, 21);
		contentPane.add(name1);
		
		endDate1 = new JTextField();
		endDate1.setColumns(10);
		endDate1.setBounds(418, 49, 118, 21);
		contentPane.add(endDate1);
		
		JLabel teacher = new JLabel("teacher");
		teacher.setBounds(482, 131, 54, 15);
		contentPane.add(teacher);
		
		majorName1 = new JTextField();
		majorName1.setColumns(10);
		majorName1.setBounds(25, 156, 78, 99);
		contentPane.add(majorName1);
		
		trainingPurpose1 = new JTextField();
		trainingPurpose1.setColumns(10);
		trainingPurpose1.setBounds(125, 156, 78, 99);
		contentPane.add(trainingPurpose1);
		
		trainingContent1 = new JTextField();
		trainingContent1.setColumns(10);
		trainingContent1.setBounds(251, 156, 78, 99);
		contentPane.add(trainingContent1);
		
		classHours1 = new JTextField();
		classHours1.setColumns(10);
		classHours1.setBounds(346, 156, 78, 99);
		contentPane.add(classHours1);
		
		teacher1 = new JTextField();
		teacher1.setColumns(10);
		teacher1.setBounds(458, 156, 78, 99);
		contentPane.add(teacher1);
		
		JButton add = new JButton("添加");
		add.setBounds(443, 281, 93, 23);
		contentPane.add(add);
		
		turn = new JButton("返回");
		turn.setBounds(443, 314, 93, 23);
		contentPane.add(turn);
	}
}
