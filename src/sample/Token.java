package sample;

import javafx.scene.layout.StackPane;

import java.io.Serializable;

public class Token implements Serializable {
    StackPane realg;
    Point location;

    Token(int rx){
        realg = new StackPane();
        location = new Point(rx,0);
        setPosition(location);
    }

    public void setPosition(Point p){
        realg.setLayoutX(p.getX());
        realg.setLayoutY(p.getY());
    }
}
