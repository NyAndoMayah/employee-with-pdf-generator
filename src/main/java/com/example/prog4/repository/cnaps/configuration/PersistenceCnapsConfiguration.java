package com.example.prog4.repository.cnaps.configuration;

import lombok.AllArgsConstructor;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@PropertySource({"classpath:application.properties"})
@EnableJpaRepositories(
        basePackages = "com.example.prog4.repository.cnaps",
        entityManagerFactoryRef = "cnapsEntityManager",
        transactionManagerRef = "cnapsTransactionManager")
@AllArgsConstructor
public class PersistenceCnapsConfiguration {
    private Environment env;

    @Bean(name = "cnapsDataSource")
    public DataSource cnapsDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(env.getProperty("spring.secondary.datasource.jdbcUrl"));
        dataSource.setUsername(env.getProperty("spring.secondary.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.secondary.datasource.password"));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean cnapsEntityManager() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(cnapsDataSource());
        em.setPackagesToScan("com.example.prog4.repository.cnaps");

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.physical_naming_strategy", CamelCaseToUnderscoresNamingStrategy.class.getName());
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    public PlatformTransactionManager cnapsTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(cnapsEntityManager().getObject());
        return transactionManager;
    }

    @Bean
    public Flyway cnapsFlywayDataSource() {
        Flyway flyway = Flyway.configure()
                .dataSource(cnapsDataSource())
                .locations("classpath:db/migration/cnaps")
                .load();
        flyway.migrate();
        return flyway;
    }
}