package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import java.io.IOException;

import javafx.scene.Node;
import javafx.scene.Parent;

public class homeController {

    @FXML private Button adminButton;
    @FXML private ImageView profileImage1;
    @FXML private ImageView profileImage2;
    @FXML private ImageView profileImage3;
    @FXML private BorderPane rootPane; // Change to BorderPane since that's the root in home.fxml


    @FXML
    private StackPane somePane;

    public void initialize() {
        makeCircular(profileImage1);
        makeCircular(profileImage2);
        makeCircular(profileImage3);
    }

    private void makeCircular(ImageView imageView) {
        if (imageView != null) {
            double radius = Math.min(imageView.getFitWidth(), imageView.getFitHeight()) / 2;
            Circle clip = new Circle(radius);
            clip.setCenterX(imageView.getFitWidth() / 2);
            clip.setCenterY(imageView.getFitHeight() / 2);
            imageView.setClip(clip);
        }
    }

    @FXML
    private void handleAdminButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserLogin.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Admin Login");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current stage
            Stage currentStage = (Stage) adminButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            System.err.println("Error loading UserLogin.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    private void handleReserveButton2(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Rooms.fxml"));
            Parent roomsPage = loader.load();
            
            // Get the stage from the event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            // Create new scene and set it on the existing stage
            Scene scene = new Scene(roomsPage);
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException e) {
            System.err.println("Error loading Rooms.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
