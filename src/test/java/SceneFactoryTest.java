import javafx.scene.Scene;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import scene.SceneFactory;
import scene.SceneType;

import static org.junit.jupiter.api.Assertions.*;

public class SceneFactoryTest extends ApplicationTest {
    @Test
    void welcomeSceneLoads(){
        Scene scene = SceneFactory.create(SceneType.WELCOME);

        assertNotNull(scene);
        assertNotNull(scene.getRoot());
    }

    @Test
    void loginSceneLoads(){
        Scene scene = SceneFactory.create(SceneType.LOGIN);

        assertNotNull(scene);
        assertNotNull(scene.getRoot());
    }

    @Test
    void signupSceneLoads(){
        Scene scene = SceneFactory.create(SceneType.SIGNUP);

        assertNotNull(scene);
        assertNotNull(scene.getRoot());
    }

    @Test
    void dashboardLoads(){
        Scene scene = SceneFactory.create(SceneType.DASHBOARD);

        assertNotNull(scene);
        assertNotNull(scene.getRoot());
    }

    @Test
    void widgetsSceneLoads(){
        Scene scene = SceneFactory.create(SceneType.WIDGETS);

        assertNotNull(scene);
        assertNotNull(scene.getRoot());
    }

    @Test
    void focusSceneLoads(){
        Scene scene = SceneFactory.create(SceneType.FOCUS);

        assertNotNull(scene);
        assertNotNull(scene.getRoot());
    }

    @Test
    void profileSceneLoads(){
        Scene scene = SceneFactory.create(SceneType.PROFILE);

        assertNotNull(scene);
        assertNotNull(scene.getRoot());
    }

    @Test
    void forgotpwSceneLoads(){
        Scene scene = SceneFactory.create(SceneType.FORGOTPW);

        assertNotNull(scene);
        assertNotNull(scene.getRoot());
    }

    @Test
    void adminSceneNotLoadsWithoutLogin(){
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> SceneFactory.create(SceneType.ADMIN)
        );

        assertEquals("Admin requires login", ex.getMessage());
    }
}
