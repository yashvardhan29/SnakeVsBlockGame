package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.plaf.basic.BasicGraphicsUtils;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Main class.
 */
public class Main extends Application implements Runnable {
    /**
     * The main Parent
     */
    Parent root;
    /**
     * Super class containing everything
     */
    Database database;
    /**
     * thread used to serialize and fetch commands from firebase
     */
    Thread thread;
    /**
     * controller for game
     */
    Controller controller;
    /**
     * Primary stage
     */
    Stage PS;
    /**
     * Main menu scene
     */
    Scene mainScene;
    /**
     * array used to store commands fetched from firebase
     */
    int[] firebaseCommmand;
    /**
     * boolean true if returned to mainmenu after game end
     */
    boolean r;
    /**
     * used to set snake's skin color
     */
    int skinCo;

    /**
     * Used to return database
     * @return
     */
    public Database getDatabase() {
        return database;
    }

    /**
     * thread's run function used to serialize and fetch commands from firebase.
     */
    @Override
    public void run(){
        while(true){
            try{
                saveState();
                firebaseCommmand = getFirebaseCommands();
                if(firebaseCommmand[0] == 1) database.getController().getGrid().getSnake().xvel = firebaseCommmand[1];
                Thread.sleep(10);
            }
            catch (Exception e){

            }
        }
    }

    /**
     * Fetches commands sent from the android app from firebase and returns them.
     * @return int array containing commands
     * @throws IOException
     */
    public int[] getFirebaseCommands() throws IOException {
        String url = "https://snakevsblocks-1cec1.firebaseio.com/foo.json";
        JSONObject json = readJsonFromUrl(url);
        int[] x = new int[2];
        x[0] = Integer.parseInt(json.get("control").toString());
        x[1] = Integer.parseInt(json.get("move").toString());
        return x;
    }

