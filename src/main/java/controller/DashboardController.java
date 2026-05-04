package controller;

import database.DatabaseManager;
import database.User;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import scene.SceneManager;
import scene.SceneType;

// TODO implement calender and pie chart to dashboard

public class DashboardController {
    public DatePicker calendarPicker;
    public Label calendarStatusLabel;
    @FXML private Label welcomeLabel;
    @FXML private ListView<String> taskPreviewList;

    private final DatabaseManager db = DatabaseManager.getInstance();
    private User user = User.guest();

    @FXML
    public void initialize() {
        loadGuest();
    }

    public void setUser(User user) {
        this.user = (user == null) ? User.guest() : user;

        if (this.user.getUserId() == -1) {
            loadGuest();
            return;
        }

        welcomeLabel.setText("Welcome " + this.user.getUsername());
        taskPreviewList.getItems().setAll(db.getTasks(this.user.getUserId()));
    }

    private void loadGuest() {
        welcomeLabel.setText("Welcome Guest");
        taskPreviewList.getItems().setAll(
                "Log in to sync tasks",
                "Use guest navigation for now",
                "Profile scene shows defaults"
        );
    }

    @FXML private void handleHome() { SceneManager.getInstance().navigateTo(SceneType.WELCOME);}
    @FXML private void handleWidgets() { SceneManager.getInstance().navigateToUser(SceneType.WIDGETS, user); }
    @FXML private void handleFocus() { SceneManager.getInstance().navigateTo(SceneType.FOCUS); }
    @FXML private void handleProfile() { SceneManager.getInstance().navigateTo(SceneType.PROFILE); }
    @FXML private void handleLogin() { SceneManager.getInstance().navigateTo(SceneType.LOGIN); }
}