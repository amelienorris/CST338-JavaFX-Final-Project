package controller;

import database.User;
import javafx.fxml.FXML;

public class ProductivityController {
  @FXML
  private FocusController timerController;
  @FXML private TaskListController taskController;

  public void setUser(User user) {
    if (timerController != null) timerController.setUser(user);
    if (taskController != null) taskController.set_user(user);
  }
}