    /**
     * Sets stage for Main Menu
     * @param primaryStage
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException{
        PS = primaryStage;
        ObjectInputStream in = null;

        try {
            in = new ObjectInputStream(new FileInputStream("data.txt"));
            database = (Database) in.readObject();
        }
        catch (Exception e) {
            System.out.println("here1");
        }
        finally {
            in.close();
        }
        if(controller == null) controller = new Controller();
        controller.getGrid().setMain(this);
        root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(root);
        mainScene = scene;
        Button startgame = (Button) root.lookup("#game");
        Button lb = (Button) root.lookup("#lb");
        Button llb = (Button) root.lookup("#llb");
        Button store = (Button) root.lookup("#store");
        Button profile = (Button) root.lookup("#profile");
        Button exit = (Button) root.lookup("#exit");
        Button resume = (Button) root.lookup("#resume");
        Label userLabel = (Label) root.lookup("#user");
        Label coinLabel = (Label) root.lookup("#coins");
        if(database == null) database = Database.getInstance();
        database.setController(controller);

        if(!loadStateResume()) {
            resume.setOpacity(0);
            database.setShowResumeButton(false);
        }

        if(database.getCurrentUser() != null){
            userLabel.setText(database.getCurrentUser().getName());
            coinLabel.setText(Integer.toString(database.getCurrentUser().getCoins()));
        }
        else{
            userLabel.setText("Guest");
            coinLabel.setText("0");
        }
        startgame.setOnAction(e -> {
            Scene scene1 = startGame(controller);
            controller.startAnimationTimers();
            primaryStage.setScene(scene1);
            primaryStage.show();
        });

        resume.setOnAction(e -> {
            Scene scene1 = resumeGame();
            primaryStage.setScene(scene1);
            primaryStage.show();

        });

        lb.setOnAction(e -> {
            try{
                Scene scene1 = startLB();
                primaryStage.setScene(scene1);
                primaryStage.show();

                Button lbmm = (Button) root.lookup("#lb_mm");
                lbmm.setOnAction(f -> {
                    try{
                        root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
                        primaryStage.setScene(scene);
                        primaryStage.show();
                    }
                    catch (Exception g){}
                });
            }
            catch (Exception as){}
        });

        llb.setOnAction(e -> {
            try{
                Scene scene1 = startLLB();
                primaryStage.setScene(scene1);
                primaryStage.show();

                Button lbmm = (Button) root.lookup("#lb_mm");
                lbmm.setOnAction(f -> {
                    try{
                        root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
                        primaryStage.setScene(scene);
                        primaryStage.show();
                    }
                    catch (Exception g){}
                });
            }
            catch (Exception as){}
        });

        store.setOnAction(e -> {
            try{
                Scene scene1 = startStore();
                primaryStage.setScene(scene1);
                primaryStage.show();
                Button storemm = (Button) root.lookup("#store_mm");
                storemm.setOnAction(f -> {
                    try{
                        root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
                        primaryStage.setScene(scene);
                        primaryStage.show();
                    }
                    catch (Exception g){}
                });
                System.out.println("asd");
//                Button bg0 = (Button) root.lookup("#gc0");
//                Button bg1 = (Button) root.lookup("#gc1");
//                Button bg2 = (Button) root.lookup("#gc2");
//                Button bg3 = (Button) root.lookup("#gc3");
//                Button bg4 = (Button) root.lookup("#gc4");
                if(root == null) System.out.println("bkl");
                Button s0 = (Button) root.lookup("#sc0");
                System.out.println("Afg");
                Button s1 = (Button) root.lookup("#sc1");
                Button s2 = (Button) root.lookup("#sc2");
                Button s3 = (Button) root.lookup("#sc3");
                Button s4 = (Button) root.lookup("#sc4");

                if(database.getController() == null) System.out.println("this");

                //int skinColorInt = database.getController().getGrid().getSnake().getColorNo();
                System.out.println("a");
                s0.setOnAction(e1 -> {
                    if(database.getCurrentUser().getUnlockedSkins().contains(0)) database.getController().getGrid().getSnake().setColor(0);
                    else if(database.getCurrentUser().getCoins() >= 500) {
                        database.getController().getGrid().getSnake().setColor(0);
                        database.getCurrentUser().addSkins(0);
                    }
                    skinCo = database.getController().getGrid().getSnake().colorNo;

                    coinLabel.setText(Integer.toString(database.getCurrentUser().getCoins()));
                });
                s1.setOnAction(e1 -> {
                    if(database.getCurrentUser().getUnlockedSkins().contains(1)) database.getController().getGrid().getSnake().setColor(1);
                    else if(database.getCurrentUser().getCoins() >= 500) {
                        database.getController().getGrid().getSnake().setColor(1);
                        database.getCurrentUser().addSkins(1);
                    }
                    skinCo = database.getController().getGrid().getSnake().colorNo;

                    coinLabel.setText(Integer.toString(database.getCurrentUser().getCoins()));

                });
                s2.setOnAction(e1 -> {
                    if(database.getCurrentUser().getUnlockedSkins().contains(2)) database.getController().getGrid().getSnake().setColor(2);
                    else if(database.getCurrentUser().getCoins() >= 500) {
                        database.getController().getGrid().getSnake().setColor(2);
                        database.getCurrentUser().addSkins(2);
                    }
                    coinLabel.setText(Integer.toString(database.getCurrentUser().getCoins()));
                    skinCo = database.getController().getGrid().getSnake().colorNo;


                });
                s3.setOnAction(e1 -> {
                    if(database.getCurrentUser().getUnlockedSkins().contains(3)) database.getController().getGrid().getSnake().setColor(3);
                    else if(database.getCurrentUser().getCoins() >= 500) {
                        database.getController().getGrid().getSnake().setColor(3);
                        database.getCurrentUser().addSkins(3);
                    }
                    skinCo = database.getController().getGrid().getSnake().colorNo;

                    coinLabel.setText(Integer.toString(database.getCurrentUser().getCoins()));

                });
                int z = 0;
                s4.setOnAction(e1 -> {
                    if(database.getCurrentUser().getUnlockedSkins().contains(4)) database.getController().getGrid().getSnake().setColor(4);
                    else if(database.getCurrentUser().getCoins() >= 500) {
                        database.getController().getGrid().getSnake().setColor(4);
                        database.getCurrentUser().addSkins(4);
                        System.out.println("skin 4");
                    }
                    skinCo = 4;
                    coinLabel.setText(Integer.toString(database.getCurrentUser().getCoins()));
                    System.out.println(skinCo + " fsd");
                });
            }
            catch (Exception as){
                System.out.println(as.getStackTrace());
            }
        });

        profile.setOnAction(e -> {
            try{
                Scene scene1 = openProfile();
                TextField userName = (TextField) root.lookup("#userName");
                TextField password = (TextField) root.lookup("#password");
                Button login = (Button) root.lookup("#logIn");
                Button logout = (Button) root.lookup("#logOut");
                login.setOnAction(e123 -> {
                    String un = userName.getText();
                    String pwd = password.getText();
                    System.out.println(un + " " + pwd);
                    database.login(un,pwd);
                    userLabel.setText(database.getCurrentUser().getName() + ": ");
                    coinLabel.setText(Integer.toString(database.getCurrentUser().getCoins()));
                    primaryStage.setScene(scene);
                    primaryStage.show();
                });
                logout.setOnAction(e123 -> {
                    database.login("Guest","");
                    userLabel.setText(database.getCurrentUser().getName() + ": ");
                    coinLabel.setText(Integer.toString(database.getCurrentUser().getCoins()));
                    primaryStage.setScene(scene);
                    primaryStage.show();
                });
                primaryStage.setScene(scene1);
                primaryStage.show();
            }
            catch (Exception as){}
        });

        exit.setOnAction(e -> {
            exitGame();
        });


        primaryStage.setTitle("Snake Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(e123 -> {
            if(controller.grid.snake != null && controller.grid.isAlive){
                database.setShowResumeButton(true);
                System.out.println("lol");
            }
            try{
                saveState();
            }
            catch (Exception e){
                System.out.println(312);
            }
        });
    }

    /**
     * Resumes game if previous game was incomplete
     * @return
     */
    public Scene resumeGame()  {
        try{
            loadState();
        }catch (Exception lol){
            System.out.println("lol");
        }
        Scene scene1 = startGame(database.getController());
        return scene1;
    }

