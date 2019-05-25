package com.sm.service;

import com.sm.entity.CClass;

import java.util.List;

public interface CClassService {
    List<CClass> selectByDepartmentId(int departmentId);

    int addCClass(CClass cClass);

    void deleteCClassId(long id);
}
