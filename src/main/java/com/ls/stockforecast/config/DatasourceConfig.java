package com.ls.stockforecast.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * @author yaochenglong
 * @date 2018/9/12
 */
@Configuration
@PropertySource(value="classpath:/application.yml")
public class DatasourceConfig {

    @Primary
    @Bean("dataSource")
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource primaryDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean("realtimeDataSource")
    @ConfigurationProperties(prefix="spring.realtime-datasource")
    public DataSource realtimeDataSource(){
        return DataSourceBuilder.create().build();
    }

}
