import javax.swing.*;
import java.awt.*;

public class MDIExample extends JFrame {
    private JPanel optionPanel; // 选项面板
    private JPanel mainPanel; // 主面板

    public MDIExample() {
        setTitle("员工管理系统");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建选项面板
        optionPanel = new JPanel();
        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));

        // 添加分隔标签（培训计划管理）
        optionPanel.add(new JLabel("---- 培训计划管理 ----")); // 标签
        optionPanel.add(Box.createVerticalStrut(10)); // 添加一些空间

        // 创建操作按钮（培训计划管理）
        JButton addButton = new JButton("添加培训计划");
        JButton deleteButton = new JButton("删除培训计划");
        JButton updateButton = new JButton("更新培训计划");
        JButton selectButton = new JButton("查询培训计划");

        // 将按钮添加到选项面板
        optionPanel.add(addButton);
        optionPanel.add(deleteButton);
        optionPanel.add(updateButton);
        optionPanel.add(selectButton);

        // 添加分隔标签（员工管理）
        optionPanel.add(Box.createVerticalStrut(20)); // 添加一些空间
        optionPanel.add(new JLabel("---- 员工管理 ----")); // 标签
        optionPanel.add(Box.createVerticalStrut(10)); // 添加一些空间

        // 创建操作按钮（员工管理）
        JButton add1Button = new JButton("添加员工");
        JButton delete1Button = new JButton("删除员工");
        JButton update1Button = new JButton("更新员工");
        JButton select1Button = new JButton("查询员工");

        // 将员工管理按钮添加到选项面板
        optionPanel.add(add1Button);
        optionPanel.add(delete1Button);
        optionPanel.add(update1Button);
        optionPanel.add(select1Button);

        // 添加分隔标签（成绩管理）
        optionPanel.add(Box.createVerticalStrut(20)); // 添加一些空间
        optionPanel.add(new JLabel("---- 成绩管理 ----")); // 标签
        optionPanel.add(Box.createVerticalStrut(10)); // 添加一些空间

        // 创建成绩管理按钮
        JButton scoreManagementButton = new JButton("成绩管理");
        optionPanel.add(scoreManagementButton); // 将成绩管理按钮添加到选项面板

        // 创建主面板
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JLabel("请选择操作", SwingConstants.CENTER), BorderLayout.CENTER);

        // 创建分割面板
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, optionPanel, mainPanel);
        splitPane.setDividerLocation(150);
        add(splitPane);

        // 添加按钮的事件监听器
        addButton.addActionListener(e -> showPanel(new Add().getContentPane()));
        deleteButton.addActionListener(e -> showPanel(new Delete().getContentPane()));
        updateButton.addActionListener(e -> showPanel(new Update().getContentPane()));
        selectButton.addActionListener(e -> showPanel(new Select().getContentPane()));

        add1Button.addActionListener(e -> showPanel(new Add1().getContentPane()));
        delete1Button.addActionListener(e -> showPanel(new Delete1().getContentPane()));
        update1Button.addActionListener(e -> showPanel(new Update1().getContentPane()));
        select1Button.addActionListener(e -> showPanel(new Select1().getContentPane()));

        // 添加成绩管理按钮的事件监听器
        scoreManagementButton.addActionListener(e -> showPanel(new Select2().getContentPane())); // 点击成绩管理按钮显示 Select2

        // 添加返回按钮
        JButton returnButton = new JButton("返回");
        returnButton.addActionListener(e -> returnToMainPanel());
        optionPanel.add(Box.createVerticalStrut(20)); // 添加一些空间
        optionPanel.add(returnButton);
    }

    // 展示方法
    private void showPanel(Container panel) {
        mainPanel.removeAll(); // 清空主面板
        mainPanel.add(panel); // 添加新的面板
        mainPanel.revalidate(); // 重新验证布局
        mainPanel.repaint(); // 重绘面板
    }

    public void returnToMainPanel() {
        this.dispose(); // 关闭当前窗口
        LoginForm loginForm = new LoginForm(); // 创建新的登录窗口实例
        loginForm.setVisible(true); // 显示登录窗口
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MDIExample mdiExample = new MDIExample();
            mdiExample.setVisible(true);
        });
    }
}
