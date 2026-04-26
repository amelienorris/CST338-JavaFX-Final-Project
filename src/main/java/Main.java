import database.DatabaseManager;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.stage.Stage;
import scene.SceneManager;
import scene.SceneType;

public class Main extends Application {
  private DatabaseManager db ;
  @Override
  public void start (Stage stage) {
    SceneManager.init(stage);
    stage.setTitle("Todo App");
    SceneManager.getInstance().navigateTo(SceneType.WELCOME);
    stage.show();

  }
  @Override
  public void stop () throws SQLException {
    DatabaseManager.getInstance().close();
  }
}
