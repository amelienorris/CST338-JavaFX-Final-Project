package database;
import java.sql.*;
import database.User;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
  private static final String DB_URL = "jdbc:sqlite:taskpals.db";
  private static DatabaseManager instance; // make an instance for singleton
  private Connection connection;
  private DatabaseManager() {
    try{
      connection = DriverManager.getConnection(DB_URL);
      System.out.println("Database connected");
      createTables();
    } catch (SQLException e){
      System.err.println("Connection failed: " + e.getMessage());
    }
  }
  public static DatabaseManager getInstance(){
    if (instance==null){
      instance = new DatabaseManager();
    }
    return instance;
  }
  public void close(){
    try{
      connection.close();
    }catch (SQLException e){
      System.err.println("Failed to close " + e.getMessage());
    }

  }
 // tables
  private void createTables(){
    try(Statement stmt = connection.createStatement()){
      String users =
              """
          CREATE TABLE IF NOT EXISTS users (
          user_id INTEGER PRIMARY KEY AUTOINCREMENT,
          user_name TEXT UNIQUE NOT NULL,
          user_password TEXT NOT NULL,
          is_admin INTEGER DEFAULT 0,
          avatar_character TEXT DEFAULT 'chiikawa',
          theme TEXT DEFAULT 'pink',
          timer_duration INTEGER DEFAULT 25,
          city TEXT,
          badges TEXT DEFAULT '[]',
          current_streak INTEGER DEFAULT 0,
          longest_streak INTEGER DEFAULT 0
          )
          """;
      String tasks = """
          CREATE TABLE IF NOT EXISTS tasks(
          task_id INTEGER PRIMARY KEY AUTOINCREMENT,
          user_id INTEGER NOT NULL,
          title TEXT NOT NULL,
          description TEXT,
          due_date TEXT,
          priority TEXT DEFAULT 'MEDIUM',
          is_completed INTEGER DEFAULT 0,
          created_at TEXT DEFAULT (datetime('now')),
          FOREIGN KEY (user_id) REFERENCES users(user_id))
          """;
      String focus = """
          CREATE TABLE IF NOT EXISTS focus_sessions(
              session_id INTEGER PRIMARY KEY AUTOINCREMENT,
              user_id INTEGER NOT NULL,
              task_id INTEGER,
              duration_minutes INTEGER,
              completed INTEGER DEFAULT 0,
              session_date TEXT DEFAULT(datetime('now')),
              FOREIGN KEY (user_id) REFERENCES users(user_id),
              FOREIGN KEY (task_id) REFERENCES tasks(task_id))
          """;
      stmt.execute(users);
      stmt.execute(tasks);
      stmt.execute(focus);
      } catch (SQLException e){
        System.err.println("createTables failed: " + e.getMessage()); // error handling from scene factory leture slides
      }
  }
  //CRUD
  public boolean insertUser(String username, String password) {
    String sql = "INSERT INTO users (user_name, user_password) VALUES (?, ?)";
    try(PreparedStatement pstmt = connection.prepareStatement(sql)){
      pstmt.setString(1, username);
      pstmt.setString(2, password);
      pstmt.executeUpdate();
      return true;
    } catch (SQLException e){
      System.err.println("insertUser failed: " + e.getMessage());
      return false;
    }
  }
  public User getUser(String username, String password) {
    String sql = "SELECT * FROM users WHERE user_name = ? AND user_password = ?";
    try(PreparedStatement pstmt = connection.prepareStatement(sql)){
      pstmt.setString(1, username);
      pstmt.setString(2, password);
      ResultSet rs = pstmt.executeQuery();
      while(rs.next()) {
        return new User (
          rs.getInt("user_id"),
          rs.getString("user_name"),
          rs.getInt("is_admin") == 1
        );
      }
    } catch (SQLException e) {
      System.err.println("Get user credentials failed: " + e.getMessage());
    }
    return null;
  }
  public boolean changePW(String username, String newpw){
    String sql = "SELECT * FROM users WHERE user_name = ?"; // check if user is valid
    String update = "UPDATE users SET user_password = ? WHERE user_name = ?";
    try(PreparedStatement pstmt = connection.prepareStatement(sql)){
      pstmt.setString(1, username);
    } catch (SQLException e){
      System.err.println("Find user failed: " + e.getMessage());
    }
    try (PreparedStatement pstmt2 = connection.prepareStatement(update)){
      pstmt2.setString(1, newpw);
      pstmt2.setString(2, username);
      pstmt2.executeUpdate();
      return true;
    } catch (SQLException e){
      System.err.println("Change password failed: " + e.getMessage());
    }
    return false;
  }
  public void updateUser(int userID, String avatar, String theme, int timer, String city){
    String sql = "UPDATE users SET avatar_character = ?, theme = ?, timer_duration = ?, city = ? WHERE user_id = ?";
    try(PreparedStatement pstmt = connection.prepareStatement(sql)){
      pstmt.setString(1, avatar);
      pstmt.setString(2, theme);
      pstmt.setInt(3, timer);
      pstmt.setString(4, city);
      pstmt.setInt(5, userID);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      System.err.println("update failed: " + e.getMessage());
    }
  }
  public void deleteUser(int userId){
    String sql = "DELETE FROM users WHERE user_id = ?";
    try(PreparedStatement pstmt = connection.prepareStatement(sql)){
      pstmt.setInt(1, userId);
      pstmt.executeUpdate();
    } catch (SQLException e){
      System.err.println("delete failed: " + e.getMessage());
    }
  }
  public void insertTask(int userId, String title, String description, String due, String priority){
    String sql = "INSERT INTO tasks(user_id, title, description, due_date, priority) VALUES (?,?,?,?,?)";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)){
      pstmt.setInt(1, userId);
      pstmt.setString(2, title);
      pstmt.setString(3, description);
      pstmt.setString(4, due);
      pstmt.setString(5, priority);
      pstmt.executeUpdate();
    } catch (SQLException e){
      System.err.println("add task failed " + e.getMessage());
    }
  }
  public List<String> getTasks(int userId){
    List<String> tasks = new ArrayList<>();
    String sql = "SELECT title FROM tasks WHERE user_id = ? AND is_completed = 0 ORDER BY due_date";
    try(PreparedStatement pstmt = connection.prepareStatement(sql)){
      pstmt.setInt(1, userId);
      ResultSet rs = pstmt.executeQuery();
      while(rs.next()){
        tasks.add(rs.getString("title"));
      }
    } catch (SQLException e){
      System.err.println("Failed to get tasks " + e.getMessage());
    }
    return tasks;
  }
  public void completeTask(int taskId){
    String sql = "UPDATE tasks SET is_completed = 1 WHERE task_id = ?";
    try(PreparedStatement pstmt = connection.prepareStatement(sql)){
      pstmt.setInt(1, taskId);
      pstmt.executeUpdate();
    } catch(SQLException e){
      System.err.println("could not mark completed " + e.getMessage());
    }
  }
  public void deleteTask(int taskId){
    String sql = "DELETE FROM tasks WHERE task_id = ?";
    try(PreparedStatement pstmt = connection.prepareStatement(sql)){
      pstmt.setInt(1, taskId);
      pstmt.executeUpdate();
    } catch(SQLException e){
      System.err.println("could not delete" + e.getMessage());
    }
  }
  public void insertFocus(int userId, int taskId, int duration, boolean completed){
    String sql = "INSERT INTO focus_sessions(user_id, task_id, duration_minutes, completed) VALUES (?,?,?,?)";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)){
      pstmt.setInt(1, userId);
      pstmt.setInt(2, taskId);
      pstmt.setInt(3, duration);
      pstmt.setInt(4, completed ? 1 : 0);
      pstmt.executeUpdate();
    } catch (SQLException e){
      System.err.println("add task failed " + e.getMessage());
    }
  }
  public int getSesssionCount(int userId){
    int total = 0;
    String sql = "SELECT session_id FROM focus_sessions WHERE user_id = ? AND completed = 1";
    try(PreparedStatement pstmt = connection.prepareStatement(sql)){
      pstmt.setInt(1, userId);
      ResultSet rs = pstmt.executeQuery();
      while(rs.next()){
        total++;
      }
    } catch (SQLException e){
      System.err.println("Failed to get tasks " + e.getMessage());
    }
    return total;
  }
}
