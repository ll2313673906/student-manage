package com.sm.service.impl;

import com.sm.entity.CClass;
import com.sm.factory.ServiceFactory;
import com.sm.service.CClassService;
import org.junit.Test;

import java.util.List;

public class CClassServiceImplTest {
    private CClassService cClassService = ServiceFactory.getCClassServiceInstance();

    @Test
    public void selectByDepartmentId() {
        List<CClass> cClass = cClassService.selectByDepartmentId(0);
        cClass.forEach(department -> System.out.println(cClass));
    }

    /**
     * 新增班级
     *
     */
    @Test
    public void addCClass() {
        CClass cClass = new CClass();
        cClass.setDepartmentId(4);
        cClass.setClassName("软件231");
        int n = cClassService.addCClass(cClass);
    }

    @Test
    public void addCClass1() {
        CClass  cClass = new CClass();
        cClass.setId(12);
        cClass.setDepartmentId(1);
        cClass.setClassName("数控123");
        cClassService.addCClass(cClass);
    }

    @Test
    public void deleteCClassId() {
        int id =2;
        cClassService.deleteCClassId(id);
    }
}
