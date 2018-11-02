package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Magnet {
    StackPane realg;
    ImageView icon;
    int time;
    Point location;

    Magnet(){
        time = 0;

        realg = new StackPane();
        Image img = new Image("file:src/magnet.png");
        icon = new ImageView(img);

        realg.getChildren().add(icon);
        realg.setLayoutX(400);
        realg.setLayoutY(400);
        location = new Point(150,100); //Yet, to randomise spawn location
    }

    public void setPosition(Point p){
        realg.setLayoutX(p.getX());
        realg.setLayoutY(p.getY());
    }
}
