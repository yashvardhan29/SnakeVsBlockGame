package sample;

import javafx.animation.KeyFrame;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import javafx.animation.Timeline;
import javafx.util.Duration;


public class Grid implements Serializable {
    transient Pane root; // The Parent container that contains everything else. This is directly added to the scene.
    int diameter = 20; // Diameter of snake
    int WIDTH = 500; // Width of grid in pixels.
    int HEIGHT = 800; // Height of grid in pixels.

    private boolean isPaused = false;

    transient Main main;
    int Coin_count; //Number of coins currently on screen.

    Coin coins[]; // Array that stores references to the coins on screen.
    Block theblocks1[]; // Array that stores references to the blocks on screen.
    Block theblocks2[];
    Block theblocks3[];
    ArrayList<Wall> thewalls;

    Magnet magnet; // Reference to the magnet on screen.
    Shield shield; // Reference to the shield on screen.
    Destruction destruction; // Reference to the destruction token on screen.

    Snake snake; //Reference to the snake.

    boolean WallIsPresent;  // True if wall is present on screen.
    boolean BlockR1IsPresent; // True if block row1 is present on screen.
    boolean BlockR2IsPresent; // True if block row2 is present on screen.
    boolean BlockR3IsPresent; // True if block row3 is present on screen.
    boolean MagnetIsPresent; // True if Magnet is present on screen.
    boolean ShieldIsPresent; // True if Shield is present on screen.
    boolean DestructionIsPresent; // True if Destruction Token is present on screen.

    boolean isAlive; // True while snake is alive.

    private long MagnetActivatedAt = Long.MAX_VALUE;
    private final int MagnetDuration = 7000;

    private long ShieldActivatedAt = Long.MAX_VALUE;
    private  final int ShieldDuration = 10000;

    int deccounter = 0;

    ArrayList<Token> TokensOnScreen;

    int score;
    transient Text score_text;

    transient ChoiceBox<String> cb;

    transient Timeline snakeTimeline, coinTimeline, blockTimeline, magnetTimeline, shieldTimeline, destructTimeline, omtimeline;

    Block beingPounded;

