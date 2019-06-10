package com.sm.service;

import com.sm.entity.Rewards;
import com.sm.entity.RewardsVO;

import java.util.List;

public interface RewardsService {
    //查询所有
    List<RewardsVO> getAll();
    //根据Id删除
    int deleteById(int id);
    //关键字搜索
    List<RewardsVO> selectByKeywords(String keywords);
    //新增奖惩
    int insertRewards(Rewards rewards);
}
