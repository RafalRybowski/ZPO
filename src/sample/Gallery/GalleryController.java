package sample.Gallery;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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



    void shift(ImageView imageView, Point2D delta) {
        Rectangle2D viewport = imageView.getViewport();

        double width = imageView.getImage().getWidth() ;
        double height = imageView.getImage().getHeight() ;

        double maxX = width - viewport.getWidth();
        double maxY = height - viewport.getHeight();

        double minX = clamp(viewport.getMinX() - delta.getX(), 0, maxX);
        double minY = clamp(viewport.getMinY() - delta.getY(), 0, maxY);

        imageView.setViewport(new Rectangle2D(minX, minY, viewport.getWidth(), viewport.getHeight()));
    }

    double clamp(double value, double min, double max) {

        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    Point2D imageViewToImage(ImageView imageView, Point2D imageViewCoordinates) {
        double xProportion = imageViewCoordinates.getX() / imageView.getBoundsInLocal().getWidth();
        double yProportion = imageViewCoordinates.getY() / imageView.getBoundsInLocal().getHeight();

        Rectangle2D viewport = imageView.getViewport();
        return new Point2D(
                viewport.getMinX() + xProportion * viewport.getWidth(),
                viewport.getMinY() + yProportion * viewport.getHeight());
    }
}
