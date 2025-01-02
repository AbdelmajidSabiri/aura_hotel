package com.example;

import com.example.Database.ReservationsDAO;
import com.example.Models.Reservation;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;

public class EditReservationController2 {
    @FXML private TextField fullnameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneNumberField;
    @FXML private DatePicker checkInPicker;
    @FXML private DatePicker checkOutPicker;
    
    private Stage dialogStage;
    private Reservation reservation;
    private final ReservationsDAO reservationsDAO = new ReservationsDAO();
    private int rowIndex;

    @FXML
    public void initialize() {
        // Add listener to ensure checkout date is after checkin date
        checkInPicker.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (checkOutPicker.getValue().isBefore(newVal)) {
                checkOutPicker.setValue(newVal.plusDays(1));
            }
        });
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
        

        // Populate fields with reservation data
        fullnameField.setText(reservation.getFullName());
        emailField.setText(reservation.getEmail());
        phoneNumberField.setText(reservation.getPhoneNumber());
        checkInPicker.setValue(checkInPicker.getValue());
        checkOutPicker.setValue(checkOutPicker.getValue());
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    @FXML
    private void handleSave() {
        if (validateInput()) {

            
            String checkInString = checkInPicker.getValue().toString();
            String checkOutString = checkOutPicker.getValue().toString();

            reservation.setFullName(fullnameField.getText());
            reservation.setEmail(emailField.getText());
            reservation.setPhoneNumber(phoneNumberField.getText());
            reservation.setCheckIn(checkInString);
            reservation.setCheckOut(checkOutString);

            try {
                reservationsDAO.updateReservation(rowIndex, reservation);
                dialogStage.close();
            } catch (Exception e) {
                showError("Error updating reservation at index: " + rowIndex, e);
            }
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean validateInput() {
        String errorMessage = "";

        if (fullnameField.getText() == null || fullnameField.getText().trim().isEmpty()) {
            errorMessage += "Full name is required!\n";
        }

        if (emailField.getText() == null || emailField.getText().trim().isEmpty()) {
            errorMessage += "Email is required!\n";
        } else if (!emailField.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errorMessage += "Invalid email format!\n";
        }

        if (phoneNumberField.getText() == null || phoneNumberField.getText().trim().isEmpty()) {
            errorMessage += "Phone number is required!\n";
        }

        if (checkInPicker.getValue() == null) {
            errorMessage += "Check-in date is required!\n";
        }

        if (checkOutPicker.getValue() == null) {
            errorMessage += "Check-out date is required!\n";
        }

        if (checkInPicker.getValue() != null && checkOutPicker.getValue() != null) {
            if (checkOutPicker.getValue().isBefore(checkInPicker.getValue())) {
                errorMessage += "Check-out date must be after check-in date!\n";
            }
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