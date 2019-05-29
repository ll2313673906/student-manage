package com.sm.service;


import com.sm.entity.Department;

import java.util.List;
import java.util.Map;

public interface DepartmentService {

    List<Department> selectAll();

    void deleteDepartment(int id);
    /**
     * 新增院系
     * @param department
     * @return int
     */
    int addDepartment(Department department);























    /**
     * 获取所有院系的完整信息，包括每个学院自身的信息，班级等学生数
     *
     * @return
     */
    List<Map> selectDepartmentInfo();
}
