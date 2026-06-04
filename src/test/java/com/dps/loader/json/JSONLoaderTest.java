package com.dps.loader.json;

import com.dps.model.RawRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link JSONLoader}.
 */
public class JSONLoaderTest {
    @Test
    @DisplayName("Should load valid JSON")
    void shouldLoadValidJson(
            @TempDir Path tempDir)
            throws Exception {

        Path file =
                tempDir.resolve("order.json");

        Files.writeString(
                file,
                """
                {
                  "order_id":1001,
                  "customer_name":"John Doe",
                  "amount":"250.75",
                  "currency":"USD",
                  "order_date":"2026-01-01"
                }
                """);

        List<RawRecord> records =
                new JSONLoader().load(file.toFile());

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
                "JSON",
                record.getSource());
    }

    @Test
    @DisplayName("Should handle null order date")
    void shouldHandleNullOrderDate(
            @TempDir Path tempDir)
            throws Exception {

        Path file =
                tempDir.resolve("null-date.json");

        Files.writeString(
                file,
                """
                {
                  "order_id":1002,
                  "customer_name":"John Doe",
                  "amount":"100",
                  "currency":"USD",
                  "order_date":null
                }
                """);

        List<RawRecord> records =
                new JSONLoader().load(file.toFile());

        assertEquals(1, records.size());

        assertNull(
                records.get(0).getOrderDate());
    }

    @Test
    @DisplayName("Should return empty list for invalid JSON")
    void shouldReturnEmptyListForInvalidJson(
            @TempDir Path tempDir)
            throws Exception {

        Path file =
                tempDir.resolve("invalid.json");

        Files.writeString(
                file,
                "{invalid}");

        List<RawRecord> records =
                new JSONLoader().load(file.toFile());

        assertTrue(records.isEmpty());
    }
}
