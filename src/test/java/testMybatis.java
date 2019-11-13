import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;

/**
 * @author Lee
 * @create 2019/11/13 17:45
 */

public class testMybatis {

    @Test
    public void testSqlSessionFactory() throws IOException
    {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);

        System.out.println(sqlSessionFactory);

    }
}
