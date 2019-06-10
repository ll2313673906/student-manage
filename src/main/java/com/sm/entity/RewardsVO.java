package com.sm.entity;

import java.util.Date;

public class RewardsVO {
    private Integer id;
    private String type;
    private Date rewardsDate;
    private String studentNumber;
    private String reason;
    private String className;
    private String departmentName;
    private String studentName;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getRewardsDate() {
        return rewardsDate;
    }

    public void setRewardsDate(Date rewardsDate) {
        this.rewardsDate = rewardsDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public String toString() {
        return  studentName ;
    }
}
