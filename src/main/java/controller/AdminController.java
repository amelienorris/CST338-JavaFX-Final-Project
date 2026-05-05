package controller;

import database.DatabaseManager;
import database.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import scene.SceneManager;
import scene.SceneType;

public class AdminController {
    @FXML private Label titleLabel;
    private User user;
    @FXML private Label currentAdminLabel;

    @FXML private ListView<String> userListView;

    private final DatabaseManager db = DatabaseManager.getInstance();

    @FXML
    public void initialize(){
        titleLabel.setText("Admin Dashboard");
        userListView.setPlaceholder(new Label("No users"));
    }

    public void setUser(User user){
        if(user == null || !user.isAdmin()){
            throw new SecurityException("admin scene requires admin user");
        }
        this.user = user;
        User.setCurrentUser(user);
        currentAdminLabel.setText("Admin: "  + user.getUsername());
        refreshList();
    }


    private void refreshList() {
        userListView.getItems().setAll(db.getAllUsers());
    }

    @FXML
    private void handleDashboard(){
        SceneManager.getInstance().navigateToUser(SceneType.DASHBOARD, user);
    }

}
