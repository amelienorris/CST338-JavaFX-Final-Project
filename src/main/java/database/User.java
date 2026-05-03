package database;

public class User {
  private int userId;
  private String username;
  private boolean isAdmin;
  private String theme;
  public static User currentUser;
  public User(int userId, String username, boolean isAdmin, String theme){
    this.userId = userId;
    this.username = username;
    this.isAdmin = isAdmin;
    this.theme = theme;
  }
  public static void setCurrentUser(User user){
    currentUser = user;
  }
  public static User getCurrentUser(){
    return currentUser;
  }
  public static void clearCurrentUser(){
    currentUser = null;
  } // handle theme changes & profile edits cleanly, acts as a cache

    public static User guest() {
    return new User(-1, "Guest", false, "pink");
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

  public boolean isGuest(){
    return userId == -1;
  }

  public String getTheme() {
    return theme;
  } // added gettheme to change ui backgrounds
}
