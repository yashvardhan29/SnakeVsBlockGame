package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;


public class Snake {
    Circle head; //Shape that references head of snake.
    int length; //Length of snake
    Point hlocation; //Location of head of snake
    ArrayList<Point> points; //Locations of all parts of the snake.
    ArrayList<Circle> circles; //References to all circles that make up the snake.
    Pane root; //Reference to root.

    Snake(int WIDTH,int HEIGHT,int side,Pane root){
        length = 0;
        hlocation = new Point(WIDTH/2,HEIGHT/2);
        setupHead(hlocation,side);
        points = new ArrayList<>();
        circles = new ArrayList<>();
        points.add(hlocation);
        circles.add(head);
        this.root = root;
    }

    public void setupHead(Point location, int side){
        head = new Circle(side/2);
        head.setFill(Color.RED);
        head.setLayoutX(location.getX());
        head.setLayoutY(location.getY());
    }

    public void incLength(int radius){
        // Still not perfect
        if(length < 7){
            Point lpoint = points.get(points.size()-1);
            int newx = lpoint.getX();
            int newy = lpoint.getY() + 2*radius;
            Point npoint = new Point(newx,newy);
            Circle lp = new Circle(radius);
            lp.setFill(Color.YELLOW);
            lp.setLayoutX(newx);
            lp.setLayoutY(newy);
            points.add(npoint);
            length++;
            circles.add(lp);
            root.getChildren().add(lp);
            //System.out.println(length + "l");
        }
        else {
            length++;
            //System.out.println(length + "l");
        }
    }

    //Function to decrease length.
    public void decrLength(int todecr){
        // Still not perfect
        int newlength = length - todecr;
        if(newlength < 7){
            //System.out.println(length + "inilength");
            if(length >= 8){
             todecr = 7 - newlength;
            }
            for(int i = circles.size()-1;i > circles.size()-todecr-1;i--){
                root.getChildren().remove(circles.get(i));
                //length--;

            }
            int i = points.size()-1;
            int bound = points.size()- todecr -1;
            while(i > bound){
                points.remove(points.size()-1);
                circles.remove(circles.size()-1);
                i--;
            }
            length = newlength;
            //System.out.println(length + "final");
        }
        else length = newlength;
    }
}
