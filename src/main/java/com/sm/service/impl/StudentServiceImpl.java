package com.sm.service.impl;


import com.sm.dao.StudentDAO;
import com.sm.entity.Student;
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


///


    @Override
    public List<StudentVO> selectByDepartmentId(int departmentId) {

        List<StudentVO> studentList = null;
        try {
            studentList = studentDAO.selectByDepartmentId(departmentId);
        } catch (SQLException e) {
            System.err.print("查询院系信息出现异常");
        }
        return studentList;
    }


    @Override
    public List<StudentVO> selectByClassId(int classId) {
        List<StudentVO> studentList = null;
        try {
            studentList = studentDAO.selectByClassId(classId);
        } catch (SQLException e) {
            System.err.print("查询班级信息出现异常");
        }
        return studentList;
    }

    @Override
    public List<StudentVO> selectByKeywords(String keywords) {
        List<StudentVO> studentList = null;
        try {
            studentList = studentDAO.selectByKeywords(keywords);
        } catch (SQLException e) {
            System.err.print("查询信息出现异常");
        }
        return studentList;
    }

    @Override
    public int updateStudent(Student student) {
        /**
         * 编辑学生信息
         */
        int n = 0;
        try {
            n = studentDAO.updateStudent(student);
        }catch (SQLException e){
            System.err.print("编辑学生信息失败");
        }
        return n;
    }

    @Override
    public void deleteById(String id) {
            //删除学生
        try {
            studentDAO.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public int countByDepartmentId(int departmentId) {
        int n = 0;
        try {
            n = studentDAO.countByDepartmentId(departmentId);

        }catch (SQLException e){
            System.out.println("统计失败");
        }
        return n;
    }

    @Override
    public int countStudentByClassId(int classId) {
        int n = 0;
        try {
            n = studentDAO.countByClassId(classId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
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