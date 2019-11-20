<a href="http://120.77.237.175:9080/photos/mybatis">Mybatis</a>

# MyBatis HelloWorld #

## 开发环境的准备 ##

1. 导入MyBatis框架的jar包、Mysql驱动包、log4j的jar包

		<?xml version="1.0" encoding="UTF-8"?>
		<project xmlns="http://maven.apache.org/POM/4.0.0"
		         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
		    <modelVersion>4.0.0</modelVersion>
		
		    <groupId>com.mybatis</groupId>
		    <artifactId>mybatis</artifactId>
		    <version>1.0-SNAPSHOT</version>
		
		    <dependencies>
		        <!--mybatis-->
		        <dependency>
		            <groupId>org.mybatis</groupId>
		            <artifactId>mybatis</artifactId>
		            <version>3.4.1</version>
		        </dependency>
		        <!--mysql驱动包-->
		        <dependency>
		            <groupId>mysql</groupId>
		            <artifactId>mysql-connector-java</artifactId>
		            <version>5.1.37</version>
		        </dependency>
		        <!--日志-->
		        <dependency>
		            <groupId>log4j</groupId>
		            <artifactId>log4j</artifactId>
		            <version>1.2.17</version>
		        </dependency>
		        <!--测试-->
		        <dependency>
		            <groupId>junit</groupId>
		            <artifactId>junit</artifactId>
		            <version>4.12</version>
		            <scope>test</scope>
		        </dependency>
		    </dependencies>
		    <build>
		        <plugins>
		            <!-- 设置编译版本为1.8 -->
		            <plugin>
		                <groupId>org.apache.maven.plugins</groupId>
		                <artifactId>maven-compiler-plugin</artifactId>
		                <configuration>
		                    <source>1.8</source>
		                    <target>1.8</target>
		                    <encoding>UTF-8</encoding>
		                </configuration>
		            </plugin>
		        </plugins>
		
		    </build>
		</project>

2. 导入log4j 的配置文件

		<?xml version="1.0" encoding="UTF-8" ?>
		<!DOCTYPE log4j:configuration SYSTEM "http://logging.apache.org/log4j/1.2/apidocs/org/apache/log4j/xml/doc-files/log4j.dtd">
		<log4j:configuration debug="true">
		    <appender name="STDOUT" class="org.apache.log4j.ConsoleAppender">
		        <param name="Encoding" value="UTF-8" />
		        <layout class="org.apache.log4j.PatternLayout">
		            <param name="ConversionPattern" value="%-5p %d{MM-dd HH:mm:ss,SSS} %m  (%F:%L) \n" />
		        </layout>
		    </appender>
		    <logger name="java.sql">
		        <level value="debug" />
		    </logger>
		    <logger name="org.apache.ibatis">
		        <level value="info" />
		    </logger>
		    <root>
		        <level value="debug" />
		        <appender-ref ref="STDOUT" />
		    </root>
		</log4j:configuration>

## 创建测试表 ##

	-- 创建库
	CREATE DATABASE test_mybatis;
	-- 使用库
	USE test_mybatis;
	-- 创建表
	CREATE TABLE tbl_employee(
	   id INT(11) PRIMARY KEY AUTO_INCREMENT,
	   last_name VARCHAR(50),
	   email VARCHAR(50),
	   gender CHAR(1)
	);


## 创建javaBean ##

		public class Employee {
	    private Integer id ;
	    private String lastName;
	    private String email ;
	    private Integer gender ;
	
	    public Employee(Integer id, String lastName, String email, Integer gender) {
	        this.id = id;
	        this.lastName = lastName;
	        this.email = email;
	        this.gender = gender;
	    }
	
	    public Employee() {
	    }
	
	    public Integer getId() {
	        return id;
	    }
	
	    public void setId(Integer id) {
	        this.id = id;
	    }
	
	    public String getLastName() {
	        return lastName;
	    }
	
	    public void setLastName(String lastName) {
	        this.lastName = lastName;
	    }
	
	    public String getEmail() {
	        return email;
	    }
	
	    public void setEmail(String email) {
	        this.email = email;
	    }
	
	    public Integer getGender() {
	        return gender;
	    }
	
	    public void setGender(Integer gender) {
	        this.gender = gender;
	    }
	
	    @Override
	    public String toString() {
	        return "Employee{" +
	                "id=" + id +
	                ", lastName='" + lastName + '\'' +
	                ", email='" + email + '\'' +
	                ", gender=" + gender +
	                '}';
	    }
	}


## 创建MyBatis的全局配置文件 ##

1. 参考MyBatis的官网手册

		<?xml version="1.0" encoding="UTF-8" ?>
		<!DOCTYPE configuration
		        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
		        "http://mybatis.org/dtd/mybatis-3-config.dtd">
		<!--配置-->
		<configuration>
		    <!--环境-->
		    <environments default="development">
		        <!--具体的环境-->
		        <environment id="development">
		            <transactionManager type="JDBC"/>
		            <dataSource type="POOLED">
		                <property name="driver" value="com.mysql.jdbc.Driver"/>
		                <property name="url" value="jdbc:mysql://120.77.237.175:9306/mybatis"/>
		                <property name="username" value="root"/>
		                <property name="password" value="123456"/>
		            </dataSource>
		        </environment>
		    </environments>
		    <!--引入SQL映射文件-->
		    <mappers>
		        <mapper resource="EmployeeMapper.xml"/>
		    </mappers>
		</configuration>

## 创建Mybatis的sql映射文件 ## 

		<?xml version="1.0" encoding="UTF-8" ?>
		<!DOCTYPE mapper
		        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
		        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
		<!-- 配置SQL映射
			 namespace: 名称空间.
		 		1. 随便写
		 		2. Mapper接口开发，不能随便写，按照规定来写。
		 -->
		<!--Mapper接口与Mapper文件映射绑定-->
		<mapper namespace="suibianxie">
		    <!--
				<select>: 定义查询语句
					id:  <select>的唯一标识
					resultType:  结果集的映射类型。
					#{id}: 获取参数值
		
			 -->
		    <!-- select *  from tbl_employee where id = #{id} -->
		    <select id="selectEmployee" resultType="com.mybatis.bean.Employee">
				<!--注意这里的last_name不启用别名会与自定义的bean类无法关联取不到值-->
		        select id,last_name AS lastName ,email ,gender from tbl_employee where id = #{id}
		    </select>
		</mapper>

## 测试 ##

1. 参考MyBatis的官方手册

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
	            Employee employee = session.selectOne("suibianxie.selectEmployeeByID", 1001);
	            System.out.println(employee);
				/**Employee{id=1001, lastName='李四', email='lisi@gmail.com', gender=0}**/
	        }finally {
	            session.close();
	        }
	    }

**所有初始化文件配置都可通过Mybatis官网进行配置**

## MyBatis HelloWorld小结##

-  两个重要的配置文件
	- mybatis-config.xml :全局配置文件 , 数据库连接信息、 引入SQL映射文件等....
	- EmployeeMapper.xml :SQL映射文件 , 配置增删改查的SQL语句的映射
- 两个重要的对象
	- SqlSessionFactory: SqlSession的工厂对象， 主要是用于获取SqlSession对象
	- SqlSession:  Java程序与数据库的会话对象.可以理解为是对Connection的封装.  	 

## Mapper接口开发MyBatis HelloWorld ##

1. 编写Mapper接口

		public interface EmployeeMapper {

		    //定义 CRUD 相关的方法
		
		    //根据id查询Employee
		    public Employee selectEmployeeById(Integer id);
		}

2. 完成两个绑定
	1. Mapper接口与Mapper映射文件的绑定在Mppper映射文件中的<mapper>标签中的namespace中必须指定Mapper接口的全类名
	2. Mapper映射文件中的增删改查标签的id必须指定成Mapper接口中的方法名. 

			<?xml version="1.0" encoding="UTF-8" ?>
			<!DOCTYPE mapper
			        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
			        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

			<!--Mapper接口与Mapper文件之间的映射绑定-->
			<mapper namespace="com.mybatis.dao.EmployeeMapper">

			    <!-- public Employee getEmployeeById(Integer id ); -->
				<!--Mapper接口方法与Mapper文件中的方法绑定-->
			    <select id="selectEmployeeById" resultType="com.mybatis.bean.Employee">
			        select id,last_name AS lastName ,email ,gender from tbl_employee where id = #{id}
			    </select>
			</mapper>

3. 获取Mapper接口的代理实现类对象

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
					System.out.println(mapper.getClass().getName());	//com.sun.proxy.$Proxy4,通过打印可以看出这里是通过Java的动态代理进行绑定调用的
		            Employee employee = mapper.selectEmployeeById(1001);
		            System.out.println(employee);
					/**Employee{id=1001, lastName='李四', email='lisi@gmail.com', gender=0}**/
		        }finally {
		            session.close();
		        }
		    }

**注意:这里的Mapper就是我们Java开发中的DAO层,为了开发上通俗易懂,以后统称Mapper**

**Mapper接口开发的好处:**

1. 有更明确的类型

	因为用session.selectOne()返回的是一个泛型,没有强制定义好参数类型和返回类型,随便写在编译时都看不出有任何问题
2. 接口本身: 接口本身就是抽象. 抽出了规范.

	EmployeeDao:    EmployeeDaoJdbcImpl 、 EmployeeDaoHibernateImpl、MyBatis代理实现类,可以基于接口实现多种类,方便作扩展,规范方法

# MyBatis全局配置文件 #

