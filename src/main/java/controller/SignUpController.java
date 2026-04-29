package controller;
import database.DatabaseManager;
import database.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import scene.SceneManager;
import scene.SceneType;


public class SignUpController {
  @FXML
  private TextField usernameField;
  @FXML
  private PasswordField passwordField;
  @FXML
  private PasswordField confirmPasswordField;
  @FXML
  private Label error;

  @FXML
  private void Signup(){
    String user = usernameField.getText();
    String pw = passwordField.getText();
    String match = confirmPasswordField.getText();
    if(!pw.equals(match)){
      error.setText("Passwords do not match.");
      passwordField.clear();
      confirmPasswordField.clear();
      return;
    }
    DatabaseManager db = DatabaseManager.getInstance();
    boolean signup = false;
    if(db.insertUser(user, pw)){
      showAlert("Account created! Please log in!");
      SceneManager.getInstance().navigateTo(SceneType.LOGIN);
    } else {
      error.setText("Username is already taken.");
      usernameField.clear();
    }
  }
  private void handleBacktoLogin(){
    SceneManager.getInstance().navigateTo(SceneType.LOGIN);
  }

  private void showAlert(String message) {
    Alert alert =  new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Signup");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

}
