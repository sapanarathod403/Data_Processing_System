package com.dps.loader.json;

import com.dps.loader.DataLoader;
import com.dps.model.RawRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class JSONLoader implements DataLoader {
    private final ObjectMapper objectMapper;

    public JSONLoader() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public List<RawRecord> load(File file) {

        try {

            JsonOrder order =
                    objectMapper.readValue(file, JsonOrder.class);

            RawRecord record = RawRecord.builder()
                    .orderId(order.getOrderId())
                    .customerName(order.getCustomerName())
                    .amount(order.getAmount())
                    .currency(order.getCurrency())
                    .orderDate(
                            order.getOrderDate() != null
                                    ? order.getOrderDate().toString()
                                    : null)
                    .source("JSON")
                    .build();

            log.info("Loaded Record: {}", record);

            return Collections.singletonList(record);

        } catch (Exception ex) {

            log.error(
                    "Failed to load JSON file {}",
                    file.getName(),
                    ex
            );

            return Collections.emptyList();
        }
    }
}
