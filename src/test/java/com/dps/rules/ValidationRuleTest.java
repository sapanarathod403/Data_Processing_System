package com.dps.rules;

import com.dps.model.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for {@link ValidationRule}.
 *
 * <p>
 * Covers:
 * </p>
 * <ul>
 *     <li>validate contract</li>
 *     <li>andThen composition</li>
 *     <li>or composition</li>
 *     <li>all execution branches</li>
 * </ul>
 *
 * @author Sapana Rathod
 * @version 1.0
 */
public class ValidationRuleTest {
    /**
     * Sample target value.
     */
    private static final String TARGET = "TARGET";

    /**
     * Creates validation error.
     *
     * @return validation error
     */
    private ValidationError createError() {
        return ValidationError.builder()
                .field("field")
                .message("message")
                .build();
    }

    @Test
    @DisplayName("Should execute validate implementation")
    void testValidate() {

        ValidationRule<String> rule =
                value -> List.of(createError());

        List<ValidationError> result =
                rule.validate(TARGET);

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should combine errors using andThen")
    void testAndThenWithErrors() {

        ValidationRule<String> first =
                value -> List.of(createError());

        ValidationRule<String> second =
                value -> List.of(createError());

        List<ValidationError> result =
                first.andThen(second)
                        .validate(TARGET);

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Should return empty list when both rules pass")
    void testAndThenWithNoErrors() {

        ValidationRule<String> first =
                value -> List.of();

        ValidationRule<String> second =
                value -> List.of();

        List<ValidationError> result =
                first.andThen(second)
                        .validate(TARGET);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should return current result when first rule succeeds")
    void testOrWhenCurrentPasses() {

        List<ValidationError> empty =
                List.of();

        ValidationRule<String> first =
                value -> empty;

        ValidationRule<String> second =
                value -> List.of(createError());

        List<ValidationError> result =
                first.or(second)
                        .validate(TARGET);

        assertSame(empty, result);
    }

    @Test
    @DisplayName("Should execute alternative when first rule fails")
    void testOrWhenCurrentFails() {

        ValidationRule<String> first =
                value -> List.of(createError());

        ValidationRule<String> second =
                value -> List.of();

        List<ValidationError> result =
                first.or(second)
                        .validate(TARGET);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should return alternative errors when both fail")
    void testOrWhenBothFail() {

        ValidationRule<String> first =
                value -> List.of(createError());

        ValidationRule<String> second =
                value -> List.of(createError());

        List<ValidationError> result =
                first.or(second)
                        .validate(TARGET);

        assertEquals(1, result.size());
    }
}
