package sample;
/**
 *
 */

import javafx.animation.KeyFrame;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import javafx.animation.Timeline;
import javafx.util.Duration;


public class Grid implements Serializable {
    /**
     * The Parent container that contains everything else. This is directly added to the scene.
     */
    private transient Pane root;

    /**
     *  Diameter of snake head.
     */
    private final int diameter = 20;

    /**
     * Width of grid in pixels.
     */
    private final int WIDTH = 500;

    /**
     *  Height of grid in pixels.
     */
    private final int HEIGHT = 800;

    /**
     * True if animations are paused, false otherwise.
     */
    private boolean isPaused = false;

    /**
     * Reference to object of class Main.
     */
    private transient Main main;

    /**
     * Number of coins currently on screen.
     */
    private int Coin_count;

    /**
     * Array that stores references to the coins on screen.
     */
    private Coin coins[];

    /**
     * First Array that stores references to the blocks on screen.
     */
    private Block theblocks1[];

    /**
     * Second Array that stores references to the blocks on screen.
     */
    private Block theblocks2[];

    /**
     * Third Array that stores references to the blocks on screen.
     */
    private Block theblocks3[];

    /**
     * Arraylist that stores references to the walls on screen.
     */
    private ArrayList<Wall> thewalls;

    /**
     * Reference to the magnet on screen.
     */
    private Magnet magnet;

    /**
     * Reference to the shield on screen.
     */
    private Shield shield;

    /**
     * Reference to the destruction token on screen.
     */
    private Destruction destruction;

    /**
     * Reference to the snake.
     */
    Snake snake;

    /**
     * True if wall is present on screen.
     */
    private boolean WallIsPresent;

    /**
     * True if block row1 is present on screen.
     */
    private boolean BlockR1IsPresent;

    /**
     *  True if block row2 is present on screen.
     */
    private boolean BlockR2IsPresent;

    /**
     * True if block row3 is present on screen.
     */
    private boolean BlockR3IsPresent;

    /**
     * True if Magnet is present on screen.
     */
    private boolean MagnetIsPresent;

    /**
     * True if Shield is present on screen.
     */
    private boolean ShieldIsPresent;

    /**
     * True if Destruction Token is present on screen.
     */
    private boolean DestructionIsPresent;

    /**
     * True while snake is alive.
     */
    boolean isAlive;

    /**
     * Time at which magnet was activated.
     */
    private long MagnetActivatedAt = Long.MAX_VALUE;

    /**
     * Duration for which magnet remains active.
     */
    private final int MagnetDuration = 5000;

    /**
     * Time at which shield was activated.
     */
    private long ShieldActivatedAt = Long.MAX_VALUE;

    /**
     * Duration for which shield remains active.
     */
    private  final int ShieldDuration = 10000;

    /**
     * Counter for decreasing length of snake.
     */
    private int deccounter;

    /**
     * Stores references to all tokens on screen.
     */
    private ArrayList<Token> TokensOnScreen;

    /**
     * Score of player.
     */
    private int score;

    /**
     * Text box to display score of player.
     */
    private transient Text score_text;

    /**
     * Choice box with options to return to Main Menu or Restart Game.
     */
    private transient ChoiceBox<String> cb;

    private transient Timeline snakeTimeline, coinTimeline, blockTimeline, magnetTimeline, shieldTimeline, destructTimeline, omtimeline;

    /**
     * Reference to block of value greater than 5 on which collision is taking place.
     */
    private Block beingPounded;

    /**
     * Difficulty level of game.
     */
    private int difficulty = 0;

    /**
     * Loads burst GIF from src.
     */
    private transient Image img = new Image("file:src/Burst.gif",50,50,true,false);

    /**
     * Loads burst GIF into Image View.
     */
    private transient ImageView icon = new ImageView(img);

    /**
     * True if GIF is present on screen.
     */
    private boolean gifonscreen = false;

    /**
     * Time for which GIF has been on screen.
     */
    private long gifstime;

    /**
     * x co-ordinate for GIF
     */
    double bx;

    /**
     * y co-ordinate for GIF
     */
    double by;


