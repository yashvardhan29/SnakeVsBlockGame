package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.Serializable;

public class Magnet extends Token implements Serializable {
    transient ImageView icon;
    int time;

    Magnet(int rx){
        super(rx);
        time = 0;

        Image img = new Image("file:src/magnet.png");
        icon = new ImageView(img);

        super.realg.getChildren().add(icon);


    }

    public void restore(){
        super.restore();
        Image img = new Image("file:src/magnet.png");
        icon = new ImageView(img);

        super.realg.getChildren().add(icon);
    }

}
