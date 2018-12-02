package sample;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.io.Serializable;
import java.util.Random;

/**
 * Block class.
 */
public class Block implements Serializable {
    /**
     * Displays numerical valOfCoin of block and is added to StackPane.
     */
    private transient Text value;
    /**
     * Integer storing numerical valOfCoin of block.
     */
    int valOfBlock;

    /**
     * Rectangle representing block that is added to StackPane.
     */
    private transient Rectangle block;

    /**
     * StackPane that is added to the pane.
     */
    transient StackPane realg;

    /**
     * Stores location of top left corner of block.
     */
    Point location;

    /**
     * Array that has 7 colors. Helps in generation of random colours for blocks.
     */
    private transient Color colors[];

    /**
     * Width of Grid.
     */
    private int DIM;


    /**
     * Constructor of class.
     * @param D Width of grid.
     * @param p Starting location of StackPane.
     */
    Block(int D,Point p){

        location = p;

        setupColors();
        DIM = D;
        Random rand = new Random();
        int col = rand.nextInt(7);

        setupRectangle(DIM,col,location);

        realg = new StackPane(); //Setting up StackPane
        value = new Text(); //Setting up TextHolder

        realg.getChildren().addAll(block,value); //Adding Rectangle and TextHolder to StackPane.

    }


    /**
     * Restores block if resume game is called.
     */
    public void restore(){
        setupColors();
        Random rand = new Random();
        int col = rand.nextInt(7);
        setupRectangle(DIM,col,location);
        realg = new StackPane(); //Setting up StackPane
        setPosition(location);
        value = new Text(); //Setting up TextHolder
        this.setValue(valOfBlock);
        realg.getChildren().addAll(block,value); //Adding Rectangle and TextHolder to StackPane.
    }

    /**
     * Helper method to setup block
     * @param DIM Width of grid.
     * @param col Number associated with color.
     * @param p Location of block.
     */
    private void setupRectangle(int DIM,int col,Point p){
        block = new Rectangle(DIM/7,DIM/7);
        block.setLayoutX(p.getX());
        block.setLayoutY(p.getY());
        block.setFill(colors[col]);
        block.setArcHeight(DIM/14);
        block.setArcWidth(DIM/14);

    }

    /**
     * Setter for position.
     * @param p Point.
     */
    public void setPosition(Point p){
       realg.setLayoutX(p.getX());
       realg.setLayoutY(p.getY());
    }

    /**
     * Sets up array of colors
     */
    private void setupColors(){
        colors = new Color[7];
        colors[0] = Color.ALICEBLUE;
        colors[1] = Color.ANTIQUEWHITE;
        colors[2] = Color.MAGENTA;
        colors[3] = Color.DARKORANGE;
        colors[4] = Color.GREEN;
        colors[5] = Color.CORAL;
        colors[6] = Color.BROWN;
    }

    /**
     * Sets value of block.
     * @param val
     */
    public void setValue(int val){
        String toSet = Integer.toString(val);
        value.setText(toSet);
    }
}
