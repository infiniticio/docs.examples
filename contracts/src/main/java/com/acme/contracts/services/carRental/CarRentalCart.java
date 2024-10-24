package com.acme.contracts.services.carRental;

import java.util.UUID;

public class CarRentalCart {
    public final UUID cartId = UUID.randomUUID();

    // code below is needed for serialization/deserialization
    public CarRentalCart() { super(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarRentalCart that = (CarRentalCart) o;
        return cartId.equals(that.cartId);
    }

    @Override
    public int hashCode() {
        return cartId.hashCode();
    }
}
