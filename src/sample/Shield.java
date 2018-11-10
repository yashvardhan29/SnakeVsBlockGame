package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.Serializable;

public class Shield extends Token implements Serializable {
    ImageView icon;
    int time;

    Shield(int rx){
        super(rx);
        time = 0;

        Image img = new Image("file:src/antivirus.png");
        icon = new ImageView(img);

        super.realg.getChildren().add(icon);

    }


}
