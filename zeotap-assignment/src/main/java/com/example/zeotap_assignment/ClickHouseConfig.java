// package com.example.zeotap_assignment;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.jdbc.datasource.DriverManagerDataSource;

// import javax.sql.DataSource;

// @Configuration
// public class ClickHouseConfig {

//     @Bean
//     public DataSource dataSource() {
//         DriverManagerDataSource dataSource = new DriverManagerDataSource();
//         dataSource.setDriverClassName("ru.yandex.clickhouse.ClickHouseDriver");
//         dataSource.setUrl("jdbc:clickhouse://<host>:<port>/<database>");
//         dataSource.setUsername("<username>");
//         dataSource.setPassword("<password>");
//         return dataSource;
//     }
// }
package com.example.zeotap_assignment;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ClickHouseConfig {

    // @Autowired
    // private ClickHouseSettings settings;

    public Connection connect(String host, int port, String database) throws SQLException {
        String url = "jdbc:clickhouse://" + host + ":" + port + "/" + database;
        return DriverManager.getConnection(url);
    }
    
}
