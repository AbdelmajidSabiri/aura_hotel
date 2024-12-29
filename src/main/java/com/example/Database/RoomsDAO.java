package com.example.Database;

import com.example.Models.Room;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class RoomsDAO {
    private MongoCollection<Document> collection;

    public RoomsDAO() {
        MongoDatabase database = MongoDBConnection.getDatabase();
        this.collection = database.getCollection("Rooms");
    }

    // Retrieve all rooms
    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Room room = new Room(
                        doc.getInteger("room_num"),
                        doc.getList("image", String.class),
                        doc.getDouble("price"),
                        doc.getString("type"),
                        doc.getString("categorie")
                );
                rooms.add(room);
            }
        }
        return rooms;
    }

    // Retrieve rooms by category
    public List<Room> getRoomsByCategory(String category) {
        List<Room> rooms = new ArrayList<>();
        Document query = new Document("categorie", category);
        try (MongoCursor<Document> cursor = collection.find(query).iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Room room = new Room(
                        doc.getInteger("room_num"),
                        doc.getList("image", String.class),
                        doc.getDouble("price"),
                        doc.getString("type"),
                        doc.getString("categorie")
                );
                rooms.add(room);
            }
        }
        return rooms;
    }
}
