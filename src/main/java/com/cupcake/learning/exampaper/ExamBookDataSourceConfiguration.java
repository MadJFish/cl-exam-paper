package com.cupcake.learning.exampaper;

import com.cupcake.learning.exampaper.auth.model.User;
import com.cupcake.learning.exampaper.exambook.model.entity.Book;
import com.cupcake.learning.exampaper.exambook.model.entity.Exam;
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
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.cupcake.learning.exampaper.exambook.repository",
        entityManagerFactoryRef = "examBookEntityManager",
        transactionManagerRef = "examBookTransactionManager")
public class ExamBookDataSourceConfiguration {
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties examBookDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.configuration")
    public DataSource examBookDataSource() {
        return examBookDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    // examBookEntityManager bean
    @Bean
    public LocalContainerEntityManagerFactoryBean examBookEntityManager(EntityManagerFactoryBuilder builder) {
        var properties = new HashMap<String, Object>();
        properties.put("generate-ddl", true);
        properties.put("hibernate.ddl-auto", "create");

        return builder
                .dataSource(examBookDataSource())
                .packages(Book.class, Exam.class, User.class)
                .build();
    }

    // examBookTransactionManager bean
    @Bean
    public PlatformTransactionManager examBookTransactionManager(final @Qualifier("examBookEntityManager") LocalContainerEntityManagerFactoryBean examBookEntityManager) {
        return new JpaTransactionManager(examBookEntityManager.getObject());
    }
}