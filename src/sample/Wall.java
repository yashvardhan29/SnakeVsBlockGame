package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public class Wall implements Serializable {
    transient Rectangle realg;
    Point location;
    double length;
    int width;

    Wall(int WIDTH){
        width = 5;
        length = 310 - WIDTH/7;
        realg = new Rectangle(width,length);
        realg.setFill(Color.WHITE);
        realg.setArcWidth(width/4);
        realg.setArcHeight(width/4);
    }

    public void setPosition(Point p){
        realg.setLayoutX(p.getX());
        realg.setLayoutY(p.getY());
    }

    public void restore(){
        realg = new Rectangle(width,length);
        realg.setFill(Color.WHITE);
        realg.setArcWidth(width/4);
        realg.setArcHeight(width/4);
        setPosition(location);
    }

}
