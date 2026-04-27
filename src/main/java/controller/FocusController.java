package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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
    public void initialize() {
        timer = new FocusTimer(25);
        timerLabel.setText(timer.getFormattedTime());
        timer.setOnTick(() -> {
            timerLabel.setText(timer.getFormattedTime());
        });

        timer.setOnFinish(() -> {
            timerLabel.setText("Done!");
        });
    }

    @FXML
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
        timer = new FocusTimer(25);
        timerLabel.setText(timer.getFormattedTime());
        timer.setOnTick(() -> {
            timerLabel.setText(timer.getFormattedTime());
        });
        timer.setOnFinish(() -> {
            timerLabel.setText("Done!");
        });
    }
}