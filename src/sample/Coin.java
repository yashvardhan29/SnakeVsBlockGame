package sample;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.Random;

public class Coin extends Token{
    int valOfCoin; //Value of coin
    Circle coin; //Circle that represents the coin
    //Point location; //Location of centre of Circle
    Text value;
    //StackPane realg;


    Coin(int radius,int rx){
        super(rx);
        //Assignment of random valOfCoin to coin
        Random rand = new Random();
        valOfCoin = rand.nextInt(5) + 1;

        //Setting up the circle
        coin = new Circle(radius);
        coin.setFill(Color.YELLOW);
        //realg = new StackPane();
        String ftext = Integer.toString(valOfCoin);
        value = new Text(ftext);

        super.realg.getChildren().addAll(coin,value);


    }

}
