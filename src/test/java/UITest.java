import database.DatabaseManager;
import database.User;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputControl;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;
import scene.SceneManager;
import scene.SceneType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UITest extends ApplicationTest {
    private Stage stage;

    @Override
    public void init() {
        System.setProperty("app.db.url", "jdbc:sqlite::memory:");
        DatabaseManager.resetForTesting();
        SceneManager.resetTests();
        User.clearCurrentUser();
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        DatabaseManager.getInstance();
        SceneManager.init(stage);
        SceneManager.getInstance().navigateTo(SceneType.WELCOME);
        stage.show();
        waitUI();
    }

    @AfterEach
    void teardown() {
        waitUI();
        User.clearCurrentUser();
        SceneManager.resetTests();
        DatabaseManager.resetForTesting();
    }

    @Test
    void adminLoginLoadsAdminDashboard() {
        addUser("Moises", "1234");

        login("Moises", "1234");
        waitUI();
        Label title = lookup("#titleLabel").query();
        Label currentAdmin = lookup("#currentAdminLabel").query();
        ListView<?> users = lookup("#userListView").queryListView();

        assertEquals("Admin Dashboard", title.getText());
        assertTrue(currentAdmin.getText().contains("Moises"));
        assertListViewContains(users, "Moises");
        assertTrue(User.getCurrentUser().isAdmin());
    }

    @Test
    void userLoginLoadsDashboard() {
        User user = addUser("hi", "1234");
        DatabaseManager.getInstance().insertTask(user.getUserId(),"task","UI test","2026-05-05","HIGH","None");

        login("hi", "1234");

        Label welcome = lookup("#welcomeLabel").query();
        ListView<?> tasks = lookup("#taskPreviewList").queryListView();

        assertTrue(welcome.getText().contains("hi"));
        assertListViewContains(tasks, "task");
        assertFalse(User.getCurrentUser().isAdmin());
    }

    @Test
    void guestLoginLoadsPreviewDashboard() {
        clickOn("#guestButton");
        waitUI();

        Label welcome = lookup("#welcomeLabel").query();
        ListView<?> tasks = lookup("#taskPreviewList").queryListView();

        assertTrue(welcome.getText().contains("Guest"));
        assertListViewContains(tasks, "Log in to sync tasks");
    }

    @Test
    void profileChangesAvatarAndBackground() {
        addUser("profileUser", "1234");
        login("profileUser", "1234");
        waitUI();
        clickOn("Profile");
        waitUI();

        selectBox("#pfpBox", "chikawa.png");
        selectBox("#colorBox", "Blue");
        clickOn("Back");
        waitUI();

        assertEquals("chikawa.png", User.getCurrentUser().getAvatar());
        assertEquals("blue", User.getCurrentUser().getTheme());
        assertTrue(stage.getScene().getRoot().getStyle().contains("#A8C8F4"));
    }

    @Test
    void taskAddTask(){
        addUser("taskUser", "1234");
        login("taskUser", "1234");
        waitUI();
        clickOn("Productivity");
        waitUI();

        typeIntoField("task", "#titleField");
        typeIntoField("TestFX test", "#descriptionArea");
        selectBox("#priorityBox", "HIGH");
        selectBox("#repeatBox", "None");
        waitUI();
        clickOn("Add Task"); // TODO: breaks
        waitUI();

        ListView<?> tasks = lookup("#taskListView").queryListView();
        assertListViewContains(tasks, "task");
        clickOn("Back");
        waitUI();

    }


    private User addUser(String username, String password) {
        DatabaseManager db = DatabaseManager.getInstance();
        assertTrue(db.insertUser(username, password));

        User user = db.getUser(username, password);
        assertNotNull(user);
        return user;
    }

    private void login(String username, String password) {
        clickOn("#signInButton");
        waitUI();

        typeIntoField(username, "#usernameField");
        typeIntoField(password, "#passwordField");
        clickOn("Log in");
        waitUI();

        assertNotNull(User.getCurrentUser());
        assertEquals(username, User.getCurrentUser().getUsername());
    }

    private void typeIntoField(String text, String fxmlId) {
        TextInputControl field = lookup(fxmlId).query();
        interact(() -> field.setText(text));
    }

    private void selectBox(String fxmlId, String value) {
        ComboBox<String> box = lookup(fxmlId).queryComboBox();
        interact(() -> {
            box.getSelectionModel().select(value);
            box.fireEvent(new ActionEvent(box, box));
        });
        waitUI();
    }

    private void assertListViewContains(ListView<?> list, String key) {
        boolean found = false;
        for (Object item : list.getItems()) {
            if (String.valueOf(item).contains(key)) {
                found = true;
                break;
            }
        }
        assertTrue(found, "expected: " + key + "; items: " + list.getItems());
    }


    private void waitUI() {
        WaitForAsyncUtils.waitForFxEvents();
        sleep(1400);
        WaitForAsyncUtils.waitForFxEvents();
    }
}
