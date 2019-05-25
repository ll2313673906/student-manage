package com.sm.dao;

import com.sm.entity.Admin;
import com.sm.entity.Department;

import java.sql.SQLException;

public interface AdminDAO {
    /**
     * 根据账号查找管理员
     * @param account
     * @return Admin
     * @throws SQLException
     */
    Admin getAdminByAccount(String account) throws SQLException;
    int insertDepartment (Department department) throws SQLException;

    int deleteDepartmentById (int id) throws SQLException;
}
