package com.sm.service.impl;


import com.sm.dao.StudentDAO;
import com.sm.entity.StudentVO;
import com.sm.factory.DAOFactory;
import com.sm.service.StudentService;

import java.sql.SQLException;
import java.util.List;

public class StudentServiceImpl implements StudentService {
    private StudentDAO studentDAO = DAOFactory.getStudentDAOIntance();
    @Override
    public List<StudentVO> selectAll() {
        List<StudentVO> studentList = null;
        try {
            studentList = studentDAO.selectAll();
        } catch (SQLException e) {
            System.out.println("查询学生异常");
        }
        return studentList;
    }

}





//    private CClassDAO cClassDAO = DAOFactory.getCClassDAOInstance();
//    @Override
//    public List<CClass> selectByDepartmentId(int departmentId) {
//        List<CClass> classList = null;
//        try {
//            classList = cClassDAO.selectByDepartmentId(departmentId);
//        } catch (SQLException e) {
//            System.err.print("查询院系信息出现异常");
//        }
//        return classList;
//    }