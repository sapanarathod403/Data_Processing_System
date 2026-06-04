package com.dps.config;

import com.dps.model.RawRecord;
import com.dps.rules.DateFormatRule;
import com.dps.rules.NullCheckRule;
import com.dps.rules.RangeRule;
import com.dps.rules.StringLengthRule;
import com.dps.validation.ValidationEngine;

import java.math.BigDecimal;

/**
 * Configures validation rules for RawRecord.
 *
 * <p>
 * Central location for registering all validation rules.
 * </p>
 *
 * @author DPS
 * @version 1.0
 */

public class ValidationConfiguration {
    /**
     * Private constructor.
     */
    private ValidationConfiguration() {
    }

    /**
     * Creates and configures validation engine.
     *
     * @return configured validation engine
     */
    public static ValidationEngine<RawRecord>
    buildRawRecordValidator() {

        ValidationEngine<RawRecord> engine =
                new ValidationEngine<>();

        /*
         * Mandatory fields
         */
        engine.addRule(
                new NullCheckRule<>(
                        "customerName",
                        RawRecord::getCustomerName));

        engine.addRule(
                new NullCheckRule<>(
                        "amount",
                        RawRecord::getAmount));

        engine.addRule(
                new NullCheckRule<>(
                        "orderDate",
                        RawRecord::getOrderDate));

        /*
         * Length validation
         */
        engine.addRule(
                new StringLengthRule<>(
                        "customerName",
                        RawRecord::getCustomerName,
                        2,
                        100));

        /*
         * Amount range validation
         */
        engine.addRule(
                new RangeRule<>(
                        "amount",
                        record -> {
                            try {
                                return new BigDecimal(record.getAmount());
                            } catch (Exception ex) {
                                return null;
                            }
                        },
                        BigDecimal.valueOf(0.01),
                        BigDecimal.valueOf(999999)));

        /*
         * Date format validation
         */
        engine.addRule(
                new DateFormatRule<>(
                        "orderDate",
                        RawRecord::getOrderDate,
                        "yyyy-MM-dd"));


        return engine;
    }

}
