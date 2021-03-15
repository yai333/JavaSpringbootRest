// package com.example.awesomeprject;

// import javax.sql.DataSource;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.boot.jdbc.DataSourceBuilder;

// @Configuration
// public class DataSourceConfig {
    
//     @Bean
//     public DataSource getDataSource() {
//         DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//         dataSourceBuilder.url(System.getenv("DATASOURCE_URL"));
//         dataSourceBuilder.username(System.getenv("DATASOURCE_USERNAME"));
//         dataSourceBuilder.password(System.getenv("DATASOURCE_PASSWORD"));
//         return dataSourceBuilder.build();
//     }
// }