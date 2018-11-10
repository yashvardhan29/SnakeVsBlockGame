package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


public class Controller implements Serializable {
    Pane root;
    Grid grid;

    Timeline snakeTimeline, coinTimeline, blockTimeline, magnetTimeline, shieldTimeline, destructTimeline, omtimeline;

    Database database;

    Controller(){
        root = new Pane();
        startAnimationTimers();
        grid = new Grid(root,snakeTimeline, coinTimeline, blockTimeline, magnetTimeline, shieldTimeline, destructTimeline, omtimeline);
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public Grid getGrid() {
        return grid;
    }

    private void startAnimationTimers(){
        this.SnakeAnimation();
        this.CoinAnimation();
        this.BlockAnimation();
        this.MagnetAnimation();
        this.ShieldAnimation();
        this.DestructionAnimation();
        this.ObjectMover();
    }

    public void SnakeAnimation(){
        //Calls the class SnakeHandler
        KeyFrame kf = new KeyFrame(Duration.millis(50),new SnakeHandler());
        snakeTimeline = new Timeline(kf);
        snakeTimeline.setCycleCount(Animation.INDEFINITE);
        snakeTimeline.play();
    }

    public void CoinAnimation(){
        //Calls the class CoinHandler
        KeyFrame kf = new KeyFrame(Duration.millis(1200),new CoinHandler());
        coinTimeline = new Timeline(kf);
        coinTimeline.setCycleCount(Animation.INDEFINITE);
        coinTimeline.play();

    }

    public void BlockAnimation(){
        //Calls the class BlockHandler
        KeyFrame kf = new KeyFrame(Duration.millis(500),new BlockHandler());
        blockTimeline = new Timeline(kf);
        blockTimeline.setCycleCount(Animation.INDEFINITE);
        blockTimeline.play();
    }

    public void MagnetAnimation(){
        KeyFrame kf = new KeyFrame(Duration.seconds(15),new MagnetHandler());
        magnetTimeline = new Timeline(kf);
        magnetTimeline.setCycleCount(Animation.INDEFINITE);
        magnetTimeline.play();
    }

    public void ShieldAnimation(){
        KeyFrame kf = new KeyFrame(Duration.seconds(30),new ShieldHandler());
        shieldTimeline = new Timeline(kf);
        shieldTimeline.setCycleCount(Animation.INDEFINITE);
        shieldTimeline.play();
    }

    public void DestructionAnimation(){
        KeyFrame kf = new KeyFrame(Duration.seconds(45),new DestructionHandler());
        destructTimeline = new Timeline(kf);
        destructTimeline.setCycleCount(Animation.INDEFINITE);
        destructTimeline.play();
    }

    public void ObjectMover(){
        //Calls the class ObjectHandler
        KeyFrame kf = new KeyFrame(Duration.millis(100),new ObjectHandler());
        omtimeline = new Timeline(kf);
        omtimeline.setCycleCount(Animation.INDEFINITE);
        omtimeline.play();
    }

    private class SnakeHandler implements EventHandler<ActionEvent>{
        /*Checks the velocity of snake and moves it accordingly.
          Also calls the collision checker.
         */
        public void handle(ActionEvent event){
            grid.CollisionCheck();
            grid.MoveSnake();
            grid.CheckIfAlive();
            grid.UpdateScore();
            grid.UpdateTokenValidity();
        }
    }

    private class CoinHandler implements EventHandler<ActionEvent>{
        //Calls the SpawnCoins() method
        public void handle(ActionEvent event){
            grid.SpawnCoins();
        }
    }

    private class BlockHandler implements EventHandler<ActionEvent>{
        //Calls the SpawnBLocks() and SpawnWalls() method.
        public void handle(ActionEvent event){
            grid.SpawnBlocks();
            grid.SpawnWalls();
        }
    }

    private class MagnetHandler implements EventHandler<ActionEvent>{
        //Calls the SpawnMagnet() method
        public void handle(ActionEvent event){
            grid.SpawnMagnet();
        }
    }

    private class ShieldHandler implements EventHandler<ActionEvent>{
        //Calls the SpawnShield() method
        public void handle(ActionEvent event){
            grid.SpawnShield();
        }
    }

    private class DestructionHandler implements EventHandler<ActionEvent>{
        //Calls the SpawnDestructionToken() method
        public void handle(ActionEvent event){
            grid.SpawnDestructionToken();
        }
    }

    private class ObjectHandler implements EventHandler<ActionEvent>{
        // Calls methods to move both Blocks and Coins.
        public void handle(ActionEvent event){
            grid.MoveBlocks();
            grid.MoveCoins();
            grid.MoveMagnet();
            grid.MoveShield();
            grid.MoveDestructionToken();
            grid.MoveWalls();
        }
    }

    public Pane getRoot(){
        return root;
    }

}
