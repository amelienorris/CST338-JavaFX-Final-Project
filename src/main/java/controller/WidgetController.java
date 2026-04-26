package controller;

import database.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WidgetController {
    @FXML private Label titleLabel;

    private User user;

    @FXML
    public void initialize(){
        titleLabel.setText("Widgets");
    }


    public void setUser(User user) {
        this.user = user;
        titleLabel.setText("hi" + user.getUsername());
    }
}
