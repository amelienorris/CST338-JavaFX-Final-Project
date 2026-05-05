package controller;

import database.User;
import javafx.fxml.FXML;
import scene.SceneManager;
import scene.SceneType;

public class ProductivityController {
  @FXML
  private FocusController timerController;
  @FXML private TaskListController taskController;

  public void setUser(User user) {
    if (timerController != null) timerController.setUser(user);
    if (taskController != null) taskController.set_user(user);
  }
  @FXML
  private void handleBack(){
    SceneManager.getInstance().navigateToUser(SceneType.DASHBOARD, User.getCurrentUser());
  }
}
