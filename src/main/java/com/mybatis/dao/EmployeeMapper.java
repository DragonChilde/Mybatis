package com.mybatis.dao;

import com.mybatis.bean.Employee;

/**
 * @author Lee
 * @create 2019/11/14 11:43
 */
public interface EmployeeMapper {

    //定义 CRUD 相关的方法

    //根据id查询Employee
    public Employee selectEmployeeById(Integer id);
}
