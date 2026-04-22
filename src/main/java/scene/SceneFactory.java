package scene;

import javafx.scene.Scene;

public class SceneFactory {
    public static Scene create(SceneType type) {
        return switch(type){
            case WELCOME -> buildWelcome();
            case LOGIN -> null;
            case SIGNUP -> null;
        };
    }

    private static Scene buildWelcome() {
        return null; // TODO
    }
}
