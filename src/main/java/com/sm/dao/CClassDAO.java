package com.sm.dao;

import com.sm.entity.CClass;

import java.sql.SQLException;
import java.util.List;

public interface CClassDAO {
    /**
     * 按照院系id查询班级
     *
     * @param departmentId
     * @return List<CClass>
     * @throws SQLException
     */
    List<CClass> selectByDepartmentId(int departmentId) throws SQLException ;


    int insertClass(CClass cClass)throws SQLException;

    /**
     * 通过id删除班级
     * @param id
     * @return
     * @throws SQLException
     */
    long deleteClassById(long id) throws SQLException;

    //学生管理
    /**
     * 查询所有班级
     * @return List<CClass>
     * @throws SQLException
     */
    List<CClass> selectAll() throws SQLException;




    /**
     * 根据院系id统计班级数
     * @param departmentId
     * @return int
     * @throws SQLException
     */
    int countByDepartmentId(int departmentId)throws SQLException;



}
