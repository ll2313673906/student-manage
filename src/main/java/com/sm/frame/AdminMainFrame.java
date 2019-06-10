package com.sm.frame;

import com.sm.entity.*;
import com.sm.factory.DAOFactory;
import com.sm.factory.ServiceFactory;
import com.sm.thread.DateThread;
import com.sm.ui.ImgPanel;
import com.sm.utils.AliOSSUtil;
import net.coobird.thumbnailator.Thumbnails;
import sun.swing.table.DefaultTableCellHeaderRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AdminMainFrame extends  JFrame {
    private ImgPanel rootPanel;
    private JButton 院系管理Button;
    private JButton 班级管理Button;
    private JButton 学生管理Button;
    private JButton 奖惩管理Button;
    private JPanel centerPanel;
    private JPanel departmentPanel;
    private JPanel student1Panel;
    private JPanel classPanel;
    private JPanel rewardPunishPanel;
    private JPanel topPanel;
    private JButton 新增院系Button;
    private JTextField depNameField;
    private JButton 选择logo图Button;
    private JButton 新增Button;
    private JButton 刷新数据Button;
    private JPanel leftPanel;
    private JScrollPane scrollPanel;
    private JPanel contentPanel;
    private JLabel adminNameLabel;
    private JLabel logoLabel;
    private JComboBox<Department> depComboBox;
    private JTextField classField;
    private JButton 新增班级Button;
    private JPanel treePanel;
    private JPanel classContentPanel;
    private JPanel stuTopPanel;
    private JComboBox<Department> comboBox1;
    private JComboBox<CClass> comboBox2;
    private JTextField searchField;
    private JButton 搜索Button;
    private JButton 新增学生Button;
    private JButton 批量导入Button;
    private JPanel tablePanel;
    private JLabel stuAvatarLabel;
    private JLabel stuIdLabel;
    private JLabel stuDepartmentLabel;
    private JLabel stuClassLabel;
    private JLabel stuNameLabel;
    private JLabel stuGenderLabel;
    private JLabel stuBirLabel;
    private JLabel stuAddLabel;
    private JLabel stuPhoneLabel;
    private JButton 重新选择Button;
    private JLabel imgLabel;
    private JButton delButton;
    private Admin admin;
    private String uploadFileUrl;
    private File file;
    private File toPic;
    private JList jList;
    private int departmentId = 0;
    private ImgPanel infoPanel;
    private JTextField stuAddressField;
    private JTextField stuPhoneField;
    private JButton 初始化数据Button;
    private JButton 编辑Button;
    private int row;
    private JLabel dateLabel;
    private JComboBox comboBox3;
    private JPanel listPanel;
    private JTextField textField3;
    private JButton 查询Button;
    private JButton 新增奖惩Button;
    private JButton 刷新Button;

    public AdminMainFrame(Admin admin) {
        DateThread dateThread = new DateThread();
        new Thread(dateThread).start();
        dateThread.setDateLabel(dateLabel);

        this.admin = admin;
        adminNameLabel.setText("当前管理员：" + admin.getAdminName());


        rootPanel.setFileName("aa.jpg");
        rootPanel.repaint();
        infoPanel.setFileName("e.jpg");
        infoPanel.repaint();


        setTitle("管理员主界面");
        setContentPane(rootPanel);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        showDepartments();


        //获取centerPanel的布局,获取的是LayoutManager，向下转型为cardLayout
        CardLayout cardLayout = (CardLayout) centerPanel.getLayout();

        院系管理Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(centerPanel, "Card1");
            }
        });
        班级管理Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(centerPanel, "Card3");
                showClassPanel();
            }
        });
        //点击学生管理按钮切换卡片，右侧面板设置背景并重绘，调用封装的方法显示学生表格数据
        学生管理Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(centerPanel, "Card2");
                //调用学生数据的方法
                List<StudentVO> studentList = ServiceFactory.getStudentServieceInstance().selectAll();
                showStudentTable(studentList);

                //两个下拉框初始化提示数据，因为里面的元素都是对象，所有进行这样的处理
                Department tip1 = new Department();
                tip1.setDepartmentName("请选择院系");
                comboBox1.addItem(tip1);
                CClass tip2 = new CClass();
                tip2.setClassName("请选择班级");
                comboBox2.addItem(tip2);
                //初始化院系下拉框数据
                List<Department> departmentList = ServiceFactory.getDepartmentServiceInstance().selectAll();
                for (Department department : departmentList) {
                    comboBox1.addItem(department);
                }
                //初始化班级下拉框数据
                List<CClass> cClassList = ServiceFactory.getCClassServiceInstance().selectAll();
                for (CClass cClass : cClassList) {
                    comboBox2.addItem(cClass);
                }

                //院系下拉框设置监听，选中哪一项表格中就显示该院系的所有学生，并级联查出该院系的所有班级，更新到班级下拉框
                comboBox1.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        //如果是选中状态
                        if (e.getStateChange() == ItemEvent.SELECTED) {
                            //得到选中项的索引
                            int index = comboBox1.getSelectedIndex();
                            //排除第一项（为提示信息），不能作为查询依据
                            if (index != 0) {
                                departmentId = comboBox1.getItemAt(index).getId();

                                //获取该院系的学生并显示在表格
                                List<StudentVO> studentList = ServiceFactory.getStudentServieceInstance().selectByDepartmentId(departmentId);
                                showStudentTable(studentList);

                                //二级联动——班级下拉框的内容随着选择院系的不同而改变
                                List<CClass> cClassList = ServiceFactory.getCClassServiceInstance().selectByDepartmentId(departmentId);
                                //先移除之前的数据，否则下拉框内容会不断增加
                                comboBox2.removeAllItems();
                                //重新加入下拉框初始化提示数据
                                CClass tip = new CClass();
                                tip.setClassName("请选择班级");
                                comboBox2.addItem(tip);
                                for (CClass cClass : cClassList) {
                                    comboBox2.addItem(cClass);
                                }
                            }
                        }
                    }
                });
                //班级下拉框设置监听，可根据选中班级显示所有学生
                comboBox2.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        if (e.getStateChange() == ItemEvent.SELECTED) {
                            int index = comboBox2.getSelectedIndex();
                            if (index != 0) {
                                int classId = comboBox2.getItemAt(index).getId();
                                List<StudentVO> studentList = ServiceFactory.getStudentServieceInstance().selectByClassId(classId);
                                showStudentTable(studentList);
                            }
                        }
                    }
                });
            }
        });
        奖惩管理Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(centerPanel, "Card4");
                List<RewardsVO> rewardsList = ServiceFactory.getRewardsServiceInstance().getAll();
                comboBox3.removeAllItems();
                comboBox3.addItem("查询");
                showRewardsTable(rewardsList);


            }
        });
        //隐藏导航栏
        新增院系Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean flag = leftPanel.isVisible();
                if (flag == false) {
                    leftPanel.setVisible(true);
                } else {
                    leftPanel.setVisible(true);
                }
            }
        });
        刷新数据Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //(刷新)重新请求所有院系数据
                AdminMainFrame.this.showDepartments();
                showDepartments();
                leftPanel.setVisible(false);

            }
        });


        depNameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                depNameField.setText("");
            }
        });
        选择logo图Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("D:\\"));
                int result = fileChooser.showOpenDialog(rootPanel);
                if (result == JFileChooser.APPROVE_OPTION) {
                    file = fileChooser.getSelectedFile();
                    Icon icon = new ImageIcon(file.getAbsolutePath());
                    ((ImageIcon) icon).setImage(((ImageIcon) icon).getImage().getScaledInstance(200, 220, Image.SCALE_DEFAULT));
                    logoLabel.setIcon(icon);
                    logoLabel.setVisible(true);
                    选择logo图Button.setVisible(false);
                }
            }
        });
        //侧边栏新增
        新增Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //上传文件到OSS并返回外链
                uploadFileUrl = AliOSSUtil.ossUpload(file);
                //创建Department对象，并设置相应属性
                Department department = new Department();
                department.setDepartmentName(depNameField.getText().trim());
                department.setLogo(uploadFileUrl);
                //调用service实现新增功能
                int n = ServiceFactory.getDepartmentServiceInstance().addDepartment(department);
                if (n == 1) {
                    JOptionPane.showMessageDialog(rootPanel, "新增院系成功");
                    //新增成功后，将侧边栏隐藏
                    leftPanel.setVisible(false);
                    //刷新界面数据
                    showDepartments();
                    //将图片预览标签隐藏
                    logoLabel.setVisible(false);
                    //将选择图片的按钮可见
                    选择logo图Button.setVisible(true);
                    //清空文本框
                    depNameField.setText("");
                } else {
                    JOptionPane.showMessageDialog(rootPanel, "新增院系失败");
                }

            }

        });
        logoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("D:/QLDownload"));
                int result = fileChooser.showOpenDialog(rootPanel);
                if (result == JFileChooser.APPROVE_OPTION) {
                    logoLabel.removeAll();
                    //选中文件,原图
                    file = fileChooser.getSelectedFile();
                    //指定缩略图大小
                    toPic = fileChooser.getSelectedFile();
                    //此处把图片压成400×500的缩略图
                    try {
                        Thumbnails.of(file).size(200, 200).toFile(toPic);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    //通过文件创建icon对象
                    Icon icon = new ImageIcon(toPic.getAbsolutePath());
                    //通过标签显示图片
                    logoLabel.setIcon(icon);
                    //设置标签可见
                    logoLabel.setVisible(true);
                }
            }
        });

        //院系下拉框
        depComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //得到选中项的索引
                int index = depComboBox.getSelectedIndex();
                Department department = (Department) depComboBox.getItemAt(index);
