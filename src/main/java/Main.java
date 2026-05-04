import database.DatabaseManager;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.stage.Stage;
import scene.SceneManager;
import scene.SceneType;

public class Main extends Application {
  @Override
  public void start (Stage stage) {
    DatabaseManager db = DatabaseManager.getInstance(); // opens / creates app.db

    stage.setTitle("Chiikawa Task Pals");
    SceneManager.init(stage);
    SceneManager.getInstance().navigateTo(SceneType.WELCOME);
    stage.show () ;

  }
  @Override
  public void stop () throws SQLException {
    DatabaseManager.getInstance().close();
  }
}
