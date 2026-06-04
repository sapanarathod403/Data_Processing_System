package com.dps.loader.csv;

import com.dps.model.RawRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link CSVLoader}.
 */
public class CSVLoaderTest {
    private static final String HEADER =
            "orderId,customerName,amount,currency,orderDate";

    private static final String VALID_ROW =
            "1001,John Doe,250.75,USD,2026-01-01";

    @Test
    @DisplayName("Should load valid CSV record")
    void shouldLoadValidCsvRecord(
            @TempDir Path tempDir)
            throws Exception {

        Path file =
                tempDir.resolve("input.csv");

        Files.writeString(
                file,
                HEADER
                        + System.lineSeparator()
                        + VALID_ROW);

        List<RawRecord> records =
                new CSVLoader().load(file.toFile());

        assertEquals(1, records.size());

        RawRecord record =
                records.get(0);

        assertEquals(
                Long.valueOf(1001L),
                record.getOrderId());

        assertEquals(
                "John Doe",
                record.getCustomerName());

        assertEquals(
                "250.75",
                record.getAmount());

        assertEquals(
                "USD",
                record.getCurrency());

        assertEquals(
                "2026-01-01",
                record.getOrderDate());

        assertEquals(
                "CSV",
                record.getSource());
    }

    @Test
    @DisplayName("Should skip invalid rows")
    void shouldSkipInvalidRows(
            @TempDir Path tempDir)
            throws Exception {

        Path file =
                tempDir.resolve("invalid.csv");

        Files.writeString(
                file,
                HEADER
                        + System.lineSeparator()
                        + "1001,,ABC,USD,INVALID");

        List<RawRecord> records =
                new CSVLoader().load(file.toFile());

        assertTrue(records.isEmpty());
    }

    @Test
    @DisplayName("Should load only valid rows")
    void shouldLoadOnlyValidRows(
            @TempDir Path tempDir)
            throws Exception {

        Path file =
                tempDir.resolve("mixed.csv");

        Files.writeString(
                file,
                HEADER
                        + System.lineSeparator()
                        + VALID_ROW
                        + System.lineSeparator()
                        + "1002,,ABC,USD,INVALID");

        List<RawRecord> records =
                new CSVLoader().load(file.toFile());

        assertEquals(1, records.size());
    }
}
