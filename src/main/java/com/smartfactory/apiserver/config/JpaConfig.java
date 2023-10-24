package com.smartfactory.apiserver.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(
        basePackages="com.smartfactory.apiserver.domain.database.repository",  //repository를 관리할 패키지 명시
        entityManagerFactoryRef = "entityManagerFactory", //EntityManagerFactory
        transactionManagerRef = "transactionManager") // transactionManager
public class JpaConfig {
    private static final String DEFAULT_NAMING_STRATEGY = "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy";

    @Autowired
    private DataSource datasource;
    

    @Bean(name = "entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        Map<String, String> propertiesHashMap = new HashMap<>();
        propertiesHashMap.put("hibernate.physical_naming_strategy",DEFAULT_NAMING_STRATEGY);

        LocalContainerEntityManagerFactoryBean rep =
                builder.dataSource(datasource)
                        .packages("com.smartfactory.apiserver.domain.database.entity")
                        //domain(Entity)을 관리할 패키지 경로 명시 (domain = DO 파일, Entity = Entity파일)
                        .properties(propertiesHashMap)
                        .build();
        return rep;
    }

    @Primary
    @Bean(name = "transactionManager")
    PlatformTransactionManager transactionManager(
            EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactory(builder).getObject());
    }
}
