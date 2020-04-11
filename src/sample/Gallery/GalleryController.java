package sample.Gallery;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import sample.utils.FileManager;

import java.util.ArrayList;
import java.util.List;

class GalleryController {

    private Gallery gallery;
    private FileManager fileManager = new FileManager();
    private List<Image> images = new ArrayList<>();

    GalleryController(Gallery gallery) {
        this.gallery = gallery;
    }

    void loadImagesFromDirectory() {
        images = fileManager.loadImagesFromDirectory();
        if (images != null) {
            gallery.clearHBox();
            gallery.setImageView(images.get(0));
            gallery.setHBox(images);
        }
    }

    void loadImages() {
        images = fileManager.loadImages();
        if (images != null) {
            gallery.clearHBox();
            gallery.setImageView(images.get(0));
            gallery.setHBox(images);
        }
    }

    void saveImage(Image image) {
        if (image == null) {
            gallery.showInformationAlert("Failed Saved", "Failed saved image because you didn't load any");
            return;
        }
        if (fileManager.saveImage(SwingFXUtils.fromFXImage(image, null))) {
            gallery.showInformationAlert("Failed Saved", "File successful saved");
        } else {
            gallery.showInformationAlert("Failed Saved", "Couldn't saved file");
        }
    }
}