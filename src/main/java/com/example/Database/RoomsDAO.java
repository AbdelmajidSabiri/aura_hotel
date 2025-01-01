package com.example.Database;

import com.example.Models.Room;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomsDAO {
    private final MongoCollection<Document> collection;

    public RoomsDAO() {
        try {
            MongoDatabase database = MongoDBConnection.getDatabase();
            this.collection = database.getCollection("rooms");
            System.out.println("Successfully connected to MongoDB collection");
        } catch (Exception e) {
            System.err.println("Failed to connect to MongoDB: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        try {
            System.out.println("Attempting to fetch rooms from database...");
            MongoCursor<Document> cursor = collection.find().iterator();
            int count = 0;
            
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                count++;
                System.out.println("Processing document: " + doc.toJson());

                Integer roomNum = Optional.ofNullable(doc.getInteger("room_num")).orElse(null);
                Double price = Optional.ofNullable(doc.getDouble("price")).orElse(null);
                String type = Optional.ofNullable(doc.getString("type")).orElse(null);
                String category = Optional.ofNullable(doc.getString("category")).orElse(null);
                String amenities = Optional.ofNullable(doc.getString("amenities")).orElse("");
                String status = Optional.ofNullable(doc.getString("status")).orElse(null);

                if (roomNum != null && price != null && type != null && category != null && status != null) {
                    Room room = new Room(
                            roomNum,
                            price,
                            type,
                            category,
                            amenities,
                            status
                    );
                    rooms.add(room);
                } else {
                    System.out.println("Skipping document due to null values: " + doc.toJson());
                }
            }
            System.out.println("Total documents processed: " + count);
            System.out.println("Total valid rooms added: " + rooms.size());
        } catch (Exception e) {
            System.err.println("Error fetching rooms: " + e.getMessage());
            e.printStackTrace();
        }
        return rooms;
    }

    public void addRoom(Room room) {
        try {
            Document doc = new Document()
                    .append("room_num", room.getRoomNum())
                    .append("price", room.getPrice())
                    .append("type", room.getType())
                    .append("category", room.getCategory())
                    .append("amenities", room.getAmenities())
                    .append("status", room.getStatus());

            collection.insertOne(doc);
            System.out.println("Room added to the database: " + doc.toJson());
        } catch (Exception e) {
            System.err.println("Error adding room: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
