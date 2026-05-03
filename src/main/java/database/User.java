package database;

public class User {
  private int userId;
  private String username;
  private boolean isAdmin;
  public User(int userId, String username, boolean isAdmin){
    this.userId = userId;
    this.username = username;
    this.isAdmin = isAdmin;
  }

    public static User guest() {
    return new User(-1, "Guest", false);
    }

    public int getUserId() {
    return userId;
  }

  public String getUsername() {
    return username;
  }

  public boolean isAdmin() {
    return isAdmin;
  }
}
