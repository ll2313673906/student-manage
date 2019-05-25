package com.sm.frame;

import com.sm.entity.Admin;
import com.sm.entity.CClass;
import com.sm.entity.Department;
import com.sm.entity.StudentVO;
import com.sm.factory.DAOFactory;
import com.sm.factory.ServiceFactory;
import com.sm.ui.ImgPanel;
import com.sm.utils.AliOSSUtil;
import net.coobird.thumbnailator.Thumbnails;
import sun.swing.table.DefaultTableCellHeaderRenderer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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

public class AdminMainFrame extends  JFrame {
    private ImgPanel rootPanel;
    private JButton 院系管理Button;
    private JButton 班级管理Button;
    private JButton 学生管理Button;
    private JButton 奖惩管理Button;
    private JPanel centerPanel;
    private JPanel departmentPanel;
    private JPanel student1Panel;
    private JPanel studentPanel;
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
    private JComboBox<Department> comboBox1;
    private JTextField textField2;
    private JButton 新增班级Button;
    private JPanel treePanel1;
    private JPanel classContentPanel1;
    private JPanel stuTopPanel;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JTextField searchField;
    private JButton 搜索Button;
    private JButton 新增学生Button;
    private JButton 批量导入Button;
    private JPanel tablePanel;
    private JLabel stuAvatarLabel;
    private JLabel stuIdLabel;
    private JLabel stuDepLabel;
    private JLabel stuClassLabel;
    private JLabel stuNameLabel;
    private JLabel stuGenderLabel;
    private JLabel stuBirLabel;
    private JLabel stuAddLabel;
    private JLabel stuPhoneLabel;
    private JTextField 个人信息TextField;
    private JButton 重新选择Button;
    private JLabel imgLabel;
    private JButton delButton;
    private Admin admin;
    private String uploadFileUrl;
    private File file;
    private File toPic;
    private JList jList;
    private int departmentID = 0;
    private JPanel infoPanel;

