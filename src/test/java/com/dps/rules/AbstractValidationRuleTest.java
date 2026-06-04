package com.dps.rules;

import com.dps.model.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for {@link AbstractValidationRule}.
 *
 * <p>
 * Verifies logger initialization and inheritance behaviour.
 * </p>
 *
 * @author Sapana Rathod
 * @version 1.0
 */
public class AbstractValidationRuleTest {
    /**
     * Validation rule used for testing.
     */
    private final AbstractValidationRule<String> rule =
            new AbstractValidationRule<>() {

                @Override
                public List<ValidationError> validate(
                        final String target) {

                    return List.of();
                }
            };

    @Test
    @DisplayName("Should initialize logger")
    void testLoggerInitialization() {

        assertNotNull(rule.log);
    }

    @Test
    @DisplayName("Should validate successfully")
    void testValidate() {

        assertNotNull(rule.validate("value"));
    }
}
