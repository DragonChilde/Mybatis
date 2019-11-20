package com.mybatis.dao;

import com.mybatis.bean.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Lee
 * @create 2019/11/20 10:14
 */
public interface EmployeeMapperDynamicSQL {

    public List<Employee> getEmpsByConditionIfWhere(Employee condition);

    public List<Employee> getEmpsByConditionTrim(Employee condition);

    public void updateEmpsByConditionSet(Employee condition);

    public List<Employee> getEmpsByConditionChoose(Employee condition);

    /*指定命名参数ids*/
    public List<Employee> getEmpsByConditionForeach(@Param("ids") List<Integer> ids);


    //批量操作:  修改  删除  添加
    public void addEmps(@Param("emps") List<Employee> emps);
}
