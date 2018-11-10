package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.Serializable;
import java.util.Random;

public class Coin extends Token implements Serializable {
    int valOfCoin; //Value of coin
    Circle coin; //Circle that represents the coin
    Text value;

    Coin(int radius,int rx){
        super(rx);
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

}
