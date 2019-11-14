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

	### Cause: org.apache.ibatis.builder.BuilderException: Error parsing SQL Mapper Configuration. Cause: org.apache.ibatis.type.TypeException: The alias 'Employee' is already mapped to the value 'com.mybatis.bean.sub.Employee'.

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
2. Mapper映射文件

### insert ###

1. Mapper接口方法
2. Mapper映射文件

### update ###

1. Mapper接口方法
2. Mapper映射文件

### delete ###

1. Mapper接口方法
2. Mapper映射文件

## 主键生成方式、获取主键值 ##

### 主键生成方式 ###
1. 支持主键自增，例如MySQL数据库
2. 不支持主键自增，例如Oracle数据库

### 获取主键值 ###
1. 若数据库支持自动生成主键的字段（比如 MySQL 和 SQL Server），则可以设置 useGeneratedKeys=”true”，然后再把 keyProperty 设置到目标属性上。

## 参数传递 ##

### 参数传递的方式 ###

1. 单个普通(基本/包装+String)参数
	这种情况MyBatis可直接使用这个参数，不需要经过任	何处理。
	取值:#{随便写}
2. 多个参数
任意多个参数，都会被MyBatis重新包装成一个Map传入。Map的key是param1，param2，或者0，1…，值就是参数的值
取值: #{0 1 2 …N / param1  param2  ….. paramN}
3. 命名参数
为参数使用@Param起一个名字，MyBatis就会将这些参数封装进map中，key就是我们自己指定的名字
取值: #{自己指定的名字 /  param1  param2 … paramN}
4. POJO
当这些参数属于我们业务POJO时，我们直接传递POJO
取值: #{POJO的属性名}
5. Map
我们也可以封装多个参数为map，直接传递
取值: #{使用封装Map时自己指定的key}
6. Collection/Array
会被MyBatis封装成一个map传入, Collection对应的key是collection,Array对应的key是array. 如果确定是List集合，key还可以是list.
取值:  
	Array: #{array}
	Collection(List/Set): #{collection}
	List : #{collection / list}
