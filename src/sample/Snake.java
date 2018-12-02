package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Snake class.
 */
public class Snake implements Serializable {
    /**
     * Shape that references head of snake.
     */
    transient Circle head;

    /**
     * Text box for displaying length of snake on screen.
     */
    transient Text sldisp;

    /**
     * Length of snake
     */
    int length;

    /**
     * Location of head of snake
     */
    Point hlocation;

    /**
     * Locations of all parts of the snake.
     */
    ArrayList<Point> points;

    /**
     * References to all circles that make up the snake.
     */
    transient ArrayList<Circle> circles;

    /**
     * Reference to root.
     */
    private transient Pane root;

    /**
     * Color of body of snake.
     */
    transient Color color;

    /**
     * True if snake has magnet.
     */
    boolean hasMagnet;

    /**
     * True if snake has shield.
     */
    boolean hasShield;

    /**
     * List of possible colors that body of snake can take.
     */
    private transient Color colorList[];

    /**
     * Radius of circles that make up the snake.
     */
    private int side;

    /**
     * Number corresponding to index in array of colors.
     */
    int colorNo;

    /**
     * x-velocity of snake.
     */
    int xvel;

    /**
     * y-velocity of snake.
     */
    int yvel;

    /**
     * Constructor of class.
     * @param WIDTH Width of Grid.
     * @param HEIGHT Height of Grid.
     * @param s Diameter of snake.
     * @param root Reference to root pane.
     */
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

    /**
     * Sets velocity to 0.
     */
    public void stopSnake(){
        xvel = 0;
        yvel = 0;
    }

    /**
     * Getter for color number.
     * @return Returns colorNo
     */
    public int getColorNo() {
        return colorNo;
    }

    /**
     * Reverses direction of snake.
     */
    public void reverseSnake(){
        yvel =0;
        xvel = xvel*(-1);
    }

    /**
     * Updates text box with length of snake.
     */
    public void updateSnakeLengthDisp(){
        String sl = Integer.toString(length);
        sldisp.setText(sl);
    }

    /**
     * Restores snake if game is resumed.
     * @param root
     */
    public void restore(Pane root){
        setupHead(hlocation,side);
        colorList = new Color[]{Color.YELLOW, Color.GREEN,Color.PURPLE,Color.PINK,Color.WHITE};
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

    /**
     * Sets color of body of snake.
     * @param c Integer corresponding to index of array of colors.
     */
    public void setColor(int c) {
        color = colorList[c];
        System.out.println(c);
    }

    /**
     * Sets up head of snake.
     * @param location Location of head of snake.
     * @param side Diameter of head of snake.
     */
    public void setupHead(Point location, int side){
        head = new Circle(side/2);
        head.setFill(Color.RED);
        head.setLayoutX(location.getX());
        head.setLayoutY(location.getY());
    }

    /**
     * Increases length of snake.
     * @param radius Radius of snake.
     */
    public void incLength(int radius){
        if(length < 19){
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

    /**
     * Decreases length of snake.
     * @param todecr Amount by which length is to be decreased.
     */
    public void decrLength(int todecr){
        int newlength = length - todecr;
        if(newlength < 19){
            if(length >= 20){
             todecr = 19 - newlength;
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
