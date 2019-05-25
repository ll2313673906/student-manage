package com.sm.service;

import com.sm.entity.CClass;
import com.sm.entity.Student;
import com.sm.entity.StudentVO;

import java.util.List;

public interface StudentService {
    List<StudentVO> selectAll();
}
