package com.dps.rules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base implementation for all validation rules.
 *
 * <p>
 * Provides a common SLF4J logger instance for subclasses and
 * enforces implementation of the {@link ValidationRule} contract.
 * </p>
 *
 * @param <T> type of object being validated
 *
 * @author Sapana Rathod
 * @version 1.0
 */
public abstract class AbstractValidationRule<T>
        implements ValidationRule<T> {
    /**
     * Logger instance available to all validation rule implementations.
     */
    protected final Logger log =
            LoggerFactory.getLogger(getClass());
}
