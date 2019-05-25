package com.sm.dao.impl;

import com.sm.dao.DepartmentDAO;
import com.sm.entity.Department;
import com.sm.factory.DAOFactory;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class DepartmentDAOImplTest {
    private DepartmentDAO departmentDAO = DAOFactory.getDepartmentDAOInstance();

    @Test
    public void getAll() {
        List<Department> departmentList = null;
        try {
            departmentList = departmentDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        departmentList.forEach(department -> System.out.println(department));
    }
    @Test
    public void insertDepartment() {
        Department department = new Department();
        department.setDepartmentName("测试院系");
        department.setLogo("https://student-manager1.oss-cn-beijing.aliyuncs.com/img/005.jpg");

        try {
            int n = departmentDAO.insertDepartment(department);
            assertEquals(1, n);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
