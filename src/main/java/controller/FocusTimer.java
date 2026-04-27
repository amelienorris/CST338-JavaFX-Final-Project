package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class FocusTimer {

    private int secondsLeft;
    //what is timeline?
    //timeline is a varaible used in java, and it makes the clock function
    //i used this as it keeps the clock ticking, it runs code for however long I tell it too in the varaible

    private Timeline timeline;
    //runnable allows us to run code later
    //only when .run is called it runs
    private Runnable onTick;
    private Runnable onFinish;

    public FocusTimer(int minutes) {
        this.secondsLeft = minutes * 60;

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            secondsLeft--;

            if (onTick != null) {
                onTick.run();
            }

            if (secondsLeft <= 0) {
                stop();

                if (onFinish != null) {
                    onFinish.run();
                }
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
    }
    public void start() {
        timeline.play();
    }

    public void pause() {
        timeline.pause();
    }

    public void stop() {
        timeline.stop();
    }

    public int getSecondsLeft() {
        return secondsLeft;
    }
    public String getFormattedTime() {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public void setOnTick(Runnable onTick) {
        this.onTick = onTick;
    }

    public void setOnFinish(Runnable onFinish) {
        this.onFinish = onFinish;
    }
}