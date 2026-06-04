package com.dps.rules;

import com.dps.constant.Severity;
import com.dps.model.ValidationError;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * Validates date format.
 *
 * @param <T> target type
 */
public class DateFormatRule<T> extends AbstractValidationRule<T> {
    private final String fieldName;
    private final Function<T, String> extractor;
    private final DateTimeFormatter formatter;

    public DateFormatRule(
            final String fieldName,
            final Function<T, String> extractor,
            final String pattern) {

        this.fieldName = fieldName;
        this.extractor = extractor;
        this.formatter =
                DateTimeFormatter.ofPattern(pattern);
    }

    @Override
    public List<ValidationError> validate(final T target) {

        String value = extractor.apply(target);

        if (value == null || value.isBlank()) {
            return Collections.emptyList();
        }

        try {

            LocalDate.parse(value, formatter);

            return Collections.emptyList();

        } catch (DateTimeParseException exception) {

            return List.of(
                    ValidationError.builder()
                            .field(fieldName)
                            .errorCode("INVALID_DATE")
                            .severity(Severity.ERROR)
                            .message(
                                    value + " is invalid")
                            .remediationHint(
                                    "Expected format: "
                                            + formatter)
                            .build());
        }
    }
}