## MyBatis全局配置文件简介 ##

1. The MyBatis configuration contains settings and properties that have a dramatic effect on how MyBatis behaves. 
MyBatis 的配置文件包含了影响 MyBatis 行为甚深的设置（settings）和属性（properties）信息。
2. 文件结构如下:


		configuration 配置 
				properties 属性
				settings 设置
				typeAliases 类型命名
				typeHandlers 类型处理器
				objectFactory 对象工厂
				plugins 插件
				environments 环境 
						environment 环境变量 
								transactionManager 事务管理器
								dataSource 数据源
				databaseIdProvider 数据库厂商标识
				mappers 映射器

## 引入DTD文件 ##

1. 在\mybatis-3.4.1.jar!\org\apache\ibatis\builder\xml\里可以看到需要的mybatis-3-config.dtd和mybatis-3-mapper.dtd,拷贝出来放到相应的目录
2. 在IDEA->Languages&Frameworks->Schemas and DTDs加入两个文件,URI分别是http://mybatis.org/dtd/mybatis-3-config.dtd和http://mybatis.org/dtd/mybatis-3-mapper.dtd
3. 现在可以看到在全局配置文件mybatis-config.xml和EmployeeMapper.xml映射文件会有相应提示

## properties属性 ##

1. 可外部配置且可动态替换的，既可以在典型的 Java 属性文件中配置，亦可通过 properties 元素的子元素来配置

		<properties>
			<!--这种写法没什么必要,只是把配置移到了同个文件的其它地方-->
	        <property name="driver" value="com.mysql.jdbc.Driver"/>
	    </properties>

		
2. 然而properties的作用并不单单是这样，你可以创建一个资源文件，名为jdbc.properties的文件,将四个连接字符串的数据在资源文件中通过键值 对(key=value)的方式放置，不要任何符号，一条占一行

		jdbc.driver=com.mysql.jdbc.Driver
		jdbc.url=jdbc:mysql://120.77.237.175:9306/mybatis
		jdbc.username=root
		jdbc.password=123456

3. 在environment元素的dataSource元素中为其动态设置

		<?xml version="1.0" encoding="UTF-8" ?>
		<!DOCTYPE configuration
		        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
		        "http://mybatis.org/dtd/mybatis-3-config.dtd">
		<!--配置-->
		<configuration>
	   		 <!-- 1.  properties: 属性配置
		    <property>: 一个具体的属性配置
		    resource: 引入类路径下的属性文件
		    url:  引入网络路径或者是磁盘路径下的属性文件.(一般url远程链接很少用到)
		    -->
		    <properties resource="db.properties"/>
		    <!--环境-->
		    <environments default="development">
		        <!--具体的环境-->
		        <environment id="development">
		            <transactionManager type="JDBC"/>
		            <dataSource type="POOLED">
		                <property name="driver" value="${jdbc.driver}"/>
		                <property name="url" value="${jdbc.url}"/>
		                <property name="username" value="${jdbc.username}"/>
		                <property name="password" value="${jdbc.password}"/>
		            </dataSource>
		        </environment>
		    </environments>
		    <!--引入SQL映射文件-->
		    <mappers>
		        <mapper resource="EmployeeMapper.xml"/>
		    </mappers>
		</configuration>

## settings设置 ##

1. 这是 MyBatis 中极为重要的调整设置，它们会改变 MyBatis 的运行时行为
2. 包含如下的setting设置

		<settings>
			<setting name="cacheEnabled" value="true"/>
			<setting name="lazyLoadingEnabled" value="true"/>
			<setting name="multipleResultSetsEnabled" value="true"/>
			<setting name="useColumnLabel" value="true"/>
			<setting name="useGeneratedKeys" value="false"/>
			<setting name="autoMappingBehavior" value="PARTIAL"/>
			<setting name="autoMappingUnknownColumnBehavior" value="WARNING"/>
			<setting name="defaultExecutorType" value="SIMPLE"/>
			<setting name="defaultStatementTimeout" value="25"/>
			<setting name="defaultFetchSize" value="100"/>
			<setting name="safeRowBoundsEnabled" value="false"/>
			<!-- 映射下划线到驼峰命名 ,默认是关闭的-->
			<setting name="mapUnderscoreToCamelCase" value="true"/>
			<setting name="localCacheScope" value="SESSION"/>
			<setting name="jdbcTypeForNull" value="OTHER"/>
			<setting name="lazyLoadTriggerMethods"
			           value="equals,clone,hashCode,toString"/>
		</settings>


## typeAliases 别名处理 ##
1. 类型别名是为 Java 类型设置一个短的名字，可以方便我们引用某个类
		
		 <typeAliases>
	        <!-- 3. typeAliases: 别名处理
			 		<typeAlias>: 给某个java类型取别名
			 			type: 指定java的全类名
			 			alias:指定别名. 默认的别名就是类名.
			 		<package>: 为指定的包以及子包下的类批量取别名.
			 				      如 果有别名冲突的情况， 可以使用@Alias()注解为冲突的类具体指定别名.
		 -->
	        <typeAlias type="com.mybatis.bean.Employee" alias="employee"/>
	    </typeAliases>
2. 类很多的情况下，可以批量设置别名这个包下的每一个类创建一个默认的别名，就是简单类名小写

		 <typeAliases>
	        <package name="com.mybatis.bean"/>
	    </typeAliases>

	注意:这里会有一个问题,如果同一个包路径下的子包有相同的类名会报异常

	Cause: org.apache.ibatis.builder.BuilderException: Error parsing SQL Mapper Configuration. Cause: org.apache.ibatis.type.TypeException: The alias 'Employee' is already mapped to the value 'com.mybatis.bean.sub.Employee'.

		解决办法:
			1. 不要用相同类名
			2. 使用注解@Alias
	
			@Alias("emp")
			public class Employee {
			}


