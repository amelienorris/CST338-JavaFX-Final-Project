import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import scene.SceneManager;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

public class SceneManagerTransitionTest {

    static {
        new JFXPanel();
    }

    private void runOnFxThread(Runnable action) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                action.run();
            } finally {
                latch.countDown();
            }
        });

        latch.await();
    }

    @Test
    void sceneManagerCanInitialize() throws Exception {
        runOnFxThread(() -> {
            Stage stage = new Stage();

            SceneManager.resetTests();
            SceneManager.init(stage);

            assertNotNull(SceneManager.getInstance());
        });
    }

    @Test
    void sceneStartsEmptyBeforeNavigation() throws Exception {
        runOnFxThread(() -> {
            Stage stage = new Stage();

            SceneManager.resetTests();
            SceneManager.init(stage);

            assertNull(stage.getScene());
        });
    }
//test
    @Test
    void fadeTransitionSceneCanHaveRootOpacityChanged() {
        VBox root = new VBox();
        Scene scene = new Scene(root, 400, 300);

        root.setOpacity(0);
        assertEquals(0, root.getOpacity());

        root.setOpacity(1);
        assertEquals(1, root.getOpacity());
    }
}