package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;

public class Destruction extends Token implements Serializable {
    transient ImageView icon;

    Destruction(int rx){
        super(rx);

        Image img = new Image("file:src/fireworks.png");
        icon = new ImageView(img);

        super.realg.getChildren().add(icon);
    }

    public void restore(){
        super.restore();
        Image img = new Image("file:src/fireworks.png");
        icon = new ImageView(img);

        super.realg.getChildren().add(icon);
    }

}
