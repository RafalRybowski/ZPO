package sample.Editor.effects;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;

public class CropSellect {

    private final Position position = new Position();
    private Rectangle rect;
    private Group group;
    private CropListener cropListener;


    public CropSellect(Group group) {

        this.group = group;

        rect = new Rectangle(0, 0, 0, 0);
        rect.setStroke(Color.BLUE);
        rect.setStrokeWidth(1);
        rect.setStrokeLineCap(StrokeLineCap.ROUND);
        rect.setFill(Color.LIGHTBLUE.deriveColor(0, 1.2, 1, 0.6));
    }

    public void startSelection() {
        group.addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
        group.addEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDraggedEventHandler);
        group.addEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);
    }

    private EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

            if (event.isSecondaryButtonDown())
                return;

            rect.setX(0);
            rect.setY(0);
            rect.setWidth(0);
            rect.setHeight(0);

            group.getChildren().remove(rect);

            position.mouseAnchorX = event.getX();
            position.mouseAnchorY = event.getY();

            rect.setX(position.mouseAnchorX);
            rect.setY(position.mouseAnchorY);
            rect.setWidth(0);
            rect.setHeight(0);

            group.getChildren().add(rect);

        }
    };

    private EventHandler<MouseEvent> onMouseDraggedEventHandler = event -> {

        if (event.isSecondaryButtonDown())
            return;

        double offsetX = event.getX() - position.mouseAnchorX;
        double offsetY = event.getY() - position.mouseAnchorY;
        System.out.println("Y: " + offsetY + " " + event.getY() + " X:" + offsetX  + " " + event.getX());

        if (offsetX > 0)
            rect.setWidth(offsetX);
        else {
            rect.setX(event.getX());
            rect.setWidth(position.mouseAnchorX - rect.getX());
        }

        if (offsetY > 0) {
            rect.setHeight(offsetY);
        } else {
            rect.setY(event.getY());
            rect.setHeight(position.mouseAnchorY - rect.getY());
        }
    };

    private EventHandler<MouseEvent> onMouseReleasedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

            if (event.isSecondaryButtonDown())
                return;

            cropListener.onCrop(rect.getBoundsInParent());

            rect.setX(0);
            rect.setY(0);
            rect.setWidth(0);
            rect.setHeight(0);

            group.getChildren().remove(rect);

            group.removeEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
            group.removeEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDraggedEventHandler);
            group.removeEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);


        }
    };

    public void setOnCropped(CropListener cropListener) {
        this.cropListener = cropListener;
    }


    private static final class Position {

        double mouseAnchorX;
        double mouseAnchorY;
    }
}