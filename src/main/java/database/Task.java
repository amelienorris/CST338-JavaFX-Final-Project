package database;

public class Task {
  private int taskId;
  private int userId;
  private String title;
  private String description;
  private String dueDate;
  private String priority;
  private boolean completed;
  public Task(int taskId, int userId, String title, String description, String dueDate, String priority, boolean completed){
    this.taskId = taskId;
    this.userId = userId;
    this.title = title;
    this.description = description;
    this.dueDate = dueDate;
    this.priority = priority;
    this.completed = completed;
  }

  public int getTaskId() {
    return taskId;
  }

  public int getUserId() {
    return userId;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public String getDueDate() {
    return dueDate;
  }

  public String getPriority() {
    return priority;
  }

  public boolean isCompleted() {
    return completed;
  }
}
