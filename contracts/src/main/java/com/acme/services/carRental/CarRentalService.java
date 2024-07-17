package com.acme.services.carRental;

import io.infinitic.annotations.Name;

@Name(name = "CarRental")
public interface CarRentalService {
    CarRentalResult book(CarRentalCart cart);

    void cancel(CarRentalCart cart);
}
