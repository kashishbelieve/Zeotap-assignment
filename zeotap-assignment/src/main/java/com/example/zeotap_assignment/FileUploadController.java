package com.example.zeotap_assignment;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    @PostMapping("/upload-flatfile")
public String uploadFile(@RequestParam("file") MultipartFile file) {
    try (
        InputStreamReader reader = new InputStreamReader(file.getInputStream());
        CSVParser parser = new CSVParser(reader, CSVFormat.Builder.create()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build())
    ) {
        for (CSVRecord record : parser) {
            // Process each record here (e.g., print, store, send to ClickHouse)
            System.out.println(record); // Just printing for now
        }

        return "File uploaded and parsed successfully: " + file.getOriginalFilename();
    } catch (IOException e) {
        e.printStackTrace();
        return "File upload failed: " + e.getMessage();
    }
}
}
