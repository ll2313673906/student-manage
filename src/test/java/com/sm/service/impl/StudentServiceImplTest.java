package com.sm.service.impl;
import com.sm.entity.CClass;
import com.sm.entity.Student;
import com.sm.entity.StudentVO;
import com.sm.factory.ServiceFactory;
import com.sm.service.StudentService;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class StudentServiceImplTest {
    private StudentService studentService = ServiceFactory.getStudentServieceInstance();


    @Test
    public void selectAll() {
        List<StudentVO> studentVOList = studentService.selectAll();
        studentVOList.forEach(studentVO -> System.out.println(studentVO));
    }

    @Test
    public void selectByDepartmentId() {
        List<StudentVO> studentVOList = studentService.selectByDepartmentId(3);
        studentVOList.forEach(studentVO -> System.out.println(studentVO));
    }

    @Test
    public void selectByClassId() {
        List<StudentVO> studentVOList = studentService.selectByClassId(3);
        studentVOList.forEach(studentVO -> System.out.println(studentVO));
    }

    @Test
    public void selectByKeywords() {
        List<StudentVO> studentVOList = studentService.selectByKeywords("江苏");
        studentVOList.forEach(studentVO -> System.out.println(studentVO));
    }

    @Test
    public void updateStudent() {
        /**
         * 编辑学生信息
         */
        Student student = new Student();
        student.setId("1802343311");
        student.setAddress("毕节");
        student.setPhone("22233344");
        studentService.updateStudent(student);

    }

    @Test
    public void deleteById() {
        //删除学生
        studentService.deleteById("1802343312");
    }

    @Test
    public void insertStudent() {
        //新增学生
        Student student = new Student();
        student.setId("123");
        student.setClassID(3);
        student.setStudentName("刘恋");
        student.setBirthday(new Date());
        student.setAvatar("");
        student.setGender("女");
        student.setAddress("南京");
        student.setPhone("2222222");
        int n = studentService.insertStudent(student);
    }
}
