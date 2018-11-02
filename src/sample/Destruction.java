package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Destruction {
    StackPane realg;
    ImageView icon;
    int time;
    Point location;

    Destruction(){
        time = 0;

        realg = new StackPane();
        Image img = new Image("file:src/fireworks.png");
        icon = new ImageView(img);

        realg.getChildren().add(icon);
        realg.setLayoutX(300);
        realg.setLayoutY(300);

        location = new Point(200,100); //Yet, to randomise spawn location.
    }

    public void setPosition(Point p){
        realg.setLayoutX(p.getX());
        realg.setLayoutY(p.getY());
    }
}
