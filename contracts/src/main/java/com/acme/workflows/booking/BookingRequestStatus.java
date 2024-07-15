package com.acme.workflows.booking;

import java.util.Arrays;

public enum BookingRequestStatus {
    ACCEPTED,
    DENIED;

    private static final String EXCEPTION_MESSAGE = " should be one of " + Arrays.toString(BookingRequestStatus.values());

    public static BookingRequestStatus getFromString(String status) {
        try {
            return BookingRequestStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(status + EXCEPTION_MESSAGE);
        }
    }
}
