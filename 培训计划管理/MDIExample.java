import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MDIExample extends JFrame {
    private JPanel optionPanel; // 选项面板
    private JPanel mainPanel; // 主面板

    public MDIExample() {
        setTitle("员工管理系统");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建选项面板 option选择
        optionPanel = new JPanel();
        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));

//        JButton addEmployeeButton = new JButton("添加员工");
//        JButton searchEmployeeButton = new JButton("查询员工");
//
//        optionPanel.add(addEmployeeButton);
//        optionPanel.add(searchEmployeeButton);
        //添加次要面板的内容

        // 创建主面板
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JLabel("请选择操作", SwingConstants.CENTER), BorderLayout.CENTER);

        // 添加按钮的事件监听
//        addEmployeeButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                showAddEmployeePanel();
//            }
//        });
//
//        searchEmployeeButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                showSearchEmployeePanel();
//            }
//        });

        // 创建分割面板
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, optionPanel, mainPanel);
        splitPane.setDividerLocation(150);
        add(splitPane);
    }

    //展示方法
    private void showPanel(JPanel panel) {
        mainPanel.removeAll(); // 清空主面板
        mainPanel.add(panel); // 添加新的面板
        mainPanel.revalidate(); // 重新验证布局
        mainPanel.repaint(); // 重绘面板
    }

//    private void showAddEmployeePanel() {
//        mainPanel.removeAll();
//        AddEmployeePanel addEmployeePanel = new AddEmployeePanel();
//        mainPanel.add(addEmployeePanel);
//        mainPanel.revalidate();
//        mainPanel.repaint();
//    }
//
//    private void showSearchEmployeePanel() {
//        mainPanel.removeAll();
//        SearchEmployeePanel searchEmployeePanel = new SearchEmployeePanel();
//        mainPanel.add(searchEmployeePanel);
//        mainPanel.revalidate();
//        mainPanel.repaint();
//    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MDIExample mdiExample = new MDIExample();
            mdiExample.setVisible(true);
        });
    }
}
