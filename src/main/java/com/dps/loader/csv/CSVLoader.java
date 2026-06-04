package com.dps.loader.csv;

import com.dps.loader.DataLoader;
import com.dps.model.RawRecord;
import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component

public class CSVLoader implements DataLoader {
    @Override
    public List<RawRecord> load(File file) {

        List<RawRecord> records = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(file))) {

            String[] row;
            int rowNumber = 0;

            while ((row = reader.readNext()) != null) {

                rowNumber++;

                if (rowNumber == 1) {
                    continue; // Header
                }

                try {

                    validateRow(row);

                    RawRecord record = RawRecord.builder()
                            .orderId(Long.parseLong(row[0]))
                            .customerName(row[1])
                            .amount(row[2])
                            .currency(row[3])
                            .orderDate(row[4])
                            .source("CSV")
                            .build();

                    records.add(record);

                } catch (Exception ex) {

                    log.warn(
                            "Row {} skipped — {}",
                            rowNumber,
                            ex.getMessage()
                    );
                }
            }

        } catch (Exception ex) {

            log.error("Failed to load CSV file {}", file.getName(), ex);
        }

        return records;
    }

    private void validateRow(String[] row) {

        List<String> errors = new ArrayList<>();

        if (row[1] == null || row[1].trim().isEmpty()) {
            errors.add("missing customerName");
        }

        try {
            new BigDecimal(row[2]);
        } catch (Exception e) {
            errors.add("field 'amount' could not be parsed as decimal");
        }

        try {
            LocalDate.parse(row[4]);
        } catch (Exception e) {
            errors.add("field 'orderDate' contains invalid date");
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(
                    String.join("; ", errors)
            );
        }
    }
}
