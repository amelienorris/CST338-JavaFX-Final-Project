package controller;

import database.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AdminController {
    @FXML private Label ittleLabel;
    private User user;

    @FXML
    public void initialize(){
        ittleLabel.setText("Admin");
    }

    public void setUser(User user){
        this.user = user;
    }
}
