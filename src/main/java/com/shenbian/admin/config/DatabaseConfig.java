package com.shenbian.admin.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

//@Configuration
//@EnableJpaRepositories("org.enquete")
//@EnableTransactionManagement
public class DatabaseConfig {

    @Value("${spring.datasource.driverClassName}")
    private String driver;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;

    @Value("${spring.jpa.hibernate.dialect}")
    private String dialect;

    @Value("${spring.entity.packageToScan}")
    private String packageToScan;

    @Value("${spring.jpa.database}")
    private String db;

    @Bean
    public DataSource dataSource() throws Exception {
        //        DriverManagerDataSource ret = new DriverManagerDataSource();
        //        ret.setDriverClassName(driver);
        //        ret.setUsername(username);
        //        ret.setPassword(password);
        //        ret.setUrl(dataSource);
        //
        //        return ret;
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(driver);
        dataSource.setJdbcUrl(url);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        //  连接池中链接耗尽的时候c3p0一次同时获取的连接数
        dataSource.setAcquireIncrement(10);
        //  每60秒检查连接池中的空闲链接
        dataSource.setIdleConnectionTestPeriod(60);
        //  连接池中保留的最大连接数
        dataSource.setMaxPoolSize(100);
        //  连接池中保留的最小连接数
        dataSource.setMinPoolSize(10);

        //  连接池的初始化数目 ,介于上二者之间
        dataSource.setInitialPoolSize(11);
        //用以控制数据源内加载的PreparedStatements数量。
        //但由于预缓存的statements属于单个connection而不是整个连接池所以设置这个参数需要考虑到多方面的因数.
        // 如果maxStatements与maxStatementsPerConnection均为0, 则缓存被关闭。Default:0
        dataSource.setMaxStatements(50);

        //  最大空闲时间 60s
        dataSource.setMaxIdleTime(600);

        return dataSource;
    }

    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory(EntityManagerFactory emf) {
        HibernateJpaSessionFactoryBean factory = new HibernateJpaSessionFactoryBean();
        factory.setEntityManagerFactory(emf);
        return factory;
    }

    /**
     * @param dataSource
     * @param jpaVendorAdapter
     * @return
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                                       JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
        lef.setDataSource(dataSource);
        lef.setJpaVendorAdapter(jpaVendorAdapter);


        lef.setPackagesToScan(new String[]{packageToScan});
        return lef;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setDatabase(Database.valueOf(db));

        return hibernateJpaVendorAdapter;
    }

}
