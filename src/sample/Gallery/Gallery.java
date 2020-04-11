package sample.Gallery;

import javafx.scene.image.Image;

import java.util.List;

public interface Gallery {
    void showInformationAlert(String header, String content);
    void setImageView(Image image);
    void setHBox(List<Image> images);
    void clearHBox();
}
