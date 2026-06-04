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
 * Unit test class for {@link DateFormatRule}.
 *
 * <p>
 * Verifies:
 * </p>
 * <ul>
 *     <li>Null value validation</li>
 *     <li>Blank value validation</li>
 *     <li>Valid date validation</li>
 *     <li>Invalid date validation</li>
 *     <li>Constructor initialization</li>
 * </ul>
 *
 * <p>
 * Provides 100% line and branch coverage.
 * </p>
 *
 * @author Sapana Rathod
 * @version 1.0
 */
public class DateFormatRuleTest {
    /**
     * Field name used in validation.
     */
    private static final String FIELD_NAME = "orderDate";

    /**
     * Valid date pattern.
     */
    private static final String DATE_PATTERN =
            "yyyy-MM-dd";

    /**
     * Valid date value.
     */
    private static final String VALID_DATE =
            "2026-06-04";

    /**
     * Invalid date value.
     */
    private static final String INVALID_DATE =
            "04/06/2026";

    /**
     * Creates a rule for testing.
     *
     * @return date format rule
     */
    private DateFormatRule<String> createRule() {

        return new DateFormatRule<>(
                FIELD_NAME,
                value -> value,
                DATE_PATTERN);
    }

    /**
     * Verifies validation passes when value is null.
     */
    @Test
    @DisplayName(
            "Should return empty list when date value is null")
    void shouldReturnEmptyListForNullValue() {

        DateFormatRule<String> rule = createRule();

        List<ValidationError> result =
                rule.validate(null);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Verifies validation passes when value is blank.
     */
    @Test
    @DisplayName(
            "Should return empty list when date value is blank")
    void shouldReturnEmptyListForBlankValue() {

        DateFormatRule<String> rule = createRule();

        List<ValidationError> result =
                rule.validate(" ");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Verifies validation passes for valid date.
     */
    @Test
    @DisplayName(
            "Should return empty list for valid date")
    void shouldReturnEmptyListForValidDate() {

        DateFormatRule<String> rule = createRule();

        List<ValidationError> result =
                rule.validate(VALID_DATE);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Verifies validation error for invalid date.
     */
    @Test
    @DisplayName(
            "Should return validation error for invalid date")
    void shouldReturnValidationErrorForInvalidDate() {

        DateFormatRule<String> rule = createRule();

        List<ValidationError> result =
                rule.validate(INVALID_DATE);

        assertEquals(1, result.size());

        ValidationError error = result.get(0);

        assertEquals(FIELD_NAME, error.getField());

        assertEquals(
                "INVALID_DATE",
                error.getErrorCode());

        assertEquals(
                Severity.ERROR,
                error.getSeverity());

        assertTrue(
                error.getMessage()
                        .contains(INVALID_DATE));

        assertTrue(
                error.getRemediationHint()
                        .contains("Expected format"));
    }

    /**
     * Verifies rule instance creation.
     */
    @Test
    @DisplayName(
            "Should create rule successfully")
    void shouldCreateRuleSuccessfully() {

        DateFormatRule<String> rule =
                createRule();

        assertNotNull(rule);
    }
}
