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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by songlinhai on 2018/8/13 0013.
 */
@Configuration
//指明了扫描dao层，并且给dao层注入指定的SqlSessionTemplate
@MapperScan(basePackages = "com.slh.userTwo", sqlSessionTemplateRef  = "trainSqlSessionTemplate")
public class TrainDataSourceConfig {
	/**
	 * 创建datasource对象
	 * @return
	 */
	@Bean(name = "trainDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.train")// prefix值必须是application.properteis中对应属性的前缀
	public DataSource trainDataSource() {
		return DataSourceBuilder.create().build();
	}

	/**
	 * 创建sql工程
	 * @param dataSource
	 * @return
	 * @throws Exception
	 */
	@Bean(name = "trainSqlSessionFactory")
	public SqlSessionFactory trainSqlSessionFactory(@Qualifier("trainDataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		//对应mybatis.type-aliases-package配置
		bean.setTypeAliasesPackage("com.slh.userTwo.dao");
		//对应mybatis.mapper-locations配置，如果在mapper层使用注解书写Sql，可以不用这个配置
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/two/*.xml"));
		//开启驼峰映射
		bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
		return bean.getObject();
	}

	/**
	 * 配置事务管理
	 * @param dataSource
	 * @return
	 */
	@Bean(name = "trainTransactionManager")
	public DataSourceTransactionManager trainTransactionManager(@Qualifier("trainDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	/**
	 * sqlSession模版，用于配置自动扫描pojo实体类
	 * @param sqlSessionFactory
	 * @return
	 * @throws Exception
	 */
	@Bean(name = "trainSqlSessionTemplate")
	public SqlSessionTemplate trainSqlSessionTemplate(@Qualifier("trainSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
