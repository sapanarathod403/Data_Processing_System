package com.dps.rules;

import com.dps.constant.Severity;
import com.dps.model.ValidationError;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * Validates that a numeric field falls within a configured range.
 *
 * @param <T> target object type
 */
public class RangeRule<T>  extends AbstractValidationRule<T>{
    private final String fieldName;

    private final Function<T, BigDecimal> extractor;

    private final BigDecimal min;

    private final BigDecimal max;

    public RangeRule(
            final String fieldName,
            final Function<T, BigDecimal> extractor,
            final BigDecimal min,
            final BigDecimal max) {

        this.fieldName = fieldName;
        this.extractor = extractor;
        this.min = min;
        this.max = max;
    }

    @Override
    public List<ValidationError> validate(final T target) {

        log.debug(
                "Executing RangeRule for field [{}]",
                fieldName);

        BigDecimal value = extractor.apply(target);

        if (value == null) {
            return Collections.emptyList();
        }

        if (value.compareTo(min) < 0
                || value.compareTo(max) > 0) {

            log.warn(
                    "Range validation failed for field [{}]. Value={}",
                    fieldName,
                    value);

            return List.of(buildError(value));
        }

        return Collections.emptyList();
    }

    private ValidationError buildError(
            final BigDecimal value) {

        return ValidationError.builder()
                .field(fieldName)
                .errorCode("RANGE_VIOLATION")
                .severity(Severity.ERROR)
                .message(String.format(
                        "Value %s is outside allowed range [%s, %s]",
                        value,
                        min,
                        max))
                .remediationHint(
                        String.format(
                                "Provide a value between %s and %s",
                                min,
                                max))
                .build();
    }
}
