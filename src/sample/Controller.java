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

import java.util.ArrayList;
import java.util.Random;


public class Controller {
    Pane root; // The Parent container that contains everything else. This is directly added to the scene.
    int diameter = 20; // Diameter of snake
    int WIDTH = 500; // Width of grid in pixels.
    int HEIGHT = 500; // Height of grid in pixels.

    int xvel; // x-velocity of snake.
    int yvel; // y-velocity of snake.

    int Coin_count; //Number of coins currently on screen.

    Coin coins[]; // Array that stores references to the coins on screen.
    Block theblocks[]; //Array that stores references to the blocks on screen.
    Magnet magnet; // Reference to the magnet on screen.
    Shield shield; // Reference to the shield on screen.
    Destruction destruction; // Reference to the destruction token on screen.
    Wall wall; // Solitary wall only for purpose of deadline 2.

    Point coinlocs[]; //Location of coins.

    Snake snake; //Reference to the snake.

    boolean WallIsPresent;  // True if wall is present on screen.
    boolean BlockIsPresent; // True if even a single block is present on screen.
    boolean MagnetIsPresent; // True if Magnet is present on screen.
    boolean ShieldIsPresent; // True if Shield is present on screen.
    boolean DestructionIsPresent; // True if Destruction Token is present on screen.

    boolean isAlive; // True while snake is alive.

    int score;
    Text score_text;

    ChoiceBox<String> cb;

    Controller(){
        root = new Pane();
        root.setStyle("-fx-background-color: black"); //Setting colour of Pane to Black.

        score = 0;
        Coin_count = 0;

        setupChoiceBox();
        setupSnake();
        setupScoreDisplay();
        InitialiseBooleans();
        setupObjectArrays();

        startAnimationTimers();


    }

    private void setupObjectArrays(){
        theblocks = new Block[10];
        coins = new Coin[5];
        coinlocs = new Point[5];
    }

    private void InitialiseBooleans(){
        BlockIsPresent = false;
        WallIsPresent = false;
        MagnetIsPresent = false;
        ShieldIsPresent = false;
        DestructionIsPresent = false;
        isAlive = true;
    }

    private void setupScoreDisplay(){
        score_text = new Text(475,13,"0");
        score_text.setFill(Color.WHITE);
        score_text.setFont(new Font(15));
        root.getChildren().add(score_text);
    }

    private void setupSnake(){
        snake = new Snake(WIDTH,HEIGHT,diameter,root);
        xvel = 0;
        yvel = 0;
        root.getChildren().add(snake.head);
    }

