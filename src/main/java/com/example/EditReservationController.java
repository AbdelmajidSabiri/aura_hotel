package com.example;

import com.example.Database.RoomsDAO;
import com.example.Models.Reservation;
import com.example.Models.Room;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class EditReservationController {
    @FXML private TextField roomNumField;
    @FXML private TextField priceField;
    @FXML private ComboBox<String> typeComboBox;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private TextArea amenitiesArea;
    @FXML private ComboBox<String> statusComboBox;
    @FXML private TableView<Room> tableView;

    
    private Stage dialogStage;
    private Room room;
    private final RoomsDAO roomsDAO = new RoomsDAO();
    private int rowIndex;

    @FXML
    public void initialize() {
        // Initialize ComboBoxes with appropriate values
        typeComboBox.getItems().addAll("Single", "Double", "Suite", "Deluxe");
        categoryComboBox.getItems().addAll("Standard", "Premium", "Luxury");
        statusComboBox.getItems().addAll("Available", "Reserved", "Occupied", "Maintenance");
        
        // Disable room number field as it shouldn't be editable
        roomNumField.setDisable(true);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setRoom(Room room) {
        this.room = room;
        
        // Populate fields with room data
        roomNumField.setText(String.valueOf(room.getRoomNum()));
        priceField.setText(String.valueOf(room.getPrice()));
        typeComboBox.setValue(room.getType());
        categoryComboBox.setValue(room.getCategory());
        amenitiesArea.setText(room.getAmenities());
        statusComboBox.setValue(room.getStatus());
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }


    @FXML
    private void handleSave() {
        if (validateInput()) {
            room.setPrice(Double.parseDouble(priceField.getText()));
            room.setType(typeComboBox.getValue());
            room.setCategory(categoryComboBox.getValue());
            room.setAmenities(amenitiesArea.getText());
            room.setStatus(statusComboBox.getValue());


            try {
                roomsDAO.updateRoom(rowIndex, room);
                dialogStage.close();
            } catch (Exception e) {
                showError("Error updating room at index: " + rowIndex, e);
            }
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean validateInput() {
        String errorMessage = "";

        if (priceField.getText() == null || priceField.getText().trim().isEmpty()) {
            errorMessage += "Price is required!\n";
        } else {
            try {
                Double.parseDouble(priceField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Price must be a valid number!\n";
            }
        }

        if (typeComboBox.getValue() == null) {
            errorMessage += "Type is required!\n";
        }

        if (categoryComboBox.getValue() == null) {
            errorMessage += "Category is required!\n";
        }

        if (statusComboBox.getValue() == null) {
            errorMessage += "Status is required!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please correct the following errors:");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

    private void showError(String message, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
        e.printStackTrace();
    }

    public void setReservation(Reservation selectedReservation) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setReservation'");
    }
}