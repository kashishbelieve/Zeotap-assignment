package com.example.zeotap_assignment;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClickHouseController {

    @Autowired
    private ClickHouseService clickHouseService;

    @GetMapping("/connect-clickhouse")
    public ResponseEntity<String> connectToClickHouse(@RequestBody ClickHouseRequest request) {
        boolean connected = clickHouseService.testConnection(
            request.getHost(),
            request.getPort(),
            request.getDatabase()
        );

        if (connected) {
            return ResponseEntity.ok("✅ Successfully connected to ClickHouse database: " + request.getDatabase());
        } else {
            return ResponseEntity.status(500).body("❌ Failed to connect to ClickHouse.");
        }
    }

    // New controller to handle CSV upload
    @PostMapping("/upload-csv")
    public ResponseEntity<String> uploadCSV(@RequestParam("file") MultipartFile file) {
        // Hardcoded ClickHouse connection details
        String host = "localhost";
        int port = 8123;
        String database = "default";

        try {
            // Parse the CSV file
            InputStreamReader reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
            List<CSVRecord> records = parser.getRecords();

            if (records.isEmpty()) {
                return ResponseEntity.status(400).body("❌ The CSV file is empty.");
            }

            // Extract column names from the first record (header)
            List<String> headers = parser.getHeaderNames();

            // Create dynamic CREATE TABLE query based on headers
            StringBuilder createTableSQL = new StringBuilder("DROP TABLE IF EXISTS test_table; ");
            createTableSQL.append("CREATE TABLE test_table (");

            // Assuming all columns are of String type for simplicity, adjust if needed
            for (String header : headers) {
                createTableSQL.append(header).append(" String, ");
            }

            // Remove last comma and add closing parenthesis
            createTableSQL.delete(createTableSQL.length() - 2, createTableSQL.length());
            createTableSQL.append(") ENGINE = MergeTree() ORDER BY " + headers.get(0) + ";");

            // Create an instance of ClickHouseConfig for this request
            ClickHouseConfig clickHouseConfig = new ClickHouseConfig();
            Connection conn = clickHouseConfig.connect(host, port, database);  // Use the connect method from ClickHouseConfig
            Statement stmt = conn.createStatement();
            stmt.execute(createTableSQL.toString()); // Drop and create table

            // Insert CSV data into ClickHouse table
            String insertQuery = "INSERT INTO test_table (" + String.join(", ", headers) + ") VALUES ";
            StringBuilder values = new StringBuilder();

            for (CSVRecord record : records) {
                values.append("(");
                for (String header : headers) {
                    values.append("'").append(record.get(header).replace("'", "''")).append("', ");  // Escape single quotes
                }
                // Remove last comma and add closing parenthesis
                values.delete(values.length() - 2, values.length());
                values.append("), ");
            }

            // Remove last comma
            if (values.length() > 0) {
                values.deleteCharAt(values.length() - 2);
            }

            stmt.executeUpdate(insertQuery + values.toString());
            conn.close();

            return ResponseEntity.ok("✅ CSV file uploaded and data inserted into ClickHouse table.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Failed to upload CSV: " + e.getMessage());
        }
    }
}

