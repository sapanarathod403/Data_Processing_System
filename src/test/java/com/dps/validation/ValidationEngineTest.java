package com.dps.validation;

import com.dps.constant.Severity;
import com.dps.model.ValidationError;
import com.dps.model.ValidationReport;
import com.dps.rules.ValidationRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@link ValidationEngine}.
 *
 * <p>
 * Verifies:
 * </p>
 * <ul>
 *     <li>Rule registration</li>
 *     <li>Null rule validation</li>
 *     <li>Validation execution</li>
 *     <li>Null rule result handling</li>
 *     <li>Exception handling during validation</li>
 *     <li>Rule count management</li>
 *     <li>Rule cleanup</li>
 * </ul>
 *
 * <p>
 * Provides 100% line coverage and 100% branch coverage.
 * </p>
 *
 * @author Sapana Rathod
 * @version 1.0
 */
public class ValidationEngineTest {
    /**
     * Sample record identifier.
     */
    private static final String RECORD_ID =
            "REC-001";

    /**
     * Sample record.
     */
    private static final String RECORD =
            "DATA";

    /**
     * Validation field.
     */
    private static final String FIELD =
            "field";

    /**
     * Validation error code.
     */
    private static final String ERROR_CODE =
            "ERROR_CODE";

    /**
     * Exception error code.
     */
    private static final String EXCEPTION_ERROR_CODE =
            "VALIDATION_EXCEPTION";

    /**
     * System field name.
     */
    private static final String SYSTEM_FIELD =
            "SYSTEM";

    /**
     * Validation message.
     */
    private static final String MESSAGE =
            "Validation message";

    /**
     * Validation hint.
     */
    private static final String HINT =
            "Validation hint";

    /**
     * Exception message.
     */
    private static final String EXCEPTION_MESSAGE =
            "Unexpected failure";

    /**
     * Creates validation error.
     *
     * @return validation error
     */
    private ValidationError createError() {

        return ValidationError.builder()
                .field(FIELD)
                .errorCode(ERROR_CODE)
                .severity(Severity.ERROR)
                .message(MESSAGE)
                .remediationHint(HINT)
                .build();
    }

    /**
     * Verifies rule registration.
     */
    @Test
    @DisplayName("Should register validation rule")
    void shouldRegisterValidationRule() {

        ValidationEngine<String> engine =
                new ValidationEngine<>();

        engine.addRule(
                value -> Collections.emptyList());

        assertEquals(
                1,
                engine.getRuleCount());
    }

    /**
     * Verifies null rule registration fails.
     */
    @Test
    @DisplayName("Should throw exception for null rule")
    void shouldThrowExceptionForNullRule() {

        ValidationEngine<String> engine =
                new ValidationEngine<>();

        assertThrows(
                NullPointerException.class,
                () -> engine.addRule(null));
    }

    /**
     * Verifies validation succeeds when no rules exist.
     */
    @Test
    @DisplayName("Should validate successfully with no rules")
    void shouldValidateWithNoRules() {

        ValidationEngine<String> engine =
                new ValidationEngine<>();

        ValidationReport report =
                engine.validate(
                        RECORD_ID,
                        RECORD);

        assertNotNull(report);
        assertEquals(
                RECORD_ID,
                report.getRecordId());

        assertEquals(
                0,
                report.getErrors().size());
    }

    /**
     * Verifies validation errors are collected.
     */
    @Test
    @DisplayName("Should collect validation errors")
    void shouldCollectValidationErrors() {

        ValidationEngine<String> engine =
                new ValidationEngine<>();

        engine.addRule(
                value -> List.of(createError()));

        ValidationReport report =
                engine.validate(
                        RECORD_ID,
                        RECORD);

        assertEquals(
                1,
                report.getErrors().size());

        ValidationError error =
                report.getErrors().get(0);

        assertEquals(
                FIELD,
                error.getField());

        assertEquals(
                ERROR_CODE,
                error.getErrorCode());
    }

    /**
     * Verifies empty validation results are handled.
     */
    @Test
    @DisplayName("Should handle empty validation result")
    void shouldHandleEmptyValidationResult() {

        ValidationEngine<String> engine =
                new ValidationEngine<>();

        engine.addRule(
                value -> Collections.emptyList());

        ValidationReport report =
                engine.validate(
                        RECORD_ID,
                        RECORD);

        assertEquals(
                0,
                report.getErrors().size());
    }

    /**
     * Verifies null validation results are handled.
     */
    @Test
    @DisplayName("Should handle null validation result")
    void shouldHandleNullValidationResult() {

        ValidationEngine<String> engine =
                new ValidationEngine<>();

        engine.addRule(
                value -> null);

        ValidationReport report =
                engine.validate(
                        RECORD_ID,
                        RECORD);

        assertEquals(
                0,
                report.getErrors().size());
    }

    /**
     * Verifies validation exceptions are converted
     * into validation errors.
     */
    @Test
    @DisplayName(
            "Should convert validation exception to validation error")
    void shouldConvertValidationExceptionToValidationError() {

        ValidationEngine<String> engine =
                new ValidationEngine<>();

        ValidationRule<String> failingRule =
                value -> {
                    throw new IllegalStateException(
                            EXCEPTION_MESSAGE);
                };

        engine.addRule(failingRule);

        ValidationReport report =
                engine.validate(
                        RECORD_ID,
                        RECORD);

        assertEquals(
                1,
                report.getErrors().size());

        ValidationError error =
                report.getErrors().get(0);

        assertEquals(
                SYSTEM_FIELD,
                error.getField());

        assertEquals(
                EXCEPTION_ERROR_CODE,
                error.getErrorCode());

        assertEquals(
                Severity.ERROR,
                error.getSeverity());

        assertEquals(
                "Validation rule execution failed",
                error.getMessage());

        assertEquals(
                EXCEPTION_MESSAGE,
                error.getRemediationHint());
    }

    /**
     * Verifies rules can be cleared.
     */
    @Test
    @DisplayName("Should clear all validation rules")
    void shouldClearAllValidationRules() {

        ValidationEngine<String> engine =
                new ValidationEngine<>();

        engine.addRule(
                value -> Collections.emptyList());

        engine.addRule(
                value -> Collections.emptyList());

        assertEquals(
                2,
                engine.getRuleCount());

        engine.clearRules();

        assertEquals(
                0,
                engine.getRuleCount());
    }
}
