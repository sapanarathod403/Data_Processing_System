package com.dps.rules;

import com.dps.constant.Severity;
import com.dps.model.ValidationError;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * Validates minimum and maximum string length.
 *
 * @param <T> target type
 */
public class StringLengthRule<T>extends AbstractValidationRule<T>{
    private final String fieldName;
    private final Function<T, String> extractor;
    private final int minLength;
    private final int maxLength;

    public StringLengthRule(
            final String fieldName,
            final Function<T, String> extractor,
            final int minLength,
            final int maxLength) {

        this.fieldName = fieldName;
        this.extractor = extractor;
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    @Override
    public List<ValidationError> validate(final T target) {

        String value = extractor.apply(target);

        if (value == null) {
            return Collections.emptyList();
        }

        int length = value.length();

        if (length < minLength || length > maxLength) {

            return List.of(
                    ValidationError.builder()
                            .field(fieldName)
                            .errorCode("INVALID_LENGTH")
                            .severity(Severity.ERROR)
                            .message(
                                    "Length must be between "
                                            + minLength
                                            + " and "
                                            + maxLength)
                            .remediationHint(
                                    "Adjust field length")
                            .build());
        }

        return Collections.emptyList();
    }
}
