package controller;
import database.DatabaseManager;
import database.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import scene.SceneManager;
import scene.SceneType;


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
    String username = usernameField.getText();
    String pw = passwordField.getText();
    User user = db.getUser(username, pw);
    if(user == null){
      error.setText("Incorrect Login.");
      usernameField.clear();
      passwordField.clear();
      return;
    } else {
      User.setCurrentUser(user);
      if(user.isAdmin()){
        SceneManager.getInstance().navigateToUser(SceneType.ADMIN, user);
      } else {
        SceneManager.getInstance().navigateToUser(SceneType.DASHBOARD, user);
      }
    }
  }
  @FXML
  private void handleBacktoSignup(){
    SceneManager.getInstance().navigateTo(SceneType.SIGNUP);
  }
  @FXML
  private void handleForgotPW(){
    SceneManager.getInstance().navigateTo(SceneType.FORGOTPW);
  }

}
