package scene;

import database.DatabaseManager;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.EnumMap;
import java.util.Map;

public class SceneManager {
    private static SceneManager instance;
    private final Stage stage;
    private final Map<SceneType, Scene> cache = new EnumMap<>(SceneType.class); // scene caching, DAC_scene_manager.pptx,

    private SceneManager(Stage stage) {
        this.stage = stage;

    }

    public static void init(Stage stage, DatabaseManager db) {
        if(instance == null) {
            instance = new SceneManager(stage);
        }
    }

    public static SceneManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("SceneManager not initialized");
        }
        return instance;
    }
    public void navigateTo(SceneType type){
        Scene scene = cache.computeIfAbsent(type, SceneFactory::create);
        stage.setScene(scene);
    }
}
