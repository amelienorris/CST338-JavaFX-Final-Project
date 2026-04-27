package controller;
import database.DatabaseManager;
import database.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;


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
      // nav back to login page
    } else {
      error.setText("Username is already taken.");
      usernameField.clear();
    }
  }

  private void showAlert(String message) {
    Alert alert =  new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Task List");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

}
