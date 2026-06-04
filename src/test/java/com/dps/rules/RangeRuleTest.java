package com.dps.rules;

import com.dps.constant.Severity;
import com.dps.model.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for {@link RangeRule}.
 *
 * <p>
 * Verifies:
 * </p>
 * <ul>
 *     <li>Null value handling</li>
 *     <li>Minimum boundary validation</li>
 *     <li>Maximum boundary validation</li>
 *     <li>Below minimum validation</li>
 *     <li>Above maximum validation</li>
 *     <li>Valid range validation</li>
 * </ul>
 *
 * <p>
 * Provides 100% line and branch coverage.
 * </p>
 *
 * @author Sapana Rathod
 * @version 1.0
 */
public class RangeRuleTest {
    /**
     * Test field name.
     */
    private static final String FIELD_NAME =
            "amount";

    /**
     * Validation error code.
     */
    private static final String ERROR_CODE =
            "RANGE_VIOLATION";

    /**
     * Minimum allowed value.
     */
    private static final BigDecimal MIN_VALUE =
            BigDecimal.ZERO;

    /**
     * Maximum allowed value.
     */
    private static final BigDecimal MAX_VALUE =
            BigDecimal.valueOf(100);

    /**
     * Value below minimum.
     */
    private static final BigDecimal BELOW_MIN =
            BigDecimal.valueOf(-1);

    /**
     * Value above maximum.
     */
    private static final BigDecimal ABOVE_MAX =
            BigDecimal.valueOf(101);

    /**
     * Valid value inside range.
     */
    private static final BigDecimal VALID_VALUE =
            BigDecimal.valueOf(50);

    /**
     * Creates a rule instance.
     *
     * @return configured range rule
     */
    private RangeRule<BigDecimal> createRule() {

        return new RangeRule<>(
                FIELD_NAME,
                value -> value,
                MIN_VALUE,
                MAX_VALUE);
    }

    /**
     * Verifies rule creation.
     */
    @Test
    @DisplayName("Should create rule successfully")
    void shouldCreateRuleSuccessfully() {

        RangeRule<BigDecimal> rule =
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
     * Verifies value exactly at minimum passes.
     */
    @Test
    @DisplayName(
            "Should pass when value equals minimum")
    void shouldPassWhenValueEqualsMinimum() {

        List<ValidationError> result =
                createRule().validate(MIN_VALUE);

        assertTrue(result.isEmpty());
    }

    /**
     * Verifies value exactly at maximum passes.
     */
    @Test
    @DisplayName(
            "Should pass when value equals maximum")
    void shouldPassWhenValueEqualsMaximum() {

        List<ValidationError> result =
                createRule().validate(MAX_VALUE);

        assertTrue(result.isEmpty());
    }

    /**
     * Verifies value inside range passes.
     */
    @Test
    @DisplayName(
            "Should pass when value is within range")
    void shouldPassWhenValueIsWithinRange() {

        List<ValidationError> result =
                createRule().validate(VALID_VALUE);

        assertTrue(result.isEmpty());
    }

    /**
     * Verifies validation error when value
     * is below minimum.
     */
    @Test
    @DisplayName(
            "Should return error when value is below minimum")
    void shouldReturnErrorWhenValueIsBelowMinimum() {

        List<ValidationError> result =
                createRule().validate(BELOW_MIN);

        assertRangeViolation(result, BELOW_MIN);
    }

    /**
     * Verifies validation error when value
     * is above maximum.
     */
    @Test
    @DisplayName(
            "Should return error when value is above maximum")
    void shouldReturnErrorWhenValueIsAboveMaximum() {

        List<ValidationError> result =
                createRule().validate(ABOVE_MAX);

        assertRangeViolation(result, ABOVE_MAX);
    }

    /**
     * Validates range violation details.
     *
     * @param result validation result
     * @param value offending value
     */
    private void assertRangeViolation(
            final List<ValidationError> result,
            final BigDecimal value) {

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

        assertTrue(
                error.getMessage()
                        .contains(value.toString()));

        assertTrue(
                error.getMessage()
                        .contains(MIN_VALUE.toString()));

        assertTrue(
                error.getMessage()
                        .contains(MAX_VALUE.toString()));

        assertTrue(
                error.getRemediationHint()
                        .contains(MIN_VALUE.toString()));

        assertTrue(
                error.getRemediationHint()
                        .contains(MAX_VALUE.toString()));
    }
}
