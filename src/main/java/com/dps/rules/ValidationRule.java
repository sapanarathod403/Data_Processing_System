package com.dps.rules;

import com.dps.model.ValidationError;

import java.util.List;

/**
 * Generic validation contract.
 *
 * @param <T> target object type
 */
@FunctionalInterface
public interface ValidationRule<T> {
    List<ValidationError> validate(T target);

    default ValidationRule<T> andThen(ValidationRule<T> next) {

        return target -> {
            List<ValidationError> errors =
                    new java.util.ArrayList<>(validate(target));

            errors.addAll(next.validate(target));

            return errors;
        };
    }

    default ValidationRule<T> or(ValidationRule<T> alternative) {

        return target -> {

            List<ValidationError> current =
                    validate(target);

            if (current.isEmpty()) {
                return current;
            }

            return alternative.validate(target);
        };
    }
}
