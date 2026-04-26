import database.DatabaseManager;
import database.User;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class DatabaseManagerTest {
  private DatabaseManager db;
  @BeforeEach
  void setUp(){
    db = DatabaseManager.getInstance();
  }
  @Test
    void testCreate(){
      String user = "chiikawa";
      String password = "chiifan123";
      boolean inserted = db.insertUser(user, password);
      assertTrue(inserted);
      User testuser = db.getUser(user, password);
      assertNotNull(testuser, "found from insertion");
  }
  @Test
    void testWrongPassword(){
      db.insertUser("chii2", "chiifan123");
      User user = db.getUser("chii2", "bleh");
      assertNull(user);
  }
  @Test
    void testExistingUser(){
    boolean existing = db.insertUser("chii2", "skadjhfs");
    assertFalse(existing);
  }
  @Test
    void testDelete(){
    db.insertUser("chii4", "woo");
    User deletetest = db.getUser("chii4", "woo");
    int testid = deletetest.getUserId();
    db.deleteUser(testid);
    assertNull(db.getUser("chii4", "woo"));
  }
}
