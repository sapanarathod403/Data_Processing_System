package com.dps.loader.xml;

import com.dps.loader.DataLoader;
import com.dps.model.RawRecord;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class XMLLoader implements DataLoader {
    @Override
    public List<RawRecord> load(File file) {

        try {

            JAXBContext context =
                    JAXBContext.newInstance(XmlOrder.class);

            Unmarshaller unmarshaller =
                    context.createUnmarshaller();

            XmlOrder order =
                    (XmlOrder) unmarshaller.unmarshal(file);

            validate(order);

            RawRecord record = RawRecord.builder()
                    .orderId(order.getOrderId())
                    .customerName(order.getCustomerName())
                    .amount(order.getAmount())
                    .currency(order.getCurrency())
                    .orderDate(
                            order.getOrderDate() != null
                                    ? order.getOrderDate().toString()
                                    : null)
                    .source("XML")
                    .build();

            log.info("Loaded Record: {}", record);

            return Collections.singletonList(record);

        } catch (Exception ex) {

            log.error(
                    "Failed to load XML file {}",
                    file.getName(),
                    ex
            );

            return Collections.emptyList();
        }
    }

    private void validate(XmlOrder order) {

        StringBuilder errors = new StringBuilder();

        if (order.getCustomerName() == null
                || order.getCustomerName().isBlank()) {
            errors.append("missing customerName");
        }

        if (order.getAmount() == null) {

            if (!errors.isEmpty()) {
                errors.append("; ");
            }

            errors.append("missing amount");
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(errors.toString());
        }
    }
}
