package com.sm.dao.impl;

import com.sm.dao.RewardsDAO;
import com.sm.entity.Rewards;
import com.sm.factory.DAOFactory;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class RewardsDAOImplTest {
    private RewardsDAO rewardsDAO = DAOFactory.getRewardsDAOIntance();

    @Test
    public void getAll() {
        List<Rewards> rewardsList = null;
        try {
            rewardsList = rewardsDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        rewardsList.forEach(rewards -> System.out.println(rewards));
    }

    @Test
    public void deleteById() throws SQLException {
        int n = rewardsDAO.deleteById("3");
        assertEquals(1,n);
    }

    @Test
    public void selectByKeywords() {
        List<Rewards> rewardsList = null;
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
        rewards.setId("6");
        rewards.setType("奖励");
        rewards.setRewardsDate(new Date());
        rewards.setNumber("1802343308");
        rewards.setName("关羽");
        rewards.setReason("歌唱比赛" +
                "");
        int n = rewardsDAO.insertRewards(rewards);
        assertEquals(1,n);
    }

}
