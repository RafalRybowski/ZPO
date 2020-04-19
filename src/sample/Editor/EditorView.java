package sample.Editor;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import sample.Editor.effects.CropSellect;
import sample.View;

public class EditorView extends View implements Editor {

    private ImageView imageView;
    private EditorClickListener changeScene;
    private EditorController presenter;
    private Group imageLayer = new Group();
    private static SepiaTone sepiaEffect = new SepiaTone(0);


    public EditorView(Stage window) {
        super(window);
    }

    @Override
    public Scene create() {
        presenter = new EditorController(this, new CropSellect(imageLayer));

        imageView = new ImageView();

        imageLayer.getChildren().add(imageView);
        imageView.setEffect(sepiaEffect);

        presenter = new EditorController(this, new CropSellect(imageLayer));

        VBox vBox = createOptionsMenu();
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(vBox);
        borderPane.setCenter(imageLayer);
        return new Scene(borderPane, 1024, 768);
    }

    private VBox createOptionsMenu() {

        Insets padding = new Insets(10,10,10,10);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(padding);
        vBox.maxWidth(window.getHeight() / 4);

        Button cropButton = new Button("Crop");
        cropButton.setOnAction( e -> {
            Image image = imageView.getImage();
            double scaleX = image.getHeight() / imageView.getFitHeight();
            double scaleY = image.getWidth() / imageView.getFitWidth();
            presenter.onCropPressed(image, scaleY, scaleX);
        });

        Button sharpeningButton = new Button("Sharpening ");
        sharpeningButton.setOnAction( e -> {
            Image image = imageView.getImage();
            presenter.onSharpeningPressed(image);
        });

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

        vBox.getChildren().addAll(cropButton, sharpeningButton, negativeButton, textSepia, sepiaSlider, cancelAndSave);
        return vBox;
    }

    private HBox createCancelSave(){
        Button cancel = new Button("Cancel");
        cancel.setOnAction( e -> changeScene());
        Button save = new Button("Save");
        save.setOnAction( e -> presenter.saveImage(imageView.getImage()));
        HBox hBox = new HBox();
        hBox.getChildren().addAll(cancel, save);
        return hBox;
    }

    @Override
    public void showInformationAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    @Override
    public void changeScene(){
        changeScene.onClick();
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