3. MyBatis已经取好的别名

	![](http://120.77.237.175:9080/photos/mybatis/1.png)

为了开发上的阅读理解方便,建议在Mapper.xml使用全名

	<select id="selectEmployeeById" resultType="com.mybatis.bean.Employee">
		.........
	<select>

## environments 环境配置 ##

1. MyBatis可以配置多种环境，比如开发、测试和生产环境需要有不同的配置
2. 每种环境使用一个environment标签进行配置并指定唯一标识符
3. 可以通过environments标签中的default属性指定一个环境的标识符来快速的切换环境
4. 
		environment-指定具体环境
			id：指定当前环境的唯一标识
			transactionManager、和dataSource都必须有

		    <!-- 4. 环境们
	         environments: Mybatis支持配置多个环境， 通过 default来指定具体使用的环境.
	             environment: 具体的环境
	                 <transactionManager>: 事务管理
	                     JDBC   :JdbcTransactionFactory
	                     MANAGED:ManagedTransactionFactory
	                     结论: 事务管理将来是交给Spring来做.
	                 <dataSource>: 数据源
	                     UNPOOLED :UnpooledDataSourceFactory
	                     POOLED   :PooledDataSourceFactory
	                     JNDI     :PooledDataSourceFactory
	                     结论: 数据源将来交给Spring.
	      -->

		<!--default指定切换环境-->
	    <environments default="development">
	        <!--具体的环境-->
			<!--id是唯一标识符-->
	        <environment id="development">
	            <transactionManager type="JDBC"/>
	            <dataSource type="POOLED">
	                <property name="driver" value="${jdbc.driver}"/>
	                <property name="url" value="${jdbc.url}"/>
	                <property name="username" value="${jdbc.username}"/>
	                <property name="password" value="${jdbc.password}"/>
	            </dataSource>
	        </environment>
			<!--当environment配置了多个环境后,必须要设置dataSource和transactionManager,因为在dtd约束里已经设定好了-->
	        <!--<environment id="test">
	            <transactionManager type=""></transactionManager>
	            <dataSource type=""></dataSource>
	        </environment>-->
	    </environments>

5. transactionManager(了解,一般都是使用Spring的事务)

		type：  JDBC | MANAGED | 自定义
		JDBC：使用了 JDBC 的提交和回滚设置，依赖于从数据源得到的连接来管理事务范围。 JdbcTransactionFactory MANAGED：不提交或回滚一个连接、让容器来管理事务的整个生命周期（比如 JEE应用服务器的上下文）。 
		ManagedTransactionFactory自定义：实现TransactionFactory接口，type=全类名/别名
6. dataSource(了解,一般都是使用Spring加载的数据源)

		type：  UNPOOLED | POOLED | JNDI | 自定义
		UNPOOLED：不使用连接池， UnpooledDataSourceFactory
		POOLED：使用连接池， PooledDataSourceFactory
		JNDI： 在EJB 或应用服务器这类容器中查找指定的数据源
		自定义：实现DataSourceFactory接口，定义数据源的获取方式。

7. 实际开发中我们使用Spring管理数据源，并进行事务控制的配置来覆盖上述配置

## mappers 映射器 ##
1. 用来在mybatis初始化的时候，告诉mybatis需要引入哪些Mapper映射文件
2. mapper逐个注册SQL映射文件
	
		resource : 引入类路径下的文件 
		url :      引入网络路径或者是磁盘路径下的文件
		class :    引入Mapper接口.
					有SQL映射文件 , 要求Mapper接口与 SQL映射文件同名同位置. 
					没有SQL映射文件 , 使用注解在接口的方法上写SQL语句.

		<!--url和class一般都不会用到,这里不作介绍-->
		<mappers>
	      <mapper resource="EmployeeMapper.xml"/>
	    </mappers>
3. 使用批量注册，这种方式要求SQL映射文件名必须和接口名相同并且在同一目录下

		 <mappers>
	        <package name="com.mybatis.dao"/>
	    </mappers>

	- 方法一:
	
	可以把EmployeeMapper.xml放到java文件路径下的\src\main\java\com\mybatis\dao,但在IDEA编辑器里,必须要把maven工程的pom的文件添加配置,否则编译时不会把同目录下的xml文件拷贝出来,配置如下:
		
		<build>
		  <!--idea编译该目录下的XML文件-->
	        <resources>
	            <resource>
	                <directory>src/main/java</directory>
	                <includes>
	                    <include>**/*.xml</include>
	                </includes>
	            </resource>
	        </resources>
		</build>

	 - 方法二:

	在\src\main\resources目录下建立相同的包名\com\mybatis\dao,把EmployeeMapper.xml放进去,这种方式就不需要改maven工程的pom文件,可以把Mapper接口文件和xml文件分离


**注意:全局配置的属性是有执行的先后顺序的,具体可参考点击配置文件里的configuration标签查看**
	
# MyBatis 映射文件 #

## Mybatis映射文件简介 ##

1. MyBatis 的真正强大在于它的映射语句，也是它的魔力所在。由于它的异常强大，映射器的 XML 文件就显得相对简单。如果拿它跟具有相同功能的 JDBC 代码进行对比，你会立即发现省掉了将近 95% 的代码。MyBatis 就是针对 SQL 构建的，并且比普通的方法做的更好。
2. SQL 映射文件有很少的几个顶级元素（按照它们应该被定义的顺序）：

		cache – 给定命名空间的缓存配置。
		cache-ref – 其他命名空间缓存配置的引用。
		resultMap – 是最复杂也是最强大的元素，用来描述如何从数据库结果集中来加载对象。
		parameterMap – 已废弃！老式风格的参数映射。内联参数是首选,这个元素可能在将来被移除，这里不会记录。
		sql – 可被其他语句引用的可重用语句块。
		insert – 映射插入语句
		update – 映射更新语句
		delete – 映射删除语句
		select – 映射查询语

## Mybatis使用insert|update|delete|select完成CRUD ##

### select ###

1. Mapper接口方法

		 public Employee selectEmployeeById(Integer id);

2. Mapper映射文件

		 <select id="selectEmployeeById" resultType="employee">
	        select id,last_name ,email ,gender from tbl_employee where id = #{id}
	    </select>

### insert ###

1. Mapper接口方法

		public void insertEmployee(Employee employee);

2. Mapper映射文件

	    <!-- public void insertEmployee(Employee employee);
		 parameterType:指定参数类型. 可以省略不配置.
		-->
		<insert id="insertEmployee" parameterType="com.mybatis.bean.Employee">
		    insert into tbl_employee (last_name,email,gender) VALUES (#{lastName},#{email},#{gender})
		</insert>

### update ###

1. Mapper接口方法

		public void updateEmployeeById(Employee employee);

2. Mapper映射文件

		 <update id="updateEmployeeById">
	        update tbl_employee set last_name=#{lastName},email=#{email},gender=#{gender} where id =#{id}
	    </update>

### delete ###

1. Mapper接口方法

		public void delEmployeeById(Integer id);

2. Mapper映射文件

		 <delete id="delEmployeeById">
	       delete from tbl_employee  where id =#{id}
	    </delete>

**注意:Mapper调用完接口方法后是没把数据提交到数据库的,成功提交是需要开启自动提交或者手动提交的**

	1. sessionFactory.openSession(true);	//openSession()带布尔类型参数,默认是false,true是自动提交
	2. session.commit();		//手动提交

	private SqlSessionFactory createSessionFactory() throws IOException
    {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void testCRUDMapper() throws IOException
    {
        SqlSessionFactory sessionFactory = this.createSessionFactory();
        /*如果需要自动提交设置为true,否则用手动提交commit()*/
        SqlSession session = sessionFactory.openSession(true);
        EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
       	mapper.delEmployeeById(1003);
		/**手动提交**/
		//session.commit();
    }

在增删改操作里,Mybatis执行可以看到会有返回值

	DEBUG 11-15 14:45:18,317 ==>  Preparing: insert into tbl_employee (last_name,email,gender) VALUES (?,?,?)   (BaseJdbcLogger.java:145) 
	DEBUG 11-15 14:45:18,348 ==> Parameters: 苍井老师(String), chuangjin@gmail.com(String), 1(Integer)  (BaseJdbcLogger.java:145) 
	DEBUG 11-15 14:45:18,367 <==    Updates: 1  (BaseJdbcLogger.java:145) 

只要把原方法改成有返回值就可以获取打印

	public Integer insertEmployee(Employee employee);

也可以改成返回boolean类型的值,会根据如果是1为ture,为0则false

	 public boolean insertEmployee(Employee employee);

## 主键生成方式、获取主键值 ##

### 主键生成方式 ###
1. 支持主键自增，例如MySQL数据库
2. 不支持主键自增，例如Oracle数据库

