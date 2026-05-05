package controller;

import database.DatabaseManager;
import database.User;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import scene.SceneManager;
import scene.SceneType;

// TODO implement calender and pie chart to dashboard

public class DashboardController {
    public DatePicker calendarPicker;
    public Label calendarStatusLabel;
    @FXML private Label welcomeLabel;
    @FXML private ListView<String> taskPreviewList;
    @FXML private ImageView dashPfpImage;

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
        String avatar = this.user.getAvatar();
        if(avatar!=null){
            String path = "/pfps/" + avatar;
            try{
                Image image = new Image(getClass().getResourceAsStream(path), 180, 180, true, true);
                dashPfpImage.setImage(image); // fix for admin user profile loading differently
            } catch (Exception e){
                dashPfpImage.setImage(new Image(getClass().getResourceAsStream("/pfps/default.png")));
            }
        }
        welcomeLabel.setText("Welcome, " + this.user.getUsername());
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
    @FXML private void handleProductivity() { SceneManager.getInstance().navigateToUser(SceneType.PRODUCTIVITY, user); }
//    @FXML private void handleFocus() { SceneManager.getInstance().navigateTo(SceneType.FOCUS); }
    @FXML private void handleProfile() { SceneManager.getInstance().navigateToUser(SceneType.PROFILE, user); }
    @FXML private void handleLogin() { SceneManager.getInstance().navigateTo(SceneType.LOGIN); }
}