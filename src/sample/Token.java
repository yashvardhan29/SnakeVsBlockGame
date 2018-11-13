package sample;

import javafx.scene.layout.StackPane;

import java.io.Serializable;

public class Token implements Serializable {
    transient StackPane realg;
    Point location;
    int rx;

    Token(int r){
        rx = r;
        realg = new StackPane();
        location = new Point(rx,0);
        setPosition(location);
    }

    public void setPosition(Point p){
        realg.setLayoutX(p.getX());
        realg.setLayoutY(p.getY());
    }

    public void restore(){
        realg = new StackPane();
        location = new Point(rx,0);
        setPosition(location);
    }
}
