package com.example.Database;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.example.Models.Client;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class ClientDAO {
    private final MongoCollection<Document> clientCollection;

    public ClientDAO() {
        MongoDatabase database = MongoDBConnection.getDatabase();
        this.clientCollection = database.getCollection("Clients");
    }

    // Add a new client
    public void addClient(Client client) {
        Document document = new Document()
                .append("name", client.getName())
                .append("email", client.getEmail())
                .append("phoneNumber", client.getPhoneNumber())
                .append("address", client.getAddress());
        clientCollection.insertOne(document);
    }

    // Get a client by ID
    public Client getClientById(ObjectId id) {
        Document query = new Document("_id", id);
        Document doc = clientCollection.find(query).first();

        if (doc != null) {
            Client client = new Client(
                doc.getString("name"),
                doc.getString("email"),
                doc.getString("phoneNumber"),
                doc.getString("address")
            );
            client.setId(doc.getObjectId("_id"));
            return client;
        }
        return null;
    }

    // Get all clients
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        for (Document doc : clientCollection.find()) {
            Client client = new Client(
                doc.getString("name"),
                doc.getString("email"),
                doc.getString("phoneNumber"),
                doc.getString("address")
            );
            client.setId(doc.getObjectId("_id"));
            clients.add(client);
        }
        return clients;
    }

    // Update a client by ID
    public void updateClient(ObjectId id, Client updatedClient) {
        Document query = new Document("_id", id);
        Document updatedDocument = new Document("$set", new Document()
                .append("name", updatedClient.getName())
                .append("email", updatedClient.getEmail())
                .append("phoneNumber", updatedClient.getPhoneNumber())
                .append("address", updatedClient.getAddress()));
        clientCollection.updateOne(query, updatedDocument);
    }

    // Delete a client by ID
    public void deleteClient(ObjectId id) {
        Document query = new Document("_id", id);
        clientCollection.deleteOne(query);
    }
}
