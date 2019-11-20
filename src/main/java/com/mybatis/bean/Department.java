package com.mybatis.bean;

import java.util.List;

/**
 * @author Lee
 * @create 2019/11/19 9:58
 */
public class Department {

    private Integer id;
    private String departName;
    private List<Employee> emps;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public List<Employee> getEmps() {
        return emps;
    }

    public void setEmps(List<Employee> emps) {
        this.emps = emps;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", departName='" + departName + '\'' +
                '}';
    }
}
