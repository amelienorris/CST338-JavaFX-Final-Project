package controller;

import database.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WidgetController {
    @FXML private Label titleLabel;
    @FXML private Label timerLabel;
    //making for the timer
    private User user;
    private FocusTimer timer;

    @FXML
    public void initialize() {
        titleLabel.setText("Widgets");
        timer = new FocusTimer(25);
        timerLabel.setText(timer.getFormattedTime());
        timer.setOnTick(() -> {
            timerLabel.setText(timer.getFormattedTime());
        });
        //need to set finsih for timer
        timer.setOnFinish(() -> {
            timerLabel.setText("Done!");
        });
    }
    public void setUser(User user) {
        this.user = user;
        titleLabel.setText("hi " + user.getUsername());
    }
    @FXML
    //controller timer stuff for starting it stopping and ending it.
    private void startTimer() {
        timer.start();
    }
    @FXML
    private void pauseTimer() {
        timer.pause();
    }
    @FXML
    private void endTimer() {
        timer.stop();
        timerLabel.setText("Ended");
    }
}