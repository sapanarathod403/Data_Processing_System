package com.dps.model;

import com.dps.constant.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawRecord {
    private Long orderId;

    private String customerName;

    private String amount;

    private String currency;

    private String orderDate;

    private String source;
}
