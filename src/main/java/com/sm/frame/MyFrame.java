package com.sm.frame;

import com.sm.ui.ImgPanel;

import javax.swing.*;

public class MyFrame extends JFrame{
    private ImgPanel rootPanel;
    private JPanel contentPanel;

    public MyFrame(){
        setContentPane(rootPanel);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        rootPanel.setFileName("1.jpg");
        rootPanel.repaint();
    }

    public static void main(String[] args) {
        new MyFrame();
    }
}
