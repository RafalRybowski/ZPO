package sample;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import sample.Editor.EditorView;
import sample.Gallery.GalleryView;


public class App extends Application {

    private Stage window;
    private Scene gallery, editor;
    private EditorView editorView;



    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        GalleryView galleryView = new GalleryView(window);
        gallery = galleryView.create();

        editorView = new EditorView(window);
        editor = editorView.create();

        galleryView.changeScene((image) -> {
            if(image != null) {
                editorView.setImage(image);
                window.setScene(editor);
            } else {
                showInformationAlert();
            }
        });

        editorView.changeScene(() -> {
            window.setScene(gallery);
        });

        window.setTitle("Przegladarka zdjęc na ZPO");
        window.setScene(gallery);
        window.setResizable(false);
        window.show();
    }

    private void showInformationAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Image is Empty");
        alert.setContentText("You can't edit image witch one doesn't exist");

        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
