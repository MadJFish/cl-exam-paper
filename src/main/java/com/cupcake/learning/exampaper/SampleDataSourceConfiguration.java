package com.cupcake.learning.exampaper;

import com.cupcake.learning.exampaper.sample.ItemCategory;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.cupcake.learning.exampaper.sample",
        entityManagerFactoryRef = "sampleEntityManager",
        transactionManagerRef = "sampleTransactionManager")
public class SampleDataSourceConfiguration {
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties sampleDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.configuration")
    public DataSource sampleDataSource() {
        return sampleDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    // sampleEntityManager bean
    @Bean
    public LocalContainerEntityManagerFactoryBean sampleEntityManager(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(sampleDataSource())
                .packages(ItemCategory.class)
                .build();
    }

    // sampleTransactionManager bean
    @Bean
    public PlatformTransactionManager sampleTransactionManager(final @Qualifier("sampleEntityManager") LocalContainerEntityManagerFactoryBean sampleEntityManager) {
        return new JpaTransactionManager(sampleEntityManager.getObject());
    }
}