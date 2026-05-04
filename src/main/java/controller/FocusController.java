package controller;

import database.DatabaseManager;
import database.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
//for textbox
import javafx.scene.control.TextField;

public class FocusController {
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
    private Label sessionCountLabel;
    private int sessionMinutes = 0;

    @FXML
    public void initialize() {
        timer = new FocusTimer(25);
        timerLabel.setText(timer.getFormattedTime());
        timer.setOnTick(() -> {
            timerLabel.setText(timer.getFormattedTime());
        });

        timer.setOnFinish(() -> {
            timerLabel.setText("Done!");
        });
        refreshStats();
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
        sessionMinutes = minutes;
        timer = new FocusTimer(minutes);

        timer.setOnTick(() -> timerLabel.setText(timer.getFormattedTime()));
        timer.setOnFinish(() -> {timerLabel.setText("Done!"); saveSession(true); refreshStats();}); //if timer ends, update stats

        timerLabel.setText(timer.getFormattedTime());
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
        saveSession(true);
        refreshStats();
        timer = new FocusTimer(25);
        timerLabel.setText(timer.getFormattedTime());
        timer.setOnTick(() -> {
            timerLabel.setText(timer.getFormattedTime());
        });
        timer.setOnFinish(() -> {
            timerLabel.setText("Done!");
        });
    }
    private void saveSession(boolean completed){
        User user = User.getCurrentUser();
        if(user.getUserId()== -1){
            return;
        }
        DatabaseManager.getInstance().insertFocus(user.getUserId(), 0, sessionMinutes, true);
    }
    private void refreshStats(){
        User user = User.getCurrentUser();
        if(user.getUserId()== -1 || user ==null){
            return;
        }
        int count = DatabaseManager.getInstance().getSesssionCount(user.getUserId());
        sessionCountLabel.setText("Focus sessions completed: " + count);
    }

    public void setUser(User user) {
        refreshStats();
    }
}