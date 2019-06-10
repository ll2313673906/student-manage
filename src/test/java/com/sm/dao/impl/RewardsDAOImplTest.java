package com.sm.dao.impl;

import com.sm.dao.RewardsDAO;
import com.sm.entity.Rewards;
import com.sm.entity.RewardsVO;
import com.sm.factory.DAOFactory;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RewardsDAOImplTest {
    private RewardsDAO rewardsDAO = DAOFactory.getRewardsDAOIntance();

    @Test
    public void getAll() {
        List<RewardsVO> rewardsList = null;
        try {
            rewardsList = rewardsDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        rewardsList.forEach(rewards -> System.out.println(rewards));
    }

    @Test
    public void deleteById() throws SQLException {
        int n = rewardsDAO.deleteById(13);
        assertEquals(1,n);
    }

    @Test
    public void selectByKeywords() {
        List<RewardsVO> rewardsList = null;
        try {
            rewardsList = rewardsDAO.selectByKeywords("爱");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        rewardsList.forEach(rewards -> System.out.println(rewards));
    }

    @Test
    public void insertRewards() throws SQLException {
        Rewards rewards = new Rewards();
        rewards.setStudentNumber("1802343303");
        rewards.setType("奖励");
        rewards.setRewardsDate(new Date());
        rewards.setReason("第一");
        int n = rewardsDAO.insertRewards(rewards);
        assertEquals(1,n);
    }
}