package com.sunrisehotel.app;

import java.io.Serializable;

/**
 * Data Model for a Room, matching the Domain Model attributes (no, type, price, fascilities).
 * Implements Serializable to pass object via Intent between Activities.
 */
public class Room implements Serializable {
    private String roomNo;
    private String type;
    private double pricePerNight;
    private String facilities;
    private boolean isAvailable; // Mock availability status

    public Room(String roomNo, String type, double pricePerNight, String facilities) {
        this.roomNo = roomNo;
        this.type = type;
        this.pricePerNight = pricePerNight;
        this.facilities = facilities;
        this.isAvailable = true; // Assume available for mock data
    }

    // --- Getters ---
    public String getRoomNo() { return roomNo; }
    public String getType() { return type; }
    public double getPricePerNight() { return pricePerNight; }
    public String getFacilities() { return facilities; }
    public boolean isAvailable() { return isAvailable; }

    // Utility method to format price
    public String getFormattedPrice() {
        return "PKR " + String.format("%,.0f", pricePerNight) + " / Night";
    }

    // Static method to generate mock available rooms (simulating ViewAvailableRooms contract output)
    public static java.util.List<Room> generateMockRooms() {
        java.util.List<Room> rooms = new java.util.ArrayList<>();
        rooms.add(new Room("101", "Standard Single", 10000, "WiFi, AC, City View"));
        rooms.add(new Room("203", "Deluxe Double", 18000, "WiFi, AC, Balcony, Mini Fridge"));
        rooms.add(new Room("305", "Luxury Suite", 25000, "WiFi, AC, King Bed, Sea View, Lounge Access"));
        rooms.add(new Room("410", "Standard Single", 10500, "WiFi, AC, City View (Higher Floor)"));
        return rooms;
    }
}