    private void setupChoiceBox(){
        cb = new ChoiceBox<>();
        cb.getItems().addAll("Restart Game", "Quit Game");
        cb.setLayoutX(0);
        cb.setLayoutY(0);
        cb.setValue("Restart Game");
        cb.setBackground(Background.EMPTY);
        String style = "-fx-background-color: rgba(255,255,255);";
        cb.setStyle(style);
        root.getChildren().add(cb);
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

    public Pane getRoot() {
        return root;
    }

    public void SnakeAnimation(){
        //Calls the class SnakeHandler
        KeyFrame kf = new KeyFrame(Duration.millis(50),new SnakeHandler());
        Timeline timeline = new Timeline(kf);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void CoinAnimation(){
        //Calls the class CoinHandler
        KeyFrame kf = new KeyFrame(Duration.millis(1200),new CoinHandler());
        Timeline timeline = new Timeline(kf);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void BlockAnimation(){
        //Calls the class BlockHandler
        KeyFrame kf = new KeyFrame(Duration.millis(500),new BlockHandler());
        Timeline timeline = new Timeline(kf);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void MagnetAnimation(){
        KeyFrame kf = new KeyFrame(Duration.seconds(15),new MagnetHandler());
        Timeline timeline = new Timeline(kf);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void ShieldAnimation(){
        KeyFrame kf = new KeyFrame(Duration.seconds(30),new ShieldHandler());
        Timeline timeline = new Timeline(kf);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void DestructionAnimation(){
        KeyFrame kf = new KeyFrame(Duration.seconds(45),new DestructionHandler());
        Timeline timeline = new Timeline(kf);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void ObjectMover(){
        //Calls the class ObjectHandler
        KeyFrame kf = new KeyFrame(Duration.millis(100),new ObjectHandler());
        Timeline timeline = new Timeline(kf);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


    private class SnakeHandler implements EventHandler<ActionEvent>{
        /*Checks the velocity of snake and moves it accordingly.
          Also calls the collision checker.
         */
        public void handle(ActionEvent event){
            CollisionCheck();
            MoveSnake();
            CheckIfAlive();
            UpdateScore();
        }
    }

    private class CoinHandler implements EventHandler<ActionEvent>{
        //Calls the SpawnCoins() method
        public void handle(ActionEvent event){
            SpawnCoins();
        }
    }

    private class BlockHandler implements EventHandler<ActionEvent>{
        //Calls the SpawnBLocks() and SpawnWalls() method.
        public void handle(ActionEvent event){
            SpawnBlocks();
            SpawnWalls();
        }
    }

    private class MagnetHandler implements EventHandler<ActionEvent>{
        //Calls the SpawnMagnet() method
        public void handle(ActionEvent event){
            SpawnMagnet();
        }
    }

    private class ShieldHandler implements EventHandler<ActionEvent>{
        //Calls the SpawnShield() method
        public void handle(ActionEvent event){
            SpawnShield();
        }
    }

    private class DestructionHandler implements EventHandler<ActionEvent>{
        //Calls the SpawnDestructionToken() method
        public void handle(ActionEvent event){
            SpawnDestructionToken();
        }
    }

    private class ObjectHandler implements EventHandler<ActionEvent>{
        // Calls methods to move both Blocks and Coins.
        public void handle(ActionEvent event){
            MoveBlocks();
            MoveCoins();
            MoveMagnet();
            MoveShield();
            MoveDestructionToken();
            MoveWalls();
        }
    }

    private void SpawnWalls(){
        if(!WallIsPresent){
            Wall thewall = new Wall(theblocks);
            root.getChildren().add(thewall.realg);
            WallIsPresent = true;
            wall = thewall;
        }
    }

    private void SpawnCoins(){
        // Spawns new Coins
        if(Coin_count < 5){
            for(int i = 0;i<5;i++){
                if(coins[i] == null){
                    Coin newcoin = new Coin(diameter /2,coinlocs);
                    root.getChildren().add(newcoin.realg);
                    Coin_count++;
                    coins[i] = newcoin;
                    coinlocs[i] = newcoin.location;
                }
            }
        }
    }

    private void SpawnBlocks(){
        // Spawns blocks
        if(!BlockIsPresent){
            for(int i = 0;i<10;i++){
                //50% chance of block spawning.
                Random rand = new Random();
                int decide = rand.nextInt(2);
                if(decide == 1){
                    Point bloc = new Point(i*(WIDTH/10),0);
                    Block curr = new Block(WIDTH,bloc);
                    root.getChildren().add(curr.realg);
                    theblocks[i] = curr;
                    BlockIsPresent = true;
                    int val = rand.nextInt(5) + 1;
                    curr.setValue(val);
                    curr.valOfBlock = val;
                }

            }
        }
    }

    private void SpawnShield(){
        if(!ShieldIsPresent){
            Shield theshield = new Shield();
            root.getChildren().add(theshield.realg);
            ShieldIsPresent = true;
            shield = theshield;
        }
    }

    private void SpawnMagnet(){
        if(!MagnetIsPresent){
            Magnet themagnet = new Magnet();
            root.getChildren().add(themagnet.realg);
            MagnetIsPresent = true;
            magnet = themagnet;
        }
    }

    private void SpawnDestructionToken(){
        if(!DestructionIsPresent){
            Destruction chaos = new Destruction();
            root.getChildren().add(chaos.realg);
            DestructionIsPresent = true;
            destruction = chaos;
        }
    }

    private void MoveBlocks(){
        //Moves the blocks downwards.
        if(BlockIsPresent){
            for(int i = 0;i<10;i++){
                if(theblocks[i] != null){
                    Block currb = theblocks[i];
                    currb.location.translate(0,1);
                    currb.setPosition(currb.location);
                    if(currb.location.getY() >= 480){
                        root.getChildren().remove(currb.realg);
                        theblocks[i] = null;
                        BlockIsPresent = false;
                    }
                    else BlockIsPresent = true;
                }
            }
        }
    }

    private void MoveCoins(){
        //Moves the coins downwards
        if(Coin_count != 0){
            for(int i = 0;i<5;i++){
                Coin currc = coins[i];
                if(currc != null){
                    currc.location.translate(0,1);
                    currc.setPosition(currc.location);
                    if(currc.location.getY() >= 490){
                        root.getChildren().remove(currc.realg);
                        coins[i] = null;
                        coinlocs[i] = null;
                        Coin_count--;
                    }
                }
            }
        }
    }

    private void MoveMagnet(){
        if(MagnetIsPresent){
            magnet.location.translate(0,1);
            magnet.setPosition(magnet.location);
            if(magnet.location.getY() >= 490){
                root.getChildren().remove(magnet.realg);
                magnet = null;
                MagnetIsPresent = false;
            }
        }
    }

    private void MoveShield(){
        if(ShieldIsPresent){
            shield.location.translate(0,1);
            shield.setPosition(shield.location);
            if(shield.location.getY() >= 490){
                root.getChildren().remove(shield.realg);
                shield = null;
                ShieldIsPresent = false;
            }
        }
    }

    private void MoveDestructionToken(){
        if(DestructionIsPresent){
            destruction.location.translate(0,1);
            destruction.setPosition(destruction.location);
            if(destruction.location.getY() >= 490){
                root.getChildren().remove(destruction.realg);
                destruction = null;
                DestructionIsPresent = false;
            }
        }
    }

    private void MoveWalls(){
        if(WallIsPresent){
            wall.location.translate(0,1);
            wall.setPosition(wall.location);
            if(wall.location.getY() >= 490){
                root.getChildren().remove(wall.realg);
                wall = null;
                WallIsPresent = false;
            }
        }
    }

    private void MoveSnake(){
        //Shifts all parts of the snakes body left or right.
        ArrayList<Point> spoints = snake.points;
        ArrayList<Circle> scircles = snake.circles;
        for(int i = 0;i<spoints.size();i++){
            Point currp = spoints.get(i);
            currp.translate(xvel,yvel);
            Circle currc = scircles.get(i);
            currc.setLayoutX(currp.getX());
            currc.setLayoutY(currp.getY());
        }
    }

    private void CollisionCheck(){
        //Checks for Collisions between Snake and Objects
        CheckCoinCollision();

        // Code for collision b/w Block and Snake.
        CheckBlockCollision();
    }

    private void CheckCoinCollision(){
        for(int i = 0;i<5;i++){
            if(coins[i] != null){
                Point cloc = coins[i].location;
                Point hloc = snake.hlocation;
                if(cloc.getY() + diameter == hloc.getY()){

                    if(cloc.getX() == hloc.getX()){
                        ConsumeCoin(i,coins[i]);
                    }
                }
                else if((hloc.getY() - cloc.getY() <= 15 && hloc.getY() - cloc.getY() >= 0) || (hloc.getY() - cloc.getY() >= 15 && hloc.getY() - cloc.getY() <= 0)){
                    int xdiff = hloc.getX() - cloc.getX();
                    if(xdiff >= 0){
                        if(xdiff <= 10) ConsumeCoin(i,coins[i]);
                    }
                    else{
                        if(xdiff >= -10) ConsumeCoin(i,coins[i]);
                    }
                }
            }
        }
    }

    private void CheckBlockCollision(){
        for(int i = 0;i<10;i++){
            if(theblocks[i] != null){
                Block currb = theblocks[i];
                int lbx = currb.location.getX();
                int ubx = lbx + (WIDTH/10);
                int yb = currb.location.getY() + (HEIGHT/10);
                if(lbx<=snake.hlocation.getX() && ubx>=snake.hlocation.getX()){
                    if(snake.hlocation.getY() + diameter/2 == yb){
                        int val = currb.valOfBlock;
                        if(val <= snake.length){
                            //System.out.println(i+"i");
                            snake.decrLength(val);
                            score += val;
                            root.getChildren().remove(theblocks[i].realg);
                            theblocks[i] = null;
                        }
                        else isAlive = false;
                    }
                }
            }
        }
    }

    private void ConsumeCoin(int i,Coin currc){
        for(int j = 0;j<currc.valOfCoin;j++) snake.incLength(diameter /2);
        root.getChildren().remove(coins[i].realg);
        coins[i] = null;
        coinlocs[i] = null;
        Coin_count--;
    }

    private void UpdateScore(){
        String scorestring = Integer.toString(score);
        score_text.setText(scorestring);
    }

    // Useless for now.
    private void CheckIfAlive(){
        if(!isAlive){
         System.exit(0);
        }
    }

}
