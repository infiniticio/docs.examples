package com.acme.workflows.loyaty;

import java.util.Arrays;

public enum BonusEvent {
    ORDER_COMPLETED,
    FORM_COMPLETED,
    REGISTRATION_COMPLETED;

    public static BonusEvent getFromString(String eventName) throws RuntimeException {
        try {
            return BonusEvent.valueOf(eventName);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(eventName + " should be one of " + String.join(", ", Arrays.toString(BonusEvent.values())));
        }
    }
}
