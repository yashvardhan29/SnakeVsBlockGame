package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;


public class Snake implements Serializable {
    transient Circle head; //Shape that references head of snake.
    transient Text sldisp;
    int length; //Length of snake
    Point hlocation; //Location of head of snake
    ArrayList<Point> points; //Locations of all parts of the snake.
    transient ArrayList<Circle> circles; //References to all circles that make up the snake.
    transient Pane root; //Reference to root.
    transient Color color;
    boolean hasMagnet;
    boolean hasShield;
    transient Color colorList[];
    int side;
    int colorNo;
    int xvel; // x-velocity of snake.
    int yvel; // y-velocity of snake.

    //   new


    Snake(int WIDTH,int HEIGHT,int s,Pane root){
        length = 0;
        side = s;
        sldisp = new Text(244,403,"0");
        sldisp.setFill(Color.WHITE);
        hlocation = new Point(WIDTH/2,HEIGHT/2);
        setupHead(hlocation,side);
        points = new ArrayList<>();
        circles = new ArrayList<>();
        hasMagnet = false;
        hasShield = false;
        colorList = new Color[]{Color.YELLOW, Color.GREEN,Color.PURPLE,Color.PINK,Color.WHITE};
        color = colorList[colorNo];
        points.add(hlocation);
        circles.add(head);
        this.root = root;
    }

    public void stopSnake(){
        xvel = 0;
        yvel = 0;
    }

    public int getColorNo() {
        return colorNo;
    }

    public void reverseSnake(){
        yvel =0;
        xvel = xvel*(-1);
    }

    public void updateSnakeLengthDisp(){
        String sl = Integer.toString(length);
        sldisp.setText(sl);
    }

    public void restore(Pane root){
        setupHead(hlocation,side);

        sldisp = new Text(244,403,"0");
        sldisp.setFill(Color.WHITE);
        updateSnakeLengthDisp();
        color = colorList[colorNo];
        circles = new ArrayList<>();
        circles.add(head);
        root.getChildren().add(head);
        for(int i = 1;i<points.size();i++){
            Circle dup = new Circle(10);
            dup.setFill(color);
            Point duploc = points.get(i);
            dup.setLayoutX(duploc.getX());
            dup.setLayoutY(duploc.getY());
            circles.add(dup);
            root.getChildren().add(dup);

        }

        this.root = root;
    }

    public void setColor(int c) {
        color = colorList[c];
        System.out.println(c);
    }

    //    public void changeColor(Color color1){
//        for(int i = 1;i<points.size();i++){
//            Circle dup = new Circle(10);
//            dup.setFill(color1);
//            Point duploc = points.get(i);
//            dup.setLayoutX(duploc.getX());
//            dup.setLayoutY(duploc.getY());
//            circles.add(dup);
//            root.getChildren().add(dup);
//
//        }
//    }

    public void setupHead(Point location, int side){
        head = new Circle(side/2);
        head.setFill(Color.RED);
        head.setLayoutX(location.getX());
        head.setLayoutY(location.getY());
    }

    public void incLength(int radius){
        if(length < 7){
            Point lpoint = points.get(points.size()-1);
            double newx = lpoint.getX();
            double newy = lpoint.getY() + 2*radius;
            Point npoint = new Point(newx,newy);
            Circle lp = new Circle(radius);
            lp.setFill(color);
            lp.setLayoutX(newx);
            lp.setLayoutY(newy);
            points.add(npoint);
            length++;
            circles.add(lp);
            root.getChildren().add(lp);
        }
        else {
            length++;
        }
    }

    //Function to decrease length.
    public void decrLength(int todecr){
        int newlength = length - todecr;
        if(newlength < 7){
            if(length >= 8){
             todecr = 7 - newlength;
            }
            for(int i = circles.size()-1;i > circles.size()-todecr-1;i--){
                root.getChildren().remove(circles.get(i));
            }
            int i = points.size()-1;
            int bound = points.size()- todecr -1;
            while(i > bound){
                points.remove(points.size()-1);
                circles.remove(circles.size()-1);
                i--;
            }
            length = newlength;
        }
        else length = newlength;
    }
}
