import controller.WidgetController;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class PfpSystemTest {

    static {
        new JFXPanel();
    }

    @Test
    void defaultPfpLoads() throws Exception {
        WidgetController controller = new WidgetController();

        ImageView imageView = new ImageView();
        ComboBox<String> comboBox = new ComboBox<>();

        Field pfpImageField = WidgetController.class.getDeclaredField("pfpImage");
        pfpImageField.setAccessible(true);
        pfpImageField.set(controller, imageView);

        Field pfpBoxField = WidgetController.class.getDeclaredField("pfpBox");
        pfpBoxField.setAccessible(true);
        pfpBoxField.set(controller, comboBox);

        controller.initialize();

        assertEquals("default.png", comboBox.getValue());
        assertNotNull(imageView.getImage());
    }

    @Test
    void pfpListContainsCorrectImages() throws Exception {
        WidgetController controller = new WidgetController();

        ImageView imageView = new ImageView();
        ComboBox<String> comboBox = new ComboBox<>();

        Field pfpImageField = WidgetController.class.getDeclaredField("pfpImage");
        pfpImageField.setAccessible(true);
        pfpImageField.set(controller, imageView);

        Field pfpBoxField = WidgetController.class.getDeclaredField("pfpBox");
        pfpBoxField.setAccessible(true);
        pfpBoxField.set(controller, comboBox);

        controller.initialize();

        assertTrue(comboBox.getItems().contains("default.png"));
        assertTrue(comboBox.getItems().contains("chikawa.png"));
        assertTrue(comboBox.getItems().contains("hachiware1.png"));
        assertTrue(comboBox.getItems().contains("king.png"));
        assertTrue(comboBox.getItems().contains("ouchie.png"));
        assertTrue(comboBox.getItems().contains("pjpals.png"));
    }

    @Test
    void changingPfpUpdatesImage() throws Exception {
        WidgetController controller = new WidgetController();

        ImageView imageView = new ImageView();
        ComboBox<String> comboBox = new ComboBox<>();

        Field pfpImageField = WidgetController.class.getDeclaredField("pfpImage");
        pfpImageField.setAccessible(true);
        pfpImageField.set(controller, imageView);

        Field pfpBoxField = WidgetController.class.getDeclaredField("pfpBox");
        pfpBoxField.setAccessible(true);
        pfpBoxField.set(controller, comboBox);

        controller.initialize();

        comboBox.setValue("king.png");

        Method method = WidgetController.class.getDeclaredMethod("handlePfpChange");
        method.setAccessible(true);
        method.invoke(controller);

        assertNotNull(imageView.getImage());
        assertEquals("king.png", comboBox.getValue());
    }
}