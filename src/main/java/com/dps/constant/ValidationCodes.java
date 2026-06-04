package com.dps.constant;

/**
 * Standard validation error codes.
 */
public final class ValidationCodes {
    private ValidationCodes() {
    }

    public static final String FIELD_NULL =
            "FIELD_NULL";

    public static final String RANGE_VIOLATION =
            "RANGE_VIOLATION";

    public static final String INVALID_DATE =
            "INVALID_DATE";

    public static final String INVALID_ENUM =
            "INVALID_ENUM";

    public static final String REGEX_FAILED =
            "REGEX_FAILED";

    public static final String DUPLICATE_VALUE =
            "DUPLICATE_VALUE";

    public static final String REFERENTIAL_FAILURE =
            "REFERENTIAL_FAILURE";

    public static final String CROSS_FIELD_FAILURE =
            "CROSS_FIELD_FAILURE";
}