//                departmentId = department.getId();
                //按照索引取出项，就是一个Department对象，然后取出其相应的id
                departmentId = depComboBox.getItemAt(index).getId();
            }
        });

        新增班级Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CClass cClass = new CClass();
                cClass.setDepartmentId(departmentId);
                cClass.setClassName(classField.getText().trim());
                long n = ServiceFactory.getCClassServiceInstance().addCClass(cClass);
                if (n == 1) {
                    JOptionPane.showMessageDialog(rootPanel, "新增班级成功");
                    showClassPanel();
                    classField.setText("");


                } else {
                    JOptionPane.showMessageDialog(student1Panel, "新增班级失败");
                }
            }
        });
        初始化数据Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //还原表格数据
                List<StudentVO> studentList = ServiceFactory.getStudentServieceInstance().selectAll();
                showStudentTable(studentList);
                //院系下拉框还原
                comboBox1.setSelectedIndex(0);
                //班级下拉框数据还原
                comboBox2.removeAllItems();
                CClass tip2 = new CClass();
                tip2.setClassName("请选择班级");
                comboBox2.addItem(tip2);
                List<CClass> cClassList = ServiceFactory.getCClassServiceInstance().selectAll();
                for (CClass cClass : cClassList) {
                    comboBox2.addItem(cClass);
                }
                //右侧显示个人信息区域数据还原
                stuAvatarLabel.setText("<html><img src=\"https://student-manage-ll.oss-cn-beijing.aliyuncs.com/logo/%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20190527201732.png\"/></html>");
                stuIdLabel.setText("未选择");
                stuDepartmentLabel.setText("未选择");
                stuClassLabel.setText("未选择");
                stuNameLabel.setText("未选择");
                stuGenderLabel.setText("未选择");
                stuAddressField.setText("");
                stuPhoneField.setText("");
                stuAddressField.setText("未选择");
                //搜索框清空
                searchField.setText("");
                //编辑按钮隐藏
                编辑Button.setVisible(false);
            }
        });
        搜索Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keywords = searchField.getText().trim();
                List<StudentVO> studentList = ServiceFactory.getStudentServieceInstance().selectByKeywords(keywords);
                if (studentList != null) {
                    showStudentTable(studentList);
                }
            }

        });

        新增学生Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddStudentFrame(AdminMainFrame.this);

            }
        });

        刷新Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                List<Rewards> rewardsList = ServiceFactory.getRewardsServiceInstance().getAll();
