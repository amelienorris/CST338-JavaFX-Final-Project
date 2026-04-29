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
  public void handlePWChange(){ // disclaimer: in a real app we would collect phone/email to verify the user, but for now this is just for the sake of the feature
    DatabaseManager db = DatabaseManager.getInstance();
    if(confirmPassword.getText().equals(passwordField.getText())){ // make sure entered passwords match
      String username = usernameField.getText();
      String password = passwordField.getText();
      db.changePW(username, password);
      showAlert("Success! Please login using your new password.");
      SceneManager.getInstance().navigateTo(SceneType.LOGIN);
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
