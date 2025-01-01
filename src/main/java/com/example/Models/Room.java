package com.example.Models; 

import java.util.List;

public class Room {
    private int roomNum;
    private List<String> image;
    private double price;
    private String type;
    private String category;

    public Room(int roomNum, List<String> image, double price, String type, String category) {
        this.roomNum = roomNum;
        this.price = price;
        this.type = type;
        this.category = category;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public double getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNum=" + roomNum +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
