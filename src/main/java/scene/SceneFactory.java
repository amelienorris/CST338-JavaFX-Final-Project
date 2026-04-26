package scene;
import controller.*;

import database.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

public class SceneFactory {
    public static Scene create(SceneType type, Stage stage) {
        return switch(type){
            case WELCOME -> loadScene("/fxml/welcome.fxml"); // FXML used for placeholder content and UI
            case LOGIN -> loadScene("/fxml/login.fxml");
            case SIGNUP -> loadScene("/fxml/signup.fxml");
            case WIDGETS -> loadScene("/fxml/widgets.fxml"); // initialze placeholder content
            case ADMIN -> throw new IllegalStateException("Admin requires login"); // blocking admin creation, needs login first
        };

    }

    public static Scene loadUser(SceneType type, Stage stage, User user){
        return switch(type){
            case WIDGETS -> loadSceneController("/fxml/widgets.fxml",
                    (WidgetController c) -> c.setUser(user));       // loads data after log in
            case ADMIN -> { if (!user.isAdmin()) {
                throw new SecurityException(("admin not initialized"));     // admin must come through the log in
            }
            yield    loadSceneController("/fxml/admin.fxml",
                    (AdminController c) -> c.setUser(user));
            }
            default -> throw new IllegalArgumentException(
                    type + "no user data needed");  // if a scene does not need a user object, it should use create()
        };
    }

    // TODO: CREATE GUEST FOR TESTING WITHOUT ADDING DATA
    private static Scene loadScene(String path){
        URL url = SceneFactory.class.getResource(path);
        if(url == null){
            throw new IllegalArgumentException("fxml not found " + path);
        }

        try {
            FXMLLoader loader = new FXMLLoader(url);
            Parent p = loader.load();

            return new Scene(p);
        } catch (IOException o){
            throw new RuntimeException("failed to load " + path, o);
        }
    }

    // used for scenes that need incoming data
    private static <T> Scene loadSceneController(String path, Consumer<T> setup)  { // replaces manual controller loader/setup

        URL url = SceneFactory.class.getResource(path);
        if(url == null) {
            throw new IllegalArgumentException("fxml not found " + path);
        }
        try{
            FXMLLoader loader = new FXMLLoader(url);
            Parent p = loader.load();
            setup.accept(loader.getController()); // setItem() call
            return new Scene(p);
        } catch(IOException e){
            throw new RuntimeException("FXML didn't load:" + path, e);
        }

        // > `loader.load()` → constructor → `@FXML` injection → `initialize()`
        //> → `loader.getController()` → your `setItem()` call

    }
}
