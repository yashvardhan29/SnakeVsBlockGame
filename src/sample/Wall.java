package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.io.Serializable;

/**
 * Wall class.
 */

public class Wall implements Serializable {
    /**
     * Stack pane in which wall is contained.
     */
    transient Rectangle realg;
    /**
     * Location of top right corner of stack pane.
     */
    Point location;
    /**
     * Length of wall.
     */
    double length;
    /**
     * Width of wall
     */
    private int width;

    /**
     * Constructor for class.
     * @param WIDTH Width of grid.
     */
    Wall(int WIDTH){
        width = 5;
        length = 310 - WIDTH/7;
        realg = new Rectangle(width,length);
        realg.setFill(Color.WHITE);
        realg.setArcWidth(width/4);
        realg.setArcHeight(width/4);
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
        realg = new Rectangle(width,length);
        realg.setFill(Color.WHITE);
        realg.setArcWidth(width/4);
        realg.setArcHeight(width/4);
        setPosition(location);
    }

}
