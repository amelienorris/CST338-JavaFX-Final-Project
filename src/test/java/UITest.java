import database.DatabaseManager;
import database.User;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;
import scene.SceneFactory;
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

    // TODO: ALL DASHBOARDS ARE UPDATING
    @Test
    void adbimLoginLoadsAdminDashboard(){ // dashboard layout updating
        addUser("Moises", "1234");

        login("Moises", "1234");
        waitUI();
        Label title = lookup("#titleLabel").query();
        Label currentAdmin = lookup("#currenAdminLabel").query();
        ListView<?> users = lookup("userListView").queryListView();

        assertEquals("Admin Dashboard", title.getText());
        assertTrue(currentAdmin.getText().contains("Moises"));
        assertListViewContains(users, "Moises");        // show all users list in admin dashboard
        assertTrue(User.getCurrentUser().isAdmin());
    }

    @Test
    void userLoginLoadsDashboard(){ // dashboard layout updating
        User user = addUser("hi", "1234");
        DatabaseManager.getInstance().insertTask(user.getUserId(), "task", "UI test", "2026-05-05", "HIGH", "None");

        login("hi", "1234");
        waitUI();
        Label welcome = lookup("#welcomeLabel").query();
        ListView<?> tasks = lookup("#taskPreviewList").queryListView();

        assertListViewContains(tasks, "task");
        assertFalse(User.getCurrentUser().isAdmin());
        assertTrue(welcome.getText().contains("hi"));

    }

    @Test
    void guestLoginLoadsPreviewDashboard(){
        clickOn("#guestButton");

        waitUI();
        Label welcome = lookup("#welcomeLabel").query();
        ListView<?> tasks = lookup("#taskPreviewList").queryListView();
        assertTrue(welcome.getText().contains("Guest"));

        assertListViewContains(tasks, "Log in to sync tasks");
    }

    @Test
    void changeAvatarAndBackground(){
        addUser("widget", "1234");
        login("widget", "1234");
        waitUI();
        clickOn("widgets");
        waitUI();

        selectPref("#pfpBox", "chikawa.png");
        selectPref("#colorBox", "Blue");

        assertEquals("chikawa.png", User.getCurrentUser().getAvatar());
        assertEquals("blue", User.getCurrentUser().getTheme());

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
    private void selectPref(String fxmlID, String value){
        ComboBox<String> box = lookup(fxmlID).queryComboBox();
        interact(() -> {
            box.getSelectionModel().select(value);
            box.fireEvent(new ActionEvent(box, box));
        });
        waitUI();
    }



}
