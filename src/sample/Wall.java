package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Wall {
    Rectangle realg;
    Point location;
    Block theblocks[];
    int length;
    int width;

    Wall(Block theblocks[]){
        width = 5;
        length = 100;
        realg = new Rectangle(width,length);
        realg.setFill(Color.RED);
        this.theblocks = theblocks;
        for(int i = 0;i<theblocks.length;i++){
            if(theblocks[i] != null){
                Block currb = theblocks[i];
                location = new Point(currb.location.getX()+50,currb.location.getY()+50);
                setPosition(location);
            }
        }

    }

    public void setPosition(Point p){
        realg.setLayoutX(p.getX());
        realg.setLayoutY(p.getY());
    }


}
