package com.dps.model;

import lombok.Builder;
import lombok.Value;

import com.dps.constant.Severity;

/**
 * Single validation violation.
 */
@Value
@Builder
public class ValidationError {
    String field;

    String errorCode;

    Severity severity;

    String message;

    String remediationHint;


}
