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

import javax.swing.plaf.basic.BasicGraphicsUtils;
import java.io.*;
import java.sql.SQLOutput;


public class Main extends Application implements Runnable {
//public class Main extends Application {

    Parent root;

    Database database;
    Thread thread;
    Controller controller;

    @Override
    public void run(){
        while(true){
            try{
                saveState();
                Thread.sleep(1000);
            }
            catch (Exception e){

            }
        }
    }

//    public Scene startMM(Stage primaryStage ) throws IOException{
//        root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
//        Scene scene = new Scene(root);
//        Button startgame = (Button) root.lookup("#game");
//        Button lb = (Button) root.lookup("#lb");
//        Button store = (Button) root.lookup("#store");
//        Button profile = (Button) root.lookup("#profile");
//        Button exit = (Button) root.lookup("#exit");
//        Button resume = (Button) root.lookup("#resume");
//        Label userLabel = (Label) root.lookup("#user");
//        Label coinLabel = (Label) root.lookup("#coins");
//
//        if(database == null) database = new Database();
//
//
//        if(database.getCurrentUser() != null){
//            userLabel.setText(database.getCurrentUser().getName());
//            coinLabel.setText(Integer.toString(database.getCurrentUser().getCoins()));
//        }
//        else{
//            userLabel.setText("Guest");
//            coinLabel.setText("0");
//        }
//        startgame.setOnAction(e -> {
//            Scene scene1 = startGame(controller);
//            controller.startAnimationTimers();
//            primaryStage.setScene(scene1);
//            primaryStage.show();
//        });
//
//        resume.setOnAction(e -> {
//            Scene scene1 = resumeGame();
//            primaryStage.setScene(scene1);
//            primaryStage.show();
//
//        });
//
//        lb.setOnAction(e -> {
//            try{
//                Scene scene1 = startLB();
//                primaryStage.setScene(scene1);
//                primaryStage.show();
//
//                Button lbmm = (Button) root.lookup("#lb_mm");
//                lbmm.setOnAction(f -> {
//                    try{
//                        root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
//                        primaryStage.setScene(scene);
//                        primaryStage.show();
//                    }
//                    catch (Exception g){}
//                });
//            }
//            catch (Exception as){}
//        });
//
//        store.setOnAction(e -> {
//            try{
//                Scene scene1 = startStore();
//                primaryStage.setScene(scene1);
//                primaryStage.show();
//                Button storemm = (Button) root.lookup("#store_mm");
//                Button bg0 = (Button) root.lookup("gc0");
//                Button bg1 = (Button) root.lookup("gc1");
//                Button bg2 = (Button) root.lookup("gc2");
//                Button bg3 = (Button) root.lookup("gc3");
//                Button bg4 = (Button) root.lookup("gc4");
//
//                Button s0 = (Button) root.lookup("sc0");
//                Button s1 = (Button) root.lookup("sc1");
//                Button s2 = (Button) root.lookup("sc2");
//                Button s3 = (Button) root.lookup("sc3");
//                Button s4 = (Button) root.lookup("sc4");
//
//                int skinColorInt = database.getController().getGrid().getSnake().getColorNo();
//
//                s0.setOnAction(e1 -> {
//                    if(database.getCurrentUser().getUnlockedSkins().contains(0)) database.getController().getGrid().getSnake().setColor(0);
//                    else if(database.getCurrentUser().getCoins() >= 500) {
//                        database.getController().getGrid().getSnake().setColor(0);
//                        database.getCurrentUser().addSkins(0);
//                    }
//                });
//                s1.setOnAction(e1 -> {
//                    if(database.getCurrentUser().getUnlockedSkins().contains(1)) database.getController().getGrid().getSnake().setColor(1);
//                    else if(database.getCurrentUser().getCoins() >= 500) {
//                        database.getController().getGrid().getSnake().setColor(1);
//                        database.getCurrentUser().addSkins(1);
//                    }
//                });
//                s2.setOnAction(e1 -> {
//                    if(database.getCurrentUser().getUnlockedSkins().contains(2)) database.getController().getGrid().getSnake().setColor(2);
//                    else if(database.getCurrentUser().getCoins() >= 500) {
//                        database.getController().getGrid().getSnake().setColor(2);
//                        database.getCurrentUser().addSkins(2);
//                    }
//                });
//                s3.setOnAction(e1 -> {
//                    if(database.getCurrentUser().getUnlockedSkins().contains(3)) database.getController().getGrid().getSnake().setColor(3);
//                    else if(database.getCurrentUser().getCoins() >= 500) {
//                        database.getController().getGrid().getSnake().setColor(3);
//                        database.getCurrentUser().addSkins(3);
//                    }
//                });
//                s4.setOnAction(e1 -> {
//                    if(database.getCurrentUser().getUnlockedSkins().contains(4)) database.getController().getGrid().getSnake().setColor(4);
//                    else if(database.getCurrentUser().getCoins() >= 500) {
//                        database.getController().getGrid().getSnake().setColor(4);
//                        database.getCurrentUser().addSkins(4);
//                    }
//                });
//                storemm.setOnAction(e1 -> {
//
//                });
////
////                        Button bt = (Button) root.lookup("bt");
////                        Button bs = (Button) root.lookup("bs");
//                primaryStage.setScene(scene);
//                primaryStage.show();
//            }
//            catch (Exception as){}
//        });
//
//        profile.setOnAction(e -> {
//            try{
//                Scene scene1 = openProfile();
//                primaryStage.setScene(scene1);
//                primaryStage.show();
//            }
//            catch (Exception as){}
//        });
//
//        exit.setOnAction(e -> {
//            exitGame();
//        });
//
//        return scene;
//    }

