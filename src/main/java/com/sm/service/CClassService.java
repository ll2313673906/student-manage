package com.sm.service;

import com.sm.entity.CClass;

import java.sql.SQLException;
import java.util.List;

public interface CClassService {
    List<CClass> selectByDepartmentId(int departmentId);

    int addCClass(CClass cClass);

    List<CClass> selectAll();

    void deleteCClassId(long id);

    /**
     * 根据院系统计班级
     * @param departmentId
     * @return
     */
    int countByDepartmentId(int departmentId) throws SQLException;
}
