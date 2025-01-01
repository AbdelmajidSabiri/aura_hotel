package com.example;

import java.io.IOException;
import java.util.List;
import com.example.Database.RoomsDAO;
import com.example.Models.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class ReserveListController {

    @FXML private TableView<Room> tableView;
    @FXML private TableColumn<Room, Integer> colRoomNum;
    @FXML private TableColumn<Room, Double> colPrice;
    @FXML private TableColumn<Room, String> colType;
    @FXML private TableColumn<Room, String> colCategory;
    @FXML private TableColumn<Room, String> colAmenities;
    @FXML private TableColumn<Room, String> colStatus;
    
    private final ObservableList<Room> roomList = FXCollections.observableArrayList();
    private final RoomsDAO roomsDAO = new RoomsDAO();

    @FXML private Button addButton;
    // @FXML private Button editButton;
    // @FXML private Button deleteButton;
    // @FXML private Button refreshButton;
    // @FXML private Button exportButton;

    @FXML
    public void initialize() {
        try {
            System.out.println("Initializing ReserveListController...");
            
            if (tableView == null || colRoomNum == null || colPrice == null || 
                colType == null || colCategory == null || colAmenities == null || colStatus == null) {
                throw new RuntimeException("FXML components not properly injected!");
            }

            colRoomNum.setCellValueFactory(new PropertyValueFactory<>("roomNum"));
            colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            colType.setCellValueFactory(new PropertyValueFactory<>("type"));
            colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
            colAmenities.setCellValueFactory(new PropertyValueFactory<>("amenities"));
            colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

            // Configure the TableView
            tableView.setItems(roomList);
            
            // Enable table columns reordering and resizing
            tableView.setTableMenuButtonVisible(true);
            
            // Load the initial data
            loadRooms();
            
            addButton.setOnAction(event -> handleAddReservation());
            // editButton.setOnAction(event -> handleEditReservation());
            // deleteButton.setOnAction(event -> handleDeleteReservation());
            // refreshButton.setOnAction(event -> refreshTable());
            // exportButton.setOnAction(event -> handleExportToCsv());





            System.out.println("Controller initialization completed.");
        } catch (Exception e) {
            System.err.println("Error during controller initialization: " + e.getMessage());
            e.printStackTrace();
        }

        
    }

    private void loadRooms() {
        try {
            System.out.println("Loading rooms...");
            RoomsDAO roomsDAO = new RoomsDAO();
            List<Room> rooms = roomsDAO.getAllRooms();
            
            System.out.println("Fetched " + rooms.size() + " rooms from database");
            
            // Clear and add all rooms
            roomList.clear();
            roomList.addAll(rooms);
            
            // Force the table to refresh
            tableView.getColumns().get(0).setVisible(false);
            tableView.getColumns().get(0).setVisible(true);
            
            System.out.println("Added " + roomList.size() + " rooms to table");
            
            // Print first room details to verify data
            if (!rooms.isEmpty()) {
                Room firstRoom = rooms.get(0);
                System.out.println("First room details:");
                System.out.println("Room number: " + firstRoom.getRoomNum());
                System.out.println("Price: " + firstRoom.getPrice());
                System.out.println("Type: " + firstRoom.getType());
            }
        } catch (Exception e) {
            System.err.println("Error loading rooms: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void refreshTable() {
        loadRooms();
    }


    @FXML
    private void handleAddReservation() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/AddReservation.fxml"));
            Parent root = loader.load();
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add New Reservation");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(tableView.getScene().getWindow());
            
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            
            AddReservationController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            
            // Show dialog and wait for user response
            dialogStage.showAndWait();
            
            // Refresh table after dialog is closed
            refreshTable();
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }

    

}