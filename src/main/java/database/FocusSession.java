package database;

public class FocusSession {
  private int sessionId;
  private int userId;
  private int taskId;
  private int durationMinutes;
  private boolean completed;
  private String sessionDate;

  public FocusSession(int sessionId, int userId, int taskId, int durationMinutes, boolean completed, String sessionDate){
    this.sessionId = sessionId;
    this.userId = userId;
    this.taskId = taskId;
    this.durationMinutes = durationMinutes;
    this.completed = completed;
    this.sessionDate = sessionDate;
  }

  public int getSessionId() {
    return sessionId;
  }

  public int getUserId() {
    return userId;
  }

  public int getTaskId() {
    return taskId;
  }

  public int getDurationMinutes() {
    return durationMinutes;
  }

  public boolean isCompleted() {
    return completed;
  }

  public String getSessionDate() {
    return sessionDate;
  }
}
