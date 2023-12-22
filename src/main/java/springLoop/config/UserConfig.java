package springLoop.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "springLoop.user.repository",
        entityManagerFactoryRef = "userEntityManagerFactory",
        transactionManagerRef = "userTransactionManager")
public class UserConfig {

    @Bean(name = "userDataSource")
    @ConfigurationProperties(prefix="spring.datasource.userloop")
    public DataSource userDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name="userEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean userEntityManager(EntityManagerFactoryBuilder builder, @Qualifier("userDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("springLoop.user.model")
                .persistenceUnit("user")
                .build();
    }

    @Bean
    public PlatformTransactionManager userTransactionManager(@Qualifier("userEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