    Grid(Pane root, Timeline t1, Timeline t2, Timeline t3, Timeline t4, Timeline t5, Timeline t6, Timeline t7){
        this.root = root;
        root.setStyle("-fx-background-color: black"); //Setting colour of Pane to Black.

        snakeTimeline = t1;
        coinTimeline = t2;
        blockTimeline = t3;
        magnetTimeline = t4;
        shieldTimeline = t5;
        destructTimeline = t6;
        omtimeline = t7;

        score = 0;
        Coin_count = 0;
        bx = 0; by = 0;
        gifstime = 0;
        deccounter = 0;

        setupChoiceBox();
        setupSnake();
        setupScoreDisplay();
        InitialiseBooleans();
        setupObjectArrays();



        cb.getSelectionModel()
                .selectedItemProperty()
                .addListener( (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {

                    if(newValue.equals("Restart Game")) {
                        //insert code here
                        resetGame();
                        cb.setValue("");

                    }
                    else {
                        cb.setValue("");
                        try{
                            main.start(main.PS);
                            main.r = true;
                            main.database.setShowResumeButton(false);
                        }catch (Exception e){
                            System.out.println("grid 99");
                        }
                    }
                } );
    }


    /**
     * Used for restart game functionality of drop down menu.
     */
    private void resetGame(){
        score = 0;
        Coin_count = 0;

        root.getChildren().clear();

        setupChoiceBox();
        setupSnake();
        setupScoreDisplay();
        InitialiseBooleans();
        setupObjectArrays();
    }

    /**
     * Setter method to set timeline variables of the class.
     * @param t1 Snake Timeline
     * @param t2 Coin Timeline
     * @param t3 Block Timeline
     * @param t4 Magnet Timeline
     * @param t5 Shield Timeline
     * @param t6 Destruction Timeline
     * @param t7 ObjectMover Timeline
     */
    public void setTimelines(Timeline t1, Timeline t2, Timeline t3, Timeline t4, Timeline t5, Timeline t6, Timeline t7) {
        snakeTimeline = t1;
        coinTimeline = t2;
        blockTimeline = t3;
        magnetTimeline = t4;
        shieldTimeline = t5;
        destructTimeline = t6;
        omtimeline = t7;
    }

    /**
     * Setter method to set main variable of class.
     * @param m Reference of main object.
     */
    public void setMain(Main m){
        main = m;
    }

    /**
     * Getter method for snake variable.
     * @return Returns reference to the snake which is a part of this grid.
     */
    public Snake getSnake() {
        return snake;
    }

    /**
     * Used to implement resume game functionality by restoring non-serialisable objects to their old state.
     * @param root Reference to root pane.
     */
    public void restore(Pane root){
        this.root = root;
        root.setStyle("-fx-background-color: black"); //Setting colour of Pane to Black.

        img = new Image("file:src/Burst.gif",50,50,true,false);
        icon = new ImageView(img);

        for (Coin coin: coins) if(coin != null) coin.restore();
        for (Block block: theblocks1) if(block != null) {
            block.restore();
            root.getChildren().add(block.realg);
        }
        for (Block block: theblocks2) if(block != null) {
            block.restore();
            root.getChildren().add(block.realg);
        }
        for (Block block: theblocks3) if(block != null) {
            block.restore();
            root.getChildren().add(block.realg);
        }
        for (Wall wall: thewalls) if(wall != null) {
            wall.restore();
            root.getChildren().add(wall.realg);
        }
        for (Coin coin: coins ) if(coin != null) {
            coin.restore();
            root.getChildren().add(coin.realg);
        }

        if(magnet != null) {
            magnet.restore();
            root.getChildren().add(magnet.realg);

        }
        if(shield != null) {
            shield.restore();
            root.getChildren().add(shield.realg);

        }
        if(destruction != null) {
            destruction.restore();
            root.getChildren().add(destruction.realg);

        }
        if(snake != null) snake.restore(root);
        root.getChildren().add(snake.sldisp);


        setupChoiceBox();
        restoreScoreDisplay();

        cb.getSelectionModel()
                .selectedItemProperty()
                .addListener( (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {

                    if(newValue.equals("Restart Game")) {
                        //insert code here
                        resetGame();
                        cb.setValue("");

                    }
                    else {
                        cb.setValue("");
                        try{
                            main.start(main.PS);
                            main.r = true;
                        }catch (Exception e){
                            System.out.println("grid 99");
                        }
                    }
                } );

    }

    /**
     * Initialises Arrays and Arraylists of objects that live in the grid.
     */
    private void setupObjectArrays(){
        theblocks1 = new Block[7];
        theblocks2 = new Block[7];
        theblocks3 = new Block[7];
        thewalls = new ArrayList<>();

        coins = new Coin[5];
        TokensOnScreen = new ArrayList<>();
    }

    /**
     * Initialises boolean class variables.
     */
    private void InitialiseBooleans(){
        BlockR1IsPresent = false;
        BlockR2IsPresent = false;
        BlockR3IsPresent = false;
        WallIsPresent = false;
        MagnetIsPresent = false;
        ShieldIsPresent = false;
        DestructionIsPresent = false;
        isAlive = true;
    }

    /**
     * Initialises text box for displaying score.
     */
    private void setupScoreDisplay(){
        score_text = new Text(475,13,"0");
        score_text.setFill(Color.WHITE);
        score_text.setFont(new Font(15));
        root.getChildren().add(score_text);
    }

    /**
     * Restores score text box on resume game.
     */
    private void restoreScoreDisplay(){
        String scorestring = Integer.toString(score);
        score_text = new Text(475,13,scorestring);
        score_text.setFill(Color.WHITE);
        score_text.setFont(new Font(15));
        root.getChildren().add(score_text);
    }

    /**
     * Sets up the the snake variable of the grid.
     */
    private void setupSnake(){
        snake = new Snake(WIDTH,HEIGHT,diameter,root);
        snake.xvel = 0;
        snake.yvel = 0;
        root.getChildren().add(snake.head);
        root.getChildren().add(snake.sldisp);
    }

    /**
     * Initialises the choice box for drop-down functionality.
     */
    private void setupChoiceBox(){
        cb = new ChoiceBox<>();
        cb.getItems().addAll("","Restart Game", "Quit Game");
        cb.setLayoutX(0);
        cb.setLayoutY(0);
        cb.setValue("");
        cb.setBackground(Background.EMPTY);
        String style = "-fx-background-color: rgba(255,255,255);";
        cb.setStyle(style);
        root.getChildren().add(cb);
    }

    /**
     * Spawns Walls on screen when called.
     */
    public void SpawnWalls(){
        double length = 310 - WIDTH/7;
        ArrayList<Integer> OnScreenInd1 = new ArrayList<>();
        ArrayList<Integer> OnScreenInd2 = new ArrayList<>();
        Block nnb1 = null;
        Block nnb2 = null;
        for(int i = 0;i<theblocks1.length;i++){
            if(theblocks1[i] != null){
                nnb1 = theblocks1[i];
                OnScreenInd1.add(i);

            }
            if(theblocks2[i] != null){
                nnb2 = theblocks2[i];
                OnScreenInd2.add(i);
            }
        }
        if(nnb1 != null && nnb1.location.getY() == 0){
            Random rand = new Random();
            int randint = rand.nextInt(OnScreenInd1.size());
            Block req = theblocks1[OnScreenInd1.get(randint)];
            Wall toadd = new Wall(WIDTH);
            toadd.location = new Point(req.location.getX(),-1*length);
            toadd.setPosition(toadd.location);
            root.getChildren().add(toadd.realg);
            thewalls.add(toadd);
        }
        if(nnb2 != null && nnb2.location.getY() == 0){
            Random rand = new Random();
            int randint = rand.nextInt(OnScreenInd2.size());
            Block req = theblocks2[OnScreenInd2.get(randint)];
            Wall toadd = new Wall(WIDTH);
            toadd.location = new Point(req.location.getX(),-1*length);
            toadd.setPosition(toadd.location);
            root.getChildren().add(toadd.realg);
            thewalls.add(toadd);
        }

    }

    /**
     * Spawns Coins on screen when called.
     */
    public void SpawnCoins(){
        // Spawns new Coins
        if(Coin_count < 5){
            for(int i = 0;i<5;i++){
                if(coins[i] == null){
                    int rx = assignRandomX();
                    Coin newcoin = new Coin(diameter /2,rx);
                    root.getChildren().add(newcoin.realg);
                    Coin_count++;
                    coins[i] = newcoin;
                    TokensOnScreen.add(newcoin);
                }
            }
        }
    }

    /**
     * Spawns Blocks on screen when called.
     */
    public void SpawnBlocks(){
        // Spawns blocks
        boolean to_proceed = true;
        for(int j = 0;j<TokensOnScreen.size();j++){
            if(TokensOnScreen.get(j).location.getY() <= WIDTH/7){
                to_proceed = false;
                break;
            }
        }
        if(to_proceed){
            if(!BlockR1IsPresent){
                Block b2 = null;
                Block b3 = null;
                for(int i = 0;i<theblocks2.length;i++){
                    if(theblocks2[i] != null) b2 = theblocks2[i];
                    if(theblocks3[i] != null) b3 = theblocks3[i];
                }

                double b2pos = -1;
                double b3pos = -1;
                if(b2 != null) b2pos = b2.location.getY();
                if(b3 != null) b3pos = b3.location.getY();
                if( b2pos >= HEIGHT/3 || b2pos == -1 ){
                    if(b3pos == -1 || b3pos >= HEIGHT/3){
                        int count = 0;
                        int min = 1000;
                        for(int i = 0;i<theblocks1.length;i++){
                            Random rand = new Random();
                            int decide = rand.nextInt(2);
                            if(decide == 1){
                                count++;
                                int val;
                                boolean sure = true;
                                if(snake.length < 10){
                                    val = rand.nextInt(10) + 1;
                                }
                                else{
                                    val = rand.nextInt(snake.length) + 5;
                                }
                                if(val < min) min = val;
                                if(i == theblocks1.length-1){
                                   if(count == 6){
                                       if(snake.length < min){
                                           if(snake.length == 0){
                                               sure = false;
                                           }
                                           else{
                                               val = snake.length;
                                           }
                                       }
                                   }
                                }
                                if(sure){
                                    Point bloc = new Point(i*(WIDTH/7),0);
                                    Block curr = new Block(WIDTH,bloc);
                                    root.getChildren().add(curr.realg);
                                    theblocks1[i] = curr;
                                    BlockR1IsPresent = true;
                                    curr.setValue(val);
                                    curr.valOfBlock = val;
                                }
                            }
                        }
                    }
                }
            }

            Block r1rep = null;

            for(int i = 0;i<theblocks1.length;i++){
                if(theblocks1[i] != null){
                    r1rep = theblocks1[i];
                }
            }
            // Remove the if condition.
            if(r1rep != null){
                if(!BlockR2IsPresent){
                    if(r1rep.location.getY() >= HEIGHT/3){
                        int count = 0;
                        int min = 1000;
                        for(int j = 0;j<theblocks2.length;j++){
                            //50% chance of block spawning.
                            Random rand = new Random();
                            int decide = rand.nextInt(2);
                            if(decide == 1){
                                count ++;
                                int val;
                                boolean sure = true;
                                if(snake.length < 10){
                                    val = rand.nextInt(10) + 1;
                                }
                                else{
                                    val = rand.nextInt(snake.length) + 5;
                                }
                                if(val < min) min = val;
                                if(j == theblocks2.length-1){
                                    if(count == 6){
                                        if(snake.length < min){
                                            if(snake.length == 0){
                                                sure = false;
                                            }
                                            else{
                                                val = snake.length;
                                            }
                                        }
                                    }
                                }
                                if(sure){
                                    Point bloc = new Point(j*(WIDTH/7),0);
                                    Block curr = new Block(WIDTH,bloc);
                                    root.getChildren().add(curr.realg);
                                    theblocks2[j] = curr;
                                    BlockR2IsPresent = true;
                                    curr.setValue(val);
                                    curr.valOfBlock = val;
                                }
                            }
                        }
                    }
                }
            }

            Block r2rep = null;

            for(int i = 0;i<theblocks2.length;i++){
                if(theblocks2[i] != null){
                    r2rep = theblocks2[i];
                }
            }

            if(r2rep != null && r1rep != null){
                if(!BlockR3IsPresent){
                    if(r2rep.location.getY() >= HEIGHT/3){
                        int count = 0;
                        int min = 1000;
                        for(int j = 0;j<theblocks3.length;j++){
                            //50% chance of block spawning.
                            Random rand = new Random();
                            int decide = rand.nextInt(2);
                            if(decide == 1){
                                count++;

                                int val;
                                boolean sure = true;
                                if(snake.length < 10){
                                    val = rand.nextInt(10) + 1;
                                }
                                else{
                                    val = rand.nextInt(snake.length) + 5;
                                }
                                if(val < min) min = val;
                                if(j == theblocks2.length-1){
                                    if(count == 6){
                                        if(snake.length < min){
                                            if(snake.length == 0){
                                                sure = false;
                                            }
                                            else{
                                                val = snake.length;
                                            }
                                        }
                                    }
                                }
                                if(sure){
                                    Point bloc = new Point(j*(WIDTH/7),0);
                                    Block curr = new Block(WIDTH,bloc);
                                    root.getChildren().add(curr.realg);
                                    theblocks3[j] = curr;
                                    BlockR3IsPresent = true;
                                    curr.setValue(val);
                                    curr.valOfBlock = val;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Spawns Shield token on screen when called.
     */
    public void SpawnShield(){
        if(!ShieldIsPresent){
            int rx = assignRandomX();
            Shield theshield = new Shield(rx);
            root.getChildren().add(theshield.realg);
            ShieldIsPresent = true;
            shield = theshield;
            TokensOnScreen.add(theshield);
        }
    }

    /**
     * Spawns Magnet token on screen when called.
     */
    public void SpawnMagnet(){
        if(!MagnetIsPresent){
            int rx = assignRandomX();
            Magnet themagnet = new Magnet(rx);
            root.getChildren().add(themagnet.realg);
            MagnetIsPresent = true;
            magnet = themagnet;
            TokensOnScreen.add(themagnet);
        }
    }

    /**
     * Spawns destroy blocks token when called.
      */
    public void SpawnDestructionToken(){
        if(!DestructionIsPresent){
            int rx = assignRandomX();
            Destruction chaos = new Destruction(rx);
            root.getChildren().add(chaos.realg);
            DestructionIsPresent = true;
            destruction = chaos;
            TokensOnScreen.add(chaos);
        }

    }

    /**
     * Translates blocks currently on screen downwards.
     */
    public void MoveBlocks(){
        //Moves the blocks downwards.
        if(BlockR1IsPresent){
            BlockR1IsPresent = moveBlockRow(theblocks1);
        }
        if(BlockR2IsPresent){
            BlockR2IsPresent = moveBlockRow(theblocks2);
        }
        if(BlockR3IsPresent){
            BlockR3IsPresent = moveBlockRow(theblocks3);
        }
    }

    /**
     * Translates coins currently on screen downwards.
     */
    public void MoveCoins(){
        //Moves the coins downwards
        if(Coin_count != 0){
            for(int i = 0;i<5;i++){
                Coin currc = coins[i];
                if(currc != null){
                    currc.location.translate(0,1,difficulty);
                    currc.setPosition(currc.location);
                    if(currc.location.getY() >= 790){
                        root.getChildren().remove(currc.realg);
                        TokensOnScreen.remove(coins[i]);
                        coins[i] = null;
                        Coin_count--;
                    }
                }
            }
        }
    }

    /**
     * Translates magnet token downwards if on screen.
      */
    public void MoveMagnet(){
        if(MagnetIsPresent){
            magnet.location.translate(0,1,difficulty);
            magnet.setPosition(magnet.location);
            if(magnet.location.getY() >= 790){
                root.getChildren().remove(magnet.realg);
                TokensOnScreen.remove(magnet);
                magnet = null;
                MagnetIsPresent = false;
            }
        }
    }

    /**
     * Translates shield token downwards if on screen.
      */
    public void MoveShield(){
        if(ShieldIsPresent){
            shield.location.translate(0,1,difficulty);
            shield.setPosition(shield.location);
            if(shield.location.getY() >= 790){
                root.getChildren().remove(shield.realg);
                TokensOnScreen.remove(shield);
                shield = null;
                ShieldIsPresent = false;
            }
        }
    }

    /**
     * Translates destroy all token downwards if on screen.
     */
    public void MoveDestructionToken(){
        if(DestructionIsPresent){
            destruction.location.translate(0,1,difficulty);
            destruction.setPosition(destruction.location);
            if(destruction.location.getY() >= 790){
                root.getChildren().remove(destruction.realg);
                TokensOnScreen.remove(destruction);
                destruction = null;
                DestructionIsPresent = false;
            }
        }
    }

    /**
     * Translates walls on screen downwards.
     */
    public void MoveWalls(){
        for(int i = 0;i<thewalls.size();i++){
            Wall currw = thewalls.get(i);
            currw.location.translate(0,1,difficulty);
            currw.setPosition(currw.location);
            if(currw.location.getY() >= 790){
                root.getChildren().remove(currw.realg);
                thewalls.remove(currw);
            }
        }
    }

    /**
     * Translates the snake left or right according to it's velocity.
     */
    public void MoveSnake(){
        //Shifts all parts of the snakes body left or right.
        ArrayList<Point> spoints = snake.points;
        ArrayList<Circle> scircles = snake.circles;
        for(int i = 0;i<spoints.size();i++){
            Point currp = spoints.get(i);
            currp.translate(snake.xvel,snake.yvel,difficulty);
            Circle currc = scircles.get(i);
            currc.setLayoutX(currp.getX());
            currc.setLayoutY(currp.getY());
        }
        snake.sldisp.setX(snake.hlocation.getX()-6);
    }

    /**
     * Helper method to implement MoveBlocks.
     * @param theblocks Reference to block row to be moved.
     * @return True if given row is still on screen, false otherwise.
     */
    private boolean moveBlockRow(Block theblocks[]){
        boolean flag = true;
        for(int i = 0;i<theblocks.length;i++){
            if(theblocks[i] != null){
                Block currb = theblocks[i];
                if(i == 1){
//                    System.out.println(currb.location.getY() + "loc");
                }
                currb.location.translate(0,1,difficulty);
                currb.setPosition(currb.location);
                if(currb.location.getY() >= 980){
                    root.getChildren().remove(currb.realg);
                    theblocks[i] = null;
                    flag = false;
                }
            }
        }
        return flag;
    }

    /**
     * Calls helper methods to check for collisions between objects on screen and snake.
     */
    public void CollisionCheck(){
        if(snake.hasMagnet){
            AttractCoins();
        }
        //Checks for Collisions between Token and Snake.
        CheckTokenCollision();

        // Code for collision b/w Block and Snake.
        CheckBlockCollision();

        // Code fo collision b/w Snake and Wall.
        CheckWallCollision();
    }

    /**
     * Helper method to check for collision between Walls and snake.
     */
    private void CheckWallCollision(){
        for(int i = 0;i<thewalls.size();i++){
            Wall currw = thewalls.get(i);
            Point wloc = currw.location;
            Point hloc = snake.hlocation;
            if(hloc.getY() > wloc.getY() && hloc.getY() - wloc.getY() < currw.length){
                double xdiff = hloc.getX() - wloc.getX();
                if(xdiff < 0){
                    if(xdiff > -5){
                        snake.reverseSnake();
                    }
                }
                else{
                    if(xdiff < 5){
                        snake.reverseSnake();
                    }
                }

            }

        }
    }

    /**
     * Helper method to check for collision between Blocks and snake.
     */
    private void CheckBlockCollision(){
        blockRowCollision(theblocks1);
        blockRowCollision(theblocks2);
        blockRowCollision(theblocks3);
        snake.updateSnakeLengthDisp();
    }

    /**
     * Helper method for taking action if collision between Blocks and snake.
     * @param theblocks Reference to block row with which the snake may collide.
     */
    private void blockRowCollision(Block theblocks[]){
        for(int i = 0;i<theblocks.length;i++){
            if(theblocks[i] != null){
                Block currb = theblocks[i];
                double lbx = currb.location.getX();
                double ubx = lbx + (WIDTH/7);
                double yb = currb.location.getY() + (WIDTH/7);
                int ryb = (int) yb;
                if(lbx<=snake.hlocation.getX() && ubx>=snake.hlocation.getX()){
                    if(320 < currb.location.getY() && currb.location.getY() < 335){
                        int val = currb.valOfBlock;
                        if(snake.hasShield){
                            score += val;
                            root.getChildren().remove(theblocks[i].realg);
                            theblocks[i] = null;
                        }
                        else if(val <= snake.length){
                            if(val > 5){
                                try{
                                    pauseTimelines();
                                    snake.stopSnake();
                                    isPaused = true;

                                    deccounter = val;
                                    beingPounded = currb;

                                    KeyFrame kf = new KeyFrame(Duration.millis(250),new SlowHandler());
                                    Timeline slowTimeline = new Timeline(kf);
                                    slowTimeline.setCycleCount(val);
                                    slowTimeline.play();


                                }catch (Exception e){
                                    System.out.println("null");
                                }
                            }
                            score += val;
                            bx = ((lbx+ubx)/2) - 25;
                            by = currb.location.getY();

                            if(val <= 5){
                                root.getChildren().remove(theblocks[i].realg);
                                snake.decrLength(val);
                                playBurst(bx,by);
                            }

                            theblocks[i] = null;

                        }
                        else isAlive = false;
                        adjustDifficulty();
//                        player.stop();
                    }
                }
            }
        }
    }

    /**
     * Checks for collision between snake and tokens.
     */
    private void CheckTokenCollision(){
        for(int i = 0;i<TokensOnScreen.size();i++){
            Token currt = TokensOnScreen.get(i);
            Point tloc = currt.location;
            Point hloc = snake.hlocation;
            boolean docollide = false;
            if((hloc.getY() - tloc.getY() <= 15 && hloc.getY() - tloc.getY() >= 0) || (tloc.getY() - hloc.getY() <= 15 && tloc.getY() - hloc.getY() >= 0)){
                double xdiff = hloc.getX() - tloc.getX();
                if(xdiff >=0 && xdiff <= 20){
                    docollide = true;
                }
                else if(xdiff < 0 && xdiff >= -20){
                    docollide = true;
                }
            }
            if(docollide){
                if(currt instanceof Coin){
                    ConsumeCoin(currt);
                }
                else if(currt instanceof Magnet){
                    ConsumeMagnet(currt);
                }
                else if(currt instanceof Shield){
                    ConsumeShield(currt);
                }
                else if(currt instanceof Destruction){
                    ConsumeDestruction(currt);
                }
                playBurst(tloc.getX() - 15,tloc.getY() - 50);
            }
        }
    }

    /**
     * Takes appropriate action if snake consumes a coin token.
     * @param currt Coin with which snake collides.
     */
    private void ConsumeCoin(Token currt){
        Coin currc = (Coin) currt;
        for(int j = 0;j<currc.valOfCoin;j++) {
            snake.incLength(diameter /2);
        }
        root.getChildren().remove(currc.realg);
        TokensOnScreen.remove(currc);
        for(int i = 0;i<coins.length;i++){
            if(coins[i] == currc){
                coins[i] = null;
                break;
            }
        }
        Coin_count--;
        adjustDifficulty();
    }

    /**
     * Takes appropriate action if snake consumes a magnet token.
     * @param currt Magnet with which snake collides.
     */
    private void ConsumeMagnet(Token currt){
        Magnet currm = (Magnet) currt;
        Activate("Magnet");
        TokensOnScreen.remove(currm);
        root.getChildren().remove(currm.realg);
        MagnetIsPresent = false;
        snake.hasMagnet = true;
        magnet = null;
    }

    /**
     * Takes appropriate action if snake consumes a shield token.
     * @param currt Shield with which snake collides.
     */
    private void ConsumeShield(Token currt){
        Shield currs = (Shield) currt;
        Activate("Shield");
        TokensOnScreen.remove(currs);
        root.getChildren().remove(currs.realg);
        ShieldIsPresent = false;
        snake.hasShield = true;
        shield = null;
    }

    /**
     * Takes appropriate action if snake consumes a destroy all token.
     * @param currt Destruction token with which snake collides.
     */
    private void ConsumeDestruction(Token currt){
        Destruction currd = (Destruction) currt;
        for(int i = 0;i<theblocks1.length;i++){
            if(theblocks1[i] != null){
                int val = theblocks1[i].valOfBlock;
                score += val;
                root.getChildren().remove(theblocks1[i].realg);
                theblocks1[i] = null;
                BlockR1IsPresent = false;
            }

            if(theblocks2[i] != null){
                int val = theblocks2[i].valOfBlock;
                score += val;
                root.getChildren().remove(theblocks2[i].realg);
                theblocks2[i] = null;
                BlockR2IsPresent = false;
            }
            if(theblocks3[i] != null){
                int val = theblocks3[i].valOfBlock;
                score += val;
                root.getChildren().remove(theblocks3[i].realg);
                theblocks3[i] = null;
                BlockR3IsPresent = false;
            }
        }
        TokensOnScreen.remove(currd);
        root.getChildren().remove(currd.realg);
        DestructionIsPresent = false;
        destruction = null;
    }

    /**
     * Updates the score in text box that displays the score.
     */
    public void UpdateScore(){
        String scorestring = Integer.toString(score);
        score_text.setText(scorestring);
    }

    /**
     * Generates an x value for token to be spawned which is not occupied by any other token/block.
     * @return Returns x value where spawning a token would lead to no overlap.
     */
    private int assignRandomX(){
        Random rand = new Random();
        int rx = 0;

        boolean flag = false;
        while(!flag){
            flag = true;
            rx = rand.nextInt(480);
            for(int i = 0;i<TokensOnScreen.size();i++){
                Token curr = TokensOnScreen.get(i);
                Point loc = curr.location;
                if(loc.getY() <= 40){
                    if((rx > loc.getX() && rx - loc.getX() <= 40) || (rx < loc.getX() && loc.getX() - rx <= 40)){
                        //Change if changing grid size
                        flag = false;
                    }
                }
            }

            if(BlockR1IsPresent) {
                for(int i = 0;i<theblocks1.length;i++){
                    if(theblocks1[i] != null){
                        Block currb = theblocks1[i];
                        Point loc = currb.location;
                        if(loc.getY() <= 40){
                            if((rx > loc.getX() && rx - loc.getX() <= 50) || (rx < loc.getX() && loc.getX() - rx <= 30)) {
                                // Change if changing grid size
                                flag = false;
                            }
                        }
                    }
                }
            }
            if(BlockR2IsPresent){
                for(int i = 0;i<theblocks2.length;i++){
                    if(theblocks2[i] != null){
                        Block currb = theblocks2[i];
                        Point loc = currb.location;
                        if(loc.getY() <= 40){
                            if((rx > loc.getX() && rx - loc.getX() <= 50) || (rx < loc.getX() && loc.getX() - rx <= 30)) {
                                // Change if changing grid size
                                flag = false;
                            }
                        }
                    }
                }
            }
            if(BlockR3IsPresent){
                for(int i = 0;i<theblocks3.length;i++){
                    if(theblocks3[i] != null){
                        Block currb = theblocks3[i];
                        Point loc = currb.location;
                        if(loc.getY() <= 40){
                            if((rx > loc.getX() && rx - loc.getX() <= 50) || (rx < loc.getX() && loc.getX() - rx <= 30)) {
                                // Change if changing grid size
                                flag = false;
                            }
                        }
                    }
                }
            }
            for(int i = 0;i<thewalls.size();i++){
                Wall currw = thewalls.get(i);
                Point loc = currw.location;
                if(loc.getY() <= 40){
                    if((rx > loc.getX() && rx - loc.getX() <= 30) || (rx < loc.getX() && loc.getX() - rx <= 30)){
                        flag = false;
                    }
                }
            }
        }
        return rx;
    }

    /**
     * Assigns time to corresponding variable at which ability was activated.
     * @param name Name of ability which is activated.
     */
    private void Activate(String name){
        if(name.equals("Magnet")){
            MagnetActivatedAt = System.currentTimeMillis();
            return;
        }
        if(name.equals("Shield")) ShieldActivatedAt = System.currentTimeMillis();
    }

    /**
     * Checks if an ability is active.
     * @param name Name of ability for which status is to be checked.
     * @return True if ability is active, else false.
     */
    private boolean isActive(String name){
        if(name.equals("Magnet")){
            long activeFor = System.currentTimeMillis() - MagnetActivatedAt;
            return activeFor >= 0 && activeFor <= MagnetDuration;
        }
        if(name.equals("Shield")){
            long activeFor = System.currentTimeMillis() - ShieldActivatedAt;
            return activeFor >= 0 && activeFor <= ShieldDuration;
        }
        return false;
    }

    /**
     * Updates token validity on basis of time elapsed.
     */
    public void UpdateTokenValidity(){
        if(!isActive("Magnet")){
            snake.hasMagnet = false;
        }
        if(!isActive("Shield")){
            snake.hasShield = false;
        }
    }

    /**
     * Coins in vicinity of snake are attracted if snake has magnet.
     */
    private void AttractCoins(){
        for(int i = 0;i<coins.length;i++){
            if(coins[i] != null){
                Coin currc = coins[i];
                Point cloc = currc.location;
                Point sloc = snake.hlocation;
                if(cloc.getY() - sloc.getY() <= 5 && cloc.getY() - sloc.getY() >=0 ){
                    if((sloc.getX() > cloc.getX() && sloc.getX() - cloc.getX() <= 175) || (cloc.getX() > sloc.getX() && cloc.getX() - sloc.getX() <= 175)){
                        for(int j = 0;j<currc.valOfCoin;j++) snake.incLength(diameter /2);
                        root.getChildren().remove(currc.realg);
                        TokensOnScreen.remove(currc);
                        coins[i] = null;
                        Coin_count--;
                    }
                }
            }
        }
    }

    /**
     * Pauses the timelines when required.
     */
    private void pauseTimelines(){
        snakeTimeline.pause();
        coinTimeline.pause();
        blockTimeline.pause();
        magnetTimeline.pause();
        shieldTimeline.pause();
        destructTimeline.pause();
        omtimeline.pause();
    }

    /**
     * Plays the timelines when required.
     */
    private void playTimelines(){
        snakeTimeline.play();
        coinTimeline.play();
        blockTimeline.play();
        magnetTimeline.play();
        shieldTimeline.play();
        destructTimeline.play();
        omtimeline.play();
    }

    /**
     * Event handler for decreasing length of snake one by one.
     */
    private class SlowHandler implements EventHandler<ActionEvent>{
        public void handle(ActionEvent event){
            beingPounded.valOfBlock--;
            snake.decrLength(1);
            deccounter--;
        }
    }

    /**
     * Acts as an observer and updates variables it observes.
     */
    public void CheckForChange(){
        if(isPaused){
            beingPounded.setValue(beingPounded.valOfBlock);
        }
        if(deccounter <=  0 && isPaused){
            playTimelines();
            isPaused = false;
            root.getChildren().remove(beingPounded.realg);
            playBurst(bx,by);
        }
        if(gifonscreen){
            long currtime = System.currentTimeMillis();
            if(currtime - gifstime > 150){
                root.getChildren().remove(icon);
                gifonscreen = false;
            }
        }
    }

    /**
     * Getter for parameter isAlive.
     * @return Returns true if snake is alive, false otherwise.
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Adjusts speed of game according to length of snake.
     */
    private void adjustDifficulty(){
        difficulty = snake.length/10;
    }

    /**
     * Checks if snake is alive. Stops game and displays score if snake dies.
     */
    public void CheckIfAlive(){
        Text text1 = new Text(150,450,"YOU DIED");
        text1.setFill(Color.WHITE);
        text1.setFont(new Font(50));
        Text text2 = new Text(175,480,"Your Score: " + Integer.toString(score));
        text2.setFill(Color.WHITE);
        text2.setFont(new Font(30));
        Button back2menu = new Button("MainMenu");
        back2menu.setLayoutX(220);
        back2menu.setLayoutY(500);
        if(!isAlive){
            String name = main.getDatabase().getCurrentUser().getName();
            String newScore = Integer.toString(this.score);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.now();
            String date = dateTimeFormatter.format(localDateTime);
            main.getDatabase().updateTopTenScores(name,newScore,date);
            main.getDatabase().getCurrentUser().updateTopTenScores(name,newScore,date);

            pauseTimelines();
            root.getChildren().add(text1);


            root.getChildren().add(text2);


            root.getChildren().add(back2menu);

            back2menu.setOnAction(e -> {
                try{
                    main.start(main.PS);
                }catch (Exception e1){
                    System.out.println("error in 1340 grid.java");
                }
            });

            main.r = true;
            main.database.setShowResumeButton(false);
        }
        else{
            if(root.getChildren().contains(text1)) root.getChildren().remove(text1);
            if(root.getChildren().contains(text2)) root.getChildren().remove(text2);
            if(root.getChildren().contains(back2menu)) root.getChildren().remove(back2menu);

        }
    }

    /**
     * Plays a burst animation if snake collides with block/token.
     * @param x x co-ordinate of GIF.
     * @param y y co-ordinate of GIF.
     */
    private void playBurst(double x,double y){
        icon.setX(x);
        icon.setY(y);
        if(!gifonscreen) root.getChildren().add(icon);
        gifonscreen = true;
        gifstime = System.currentTimeMillis();
    }
}
