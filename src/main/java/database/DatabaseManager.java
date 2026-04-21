package database;
import java.sql.*;
public class DatabaseManager {
  private static final String DB_URL = "jdbc:squlite:app.db";
  private Connection connection;
  public DatabaseManager() {
    try{
      connection = DriverManager.getConnection(DB_URL);
      System.out.println("Database connected");
      createTables();
    } catch (SQLException e){
      System.err.println("Connection failed: " + e.getMessage());
    }
  }
  public void close() throws SQLException {
    connection.close();
  }

  private void createTables(){
    try(Statement stmt = connection.createStatement()){
      String users =
              """
          CREATE TABLE IF NOT EXISTS users (
          user_id INTEGER PRIMARY KEY AUTOINCREMENT,
          user_name TEXT UNIQUE NOT NULL,
          user_password TEXT NOT NULL,
          is_admin INTEGER DEFAULT 0
          created_at TEXT DEFAULT(datetime('now'))
          avatar_character TEXT DEFAULT 'chiikawa',
          theme TEXT DEFAULT 'pink'
          timer_duration INTEGER DEFAULT 25,
          city TEXT,
          badges TEXT DEFAULT '[]',
          current_streak INTEGER DEFAULT 0,
          longest_streak INEGER DEFAULT 0
          )
          """;
      String tasks = """
          CREATE TABLE IF NOT EXISTS tasks(
          task_id INTEGER PRIMARY KEY AUTOINCREMENT,
          user_id INTEGER NOT NULL
          title TEXT NOT NULL,
          description TEXT
          due_date TEXT,
          priority TEXT DEFAULT 'MEDIUM'
          is_completed INTEGER DEFAULT 0,
          created_at TEXT DEFAULT (datetime('now')),
          FOREIGN KEY (user_id) REFERENCES users(user_id)
          """;
      String focus = """
          CREATE TABLE IF NOT EXISTS focus_sessions(
              session_id INTEGER PRIMARY KEY AUTOINCREMENT,
              user_id INTEGER NOT NULL,
              task_id INTEGER,
              duration_minutes INTEGER,
              completed INTEGER DEFAULT 0,
              session_date TEXT DEFAULT(datetime('now')),
              FORIEGN KEY (user_id) REFERENCES users(user_id),
              FOREIGN KEY (task_id) REFERENCES (tasks(task_id))
          )""";
      stmt.execute(users);
      stmt.execute(tasks);
      stmt.execute(focus);
      } catch (SQLException e){
        System.err.println("createTables failed: " + e.getMessage());
      }
  }
}
