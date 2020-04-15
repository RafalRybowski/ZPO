package sample.Gallery;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public interface Gallery {
    void showInformationAlert(String header, String content);
    void setImageView(Image image);
    void setHBox(List<Image> images);
    void clearHBox();
}
