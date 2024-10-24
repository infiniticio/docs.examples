package com.acme.contracts.services.notification;


public class BookingRequest  {

    private String id;
    private String host;
    private String traveler;

    public BookingRequest(String id, String host, String traveler) {
        this.id = id;
        this.host = host;
        this.traveler = traveler;
    }

    // Needed for Json serialization
    @SuppressWarnings("unused")
    public BookingRequest() { super(); }


    public String getId() {
        return id;
    }

    public String getHost() { return host;}

    public String getTraveler() {
        return traveler;
    }
}