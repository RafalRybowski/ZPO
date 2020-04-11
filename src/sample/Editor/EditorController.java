package sample.Editor;

import javafx.scene.image.Image;
import sample.Editor.effects.ImageEffects;

class EditorController {

    private Editor view;

    EditorController(Editor view) {
        this.view = view;
    }

    void onNegativeButtonPressed(Image image) {
        view.setImage(
                ImageEffects.negative(image)
        );
    }
}
