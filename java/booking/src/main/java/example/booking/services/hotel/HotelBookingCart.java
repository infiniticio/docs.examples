package example.booking.services.hotel;

import java.util.UUID;

public class HotelBookingCart {
    private final UUID cartId = UUID.randomUUID();

    // code below is needed for serialization/deserialization

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HotelBookingCart that = (HotelBookingCart) o;
        return cartId.equals(that.cartId);
    }

    @Override
    public int hashCode() {
        return cartId.hashCode();
    }
}