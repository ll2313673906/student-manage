package com.sm.dao.impl;

import com.sm.dao.CClassDAO;
import com.sm.entity.CClass;
import com.sm.entity.Department;
import com.sm.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CClassDAOImpl implements CClassDAO {
    @Override
    public List<CClass> selectByDepartmentId(int departmentId) throws SQLException {
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "SELECT * FROM t_class WHERE department_id=? ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1,departmentId);
        // pstmt.setString(1, String.valueOf(departmentId));
        ResultSet rs = pstmt.executeQuery();
        List<CClass> cClassList = convert(rs);
        List<CClass> cClasses = new ArrayList<>();
        // while (rs.next()) {
        //     CClass cClass = new CClass();
        //     cClass.setId(rs.getInt("id"));
        //     cClass.setDepartmentId(rs.getInt("department_id"));
        //     cClass.setClassName(rs.getString("class_name"));
        //     cClasses.add(cClass);
        // }
        rs.close();
        pstmt.close();
        jdbcUtil.closeConnection();
        //return cClasses;
        return cClassList;
    }

    /**
     * 新增班级
     * @param cClass
     * @return
     * @throws SQLException
     */
    @Override
    public int insertClass(CClass cClass) throws SQLException {
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "INSERT INTO t_class (department_id,class_name) VALUES (?,?) ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1,cClass.getDepartmentId());
        pstmt.setString(2,cClass.getClassName());
        int n = pstmt.executeUpdate();
        pstmt.close();
        connection.close();
        return n;
    }

    /**
     * 删除班级
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public long deleteClassById(long id) throws SQLException {
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "DELETE FROM t_class WHERE id= " + id;
        PreparedStatement pstmt = connection.prepareStatement(sql);
        int n = pstmt.executeUpdate();
        pstmt.close();
        connection.close();
        return n;
    }

    @Override
    public List<CClass> selectAll() throws SQLException {
        //学生管理
            JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
            Connection connection = jdbcUtil.getConnection();
            String sql = "SELECT * FROM t_class ORDER BY department_id";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            List<CClass> cClassList = convert(rs);
            rs.close();
            pstmt.close();
            jdbcUtil.closeConnection();
            return cClassList;

        }


    @Override
    public int countByDepartmentId(int departmentId) throws SQLException {
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "SELECT COUNT(*) FROM t_class WHERE department_id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1,departmentId);
        ResultSet rs = pstmt.executeQuery();
        int rowCount = 0;
        if (rs.next()){
            rowCount = rs.getInt(1);
        }
        rs.close();
        pstmt.close();
        jdbcUtil.closeConnection();
        return rowCount;
    }
    private List<CClass> convert(ResultSet rs) throws SQLException {
        List<CClass> cClassList = new ArrayList<>();
        while (rs.next()) {
            CClass cClass = new CClass();
            cClass.setId(rs.getInt("id"));
            cClass.setDepartmentId(rs.getInt("department_id"));
            cClass.setClassName(rs.getString("class_name"));
            cClassList.add(cClass);
        }
        return cClassList;

    }
    public static void main(String[] args) {

    }
}
