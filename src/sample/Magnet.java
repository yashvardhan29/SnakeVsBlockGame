package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Magnet extends Token {
    ImageView icon;
    int time;

    Magnet(int rx){
        super(rx);
        time = 0;

        Image img = new Image("file:src/magnet.png");
        icon = new ImageView(img);

        super.realg.getChildren().add(icon);


    }

}
