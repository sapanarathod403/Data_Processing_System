package com.dps.rules;

import com.dps.constant.Severity;
import com.dps.model.ValidationError;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * Validates enum membership.
 *
 * @param <T> target type
 */
public class EnumMembershipRule<T> extends AbstractValidationRule<T>{
    private final String fieldName;
    private final Function<T, String> extractor;
    private final Class<? extends Enum<?>> enumClass;

    public EnumMembershipRule(
            final String fieldName,
            final Function<T, String> extractor,
            final Class<? extends Enum<?>> enumClass) {

        this.fieldName = fieldName;
        this.extractor = extractor;
        this.enumClass = enumClass;
    }

    @Override
    public List<ValidationError> validate(final T target) {

        String value = extractor.apply(target);

        if (value == null) {
            return Collections.emptyList();
        }

        boolean valid =
                Arrays.stream(enumClass.getEnumConstants())
                        .anyMatch(
                                e -> e.name()
                                        .equalsIgnoreCase(value));

        if (!valid) {

            return List.of(
                    ValidationError.builder()
                            .field(fieldName)
                            .errorCode("INVALID_ENUM")
                            .severity(Severity.ERROR)
                            .message(
                                    "Unsupported value: "
                                            + value)
                            .remediationHint(
                                    "Use allowed enum value")
                            .build());
        }

        return Collections.emptyList();
    }
}
