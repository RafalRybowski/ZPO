package sample.Editor;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import sample.Editor.effects.ImageEffects;
import sample.utils.FileManager;

class EditorController {

    private Editor view;
    private FileManager fileManager = new FileManager();

    EditorController(Editor view) {
        this.view = view;
    }

    void onNegativeButtonPressed(Image image) {
        view.setImage(
                ImageEffects.negative(image)
        );
    }

    void saveImage(Image image) {
//        if (image == null) {
//            view.showInformationAlert("Failed Saved", "Failed saved image because you didn't load any");
//            return;
//        }
//        if (fileManager.saveImage(SwingFXUtils.fromFXImage(image, null))) {
//            gallery.showInformationAlert("Failed Saved", "File successful saved");
//        } else {
//            gallery.showInformationAlert("Failed Saved", "Couldn't saved file");
//        }
    }
}
