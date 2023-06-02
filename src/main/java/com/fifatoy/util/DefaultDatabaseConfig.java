package com.fifatoy.util;

import javax.sql.DataSource;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan(value = "com.main.fifatoy", annotationClass = Mapper.class, sqlSessionFactoryRef = "defaultSqlSessionFactory")
@EnableTransactionManagement
public class DefaultDatabaseConfig {
    private static final Logger logger = LoggerFactory.getLogger(DefaultDatabaseConfig.class);

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "defaultDataSource", destroyMethod = "close")
    @ConfigurationProperties("spring.datasource.configuration")
    public DataSource defaultDataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    /*
     * SqlSessionFactory 설정 MyBatis에 관련된 SqlSessionFactory 객체를 반환하는 빈을 선언
     */
    @Primary
    @Bean(name = "defaultSqlSessionFactory")
    public SqlSessionFactory defaultSqlSessionFactory(@Qualifier("defaultDataSource") DataSource defaultDataSource)
            throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(defaultDataSource);
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml")); // MyBatis
                                                                                                   // 매퍼
                                                                                                   // 파일의
                                                                                                   // 위치를
                                                                                                   // 설정
        sqlSessionFactoryBean.getObject().getConfiguration().setJdbcTypeForNull(JdbcType.FLOAT);
        sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);

        logger.info("####################################");
        logger.info("#  defaultSqlSessionFactory        #");
        logger.info("####################################");

        return sqlSessionFactoryBean.getObject();
    }

    /*
     * SqlSessionTemplate 설정 SqlSessionTemplate은 SqlSession을 구현하고 코드에서
     * SqlSession를 대체하는 역할 Thead Safe하게 작성되었기 때문에 여러 매퍼에서 공유
     */
    @Primary
    @Bean(name = "defaultSqlSessionTemplate")
    public SqlSessionTemplate defaultSqlSessionTemplate(SqlSessionFactory defaultSqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(defaultSqlSessionFactory);
    }

    /*
     * DataSourceTransactionManager 설정 트랜잭션 매니저를 반환하는 빈을 선언
     */
    @Bean(name = "defaultTransactionManager")
    @Primary
    public DataSourceTransactionManager defaultTransactionManager(
            @Qualifier("defaultDataSource") DataSource defaultDataSource) throws Exception {
        return new DataSourceTransactionManager(defaultDataSource);
    }

}
