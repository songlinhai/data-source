package com.slh.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by songlinhai on 2018/8/13 0013.
 */
@Configuration
//指明了扫描dao层，并且给dao层注入指定的SqlSessionTemplate
@MapperScan(basePackages = "com.slh.userOne", sqlSessionTemplateRef  = "bicapSqlSessionTemplate")
public class BicapDataSourceConfig {

	/**
	 * 创建datasource对象
	 * @return
	 */
	@Bean(name = "bicapDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.bicap")// prefix值必须是application.properteis中对应属性的前缀
	@Primary//说明该数据源为主数据源
	public DataSource bicapDataSource() {
		return DataSourceBuilder.create().build();
	}

	/**
	 * 创建sql工程
	 * @param dataSource
	 * @return
	 * @throws Exception
	 */
	@Bean(name = "bicapSqlSessionFactory")
	@Primary
	public SqlSessionFactory bicapSqlSessionFactory(@Qualifier("bicapDataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		//对应mybatis.type-aliases-package配置
		bean.setTypeAliasesPackage("com.slh.userOne.dao");
		//对应mybatis.mapper-locations配置，如果在mapper层使用注解书写Sql，可以不用这个配置
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/one/*.xml"));
		//开启驼峰映射
		bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
		return bean.getObject();
	}

	/**
	 * 配置事务管理
	 * @param dataSource
	 * @return
	 */
	@Bean(name = "bicapTransactionManager")
	@Primary
	public DataSourceTransactionManager bicapTransactionManager(@Qualifier("bicapDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	/**
	 * sqlSession模版，用于配置自动扫描pojo实体类
	 * @param sqlSessionFactory
	 * @return
	 * @throws Exception
	 */
	@Bean(name = "bicapSqlSessionTemplate")
	@Primary
	public SqlSessionTemplate bicapSqlSessionTemplate(@Qualifier("bicapSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
