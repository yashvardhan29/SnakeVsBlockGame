package sample;

import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;


public class Grid {
    Pane root; // The Parent container that contains everything else. This is directly added to the scene.
    int diameter = 20; // Diameter of snake
    int WIDTH = 500; // Width of grid in pixels.
    int HEIGHT = 500; // Height of grid in pixels.

    int Coin_count; //Number of coins currently on screen.

    Coin coins[]; // Array that stores references to the coins on screen.
    Block theblocks1[]; // Array that stores references to the blocks on screen.
    Block theblocks2[];

    Magnet magnet; // Reference to the magnet on screen.
    Shield shield; // Reference to the shield on screen.
    Destruction destruction; // Reference to the destruction token on screen.
    Wall wall; // Solitary wall only for purpose of deadline 2.



    Snake snake; //Reference to the snake.

    boolean WallIsPresent;  // True if wall is present on screen.
    boolean BlockR1IsPresent; // True if block row1 is present on screen.
    boolean BlockR2IsPresent; // True if block row2 is present on screen.
    boolean MagnetIsPresent; // True if Magnet is present on screen.
    boolean ShieldIsPresent; // True if Shield is present on screen.
    boolean DestructionIsPresent; // True if Destruction Token is present on screen.

    boolean isAlive; // True while snake is alive.

    ArrayList<Token> TokensOnScreen;

    int score;
    Text score_text;

    ChoiceBox<String> cb;

    Timer mgt;

    Grid(Pane root){
        this.root = root;
        root.setStyle("-fx-background-color: black"); //Setting colour of Pane to Black.

        score = 0;
        Coin_count = 0;

        setupChoiceBox();
        setupSnake();
        setupScoreDisplay();
        InitialiseBooleans();
        setupObjectArrays();

    }

    private void setupObjectArrays(){
        theblocks1 = new Block[10];
        theblocks2 = new Block[WIDTH/5];

        coins = new Coin[5];
        TokensOnScreen = new ArrayList<>();
    }

