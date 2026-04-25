package scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SceneFactory {
    public static Scene create(SceneType type, Stage stage) {
        return switch(type){
            case WELCOME -> loadScene("resources/fxml/welcome.fxml"); // paths may be wrong
            case LOGIN -> loadScene("resources/fxml/login.fxml");
            case SIGNUP -> loadScene("resources/fxml/signup.fxml"); // controllers here
            case WIDGETS -> loadScene("resources/fxml/widgets.fxml");
            case ADMIN -> loadScene("resources/fxml/admin.fxml");
        };
    }

    // TODO, add controllers to scenes that need initialisation data
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
}
