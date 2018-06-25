package com.tuandai.bigdata.baseproject.datasource;

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

@Configuration
@MapperScan(basePackages = "com.tuandai.bigdata.baseproject.dao.tuandaibm", sqlSessionTemplateRef = "tuandaibmSqlSessionTemplate")
public class TuandaiBmConfig {

@Bean(name = "tuandaibmDataSource")
@ConfigurationProperties(prefix = "spring.datasource.tuandai_bm")
@Primary
public DataSource testDataSource() {
    return DataSourceBuilder.create().build();
}

    @Bean(name = "tuandaibmSqlSessionFactory")
    @Primary
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("tuandaibmDataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mapper/tuandai_bm/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "tuandaibmTransactionManager")
    @Primary
    public DataSourceTransactionManager testTransactionManager(@Qualifier("tuandaibmDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "tuandaibmSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate testSqlSessionTemplate(
            @Qualifier("tuandaibmSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
