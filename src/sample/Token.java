package sample;

import javafx.scene.layout.StackPane;
import java.io.Serializable;

/**
 * Token Class. Superclass for Magnet, Coin, Shield and Destruction.
 */

public class Token implements Serializable {
    /**
     * Stack pane in which token is contained.
     */
    transient StackPane realg;
    /**
     * Location of top right corner of stack pane.
     */
    Point location;
    /**
     * x co-ordinate of stack pane.
     */
    int rx;

    /**
     * Constructor for class.
     * @param r Value to be assigned to variable rx.
     */
    Token(int r){
        rx = r;
        realg = new StackPane();
        location = new Point(rx,0);
        setPosition(location);
    }

    /**
     * Sets position of Stack Pane.
     * @param p Point to which stack pane is to be aligned.
     */
    public void setPosition(Point p){
        realg.setLayoutX(p.getX());
        realg.setLayoutY(p.getY());
    }

    /**
     * Restores state of Stack Pane on game being resumed.
     */
    public void restore(){
        realg = new StackPane();
    }
}
