package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.Serializable;

/**
 * Desttruction class. Subclass of class Token.
 */

public class Destruction extends Token implements Serializable {
    /**
     * Holds picture of destruction token(fireworks).
     */
    transient ImageView icon;

    /**
     * Constructor for class.
     * @param rx x co-ordinate of token.
     */
    Destruction(int rx){
        super(rx);

        Image img = new Image("file:src/fireworks.png");
        icon = new ImageView(img);

        super.realg.getChildren().add(icon);
    }

    /**
     * Restores state on game being resumed.
     */
    public void restore(){
        super.restore();
        Image img = new Image("file:src/fireworks.png");
        icon = new ImageView(img);

        super.realg.getChildren().add(icon);
    }

}
