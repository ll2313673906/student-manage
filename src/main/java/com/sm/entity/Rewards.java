package com.sm.entity;

import java.util.Date;

public class Rewards {
    private Integer id;
    private String type;
    private Date rewardsDate;
    private String studentNumber;
    private String reason;

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

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Rewards{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", rewardsDate=" + rewardsDate +
                ", studentNumber='" + studentNumber + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
