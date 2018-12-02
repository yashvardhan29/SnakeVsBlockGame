package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.Serializable;

/**
 * Shield class. Subclass of class Token.
 */
public class Shield extends Token implements Serializable {
    /**
     * Holds picture of shield.
     */
    transient ImageView icon;

    /**
     * Constructor for class.
     * @param rx x co-ordinate of token.
     */
    Shield(int rx){
        super(rx);
        Image img = new Image("file:src/antivirus.png");
        icon = new ImageView(img);
        super.realg.getChildren().add(icon);

    }

    /**
     * Restores state on game being resumed.
     */
    public void restore(){
        super.restore();
        Image img = new Image("file:src/antivirus.png");
        icon = new ImageView(img);
        super.realg.getChildren().add(icon);
    }


}
