package com.dps.loader.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class JsonOrder {
    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("customer_name")
    private String customerName;

    private String amount;

    private String currency;

    @JsonProperty("order_date")
    private LocalDate orderDate;
}
