import database.DatabaseManager;
import database.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
  @BeforeEach
  void freshDb() {
    System.setProperty("app.db.url", "jdbc:sqlite::memory:"); // refactored db tests & databasemanager to use memory for testing
    DatabaseManager.resetForTesting();
  }
  @AfterEach
  void teardown() {
    DatabaseManager.resetForTesting();
  }
  @Test
      void testCurrentUser(){
      DatabaseManager db = DatabaseManager.getInstance();
      db.insertUser("Chiikawa", "123");
      User test = db.getUser("Chiikawa", "123");
      User.setCurrentUser(test);
      assertEquals(test, User.getCurrentUser());
  }
  @Test
    void testChangeTheme(){
    DatabaseManager db = DatabaseManager.getInstance();
    db.insertUser("Chiikawa", "123");
    User test = db.getUser("Chiikawa", "123");
    test.setTheme("blue");
    assertEquals("blue", test.getTheme());
  }
  @Test
  void testChangeAvatar(){
    DatabaseManager db = DatabaseManager.getInstance();
    db.insertUser("Chiikawa", "123");
    User test = db.getUser("Chiikawa", "123");
    test.setAvatar("hachiware2.png");
    assertEquals("hachiware2.png", test.getAvatar());
  }
  @Test
  void testDbTheme(){
    DatabaseManager db = DatabaseManager.getInstance();
    db.insertUser("Chiikawa", "123");
    User test = db.getUser("Chiikawa", "123");
    test.setTheme("green");
    User newtest = db.getUser("Chiikawa", "123");
    assertEquals("green", newtest.getTheme());
  }
  @Test
  void testGuest(){
    User guest = User.guest();
    assertFalse(guest.isAdmin());
    assertEquals(-1, guest.getUserId());
  }
  @Test
  void testClearCurrent(){
    User test = new User(1, "Chii", false, "pink", "default.png");
    User.setCurrentUser(test);
    User.clearCurrentUser();
    assertNull(User.getCurrentUser());
  }
}