### 获取主键值 ###
1. 若数据库支持自动生成主键的字段（比如 MySQL 和 SQL Server），则可以设置 useGeneratedKeys=”true”，然后再把 keyProperty 设置到目标属性上。
	
	    <!-- public void insertEmployee(Employee employee);
			 parameterType:指定参数类型. 可以省略不配置.
	
			 useGeneratedKeys:告诉Mybatis使用主键自增的方式
			 keyProperty:  指定用对象的哪个属性保存Mybatis返回的主键值
		-->
		<!--注意:这里keyProperty对应的是Employee bean里的private Integer id ;-->
	    <insert id="insertEmployee" parameterType="com.mybatis.bean.Employee" useGeneratedKeys="true" keyProperty="id">
	        insert into tbl_employee (last_name,email,gender) VALUES (#{lastName},#{email},#{gender})
	    </insert>

执行添加操作:

    //添加
    Employee employee = new Employee(null, "苍井老师", "chuangjin@gmail.com", 1);
    boolean b = mapper.insertEmployee(employee);
    //提交
    session.commit();
    System.out.println(b);
    System.out.println(employee);

	/**
	DEBUG 11-15 15:21:07,096 ==>  Preparing: insert into tbl_employee (last_name,email,gender) VALUES (?,?,?)   (BaseJdbcLogger.java:145) 
	DEBUG 11-15 15:21:07,120 ==> Parameters: 苍井老师(String), chuangjin@gmail.com(String), 1(Integer)  (BaseJdbcLogger.java:145) 
	DEBUG 11-15 15:21:07,135 <==    Updates: 1  (BaseJdbcLogger.java:145) 
	true
	Employee{id=1008, lastName='苍井老师', email='chuangjin@gmail.com', gender=1}

	**/

其获取主键的原理可参考JDBC获取主键值:

		 /*JDBC操作获取新插入数据的主键值:*/
       	Connection con =null;
		/** PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
        throws SQLException;**/
        java.sql.PreparedStatement sql = con.prepareStatement("sql", PreparedStatement.RETURN_GENERATED_KEYS);
        sql.execute();
        sql.getGeneratedKeys();

## 参数传递 ##

### 参数传递的方式 ###

#### 单个普通(基本/包装+String)参数 ####

- 这种情况MyBatis可直接使用这个参数，不需要经过任何处理。
- 取值:#{随便写}

		 <select id="selectEmployeeById" resultType="employee">
			<!--id名可以随便写也可以获取到,改成#{idaaaaa}-->
	        <!--select id,last_name AS lastName ,email ,gender from tbl_employee where id = #{id}-->
	        select id,last_name ,email ,gender from tbl_employee where id = #{id}
	    </select>


#### 多个参数 ####
任意多个参数，都会被MyBatis重新包装成一个Map传入。

- Map的key是param1，param2，或者0，1…，值就是参数的值
- 取值: #{0 1 2 …N / param1  param2  ….. paramN}

	**EmployeeMapper**

		public Employee selectEmployeeByIdAndLastName(Integer id,String lastName);

	**EmployeeMapper.xml**

		 <select id="selectEmployeeByIdAndLastName" resultType="com.mybatis.bean.Employee">
	        select id,last_name,email,gender from tbl_employee where id =#{0} and last_name=#{1}
	    </select>
			或者:
		 <select id="selectEmployeeByIdAndLastName" resultType="com.mybatis.bean.Employee">
	        select id,last_name,email,gender from tbl_employee where id =#{param1} and last_name=#{param2}
	    </select>

	**测试**

	    @Test
	    public void testParameter() throws IOException
	    {
	        SqlSessionFactory sessionFactory = this.createSessionFactory();
	        SqlSession session = sessionFactory.openSession();
	        EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
	        Employee employee = mapper.selectEmployeeByIdAndLastName(1001, "李四");
	        System.out.println(employee);
	    }

	
	**务必务必注意:多参数情况下取值是#{0 1 2 …N / param1  param2  ….. paramN},从0开始或者是param1开始**
	
	使用命名的错误方式:
	
			<select id="selectEmployeeByIdAndLastName" resultType="com.mybatis.bean.Employee">
		        select id,last_name,email,gender from tbl_employee where id =#{id} and last_name=#{lastName}
		    </select>
	
	会报如下异常:
	
			/**提示形参没有匹配到**/
			/**
				org.apache.ibatis.exceptions.PersistenceException: 
				### Error querying database.  Cause: org.apache.ibatis.binding.BindingException: Parameter 'id' not found. Available parameters are [0, 1, param1, param2]
				### Cause: org.apache.ibatis.binding.BindingException: Parameter 'id' not found. Available parameters are [0, 1, param1, param2]
			**/

#### 命名参数 ####

- 为参数使用@Param起一个名字，MyBatis就会将这些参数封装进map中，key就是我们自己指定的名字
- 取值: #{自己指定的名字 /  param1  param2 … paramN}
	
	**EmployeeMapper.selectEmployeeByIdAndLastName()改成以命名方式指定Key**

		public Employee selectEmployeeByIdAndLastName(@Param("id") Integer id, @Param("lastName") String lastName);

	**EmployeeMapper.xml**:现在可以用上面报异常的方式

		<select id="selectEmployeeByIdAndLastName" resultType="com.mybatis.bean.Employee">
	        select id,last_name,email,gender from tbl_employee where id =#{id} and last_name=#{lastName}
	    </select>

	现在不会报异常了,使用以#{paramN}的方式也可以

		<select id="selectEmployeeByIdAndLastName" resultType="com.mybatis.bean.Employee">
	        select id,last_name,email,gender from tbl_employee where id =#{param1} and last_name=#{param2}
	    </select>

	但以${N}的方式是会有异常,可以看到Map的key已经改成以命名方式的

		  <select id="selectEmployeeByIdAndLastName" resultType="com.mybatis.bean.Employee">
	        select id,last_name,email,gender from tbl_employee where id =#{0} and last_name=#{1}
		</select>

		/**
		org.apache.ibatis.exceptions.PersistenceException: 
		### Error querying database.  Cause: org.apache.ibatis.binding.BindingException: Parameter '0' not found. Available parameters are [lastName, id, param1, param2]
		### Cause: org.apache.ibatis.binding.BindingException: Parameter '0' not found. Available parameters are [lastName, id, param1, param2]
		*/

#### POJO ####
- 当这些参数属于我们业务POJO时，我们直接传递POJO
- 取值: #{POJO的属性名}

之前增改操作就是个例子,这里不多作介绍,介绍在多参的情况下传下POJO

举例:假如传入两个Employee对象,以命名方式先绑定其中一个为emp,然后以#{emp.属性}进行取值

#### Map ####

- 我们也可以封装多个参数为map，直接传递
- 取值: #{使用封装Map时自己指定的key}

	**EmployeeMapper**

		public Employee selectEmployeeByMap(Map<String,Object> map);

	**EmployeeMapper.xml**

		<!--注意:这里的命名方式是在Map里指定的key-->
	    <select id="selectEmployeeByMap" resultType="com.mybatis.bean.Employee">
	        select id,last_name,email,gender from tbl_employee where id =#{id} and last_name=#{ln}
	    </select>

	**测试**

		@Test
	    public void testParameter() throws IOException
	    {
	        SqlSessionFactory sessionFactory = this.createSessionFactory();
	        SqlSession session = sessionFactory.openSession();
	        EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
	
	        HashMap<String, Object> map = new HashMap<>();
	        map.put("id",1001);
	        map.put("ln","李四");
	        Employee employee = mapper.selectEmployeeByMap(map);
	        System.out.println(employee);
	    }

#### Collection/Array ####

- 会被MyBatis封装成一个map传入, Collection对应的key是collection,Array对应的key是array. 如果确定是List集合，key还可以是list.
- 取值:  

		Array: #{array}
		Collection(List/Set): #{collection}
		List : #{collection / list}

### 参数传递源码分析 ###

1. 以命名参数为例
2. 源码:前提:  args=[1001,李四]    names={0=id ,1=lastName}

	1. mapper.selectEmployeeByIdAndLastName(1001, "李四");
	2. public Object invoke(Object proxy, Method method, Object[] args) throws Throwable 
	3. public Object execute(SqlSession sqlSession, Object[] args)
	4. public Object convertArgsToSqlCommandParam(Object[] args)
	5. public Object getNamedParams(Object[] args)

		    public Object getNamedParams(Object[] args) {
			    int paramCount = this.names.size();
			    if (args != null && paramCount != 0) {
			        if (!this.hasParamAnnotation && paramCount == 1) {
			            return args[(Integer)this.names.firstKey()];
			        } else {
			            Map<String, Object> param = new ParamMap();
			            int i = 0;
			
			            for(Iterator i$ = this.names.entrySet().iterator(); i$.hasNext(); ++i) {
			                Entry<Integer, String> entry = (Entry)i$.next();
			                param.put(entry.getValue(), args[(Integer)entry.getKey()]);
			                String genericParamName = "param" + String.valueOf(i + 1);
			                if (!this.names.containsValue(genericParamName)) {
			                    param.put(genericParamName, args[(Integer)entry.getKey()]);
			                }
			            }
			
			            return param;
			        }
			    } else {
			        return null;
			    }
			}

最终参数处理完调用熟悉的执行方法,可以看到为何命名参数可以用#{自己指定的名字 /  param1  param2 … paramN}

	result = sqlSession.selectOne(this.command.getName(), param);

![](http://120.77.237.175:9080/photos/mybatis/2.png)

## 参数处理(了解) ##

1. 参数位置支持的属性:

		javaType、jdbcType、mode、numericScale、resultMap、typeHandler、jdbcTypeName、expression

2. 实际上通常被设置的是：可能为空的列名指定 jdbcType ,例如:

		insert into orcl_employee(id,last_name,email,gender) values(employee_seq.nextval,#{lastName, ,jdbcType=NULL },#{email},#{gender})    --Oracle

**上面插入语句是Oracle表达式,只需了解就可以了,主要是说明#{lastName, ,jdbcType=NULL},参数里面是可以设置值,这里意思是lastName值为OTHER时,把值设置为NULL,这里也可以通过settings进行全局的设置**

		jdbcTypeForNull	当没有为参数提供特定的JDBC类型时，为空值指定JDBC类型。 某些驱动需要指定列的JDBC类型，多数情况直接用一般类型即可，比如NULL、VARCHAR 或 OTHER。	JdbcType 常量，常用值：NULL, VARCHAR 或 OTHER。	默认值:OTHER

## 参数的获取方式 ##

1. \#{key}：可取单个普通类型、 POJO类型 、多个参数、 集合类型获取参数的值，预编译到SQL中。安全。 用的是JDBC的PreparedStatement,在SQL里设置占位符,防止注入

		select id,last_name,email,gender from tbl_employee where id =? and last_name=?
2. ${key}：可取单个普通类型、POJO类型、多个参数、集合类型. 


**注意: 取单个普通类型的参数，${}中不能随便写，必须使用 _parameter,parameter 是Mybatis的内置参数. 获取参数的值，拼接到SQL中。有SQL注入问题。 用的是JDBC的Statement**

		/**Mapper SQL**/
	 	select id,last_name,email,gender from tbl_employee where id =${id} and last_name=#{ln}
		
		/**打印SQL**/
		select id,last_name,email,gender from tbl_employee where id =1001 and last_name=?

可以看到通过${}是通过拼接的方式把参数拼接到SQL表达式里,要使用${}只有在SQL表达式里不支持占位符的情况下才可以使用,可以看下面: 

**原则: 能用#{}取值就优先使用#{},#{}解决不了的可以使用${}.**

	  //例如: 原生的JDBC不支持占位符的地方，就可以使用${},下面的column,表名...都是不支持用占位符
	  //Select  column1 ,column2… from 表 where 条件group by   组标识 having  条件 order by 排序字段  desc/asc  limit  x, x

测试:

    @Test
    public void testParameter() throws IOException
    {
        SqlSessionFactory sessionFactory = this.createSessionFactory();
        SqlSession session = sessionFactory.openSession();
        EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);

        HashMap<String, Object> map = new HashMap<>();
        map.put("id",1001);
        map.put("ln","李四");
        map.put("tableName","tbl_employee");
        Employee employee = mapper.selectEmployeeByMap(map);
        System.out.println(employee);
    }

	<!--这种就可以使用${}方式,表名可以通过动态设置进行查询-->
	 <select id="selectEmployeeByMap" resultType="com.mybatis.bean.Employee">
		select id,last_name,email,gender from ${tableName} where id =#{id} and last_name=#{ln}
	</select>

	/**select id,last_name,email,gender from tbl_employee where id =? and last_name=?**/


## select查询的几种情况 ##

1. 查询单行数据返回单个对象

		 public Employee selectEmployeeById(Integer id);

2. 查询多行数据返回对象的集合

	EmployeeMapper
	
		public List<Employee> getAllEmps();
	
	EmployeeMapper.xml
	
		    <!-- public List<Employee> getEmps(); 
	   				注意: resultType: 结果集的封装类型. 不要以为是public List<Employee> getAllEmps()的返回类型
			-->
	    <select id="getAllEmps" resultType="com.mybatis.bean.Employee">
	        select id,last_name,email,gender from tbl_employee
	    </select>
	
	测试:
	
	    @Test
	    public void testSelect() throws IOException
	    {
	        SqlSessionFactory sessionFactory = this.createSessionFactory();
	        SqlSession session = sessionFactory.openSession();
	        EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
	        List<Employee> emps = mapper.getAllEmps();
	        System.out.println(emps);
	    }

		/**[Employee{id=1001, lastName='李四', email='lisi@gmail.com', gender=0}, Employee{id=1004, lastName='三上老师', email='sanshang@gmail.com', gender=1}, Employee{id=1005, lastName='苍井老师', email='chuangjin@gmail.com', gender=1}, Employee{id=1006, lastName='苍井老师', email='chuangjin@gmail.com', gender=1}, Employee{id=1007, lastName='苍井老师', email='chuangjin@gmail.com', gender=1}, Employee{id=1008, lastName='苍井老师', email='chuangjin@gmail.com', gender=1}]**/


3. 查询单行数据返回Map集合
	
	
	EmployeeMapper
	
		    public Map<String,Object> getEmployeeByIdReturnMap(Integer id);

	
	EmployeeMapper.xml
	
	   	 <!-- public Map<String,Object> getEmployeeByIdReturnMap(Integer id);-->
	    <select id="getEmployeeByIdReturnMap" resultType="map">
	        select id,last_name ,email ,gender from tbl_employee where id = #{id}
	    </select>
	
	测试:
	
	    @Test
	    public void testSelect() throws IOException
	    {
	        SqlSessionFactory sessionFactory = this.createSessionFactory();
	        SqlSession session = sessionFactory.openSession();
	        EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
	        Map<String, Object> emps = mapper.getEmployeeByIdReturnMap(1001);
	        System.out.println(emps);
	    }

		/**{gender=0, last_name=李四, id=1001, email=lisi@gmail.com}**/

4. 查询多行数据返回Map集合

	EmployeeMapper
	
	     @MapKey("id")	//这里是指定返回类型Map的key,id是对象的属性id,不是SQL数据库的字段
		public Map<Integer,Employee> getEmployeesReturnMap();

	
	EmployeeMapper.xml
	
	     <!--public Map<Integer,Employee> getEmployeesReturnMap();-->
	    <select id="getEmployeesReturnMap" resultType="com.mybatis.bean.Employee">
	        select id,last_name,email,gender from tbl_employee
	    </select>
	
	测试:
	
	    @Test
	    public void testSelect() throws IOException
	    {
	        SqlSessionFactory sessionFactory = this.createSessionFactory();
	        SqlSession session = sessionFactory.openSession();
	        EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
	        Map<Integer, Employee> emps = mapper.getEmployeesReturnMap();
	        System.out.println(emps);
	    }

		/**{1008=Employee{id=1008, lastName='苍井老师', email='chuangjin@gmail.com', gender=1}, 1001=Employee{id=1001, lastName='李四', email='lisi@gmail.com', gender=0}, 1004=Employee{id=1004, lastName='三上老师', email='sanshang@gmail.com', gender=1}, 1005=Employee{id=1005, lastName='苍井老师', email='chuangjin@gmail.com', gender=1}, 1006=Employee{id=1006, lastName='苍井老师', email='chuangjin@gmail.com', gender=1}, 1007=Employee{id=1007, lastName='苍井老师', email='chuangjin@gmail.com', gender=1}}**/

**特别注意:resultType是需要把取得的数据封装成的类型,不是方法的返回类型,两个是区分的**

## resultType自动映射 ##
1. autoMappingBehavior默认是PARTIAL，开启自动映射的功能。唯一的要求是列名和javaBean属性名一致
2. 如果autoMappingBehavior设置为null则会取消自动映射
3. 数据库字段命名规范，POJO属性符合驼峰命名法，如A_COLUMNaColumn，我们可以开启自动驼峰命名规则映射功能，mapUnderscoreToCamelCase=true

在多表关联时,自动映射无法满足需要,因此需要用下面的自定义映射

## resultMap自定义映射 ##

1. 自定义resultMap，实现高级结果集映射
2. id ：用于完成主键值的映射
3. result ：用于完成普通列的映射
4. association ：一个复杂的类型关联;许多结果将包成这种类型
5. collection ： 复杂类型的集

### id&result ###

![](http://120.77.237.175:9080/photos/mybatis/3.png)

**接口**

	public interface EmployeeMapperResultMap {

	    public Employee getEmployeeById(Integer id);
	}

**Mapper文件**

	<?xml version="1.0" encoding="UTF-8" ?>
	<!DOCTYPE mapper
	        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
	        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
	<mapper namespace="com.mybatis.dao.EmployeeMapperResultMap">
	    <!-- public Employee getEmployeeById();-->
	    <select id="getEmployeeById" resultMap="myEmp">
	        select id,last_name AS lastName ,email ,gender from tbl_employee where id = #{id}
	    </select>

	    <!-- 自定义映射
			type: 最终结果集封装的类型
			<id>: 完成主键列的映射
				column: 指定结果集的列名(查询结果的列名)
				property:指定对象的属性名(对象的Bean属性名)
			<result>:完成普通列的映射
		 -->
	    <!--
	            id      last_name        email              gender
	           1001     李四            lisi@gmail.com         0
	     -->
	    <resultMap id="myEmp" type="com.mybatis.bean.Employee">
	       <id column="id" property="id"/>
	        <result column="last_name" property="lastName"/>
	        <result column="email" property="email"/>
	        <result column="gender" property="gender"/>
	    </resultMap>
	</mapper>

**测试**

    @Test
    public void testResultMap() throws IOException
    {
        SqlSessionFactory sessionFactory = createSessionFactory();
        SqlSession session = sessionFactory.openSession();
        EmployeeMapperResultMap mapper = session.getMapper(EmployeeMapperResultMap.class);
        Employee emp = mapper.getEmployeeById(1001);
        System.out.println(emp);
    }

### association ###
1. POJO中的属性可能会是一个对象,我们可以使用联合查询，并以级联属性的方式封装对象.使用association标签定义对象的封装规则

需求: 查询员工对象， 并且查询员工所在 的部门信息

	**Bean**
	
		public class Department {
		
		    private Integer id;
		    private String departName;
		
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
		
		    @Override
		    public String toString() {
		        return "Department{" +
		                "id=" + id +
		                ", departName='" + departName + '\'' +
		                '}';
		    }
		}
	
	**接口**
	
		 public Employee getEmpAndDeptById(Integer id);
2. 使用级联的方式:

	
	**Mapper.xml**
	
		    <!--
			需求: 查询员工对象， 并且查询员工所在 的部门信息.
			 -->
		    <!--public Employee getEmpAndDeptById(Integer id);-->
		    <select id="getEmpAndDeptById" resultMap="myEmpAndDept">
		        select e.id eid,e.last_name,e.email,e.gender,d.id did,d.dept_name from tbl_employee e, tbl_dept d where e.id = #{id} and d.id = e.d_id
		    </select>
		
		    <!--
		            id           last_name        email              gender       did  dept_name
		           1001        李四              lisi@gmail.com         0         4       开发部
		     -->
		    <resultMap id="myEmpAndDept" type="com.mybatis.bean.Employee">
		        <id column="id" property="id"/>
		        <result column="last_name" property="lastName"/>
		        <result column="email" property="email"/>
		        <result column="gender" property="gender"/>
		        <!--级联-->
		        <result column="did" property="department.id"/>
		        <result column="depart_name" property="department.departName"/>
		    </resultMap>

	**测试**

	    @Test
	    public void testResultMapCascade() throws IOException
	    {
	        SqlSessionFactory sessionFactory = createSessionFactory();
	        SqlSession session = sessionFactory.openSession();
	        EmployeeMapperResultMap mapper = session.getMapper(EmployeeMapperResultMap.class);
	        Employee employee = mapper.getEmpAndDeptById(1001);
	        System.out.println(employee.getDepartment());
	        System.out.println(employee);

			/**
			Department{id=3, departName='开发部'}
			Employee{id=null, lastName='李四', email='lisi@gmail.com', gender=0}	
			**/
	    }
3. Association

	    <resultMap id="myEmpAndDept" type="com.mybatis.bean.Employee">
        <id column="id" property="id"/>
        <result column="last_name" property="lastName"/>
        <result column="email" property="email"/>
        <result column="gender" property="gender"/>
        <!--
	 		association: 完成关联、联合属性的映射
	 			property: 指定联合属性
	 			javaType: 指定联合属性的类型
	 	 -->
		<!--同理,这里的property就是Employee.department的属性,javaType就是Employee.department的类型-->
        <association property="department" javaType="com.mybatis.bean.Department">
            <id column="did" property="id"/>
            <result column="depart_name" property="departName"/>
        </association>
    </resultMap>

### association 分步查询 ###
1. 实际的开发中，对于每个实体类都应该有具体的增删改查方法，也就是DAO层， 因此
对于查询员工信息并且将对应的部门信息也查询出来的需求，就可以通过分步的方式
完成查询。
	1. 先通过员工的id查询员工信息
	2. 再通过查询出来的员工信息中的外键(部门id)查询对应的部门信息. 
	
	**新增接口**

		public interface DepartmentMapperResultMap {

		    public Department getDeptById(Integer id);
		}

	**DepartmentMapperResultMap.xml**

		<?xml version="1.0" encoding="UTF-8" ?>
		<!DOCTYPE mapper
		        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
		        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
		
		<mapper namespace="com.mybatis.dao.DepartmentMapperResultMap">
		    <!--public Department getDeptById(Integer id);-->
		    <select id="getDeptById" resultType="com.mybatis.bean.Department">
		        select id, depart_name from tbl_dept where id = #{id}
		    </select>
		</mapper>

	**EmployeeMapperResultMap.xml**

		    <!--
        association 使用分步查询:
        需求:  查询员工信息并且查询员工所在的部门信息.
              1. 先根据员工的id查询员工信息
              2. 使用外键 d_id查询部门信息
	     -->
	    <!--    public Employee getEmpAndDeptStep(Integer id);-->
	    <select id="getEmpAndDeptStep" resultMap="myDeptAndEmpsStep">
	        select id,last_name,email,gender,d_id from tbl_employee where id =#{id}
	    </select>
	
	    <resultMap id="myDeptAndEmpsStep" type="com.mybatis.bean.Employee">
	        <id column="id" property="id"/>
	        <result column="last_name" property="lastName"/>
	        <result column="email" property="email"/>
	        <result column="gender" property="gender"/>
			<!-- 分步查询 -->
			<!--column:把第一个select语句的查询结果字段赋值给指定的查询-->
	        <association property="department" select="com.mybatis.dao.DepartmentMapperResultMap.getDeptById" column="d_id" />
	    </resultMap>

	**测试**

		@Test
	    public void testResultMapAssociation() throws IOException
	    {
	        SqlSessionFactory sessionFactory = createSessionFactory();
	        SqlSession session = sessionFactory.openSession();
	        EmployeeMapperResultMap mapper = session.getMapper(EmployeeMapperResultMap.class);
	        Employee employee = mapper.getEmpAndDeptStep(1001);
	        System.out.println(employee);
	        System.out.println(employee.getDepartment());

			/**
			DEBUG 11-19 11:14:51,978 ==>  Preparing: select id,last_name,email,gender,d_id from tbl_employee where id =?   (BaseJdbcLogger.java:145) 
			DEBUG 11-19 11:14:52,013 ==> Parameters: 1001(Integer)  (BaseJdbcLogger.java:145) 
			DEBUG 11-19 11:14:52,039 ====>  Preparing: select id, depart_name from tbl_dept where id = ?   (BaseJdbcLogger.java:145) 
			DEBUG 11-19 11:14:52,040 ====> Parameters: 3(Integer)  (BaseJdbcLogger.java:145) 
			DEBUG 11-19 11:14:52,049 <====      Total: 1  (BaseJdbcLogger.java:145) 
			DEBUG 11-19 11:14:52,049 <==      Total: 1  (BaseJdbcLogger.java:145) 
			Employee{id=1001, lastName='李四', email='lisi@gmail.com', gender=0}
			Department{id=3, departName='开发部'}
			**/
			/**可以看到上面执行结果是分两条SQL语句执行**/
	    }

### association 分步查询使用延迟加载 ###
1. 在分步查询的基础上，可以使用延迟加载来提升查询的效率，只需要在全局的
Settings中进行如下的配置

	**mybatis-config.xml**

	    <!-- 2. settings:  包含了很多重要的设置项 -->
	    <settings>
	        <!-- 映射下划线到驼峰命名 ,默认是关闭的-->
	        <setting name="mapUnderscoreToCamelCase" value="true"/>
	        <!-- 开启延迟加载 -->
	        <setting name="lazyLoadingEnabled" value="true"/>
	        <!-- 配置按需加载-->
	        <setting name="aggressiveLazyLoading" value="false"/>
	    </settings>

	**测试**

		@Test
	    public void testResultMapAssociation() throws IOException
	    {
	        SqlSessionFactory sessionFactory = createSessionFactory();
	        SqlSession session = sessionFactory.openSession();
	        EmployeeMapperResultMap mapper = session.getMapper(EmployeeMapperResultMap.class);
	        Employee employee = mapper.getEmpAndDeptStep(1001);
	        System.out.println(employee.getLastName());
	        System.out.println("----------");
	        System.out.println(employee.getDepartment());

			/**
				DEBUG 11-19 11:48:29,488 ==>  Preparing: select id,last_name,email,gender,d_id from tbl_employee where id =?   (BaseJdbcLogger.java:145) 
				DEBUG 11-19 11:48:29,514 ==> Parameters: 1001(Integer)  (BaseJdbcLogger.java:145) 
				DEBUG 11-19 11:48:29,601 <==      Total: 1  (BaseJdbcLogger.java:145) 
				李四
				----------
				DEBUG 11-19 11:48:29,601 ==>  Preparing: select id, depart_name from tbl_dept where id = ?   (BaseJdbcLogger.java:145) 
				DEBUG 11-19 11:48:29,602 ==> Parameters: 3(Integer)  (BaseJdbcLogger.java:145) 
				DEBUG 11-19 11:48:29,611 <==      Total: 1  (BaseJdbcLogger.java:145) 
				Department{id=3, departName='开发部'}
			**/

			/**可以看到上面现在按需要进行加载的,不需要是不进行查询**/
	    }

### collection ###
1. POJO中的属性可能会是一个集合对象,我们可以使用联合查询，并以级联属性的方式封装对象.使用collection标签定义对象的封装规则
2. Collection

	**Department**

		 private List<Employee> emps;

	**DepartmentMapperResultMap**

		public Department getDeptAndEmps(Integer id);

	**DepartmentMapperResultMap.xml**
	
		<!--public Department getDeptAndEmps(Integer id);-->
	    <select id="getDeptAndEmps" resultMap="myDeptAndEmps">
	        select d.id , d.depart_name,e.id eid ,e.last_name,e.email,e.gender from tbl_dept d left join tbl_employee e on d.id = e.d_id where d.id = #{id}
	    </select>
	
	    <resultMap id="myDeptAndEmps" type="com.mybatis.bean.Department">
	        <id column="id" property="id"/>
	        <result column="depart_name" property="departName"/>
	        <!--
				collection: 完成集合类型的联合属性的映射
					property: 指定联合属性
					ofType: 指定集合中元素的类型
			 -->
	        <!--同理,这里的property就是Department.emps属性,ofType就是Department.emps的返回的集合中的类型-->
	        <collection property="emps" ofType="com.mybatis.bean.Employee">
	            <id column="eid" property="id"/>
	            <result column="last_name" property="lastName"/>
	            <result column="email" property="email"/>
	            <result column="gender" property="gender"/>
	        </collection>
	    </resultMap>

	**测试**

		 @Test
	    public void testResultMapCollection() throws IOException
	    {
	        SqlSessionFactory sessionFactory = createSessionFactory();
	        SqlSession session = sessionFactory.openSession();
	        DepartmentMapperResultMap mapper = session.getMapper(DepartmentMapperResultMap.class);
	        Department deptAndEmps = mapper.getDeptAndEmps(3);
	        System.out.println(deptAndEmps);
	        System.out.println(deptAndEmps.getEmps());

			/**
				Department{id=3, departName='开发部'}
				[Employee{id=1001, lastName='李四', email='lisi@gmail.com', gender=0}, Employee{id=1004, lastName='三上老师', email='sanshang@gmail.com', gender=1}, Employee{id=1005, lastName='苍井老师', email='chuangjin@gmail.com', gender=1}]
			**/
	    }

### collection 分步查询 ###
1. 实际的开发中，对于每个实体类都应该有具体的增删改查方法，也就是DAO层， 因此
对于查询部门信息并且将对应的所有的员工信息也查询出来的需求，就可以通过分步的方式完成查询。
	1. 先通过部门的id查询部门信息
	2. 再通过部门id作为员工的外键查询对应的部门信息. 

###  collection 分步查询使用延迟加载 ###

**EmployeeMapperResultMap**

	 public List<Employee> getEmpsByDid(Integer id);

**DepartmentMapperResultMap**

	public Department getDeptAndEmpsStep(Integer id);

**DepartmentMapperResultMap.xml**

    <!-- collection 分步查询:
		  需求:  查询部门信息 并且查询部门下所有的员工信息
		 	   1. 先根据部门的id查询部门的信息
		 	   2. 再根据部门的id作为员工的外键 查询所有的员工信息
	 -->
    <!--public Department getDeptAndEmpsStep(Integer id);-->
    <select id="getDeptAndEmpsStep" resultMap="myDeptAndEmpsStep">
        select id, depart_name from tbl_dept where id = #{id}
    </select>
    <resultMap id="myDeptAndEmpsStep" type="com.mybatis.bean.Department">
        <id column="id" property="id"/>
        <result column="depart_name" property="departName"/>
		<!--column:把第一个select语句的查询结果字段赋值给指定的查询-->
        <collection property="emps" select="com.mybatis.dao.EmployeeMapperResultMap.getEmpsByDid" column="id"/>
    </resultMap>

**EmployeeMapperResultMap.xml**

	<select id="getEmpsByDid" resultType="com.mybatis.bean.Employee">
         select id,last_name AS lastName ,email ,gender from tbl_employee where d_id = #{id}
    </select>

**测试**

    @Test
    public void testResultMapCollectionStep() throws IOException{
        SqlSessionFactory sessionFactory = createSessionFactory();
        SqlSession session = sessionFactory.openSession();
        DepartmentMapperResultMap mapper = session.getMapper(DepartmentMapperResultMap.class);
        Department deptAndEmps = mapper.getDeptAndEmpsStep(3);
        System.out.println(deptAndEmps.getDepartName());
        System.out.println("----------------");
        System.out.println(deptAndEmps.getEmps());

		/**
		DEBUG 11-19 15:04:35,514 ==>  Preparing: select id, depart_name from tbl_dept where id = ?   (BaseJdbcLogger.java:145) 
		DEBUG 11-19 15:04:35,539 ==> Parameters: 3(Integer)  (BaseJdbcLogger.java:145) 
		DEBUG 11-19 15:04:35,633 <==      Total: 1  (BaseJdbcLogger.java:145) 
		开发部
		----------------
		DEBUG 11-19 15:04:35,634 ==>  Preparing: select id,last_name AS lastName ,email ,gender from tbl_employee where d_id = ?   (BaseJdbcLogger.java:145) 
		DEBUG 11-19 15:04:35,634 ==> Parameters: 3(Long)  (BaseJdbcLogger.java:145) 
		DEBUG 11-19 15:04:35,644 <==      Total: 3  (BaseJdbcLogger.java:145) 
		[Employee{id=1001, lastName='李四', email='lisi@gmail.com', gender=0}, Employee{id=1004, lastName='三上老师', email='sanshang@gmail.com', gender=1}, Employee{id=1005, lastName='苍井老师', email='chuangjin@gmail.com', gender=1}]
		**/
    }
		

### 扩展: 分步查询多列值的传递 ###
1. 如果分步查询时，需要传递给调用的查询中多个参数，则需要将多个参数封装成
Map来进行传递，语法如下: {k1=v1, k2=v2....}
2. 在所调用的查询方，取值时就要参考Map的取值方式，需要严格的按照封装map
时所用的key来取值. 


**多个参数传值必须使用MAP进行封装,k=v键值对{k1=v1, k2=v2....}**

		//原单个字段:
		 <resultMap ...
		 	<association property="department" select="com.mybatis.dao.DepartmentMapperResultMap.getDeptById" column="d_id" />
		</resultMap>
		//现MAP封将:
		 <resultMap ...
		 	<association property="department" select="com.mybatis.dao.DepartmentMapperResultMap.getDeptById" column="{did=d_id}" />
		</resultMap>

**取值时必须指定MAP的key值**

		//原单个字段:
		<select id="getDeptById" resultType="com.mybatis.bean.Department">
	        select id, depart_name from tbl_dept where id = #{id}
	    </select>
		//现MAP封将:
		<select id="getDeptById" resultType="com.mybatis.bean.Department">
	        select id, depart_name from tbl_dept where id = #{did}
	    </select>

### 扩展: association 或 collection的 fetchType属性 ###
1. 在<association> 和<collection>标签中都可以设置fetchType，指定本次查询是否要使用延迟加载。默认为 fetchType=”lazy” ,如果本次的查询不想使用延迟加载，则可设置为fetchType=”eager”.
2. fetchType可以灵活的设置查询是否需要使用延迟加载，而不需要因为某个查询不想使用延迟加载将全局的延迟加载设置关闭.

		//当设置了为eager后
	    <resultMap ....
	        <association property="department" select="com.mybatis.dao.DepartmentMapperResultMap.getDeptById" column="{did=d_id}" fetchType="eager"/>
	    </resultMap>

		/**
			DEBUG 11-19 16:51:50,553 ==>  Preparing: select id,last_name,email,gender,d_id from tbl_employee where id =?   (BaseJdbcLogger.java:145) 
			DEBUG 11-19 16:51:50,595 ==> Parameters: 1001(Integer)  (BaseJdbcLogger.java:145) 
			DEBUG 11-19 16:51:50,620 ====>  Preparing: select id, depart_name from tbl_dept where id = ?   (BaseJdbcLogger.java:145) 
			DEBUG 11-19 16:51:50,620 ====> Parameters: 3(Integer)  (BaseJdbcLogger.java:145) 
			DEBUG 11-19 16:51:50,631 <====      Total: 1  (BaseJdbcLogger.java:145) 
			DEBUG 11-19 16:51:50,632 <==      Total: 1  (BaseJdbcLogger.java:145) 
			李四
			----------
			Department{id=3, departName='开发部'}
		**/
		/**可以看到不用延迟加载后,打印一个字段,会执行两次查询**/


### 抽取可重用的SQL片段 ###
1.可以把重用的SQL语句包装起来进行复用

	原:
		 <select id="getEmployeeById" resultMap="myEmp">
	        select id,last_name AS lastName ,email ,gender from tbl_employee where id = #{id}
	    </select>
	现:
	   <select id="getEmployeeById" resultMap="myEmp">
		    <include refid="selectEmployeeSQL"/> where id = #{id}
	    </select>
		<sql id="selectEmployeeSQL">
		    select id,last_name AS lastName ,email ,gender from tbl_employee
		</sql>

# MyBatis 动态SQL #

## MyBatis动态SQL简介 ##

1. 动态 SQL是MyBatis强大特性之一。极大的简化我们拼装SQL的操作
2. 动态 SQL 元素和使用 JSTL 或其他类似基于 XML 的文本处理器相似
3. MyBatis 采用功能强大的基于 OGNL 的表达式来简化操作


		if
		choose (when, otherwise)
		trim (where, set)
		foreach
4. OGNL（ Object Graph Navigation Language ）对象图导航语言，这是一种强大的
表达式语言，通过它可以非常方便的来操作对象属性。 类似于我们的EL，SpEL等

		访问对象属性：		person.name
		调用方法：		    person.getName()
		调用静态属性/方法：	@java.lang.Math@PI	
					        @java.util.UUID@randomUUID()
		调用构造方法：		new com.atguigu.bean.Person(‘admin’).name
		运算符：		     +,-*,/,%
		逻辑运算符：		 in,not in,>,>=,<,<=,==,!=
		注意：xml中特殊符号如”,>,<等这些都需要使用转义字符

## if  where ##
1. If用于完成简单的判断.
2. Where用于解决SQL语句中where关键字以及条件中第一个and或者or的问题 

	**接口**

		public interface EmployeeMapperDynamicSQL {

		    public List<Employee> getEmpsByConditionIfWhere(Employee condition);
		}

	**EmployeeMapperDynamicSQL.xml**

		<?xml version="1.0" encoding="UTF-8" ?>
		<!DOCTYPE mapper
		        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
		        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
		<mapper namespace="com.mybatis.dao.EmployeeMapperDynamicSQL">
		    <!--public List<Employee> getEmpsByConditionIfWhere(Employee condition);-->
		    <select id="getEmpsByConditionIfWhere" resultType="com.mybatis.bean.Employee">
		        select id ,last_name,email,gender from tbl_employee
		          <!-- 在SQL语句中提供WHERE关键字，  并且要解决第一个出现的and 或者是 or的问题 -->
       			 <!-- 相当于where 1=1 -->
		        <where>
					<!--if条件语句,判断id不为Null值-->
		            <if test="id!=null">
		                and id = #{id}
		            </if>
					<!--if条件语句,判断lastName并且不为空,注:&&和""特殊符号需要进行转义才可解析-->
		            <if test="lastName!=null&amp;&amp;lastName!=&quot;&quot;">
		                and last_name = #{lastName}
		            </if>
					<!--if条件语句,判断email不为Null值,并且去掉前后指定字符不为空,注:用and和''就不需要进行转义-->
		            <if test="email!=null and email.trim()!=''">
		                and email = #{email}
		            </if>
					 <!--if条件语句,判断gender等于0或者等于1-->
		            <if test="gender==0 or gender==1">
		                and gender = #{gender}
		            </if>
		        </where>
		    </select>
		</mapper>

	**测试**

	    @Test
	    public void testIf() throws IOException
	    {
	        SqlSessionFactory sessionFactory = createSessionFactory();
	        SqlSession session = sessionFactory.openSession();
	        EmployeeMapperDynamicSQL mapper = session.getMapper(EmployeeMapperDynamicSQL.class);
	        Employee employee = new Employee();
	        employee.setId(1001);
	        employee.setLastName("李四");
	        employee.setEmail("list@gmail.com");
	        employee.setGender(0);
	
	        List<Employee> list = mapper.getEmpsByConditionIfWhere(employee);
	        System.out.println(list);

			/**
				DEBUG 11-20 10:53:59,663 ==>  Preparing: select id ,last_name,email,gender from tbl_employee WHERE id = ? and last_name = ? and email = ? and gender = ?   (BaseJdbcLogger.java:145) 
				DEBUG 11-20 10:53:59,687 ==> Parameters: 1001(Integer), 李四(String), lisi@gmail.com(String), 0(Integer)  (BaseJdbcLogger.java:145) 
				DEBUG 11-20 10:53:59,713 <==      Total: 1  (BaseJdbcLogger.java:145) 
				[Employee{id=1001, lastName='李四', email='lisi@gmail.com', gender=0}]
			**/
	    }

## trim ##
1. Trim 可以在条件判断完的SQL语句前后 添加或者去掉指定的字符

		prefix: 添加前缀
		prefixOverrides: 去掉前缀
		suffix: 添加后缀
		suffixOverrides: 去掉后缀

	**EmployeeMapperDynamicSQL**

		public List<Employee> getEmpsByConditionTrim(Employee condition);

	**EmployeeMapperDynamicSQL.xml**

	    <!--public List<Employee> getEmpsByConditionTrim(Employee condition);-->
	    <select id="getEmpsByConditionTrim" resultType="com.mybatis.bean.Employee">
	        select id ,last_name,email,gender from tbl_employee
	        <!--
				prefix:  添加一个前缀
				prefixOverrides: 覆盖/去掉一个前缀
				suffxi:  添加一个后缀
				suffixOverrides: 覆盖/去掉一个后缀
			 -->
			 <!--假如在SQL语句and或者or不是写在前,可以使用trim进行覆盖去掉-->
	        <!--prefix添加一个前缀where,作用与标签where是一样-->
	        <!--语句后有and|or可以使用suffixOverrides-->
			<!--注意:trim标签是可以相互嵌套的,如果不确定过滤and和or的写法,可以写两层trim分别过滤and和or-->
	        <trim prefix="where" suffixOverrides="and | or">
	            <if test="id!=null">
	                 id = #{id} and
	            </if>
	            <if test="lastName!=null&amp;&amp;lastName!=&quot;&quot;">
	                 last_name = #{lastName} and
	            </if>
	            <if test="email!=null and email.trim()!=''">
	                 email = #{email} and
	            </if>
	            <if test="gender==0 or gender==1">
	                 gender = #{gender} and
	            </if>
	        </trim>
	    </select>

	**测试**

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

			/**
			DEBUG 11-20 11:21:23,420 ==>  Preparing: select id ,last_name,email,gender from tbl_employee where id = ? and last_name = ? and email = ? and gender = ?   (BaseJdbcLogger.java:145) 
			DEBUG 11-20 11:21:23,459 ==> Parameters: 1001(Integer), 李四(String), lisi@gmail.com(String), 0(Integer)  (BaseJdbcLogger.java:145) 
			DEBUG 11-20 11:21:23,478 <==      Total: 1  (BaseJdbcLogger.java:145) 
			[Employee{id=1001, lastName='李四', email='lisi@gmail.com', gender=0}]
			**/
	    }

## set ##
1. set 主要是用于解决修改操作中SQL语句中可能多出逗号的问题

	**EmployeeMapperDynamicSQL**

		public void updateEmpsByConditionSet(Employee condition);

	**EmployeeMapperDynamicSQL.xml**

		 <!--public List<Employee> getEmpsByConditionSet(Employee condition);-->
		<!--set操作主要是过滤掉修改操作中SQL多出的逗号,其功能trim标签也可以完成-->
	    <update id="updateEmpsByConditionSet">
	        update tbl_employee
	    <set>
	        <if test="lastName != null">
	            last_name = #{lastName},
	        </if>
	        <if test="email != null">
	            email = #{email},
	        </if>
	        <if test="gender ==0 or gender ==1">
	            gender = #{gender},
	        </if>
	    </set>
	        where id = #{id}
	    </update>

	**测试**

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
	
			/**
				DEBUG 11-20 11:46:12,813 ==>  Preparing: update tbl_employee SET last_name = ?, email = ?, gender = ? where id = ?   (BaseJdbcLogger.java:145) 
				DEBUG 11-20 11:46:12,838 ==> Parameters: 李三(String), lisi@gmail.com(String), 0(Integer), 1001(Integer)  (BaseJdbcLogger.java:145) 
				DEBUG 11-20 11:46:12,858 <==    Updates: 1  (BaseJdbcLogger.java:145) 
			**/
	    }

## choose(when、otherwise)  ##
1. choose 主要是用于分支判断，类似于java中的switch case,只会满足所有分支中的一个

	**EmployeeMapperDynamicSQL**

		 public List<Employee> getEmpsByConditionChoose(Employee condition);

	**EmployeeMapperDynamicSQL.xml**

	    <!--    public List<Employee> getEmpsByConditionChoose(Employee condition);-->
	    <select id="getEmpsByConditionChoose" resultType="com.mybatis.bean.Employee">
	        select id ,last_name,email,gender from tbl_employee
	        <where>
				<!--当有一个条件满足时就不会再执行下其它的when条件,否则使用默认条件otherwise-->
	            <choose>
	                <when test="id!=null">
	                    id = #{id}
	                </when>
	                <when test="lastName!=null">
	                    last_name=#{lastName}
	                </when>
	                <when test="email!=null">
	                    email=#{email}
	                </when>
	                <otherwise>
	                    gender = 0
	                </otherwise>
	            </choose>
	        </where>
	    </select>

	**测试**

		   @Test
		    public void testChoose() throws IOException
		    {
		        SqlSessionFactory sessionFactory = createSessionFactory();
		        SqlSession session = sessionFactory.openSession(true);
		        EmployeeMapperDynamicSQL mapper = session.getMapper(EmployeeMapperDynamicSQL.class);
		        Employee employee = new Employee();
		        employee.setId(1001);
		        employee.setLastName("李三");
		        employee.setEmail("lisi@gmail.com");
		       employee.setGender(0);
		
		        List<Employee> list = mapper.getEmpsByConditionChoose(employee);
		        System.out.println(list);
				/**
				DEBUG 11-20 12:16:19,279 ==>  Preparing: select id ,last_name,email,gender from tbl_employee WHERE id = ?   (BaseJdbcLogger.java:145) 
				DEBUG 11-20 12:16:19,304 ==> Parameters:   (BaseJdbcLogger.java:145) 
				DEBUG 11-20 12:16:19,329 <==      Total: 1  (BaseJdbcLogger.java:145) 
				[Employee{id=1001, lastName='李三', email='lisi@gmail.com', gender=0}]
				**/
		    }

	

## foreach ##
1. foreach 主要用户循环迭代

		collection: 要迭代的集合
		item: 当前从集合中迭代出的元素
		open: 开始字符
		close:结束字符
		separator: 元素与元素之间的分隔符
		index:
			迭代的是List集合: index表示的当前元素的下标
				迭代的Map集合:  index表示的当前元素的key

	**EmployeeMapperDynamicSQL**

		/*指定命名参数ids*/
   	 	public List<Employee> getEmpsByConditionForeach(@Param("ids") List<Integer> ids);

	**EmployeeMapperDynamicSQL.xml**

	    <!--    public List<Employee> getEmpsByConditionForeach(@Param("ids") List<Integer> ids);-->
	    <select id="getEmpsByConditionForeach" resultType="com.mybatis.bean.Employee">
			<!-- 
			select * from tbl_employee where id in(?,?,?);
			select * from tbl_employee where id = ?  or id = ?  or id = ? ...
		 -->
	        select id ,last_name,email,gender from tbl_employee
			<!--collection:指定命名参数,item:迭代后的元素名,用于下面参数指定,open:迭代开始前的拼接元素,close:迭代结束后的拼接元素,separator:每次迭代后的拼接元素-->
	        <foreach collection="ids" item="id" open="where id in (" close=")" separator=",">
	            #{id}
	        </foreach>
	    </select>

	**测试**
	
		@Test
	    public void testForeach() throws IOException
	    {
	        SqlSessionFactory sessionFactory = createSessionFactory();
	        SqlSession session = sessionFactory.openSession(true);
	        EmployeeMapperDynamicSQL mapper = session.getMapper(EmployeeMapperDynamicSQL.class);
	        List<Integer> ids = new ArrayList<>();
	        ids.add(1001);
	        ids.add(1004);
	        List<Employee> list = mapper.getEmpsByConditionForeach(ids);
	        System.out.println(list);
	
			/**
				DEBUG 11-20 14:34:32,244 ==>  Preparing: select id ,last_name,email,gender from tbl_employee where id in ( ? , ? )   (BaseJdbcLogger.java:145) 
				DEBUG 11-20 14:34:32,272 ==> Parameters: 1001(Integer), 1004(Integer)  (BaseJdbcLogger.java:145) 
				DEBUG 11-20 14:34:32,358 <==      Total: 2  (BaseJdbcLogger.java:145) 
				[Employee{id=1001, lastName='李三', email='lisi@gmail.com', gender=0}, Employee{id=1004, lastName='三上老师', email='sanshang@gmail.com', gender=1}]
			**/
	    }


2. 批量操作:  修改  删除  添加

	**EmployeeMapperDynamicSQL**

		 public void addEmps(@Param("emps") List<Employee> emps);

	**EmployeeMapperDynamicSQL.xml**

		<!--public void addEmps(@Param("emps") List<Employee> emps);-->
	    <!--
			添加:insert into tbl_employee(x,x,x) values(?,?,?),(?,?,?),(?,?,?)
			删除:delete from tbl_employee where id in(?,?,?)
			修改: update tbl_employee set  last_name = #{lastName} ...where id = #{id};
				 update tbl_employee set  last_name = #{lastName} ...where id = #{id};
				 update tbl_employee set  last_name = #{lastName} ...where id = #{id};
			默认情况下， JDBCB不允许将多条SQL通过;拼成一个字符串。
			可以在连接的url后面加上一个参数:  allowMultiQueries=true-->
	    <insert id="addEmps">
	        insert into tbl_employee (last_name , email,gender) values
	        <foreach collection="emps" item="emp" separator=",">
	            (#{emp.lastName},#{emp.email},#{emp.gender})
	        </foreach>
	    </insert>

	**测试**

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

			/**
				DEBUG 11-20 15:58:08,700 ==>  Preparing: insert into tbl_employee (last_name , email,gender) values (?,?,?) , (?,?,?) , (?,?,?)   (BaseJdbcLogger.java:145) 
				DEBUG 11-20 15:58:08,751 ==> Parameters: julia(String), julia@gmail.com(String), 1(Integer), 爱田老师(String), aitian@gmail.com(String), 1(Integer), 吉择老师(String), jizhe@gmail.com(String), 1(Integer)  (BaseJdbcLogger.java:145) 
				DEBUG 11-20 15:58:08,771 <==    Updates: 3  (BaseJdbcLogger.java:145) 
			**/
	    }

## sql  ##
1. sql 标签是用于抽取可重用的sql片段，将相同的，使用频繁的SQL片段抽取出来，单独定义，方便多次引用

		原:
		 <select id="getEmployeeById" resultMap="myEmp">
	        select id,last_name AS lastName ,email ,gender from tbl_employee where id = #{id}
	    </select>

2. 抽取SQL

		现:
			<sql id="selectEmployeeSQL">
			    select id,last_name AS lastName ,email ,gender from tbl_employee
			</sql>
3. 引用SQL	   

		<select id="getEmployeeById" resultMap="myEmp">
		    <include refid="selectEmployeeSQL"/> where id = #{id}
	    </select>