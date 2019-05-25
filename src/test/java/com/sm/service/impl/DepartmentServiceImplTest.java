package com.sm.service.impl;

import com.sm.entity.Department;
import com.sm.factory.ServiceFactory;
import com.sm.service.DepartmentService;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DepartmentServiceImplTest {

    private DepartmentService departmentService = ServiceFactory.getDepartmentServiceInstance();

    @Test
    public void selectAll() {
        List<Department> departmentList = departmentService.selectAll();
        departmentList.forEach(department -> System.out.println(department));
    }

    @Test
    public void addDepartment() {
        Department department = new Department();
        department.setDepartmentName("测试院系2");
        department.setLogo("https://student-manager1.oss-cn-beijing.aliyuncs.com/logo/4eed2840-3c2c-4303-85d4-561d101e398c.jpg");
        int n = departmentService.addDepartment(department);
    }
    }
