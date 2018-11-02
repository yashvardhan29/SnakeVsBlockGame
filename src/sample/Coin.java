package sample;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.Random;

public class Coin {
    int valOfCoin; //Value of coin
    Circle coin; //Circle that represents the coin
    Point location; //Location of centre of Circle
    Text value;
    StackPane realg;


    Coin(int radius,Point coinlocs[]){
        //Assignment of random valOfCoin to coin
        Random rand = new Random();
        valOfCoin = rand.nextInt(5) + 1;

        //Setting up the circle
        coin = new Circle(radius);
        coin.setFill(Color.YELLOW);
        realg = new StackPane();
        String ftext = Integer.toString(valOfCoin);
        value = new Text(ftext);

        realg.getChildren().addAll(coin,value);

        //Assigning random point to coin and shifting it
        int randx = rand.nextInt(48) + 1;
        randx *= 10;

        boolean nocollisions = false;
        while(!nocollisions){

            if(CoinsOverlap(coinlocs,randx)){
                randx = rand.nextInt(48) + 1;
                randx *= 10;
            }
            else{
                nocollisions = true;
            }
        }

        location = new Point(randx,0);
        realg.setLayoutX(randx);
        realg.setLayoutY(0);
    }

    public void setPosition(Point p){
        realg.setLayoutX(p.getX());
        realg.setLayoutY(p.getY());
    }

    private boolean CoinsOverlap(Point coinlocs[], int randx){
        for(int i = 0;i<coinlocs.length;i++){
            if(coinlocs[i] != null){
                int currcx = coinlocs[i].getX();
                if(currcx > randx){
                    if(currcx - randx <= 10){
                        return true;
                    }
                }
                else{
                    if(randx - currcx <= 10){
                        return true;
                    }
                }
            }

        }
        return false;
    }


}
