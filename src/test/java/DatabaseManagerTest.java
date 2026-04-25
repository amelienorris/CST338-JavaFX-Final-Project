import database.DatabaseManager;
import database.User;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class DatabaseManagerTest {
  private DatabaseManager db;

  public DatabaseManagerTest() throws SQLException {
  }

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

}
