package com.dps.loader.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlOrder {
    @XmlElement(name = "order_id")
    private Long orderId;

    @XmlElement(name = "customer_name")
    private String customerName;

    @XmlElement(name = "amount")
    private String amount;

    @XmlElement(name = "currency")
    private String currency;

    @XmlElement(name = "order_date")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate orderDate;
}
