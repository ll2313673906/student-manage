package com.sm.dao;

import com.sm.entity.Student;
import com.sm.entity.StudentVO;

import java.sql.SQLException;
import java.util.List;

public interface StudentDAO {
    /**
     * 查询所有学生（视图对象）
     * @return  List<StudentVO>
     * @throws SQLException
     */
    List<StudentVO> selectAll() throws SQLException;


    /**
     * 根据学院的ID，查询学生
     * @param departmentId
     * @return List<StudentVO>
     * @throws SQLException
     */
    List<StudentVO> selectByDepartmentId(int departmentId) throws SQLException;

    /**
     * 根据班级Id查询所有学生
     * @param classId
     * @return List<StudentVO>
     * @throws SQLException
     */

    List<StudentVO> selectByClassId(int classId) throws SQLException;

    /**
     * 根据关键词模糊查询
     * @param keywords
     * @return List<StudentVO>
     * @throws SQLException
     */
    List<StudentVO> selectByKeywords(String keywords) throws SQLException;

    /**
     * 编辑学生信息功能
     * @param student
     * @return
     * @throws SQLException
     */
    int updateStudent(Student student)throws SQLException;

    /**
     * 删除学生
     * @param id
     * @return
     * @throws SQLException
     */
    int deleteById(String id)throws SQLException;

    /**
     * 新增学生
     * @param student
     * @return
     * @throws SQLException
     */
    int insertStudent(Student student)throws SQLException;




































    /**
     * 根据学院的id统计学生的方法
     * @param departmentId
     * @return
     * @throws SQLException
     */
    int countByDepartmentId(int departmentId)throws SQLException;

    /**
     * 根据班级的Id统计学生
     * @param classId
     * @return
     * @throws SQLException
     */
    int countByClassId(int classId)throws SQLException;




}
