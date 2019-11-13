<a href="http://120.77.237.175:9080/photos/mybatis">Mybatis</a>

# MyBatis HelloWorld #

## 开发环境的准备 ##

1. 导入MyBatis框架的jar包、Mysql驱动包、log4j的jar包

2. 导入log4j 的配置文件

## 创建测试表 ##

## 创建javaBean ##

## 创建MyBatis的全局配置文件 ##

1. 参考MyBatis的官网手册

## 创建Mybatis的sql映射文件 ## 

## 测试 ##

1. 参考MyBatis的官方手册

## Mapper接口开发MyBatis HelloWorld ##

1. 编写Mapper接口

2. 完成两个绑定
	1. Mapper接口与Mapper映射文件的绑定在Mppper映射文件中的<mapper>标签中的namespace中必须指定Mapper接口的全类名
	2. Mapper映射文件中的增删改查标签的id必须指定成Mapper接口中的方法名. 
3. 获取Mapper接口的代理实现类对象

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

## properties属性 ##

1. 可外部配置且可动态替换的，既可以在典型的 Java 属性文件中配置，亦可通过 properties 元素的子元素来配置


2. 然而properties的作用并不单单是这样，你可以创建一个资源文件，名为jdbc.properties的文件,将四个连接字符串的数据在资源文件中通过键值 对(key=value)的方式放置，不要任何符号，一条占一行

3. 在environment元素的dataSource元素中为其动态设置

## settings设置 ##

1. 这是 MyBatis 中极为重要的调整设置，它们会改变 MyBatis 的运行时行为
2. 包含如下的setting设置

## typeAliases 别名处理 ##
1. 类型别名是为 Java 类型设置一个短的名字，可以方便我们引用某个类
2. 类很多的情况下，可以批量设置别名这个包下的每一个类创建一个默认的别名，就是简单类名小写
3. MyBatis已经取好的别名

## environments 环境配置 ##

1. MyBatis可以配置多种环境，比如开发、测试和生产环境需要有不同的配置
2. 每种环境使用一个environment标签进行配置并指定唯一标识符
3. 可以通过environments标签中的default属性指定一个环境的标识符来快速的切换环境
4. 
		environment-指定具体环境
		id：指定当前环境的唯一标识
		transactionManager、和dataSource都必须有

5. transactionManager

		type：  JDBC | MANAGED | 自定义
		JDBC：使用了 JDBC 的提交和回滚设置，依赖于从数据源得到的连接来管理事务范围。 JdbcTransactionFactory MANAGED：不提交或回滚一个连接、让容器来管理事务的整个生命周期（比如 JEE应用服务器的上下文）。 
		ManagedTransactionFactory自定义：实现TransactionFactory接口，type=全类名/别名
6. dataSource

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
3. 使用批量注册，这种方式要求SQL映射文件名必须和接口名相同并且在同一目录下

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
