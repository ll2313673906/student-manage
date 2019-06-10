package com.sm.dao.impl;

import com.sm.dao.RewardsDAO;
import com.sm.entity.Rewards;
import com.sm.entity.RewardsVO;
import com.sm.utils.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RewardsDAOImpl implements RewardsDAO {
    @Override
    public List<RewardsVO> getAll() throws SQLException {
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "SELECT t1.*,t2.class_name,t3.department_name,t4.student_name\n" +
                "FROM t_rewards t1\n" +
                "LEFT JOIN t_student t4\n" +
                "ON t1.student_number = t4.`id`\n" +
                "LEFT JOIN t_class t2\n" +
                "ON t4.`class_id` = t2.`id`\n" +
                "LEFT JOIN t_department t3\n" +
                "ON t2.`department_id` = t3.`id`";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List<RewardsVO> rewardsList = convert(rs);
        rs.close();
        pstmt.close();
        jdbcUtil.closeConnection();
        return rewardsList;
    }

    @Override
    public int deleteById(int id) throws SQLException {
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "DELETE FROM t_rewards WHERE id = ? ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, id);
        int n = pstmt.executeUpdate();
        pstmt.close();
        connection.close();
        return n;
    }

    @Override
    public List<RewardsVO> selectByKeywords(String keywords) throws SQLException {
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql ="SELECT t1.*,t2.class_name,t3.department_name,t4.student_name\n" +
                "FROM t_rewards t1\n" +
                "LEFT JOIN t_student t4\n" +
                "ON t1.student_number = t4.`id`\n" +
                "LEFT JOIN t_class t2\n" +
                "ON t4.`class_id` = t2.`id`\n" +
                "LEFT JOIN t_department t3\n" +
                "ON t2.`department_id` = t3.`id`" +
                "WHERE t4.student_name LIKE ?"
                ;
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, "%" + keywords + "%");
       // pstmt.setString(2, "%" + keywords + "%");
        ResultSet rs = pstmt.executeQuery();
        List<RewardsVO> rewardsList = convert(rs);
        rs.close();
        pstmt.close();
        jdbcUtil.closeConnection();
        return rewardsList;
    }

    @Override
    public int insertRewards(Rewards rewards) throws SQLException {
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "INSERT INTO t_rewards (r_type,rewardsdate,student_number,reason)VALUES (?,?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1,rewards.getType());
        pstmt.setDate(2,new Date(rewards.getRewardsDate().getTime()));
        pstmt.setString(3,rewards.getStudentNumber());
        pstmt.setString(4,rewards.getReason());
        int n = pstmt.executeUpdate();
        pstmt.close();
        connection.close();
        return n;
    }

    private List<RewardsVO> convert(ResultSet rs) throws SQLException{
        List<RewardsVO> rewardsList = new ArrayList<>();
        while (rs.next()){
            RewardsVO rewards = new RewardsVO();
            rewards.setId(rs.getInt("id"));
            rewards.setType(rs.getString("r_type"));
            rewards.setRewardsDate(rs.getDate("rewardsdate"));
            rewards.setStudentNumber(rs.getString("student_number"));
            rewards.setReason(rs.getString("reason"));
            rewards.setStudentName(rs.getString("student_name"));
            rewards.setDepartmentName(rs.getString("department_name"));
            rewards.setClassName(rs.getString("class_name"));
            rewardsList.add(rewards);
        }

        return rewardsList;
    }
}
