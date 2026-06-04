package com.dps.rules;

import com.dps.constant.Severity;
import com.dps.model.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for {@link StringLengthRule}.
 *
 * <p>
 * Verifies:
 * </p>
 * <ul>
 *     <li>Null value handling</li>
 *     <li>Minimum length validation</li>
 *     <li>Maximum length validation</li>
 *     <li>Below minimum length validation</li>
 *     <li>Above maximum length validation</li>
 *     <li>Valid length validation</li>
 * </ul>
 *
 * <p>
 * Provides 100% line coverage and 100% branch coverage.
 * </p>
 *
 * @author Sapana Rathod
 * @version 1.0
 */
public class StringLengthRuleTest {
    /**
     * Field name used for validation.
     */
    private static final String FIELD_NAME = "customerName";

    /**
     * Minimum allowed length.
     */
    private static final int MIN_LENGTH = 3;

    /**
     * Maximum allowed length.
     */
    private static final int MAX_LENGTH = 10;

    /**
     * Expected error code.
     */
    private static final String ERROR_CODE =
            "INVALID_LENGTH";

    /**
     * Expected remediation hint.
     */
    private static final String REMEDIATION_HINT =
            "Adjust field length";

    /**
     * Value shorter than minimum length.
     */
    private static final String SHORT_VALUE = "AB";

    /**
     * Value longer than maximum length.
     */
    private static final String LONG_VALUE =
            "ABCDEFGHIJK";

    /**
     * Value exactly at minimum length.
     */
    private static final String MIN_BOUNDARY_VALUE =
            "ABC";

    /**
     * Value exactly at maximum length.
     */
    private static final String MAX_BOUNDARY_VALUE =
            "ABCDEFGHIJ";

    /**
     * Value within valid range.
     */
    private static final String VALID_VALUE =
            "Customer";

    /**
     * Creates rule instance.
     *
     * @return configured rule
     */
    private StringLengthRule<String> createRule() {

        return new StringLengthRule<>(
                FIELD_NAME,
                value -> value,
                MIN_LENGTH,
                MAX_LENGTH);
    }

    /**
     * Verifies rule creation.
     */
    @Test
    @DisplayName("Should create rule successfully")
    void shouldCreateRuleSuccessfully() {

        StringLengthRule<String> rule =
                createRule();

        assertNotNull(rule);
    }

    /**
     * Verifies null values are ignored.
     */
    @Test
    @DisplayName(
            "Should return empty list when value is null")
    void shouldReturnEmptyListWhenValueIsNull() {

        List<ValidationError> result =
                createRule().validate(null);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Verifies value exactly at minimum length passes.
     */
    @Test
    @DisplayName(
            "Should pass when value equals minimum length")
    void shouldPassWhenValueEqualsMinimumLength() {

        List<ValidationError> result =
                createRule().validate(
                        MIN_BOUNDARY_VALUE);

        assertTrue(result.isEmpty());
    }

    /**
     * Verifies value exactly at maximum length passes.
     */
    @Test
    @DisplayName(
            "Should pass when value equals maximum length")
    void shouldPassWhenValueEqualsMaximumLength() {

        List<ValidationError> result =
                createRule().validate(
                        MAX_BOUNDARY_VALUE);

        assertTrue(result.isEmpty());
    }

    /**
     * Verifies value within range passes.
     */
    @Test
    @DisplayName(
            "Should pass when value length is within range")
    void shouldPassWhenLengthIsWithinRange() {

        List<ValidationError> result =
                createRule().validate(
                        VALID_VALUE);

        assertTrue(result.isEmpty());
    }

    /**
     * Verifies validation fails when value
     * is shorter than minimum length.
     */
    @Test
    @DisplayName(
            "Should return error when value is shorter than minimum")
    void shouldReturnErrorWhenLengthIsBelowMinimum() {

        List<ValidationError> result =
                createRule().validate(
                        SHORT_VALUE);

        assertInvalidLength(result);
    }

    /**
     * Verifies validation fails when value
     * exceeds maximum length.
     */
    @Test
    @DisplayName(
            "Should return error when value exceeds maximum")
    void shouldReturnErrorWhenLengthExceedsMaximum() {

        List<ValidationError> result =
                createRule().validate(
                        LONG_VALUE);

        assertInvalidLength(result);
    }

    /**
     * Validates invalid length error details.
     *
     * @param result validation result
     */
    private void assertInvalidLength(
            final List<ValidationError> result) {

        assertEquals(1, result.size());

        ValidationError error =
                result.get(0);

        assertEquals(
                FIELD_NAME,
                error.getField());

        assertEquals(
                ERROR_CODE,
                error.getErrorCode());

        assertEquals(
                Severity.ERROR,
                error.getSeverity());

        assertEquals(
                "Length must be between "
                        + MIN_LENGTH
                        + " and "
                        + MAX_LENGTH,
                error.getMessage());

        assertEquals(
                REMEDIATION_HINT,
                error.getRemediationHint());
    }
}
