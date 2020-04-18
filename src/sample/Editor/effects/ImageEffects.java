package sample.Editor.effects;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.Arrays;

public class ImageEffects {

    public static Image negative(Image image) {
        PixelReader pixelReader = image.getPixelReader();
        WritableImage wImage = new WritableImage(
                (int) image.getWidth(),
                (int) image.getHeight());
        PixelWriter pixelWriter = wImage.getPixelWriter();
        double tempH, tempW;
        tempH = image.getHeight();
        tempW = image.getWidth();
        for (int y = 0; y < tempH; y++) {
            for (int x = 0; x < tempW; x++) {
                Color color = pixelReader.getColor(x, y);
                color = color.invert();
                pixelWriter.setColor(x, y, color);
            }
        }
        return wImage;
    }

    public static Image sharpeningImage(Image image) {
        Image filteredImage = medianFilter(image);
        Image substracted = substractImageWithFiltered(image, filteredImage);
        Image added = addImage(image, substracted);
        return added;
    }

    private static Image substractImageWithFiltered(Image image, Image filteredImage) {
        PixelReader pixelReader = image.getPixelReader();
        PixelReader pixelFilteredReader = filteredImage.getPixelReader();
        WritableImage wImage = new WritableImage(
                (int) filteredImage.getWidth(),
                (int) filteredImage.getHeight());
        PixelWriter pixelWriter = wImage.getPixelWriter();
        for (int i = 0; i < filteredImage.getWidth(); ++i) {
            for (int j = 0; j < filteredImage.getHeight(); ++j) {
                Color readedColor = pixelReader.getColor(i + 1, j + 1);
                Color filderedColor = pixelFilteredReader.getColor(i, j);
                double red = Math.max(readedColor.getRed() - filderedColor.getRed(), 0);
                double green = Math.max(readedColor.getGreen() - filderedColor.getRed(), 0);
                double blue = Math.max(readedColor.getBlue() - filderedColor.getRed(), 0);
                Color color = Color.color(red, green, blue);
                pixelWriter.setColor(i, j, color);
            }
        }
        return wImage;
    }

    private static Image addImage(Image image, Image substracted) {
        PixelReader pixelReader = image.getPixelReader();
        PixelReader pixelFilteredReader = substracted.getPixelReader();
        WritableImage wImage = new WritableImage(
                (int) substracted.getWidth(),
                (int) substracted.getHeight());
        PixelWriter pixelWriter = wImage.getPixelWriter();
        for (int i = 0; i < substracted.getWidth(); ++i) {
            for (int j = 0; j < substracted.getHeight(); ++j) {
                Color readedColor = pixelReader.getColor(i + 1, j + 1);
                Color filderedColor = pixelFilteredReader.getColor(i, j);
                double red = Math.min(readedColor.getRed() + filderedColor.getRed(), 1);
                double green = Math.min(readedColor.getGreen() + filderedColor.getRed(), 1);
                double blue = Math.min(readedColor.getBlue() + filderedColor.getRed(), 1);
                Color color = Color.color(red, green, blue);
                pixelWriter.setColor(i, j, color);
            }
        }
        return wImage;
    }


    public static Image medianFilter(Image image) {
        PixelReader pixelReader = image.getPixelReader();
        Color[] pixels = new Color[9];
        double[] red = new double[9];
        double[] blue = new double[9];
        double[] green = new double[9];
        WritableImage wImage = new WritableImage(
                (int) image.getWidth() - 2,
                (int) image.getHeight() - 2);
        PixelWriter pixelWriter = wImage.getPixelWriter();
        for (int i = 1; i < image.getWidth() - 1; ++i) {
            for (int j = 1; j < image.getHeight() - 1; j++) {
                pixels[0] = pixelReader.getColor(i - 1, j - 1);
                pixels[1] = pixelReader.getColor(i - 1, j);
                pixels[2] = pixelReader.getColor(i - 1, j + 1);
                pixels[3] = pixelReader.getColor(i, j + 1);
                pixels[4] = pixelReader.getColor(i + 1, j + 1);
                pixels[5] = pixelReader.getColor(i + 1, j);
                pixels[6] = pixelReader.getColor(i + 1, j - 1);
                pixels[7] = pixelReader.getColor(i, j - 1);
                pixels[8] = pixelReader.getColor(i, j);
                for (int k = 0; k < 9; k++) {
                    red[k] = pixels[k].getRed();
                    blue[k] = pixels[k].getBlue();
                    green[k] = pixels[k].getGreen();
                }
                Arrays.sort(red);
                Arrays.sort(blue);
                Arrays.sort(green);
                pixelWriter.setColor(i - 1, j - 1, Color.color(red[4], green[4], blue[4]));
            }
        }
        return wImage;
    }

}
