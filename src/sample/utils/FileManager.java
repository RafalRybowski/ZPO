package sample.utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    public List<Image> loadImagesFromDirectory() {
        List<Image> images = new ArrayList<>();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose Directory");
        File directory = directoryChooser.showDialog(null);
        if (directory != null) {
            File[] files = directory.listFiles();
            String fileName;
            for (File file : files) {
                fileName = file.getName().toLowerCase();
                if (fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".bmp")) {
                    try {
                        images.add(SwingFXUtils.toFXImage(ImageIO.read(file), null));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            return null;
        }
        return images;
    }

    public List<Image> loadImages() {
        List<Image> images = new ArrayList<>();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Images");
        List<File> files = fileChooser.showOpenMultipleDialog(null);
        if (files != null) {
            String fileName;
            for (File file : files) {
                fileName = file.getName().toLowerCase();
                if (fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".bmp")) {
                    try {
                        images.add(SwingFXUtils.toFXImage(ImageIO.read(file), null));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            return null;
        }
        return images;
    }

    public boolean saveImage(BufferedImage image) {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("image (*.png)", "*.jpg", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                ImageIO.write(image, "png", file);
            } catch (IOException ex) {
                return false;
            }
        }
        return true;
    }
}
