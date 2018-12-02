package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import java.io.Serializable;
import java.util.Random;

/**
 * Coin class. Subclass of class Token.
 */

public class Coin extends Token implements Serializable {
    /**
     * Value of coin.
     */
    int valOfCoin;

    /**
     * Circle that represents the coin.
     */
    private transient Circle coin;

    /**
     * Text box that displays value of coin.
     */
    private transient Text value;

    /**
     * Radius of coin.
     */
    private int radius;

    /**
     * Constructor of class.
     * @param rd Radius of coin.
     * @param rx x co-ordinate of stack pane.
     */
    Coin(int rd,int rx){
        super(rx);
        radius = rd;
        //Assignment of random valOfCoin to coin
        Random rand = new Random();
        valOfCoin = rand.nextInt(5) + 1;

        //Setting up the circle
        coin = new Circle(radius);
        coin.setFill(Color.YELLOW);

        String ftext = Integer.toString(valOfCoin);
        value = new Text(ftext);

        super.realg.getChildren().addAll(coin,value);
    }

    /**
     * Restores coin if game is resumed.
     */
    public void restore(){
        super.restore();
        coin = new Circle(radius);
        coin.setFill(Color.YELLOW);

        String ftext = Integer.toString(valOfCoin);
        value = new Text(ftext);

        super.realg.getChildren().addAll(coin,value);

    }

}
