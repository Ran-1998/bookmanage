package com.book.config;

import java.sql.Connection;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Before;
import org.junit.Test;

import com.book.mapper.BookMapper;
import com.book.mapper.CategoryMapper;

/**
 * @author Ran
 * @date 2020年2月29日
 */
public class MybatisConfig {
	protected  SqlSessionFactory sqlSessionFactory;

	@Before
	public void init() {
		// 1.构建数据源对象
		PooledDataSource dataSource = new PooledDataSource();
		dataSource.setDriver("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql:///bookmanage?serverTimezone=GMT");
		dataSource.setUsername("root");
		dataSource.setPassword("123456");

		// 2.创建事务管理工厂
		JdbcTransactionFactory transactionFactory = new JdbcTransactionFactory();

		// 3.创建一个环境对象
		Environment environment = new Environment("development", transactionFactory, dataSource);

		// 4.创建配置对象
		Configuration configuration = new Configuration(environment);
		
		configuration.addMapper(CategoryMapper.class);
		configuration.addMapper(BookMapper.class);

		// 5.创建sqlSessionFactory对象

		sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

	}

	@Test
	public void testConnection() {
		Connection conn = sqlSessionFactory.openSession().getConnection();
		System.out.println(conn);
	}

}
