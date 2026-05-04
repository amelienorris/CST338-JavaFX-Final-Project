package scene;
import controller.*;

import database.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

public class SceneFactory {
    public static Scene create(SceneType type) {
        return switch(type){
            case WELCOME -> loadScene("/fxml/welcome.fxml"); // FXML used for placeholder content and UI
            case LOGIN -> loadScene("/fxml/login.fxml");
            case SIGNUP -> loadScene("/fxml/signup.fxml");
            case DASHBOARD -> loadScene("/fxml/dashboard.fxml");
            case WIDGETS -> loadScene("/fxml/widgets.fxml"); // initialze placeholder content
            case FOCUS -> loadScene("/fxml/focus.fxml");
            case PROFILE -> loadScene("/fxml/profile");
            case FORGOTPW -> loadScene("/fxml/forgotpw.fxml");
            case ADMIN -> throw new IllegalStateException("Admin requires login"); // blocking admin creation, needs login first
        };

    }

    public static Scene loadUser(SceneType type, User user){
        return switch(type){
            case DASHBOARD -> loadSceneController("/fxml/dashboard.fxml",
                    (DashboardController c) -> c.setUser(user));
            case WIDGETS -> loadSceneController("/fxml/widgets.fxml",
                    (WidgetController c) -> c.setUser(user));       // loads data after log in
            case FOCUS -> loadSceneController("/fxml/focus.fxml",
                    (FocusController c) -> c.setUser(user));
            case PROFILE -> loadSceneController("/fxml/profile.fxml",
                    (ProfileController c) -> c.setUser(user));
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
    private static final double WIDTH = 800;
    private static final double HEIGHT = 600;
    // TODO: CREATE GUEST FOR TESTING WITHOUT ADDING DATA
    private static String themeColor(){
        User user = User.getCurrentUser();
        if(user==null){
            return "#F4A8B5";
        }
        return switch(user.getTheme()){
            case "blue" -> "#A8C8F4";
            case "green" -> "#A8F4C0";
            default -> "#F4A8B5";
        };
    }
    private static Scene loadScene(String path){
        URL url = SceneFactory.class.getResource(path);
        if(url == null){
            throw new IllegalArgumentException("fxml not found " + path);
        }

        try {
            FXMLLoader loader = new FXMLLoader(url);
            Parent p = loader.load();
            p.setStyle("-fx-background-color: " + themeColor() + ";"); // use theme grabber to get the user's preferred scene color
            return new Scene(p, WIDTH, HEIGHT); // make all scenes the same dimensions
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
            p.setStyle("-fx-background-color: " + themeColor() + ";");
            setup.accept(loader.getController()); // setItem() call
            return new Scene(p, WIDTH, HEIGHT); // include standard w/h
        } catch(IOException e){
            throw new RuntimeException("FXML didn't load:" + path, e);
        }

        // > `loader.load()` → constructor → `@FXML` injection → `initialize()`
        //> → `loader.getController()` → your `setItem()` call

    }
}
