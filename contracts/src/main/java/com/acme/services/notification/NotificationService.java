package com.acme.services.notification;

import io.infinitic.annotations.Name;

@Name(name = "NotificationService")
public interface NotificationService {

    void notifyHostOfRequest(BookingRequest request, int attempt);

    void notifyHostOfRequestExpiration(BookingRequest request);

    void notifyTravelerOfRequestExpiration(BookingRequest request);

    void notifyHostOfPaymentSuccess(BookingRequest request);

    void notifyTravelerOfBookingSuccess(BookingRequest request);

    void notifyTravelerOfBookingDenial(BookingRequest request);

}