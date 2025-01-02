package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RoomsController {

    @FXML
    private Button reserveButton1, reserveButton2, reserveButton3, reserveButton4, reserveButton5, reserveButton6;

    private String roomDetails = ""; // Store room details for confirmation

    @FXML
    private void onReserveButtonClicked(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();

        Stage popUpStage1 = new Stage();
        VBox vbox1 = new VBox(20);
        vbox1.getStyleClass().add("popup-container");

        if (sourceButton == reserveButton1) {
            roomDetails = "Cozy Single Room. City View.";
        } else if (sourceButton == reserveButton2) {
            roomDetails = "Standard Single Room. Garden View.";
        } else if (sourceButton == reserveButton3) {
            roomDetails = "Premium Single Room. Ocean View.";
        } else if (sourceButton == reserveButton4) {
            roomDetails = "Classic Double Room. City View.";
        } else if (sourceButton == reserveButton5) {
            roomDetails = "Deluxe Double Room. Ocean View.";
        } else if (sourceButton == reserveButton6) {
            roomDetails = "Premium Double Room. Panoramic View.";
        }

        Label roomLabel = new Label("Selected Room: " + roomDetails);
        roomLabel.getStyleClass().add("section-title");

        Label personalInfoLabel = new Label("Enter your personal information:");
        personalInfoLabel.getStyleClass().add("section-title");

        TextField nameField = new TextField();
        nameField.setPromptText("Full Name");
        nameField.getStyleClass().add("personal-info-field");

        TextField emailField = new TextField();
        emailField.setPromptText("Email Address");
        emailField.getStyleClass().add("personal-info-field");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone Number");
        phoneField.getStyleClass().add("personal-info-field");

        Label reservationLabel = new Label("Select your check-in and check-out dates:");
        reservationLabel.getStyleClass().add("label");

        DatePicker checkInDatePicker = new DatePicker(LocalDate.now());
        checkInDatePicker.setDayCellFactory(dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(empty || item.isBefore(LocalDate.now())); // Disable past dates
            }
        });

        DatePicker checkOutDatePicker = new DatePicker(LocalDate.now().plusDays(1));
        checkOutDatePicker.setDayCellFactory(dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(empty || item.isBefore(checkInDatePicker.getValue().plusDays(1))); // Disable invalid dates
            }
        });

        checkInDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                checkOutDatePicker.setValue(newValue.plusDays(1));
                checkOutDatePicker.setDayCellFactory(dp -> new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(empty || item.isBefore(newValue.plusDays(1)));
                    }
                });
            }
        });

        vbox1.getChildren().addAll(
                roomLabel, personalInfoLabel, nameField, emailField, phoneField,
                reservationLabel, checkInDatePicker, checkOutDatePicker
        );

        Button nextButton = new Button("Next");
        nextButton.getStyleClass().add("confirm-button");
        nextButton.setOnAction(e -> openPaymentPopUp(popUpStage1, checkInDatePicker.getValue(), checkOutDatePicker.getValue(), nameField, emailField, phoneField));

        vbox1.getChildren().add(nextButton);

        Scene scene = new Scene(vbox1);
        scene.getStylesheets().add(getClass().getResource("pop-up-style.css").toExternalForm());
        popUpStage1.setScene(scene);
        popUpStage1.setTitle("Room Reservation");
        popUpStage1.show();
    }

    private void openPaymentPopUp(Stage parentStage, LocalDate checkInDate, LocalDate checkOutDate, TextField nameField, TextField emailField, TextField phoneField) {
        parentStage.close();

        Stage popUpStage2 = new Stage();
        VBox vbox2 = new VBox(20);
        vbox2.getStyleClass().add("popup-container");

        Label paymentInfoLabel = new Label("Payment Information");
        paymentInfoLabel.getStyleClass().add("section-title");

        TextField cardNumberField = new TextField();
        cardNumberField.setPromptText("Card Number (1234 5678 9876 5432)");
        TextField expiryDateField = new TextField();
        expiryDateField.setPromptText("Expiry Date (MM/YY)");
        TextField cvvField = new TextField();
        cvvField.setPromptText("CVV/CVC (123)");
        TextField cardholderNameField = new TextField();
        cardholderNameField.setPromptText("Cardholder Name");
        TextField billingAddressField = new TextField();
        billingAddressField.setPromptText("Billing Address (Optional)");

        vbox2.getChildren().addAll(paymentInfoLabel, cardNumberField, expiryDateField, cvvField, cardholderNameField, billingAddressField);

        Button confirmButton = new Button("Confirm Payment");
        confirmButton.getStyleClass().add("confirm-button");
        confirmButton.setOnAction(e -> openConfirmationPopUp(popUpStage2, checkInDate, checkOutDate, nameField, emailField));

        vbox2.getChildren().add(confirmButton);

        Scene scene = new Scene(vbox2);
        scene.getStylesheets().add(getClass().getResource("pop-up-style.css").toExternalForm());
        popUpStage2.setScene(scene);
        popUpStage2.setTitle("Payment Information");
        popUpStage2.show();
    }

    private void openConfirmationPopUp(Stage parentStage, LocalDate checkInDate, LocalDate checkOutDate, TextField nameField, TextField emailField) {
        parentStage.close();

        Stage popUpStage3 = new Stage();
        VBox vbox3 = new VBox(20);
        vbox3.getStyleClass().add("popup-container");

        Label confirmationLabel = new Label("Confirm Your Reservation");
        confirmationLabel.getStyleClass().add("section-title");

        Label roomDetailsLabel = new Label("Room: " + roomDetails);
        Label checkInLabel = new Label("Check-in Date: " + checkInDate);
        Label checkOutLabel = new Label("Check-out Date: " + checkOutDate);
        Label nameLabel = new Label("Name: " + (nameField.getText().isEmpty() ? "Not Provided" : nameField.getText()));
        Label emailLabel = new Label("Email: " + (emailField.getText().isEmpty() ? "Not Provided" : emailField.getText()));

        vbox3.getChildren().addAll(confirmationLabel, roomDetailsLabel, checkInLabel, checkOutLabel, nameLabel, emailLabel);

        Button finalConfirmButton = new Button("Confirm Reservation");
        finalConfirmButton.getStyleClass().add("confirm-button");
        finalConfirmButton.setOnAction(e -> popUpStage3.close());

        vbox3.getChildren().add(finalConfirmButton);

        Scene scene = new Scene(vbox3);
        scene.getStylesheets().add(getClass().getResource("pop-up-style.css").toExternalForm());
        popUpStage3.setScene(scene);
        popUpStage3.setTitle("Reservation Confirmation");
        popUpStage3.show();
    }
}
