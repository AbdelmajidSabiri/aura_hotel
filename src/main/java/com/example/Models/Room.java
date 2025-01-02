package com.example.Models;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Room {
    private final IntegerProperty roomNum = new SimpleIntegerProperty();
    private final DoubleProperty price = new SimpleDoubleProperty();
    private final StringProperty type = new SimpleStringProperty();
    private final StringProperty category = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty amenities = new SimpleStringProperty();  // Updated to StringProperty

    public Room(int roomNum, double price, String type, String category, String amenities, String status) {
        this.roomNum.set(roomNum);
        this.price.set(price);
        this.type.set(type);
        this.category.set(category);
        this.amenities.set(amenities);  // Store amenities as a string
        this.status.set(status);
    }



    // Getters for each property
    public int getRoomNum() {
        return roomNum.get();
    }
    public IntegerProperty roomNumProperty() {
        return roomNum;
    }
    public void setRoomNum(int roomNum) {
        this.roomNum.set(roomNum);
    }

    public double getPrice() {
        return price.get();
    }
    public DoubleProperty priceProperty() {
        return price;
    }
    public void setPrice(double price) {
        this.price.set(price);
    }

    public String getType() {
        return type.get();
    }
    public StringProperty typeProperty() {
        return type;
    }
    public void setType(String type) {
        this.type.set(type);
    }

    public String getCategory() {
        return category.get();
    }
    public StringProperty categoryProperty() {
        return category;
    }
    public void setCategory(String category) {
        this.category.set(category);
    }

    public String getAmenities() {
        return amenities.get();
    }
    public StringProperty amenitiesProperty() {
        return amenities;
    }
    public void setAmenities(String amenities) {
        this.amenities.set(amenities);
    }

    public String getStatus() {
        return status.get();
    }
    public StringProperty statusProperty() {
        return status;
    }
    public void setStatus(String status) {
        this.status.set(status);
    }
}