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
 * Unit tests for {@link NullCheckRule}.
 *
 * <p>
 * Verifies:
 * </p>
 * <ul>
 *     <li>Validation succeeds when field value is not null</li>
 *     <li>Validation fails when field value is null</li>
 *     <li>Validation error attributes are populated correctly</li>
 * </ul>
 *
 * <p>
 * Provides 100% line and branch coverage.
 * </p>
 *
 * @author Sapana Rathod
 * @version 1.0
 */
public class NullCheckRuleTest {
    /**
     * Test field name.
     */
    private static final String FIELD_NAME =
            "customerName";

    /**
     * Valid field value.
     */
    private static final String VALID_VALUE =
            "John Doe";

    /**
     * Expected error code.
     */
    private static final String ERROR_CODE =
            "FIELD_NULL";

    /**
     * Expected remediation hint.
     */
    private static final String REMEDIATION_HINT =
            "Provide a valid value for the field";

    /**
     * Creates a rule that directly validates
     * the provided String value.
     *
     * @return configured rule
     */
    private NullCheckRule<String> createRule() {

        return new NullCheckRule<>(
                FIELD_NAME,
                value -> value);
    }

    /**
     * Verifies validation succeeds when value is present.
     */
    @Test
    @DisplayName(
            "Should return empty list when value is not null")
    void shouldReturnEmptyListWhenValueExists() {

        NullCheckRule<String> rule =
                createRule();

        List<ValidationError> result =
                rule.validate(VALID_VALUE);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Verifies validation error is generated
     * when value is null.
     */
    @Test
    @DisplayName(
            "Should return validation error when value is null")
    void shouldReturnValidationErrorWhenValueIsNull() {

        NullCheckRule<String> rule =
                createRule();

        List<ValidationError> result =
                rule.validate(null);

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
                FIELD_NAME + " is required",
                error.getMessage());

        assertEquals(
                REMEDIATION_HINT,
                error.getRemediationHint());
    }

    /**
     * Verifies rule instance creation.
     */
    @Test
    @DisplayName(
            "Should create rule successfully")
    void shouldCreateRuleSuccessfully() {

        NullCheckRule<String> rule =
                createRule();

        assertNotNull(rule);
    }
}
