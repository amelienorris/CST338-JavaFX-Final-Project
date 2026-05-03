package controller;

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
        timer = new FocusTimer(minutes);

        timer.setOnTick(() -> timerLabel.setText(timer.getFormattedTime()));
        timer.setOnFinish(() -> timerLabel.setText("Done!"));

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