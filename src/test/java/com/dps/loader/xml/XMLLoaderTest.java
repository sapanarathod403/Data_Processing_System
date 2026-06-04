package com.dps.loader.xml;

import com.dps.model.RawRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link XMLLoader}.
 */
public class XMLLoaderTest {
    @Test
    @DisplayName("Should load valid XML")
    void shouldLoadValidXml(
            @TempDir Path tempDir)
            throws Exception {

        Path file =
                tempDir.resolve("order.xml");

        Files.writeString(
                file,
                """
                <order>
                    <order_id>1001</order_id>
                    <customer_name>John Doe</customer_name>
                    <amount>250.75</amount>
                    <currency>USD</currency>
                    <order_date>2026-01-01</order_date>
                </order>
                """);

        List<RawRecord> records =
                new XMLLoader().load(file.toFile());

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
                "XML",
                record.getSource());
    }

    @Test
    @DisplayName("Should load XML with null order date")
    void shouldLoadXmlWithNullOrderDate(
            @TempDir Path tempDir)
            throws Exception {

        Path file =
                tempDir.resolve("null-date.xml");

        Files.writeString(
                file,
                """
                <order>
                    <order_id>1002</order_id>
                    <customer_name>John Doe</customer_name>
                    <amount>100</amount>
                    <currency>USD</currency>
                </order>
                """);

        List<RawRecord> records =
                new XMLLoader().load(file.toFile());

        assertEquals(1, records.size());

        assertNull(
                records.get(0).getOrderDate());
    }

    @Test
    @DisplayName("Should reject invalid XML")
    void shouldRejectInvalidXml(
            @TempDir Path tempDir)
            throws Exception {

        Path file =
                tempDir.resolve("invalid.xml");

        Files.writeString(
                file,
                "<order><invalid>");

        List<RawRecord> records =
                new XMLLoader().load(file.toFile());

        assertTrue(records.isEmpty());
    }

    @Test
    @DisplayName("Should reject missing customer")
    void shouldRejectMissingCustomer(
            @TempDir Path tempDir)
            throws Exception {

        Path file =
                tempDir.resolve("missing-customer.xml");

        Files.writeString(
                file,
                """
                <order>
                    <order_id>1001</order_id>
                    <amount>250.75</amount>
                    <currency>USD</currency>
                </order>
                """);

        List<RawRecord> records =
                new XMLLoader().load(file.toFile());

        assertTrue(records.isEmpty());
    }
    @Test
    void shouldRejectBlankCustomerName(
            @TempDir Path tempDir)
            throws Exception {

        Path file =
                tempDir.resolve("blank.xml");

        Files.writeString(
                file,
                """
                <order>
                    <order_id>1</order_id>
                    <customer_name> </customer_name>
                    <amount>100</amount>
                    <currency>USD</currency>
                </order>
                """);

        assertTrue(
                new XMLLoader()
                        .load(file.toFile())
                        .isEmpty());
    }
    @Test
    void shouldRejectMissingAmount(
            @TempDir Path tempDir)
            throws Exception {

        Path file =
                tempDir.resolve("missingAmount.xml");

        Files.writeString(
                file,
                """
                <order>
                    <order_id>1</order_id>
                    <customer_name>John</customer_name>
                    <currency>USD</currency>
                </order>
                """);

        assertTrue(
                new XMLLoader()
                        .load(file.toFile())
                        .isEmpty());
    }
    @Test
    void shouldRejectMissingCustomerAndAmount(
            @TempDir Path tempDir)
            throws Exception {

        Path file =
                tempDir.resolve("allMissing.xml");

        Files.writeString(
                file,
                """
                <order>
                    <order_id>1</order_id>
                </order>
                """);

        assertTrue(
                new XMLLoader()
                        .load(file.toFile())
                        .isEmpty());
    }
}
