package sample.Editor.effects;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImageEffects {

    public static Image negative(Image image){
        PixelReader pixelReader = image.getPixelReader();
        WritableImage wImage = new WritableImage(
                (int)image.getWidth(),
                (int)image.getHeight());
        PixelWriter pixelWriter = wImage.getPixelWriter();
        double tempH, tempW;
        tempH = image.getHeight();
        tempW = image.getWidth();
        for(int y = 0; y < tempH; y++) {
            for (int x = 0; x < tempW; x++) {
                Color color = pixelReader.getColor(x, y);
                color = color.invert();
                pixelWriter.setColor(x, y, color);
            }
        }
        return wImage;
    }

}
