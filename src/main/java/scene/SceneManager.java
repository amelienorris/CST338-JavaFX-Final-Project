package scene;

import database.User;
import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.EnumMap;
import java.util.Map;

public class SceneManager {
    private static SceneManager instance;
    private final Stage stage;
    private final Map<SceneType, Scene> cache = new EnumMap<>(SceneType.class);

    private SceneManager(Stage stage) {
        this.stage = stage;
    }

    public static void init(Stage stage) {
        if (instance == null) {
            instance = new SceneManager(stage);
        }
    }

    public static SceneManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("SceneManager not initialized");
        }
        return instance;
    }

    public void navigateTo(SceneType type) {
        Scene scene = cache.computeIfAbsent(type, SceneFactory::create);
        switchScene(scene);
    }

    public void navigateToUser(SceneType type, User user) {
        cache.remove(type);
        Scene scene = SceneFactory.loadUser(type, user);
        switchScene(scene);
    }

    public void refresh(SceneType type) {
        cache.remove(type);
        Scene scene = SceneFactory.create(type);
        switchScene(scene);
    }

    private void switchScene(Scene newScene) {
        if (stage.getScene() == null) {
            stage.setScene(newScene);

            newScene.getRoot().setOpacity(0);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(400), newScene.getRoot());
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();
            return;
        }

        Scene currentScene = stage.getScene();

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), currentScene.getRoot());
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        fadeOut.setOnFinished(e -> {
            stage.setScene(newScene);

            newScene.getRoot().setOpacity(0);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(400), newScene.getRoot());
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();
        });

        fadeOut.play();
    }

    public void clearAllCache() {
        cache.clear();
    }

    public static void resetTests() {
        instance = null;
    }
}