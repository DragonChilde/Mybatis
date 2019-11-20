import com.mybatis.bean.Employee;
import com.mybatis.dao.EmployeeMapperDynamicSQL;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lee
 * @create 2019/11/20 10:26
 */
public class TestMybatisDynamicSQL {

    private SqlSessionFactory createSessionFactory() throws IOException
    {

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);

    }

    @Test
    public void testIf() throws IOException
    {
        SqlSessionFactory sessionFactory = createSessionFactory();
        SqlSession session = sessionFactory.openSession();
        EmployeeMapperDynamicSQL mapper = session.getMapper(EmployeeMapperDynamicSQL.class);
        Employee employee = new Employee();
        employee.setId(1001);
        employee.setLastName("李四");
        employee.setEmail("lisi@gmail.com");
        employee.setGender(0);

        List<Employee> list = mapper.getEmpsByConditionIfWhere(employee);
        System.out.println(list);
    }

    @Test
    public void testTrim() throws IOException
    {
        SqlSessionFactory sessionFactory = createSessionFactory();
        SqlSession session = sessionFactory.openSession();
        EmployeeMapperDynamicSQL mapper = session.getMapper(EmployeeMapperDynamicSQL.class);
        Employee employee = new Employee();
        employee.setId(1001);
        employee.setLastName("李四");
        employee.setEmail("lisi@gmail.com");
        employee.setGender(0);

        List<Employee> list = mapper.getEmpsByConditionTrim(employee);
        System.out.println(list);
    }


    @Test
    public void testSet() throws IOException
    {
        SqlSessionFactory sessionFactory = createSessionFactory();
        SqlSession session = sessionFactory.openSession(true);
        EmployeeMapperDynamicSQL mapper = session.getMapper(EmployeeMapperDynamicSQL.class);
        Employee employee = new Employee();
        employee.setId(1001);
        employee.setLastName("李三");
        employee.setEmail("lisi@gmail.com");
        employee.setGender(0);

        mapper.updateEmpsByConditionSet(employee);

    }

    @Test
    public void testChoose() throws IOException
    {
        SqlSessionFactory sessionFactory = createSessionFactory();
        SqlSession session = sessionFactory.openSession();
        EmployeeMapperDynamicSQL mapper = session.getMapper(EmployeeMapperDynamicSQL.class);
        Employee employee = new Employee();
        employee.setId(1001);
        employee.setLastName("李三");
        employee.setEmail("lisi@gmail.com");
       employee.setGender(0);

        List<Employee> list = mapper.getEmpsByConditionChoose(employee);
        System.out.println(list);

    }

    @Test
    public void testForeach() throws IOException
    {
        SqlSessionFactory sessionFactory = createSessionFactory();
        SqlSession session = sessionFactory.openSession();
        EmployeeMapperDynamicSQL mapper = session.getMapper(EmployeeMapperDynamicSQL.class);
        List<Integer> ids = new ArrayList<>();
        ids.add(1001);
        ids.add(1004);

        List<Employee> list = mapper.getEmpsByConditionForeach(ids);
        System.out.println(list);

    }

    @Test
    public void testAddEmps() throws IOException
    {
        SqlSessionFactory sessionFactory = createSessionFactory();
        SqlSession session = sessionFactory.openSession(true);
        EmployeeMapperDynamicSQL mapper = session.getMapper(EmployeeMapperDynamicSQL.class);

        List<Employee> list = new ArrayList<>();
        list.add(new Employee(null,"julia", "julia@gmail.com", 1));
        list.add(new Employee(null,"爱田老师", "aitian@gmail.com", 1));
        list.add(new Employee(null,"吉择老师", "jizhe@gmail.com", 1));
        mapper.addEmps(list);
    }
}
