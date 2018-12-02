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

/**
 * Controller class.
 */
public class Controller implements Serializable {
    /**
     * root associated with the game
     */
    transient Pane root;
    /**
     * component grid class
     */
    Grid grid;
    /**
     * respective timelines
     */
    transient Timeline snakeTimeline, coinTimeline, blockTimeline, magnetTimeline, shieldTimeline, destructTimeline, omtimeline,collTimeline, changeCheckTimeline;
    /**
     * parent database
     */
    Database database;
    /**
     * animation duration for object animation
     */
    int objectanimdur;

    Controller(){
        root = new Pane();
        grid = new Grid(root,snakeTimeline, coinTimeline, blockTimeline, magnetTimeline, shieldTimeline, destructTimeline, omtimeline);
        objectanimdur = 15;
    }

    /**
     * Used to restore controller class after deserialization
     */
    public void restore(){
        root = new Pane();
        grid.restore(root);
        startAnimationTimers();
        grid.setTimelines(snakeTimeline, coinTimeline, blockTimeline, magnetTimeline, shieldTimeline, destructTimeline, omtimeline);
    }

    /**
     * mutator function for database
     * @param database
     */
    public void setDatabase(Database database) {
        this.database = database;
    }

    /**
     * Getter function for Grid
     * @return
     */
    public Grid getGrid() {
        return grid;
    }

    /**
     * starts animation timers
     */
    public void startAnimationTimers(){
        this.SnakeAnimation();
        this.CoinAnimation();
        this.BlockAnimation();
        this.MagnetAnimation();
        this.ShieldAnimation();
        this.DestructionAnimation();
        this.ObjectMover();
        this.CollisionCheckingTimer();
        this.ChangeListener();

        grid.setTimelines(snakeTimeline, coinTimeline, blockTimeline, magnetTimeline, shieldTimeline, destructTimeline, omtimeline);
    }

    /**
     * initializes checkChangeTimeline
     */
    public void ChangeListener(){
        KeyFrame kf = new KeyFrame(Duration.millis(1),new ChangeObserver());
        changeCheckTimeline = new Timeline(kf);
        changeCheckTimeline.setCycleCount(Animation.INDEFINITE);
        changeCheckTimeline.play();
    }

    /**
     * initializes snakeTimes
     */
    public void SnakeAnimation(){
        //Calls the class SnakeHandler
        KeyFrame kf = new KeyFrame(Duration.millis(7.5),new SnakeHandler());
        snakeTimeline = new Timeline(kf);
        snakeTimeline.setCycleCount(Animation.INDEFINITE);
        snakeTimeline.play();
    }

    /**
     * initializes collTimeline
     */
    public void CollisionCheckingTimer(){
        KeyFrame kf = new KeyFrame(Duration.millis(1),new CollHandler());
        collTimeline = new Timeline(kf);
        collTimeline.setCycleCount(Animation.INDEFINITE);
        collTimeline.play();
    }

    /**
     * initializes coinTimeline
     */
    public void CoinAnimation(){
        //Calls the class CoinHandler
        KeyFrame kf = new KeyFrame(Duration.millis(1200),new CoinHandler());
        coinTimeline = new Timeline(kf);
        coinTimeline.setCycleCount(Animation.INDEFINITE);
        coinTimeline.play();

    }

    /**
     * intializes blockTimeline
     */
    public void BlockAnimation(){
        //Calls the class BlockHandler
        KeyFrame kf = new KeyFrame(Duration.millis(100),new BlockHandler());
        blockTimeline = new Timeline(kf);
        blockTimeline.setCycleCount(Animation.INDEFINITE);
        blockTimeline.play();
    }

    /**
     * initializes magnetTimeline
     */
    public void MagnetAnimation(){
        KeyFrame kf = new KeyFrame(Duration.seconds(20),new MagnetHandler());
        magnetTimeline = new Timeline(kf);
        magnetTimeline.setCycleCount(Animation.INDEFINITE);
        magnetTimeline.play();
    }

    /**
     * initializes shieldTimeline
     */
    public void ShieldAnimation(){
        KeyFrame kf = new KeyFrame(Duration.seconds(30),new ShieldHandler());
        shieldTimeline = new Timeline(kf);
        shieldTimeline.setCycleCount(Animation.INDEFINITE);
        shieldTimeline.play();
    }

    /**
     * initializes destructionTimeline
     */
    public void DestructionAnimation(){
        KeyFrame kf = new KeyFrame(Duration.seconds(45),new DestructionHandler());
        destructTimeline = new Timeline(kf);
        destructTimeline.setCycleCount(Animation.INDEFINITE);
        destructTimeline.play();
    }

    /**
     * initializes omTimeline
     */
    public void ObjectMover(){
        //Calls the class ObjectHandler
        KeyFrame kf = new KeyFrame(Duration.millis(15),new ObjectHandler()); //Prev Value 100
        omtimeline = new Timeline(kf);
        omtimeline.setCycleCount(Animation.INDEFINITE);
        omtimeline.play();
    }

    private class SnakeHandler implements EventHandler<ActionEvent>{
        /**
         * Checks the velocity of snake and moves it accordingly.
         * Also calls the collision checker.
         * @param event
         */
        public void handle(ActionEvent event){
            grid.MoveSnake();
            grid.CheckIfAlive();
            grid.UpdateScore();
            grid.UpdateTokenValidity();
        }
    }

    private class CollHandler implements EventHandler<ActionEvent>{
        /**
         * checks collisions with objects
         * @param event
         */
        public void handle(ActionEvent event){
            grid.CollisionCheck();
        }
    }

    private class CoinHandler implements EventHandler<ActionEvent>{
        /**
         * Calls the SpawnCoins() method
         */
        public void handle(ActionEvent event){
            grid.SpawnCoins();
        }
    }

    private class BlockHandler implements EventHandler<ActionEvent>{
        /**
         * Calls the SpawnBLocks() and SpawnWalls() method.
         */
        public void handle(ActionEvent event){
            grid.SpawnBlocks();
            grid.SpawnWalls();
        }
    }

    private class MagnetHandler implements EventHandler<ActionEvent>{
        /**
         *Calls the SpawnMagnet() method
         * @param event
         */
        public void handle(ActionEvent event){
            grid.SpawnMagnet();
        }
    }

    private class ShieldHandler implements EventHandler<ActionEvent>{
        /**
         * Calls the SpawnShield() method
         * @param event
         */
        public void handle(ActionEvent event){
            grid.SpawnShield();
        }
    }

    private class DestructionHandler implements EventHandler<ActionEvent>{
        /**
         * Calls the SpawnDestructionToken() method
         * @param event
         */
        public void handle(ActionEvent event){
            grid.SpawnDestructionToken();
        }
    }

    private class ObjectHandler implements EventHandler<ActionEvent>{
        /**
         * Calls methods to move both Blocks and Coins.
         * @param event
         */
        public void handle(ActionEvent event){
            grid.MoveBlocks();
            grid.MoveCoins();
            grid.MoveMagnet();
            grid.MoveShield();
            grid.MoveDestructionToken();
            grid.MoveWalls();
        }
    }

    private class ChangeObserver implements EventHandler<ActionEvent>{
        /**
         * checks for changes
         * @param event
         */
        public void handle(ActionEvent event){
            grid.CheckForChange();
        }
    }

    /**
     * Getter method for root
     * @return
     */
    public Pane getRoot(){
        return root;
    }

}
