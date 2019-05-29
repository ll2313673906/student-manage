package com.sm.dao.impl;

import com.sm.dao.StudentDAO;
import com.sm.entity.Student;
import com.sm.entity.StudentVO;
import com.sm.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    @Override
    public List<StudentVO> selectAll() throws SQLException {
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "SELECT t1.*,t2.class_name,t3.department_name\n" +
                "FROM t_student t1\n" +
                "LEFT JOIN t_class t2\n" +
                "ON t1.class_id = t2.id\n" +
                "LEFT JOIN t_department t3\n" +
                "ON t2.department_id = t3.id";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List<StudentVO> studentList = convert(rs);
       // List<StudentVO> studentList = new ArrayList<>();
       //while (rs.next()){
       //    StudentVO student = new StudentVO();
       //    student.setId(rs.getString("id"));
       //    student.setStudentName(rs.getString("student_name"));
       //    student.setGender(rs.getString("gender"));
       //    student.setAvatar(rs.getString("avatar"));
       //    student.setPhone(rs.getString("phone"));
       //    student.setAddress(rs.getString("address"));
       //    student.setBirthday(rs.getDate("birthday"));
       //    student.setDepartmentName(rs.getString("department_name"));
       //    student.setClassName(rs.getString("class_name"));
       //    studentList.add(student);
       //}
        rs.close();
        pstmt.close();
        jdbcUtil.closeConnection();
        return studentList;
    }







    @Override
    public List<StudentVO> selectByDepartmentId(int departmentId) throws SQLException {
        //根据院系查询Id
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "SELECT t1.*,t2.class_name,t3.department_name\n"+
                "FROM t_student t1\n"+
                "LEFT JOIN t_class t2\n" +
                "ON t1.class_id = t2.id\n" +
                "LEFT JOIN t_department t3\n" +
                "ON t2.department_id = t3.id\n"+
                "WHERE t3.id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1,departmentId);
        ResultSet rs = pstmt.executeQuery();
        List<StudentVO> studentList = convert(rs);
        rs.close();
        pstmt.close();
        jdbcUtil.closeConnection();

        return studentList;
    }

    @Override
    public List<StudentVO> selectByClassId(int classId) throws SQLException {
        //根据班级id查询
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "SELECT t1.*,t2.class_name,t3.department_name\n"+
                "FROM t_student t1\n"+
                "LEFT JOIN t_class t2\n" +
                "ON t1.class_id = t2.id\n" +
                "LEFT JOIN t_department t3\n" +
                "ON t2.department_id = t3.id\n"+
                "WHERE t1.class_id = ?";
        PreparedStatement pstmt= connection.prepareStatement(sql);
        pstmt.setInt(1,classId);
        ResultSet rs = pstmt.executeQuery();
        List<StudentVO> studentList = convert(rs);
        rs.close();
        pstmt.close();
        jdbcUtil.closeConnection();
        return studentList;
    }

    @Override
    public List<StudentVO> selectByKeywords(String keywords) throws SQLException {
        //根据关键字进行检索
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "SELECT t1.*,t2.class_name,t3.department_name\n"+
                "FROM t_student t1\n"+
                "LEFT JOIN t_class t2\n" +
                "ON t1.class_id = t2.id\n" +
                "LEFT JOIN t_department t3\n" +
                "ON t2.department_id = t3.id\n"+
                "WHERE t1.student_name LIKE ? OR t1.address LIKE  ? ";
        PreparedStatement pstmt= connection.prepareStatement(sql);
        pstmt.setString(1,"%" + keywords + "%");
        pstmt.setString(2,"%" + keywords + "%");
        ResultSet rs = pstmt.executeQuery();
        List<StudentVO> studentList = convert(rs);
        rs.close();
        pstmt.close();
        jdbcUtil.closeConnection();
        return studentList;
    }

    @Override
    public int updateStudent(Student student) throws SQLException {
        //编辑学生信息
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "UPDATE t_student SET address = ?,phone = ? WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1,student.getAddress());
        pstmt.setString(2,student.getPhone());
        pstmt.setString(3,student.getId());
        int n = pstmt.executeUpdate();
        pstmt.close();
        connection.close();
        return n;
    }

    @Override
    public int deleteById(String id) throws SQLException {
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "DELECT FROM t_student WHERE id = " + id;
        PreparedStatement pstmt = connection.prepareStatement(sql);
        int n = pstmt.executeUpdate();
        pstmt.close();
        connection.close();
        return n;
    }


    /**
     * 封装方法
     * @param rs
     * @return List<StudentVO>
     * @throws SQLException
     */
    private List<StudentVO> convert(ResultSet rs)throws SQLException{
        List<StudentVO> studentList = new ArrayList<>();
        while (rs.next()){
            StudentVO student = new StudentVO();
            student.setId(rs.getString("id"));
            student.setDepartmentName(rs.getString("department_name"));
            student.setClassName(rs.getString("class_name"));
            student.setStudentName(rs.getString("student_name"));
            student.setAvatar(rs.getString("avatar"));
            student.setGender(rs.getString("gender"));
            student.setBirthday(rs.getDate("birthday"));
            student.setAddress(rs.getString("address"));
            student.setPhone(rs.getString("phone"));
            studentList.add(student);

        }
        return studentList;
    }













































    @Override
    public int countByDepartmentId(int departmentId) throws SQLException {
        //按照学院id统计学生
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "SELECT COUNT(*)FROM t_student t1 LEFT JOIN t_class t2 ON t1.class_id = t2.id\n" +
                "LEFT JOIN t_department t3 ON t2.department_id = t3.id\n" +
                "WHERE t3.id = ?";
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

    @Override
    public int countByClassId(int classId) throws SQLException {
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "SELECT COUNT(*)FROM t_student t1 LEFT JOIN t_class t2 ON t1.class_id = t2.id\n" +
                "LEFT JOIN t_department t3 ON t2.department_id = t3.id\n" +
                "WHERE t3.id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1,classId);
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

}
