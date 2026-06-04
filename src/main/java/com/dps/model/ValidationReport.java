package com.dps.model;

import com.dps.constant.Severity;
import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * Represents the validation result for a single record.
 *
 * <p>
 * Contains all validation errors and warnings generated during
 * record validation. Validation processing is non-failing,
 * meaning all rules are executed and all violations are collected.
 * </p>
 *
 * Example:
 *
 * <pre>
 * ValidationReport report = ValidationReport.builder()
 *         .recordId("1004")
 *         .errors(errors)
 *         .build();
 * </pre>
 *
 * @author DPS
 * @version 1.0
 */
@Data
@Builder
public class ValidationReport {
    /**
     * Unique identifier of the processed record.
     */
    private String recordId;

    /**
     * Collection of validation violations.
     */
    private List<ValidationError> errors;

    /**
     * Returns total validation errors.
     *
     * @return number of ERROR severity violations
     */
    public long getTotalErrors() {

        return getSafeErrors().stream()
                .filter(error -> Severity.ERROR.equals(error.getSeverity()))
                .count();
    }

    /**
     * Returns total validation warnings.
     *
     * @return number of WARN severity violations
     */
    public long getTotalWarnings() {

        return getSafeErrors().stream()
                .filter(error -> Severity.WARN.equals(error.getSeverity()))
                .count();
    }

    /**
     * Returns total informational messages.
     *
     * @return number of INFO severity violations
     */
    public long getTotalInfos() {

        return getSafeErrors().stream()
                .filter(error -> Severity.INFO.equals(error.getSeverity()))
                .count();
    }

    /**
     * Returns total validation messages.
     *
     * @return total number of validation entries
     */
    public int getTotalMessages() {

        return getSafeErrors().size();
    }

    /**
     * Checks whether report contains any errors.
     *
     * @return true if at least one ERROR exists
     */
    public boolean hasErrors() {

        return getTotalErrors() > 0;
    }

    /**
     * Returns safe list to avoid NullPointerException.
     *
     * @return validation error list
     */
    private List<ValidationError> getSafeErrors() {

        return errors == null
                ? Collections.emptyList()
                : errors;
    }
}
