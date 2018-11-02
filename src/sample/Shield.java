package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Shield {
    StackPane realg;
    ImageView icon;
    int time;
    Point location;

    Shield(){
        time = 0;

        realg = new StackPane();
        Image img = new Image("file:/Users/yash/Desktop/antivirus.png");
        icon = new ImageView(img);

        realg.getChildren().add(icon);
        realg.setLayoutX(200);
        realg.setLayoutY(200);
        location = new Point(100,100); //Yet, to randomise spawn location
    }

    public void setPosition(Point p){
        realg.setLayoutX(p.getX());
        realg.setLayoutY(p.getY());
    }
}