//                showRewardsTable(rewardsList);
                textField3.setText("");

            }
        });
        新增奖惩Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RewardsAddFrame(AdminMainFrame.this);
                AdminMainFrame.this.setEnabled(false);
            }
        });
        查询Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keywords = textField3.getText();
                List<RewardsVO> rewardsList = ServiceFactory.getRewardsServiceInstance().selectByKeywords(keywords);
                showRewardsTable(rewardsList);

            }
        });

    }



    //院系管理
    private void showDepartments() {
        //移除原有数据
        contentPanel.removeAll();

        List<Map> departmentList1 = ServiceFactory.getDepartmentServiceInstance().selectDepartmentInfo();
        //从service层获取到所有院系列表
        List<Department> departmentList = ServiceFactory.getDepartmentServiceInstance().selectAll();
        GridLayout gridLayout = new GridLayout(0, 4, 10, 10);
        contentPanel.setLayout(gridLayout);
        for (Map map : departmentList1) {
            //给每个院系对象创建一个面板
            JPanel depPanel = new JPanel();
            Department department = (Department)map.get("department");
            int classCount = (int)map.get("classCount");
            int studentCount = (int)map.get("studentCount");
            depPanel.setPreferredSize(new Dimension(300,300));
            depPanel.setBorder(BorderFactory.createTitledBorder(department.getDepartmentName()));
            JLabel logoLabel = new JLabel("<html><img src='" + department.getLogo() + "'/></html>");
            logoLabel.setPreferredSize(new Dimension(280,280));
            depPanel.add(logoLabel);
            JLabel infoLabel = new JLabel("班级"+ classCount + "个，学生" + studentCount + "人");

            JButton deleteBtn = new JButton("删除");
            deleteBtn.setPreferredSize(new Dimension(110, 42));
            deleteBtn.setBackground(new Color(83, 134, 66));
            deleteBtn.setForeground(new Color(1, 1, 1));
            deleteBtn.setFont(new Font(null, Font.BOLD, 20));
            deleteBtn.setSize(new Dimension(80, 40));
            deleteBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int n = JOptionPane.showConfirmDialog(contentPanel, "确认删除吗？", "确认对话框", JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        contentPanel.remove(depPanel);
                        //实时删除
                        contentPanel.repaint();
                        ServiceFactory.getDepartmentServiceInstance().deleteDepartment(department.getId());
                        System.out.println("已删除");
                    } else if (n == JOptionPane.NO_OPTION) {
                        System.out.println("已取消");
                    }
                }
            });
            depPanel.add(infoLabel);
            depPanel.add(deleteBtn);
            contentPanel.add(depPanel);
            contentPanel.revalidate();
        }
    }
    //班级管理
    private void showClassPanel(){
        //获取所有院系信息
        List<Department> departmentList = ServiceFactory.getDepartmentServiceInstance().selectAll();
        //班级管理中的顶上的下拉框
        showCombobox(departmentList);
        //班级管理中左侧的树
        showTree(departmentList);
        //班级管理中的流式布局面板
        showClasses(departmentList);
    }
    private void showCombobox(List<Department> departmentList){
        for (Department department: departmentList) {
            depComboBox.addItem(department);
        }
    }
    private void showTree(List<Department> departmentList){
        treePanel.removeAll();
        //左侧树形结构根节点
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("南工院");
        //一级目录遍历
        for (Department department: departmentList) {
            //得到学院名称
            DefaultMutableTreeNode group = new DefaultMutableTreeNode(department.getDepartmentName());
            top.add(group);
            //根据学院的id得到班级
            List<CClass> cClassList = ServiceFactory.getCClassServiceInstance().selectByDepartmentId(department.getId());
            //二级目录遍历
            for (CClass cClass: cClassList) {
                int num = ServiceFactory.getStudentServieceInstance().countStudentByClassId(cClass.getId());
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(cClass.getClassName()+"("+num+"人)");
                group.add(node);
            }
        }
        final JTree tree = new JTree(top);
        //树的行高
        tree.setRowHeight(30);
        tree.setFont(new Font("楷体",Font.PLAIN,20));
        treePanel.add(tree);
        treePanel.revalidate();
    }
    private void showClasses(List<Department> departmentList){
        classContentPanel.removeAll();
        //右侧流式布局面板显示
        Font titleFont = new Font("微软雅黑",Font.PLAIN,23);
        for (Department department:departmentList) {
            //外边框背景图
            ImgPanel depPanel = new ImgPanel();
            depPanel.setFileName("c.png");
            depPanel.repaint();
            depPanel.setPreferredSize(new Dimension(270,350));
            depPanel.setLayout(null);
            //加入院系名称
            JLabel depNameLabel = new JLabel(department.getDepartmentName());
            depNameLabel.setFont(titleFont);
            depNameLabel.setBounds(50,45,200,30);
            //获得该院系的所有班级
            List<CClass> cClassList = ServiceFactory.getCClassServiceInstance().selectByDepartmentId(department.getId());
            //数据模型
            DefaultListModel listModel = new DefaultListModel();
            //遍历班级集合，依次加入到数据模型中
            for (CClass cClass: cClassList) {
                listModel.addElement(cClass);
            }
            //使用数据模型创建一个JList组件
            JList<CClass> jList = new JList<>(listModel);
            jList.setForeground(new Color(0,0,0));
            jList.setBackground(new Color(240,240,240));
            jList.setFont(new Font("微软雅黑",Font.PLAIN,20));
            //jList加入到滚动面板
            JScrollPane listScrollPane = new JScrollPane(jList);
            listScrollPane.setBounds(35,75,200,240);
            depPanel.add(depNameLabel);
            depPanel.add(listScrollPane);
            classContentPanel.add(depPanel);
            //每个院系的JList中，选中班级右键会显示弹出菜单（修改、删除）
            JPopupMenu jPopupMenu = new JPopupMenu();
            JMenuItem item1 = new JMenuItem("修改");
            JMenuItem item2 = new JMenuItem("删除");
            jPopupMenu.add(item1);
            jPopupMenu.add(item2);
            jList.add(jPopupMenu);
            jList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //选中项的下标
                    int index = jList.getSelectedIndex();
                    //点击鼠标右键
                    if (e.getButton() == 3){
                        //在鼠标位置弹出菜单
                        jPopupMenu.show(jList,e.getX(),e.getY());
                        //取出选中数据
                        CClass cClass = jList.getModel().getElementAt(index);
                        //对“删除”菜单项添加监听
                        item2.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int choice = JOptionPane.showConfirmDialog(depPanel,"确认删除吗？");
                                if (choice == 0){
                                    ServiceFactory.getCClassServiceInstance().deleteCClassId(cClass.getId());
                                    listModel.remove(index);
                                    showTree(ServiceFactory.getDepartmentServiceInstance().selectAll());
                                }
                            }
                        });
                    }

                }
            });

        }
    }

    public void showStudentTable(List<StudentVO> studentList){
        tablePanel.removeAll();
        //创建表格
        JTable table = new JTable();
        //表格数据模型
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);
        //表头内容
        model.setColumnIdentifiers(new String[]{"学号","院系","班级","姓名","性别","地址","手机号","出生日期","头像"});
        //遍历List，集成object数据
        for (StudentVO s:studentList
        ) {
            Object[] objects = new Object[]{s.getId(),s.getDepartmentName(),s.getClassName(),
                    s.getStudentName(),s.getGender(),s.getAddress(),s.getPhone(),s.getBirthday(),
                    s.getAvatar()};
            model.addRow(objects);
        }
        //将最后一列隐藏头像地址不显示在表格中
        TableColumn tc = table.getColumnModel().getColumn(8);
        tc.setMinWidth(0);
        tc.setMaxWidth(0);
        //获得表头
        JTableHeader head = table.getTableHeader();
        //表头居中
        DefaultTableCellHeaderRenderer hr = new DefaultTableCellHeaderRenderer();
        hr.setHorizontalAlignment(JLabel.CENTER);
        head.setDefaultRenderer(hr);
        //设置表头大小
        head.setPreferredSize(new Dimension(head.getWidth(),30));
        //设置表头字体
        head.setFont(new Font("楷体",Font.PLAIN,22));
        //设置表头行高
        table.setRowHeight(35);
        //表格背景色
        table.setBackground(new Color(172,141,185));
        //表格内容居中
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class,r);
        //表格加入滚动面板，水平垂直方向带滚动条
        JScrollPane scrollPane = new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        tablePanel.add(scrollPane);

        //表格刷新
        tablePanel.revalidate();

        //弹出菜单
        JPopupMenu jPopupMenu = new JPopupMenu();
        JMenuItem item = new JMenuItem("删除");
        jPopupMenu.add(item);
        table.add(jPopupMenu);

        //从表格内容选择监听。点击一行，在右边显示这个学生的信息
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                row = table.getSelectedRow();
                stuIdLabel.setText(table.getValueAt(row, 0).toString());
                stuDepartmentLabel.setText(table.getValueAt(row, 1).toString());
                stuClassLabel.setText(table.getValueAt(row, 2).toString());
                stuNameLabel.setText(table.getValueAt(row, 3).toString());
                stuGenderLabel.setText(table.getValueAt(row, 4).toString());
                stuAddressField.setText(table.getValueAt(row, 5).toString());
                stuPhoneField.setText(table.getValueAt(row, 6).toString());
                stuBirLabel.setText(table.getValueAt(row, 7).toString());
                stuAvatarLabel.setText("<html><img src = '" + table.getValueAt(row,8).toString() + "'/><html>");
                编辑Button.setVisible(true);
                编辑Button.setText("编辑");
                编辑Button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getActionCommand().equals("编辑")) {
                            stuAddressField.setEditable(true);
                            stuAddressField.setEnabled(true);
                            stuPhoneField.setEditable(true);
                            stuPhoneField.setEnabled(true);
                            编辑Button.setText("保存");
                        }
                        if (e.getActionCommand().equals("保存")) {
                            Student student = new Student();
                            student.setId(stuIdLabel.getText());
                            student.setAddress(stuAddressField.getText());
                            student.setPhone(stuPhoneField.getText());
                            int n = ServiceFactory.getStudentServieceInstance().updateStudent(student);
                            if (n == 1) {
                                model.setValueAt(stuAddressField.getText(), row, 5);
                                model.setValueAt(stuPhoneField.getText(), row, 6);
                                stuAddressField.setEditable(false);
                                stuAddressField.setEnabled(false);
                                stuPhoneField.setEditable(false);
                                stuPhoneField.setEnabled(false);
                                编辑Button.setText("编辑");
                            }
                        }
                    }
                });
                if (e.getButton() == 3) {
                    jPopupMenu.show(table, e.getX(), e.getY());
                }
            }
        });
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id= (String) table.getValueAt(row,0);
                int choice = JOptionPane.showConfirmDialog(tablePanel,"确定删除么？");
                if (choice == 0){
                    if (row != -1){
                        model.removeRow(row);
                    }
                    ServiceFactory.getStudentServieceInstance().deleteById(id);
                }

            }
        });




    }
    public void showRewardsTable(List<RewardsVO> rewardsList){
        listPanel.removeAll();
        //创建表格
        JTable table = new JTable();
        //表格数据模型
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);
        //表头内容
        model.setColumnIdentifiers(new String[]{"id", "奖惩", "日期", "学号", "姓名", "内容"});
        for (RewardsVO rewards:rewardsList) {
            Object[] objects = new Object[]{rewards.getId(),rewards.getType(),rewards.getRewardsDate(),rewards.getStudentNumber(),rewards.getStudentName()
                    ,rewards.getReason()};
            model.addRow(objects);
        }
        //获得表头
        JTableHeader head = table.getTableHeader();
        //表头居中
        DefaultTableCellHeaderRenderer hr = new DefaultTableCellHeaderRenderer();
        hr.setHorizontalAlignment(JLabel.CENTER);
        head.setDefaultRenderer(hr);
        //设置表头大小
        head.setPreferredSize(new Dimension(head.getWidth(), 40));
        //设置表头字体
        head.setFont(new Font("楷体", Font.PLAIN, 22));
        //设置表格行高
        table.setRowHeight(35);
        //表格背景色
        table.setBackground(new Color(212, 212, 212));
        //表格内容居中
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, r);
        //表格加入滚动面板，水平垂直方向带滚动条
        JScrollPane scrollPane = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        listPanel.add(scrollPane);
        listPanel.revalidate();
        //弹出菜单
        JPopupMenu jPopupMenu = new JPopupMenu();
        JMenuItem item = new JMenuItem("删除");
        jPopupMenu.add(item);
        table.add(jPopupMenu);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                row = table.getSelectedRow();
                if (e.getButton() == 3) {
                    jPopupMenu.show(table, e.getX(), e.getY());
                }
            }
        });
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = (int) table.getValueAt(row, 0);
                int choice = JOptionPane.showConfirmDialog(listPanel, "确定删除吗");
                if (choice == 0) {
                    if (row != -1) {
                        model.removeRow(row);
                    }
                    //刷新表格数据
                    ServiceFactory.getRewardsServiceInstance().deleteById(id);
                }
            }
        });
    }
    public static void main(String[] args) throws Exception{
        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
        UIManager.setLookAndFeel(lookAndFeel);
        new AdminMainFrame(DAOFactory.getAdminDAOInstance().getAdminByAccount("aaa@qq.com"));
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
