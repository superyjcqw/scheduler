package com.gk.scheduler.base;

import com.gk.scheduler.constants.ApplicationConfig;
import com.gk.scheduler.util.BeanManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@Configuration
@EnableWebMvc
public class WebConfigurerAdapter implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(messageConverter());
    }

    @Bean
    public MessageConverter messageConverter() {
        return new MessageConverter();
    }


    @Bean(initMethod = "init", destroyMethod = "destroy")
    public SystemInitConfig initSystemConfig(){
        return new SystemInitConfig();
    }

    @Bean(initMethod = "init")
    public ApplicationConfig initApplicationConfig(){
        return new ApplicationConfig();
    }

    @Bean
    public BeanManager beanManage() {
        return new BeanManager();
    }

    @Bean(name = "dataSourceScheduler")
    @ConfigurationProperties(prefix="spring.datasource.db2")
    public DataSource dataSource2() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dataSourceQuartzAdmin")
    @ConfigurationProperties(prefix="spring.datasource.db1")
    public DataSource dataSource1() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "jdbcTemplateQuartzAdmin")
    public JdbcTemplate primaryJdbcTemplate(
            @Qualifier("dataSourceQuartzAdmin") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "jdbcTemplateScheduler")
    public JdbcTemplate secondaryJdbcTemplate(
            @Qualifier("dataSourceScheduler") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name="quartzScheduler")
    public SchedulerFactoryBean schedulerFactoryBean(@Qualifier("dataSourceScheduler") DataSource dataSource) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setQuartzProperties(quartzProperties());
        factory.setStartupDelay(0);
        return factory;
    }

    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/application.properties"));
        //在quartz.properties中的属性被读取并注入后再初始化对象
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }
}