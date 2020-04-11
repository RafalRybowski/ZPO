package sample.Editor;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.View;

public class EditorView extends View implements Editor {

    private Image image;
    private ImageView imageView;
    private EditorClickListener changeScene;
    private EditorController presenter;

    public EditorView(Stage window) {
        super(window);
    }

    @Override
    public Scene create() {
        presenter = new EditorController(this);

        imageView = new ImageView();

        VBox vBox = createOptionsMenu();

        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(vBox);
        borderPane.setCenter(imageView);
        return new Scene(borderPane, 1024, 768);
    }

    private VBox createOptionsMenu() {
        Button negativeButton = new Button("Negative");
        VBox vBox = new VBox();
        vBox.maxWidth(window.getHeight() / 4);
        negativeButton.setOnAction(e -> {
            presenter.onNegativeButtonPressed(image);
        });

        HBox cancelAndSave = createCancelSave();

        vBox.getChildren().addAll(negativeButton, cancelAndSave);
        return vBox;
    }

    private HBox createCancelSave(){
        Button cancel = new Button("Cancel");
        cancel.setOnAction( e -> changeScene.onClick());
        Button apply = new Button("Apply");
        HBox hBox = new HBox();
        hBox.getChildren().addAll(cancel, apply);
        return hBox;
    }

    @Override
    public void setImage(Image image) {
        this.image = image;
        imageView.setImage(image);
        imageView.setFitWidth(window.getHeight());
        imageView.setFitHeight(window.getHeight() - window.getHeight() / 4);
        imageView.setPreserveRatio(true);
    }

    public void changeScene(EditorClickListener changeScene) {
        this.changeScene = changeScene;
    }
}
