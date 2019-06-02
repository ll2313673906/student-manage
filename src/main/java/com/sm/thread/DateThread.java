package com.sm.thread;

import javax.swing.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateThread extends Thread {
    private JLabel dateLabel;

    public void setDateLabel(JLabel dateLabel) {
        this.dateLabel = dateLabel;
    }
    @Override
    public void run() {
        while (true)
        {
            GregorianCalendar time = new GregorianCalendar();
            int year = time.get(Calendar.YEAR);
            int month = time.get(Calendar.MONTH)+1;
            int day = time.get(Calendar.DAY_OF_MONTH);
            int second = time.get(Calendar.SECOND);
            int hour = time.get(Calendar.HOUR_OF_DAY);
            int minute = time.get(Calendar.MINUTE);
            dateLabel.setText(
                    "当前登录的时间:" + year + "-" + month + "-" + day +"-"+ hour+":"+ minute  +":"+ second );
            try {
                Thread.sleep(950);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}


