package com.mybatis.dao;

import com.mybatis.bean.Employee;

import java.util.List;

/**
 * @author Lee
 * @create 2019/11/18 17:11
 */
public interface EmployeeMapperResultMap {

    public Employee getEmployeeById(Integer id);

    public Employee getEmpAndDeptById(Integer id);

    public Employee getEmpAndDeptStep(Integer id);

    public List<Employee> getEmpsByDid(Integer id);
}
