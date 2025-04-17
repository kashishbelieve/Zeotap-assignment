package com.example.zeotap_assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;

@Service
public class ClickHouseService {

    @Autowired
    private ClickHouseConfig clickHouseConfig;

    public boolean testConnection(String host, int port, String database) {
        try (Connection conn = clickHouseConfig.connect(host, port, database)) {
            return !conn.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
