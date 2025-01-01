package com.example;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class dashboardController {

    @FXML
    private BorderPane rootPane;
    private String currentPage = "";

    // Reference all icons
    @FXML
    private FontAwesomeIcon homeIcon;
    @FXML
    private FontAwesomeIcon listIcon;
    @FXML
    private FontAwesomeIcon imageIcon;
    @FXML
    private FontAwesomeIcon settingsIcon;
    @FXML
    private FontAwesomeIcon plusIcon;

    // Handle icon click events
    @FXML
    private void handleIconClick(javafx.scene.input.MouseEvent event) {
        // Reset all icons to default color (grey)
        resetIconColors();

        // Highlight the clicked icon (blue)
        FontAwesomeIcon clickedIcon = (FontAwesomeIcon) event.getSource();
        clickedIcon.setFill(Color.valueOf("#1b27ff")); // Blue color

        if (clickedIcon == listIcon) {
            loadPage("reservList.fxml"); // Load the reservList page
        }

        if (clickedIcon == homeIcon) {
            loadPage("dashboard.fxml"); // Load the reservList page
        }
    }

    // Reset all icons to grey
    private void resetIconColors() {
        homeIcon.setFill(Color.GREY);
        listIcon.setFill(Color.GREY);
        imageIcon.setFill(Color.GREY);
        settingsIcon.setFill(Color.GREY);
        plusIcon.setFill(Color.GREY);
    }
    @FXML
    private void onMouseEntered(MouseEvent event) {
        FontAwesomeIcon icon = (FontAwesomeIcon) event.getSource();
        icon.setCursor(Cursor.HAND);  // Change cursor to hand
        icon.setScaleX(1.2);  // Increase size
        icon.setScaleY(1.2);  // Increase size
    }

    // Reset cursor and size when mouse exits
    @FXML
    private void onMouseExited(MouseEvent event) {
        FontAwesomeIcon icon = (FontAwesomeIcon) event.getSource();
        icon.setCursor(Cursor.DEFAULT);  // Reset cursor
        icon.setScaleX(1.0);  // Reset size
        icon.setScaleY(1.0);  // Reset size
    }

    private void loadPage(String fxmlFile) {
        try {
            // If the new page is dashboard, load it as the entire content of rootPane, not just center
            if (fxmlFile.equals("dashboard.fxml")) {
                // Check if the current page is already the dashboard
                if (!currentPage.equals("dashboard.fxml")) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                    Parent newPage = loader.load();
                    rootPane.getChildren().setAll(newPage);  // Replace the entire rootPane content with the new page
                    currentPage = "dashboard.fxml"; // Update the current page
                }
            } else {
                // For other pages, set them as the center content of rootPane
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                Parent newPage = loader.load();
                rootPane.setCenter(newPage);  // Set the new page as the center content
                currentPage = fxmlFile; // Update the current page
            }
        } catch (Exception e) {
            e.printStackTrace();  // Handle exceptions properly in your real application
        }
    }
    
}

