package sample.Editor;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.View;

public class EditorView extends View implements Editor {

    private ImageView imageView;
    private EditorClickListener changeScene;
    private EditorController presenter;
    private static SepiaTone sepiaEffect = new SepiaTone(0);


    public EditorView(Stage window) {
        super(window);
    }

    @Override
    public Scene create() {
        presenter = new EditorController(this);

        imageView = new ImageView();
        imageView.setEffect(sepiaEffect);

        VBox vBox = createOptionsMenu();

        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(vBox);
        borderPane.setCenter(imageView);
        return new Scene(borderPane, 1024, 768);
    }

    private VBox createOptionsMenu() {

        Insets padding = new Insets(10,10,10,10);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(padding);
        vBox.maxWidth(window.getHeight() / 4);

        Button negativeButton = new Button("Negative");
        negativeButton.setOnAction(e -> {
            presenter.onNegativeButtonPressed(imageView.getImage());
        });

        Text textSepia = new Text("Sepia:");

        Slider sepiaSlider = new Slider(0, 1, 0);
        sepiaSlider.setMinorTickCount(5);
        sepiaSlider.setBlockIncrement(10);
        sepiaSlider.valueProperty().addListener((observable, oldValue, newValue) -> sepiaEffect.setLevel(newValue.doubleValue()));

        HBox cancelAndSave = createCancelSave();

        vBox.getChildren().addAll(negativeButton,textSepia, sepiaSlider, cancelAndSave);
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
        imageView.setImage(image);
        imageView.setFitWidth(window.getHeight());
        imageView.setFitHeight(window.getHeight() - window.getHeight() / 4);
        imageView.setPreserveRatio(true);
    }

    public void changeScene(EditorClickListener changeScene) {
        this.changeScene = changeScene;
    }
}
