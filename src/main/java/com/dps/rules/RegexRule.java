package com.dps.rules;

import com.dps.constant.Severity;
import com.dps.model.ValidationError;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Validates a field against a regular expression.
 *
 * @param <T> target object type
 */
public class RegexRule<T> extends AbstractValidationRule<T>{
    private final String fieldName;
    private final Function<T, String> extractor;
    private final Pattern pattern;

    public RegexRule(
            final String fieldName,
            final Function<T, String> extractor,
            final String regex) {

        this.fieldName = fieldName;
        this.extractor = extractor;
        this.pattern = Pattern.compile(regex);
    }

    @Override
    public List<ValidationError> validate(final T target) {

        String value = extractor.apply(target);

        if (value == null || value.isBlank()) {
            return Collections.emptyList();
        }

        if (!pattern.matcher(value).matches()) {

            log.warn("Regex validation failed for {}", fieldName);

            return List.of(
                    ValidationError.builder()
                            .field(fieldName)
                            .errorCode("REGEX_VIOLATION")
                            .severity(Severity.ERROR)
                            .message("Invalid format")
                            .remediationHint("Verify field format")
                            .build());
        }

        return Collections.emptyList();
    }

}
