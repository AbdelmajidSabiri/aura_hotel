package com.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import com.example.Database.ReservationsDAO;
import com.example.Models.Reservation;
import com.gluonhq.charm.glisten.control.Alert.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class ReservationsController {

    @FXML private TableView<Reservation> tableView;
    @FXML private TableColumn<Reservation, String> colFullname;
    @FXML private TableColumn<Reservation, String> colEmail;
    @FXML private TableColumn<Reservation, String> colPhoneNumber;
    @FXML private TableColumn<Reservation, String> colCheckIn;
    @FXML private TableColumn<Reservation, String> colCheckOut;
   
    private final ObservableList<Reservation> ReservationList = FXCollections.observableArrayList();
    private final ReservationsDAO ReservationsDAO = new ReservationsDAO();

    @FXML private Button addButton;
    @FXML private Button editButton;
    @FXML private Button deleteButton;
    @FXML private Button refreshButton;
    @FXML private Button exportButton;

    @FXML
    public void initialize() {
        try {
            System.out.println("Initializing ReserveListController...");
            
            if (tableView == null || colFullname == null || colEmail == null || 
                colPhoneNumber == null || colCheckIn == null || colCheckOut == null) {
                throw new RuntimeException("FXML components not properly injected!");
            }

            colFullname.setCellValueFactory(new PropertyValueFactory<>("fullName"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            colCheckIn.setCellValueFactory(new PropertyValueFactory<>("CheckIn"));
            colCheckOut.setCellValueFactory(new PropertyValueFactory<>("CheckOut"));

            // Configure the TableView
            tableView.setItems(ReservationList);
            
            // Enable table columns reordering and resizing
            tableView.setTableMenuButtonVisible(true);
            
            // Load the initial data
            loadReservations();
            
            addButton.setOnAction(event -> handleAddReservation());
            editButton.setOnAction(event -> handleEditReservation());
            deleteButton.setOnAction(event -> handleDeleteReservation());
            refreshButton.setOnAction(event -> refreshTable());
            exportButton.setOnAction(event -> handleExportToCsv());





            System.out.println("Controller initialization completed.");
        } catch (Exception e) {
            System.err.println("Error during controller initialization: " + e.getMessage());
            e.printStackTrace();
        }

        
    }

    private void loadReservations() {
        try {
            System.out.println("Loading reservations...");
            ReservationsDAO ReservationsDAO = new ReservationsDAO();
            List<Reservation> Reservations = ReservationsDAO.getAllReservations();
            
            System.out.println("Fetched " + Reservations.size() + " reservations from database");
            
            // Clear and add all reservations
            ReservationList.clear();
            ReservationList.addAll(Reservations);
            
            // Force the table to refresh
            tableView.getColumns().get(0).setVisible(false);
            tableView.getColumns().get(0).setVisible(true);
            
            System.out.println("Added " + ReservationList.size() + " reservations to table");
            
            // Print first reservation details to verify data
            if (!Reservations.isEmpty()) {
                Reservation firstReservation = Reservations.get(0);
                System.out.println("First reservation details:");
                System.out.println("full name: " + firstReservation.getFullName());
                System.out.println("email: " + firstReservation.getEmail());
                System.out.println("phone num: " + firstReservation.getPhoneNumber());
            }
        } catch (Exception e) {
            System.err.println("Error loading reservations: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void refreshTable() {
        loadReservations();
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
            
            AddReservationController2 controller = loader.getController();
            controller.setDialogStage(dialogStage);
            
            // Show dialog and wait for user response
            dialogStage.showAndWait();
            
            // Refresh table after dialog is closed
            refreshTable();
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }

    @FXML
    private void handleEditReservation() {
        Reservation selectedReservation = tableView.getSelectionModel().getSelectedItem();
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();


        if (selectedReservation == null) {
            showAlert("Please select a reservation to edit.", null);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/EditReservation.fxml"));
            Parent root = loader.load();
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Reservation");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(tableView.getScene().getWindow());
            
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            
            EditReservationController2 controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setReservation(selectedReservation);
            controller.setRowIndex(selectedIndex); // Pass the selected row index

            
            dialogStage.showAndWait();
            
            refreshTable();
        } catch (IOException e) {
            System.out.println("error");
        }
    }


    @FXML
    private void handleDeleteReservation() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            showAlert("Please select a reservation to delete.", null);
            return;
        }
    
        try {
            ReservationsDAO.deleteReservation(selectedIndex);
            refreshTable();

        } catch (Exception e) {
            System.out.println("Error delete");
        }
    }
    

    @FXML
    private void handleExportToCsv() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save CSV File");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );

        File file = fileChooser.showSaveDialog(tableView.getScene().getWindow());
        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                // Write header
                writer.write("Reservation Number,Price,Type,CheckIn,Amenities,Status\n");
                
                // Write data
                for (Reservation reservation : ReservationList) {
                    writer.write(String.format("%d,%f,%s,%s,%s,%s\n",
                        reservation.getFullName(),
                        reservation.getPhoneNumber(),
                        escapeCsv(reservation.getEmail()),
                        escapeCsv(reservation.getCheckIn()),
                        escapeCsv(reservation.getCheckOut())
                    ));
                }
                
                showAlert("Data exported successfully!", Alert.AlertType.INFORMATION);
            } catch (IOException e) {
                System.out.println("Error exporting data to CSV");
            }
        }
    }

    private String escapeCsv(String value) {
        if (value == null) return "";
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }







    private void showAlert(String message, AlertType warning) {
        showAlert(message, Alert.AlertType.WARNING);
    }

    

}