package com.acme.contracts.services.carRental;

import io.infinitic.annotations.Name;

@Name(name = "CarRental")
public interface CarRentalService {
    CarRentalResult book(CarRentalCart cart);

    void rollback(CarRentalCart cart);
}
