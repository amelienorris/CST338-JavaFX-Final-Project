package controller;

import database.User;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WidgetController {

    @FXML private ImageView pfpImage;
    @FXML private ComboBox<String> pfpBox;

    private User user;

    @FXML
    public void initialize() {
        pfpBox.getItems().addAll(
                "default.png",
                "chikawa.png",
                "hachiware1.png",
                "king.png",
                "ouchie.png",
                "pjpals.png"
        );

        if ((int)(Math.random() * 100) == 0) {
            pfpBox.getItems().add("secret.png");
            pfpBox.setValue("secret.png");
            setPfpImage("secret.png");
        } else {
            pfpBox.setValue("default.png");
            setPfpImage("default.png");
        }
    }

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    private void handlePfpChange() {
        String selected = pfpBox.getValue();

        if (selected != null) {
            setPfpImage(selected);
        }
    }

    private void setPfpImage(String fileName) {
        String path = "/pfps/" + fileName;

        if (getClass().getResource(path) == null) {
            System.out.println("Image not found: " + path);
            return;
        }
        Image image = new Image(getClass().getResource(path).toExternalForm(), 180, 180, true, true);
        if (image.isError()) {
            System.out.println("Image error: " + fileName);
            System.out.println(image.getException());
            return;
        }
        pfpImage.setImage(image);
    }
}