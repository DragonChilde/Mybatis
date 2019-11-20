package com.mybatis.dao;

import com.mybatis.bean.Employee;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Lee
 * @create 2019/11/14 11:43
 */
public interface EmployeeMapper {

    //定义 CRUD 相关的方法

    //根据id查询Employee
    public Employee selectEmployeeById(Integer id);


    public boolean insertEmployee(Employee employee);

    public void updateEmployeeById(Employee employee);

    public void delEmployeeById(Integer id);

    public Employee selectEmployeeByIdAndLastName(@Param("id") Integer id, @Param("lastName") String lastName);

    public Employee selectEmployeeByMap(Map<String,Object> map);

    public List<Employee> getAllEmps();

    public Map<String,Object> getEmployeeByIdReturnMap(Integer id);

    @MapKey("id")
    public Map<Integer,Employee> getEmployeesReturnMap();
}
