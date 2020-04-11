package sample;


import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class View {

    protected Stage window;

    public View(Stage window) {
        this.window = window;
    }

    abstract public Scene create();
}
