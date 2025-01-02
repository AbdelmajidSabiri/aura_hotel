package com.example.Database;

import com.example.Models.Reservation;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservationsDAO {
    private final MongoCollection<Document> collection;

    public ReservationsDAO() {
        try {
            MongoDatabase database = MongoDBConnection.getDatabase();
            this.collection = database.getCollection("reservations");
            System.out.println("Successfully connected to MongoDB collection");
        } catch (Exception e) {
            System.err.println("Failed to connect to MongoDB: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        try {
            System.out.println("Attempting to fetch reservations from database...");
            MongoCursor<Document> cursor = collection.find().iterator();
            int count = 0;

            while (cursor.hasNext()) {
                Document doc = cursor.next();
                count++;
                System.out.println("Processing document: " + doc.toJson());

                String fullName = Optional.ofNullable(doc.getString("fullName")).orElse(null);
                String email = Optional.ofNullable(doc.getString("email")).orElse(null);
                String phoneNumber = Optional.ofNullable(doc.getString("phoneNumber")).orElse(null);
                String checkIn = Optional.ofNullable(doc.getString("checkIn")).orElse(null);
                String checkOut = Optional.ofNullable(doc.getString("checkOut")).orElse(null);

                if (fullName != null && email != null && phoneNumber != null && checkIn != null && checkOut != null) {
                    Reservation reservation = new Reservation(fullName, email, phoneNumber, checkIn, checkOut);
                    reservations.add(reservation);
                } else {
                    System.out.println("Skipping document due to null values: " + doc.toJson());
                }
            }
            System.out.println("Total documents processed: " + count);
            System.out.println("Total valid reservations added: " + reservations.size());
        } catch (Exception e) {
            System.err.println("Error fetching reservations: " + e.getMessage());
            e.printStackTrace();
        }
        return reservations;
    }

    public void addReservation(Reservation reservation) {
        try {
            Document doc = new Document()
                    .append("fullName", reservation.getFullName())
                    .append("email", reservation.getEmail())
                    .append("phoneNumber", reservation.getPhoneNumber())
                    .append("checkIn", reservation.getCheckIn())
                    .append("checkOut", reservation.getCheckOut());

            collection.insertOne(doc);
            System.out.println("Reservation added to the database: " + doc.toJson());
        } catch (Exception e) {
            System.err.println("Error adding reservation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateReservation(int index, Reservation updatedReservation) {
        try {
            // Fetch the current document by the unique identifier or index logic
            MongoCursor<Document> cursor = collection.find().skip(index).limit(1).iterator();
            if (cursor.hasNext()) {
                Document originalDocument = cursor.next();
                // Build the update document
                Document update = new Document("$set", new Document()
                        .append("fullName", updatedReservation.getFullName())
                        .append("email", updatedReservation.getEmail())
                        .append("phoneNumber", updatedReservation.getPhoneNumber())
                        .append("checkIn", updatedReservation.getCheckIn())
                        .append("checkOut", updatedReservation.getCheckOut())
                );

                // Update the document in the collection
                collection.updateOne(originalDocument, update);
                System.out.println("Reservation updated successfully at index: " + index);
            } else {
                System.err.println("No document found at index: " + index);
            }
        } catch (Exception e) {
            System.err.println("Error updating reservation at index: " + index + ", " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteReservation(int index) {
        try {
            // Use a cursor to find the document at the given index
            MongoCursor<Document> cursor = collection.find().skip(index).limit(1).iterator();
            if (cursor.hasNext()) {
                Document documentToDelete = cursor.next();

                // Delete the document
                long deletedCount = collection.deleteOne(documentToDelete).getDeletedCount();
                if (deletedCount > 0) {
                    System.out.println("Reservation at index " + index + " deleted successfully.");
                } else {
                    System.err.println("Failed to delete reservation at index " + index + ".");
                }
            } else {
                System.err.println("No reservation found at index: " + index);
            }
        } catch (Exception e) {
            System.err.println("Error deleting reservation at index " + index + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}
