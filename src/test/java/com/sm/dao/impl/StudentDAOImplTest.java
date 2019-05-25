package com.sm.dao.impl;

import com.sm.dao.StudentDAO;
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
}