package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.Serializable;
import java.util.Random;

public class Coin extends Token implements Serializable {
    int valOfCoin; //Value of coin
    transient Circle coin; //Circle that represents the coin
    transient Text value;
    int radius;

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

    public void restore(){
        super.restore();
        coin = new Circle(radius);
        coin.setFill(Color.YELLOW);

        String ftext = Integer.toString(valOfCoin);
        value = new Text(ftext);

        super.realg.getChildren().addAll(coin,value);

    }

}
