package com.example.Database;

import com.example.Models.Room;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

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



    public void updateRoom(int index, Room updatedRoom) {
        try {
            // Fetch the current document by the unique identifier or index logic
            MongoCursor<Document> cursor = collection.find().skip(index).limit(1).iterator();
            if (cursor.hasNext()) {
                Document originalDocument = cursor.next();
                // Build the update document
                Document update = new Document("$set", new Document()
                        .append("room_num", updatedRoom.getRoomNum())
                        .append("price", updatedRoom.getPrice())
                        .append("type", updatedRoom.getType())
                        .append("category", updatedRoom.getCategory())
                        .append("amenities", updatedRoom.getAmenities())
                        .append("status", updatedRoom.getStatus())
                );
    
                // Update the document in the collection
                collection.updateOne(originalDocument, update);
                System.out.println("Room updated successfully at index: " + index);
            } else {
                System.err.println("No document found at index: " + index);
            }
        } catch (Exception e) {
            System.err.println("Error updating room at index: " + index + ", " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void deleteRoom(int index) {
        try {
            // Use a cursor to find the document at the given index
            MongoCursor<Document> cursor = collection.find().skip(index).limit(1).iterator();
            if (cursor.hasNext()) {
                Document documentToDelete = cursor.next();
    
                // Delete the document
                long deletedCount = collection.deleteOne(documentToDelete).getDeletedCount();
                if (deletedCount > 0) {
                    System.out.println("Room at index " + index + " deleted successfully.");
                } else {
                    System.err.println("Failed to delete room at index " + index + ".");
                }
            } else {
                System.err.println("No room found at index: " + index);
            }
        } catch (Exception e) {
            System.err.println("Error deleting room at index " + index + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    

}
