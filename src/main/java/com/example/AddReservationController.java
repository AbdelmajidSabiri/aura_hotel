// AddReservationController.java
package com.example;

import com.example.Database.RoomsDAO;
import com.example.Models.Room;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddReservationController {
    @FXML private TextField roomNumField;
    @FXML private TextField priceField;
    @FXML private ComboBox<String> typeComboBox;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private TextArea amenitiesArea;
    @FXML private ComboBox<String> statusComboBox;
    
    private Stage dialogStage;
    private final RoomsDAO roomsDAO = new RoomsDAO();

    @FXML
    public void initialize() {
        // Initialize ComboBoxes with appropriate values
        typeComboBox.getItems().addAll("Single", "Double", "Suite", "Deluxe");
        categoryComboBox.getItems().addAll("Standard", "Premium", "Luxury");
        statusComboBox.getItems().addAll("Available", "Reserved", "Occupied", "Maintenance");
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void handleSave() {
        if (validateInput()) {
            // Create the Room object using the constructor with parameters
            Room room = new Room(
                Integer.parseInt(roomNumField.getText()),
                Double.parseDouble(priceField.getText()),
                typeComboBox.getValue(),
                categoryComboBox.getValue(),
                amenitiesArea.getText(),
                statusComboBox.getValue()
            );
    
            try {
                roomsDAO.addRoom(room);  // Add room to the database
                dialogStage.close();  // Close the dialog
            } catch (Exception e) {
                showError("Error saving room", e);  // Show error if adding room fails
            }
        }
    }
    

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean validateInput() {
        String errorMessage = "";

        if (roomNumField.getText() == null || roomNumField.getText().trim().isEmpty()) {
            errorMessage += "Room number is required!\n";
        } else {
            try {
                Integer.parseInt(roomNumField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Room number must be a valid integer!\n";
            }
        }

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
}