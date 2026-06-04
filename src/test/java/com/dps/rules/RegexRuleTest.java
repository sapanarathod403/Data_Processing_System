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
 * Unit tests for {@link RegexRule}.
 *
 * <p>
 * Verifies:
 * </p>
 * <ul>
 *     <li>Null value handling</li>
 *     <li>Blank value handling</li>
 *     <li>Successful regex validation</li>
 *     <li>Regex validation failure</li>
 *     <li>Validation error content</li>
 * </ul>
 *
 * <p>
 * Provides 100% line coverage and 100% branch coverage.
 * </p>
 *
 * @author Sapana Rathod
 * @version 1.0
 */
public class RegexRuleTest {
    /**
     * Field name under validation.
     */
    private static final String FIELD_NAME = "email";

    /**
     * Email validation regex.
     */
    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    /**
     * Valid email value.
     */
    private static final String VALID_EMAIL =
            "test@example.com";

    /**
     * Invalid email value.
     */
    private static final String INVALID_EMAIL =
            "invalid-email";

    /**
     * Error code expected from validation.
     */
    private static final String ERROR_CODE =
            "REGEX_VIOLATION";

    /**
     * Expected validation message.
     */
    private static final String ERROR_MESSAGE =
            "Invalid format";

    /**
     * Expected remediation hint.
     */
    private static final String REMEDIATION_HINT =
            "Verify field format";

    /**
     * Creates a regex rule for testing.
     *
     * @return configured regex rule
     */
    private RegexRule<String> createRule() {

        return new RegexRule<>(
                FIELD_NAME,
                value -> value,
                EMAIL_REGEX);
    }

    /**
     * Verifies rule creation.
     */
    @Test
    @DisplayName("Should create rule successfully")
    void shouldCreateRuleSuccessfully() {

        RegexRule<String> rule = createRule();

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
     * Verifies blank values are ignored.
     */
    @Test
    @DisplayName(
            "Should return empty list when value is blank")
    void shouldReturnEmptyListWhenValueIsBlank() {

        List<ValidationError> result =
                createRule().validate(" ");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Verifies valid values pass validation.
     */
    @Test
    @DisplayName(
            "Should return empty list for matching regex")
    void shouldReturnEmptyListForMatchingRegex() {

        List<ValidationError> result =
                createRule().validate(VALID_EMAIL);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Verifies validation error is returned
     * when regex validation fails.
     */
    @Test
    @DisplayName(
            "Should return validation error for invalid format")
    void shouldReturnValidationErrorForInvalidFormat() {

        List<ValidationError> result =
                createRule().validate(INVALID_EMAIL);

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
                ERROR_MESSAGE,
                error.getMessage());

        assertEquals(
                REMEDIATION_HINT,
                error.getRemediationHint());
    }
}