    public AdminMainFrame(Admin admin) {
        this.admin = admin;
        adminNameLabel.setText("当前管理员：" + admin.getAdminName());


        rootPanel.setFileName("aa.jpg");
        rootPanel.repaint();



        setTitle("管理员主界面");
        setContentPane(rootPanel);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        showDepartments();


        //获取centerPanel的布局,获取的是LayoutManager，向下转型为CardLayout
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
                showStudentTable();
                cardLayout.show(centerPanel, "Card2");
            }
        });
        学生管理Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminMainFrame.this.showClassPanel();
                cardLayout.show(centerPanel, "Card3");

                showClassPanel();
            }
        });
        奖惩管理Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(centerPanel, "Card4");
            }
        });
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
               AdminMainFrame.this.showDepartments();
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
                    ((ImageIcon) icon).setImage(((ImageIcon) icon).getImage().getScaledInstance(200,220,Image.SCALE_DEFAULT));
                    logoLabel.setIcon(icon);
                    logoLabel.setVisible(true);
                    选择logo图Button.setVisible(false);
                }
            }
        });
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
                    toPic=fileChooser.getSelectedFile();
                    //此处把图片压成400×500的缩略图
                    try {
                        Thumbnails.of(file).size(200,200).toFile(toPic);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    //通过文件创建icon对象
                    Icon icon = new ImageIcon(toPic.getAbsolutePath());
                    //通过标签显示图片
                    logoLabel.setIcon(icon);
                    //设置标签可见
                    logoLabel.setVisible(true);
                }}
        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = comboBox1.getSelectedIndex();
                //按照索引取出项，就是一个Department对象，然后取出其id备用
                departmentID = comboBox1.getItemAt(index).getId();
            }
        });



        新增班级Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CClass cClass = new CClass();
                cClass.setDepartmentId(departmentID);
                cClass.setClassName(textField2.getText().trim());
                long n = ServiceFactory.getCClassServiceInstance().addCClass(cClass);
                if (n == 1) {
                    JOptionPane.showMessageDialog(rootPanel, "新增班级成功");
                    showClassPanel();
                    textField2.setText("");


                } else {
                    JOptionPane.showMessageDialog(student1Panel, "新增班级失败");
                }
            }
        });


        重新选择Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                logoLabel.setVisible(false);
                选择logo图Button.setVisible(true);
            }
        });
    }

    private void showStudent1Panel() {
    }


    private void showDepartments() {
        //移除原有数据
        contentPanel.removeAll();
        //从service层获取到所有院系列表
        List<Department> departmentList = ServiceFactory.getDepartmentServiceInstance().selectAll();
        int len = departmentList.size();
        int row = len % 4 == 0 ? len / 4 : len / 4 + 1;
        GridLayout gridLayout = new GridLayout(row, 4, 10, 10);
        contentPanel.setLayout(gridLayout);

        for (Department department : departmentList) {
            //给每个院系对象创建一个面板
            JPanel depPanel = new JPanel();
            depPanel.setPreferredSize(new Dimension(400, 400));
            //将院系名称设置给面板标题
            depPanel.setBorder(BorderFactory.createTitledBorder(department.getDepartmentName()));
            //新建一个Label用来放置院系logo，并指定大小
            JLabel logoLabel = new JLabel("<html><img src='" + department.getLogo() + "' width=350 height=100/></html>");
            JButton delBtn=new JButton("删除");
            delBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked (MouseEvent e) {
                    int n=JOptionPane.showConfirmDialog(null,"确定要删除这行数据吗？","删除警告",
                            JOptionPane.YES_OPTION);
                    if (n==JOptionPane.YES_OPTION) {
                        contentPanel.remove(depPanel);
                        contentPanel.repaint();
                        ServiceFactory.getDepartmentServiceInstance().deleteDepartment(department.getId());
                    }
                }
            });
            delBtn.setPreferredSize(new Dimension(100,50));


            //图标标签加入院系面板
            depPanel.add(logoLabel);
            depPanel.add(delBtn);
            //院系面板加入主体内容面板
            contentPanel.add(depPanel);
            //刷新主体内容面板
            contentPanel.revalidate();
        }
    }
    private void showStudentTable() {
        //获得所有学生视图数据
        List<StudentVO> studentVOList = ServiceFactory.getStudentServieceInstance().selectAll();
        //创建表格
        JTable table = new JTable();
        //表格数据模型
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);
        //表头内容
        model.setColumnIdentifiers(new String[]{"学号", "院系", "班级", "姓名", "性别", "地址", "手机号", "出生日期", "头像"});
        //遍历List,转成Objuect数组
        for (StudentVO student : studentVOList) {
            Object[] objects = new Object[]{student.getId(), student.getDepartmentName(), student.getClassName(), student.getStudentName(),
                    student.getGender(), student.getAddress(),student.getPhone(), student.getBirthday(), student.getAvatar()};
            model.addRow(objects);
        }
        //将最后一列隐藏头像地址不显示在表格中
        TableColumn tableColumn = table.getColumnModel().getColumn(8);
        tableColumn.setMinWidth(0);
        tableColumn.setMaxWidth(0);
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
        table.setBackground(new Color(203, 164, 216));
        //表格内容居中
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, r);
        //表格加入滚动面板，水平垂直方向带滚动条
        JScrollPane scrollPane = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        tablePanel.add(scrollPane);
        //表格内容选择监听，点击一行，在右边显示这个学生信息
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = table.getSelectedRow();
                stuIdLabel.setText(table.getValueAt(row, 0).toString());
                stuDepLabel.setText(table.getValueAt(row, 1).toString());
                stuClassLabel.setText(table.getValueAt(row, 2).toString());
                stuNameLabel.setText(table.getValueAt(row, 3).toString());
                stuGenderLabel.setText(table.getValueAt(row, 4).toString());
                stuAddLabel.setText(table.getValueAt(row, 5).toString());
                stuPhoneLabel.setText(table.getValueAt(row, 6).toString());
                stuBirLabel.setText(table.getValueAt(row, 7).toString());
                stuAvatarLabel.setText("<html><img src='" + table.getValueAt(row, 8).toString() + "'/><ml>");

            }
        });
    }









    private void showClassPanel(){
        List<Department> departmentList = ServiceFactory.getDepartmentServiceInstance().selectAll();
        showCombobox(departmentList);
        showTree(departmentList);
        showClasses(departmentList);
    }
    private  void showCombobox(List<Department> departmentList){
        for (Department department:departmentList){
            comboBox1.addItem(department);
        }
    }



    private void  showTree(List<Department> departmentList){
        treePanel1.removeAll();
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("南工院");
        for (Department department:departmentList){
            DefaultMutableTreeNode group = new DefaultMutableTreeNode(department.getDepartmentName());
            top.add(group);
            List<CClass> cClassList = ServiceFactory.getCClassServiceInstance().selectByDepartmentId(department.getId());
            for (CClass cClass:cClassList){
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(cClass.getClassName());
                group.add(node);
            }
        }
        final  JTree tree = new JTree(top);
        tree.setRowHeight(30);
        tree.setFont(new Font("微软雅黑",Font.PLAIN,20));
        treePanel1.add(tree);
        treePanel1.revalidate();
    }
    private void showClasses(List<Department> departmentList){
        classContentPanel1.removeAll();
        Font titlefont = new Font("微软雅黑",Font.PLAIN,22);
        for (Department department:departmentList){
            ImgPanel depPanel = new ImgPanel();
            depPanel.setFileName("bg1.jpg");
            depPanel.repaint();
            depPanel.setPreferredSize(new Dimension(300,400));
            depPanel.setLayout(null);
            JLabel depNameLabel = new JLabel(department.getDepartmentName());
            depNameLabel.setFont(titlefont);
            depNameLabel.setBounds(50,15,200,30);
            List<CClass> cClassList = ServiceFactory.getCClassServiceInstance().selectByDepartmentId(department.getId());
            DefaultListModel listModel = new DefaultListModel();
            for (CClass cClass:cClassList){
                listModel.addElement(cClass);
            }
            JList<CClass> jList = new JList<>(listModel);
            JScrollPane listSrocPanel = new JScrollPane(jList);
            listSrocPanel.setBounds(50,100,200,170);
            depPanel.add(depNameLabel);
            depPanel.add(listSrocPanel);
            classContentPanel1.add(depPanel);
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
                    if (e.getButton()==3){
                        //在鼠标位置弹出菜单
                        jPopupMenu.show(jList,e.getX(),e.getY());
                        //取出选中数据
                        CClass cClass = jList.getModel().getElementAt(index);
                        //对删除菜单项添加监听
                        item2.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int choice = JOptionPane.showConfirmDialog(depPanel,"删除？");
                                if (choice==0){
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

    public static void main(String[] args)throws Exception {
        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
        UIManager.setLookAndFeel(lookAndFeel);

        new AdminMainFrame(DAOFactory.getAdminDAOInstance().getAdminByAccount("aaa@qq.com"));
    }
        }