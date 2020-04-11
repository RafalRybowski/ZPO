package sample;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Editor.EditorView;
import sample.Gallery.GalleryView;


public class App extends Application {

    private Stage window;
    private Scene gallery, editor;
    private GalleryView galleryView;
    private EditorView editorView;



    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        galleryView = new GalleryView(window);
        gallery = galleryView.create();

        editorView = new EditorView(window);
        editor = editorView.create();

        galleryView.changeScene((image) -> {
            editorView.setImage(image);
            window.setScene(editor);
        });

        editorView.changeScene(() -> {
            window.setScene(gallery);
        });

        window.setTitle("Przegladarka zdjęc na ZPO");
        window.setScene(gallery);
        window.setResizable(false);
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
