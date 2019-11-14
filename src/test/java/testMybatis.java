import com.mybatis.bean.Employee;
import com.mybatis.dao.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;


/**
 * @author Lee
 * @create 2019/11/13 17:45
 */

public class testMybatis {


    /**
     *
     * MyBatis HelloWorld 小结:
     * 	 两个重要的配置文件
     * 		mybatis-config.xml :全局配置文件 , 数据库连接信息、 引入SQL映射文件等....
     * 		EmployeeMapper.xml :SQL映射文件 , 配置增删改查的SQL语句的映射
     *
     *  两个重要的对象
     *  	SqlSessionFactory: SqlSession的工厂对象， 主要是用于获取SqlSession对象
     *  	SqlSession:  Java程序与数据库的会话对象.可以理解为是对Connection的封装.
     */

    @Test
    public void testSqlSessionFactory() throws IOException
    {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);

        System.out.println(sqlSessionFactory);

        SqlSession session = sqlSessionFactory.openSession();

        System.out.println(session);

        try{
            /**
             *  statement    Unique identifier matching the statement to use.
             *  			 SQL语句的唯一标识
             parameter    A parameter object to pass to the statement.
             执行SQL需要用到的参数
             */
            Employee employee = session.selectOne("suibianxie.selectEmployee", 1001);
            System.out.println(employee);
        }finally {
            session.close();
        }
    }

    @Test
    public void testHelloWorldMapper() throws IOException
    {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();

        try {
            //mapper接口： dao接口
            /*
             * 两个绑定:
             * 	 1. Mapper接口与SQL映射文件的绑定.  映射文件的namesapce的值必须指定成接口的全类名.
             * 	 2. Mapper接口的方法  与 SQL映射文件的具体SQL语句的绑定    SQL语句的id值  必须指定成接口的方法名.
             *
             * Mapper接口开发的好处:
             * 	 1. 有更明确的类型
             * 	 2. 接口本身: 接口本身就是抽象. 抽出了规范.
             * 			EmployeeDao:    EmployeeDaoJdbcImpl 、 EmployeeDaoHibernateImpl、MyBatis代理实现类
             */
            //获取MyBatis为Mapper接口生成的代理实现类对象
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);

            System.out.println(mapper.getClass().getName());
            Employee employee = mapper.selectEmployeeById(1001);
            System.out.println(employee);
        }finally {
            session.close();
        }
    }
}
