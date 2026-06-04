package com.dps.validation;

import com.dps.constant.Severity;
import com.dps.model.ValidationError;
import com.dps.model.ValidationReport;
import com.dps.rules.ValidationRule;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Central validation engine responsible for executing
 * configured validation rules against a target record.
 *
 * <p>
 * The engine follows a non-failing validation strategy:
 * all registered rules are executed regardless of failures
 * in previous rules.
 * </p>
 *
 * <p>
 * Validation exceptions are captured and converted into
 * validation errors to prevent interruption of processing.
 * </p>
 *
 * <p>
 * Example:
 * </p>
 *
 * <pre>
 * ValidationEngine&lt;RawRecord&gt; engine =
 *         new ValidationEngine&lt;&gt;();
 *
 * engine.addRule(
 *         new NullCheckRule&lt;&gt;(
 *                 "customerName",
 *                 RawRecord::getCustomerName));
 *
 * ValidationReport report =
 *         engine.validate("1001", record);
 * </pre>
 *
 * @param <T> type of record being validated
 *
 * @author DPS
 * @version 1.0
 */
@Slf4j
public class ValidationEngine<T> {
    /**
     * Registered validation rules.
     */
    private final List<ValidationRule<T>> rules =
            new ArrayList<>();

    /**
     * Registers a validation rule.
     *
     * @param rule validation rule to register
     * @return current engine instance
     */
    public ValidationEngine<T> addRule(
            final ValidationRule<T> rule) {

        Objects.requireNonNull(
                rule,
                "Validation rule cannot be null");

        rules.add(rule);

        log.debug(
                "Validation rule registered: {}",
                rule.getClass().getSimpleName());

        return this;
    }

    /**
     * Executes all configured validation rules against
     * the supplied record.
     *
     * <p>
     * Validation continues even if a rule throws an exception.
     * Such exceptions are captured and added to the report.
     * </p>
     *
     * @param recordId unique record identifier
     * @param record record to validate
     *
     * @return validation report containing all violations
     */
    public ValidationReport validate(
            final String recordId,
            final T record) {

        log.info(
                "Starting validation for recordId={}",
                recordId);

        List<ValidationError> errors =
                new ArrayList<>();

        for (ValidationRule<T> rule : rules) {

            try {

                log.debug(
                        "Executing validation rule: {}",
                        rule.getClass().getSimpleName());

                List<ValidationError> ruleErrors =
                        rule.validate(record);

                if (ruleErrors != null) {

                    errors.addAll(ruleErrors);

                    log.debug(
                            "Rule {} produced {} validation issue(s)",
                            rule.getClass().getSimpleName(),
                            ruleErrors.size());
                }

            } catch (Exception exception) {

                log.error(
                        "Unexpected exception while executing rule {}",
                        rule.getClass().getSimpleName(),
                        exception);

                errors.add(
                        ValidationError.builder()
                                .field("SYSTEM")
                                .errorCode("VALIDATION_EXCEPTION")
                                .severity(Severity.ERROR)
                                .message(
                                        "Validation rule execution failed")
                                .remediationHint(
                                        exception.getMessage())
                                .build()
                );
            }
        }

        ValidationReport report =
                ValidationReport.builder()
                        .recordId(recordId)
                        .errors(errors)
                        .build();

        log.info(
                "Validation completed for recordId={} | totalMessages={} | errors={} | warnings={}",
                recordId,
                report.getTotalMessages(),
                report.getTotalErrors(),
                report.getTotalWarnings());

        return report;
    }

    /**
     * Returns number of registered rules.
     *
     * @return total rule count
     */
    public int getRuleCount() {

        return rules.size();
    }

    /**
     * Removes all registered rules.
     */
    public void clearRules() {

        log.info(
                "Clearing {} validation rule(s)",
                rules.size());

        rules.clear();
    }
}
