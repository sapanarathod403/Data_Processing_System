package com.dps.rules;

import com.dps.constant.Severity;
import com.dps.model.ValidationError;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * Validation rule that verifies a mandatory field is not null.
 *
 * <p>
 * This rule extracts a field value from the target object using
 * the supplied extractor function and validates that the value
 * is not null.
 * </p>
 *
 * <p>
 * Example:
 * </p>
 *
 * <pre>
 * NullCheckRule&lt;RawRecord&gt; rule =
 *     new NullCheckRule&lt;&gt;(
 *         "customerName",
 *         RawRecord::getCustomerName);
 * </pre>
 *
 * <p>
 * If the field value is null, a validation error with code
 * {@code FIELD_NULL} is generated.
 * </p>
 *
 * @param <T> target object type
 *
 * @author Sapana Rathod
 * @version 1.0
 */
public class NullCheckRule<T>  extends AbstractValidationRule<T>{
    /**
     * Name of the field being validated.
     */
    private final String fieldName;

    /**
     * Function used to extract the field value.
     */
    private final Function<T, Object> extractor;

    /**
     * Creates a new null-check validation rule.
     *
     * @param fieldName name of the field being validated
     * @param extractor function used to extract field value
     */
    public NullCheckRule(
            final String fieldName,
            final Function<T, Object> extractor) {

        this.fieldName = fieldName;
        this.extractor = extractor;
    }

    /**
     * Validates that the configured field is not null.
     *
     * @param target object being validated
     *
     * @return validation errors if field is null,
     *         otherwise an empty list
     */
    @Override
    public List<ValidationError> validate(final T target) {

        log.debug(
                "Executing NullCheckRule for field [{}]",
                fieldName);

        Object value = extractor.apply(target);

        if (value != null) {

            log.debug(
                    "NullCheckRule passed for field [{}]",
                    fieldName);

            return Collections.emptyList();
        }

        log.warn(
                "Null validation failed for field [{}]",
                fieldName);

        return List.of(
                ValidationError.builder()
                        .field(fieldName)
                        .errorCode("FIELD_NULL")
                        .severity(Severity.ERROR)
                        .message(fieldName + " is required")
                        .remediationHint(
                                "Provide a valid value for the field")
                        .build()
        );
    }
}
