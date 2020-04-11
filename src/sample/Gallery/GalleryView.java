package sample.Gallery;

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

    private GalleryController appController = new GalleryController(this);

    public GalleryView(Stage window) {
        super(window);
    }

    @Override
    public Scene create() {
        createMenuBar();

        mainImageView = new ImageView();
        imagesStore = new HBox(4);

        scrollPane = new ScrollPane(imagesStore);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setCenter(mainImageView);
        borderPane.setBottom(scrollPane);
        return new Scene(borderPane, 1024, 768);
    }

    private void createMenuBar() {
        menuBar = new MenuBar();

        //File menu
        Menu fileMenu = new Menu("File");
        MenuItem loadImagesFromDirectory = new MenuItem("Load Images From directory");
        loadImagesFromDirectory.setOnAction(e -> {
            appController.loadImagesFromDirectory();
        });
        MenuItem loadImages = new MenuItem("Load Images");
        loadImages.setOnAction(e -> {
            appController.loadImages();
        });
        MenuItem saveImage = new MenuItem("Save Image");
        saveImage.setOnAction(e -> {
            appController.saveImage(mainImageView.getImage());
        });
        fileMenu.getItems().addAll(loadImagesFromDirectory, loadImages, saveImage);
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
        mainImageView.setFitWidth(window.getHeight() - window.getHeight() / 4);
        mainImageView.setFitHeight(window.getHeight() - window.getHeight() / 4);
        mainImageView.setPreserveRatio(true);
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