    @Override
    public void start(Stage primaryStage) throws IOException{
        controller = new Controller();
        root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(root);
        Button startgame = (Button) root.lookup("#game");
        Button lb = (Button) root.lookup("#lb");
        Button store = (Button) root.lookup("#store");
        Button profile = (Button) root.lookup("#profile");
        Button exit = (Button) root.lookup("#exit");
        Button resume = (Button) root.lookup("#resume");
        Label userLabel = (Label) root.lookup("#user");
        Label coinLabel = (Label) root.lookup("#coins");
        if(database == null) database = new Database();
        database.setController(controller);

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
                });
                s1.setOnAction(e1 -> {
                    if(database.getCurrentUser().getUnlockedSkins().contains(1)) database.getController().getGrid().getSnake().setColor(1);
                    else if(database.getCurrentUser().getCoins() >= 500) {
                        database.getController().getGrid().getSnake().setColor(1);
                        database.getCurrentUser().addSkins(1);
                    }
                });
                s2.setOnAction(e1 -> {
                    if(database.getCurrentUser().getUnlockedSkins().contains(2)) database.getController().getGrid().getSnake().setColor(2);
                    else if(database.getCurrentUser().getCoins() >= 500) {
                        database.getController().getGrid().getSnake().setColor(2);
                        database.getCurrentUser().addSkins(2);
                    }
                });
                s3.setOnAction(e1 -> {
                    if(database.getCurrentUser().getUnlockedSkins().contains(3)) database.getController().getGrid().getSnake().setColor(3);
                    else if(database.getCurrentUser().getCoins() >= 500) {
                        database.getController().getGrid().getSnake().setColor(3);
                        database.getCurrentUser().addSkins(3);
                    }
                    System.out.println("fdfs");

                });
                int z = 0;
                s4.setOnAction(e1 -> {
                    System.out.println("fdfs");
                    if(database.getCurrentUser().getUnlockedSkins().contains(4)) database.getController().getGrid().getSnake().setColor(4);
                    else if(database.getCurrentUser().getCoins() >= 500) {
                        System.out.println("ll");
                        database.getController().getGrid().getSnake().setColor(4);
                        System.out.println("kk");
                        database.getCurrentUser().addSkins(4);
                    }
                    System.out.println("fdfs");
                });
//                storemm.setOnAction(e1 -> {
//                        try{
//                            root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
//                            primaryStage.setScene(scene);
//                            primaryStage.show();
//                        }
//                        catch (Exception g){
//                            System.out.println("jkk");
//                        }
//
//                });
//
//                        Button bt = (Button) root.lookup("bt");
//                        Button bs = (Button) root.lookup("bs");
//                primaryStage.setScene(scene);
//                primaryStage.show();
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
    }

    public Scene resumeGame()  {
        try{
            loadState();
        }catch (Exception lol){
            System.out.println("lol");
        }
        Scene scene1 = startGame(database.getController());
//        database.getController().startAnimationTimers();
        return scene1;
    }

    public Scene startStore() throws IOException {
        root = FXMLLoader.load(getClass().getResource("Store.fxml"));
        return new Scene(root);
    }

    public Scene openProfile() throws IOException {
        root = FXMLLoader.load(getClass().getResource("profile.fxml"));
        return new Scene(root);
    }
    public void exitGame(){}

    public Scene startLB() throws IOException {
        root = FXMLLoader.load(getClass().getResource("LeaderBoard.fxml"));
        String[][] tties  = database.getTopTenScores();
        int ttiesLength = database.getTtsLength();
        for (int i = 0; i < ttiesLength; i++) {
            Label label = (Label) root.lookup("u" + i + 1);
            label.setText(tties[i][0]);
            Label label1 = (Label) root.lookup("s" + i + 1);
            label1.setText(tties[i][1]);
            Label label2 = (Label) root.lookup("d" + i + 1);
            label2.setText(tties[i][2]);
        }
        return new Scene(root);
    }

    public Scene startGame(Controller controller){
//        System.out.println("lol");
        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
        Controller Admin;
        if(controller == null) Admin = new Controller();
        else Admin = controller;

        this.database.setController(Admin);

        if(Admin.getRoot() == null) {
            System.out.println("yhis happens");
            Admin.restore();
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
                    case P:
                        try {
                            saveState();
                            System.out.println("done");
                        }catch (Exception e){

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

    public static void main(String[] args) {
        launch(args);
    }
}