    /**
     * returns scene after loading store.fxml into it
     * @return
     * @throws IOException
     */
    public Scene startStore() throws IOException {
        root = FXMLLoader.load(getClass().getResource("Store.fxml"));
        return new Scene(root);
    }

    /**
     * returns scene after loading Profile.fxml into it
     * @return
     * @throws IOException
     */
    public Scene openProfile() throws IOException {
        root = FXMLLoader.load(getClass().getResource("profile.fxml"));
        return new Scene(root);
    }

    /**
     * Exits game
     */
    public void exitGame(){
        System.exit(0);
    }

    /**
     * returns scene after loading Leaderboard.fxml into it. Used for Global Leaderboard
     * @return
     * @throws IOException
     */
    public Scene startLB() throws IOException {

        root = FXMLLoader.load(getClass().getResource("LeaderBoard.fxml"));

        String[][] tties  = loadStateTTies();
        int ttiesLength = loadStateTTiesLength();

        for (int i = 0; i < ttiesLength; i++) {
            Label label = (Label) root.lookup("#u" +  (i + 1));
            label.setText(tties[ttiesLength - 1 - i][0]);
            Label label1 = (Label) root.lookup("#s" + (i + 1));
            label1.setText(tties[ttiesLength - 1 - i][1]);
            Label label2 = (Label) root.lookup("#d" + (i + 1));
            label2.setText(tties[ttiesLength - 1 - i][2]);
        }
        System.out.println(ttiesLength);
        return new Scene(root);
    }

    /**
     * returns scene after loading LocalLeaderboard.fxml into it. Used for Local Leaderboard
     * @return
     * @throws IOException
     */
    public Scene startLLB() throws IOException {

        root = FXMLLoader.load(getClass().getResource("LocalLeaderBoard.fxml"));

        String[][] tties  = loadStateTTiesLocal();
        int ttiesLength = loadStateTTiesLengthLocal();

        for (int i = 0; i < ttiesLength; i++) {
            Label label = (Label) root.lookup("#u" +  (i + 1));
            label.setText(tties[ttiesLength - 1 - i][0]);
            Label label1 = (Label) root.lookup("#s" + (i + 1));
            label1.setText(tties[ttiesLength - 1 - i][1]);
            Label label2 = (Label) root.lookup("#d" + (i + 1));
            label2.setText(tties[ttiesLength - 1 - i][2]);
        }
        System.out.println(ttiesLength);
        return new Scene(root);
    }

