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
 * Unit tests for {@link EnumMembershipRule}.
 *
 * <p>
 * Verifies:
 * </p>
 * <ul>
 *     <li>Null value handling</li>
 *     <li>Valid enum value validation</li>
 *     <li>Case-insensitive enum matching</li>
 *     <li>Invalid enum value validation</li>
 *     <li>Validation error details</li>
 * </ul>
 *
 * <p>
 * Provides 100% line coverage and 100% branch coverage.
 * </p>
 *
 * @author Sapana Rathod
 * @version 1.0
 */
public class EnumMembershipRuleTest {
    /**
     * Field name used during validation.
     */
    private static final String FIELD_NAME = "status";

    /**
     * Valid enum value.
     */
    private static final String VALID_VALUE = "ACTIVE";

    /**
     * Valid enum value with different case.
     */
    private static final String VALID_LOWER_CASE = "active";

    /**
     * Invalid enum value.
     */
    private static final String INVALID_VALUE = "UNKNOWN";

    /**
     * Expected validation error code.
     */
    private static final String ERROR_CODE = "INVALID_ENUM";

    /**
     * Expected remediation hint.
     */
    private static final String REMEDIATION_HINT =
            "Use allowed enum value";

    /**
     * Test enum used for validation.
     */
    private enum Status {
        ACTIVE,
        INACTIVE
    }

    /**
     * Creates a configured rule.
     *
     * @return enum membership rule
     */
    private EnumMembershipRule<String> createRule() {

        return new EnumMembershipRule<>(
                FIELD_NAME,
                value -> value,
                Status.class);
    }

    /**
     * Verifies rule creation.
     */
    @Test
    @DisplayName("Should create rule successfully")
    void shouldCreateRuleSuccessfully() {

        EnumMembershipRule<String> rule =
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
     * Verifies exact enum value passes validation.
     */
    @Test
    @DisplayName(
            "Should pass for exact enum value")
    void shouldPassForExactEnumValue() {

        List<ValidationError> result =
                createRule().validate(VALID_VALUE);

        assertTrue(result.isEmpty());
    }

    /**
     * Verifies enum matching is case-insensitive.
     */
    @Test
    @DisplayName(
            "Should pass for case insensitive enum value")
    void shouldPassForCaseInsensitiveEnumValue() {

        List<ValidationError> result =
                createRule().validate(
                        VALID_LOWER_CASE);

        assertTrue(result.isEmpty());
    }

    /**
     * Verifies validation error for unsupported enum value.
     */
    @Test
    @DisplayName(
            "Should return error for unsupported enum value")
    void shouldReturnErrorForUnsupportedEnumValue() {

        List<ValidationError> result =
                createRule().validate(
                        INVALID_VALUE);

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
                "Unsupported value: "
                        + INVALID_VALUE,
                error.getMessage());

        assertEquals(
                REMEDIATION_HINT,
                error.getRemediationHint());
    }
}
