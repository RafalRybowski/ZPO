package sample.Editor;

import javafx.scene.image.Image;

public interface Editor {
    void setImage(Image image);
    void showInformationAlert(String header, String content);
    void changeScene();
}
