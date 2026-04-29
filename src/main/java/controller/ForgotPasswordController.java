package controller;
import database.DatabaseManager;
import database.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import scene.SceneManager;
import scene.SceneType;

public class ForgotPasswordController {
  @FXML
  private TextField usernameField;
  @FXML
  private PasswordField passwordField;
  @FXML
  private PasswordField confirmPassword;
  @FXML
  public void handlePWChange(){
    DatabaseManager db = DatabaseManager.getInstance();
    if(passwordField.equals(confirmPassword)){ // make sure entered passwords match
      String username = usernameField.getText();
      String password = passwordField.getText();
      db.changePW(username, password);
    } else {
      showAlert("Passwords do not match!");
    }
  }
  private void showAlert(String message) {
    Alert alert =  new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Signup");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

}
