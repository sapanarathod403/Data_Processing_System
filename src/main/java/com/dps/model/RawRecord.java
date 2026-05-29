package com.dps.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class RawRecord {
    private Long orderId;

    private String customerName;

    private BigDecimal amount;

    private String currency;

    private LocalDate orderDate;

    private String source;
}
