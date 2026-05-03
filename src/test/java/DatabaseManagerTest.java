import database.DatabaseManager;
import database.User;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class DatabaseManagerTest {
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
    void testCreateUser(){
      DatabaseManager db = DatabaseManager.getInstance();
      String user = "chiikawa";
      String password = "chiifan123";
      boolean inserted = db.insertUser(user, password);
      assertTrue(inserted);
      User testuser = db.getUser(user, password);
      assertNotNull(testuser, "found from insertion");
  }
  @Test
    void testWrongPassword(){
      DatabaseManager db = DatabaseManager.getInstance();
      db.insertUser("chii2", "chiifan123");
      User user = db.getUser("chii2", "bleh");
      assertNull(user);
  }
  @Test
    void testExistingUser(){
      DatabaseManager db = DatabaseManager.getInstance();
      db.insertUser("chii2", "yay");
      boolean existing = db.insertUser("chii2", "skadjhfs");
      assertFalse(existing);
  }
  @Test
    void testDeleteUser(){
      DatabaseManager db = DatabaseManager.getInstance();
      db.insertUser("chii4", "woo");
      User deletetest = db.getUser("chii4", "woo");
      int testid = deletetest.getUserId();
      db.deleteUser(testid);
      assertNull(db.getUser("chii4", "woo"));
  }
  @Test
  void testInsertTask(){
    DatabaseManager db = DatabaseManager.getInstance();
    db.insertUser("chiikawa", "123");
    User user = db.getUser("chiikawa", "123");
    db.insertTask(user.getUserId(), "do homework", "blablabla", "2026-05-23", "HIGH");
    List<String> tasks = db.getTasks(user.getUserId());
    assertTrue(tasks.contains("do homework"));
  }
  @Test
  void testUpdateTask(){
    DatabaseManager db = DatabaseManager.getInstance();
    db.insertUser("chiikawa", "123");
    User user = db.getUser("chiikawa", "123");
    int id = db.insertTask(user.getUserId(), "do homework", "blablabla", "2026-05-23", "HIGH");
    db.completeTask(id);
    List<String> tasks = db.getTasks(user.getUserId());
    assertFalse(tasks.contains("do homework"));
  }
  @Test
  void testDeleteTask(){
    DatabaseManager db = DatabaseManager.getInstance();
    db.insertUser("chiikawa", "123");
    User user = db.getUser("chiikawa", "123");
    int id = db.insertTask(user.getUserId(), "do homework", "blablabla", "2026-05-23", "HIGH");
    db.deleteTask(id);
    List<String> tasks = db.getTasks(user.getUserId());
    assertFalse(tasks.contains("do homework"));
  }

  @Test
  void testInsertFocus(){
    DatabaseManager db = DatabaseManager.getInstance();
    db.insertUser("chiikawa", "123");
    User user = db.getUser("chiikawa", "123");
    boolean inserted = db.insertFocus(user.getUserId(), 0, 25, true);
    assertTrue(inserted);
  }
  @Test
  void testUpdateFocus(){
    DatabaseManager db = DatabaseManager.getInstance();
    db.insertUser("chiikawa", "123");
    User user = db.getUser("chiikawa", "123");
    db.insertFocus(user.getUserId(), 0, 25, false);
    db.updateFocus(1, true);
    assertEquals(1, db.getSesssionCount(user.getUserId())); // should have 1 completed session if focus is marked true
  }
  @Test
  void testDeleteFocus(){
    DatabaseManager db = DatabaseManager.getInstance();
    db.insertUser("chiikawa", "123");
    User user = db.getUser("chiikawa", "123");
    db.insertFocus(user.getUserId(), 0, 25, true);
    db.deleteFocus(1);
    assertEquals(0, db.getSesssionCount(user.getUserId())); // should have 0 completed if the inserted completed focus session is deleted
  }
}
