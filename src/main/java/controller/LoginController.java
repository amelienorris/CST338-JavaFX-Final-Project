package controller;
import database.DatabaseManager;
import database.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class LoginController {
  @FXML
  private TextField usernameField;
  @FXML
  private PasswordField passwordField;
  @FXML
  private Label error;

  @FXML
  private void Login(){
    DatabaseManager db = DatabaseManager.getInstance();
    String user = usernameField.getText();
    String pw = passwordField.getText();
    if(db.getUser(user, pw) == null){
      error.setText("Incorrect Login.");
      usernameField.clear();
      passwordField.clear();
      return;
    }
  }

}
