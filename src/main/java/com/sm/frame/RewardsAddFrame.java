package com.sm.frame;

import com.eltima.components.ui.DatePicker;
import com.sm.entity.Rewards;
import com.sm.entity.RewardsVO;
import com.sm.entity.StudentVO;
import com.sm.factory.ServiceFactory;
import com.sm.ui.ImgPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RewardsAddFrame extends JFrame {
    private ImgPanel rootPanel;
    private JTextField numberTextField;
    private JTextField nameTextField4;
    private JTextField reasonTextField;
    private JPanel timePanel;
    private JButton 新增Button2;
    private JLabel closeLabel2;
    private JRadioButton 奖RadioButton;
    private JRadioButton 惩RadioButton;
    private JComboBox comboBox1;
    private JComboBox<StudentVO> comboBox2;
    private AdminMainFrame adminMainFrame;
    private String studentId;
    private String rewardsReason;
    public RewardsAddFrame(AdminMainFrame adminMainFrame) {
        this.adminMainFrame = adminMainFrame;
        rootPanel.setFileName("info.jpg");
        rootPanel.repaint();
        setTitle("新增学生界面");
        setContentPane(rootPanel);
        setSize(600,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        closeLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RewardsAddFrame.this.dispose();
                adminMainFrame.setEnabled(true);
            }
        });
        DatePicker datepick = getDatePicker();
        timePanel.add(datepick);
        timePanel.revalidate();

        ButtonGroup group = new ButtonGroup();
        group.add(奖RadioButton);
        group.add(惩RadioButton);


        comboBox1.removeAllItems();
        comboBox1.addItem("寝室卫生");
        comboBox1.addItem("个人素养");
        comboBox1.addItem("班级贡献");
        comboBox1.addItem("公共场合");
        comboBox1.addItem("其他");
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = comboBox1.getSelectedIndex();
                rewardsReason = comboBox1.getItemAt(index).toString();
            }
        });

        StudentVO tip = new StudentVO();
        tip.setId("请选择学生学号");
        tip.setStudentName("姓名");
        comboBox2.addItem(tip);
        List<StudentVO> studentVOList = ServiceFactory.getStudentServieceInstance().selectAll();
        for (StudentVO studentVO:studentVOList) {
            comboBox2.addItem(studentVO);

        }

        comboBox2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED){
                    int index = comboBox2.getSelectedIndex();
                    studentId = comboBox2.getItemAt(index).getId();
                }
            }
        });

        新增Button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String type = null;
                if (奖RadioButton.isSelected()) {
                    type = "奖";

                }
                if (惩RadioButton.isSelected()) {
                    type = "惩";

                }
                Rewards rewards = new Rewards();
                rewards.setStudentNumber(studentId);
                rewards.setType(type);
                rewards.setRewardsDate((Date) datepick.getValue());
                rewards.setReason(rewardsReason);
                int n = ServiceFactory.getRewardsServiceInstance().insertRewards(rewards);
                if (n == 1){
                    JOptionPane.showMessageDialog(rootPanel,"新增成功");
                    RewardsAddFrame.this.dispose();
                    adminMainFrame.setEnabled(true);
                    List<RewardsVO> rewardsList = ServiceFactory.getRewardsServiceInstance().getAll();
                    adminMainFrame.showRewardsTable(rewardsList);
                }
            }
        });
    }

    private static DatePicker getDatePicker() {
        final DatePicker datepick;
        // 格式
        String DefaultFormat = "yyyy-MM-dd";
        // 当前时间
        Date date = new Date();
        // 字体
        Font font = new Font("Times New Roman", Font.PLAIN, 18);
        Dimension dimension = new Dimension(200, 30);
        int[] hilightDays = {1, 3, 5, 7};
        int[] disabledDays = {4, 6, 5, 9};
        //构造方法（初始时间，时间显示格式，字体，控件大小）
        datepick = new DatePicker(date, DefaultFormat, font, dimension);
        datepick.setHightlightdays(hilightDays, Color.red);
        // 设置一个月份中不需要的日子，呈灰色显示
        datepick.setDisableddays(disabledDays);
        // 设置国家
        datepick.setLocale(Locale.CHINA);
        return datepick;
    }
}