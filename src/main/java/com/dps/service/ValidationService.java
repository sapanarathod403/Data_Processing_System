package com.dps.service;

import com.dps.config.ValidationConfiguration;
import com.dps.model.RawRecord;
import com.dps.model.ValidationReport;
import com.dps.validation.ValidationEngine;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

/**
 * Service responsible for validating incoming records.
 *
 * <p>
 * Delegates validation processing to the configured
 * {@link ValidationEngine}.
 * </p>
 *
 * @author DPS
 * @version 1.0
 */
@Service
@Slf4j
public class ValidationService {
    /**
     * Validation engine containing all configured rules.
     */
    private final ValidationEngine<RawRecord> validationEngine;

    /**
     * Initializes validation engine configuration.
     */
    public ValidationService() {

        log.info("Initializing ValidationService");

        this.validationEngine =
                ValidationConfiguration
                        .buildRawRecordValidator();

        log.info(
                "Validation engine initialized with {} rule(s)",
                validationEngine.getRuleCount());
    }

    /**
     * Validates a record and returns a validation report.
     *
     * @param recordId unique record identifier
     * @param record record to validate
     * @return validation report
     */
    public ValidationReport validate(
            final String recordId,
            final RawRecord record) {

        log.debug(
                "Validating record with id={}",
                recordId);

        return validationEngine.validate(
                recordId,
                record);
    }
}
