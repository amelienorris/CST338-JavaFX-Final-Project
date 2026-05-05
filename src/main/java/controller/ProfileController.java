package controller;

import database.User;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import scene.SceneFactory;
import scene.SceneManager;

public class ProfileController {

  @FXML private ImageView pfpImage;
  @FXML private ComboBox<String> pfpBox;
  @FXML private ComboBox<String> colorBox;

  private User user;

  @FXML
  public void initialize() {
    pfpBox.getItems().addAll(
        "default.png",
        "chikawa.png",
        "hachiware1.png",
        "king.png",
        "ouchie.png",
        "pjpals.png"
    );
    colorBox.getItems().addAll("Pink", "Green", "Blue");
  }

  public void setUser(User user) {
    this.user = user;
    if ((int)(Math.random() * 100) == 0) {
      pfpBox.getItems().add("secret.png");
    }
    String avatar = user.getAvatar();
    pfpBox.setValue(avatar);
    setPfpImage(avatar);}

  @FXML
  private void handlePfpChange() {
    String selected = pfpBox.getValue();
    if(user == null){
      user = User.getCurrentUser();
    }
    if (selected != null) {
      user.setAvatar(selected);
      setPfpImage(selected);
    }
  }
  @FXML
  private void handleThemeChange(){
    String selected = colorBox.getValue().toLowerCase();
    if(user == null){
      user = User.getCurrentUser();
    }
    String colorcode= switch(selected){
      case "blue" -> "#A8C8F4";
      case "green" -> "#A8F4C0";
      default -> "#F4A8B5";
    };
    user.setTheme(selected);
    colorBox.getScene().getRoot().setStyle("-fx-background-color: " + colorcode + ";");
  }

  private void setPfpImage(String fileName) {
    String path = "/pfps/" + fileName;

    if (getClass().getResource(path) == null) {
      System.out.println("Image not found: " + path);
      return;
    }
    Image image = new Image(getClass().getResource(path).toExternalForm(), 180, 180, true, true);
    if (image.isError()) {
      System.out.println("Image error: " + fileName);
      System.out.println(image.getException());
      return;
    }
    pfpImage.setImage(image);
  }
}