package controller;

import database.DatabaseManager;
import database.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
//for textbox
import javafx.scene.control.TextField;

public class FocusController {
    private final DatabaseManager db = DatabaseManager.getInstance();
    private User currentUser = User.guest();


    @FXML
    private Label timerLabel;
    @FXML
    private Button startButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button endButton;
    //takes from FocusTimer.
    private FocusTimer timer;
    @FXML
    private TextField minutesField;

    @FXML
    public void initialize() {
        timer = new FocusTimer(25);
    }

    public void setUser(User user){
        this.currentUser = (user == null) ? User.guest(): user;

        if(this.currentUser.isGuest()){
            loadGuest();
            return;
        }

        loadUser();
    }

    private void loadGuest(){
        if(minutesField != null){
            minutesField.setPromptText("Guest timer");
        }
    }

    private void loadUser(){
        if(minutesField != null){
            minutesField.setPromptText("Timer for " + currentUser.getUsername());
        }

        // int userTime = db.getTimerDuration(user.getUserId());    // TODO: user preference
        // createTimer(userTime); new method

    }

    public void createTimer(int min){ //
        timer = new FocusTimer(min);
        timerLabel.setText(timer.getFormattedTime());
        timer.setOnTick(() -> {
            timerLabel.setText(timer.getFormattedTime());
        });

        timer.setOnFinish(() -> {
            timerLabel.setText("Done!");

            // todo: focus feature on on DatabaseManager?
        });
    }


    @FXML
    private void startTimer() {
        String input = minutesField.getText();
        if (input == null || input.isBlank()) {
            timerLabel.setText("Enter time");
            return;
        }

        int minutes;
        try {
            minutes = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            timerLabel.setText("Invalid number");
            return;
        }
        if (minutes <= 0) {
            timerLabel.setText("Must be > 0");
            return;
        }
        timer.stop();
        createTimer(minutes);
        timer.start();
    }
//test
    @FXML
    private void pauseTimer() {
        timer.pause();
    }

    @FXML
    private void endTimer() {
        timer.stop();
        createTimer(25);
    }
}