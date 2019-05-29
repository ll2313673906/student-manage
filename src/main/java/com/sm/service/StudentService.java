package com.sm.service;

import com.sm.entity.CClass;
import com.sm.entity.Student;
import com.sm.entity.StudentVO;

import java.sql.SQLException;
import java.util.List;

public interface StudentService {
    List<StudentVO> selectAll();




 ///
    /**
     * 根据学院差学生
     * @param departmentId
     * @return
     */
    List<StudentVO> selectByDepartmentId(int departmentId);

    /**
     * 根据班级查学生
     * @param classId
     * @return
     */
    List<StudentVO> selectByClassId(int classId);

    /**
     * 根据关键词查学生
     * @param keywords
     * @return
     */
    List<StudentVO> selectByKeywords(String keywords);

    /**
     * 编辑学生信息
     * @param student
     * @return
     */
    int updateStudent(Student student);

    /**
     * 删除学生
     * @param id
     */
    void deleteById(String id);























    int countByDepartmentId(int departmentId);
    int countStudentByClassId(int Id);

}
