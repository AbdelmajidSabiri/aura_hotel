package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    // Root container defined in FXML
    @FXML
    private StackPane rootPane;

    // Text fields defined in FXML
    @FXML
    private TextField customerNIDField;

    @FXML
    private PasswordField customerPassField;

    /**
     * Closes the application when the close button is clicked.
     * @param event the action event triggered by the button
     */
    @FXML
    private void handleCloseButton(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Handles the login action when the "Log In" button is clicked.
     * @param event the action event triggered by the button
     */
    @FXML
    private void UserLoginn(ActionEvent event) {
        // Retrieve input values
        String nid = customerNIDField.getText();
        String password = customerPassField.getText();

        // Perform simple validation
        if (nid == null || nid.isEmpty() || password == null || password.isEmpty()) {
            showAlert(AlertType.ERROR, "Login Failed", "NID and Password cannot be empty.");
            return;
        }

        // Simulate login logic
        if (nid.equals("admin") && password.equals("admin")) {
            // Load the dashboard scene
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
                Stage stage = (Stage) rootPane.getScene().getWindow(); // Get the current stage
                stage.setScene(new Scene(loader.load()));
                stage.setTitle("Dashboard");
                stage.show();
            } catch (IOException e) {
                showAlert(AlertType.ERROR, "Error", "Unable to load dashboard.");
                e.printStackTrace();
            }
        } else {
            showAlert(AlertType.ERROR, "Login Failed", "Invalid NID or Password.");
        }
    }

    /**
     * Utility method to show an alert dialog.
     * @param alertType the type of alert (e.g., ERROR, INFORMATION)
     * @param title the title of the alert
     * @param content the message content of the alert
     */
    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}