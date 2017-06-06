package com.testspring.configuration.test.config;

import net.ttddyy.dsproxy.listener.ChainListener;
import net.ttddyy.dsproxy.listener.DataSourceQueryCountListener;
import net.ttddyy.dsproxy.listener.logging.CommonsLogLevel;
import net.ttddyy.dsproxy.listener.logging.CommonsQueryLoggingListener;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@SuppressWarnings("Duplicates")
@Configuration
@EnableConfigurationProperties(JpaProperties.class)
@EnableTransactionManagement
public class DBConfigurationTest {


    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    @Autowired
    private JpaProperties jpaProperties;



    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("rs.saga.javashowcase.playground.datasourceproxy.model");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        Properties prop = new Properties();
        prop.putAll(jpaProperties.getHibernateProperties(dataSource));
        em.setJpaProperties(prop);
        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Autowired
    private DataSource dataSource;

    @Bean
    public DataSource poolDataSource() {
        return DataSourceBuilder.create().url(url).username(username).password(password).driverClassName(driverClassName).build();
    }

    @Bean
    @Primary
    public DataSource dataSource() throws SQLException {
        ChainListener chainListener = new ChainListener();
        CommonsQueryLoggingListener commonsQueryLoggingListener = new CommonsQueryLoggingListener();
        commonsQueryLoggingListener.setLogLevel(CommonsLogLevel.INFO);
        chainListener.getListeners().add(commonsQueryLoggingListener);
        DataSourceQueryCountListener dataSourceQueryCountListener = new DataSourceQueryCountListener();
        chainListener.getListeners().add(dataSourceQueryCountListener);

        return ProxyDataSourceBuilder
                .create(poolDataSource())
                .listener(chainListener)
                .build();
    }

}
