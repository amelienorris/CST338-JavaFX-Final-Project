import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import scene.SceneManager;
import scene.SceneType;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class WelcomeFlowTest extends ApplicationTest {

    @Override
    public void start(Stage stage) {
        SceneManager.resetTests();
        SceneManager.init(stage);
        SceneManager.getInstance().navigateTo(SceneType.WELCOME);
        stage.show();
    }

    @Test
    void clickSignIn() {
        clickOn("Sign In");
        assertDoesNotThrow(() -> lookup("Sign In").query());
    }

    @Test
    void clickSignUp() {
        clickOn("Sign Up");
        assertDoesNotThrow(() -> lookup("Sign Up").query());
    }

    @Test
    void clickGuest() { //passed!
        clickOn("Guest");
        assertDoesNotThrow(() -> lookup("Add Task").query());
    }
}