package sample.Gallery;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sample.View;

import java.util.List;

public class GalleryView extends View implements Gallery {
    private MenuBar menuBar;
    private ImageView mainImageView;
    private HBox imagesStore;
    private ScrollPane scrollPane;
    private GalleryClickListener changeScene;
    private double widthScreen = 1024;
    private double heightScreen = 768;
    private int MIN_PIXELS = 10;

    private GalleryController galleryController = new GalleryController(this);


    public GalleryView(Stage window) {
        super(window);
    }

    @Override
    public Scene create() {
        createMenuBar();
        setImageView();
        imagesStore = new HBox(4);

        scrollPane = new ScrollPane(imagesStore);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setCenter(mainImageView);
        borderPane.setBottom(scrollPane);
        return new Scene(borderPane, widthScreen, heightScreen);
    }

    private void setImageView() {
        mainImageView = new ImageView();

        ObjectProperty<Point2D> mouseDown = new SimpleObjectProperty<>();

        mainImageView.setOnMousePressed(e -> {
            Point2D mousePress = galleryController.imageViewToImage(mainImageView, new Point2D(e.getX(), e.getY()));
            mouseDown.set(mousePress);
        });

        mainImageView.setOnMouseDragged(e -> {
            Point2D dragPoint = galleryController.imageViewToImage(mainImageView, new Point2D(e.getX(), e.getY()));
            galleryController.shift(mainImageView, dragPoint.subtract(mouseDown.get()));
            mouseDown.set(galleryController.imageViewToImage(mainImageView, new Point2D(e.getX(), e.getY())));
        });

        mainImageView.setOnScroll(e -> {
            double delta = e.getDeltaY();
            Rectangle2D viewport = mainImageView.getViewport();

            double scale = galleryController.clamp(Math.pow(1.01, delta),
                    Math.min(MIN_PIXELS / viewport.getWidth(), MIN_PIXELS / viewport.getHeight()),
                    Math.max(widthScreen / viewport.getWidth(), heightScreen / viewport.getHeight())

            );

            Point2D mouse = galleryController.imageViewToImage(mainImageView, new Point2D(e.getX(), e.getY()));

            double newWidth = viewport.getWidth() * scale;
            double newHeight = viewport.getHeight() * scale;
            double newMinX = galleryController.clamp(mouse.getX() - (mouse.getX() - viewport.getMinX()) * scale,
                    0, widthScreen - newWidth);
            double newMinY = galleryController.clamp(mouse.getY() - (mouse.getY() - viewport.getMinY()) * scale,
                    0, heightScreen - newHeight);

            mainImageView.setViewport(new Rectangle2D(newMinX, newMinY, newWidth, newHeight));
        });

        mainImageView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                reset(mainImageView, mainImageView.getImage().getWidth(), mainImageView.getImage().getHeight());
            }
        });
    }

    private void createMenuBar() {
        menuBar = new MenuBar();

        //File menu
        Menu fileMenu = new Menu("File");
        MenuItem loadImagesFromDirectory = new MenuItem("Load Images From directory");
        loadImagesFromDirectory.setOnAction(e -> {
            galleryController.loadImagesFromDirectory();
        });
        MenuItem loadImages = new MenuItem("Load Images");
        loadImages.setOnAction(e -> {
            galleryController.loadImages();
        });
        fileMenu.getItems().addAll(loadImagesFromDirectory, loadImages);
        menuBar.getMenus().add(fileMenu);

        //About
        Menu helpMenu = new Menu("Options");
        MenuItem editItem = new MenuItem("Edit");
        editItem.setOnAction(e -> {
            changeScene.onClick(mainImageView.getImage());
        });
        MenuItem aboutItem = new MenuItem("About");
        aboutItem.setOnAction(e -> {
            showInformationAlert("About", "Program created on study classes by Rafał Rybowski");
        });
        helpMenu.getItems().addAll(aboutItem, editItem);
        menuBar.getMenus().add(helpMenu);
    }

    @Override
    public void setImageView(Image image) {
        mainImageView.setImage(image);
        mainImageView.setFitWidth(window.getWidth() - window.getWidth() / 4);
        mainImageView.setFitHeight(window.getHeight() - window.getHeight() / 4);
        mainImageView.setPreserveRatio(true);
        reset(mainImageView, image.getWidth(), image.getHeight());
    }


    void reset(ImageView imageView, double width, double height) {
        imageView.setViewport(new Rectangle2D(0, 0, width, height));
    }

    @Override
    public void setHBox(List<Image> images) {
        for (Image image : images) {
            ImageView imageView = new ImageView();
            imageView.setOnMouseClicked(event ->
                    mainImageView.setImage(imageView.getImage())
            );
            imageView.setImage(image);
            imageView.setFitWidth(window.getWidth() / 4);
            imageView.setFitHeight(window.getHeight() / 4 - 70);
            imageView.setPreserveRatio(true);
            imagesStore.getChildren().add(imageView);
        }
        scrollPane.setMaxWidth(window.getWidth());
    }

    @Override
    public void clearHBox() {
        imagesStore.getChildren().clear();
    }

    @Override
    public void showInformationAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    public void changeScene(GalleryClickListener changeScene) {
        this.changeScene = changeScene;
    }
}
