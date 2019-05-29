package com.sm.dao.impl;

import com.sm.dao.StudentDAO;
import com.sm.entity.Student;
import com.sm.entity.StudentVO;
import com.sm.factory.DAOFactory;
import org.junit.Test;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class StudentDAOImplTest {
    private StudentDAO studentDAO = DAOFactory.getStudentDAOIntance();

    @Test
    public void selectAll() {
        List<StudentVO> studentVOList = null;
        try{
            studentVOList = studentDAO.selectAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        studentVOList.forEach(studentVO -> System.out.println(studentVO));
    }


    @Test
    public void selectByDepartmentId() {
        List<StudentVO> studentList = null;
        try {
            studentList = studentDAO.selectByDepartmentId(3);
        }catch (SQLException e){
            e.printStackTrace();
        }
        studentList.forEach(studentVO -> System.out.println(studentVO));
    }

    @Test
    public void selectByClassId() {
        List<StudentVO> studentList = null;
        try {
            studentList = studentDAO.selectByClassId(3);
        }catch (SQLException e){
            e.printStackTrace();
        }
        studentList.forEach(studentVO -> System.out.println(studentVO));
    }

    @Test
    public void selectByKeywords() {
        List<StudentVO> studentList = null;
        try {
            studentList = studentDAO.selectByKeywords("江苏");
        }catch (SQLException e){
            e.printStackTrace();
        }
        studentList.forEach(studentVO -> System.out.println(studentVO));
    }

    @Test
    public void updateStudent() throws SQLException {
        //编辑学生功能测试
        Student student = new Student();
        student.setAddress("毕节");
        student.setPhone("1111");
        student.setId("1802343311");
        studentDAO.updateStudent(student);
    }

    @Test
    public void deleteById() {
        //删除学生
        try {
            int n = studentDAO.deleteById("1802343310");
            assertEquals(1,n);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}