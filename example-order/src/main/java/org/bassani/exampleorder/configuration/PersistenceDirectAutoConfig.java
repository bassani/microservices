package org.bassani.exampleorder.configuration;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = {"org.bassani.exampleorder.repository"},
        entityManagerFactoryRef = "directEntityManagerFactory",
        transactionManagerRef = "directTransactionManager"
)
@NoArgsConstructor
public class PersistenceDirectAutoConfig {

    @Primary
    @Bean(name = "directDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource directDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "directEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean directEntityManagerFactory(EntityManagerFactoryBuilder entityManagerFactoryBuilder,
                                                                             @Qualifier("directDataSource")  DataSource dataSource){
        return  entityManagerFactoryBuilder
                .dataSource(dataSource)
                .packages("org.bassani.examplemodellib.domain.entity.direct")
                .persistenceUnit("directDataSource")
                .build();
    }

    @Bean(name = "directTransactionManager")
    public PlatformTransactionManager directTransactionManager(
            @Qualifier("directEntityManagerFactory") EntityManagerFactory directEntityManagerFactory) {
        return new JpaTransactionManager(directEntityManagerFactory);
    }

}
