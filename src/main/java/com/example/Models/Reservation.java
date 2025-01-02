package com.example.Models;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Reservation {

    private final StringProperty fullName = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty phoneNumber = new SimpleStringProperty();
    private final StringProperty CheckIn= new SimpleStringProperty();
    private final StringProperty CheckOut = new SimpleStringProperty();

    public Reservation(String fullName, String email ,String phoneNumber, String CheckIn, String CheckOut) {
        this.fullName.set(fullName);
        this.email.set(email);
        this.phoneNumber.set(phoneNumber);
        this.CheckIn.set(CheckIn);
        this.CheckOut.set(CheckOut);  // Store amenities as a string
    }

// Getters and Setters for fullName
    public String getFullName() {
        return fullName.get();
    }
    public StringProperty fullNameProperty() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    // Getters and Setters for email
    public String getEmail() {
        return email.get();
    }
    public StringProperty emailProperty() {
        return email;
    }
    public void setEmail(String email) {
        this.email.set(email);
    }

    // Getters and Setters for phoneNumber
    public String getPhoneNumber() {
        return phoneNumber.get();
    }
    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    // Getters and Setters for checkIn
    public String getCheckIn() {
        return CheckIn.get();
    }
    public StringProperty checkInProperty() {
        return CheckIn;
    }
    public void setCheckIn(String checkIn) {
        this.CheckIn.set(checkIn);
    }

    // Getters and Setters for checkOut
    public String getCheckOut() {
        return CheckOut.get();
    }
    public StringProperty checkOutProperty() {
        return CheckOut;
    }
    public void setCheckOut(String checkOut) {
        this.CheckOut.set(checkOut);
    }

}