package com.sm.service.impl;

import com.sm.dao.CClassDAO;
import com.sm.dao.DepartmentDAO;
import com.sm.entity.CClass;
import com.sm.factory.DAOFactory;
import com.sm.service.CClassService;

import java.sql.SQLException;
import java.util.List;

public class CClassServiceImpl implements CClassService {
    private CClassDAO cClassDAO = DAOFactory.getCClassDAOInstance();
    @Override
    public List<CClass> selectByDepartmentId(int departmentId) {
        List<CClass> classList = null;
        try {
            classList = cClassDAO.selectByDepartmentId(departmentId);
        } catch (SQLException e) {
            System.err.print("查询院系信息出现异常");
        }
        return classList;
    }

    @Override
    public int addCClass(CClass cClass) {
        int result = 0;

        try {
            result = cClassDAO.insertClass(cClass);
        } catch (SQLException e) {
            System.out.println("新增班级异常");
        }
        return result;
    }

    @Override
    public List<CClass> selectAll() {

            List<CClass> cClassList = null;
            try {
                cClassList = cClassDAO.selectAll();
            } catch (SQLException e) {
                System.err.print("查询院系信息出现异常");
            }
            return cClassList;
        }


    @Override
    public void deleteCClassId(long id) {
        try {
            cClassDAO.deleteClassById((int) id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }







    @Override
    public int countByDepartmentId(int departmentId) throws SQLException {
        int n = 0;
        try {
            departmentId = cClassDAO.countByDepartmentId(departmentId);
        } catch (SQLException e) {
            System.err.print("查询院系信息出现异常");
        }
        return n;
    }
}




