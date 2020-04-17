package sample.Editor;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import sample.Editor.effects.ImageEffects;
import sample.Editor.effects.CropSellect;
import sample.utils.FileManager;

class EditorController {

    private Editor view;
    private FileManager fileManager = new FileManager();
    private CropSellect cropSellect;

    EditorController(Editor view, CropSellect cropSellect) {
        this.view = view;
        this.cropSellect = cropSellect;
    }

    void onNegativeButtonPressed(Image image) {
        view.setImage(
                ImageEffects.negative(image)
        );
    }

    void saveImage(Image image) {
        if (fileManager.saveImage(SwingFXUtils.fromFXImage(image, null))) {
            view.showInformationAlert("Failed Saved", "File successful saved");
        } else {
            view.showInformationAlert("Failed Saved", "Couldn't saved file");
        }
        view.changeScene();
    }

    void onCropPressed(Image image, double scaleY, double scaleX) {
        this.cropSellect.setOnCropped(bounds -> {
            double scale =  0;
            if (scaleY > scaleX) {
                scale = scaleY;
            } else {
                scale = scaleX;
            }
            Image newImage = cropImage(bounds, image, scale);
            view.setImage(newImage);
        });
        cropSellect.startSelection();
    }

    private Image cropImage(Bounds bounds, Image image, double scale) {
        PixelReader reader = image.getPixelReader();
        WritableImage newImage = new WritableImage(reader,
                (int) (bounds.getMinX() * scale),
                (int) (bounds.getMinY() * scale),
                (int) (bounds.getWidth() * scale),
                (int) (bounds.getHeight() * scale)
        );
        return newImage;
    }
}
