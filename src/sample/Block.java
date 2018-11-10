package sample;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.Serializable;
import java.util.Random;

public class Block implements Serializable {
    Text value; //Displays numerical valOfCoin of block and is added to StackPane.
    int valOfBlock; //Integer storing numerical valOfCoin of block.
    Rectangle block; //Rectangle representing block that is added to StackPane.
    StackPane realg; // StackPane that is added to the pane.
    Point location; //Stores location of top left corner of block.
    Color colors[]; //Array that has 7 colors. Helps in generation of random colours for blocks.

    Block(int DIM,Point p){

        location = p;

        setupColors();

        Random rand = new Random();
        int col = rand.nextInt(7);

        setupRectangle(DIM,col,location);

        realg = new StackPane(); //Setting up StackPane
        value = new Text(); //Setting up TextHolder

        realg.getChildren().addAll(block,value); //Adding Rectangle and TextHolder to StackPane.

    }

    private void setupRectangle(int DIM,int col,Point p){
        block = new Rectangle(DIM/7,DIM/7);
        block.setLayoutX(p.getX());
        block.setLayoutY(p.getY());
        block.setFill(colors[col]);
        block.setArcHeight(DIM/20);
        block.setArcWidth(DIM/20);

    }

    public void setPosition(Point p){
       realg.setLayoutX(p.getX());
       realg.setLayoutY(p.getY());
    }

    public void setupColors(){
        colors = new Color[7];
        colors[0] = Color.ALICEBLUE;
        colors[1] = Color.ANTIQUEWHITE;
        colors[2] = Color.MAGENTA;
        colors[3] = Color.DARKORANGE;
        colors[4] = Color.GREEN;
        colors[5] = Color.CORAL;
        colors[6] = Color.BROWN;
    }

    public void setValue(int val){
        String toSet = Integer.toString(val);
        value.setText(toSet);
    }
}
