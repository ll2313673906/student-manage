package com.sm.dao.impl;

import com.sm.dao.CClassDAO;
import com.sm.dao.DepartmentDAO;
import com.sm.entity.CClass;
import com.sm.entity.Department;
import com.sm.factory.DAOFactory;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class CClassDAOImplTest {
    private CClassDAO cClassDAO = DAOFactory.getCClassDAOInstance();

    @Test
    public void selectByDepartmentId() {
        List<CClass> cClassList = null;
        try {
            cClassList = cClassDAO.selectByDepartmentId(1);
        }catch (SQLException e){
            e.printStackTrace();
        }
        cClassList.forEach(cClass -> System.out.println(cClass));

    }


    @Test
    public void insertCClass() {
        CClass cClass = new CClass();
        cClass.setDepartmentId(1);
        cClass.setClassName("动漫1811");
        int n = 0;
        try {
            n = cClassDAO.insertClass(cClass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals(1,n);
    }

    @Test
    public void deleteCClassById() {
        try{
            System.out.println(cClassDAO.deleteClassById(1));
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}