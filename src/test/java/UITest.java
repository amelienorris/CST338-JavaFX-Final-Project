import database.DatabaseManager;
import database.User;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;
import scene.SceneManager;
import scene.SceneType;

import static org.junit.jupiter.api.Assertions.*;

public class UITest extends ApplicationTest {
    private Stage stage;

    @Override
    public void init(){
        System.setProperty("app.db.url", "jdbc:sqlite::memory:");
        DatabaseManager.resetForTesting();
        SceneManager.resetTests();
        User.clearCurrentUser();
    }

    @Override
    public void start(Stage stage){
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
    void adbimLoginLoadsAdminDashboard(){ // dashboard layout updating
        addUser("Moises", "1234");

        login("Moises", "1234");
        Label title = lookup("#titleLabel").query();
        Label currentAdmin = lookup("#currenAdminLabel").query();
        ListView<?> users = lookup("userListView").queryListView();

        assertEquals("Admin Dashboard", title.getText());
        assertTrue(currentAdmin.getText().contains("Admin: Moises"));
        assertListViewContains(users, "Moises");        // show all users list in admin dashboard
        assertTrue(User.getCurrentUser().isAdmin());
    }

    @Test
    void userLoginLoadsDashboard(){ // dashboard layout updating
        User user = addUser("hi", "1234");
        DatabaseManager.getInstance().insertTask(user.getUserId(), "task", "UI test", "2026-05-05", "HIGH", "None");

        login("hi", "1234");
        ListView<?> tasks = lookup("#taskPreviewList").queryListView();

        assertListViewContains(tasks, "task");
        assertFalse(User.getCurrentUser().isAdmin());
    }

    private void assertListViewContains(ListView<?> list, String key) {
        boolean found = false;
        for(Object i: list.getItems()){
            if(String.valueOf(i).contains(key)){
                found = true;
                break;
            }
        }
        assertTrue(found, "expected: " + key + "; items: " + list.getItems());
    }


    private User addUser(String username, String password){
        DatabaseManager db = DatabaseManager.getInstance();
        assertTrue(db.insertUser(username, password));
        User user = db.getUser(username, password);
        assertNotNull(user);
        return user;
    }
    private void login(String username, String password){
        clickOn("#signInButton");
        waitUI();
        typeIntoField(username, "#usernameField");
        typeIntoField(password, "#passwordField");
        clickOn("Log in");
        waitUI();

        assertNotNull(User.getCurrentUser());
        assertEquals(username, User.getCurrentUser().getUsername());
    }
    private void waitUI(){
        WaitForAsyncUtils.waitForFxEvents(); // pause test until ui loads
        sleep(900); // time for fade transition
        WaitForAsyncUtils.waitForFxEvents();
    }

    private void typeIntoField(String text, String fxmlID) {
        TextInputControl field = lookup(fxmlID).query();
        interact(() -> field.setText(text));
    }



}
