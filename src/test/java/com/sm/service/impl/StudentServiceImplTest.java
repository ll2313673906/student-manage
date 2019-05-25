package com.sm.service.impl;
import com.sm.entity.CClass;
import com.sm.entity.StudentVO;
import com.sm.factory.ServiceFactory;
import com.sm.service.StudentService;
import org.junit.Test;

import java.util.List;

public class StudentServiceImplTest {
    private StudentService studentService = ServiceFactory.getStudentServieceInstance();


    @Test
    public void selectAll() {
        List<StudentVO> studentVOList = studentService.selectAll();
        studentVOList.forEach(studentVO -> System.out.println(studentVO));
    }
}
