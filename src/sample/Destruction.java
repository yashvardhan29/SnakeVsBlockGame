package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Destruction extends Token{
    ImageView icon;

    Destruction(int rx){
        super(rx);

        Image img = new Image("file:src/fireworks.png");
        icon = new ImageView(img);

        super.realg.getChildren().add(icon);
    }

}
