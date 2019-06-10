package com.sm.service.impl;

import com.sm.dao.RewardsDAO;
import com.sm.entity.Rewards;
import com.sm.entity.RewardsVO;
import com.sm.factory.DAOFactory;
import com.sm.service.RewardsService;

import java.sql.SQLException;
import java.util.List;

public class RewardsServiceImpl implements RewardsService {

    private RewardsDAO rewardsDAO = DAOFactory.getRewardsDAOIntance();
    @Override
    public List<RewardsVO> getAll() {
        List<RewardsVO> rewardsList = null;
        try {
            rewardsList = rewardsDAO.getAll();
        } catch (SQLException e) {
            System.err.print("查询奖惩信息出现异常");
        }
        return rewardsList;
    }

    @Override
    public int deleteById(int id) {
        int result = 0;
        try {
            result = rewardsDAO.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<RewardsVO> selectByKeywords(String keywords) {
        List<RewardsVO> rewardsList = null;
        try {
            rewardsList = rewardsDAO.selectByKeywords(keywords);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rewardsList;
    }

    @Override
    public int insertRewards(Rewards rewards) {
        int rewardsList = 0;
        try {
            rewardsList = rewardsDAO.insertRewards(rewards);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rewardsList;
    }
}
