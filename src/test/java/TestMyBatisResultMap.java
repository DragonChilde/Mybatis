import com.mybatis.bean.Department;
import com.mybatis.bean.Employee;
import com.mybatis.dao.DepartmentMapperResultMap;
import com.mybatis.dao.EmployeeMapperResultMap;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Lee
 * @create 2019/11/18 17:16
 */
public class TestMyBatisResultMap {


    private SqlSessionFactory createSessionFactory() throws IOException
    {

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);

    }

    @Test
    public void testResultMap() throws IOException
    {
        SqlSessionFactory sessionFactory = createSessionFactory();
        SqlSession session = sessionFactory.openSession();
        EmployeeMapperResultMap mapper = session.getMapper(EmployeeMapperResultMap.class);
        Employee emp = mapper.getEmployeeById(1001);
        System.out.println(emp);
    }

    @Test
    public void testResultMapCascade() throws IOException
    {
        SqlSessionFactory sessionFactory = createSessionFactory();
        SqlSession session = sessionFactory.openSession();
        EmployeeMapperResultMap mapper = session.getMapper(EmployeeMapperResultMap.class);
        Employee employee = mapper.getEmpAndDeptById(1001);
        System.out.println(employee.getDepartment());
        System.out.println(employee);
    }

    @Test
    public void testResultMapAssociation() throws IOException
    {
        SqlSessionFactory sessionFactory = createSessionFactory();
        SqlSession session = sessionFactory.openSession();
        EmployeeMapperResultMap mapper = session.getMapper(EmployeeMapperResultMap.class);
        Employee employee = mapper.getEmpAndDeptStep(1001);
      /*  System.out.println(employee);
        System.out.println(employee.getDepartment());*/
        System.out.println(employee.getLastName());

        System.out.println("----------");
        System.out.println(employee.getDepartment());
    }

    @Test
    public void testResultMapCollection() throws IOException
    {
        SqlSessionFactory sessionFactory = createSessionFactory();
        SqlSession session = sessionFactory.openSession();
        DepartmentMapperResultMap mapper = session.getMapper(DepartmentMapperResultMap.class);
        Department deptAndEmps = mapper.getDeptAndEmps(3);
        System.out.println(deptAndEmps);
        System.out.println(deptAndEmps.getEmps());
    }

    @Test
    public void testResultMapCollectionStep() throws IOException{
        SqlSessionFactory sessionFactory = createSessionFactory();
        SqlSession session = sessionFactory.openSession();
        DepartmentMapperResultMap mapper = session.getMapper(DepartmentMapperResultMap.class);
        Department deptAndEmps = mapper.getDeptAndEmpsStep(3);
        System.out.println(deptAndEmps.getDepartName());
        System.out.println("----------------");
        System.out.println(deptAndEmps.getEmps());
    }


}
