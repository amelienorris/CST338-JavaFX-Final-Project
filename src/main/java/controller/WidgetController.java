package controller;

import database.User;
import javafx.fxml.FXML;

public class WidgetController {
    @FXML private TaskListController taskController;
//    @FXML private TimerController timerController;
//    @FXML private MusicController musicController;
//    @FXML private QuoteController quoteController;
//    @FXML private WeatherController weatherController;
    private User user;

    public void initialize(){ // placeholders

    }
    public void setUser(User user){
        this.user = user;
        if(isGuest()) return;

//        weatherController.setUser(user); // only that need user put data
//        streakController.setUser(user);
//        timerController.setUser(user);
//        taskController.setUser(user);
    }

    private boolean isGuest(){
        return user == null || user.getUserId() == -1;
    }
}
