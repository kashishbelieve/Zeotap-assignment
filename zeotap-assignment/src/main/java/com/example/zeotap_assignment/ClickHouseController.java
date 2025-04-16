package com.example.zeotap_assignment;

import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ClickHouseController {

    @Autowired
    private ClickHouseConfig clickHouseConfig;

    @PostMapping("/connect-clickhouse")
    public ResponseEntity<String> connectClickHouse(@RequestBody ClickHouseRequest request) {
        try (Connection conn = clickHouseConfig.connect(
                request.getHost(),
                request.getPort(),
                request.getDatabase())) {
            return ResponseEntity.ok("✅ Connected to ClickHouse successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("❌ Failed to connect: " + e.getMessage());
        }
    }
}