    int difficulty = 0;


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
                        }catch (Exception e){
                            System.out.println("grid 99");
                        }
                    }
                } );
    }

    public void resetGame(){
        score = 0;
        Coin_count = 0;

        root.getChildren().clear();

        setupChoiceBox();
        setupSnake();
        setupScoreDisplay();
        InitialiseBooleans();
        setupObjectArrays();
    }

    public void setTimelines(Timeline t1, Timeline t2, Timeline t3, Timeline t4, Timeline t5, Timeline t6, Timeline t7) {
        snakeTimeline = t1;
        coinTimeline = t2;
        blockTimeline = t3;
        magnetTimeline = t4;
        shieldTimeline = t5;
        destructTimeline = t6;
        omtimeline = t7;
    }

    public void setMain(Main m){
        main = m;
    }

    public Snake getSnake() {
        return snake;
    }

    public void restore(Pane root){
        this.root = root;
        root.setStyle("-fx-background-color: black"); //Setting colour of Pane to Black.


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


        setupChoiceBox();
        restoreScoreDisplay();
    }

    private void setupObjectArrays(){
        theblocks1 = new Block[7];
        theblocks2 = new Block[7];
        theblocks3 = new Block[7];
        thewalls = new ArrayList<>();

        coins = new Coin[5];
        TokensOnScreen = new ArrayList<>();
    }

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

    private void setupScoreDisplay(){
        score_text = new Text(475,13,"0");
        score_text.setFill(Color.WHITE);
        score_text.setFont(new Font(15));
        root.getChildren().add(score_text);
    }

    private void restoreScoreDisplay(){
        String scorestring = Integer.toString(score);
        score_text = new Text(475,13,scorestring);
        score_text.setFill(Color.WHITE);
        score_text.setFont(new Font(15));
        root.getChildren().add(score_text);
    }

    private void setupSnake(){
        snake = new Snake(WIDTH,HEIGHT,diameter,root);
        snake.xvel = 0;
        snake.yvel = 0;
        root.getChildren().add(snake.head);
        root.getChildren().add(snake.sldisp);
    }

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
                        for(int i = 0;i<theblocks1.length;i++){
                            //50% chance of block spawning.
                            Random rand = new Random();
                            int decide = rand.nextInt(2);
                            if(decide == 1){
                                Point bloc = new Point(i*(WIDTH/7),0);
                                Block curr = new Block(WIDTH,bloc);
                                root.getChildren().add(curr.realg);
                                theblocks1[i] = curr;
                                BlockR1IsPresent = true;
                                int val = rand.nextInt(10) + 1;
                                curr.setValue(val);
                                curr.valOfBlock = val;
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
                        for(int j = 0;j<theblocks2.length;j++){
                            //50% chance of block spawning.
                            Random rand = new Random();
                            int decide = rand.nextInt(2);
                            if(decide == 1){
                                Point bloc = new Point(j*(WIDTH/7),0);
                                Block curr = new Block(WIDTH,bloc);
                                root.getChildren().add(curr.realg);
                                theblocks2[j] = curr;
                                BlockR2IsPresent = true;
                                int val = rand.nextInt(10) + 1;
                                curr.setValue(val);
                                curr.valOfBlock = val;
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
                        for(int j = 0;j<theblocks3.length;j++){
                            //50% chance of block spawning.
                            Random rand = new Random();
                            int decide = rand.nextInt(2);
                            if(decide == 1){
                                Point bloc = new Point(j*(WIDTH/7),0);
                                Block curr = new Block(WIDTH,bloc);
                                root.getChildren().add(curr.realg);
                                theblocks3[j] = curr;
                                BlockR3IsPresent = true;
                                int val = rand.nextInt(10) + 1;
                                curr.setValue(val);
                                curr.valOfBlock = val;
                            }
                        }
                    }
                }
            }
        }
    }

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

    private void CheckBlockCollision(){
        blockRowCollision(theblocks1);
        blockRowCollision(theblocks2);
        blockRowCollision(theblocks3);
        snake.updateSnakeLengthDisp();
    }

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
                            if(val <= 5){
                                root.getChildren().remove(theblocks[i].realg);
                                snake.decrLength(val);
                            }
                            theblocks[i] = null;
                        }
                        else isAlive = false;
                        adjustDifficulty();
                    }
                }
            }
        }
    }

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
            }
        }
    }

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

    private void ConsumeMagnet(Token currt){
        Magnet currm = (Magnet) currt;
        Activate("Magnet");
        TokensOnScreen.remove(currm);
        root.getChildren().remove(currm.realg);
        MagnetIsPresent = false;
        snake.hasMagnet = true;
        magnet = null;
    }

    private void ConsumeShield(Token currt){
        Shield currs = (Shield) currt;
        Activate("Shield");
        TokensOnScreen.remove(currs);
        root.getChildren().remove(currs.realg);
        ShieldIsPresent = false;
        snake.hasShield = true;
        shield = null;
    }

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

    public void UpdateScore(){
        String scorestring = Integer.toString(score);
        score_text.setText(scorestring);
    }

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

    private void Activate(String name){
        if(name.equals("Magnet")){
            MagnetActivatedAt = System.currentTimeMillis();
            return;
        }
        if(name.equals("Shield")) ShieldActivatedAt = System.currentTimeMillis();
    }

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

    public void UpdateTokenValidity(){
        if(!isActive("Magnet")){
            snake.hasMagnet = false;
        }
        if(!isActive("Shield")){
            snake.hasShield = false;
        }
    }

    private void AttractCoins(){
        for(int i = 0;i<coins.length;i++){
            if(coins[i] != null){
                Coin currc = coins[i];
                Point cloc = currc.location;
                Point sloc = snake.hlocation;
                if(cloc.getY() == sloc.getY()){
                    if((sloc.getX() > cloc.getX() && sloc.getX() - cloc.getX() <= 150) || (cloc.getX() > sloc.getX() && cloc.getX() - sloc.getX() <= 150)){
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

    public int getScore() {
        return score;
    }

    public void pauseTimelines(){
        snakeTimeline.pause();
        coinTimeline.pause();
        blockTimeline.pause();
        magnetTimeline.pause();
        shieldTimeline.pause();
        destructTimeline.pause();
        omtimeline.pause();
    }

    public void playTimelines(){
        snakeTimeline.play();
        coinTimeline.play();
        blockTimeline.play();
        magnetTimeline.play();
        shieldTimeline.play();
        destructTimeline.play();
        omtimeline.play();
    }

    private class SlowHandler implements EventHandler<ActionEvent>{
        public void handle(ActionEvent event){
            beingPounded.valOfBlock--;
            snake.decrLength(1);
            deccounter--;
        }
    }

    public void CheckForChange(){
        if(isPaused){
            beingPounded.setValue(beingPounded.valOfBlock);
        }
        if(deccounter <=  0 && isPaused){
            playTimelines();
            isPaused = false;
            root.getChildren().remove(beingPounded.realg);
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void adjustDifficulty(){
        difficulty = snake.length/10;
    }

    public void CheckIfAlive(){
        if(!isAlive){
//            try{
//                System.out.println("works");
//                root.getChildren().removeAll();
//                root = FXMLLoader.load(getClass().getResource("youded.fxml"));
//                Label scoreLabel = (Label) root.lookup("#scoreLabel");
//                scoreLabel.setText("Your Score: " + Integer.toString(score));
//                Button mm = (Button) root.lookup("#mainMenu");
//                Button exit = (Button) root.lookup("#exitGame");
//                exit.setOnAction(e1 -> {
//                    System.exit(0);
//                });
//                mm.setOnAction(e1 -> {
//                    System.out.println("go to main menu");
//                });
//            }
//            catch (Exception e){
//                System.out.println("lol");
//            }
//            if(snakeTimeline == null) System.out.println(1234);
            String name = main.getDatabase().getCurrentUser().getName();
            String newScore = Integer.toString(this.score);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.now();
            String date = dateTimeFormatter.format(localDateTime);
            main.getDatabase().updateTopTenScores(name,newScore,date);

            pauseTimelines();
            Text text1 = new Text(150,450,"YOU DIED");
            text1.setFill(Color.WHITE);
            text1.setFont(new Font(50));
            root.getChildren().add(text1);

            Text text2 = new Text(175,480,"Your Score: " + Integer.toString(score));
            text2.setFill(Color.WHITE);
            text2.setFont(new Font(30));
            root.getChildren().add(text2);

            Button back2menu = new Button("MainMenu");
            back2menu.setLayoutX(220);
            back2menu.setLayoutY(500);
            root.getChildren().add(back2menu);

            back2menu.setOnAction(e -> {
                try{
                    main.start(main.PS);
                }catch (Exception e1){
                    System.out.println("error in 902 grid.java");
                }
            });

            main.r = true;



//            System.exit(0);
        }
    }
}
