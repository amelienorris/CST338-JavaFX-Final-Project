import database.DatabaseManager;
import java.sql.SQLException;
import database.User;

public class MainApp extends Application {
  private DatabaseManager db ;
  @Override
  public void start ( Stage stage ) {
    db = new DatabaseManager () ; // opens / creates app.db
    stage.setTitle("Todo App");
    stage.setScene(SceneFactory.create (SceneType.MAIN, stage, db));
    stage.show () ;
  }
  @Override
  public void stop () throws SQLException {
    if ( db != null ) db.close() ; // called automatically on window close
  } // framework for SQLite database implementation with scenemanger, to be implemented fully after we work on scenefactory
}
