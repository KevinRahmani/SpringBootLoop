package springLoop.config;

import jakarta.persistence.EntityManagerFactory;
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
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "springLoop.product.repository",
        entityManagerFactoryRef = "productEntityManagerFactory",
        transactionManagerRef = "productTransactionManager")
public class ProductConfig {

    @Primary
    @Bean(name="productDataSource")
    @ConfigurationProperties(prefix="spring.datasource.stockloop")
    public DataSource productDataSource() {
        return DataSourceBuilder.create().build();
    }
    @Primary
    @Bean(name = "productEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean productEntityManager(EntityManagerFactoryBuilder builder, @Qualifier("productDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("springLoop.product.model")
                .persistenceUnit("product")
                .build();
    }
    @Primary
    @Bean(name = "productTransactionManager")
    public PlatformTransactionManager productTransactionManager(@Qualifier("productEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}