    private void InitialiseBooleans(){
        BlockR1IsPresent = false;
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
        snake.xvel = 0;
        snake.yvel = 0;
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

    public void SpawnWalls(){
        if(!WallIsPresent){
            Wall thewall = new Wall(theblocks1);
            root.getChildren().add(thewall.realg);
            WallIsPresent = true;
            wall = thewall;
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
            if(TokensOnScreen.get(j).location.getY() <=50){
                to_proceed = false;
                break;
            }
        }
        if(to_proceed){
            if(!BlockR1IsPresent){
                for(int i = 0;i<10;i++){
                    //50% chance of block spawning.
                    Random rand = new Random();
                    int decide = rand.nextInt(2);
                    if(decide == 1){
                        Point bloc = new Point(i*(WIDTH/10),0);
                        Block curr = new Block(WIDTH,bloc);
                        root.getChildren().add(curr.realg);
                        theblocks1[i] = curr;
                        BlockR1IsPresent = true;
                        int val = rand.nextInt(5) + 1;
                        curr.setValue(val);
                        curr.valOfBlock = val;
                    }
                }
            }

            for(int i = 0;i<theblocks1.length;i++){
                if(theblocks1[i] != null){
                    Block cb = theblocks1[i];
                    if(cb.location.getY() > 250){
                        if(!BlockR2IsPresent){
                            for(int j = 0;j<10;j++){
                                //50% chance of block spawning.
                                Random rand = new Random();
                                int decide = rand.nextInt(2);
                                if(decide == 1){
                                    Point bloc = new Point(j*(WIDTH/10),0);
                                    Block curr = new Block(WIDTH,bloc);
                                    root.getChildren().add(curr.realg);
                                    theblocks2[j] = curr;
                                    BlockR2IsPresent = true;
                                    int val = rand.nextInt(5) + 1;
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
            for(int i = 0;i<10;i++){
                if(theblocks1[i] != null){
                    Block currb = theblocks1[i];
                    currb.location.translate(0,1);
                    currb.setPosition(currb.location);
                    if(currb.location.getY() >= 480){
                        root.getChildren().remove(currb.realg);
                        theblocks1[i] = null;
                        BlockR1IsPresent = false;
                    }
                    else BlockR1IsPresent = true;
                }
            }
        }
        if(BlockR2IsPresent){
            for(int i = 0;i<10;i++){
                if(theblocks2[i] != null){
                    Block currb = theblocks2[i];
                    currb.location.translate(0,1);
                    currb.setPosition(currb.location);
                    if(currb.location.getY() >= 480){
                        root.getChildren().remove(currb.realg);
                        theblocks2[i] = null;
                        BlockR2IsPresent = false;
                    }
                    else BlockR2IsPresent = true;
                }
            }
        }
    }

    public void MoveCoins(){
        //Moves the coins downwards
        if(Coin_count != 0){
            for(int i = 0;i<5;i++){
                Coin currc = coins[i];
                if(currc != null){
                    currc.location.translate(0,1);
                    currc.setPosition(currc.location);
                    if(currc.location.getY() >= 490){
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
            magnet.location.translate(0,1);
            magnet.setPosition(magnet.location);
            if(magnet.location.getY() >= 490){
                root.getChildren().remove(magnet.realg);
                TokensOnScreen.remove(magnet);
                magnet = null;
                MagnetIsPresent = false;
            }
        }
    }

    public void MoveShield(){
        if(ShieldIsPresent){
            shield.location.translate(0,1);
            shield.setPosition(shield.location);
            if(shield.location.getY() >= 490){
                root.getChildren().remove(shield.realg);
                TokensOnScreen.remove(shield);
                shield = null;
                ShieldIsPresent = false;
            }
        }
    }

    public void MoveDestructionToken(){
        if(DestructionIsPresent){
            destruction.location.translate(0,1);
            destruction.setPosition(destruction.location);
            if(destruction.location.getY() >= 490){
                root.getChildren().remove(destruction.realg);
                TokensOnScreen.remove(destruction);
                destruction = null;
                DestructionIsPresent = false;
            }
        }
    }

    public void MoveWalls(){
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

    public void MoveSnake(){
        //Shifts all parts of the snakes body left or right.
        ArrayList<Point> spoints = snake.points;
        ArrayList<Circle> scircles = snake.circles;
        for(int i = 0;i<spoints.size();i++){
            Point currp = spoints.get(i);
            currp.translate(snake.xvel,snake.yvel);
            Circle currc = scircles.get(i);
            currc.setLayoutX(currp.getX());
            currc.setLayoutY(currp.getY());
        }
    }

    public void CollisionCheck(){
        //Checks for Collisions between Token and Snake.
        CheckTokenCollision();

        // Code for collision b/w Block and Snake.
        CheckBlockCollision();

    }

    private void CheckBlockCollision(){
        for(int i = 0;i<10;i++){
            if(theblocks1[i] != null){
                Block currb = theblocks1[i];
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
                            root.getChildren().remove(theblocks1[i].realg);
                            theblocks1[i] = null;
                        }
                        else isAlive = false;
                    }
                }
            }
            if(theblocks2[i] != null){
                Block currb = theblocks2[i];
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
                            root.getChildren().remove(theblocks2[i].realg);
                            theblocks2[i] = null;
                        }
                        else isAlive = false;
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
                int xdiff = hloc.getX() - tloc.getX();
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
        for(int j = 0;j<currc.valOfCoin;j++) snake.incLength(diameter /2);
        root.getChildren().remove(currc.realg);
        TokensOnScreen.remove(currc);
        Coin_count--;
    }

    private void ConsumeMagnet(Token currt){
        Magnet currm = (Magnet) currt;


    }

    private void ConsumeShield(Token currt){
        Shield currs = (Shield) currt;

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
        }
        TokensOnScreen.remove(destruction);
        root.getChildren().remove(destruction.realg);
        DestructionIsPresent = false;
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
            rx = rand.nextInt(490);
            for(int i = 0;i<TokensOnScreen.size();i++){
                Token curr = TokensOnScreen.get(i);
                Point loc = curr.location;
                if(loc.getY() <= 60){
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
                        if(loc.getY() <= 50){
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
                        if(loc.getY() <= 80){
                            if((rx > loc.getX() && rx - loc.getX() <= 50) || (rx < loc.getX() && loc.getX() - rx <= 30)) {
                                // Change if changing grid size
                                flag = false;
                            }
                        }
                    }
                }
            }
        }
        return rx;
    }

    // Useless for now.
    public void CheckIfAlive(){
        if(!isAlive){
            System.exit(0);
        }
    }
}
