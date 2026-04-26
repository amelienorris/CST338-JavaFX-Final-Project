package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import scene.SceneManager;
import scene.SceneType;

public class WelcomeController {
    @FXML
    private Label subtitleLabel;

    @FXML
    public void initialize(){
        subtitleLabel.setText("hi");

    }

    @FXML
    private void handleSignIn(){
        SceneManager.getInstance().navigateTo(SceneType.LOGIN);
    }

    @FXML
    private void handleSignUp(){
        SceneManager.getInstance().navigateTo(SceneType.SIGNUP);
    }
}
