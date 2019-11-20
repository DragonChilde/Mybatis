package com.mybatis.dao;

import com.mybatis.bean.Department;

/**
 * @author Lee
 * @create 2019/11/19 10:57
 */
public interface DepartmentMapperResultMap {

    public Department getDeptById(Integer id);

    public Department getDeptAndEmps(Integer id);

    public Department getDeptAndEmpsStep(Integer id);
}
