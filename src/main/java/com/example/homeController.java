package com.example;

import javafx.fxml.FXML;
import javafx.scene.shape.Circle;
import javafx.scene.image.ImageView;

public class homeController {

    @FXML
    private ImageView profileImage1;

    @FXML
    private ImageView profileImage2;

    @FXML
    private ImageView profileImage3;

    public void initialize() {
        makeCircular(profileImage1);
        makeCircular(profileImage2);
        makeCircular(profileImage3);
    }

    private void makeCircular(ImageView imageView) {
        double radius = Math.min(imageView.getFitWidth(), imageView.getFitHeight()) / 2;
        Circle clip = new Circle(radius);
        clip.setCenterX(imageView.getFitWidth() / 2);
        clip.setCenterY(imageView.getFitHeight() / 2);
        imageView.setClip(clip);
    }
}

