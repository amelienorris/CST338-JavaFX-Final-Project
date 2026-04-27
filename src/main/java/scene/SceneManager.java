package scene;

import database.User;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
    public void navigateTo(SceneType type){ // no user needed navigation
        Scene scene = cache.computeIfAbsent(type, SceneFactory::create);
        stage.setScene(scene);
    }

    public void navigateToUser (SceneType type, User user){ // logged in users, TODO: constantly asked for new data
        cache.remove(type);
        stage.setScene(SceneFactory.loadUser(type, user));
    }

    public void refresh(SceneType type){
        cache.remove(type);
        stage.setScene(SceneFactory.create(type));
    }

    public void clearAllCache(){
        cache.clear();
    }

}
