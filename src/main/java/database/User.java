package database;

public class User {
  private int userId;
  private String username;
  private boolean isAdmin;
  private String theme;
  public static User currentUser;
  private String avatar = "default.png";
  private int timer = 25;
  public User(int userId, String username, boolean isAdmin, String theme, String avatar){
    this.userId = userId;
    this.username = username;
    this.isAdmin = isAdmin;
    this.theme = theme;
    this.avatar = avatar;
  }

  public int getTimer() {
    return timer;
  }

  public void setTimer(int timer) {
    this.timer = timer;
    DatabaseManager.getInstance().updateUser(this.userId, this.avatar, this.theme, timer);
  }

  public static void setCurrentUser(User user){
    currentUser = user;
  }
  public static User getCurrentUser(){
    return currentUser;
  }
  public void setTheme(String th){
    this.theme = th;
    DatabaseManager.getInstance().updateUser(this.userId, this.avatar, th, this.timer);
  }
  public void setAvatar(String av){
    this.avatar = av;
    DatabaseManager.getInstance().updateUser(this.userId, av, this.theme, this.timer);
  }
  public String getAvatar() {
    return avatar;
  }

  public static void clearCurrentUser(){
    currentUser = null;
  } // handle theme changes & profile edits cleanly, acts as a cache

    public static User guest() {
    return new User(-1, "Guest", false, "pink", "default.png");
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

  public String getTheme() {
    return theme;
  } // added gettheme to change ui backgrounds
}
