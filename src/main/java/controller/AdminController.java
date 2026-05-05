package controller;

import database.DatabaseManager;
import database.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import scene.SceneManager;
import scene.SceneType;

public class AdminController {
    @FXML private Label ittleLabel;
    private User user;
    @FXML private Label currentAdminLabel;
    @FXML private ListView<String> whitelist;

    @FXML
    public void initialize(){
        ittleLabel.setText("Admin Dashboard");
        currentAdminLabel.setText("No admin loaded");
        whitelist.getItems().setAll(DatabaseManager.getWhitelist());
    }

    public void setUser(User user){
        if(user == null || !user.isAdmin()){
            throw new SecurityException("admin scene requires admin user");
        }
        this.user = user;
        User.setCurrentUser(user);
        currentAdminLabel.setText("Admin: "  + user.getUsername());
    }

    @FXML
    private void handleDashboard(){
        if(user == null){
            user = User.getCurrentUser();
        }
        SceneManager.getInstance().navigateToUser(SceneType.DASHBOARD, user);
    }

    @FXML
    private void handleLogout(){
        User.clearCurrentUser();
        SceneManager.getInstance().clearAllCache();
        SceneManager.getInstance().navigateTo(SceneType.WELCOME);
    }
}
