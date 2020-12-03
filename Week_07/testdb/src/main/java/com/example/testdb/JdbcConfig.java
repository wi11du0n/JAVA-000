package com.example.testdb;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

@EnableTransactionManagement
@Configuration
@PropertySource(value = "classpath:jdbc.properties", ignoreResourceNotFound = false, encoding = "UTF-8")
public class JdbcConfig implements TransactionManagementConfigurer {

    @Value("${datasource.username}")
    private String userName;
    @Value("${datasource.password}")
    private String password;
    @Value("${datasource.url}")
    private String url;

    // 从库配置
    @Value("${datasource.slave.username}")
    private String slaveUserName;
    @Value("${datasource.slave.password}")
    private String slavePassword;
    @Value("${datasource.slave.url}")
    private String slaveUrl;


    ////////////////=====配置好两个数据源：
    @Primary
    @Bean
    public DataSource masterDataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser(userName);
        dataSource.setPassword(password);
        dataSource.setURL(url);
        return dataSource;
    }

    @Bean
    public DataSource slaveDataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser(slaveUserName);
        dataSource.setPassword(slavePassword);
        dataSource.setURL(slaveUrl);
        return dataSource;
    }

    // 手动配置好两个JdbcTemplate  分别用于操作
    @Primary
    @Bean
    public JdbcTemplate masterJdbcTemplate() {
        return new JdbcTemplate(masterDataSource());
    }

    @Bean
    public JdbcTemplate slaveJdbcTemplate() {
        return new JdbcTemplate(slaveDataSource());
    }

    // 事务管理器自己具体配置（如果是真的主从模式，从库可以不用事务管理器  只需要配置主库的即可）
    @Primary
    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(masterDataSource());
        dataSourceTransactionManager.setEnforceReadOnly(true); // 让事务管理器进行只读事务层面上的优化  建议开启
        return dataSourceTransactionManager;
    }

    // 指定注解使用的事务管理器
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return transactionManager();
    }
}
