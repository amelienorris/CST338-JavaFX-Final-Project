package controller;

import database.User;
import javafx.fxml.FXML;

public class WidgetController {
    @FXML private TaskListController taskController;
//    @FXML private TimerController timerController;
//    @FXML private MusicController musicController;
//    @FXML private QuoteController quoteController;
//    @FXML private WeatherController weatherController;
    private User currentUser = User.guest();


    public void initialize(){ // placeholders
        loadGuest();
    }
    public void setUser(User user){
        this.currentUser = (user == null) ? User.guest() : user;

        if(this.currentUser.isGuest()){
            loadGuest();
            return;
        }
        loadUser();
    }

    private void loadGuest(){ // TODO load info, guest vs user
        //        weatherController.setUser(user); // only that need user put data
//        streakController.setUser(user);
//        timerController.setUser(user);
//        taskController.setUser(user);
    }

    private void loadUser(){
        //        weatherController.setUser(user); // only that need user put data
//        streakController.setUser(user);
//        timerController.setUser(user);
//        taskController.setUser(user);
    }



}