    /**
     * returns the scene used to load the actual game
     * @param controller null if new game and an object if previous game was incomplete
     * @return
     */
    public Scene startGame(Controller controller){
        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
        Controller Admin;
        if(this.r) {
            Admin = new Controller();
            this.controller = Admin;
            database.setController(Admin);
            this.controller.getGrid().setMain(this);
            controller.getGrid().getSnake().setColor(skinCo);
            System.out.println(skinCo + " is the color");
            this.r = false;
        }
        else Admin = controller;
        System.out.println(controller.getGrid().getSnake().getColorNo() + " skin");
        this.database.setController(Admin);

        if(Admin.getRoot() == null) {
            Admin.restore();
            Admin.grid.setMain(this);
        }
        Scene scene = new Scene(Admin.getRoot(), 500, 800, Color.BLACK);
        //KeyHandler for KeyPresses
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case LEFT:
                        Admin.grid.snake.xvel = -1;
                        Admin.grid.snake.yvel = 0;
                        break;
                    case RIGHT:
                        Admin.grid.snake.xvel = 1;
                        Admin.grid.snake.yvel = 0;
                        break;
                    case ENTER:
                        if(!Admin.getGrid().isAlive()) {
                            System.out.println("setting scene");
                            PS.setScene(scene);
                            PS.show();
                        }
                }
            }
        });

        //KeyHandler for KeyReleases
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case LEFT:
                        Admin.grid.snake.xvel = 0;
                        break;
                    case RIGHT:
                        Admin.grid.snake.xvel = 0;
                        break;
                }
            }
        });

        return scene;
    }

    /**
     * Used for serialization
     * @throws IOException
     */
    public void saveState() throws IOException {
        ObjectOutputStream out = null;
        try{

            out = new ObjectOutputStream(new FileOutputStream("data.txt"));
            out.writeObject(database);
            out.close();
        } catch (IOException e){
        } finally {
            if(out != null) out.close();
        }
    }

    /**
     * Used for deserialization
     * @throws IOException
     */
    public void loadState() throws IOException {
        ObjectInputStream in = null;

        try {
            in = new ObjectInputStream(new FileInputStream("data.txt"));
            database = (Database) in.readObject();
            controller = database.getController();
        }
        catch (Exception e) {
            System.out.println("here");
        }
        finally {
            in.close();
        }
    }


    /**
     * Deserializes just the array storing global top ten scores
     * @return
     * @throws IOException
     */
    public String[][] loadStateTTies() throws IOException{
        ObjectInputStream in = null;
        String [][] tties1 = null;
        try {
            in = new ObjectInputStream(new FileInputStream("data.txt"));
            tties1 = ((Database) in.readObject()).getTopTenScores();
        }
        catch (Exception e) {
            System.out.println("main.java 456 catch");
            System.out.println(e.getMessage());
        }
        finally {
            in.close();
        }

        return tties1;
    }

    /**
     * Deserializes just length of the array storing global top ten scores
     * @return
     * @throws IOException
     */
    public int loadStateTTiesLength() throws IOException{
        ObjectInputStream in = null;
        int i = 0;
        try {
            in = new ObjectInputStream(new FileInputStream("data.txt"));
            i = ((Database) in.readObject()).getTtsLength();
        }
        catch (Exception e) {
            System.out.println("main.java 474 catch");
            System.out.println(e.getMessage());
        }
        finally {
            in.close();
        }

        return i;
    }

    /**
     * Deserializes just the array storing local top ten scores
     * @return
     * @throws IOException
     */
    public String[][] loadStateTTiesLocal() throws IOException{
        ObjectInputStream in = null;
        String [][] tties1 = null;
        try {
            in = new ObjectInputStream(new FileInputStream("data.txt"));
            tties1 = ((Database) in.readObject()).getCurrentUser().getTopTenScores();
        }
        catch (Exception e) {
            System.out.println("main.java 456 catch");
            System.out.println(e.getMessage());
        }
        finally {
            in.close();
        }

        return tties1;
    }

    /**
     * Deserializes just length of the array storing local top ten scores
     * @return
     * @throws IOException
     */
    public int loadStateTTiesLengthLocal() throws IOException{
        ObjectInputStream in = null;
        int i = 0;
        try {
            in = new ObjectInputStream(new FileInputStream("data.txt"));
            i = ((Database) in.readObject()).getCurrentUser().getTtsLength();
        }
        catch (Exception e) {
            System.out.println("main.java 474 catch");
            System.out.println(e.getMessage());
        }
        finally {
            in.close();
        }

        return i;
    }

    /**
     * Loads boolean used to check if last game was complete or not
     * @return
     * @throws IOException
     */
    public boolean loadStateResume() throws IOException{
        ObjectInputStream in = null;
        boolean b =  false;
        try {
            in = new ObjectInputStream(new FileInputStream("data.txt"));
            Database temp = (Database) in.readObject();
            b = temp.isShowResumeButton();
            System.out.println(b + "resume");
        }
        catch (Exception e) {
            System.out.println("main.java 541 catch");
            System.out.println(e.getMessage());
        }
        finally {
            in.close();
        }

        return b;
    }

    /**
     * main
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Used to parse input from bufferedreader and return it as a string
     * source: https://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
     * @param rd
     * @return
     * @throws IOException
     */
    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    /**
     * Used to pull json and return a JSONObject from a url
     * source: https://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
     * @param url Provides json
     * @return required json as a JSONObject
     * @throws IOException
     * @throws JSONException
     */
    public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

}

