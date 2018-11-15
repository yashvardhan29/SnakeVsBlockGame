package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.plaf.basic.BasicGraphicsUtils;
import java.io.*;


public class Main extends Application implements Runnable {
//public class Main extends Application {

    Parent root;

    Database database;
    Thread thread;

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

    @Override
    public void start(Stage primaryStage) throws IOException{
//        thread = new Thread(this);
//        thread.setDaemon(true);
//        thread.start();
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


        if(database.getCurrentUser() != null){
            userLabel.setText(database.getCurrentUser().getName());
            coinLabel.setText(Integer.toString(database.getCurrentUser().getCoins()));
        }
        else{
            userLabel.setText("Guest");
            coinLabel.setText("0");
        }
        startgame.setOnAction(e -> {
            Scene scene1 = startGame(null);
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
            }
            catch (Exception as){}
        });

        profile.setOnAction(e -> {
            try{
                Scene scene1 = openProfile();
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

        }
        Scene scene1 = startGame(database.getController());
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
            System.out.println("11");
            out.writeObject(database);
            System.out.println("22");
            out.close();
            System.out.println("saved123");
        } catch (IOException e){
            System.out.println("lol");
        } finally {
            if(out != null) out.close();
        }
    }

    public void loadState() throws IOException {
        ObjectInputStream in = null;
//        while(true) {
            try {
                in = new ObjectInputStream(new FileInputStream("data.txt"));
                database = (Database) in.readObject();
                System.out.println("loaded123");
//                break;
            } catch (Exception e) {
                System.out.println("l");
            } finally {
                in.close();
            }
//        }
    }

    public static void main(String[] args) {
        launch(args);
//        Database d = new Database();
//        d.topTenScores =
//        String[][] one = new String[6][3];
//        for (int i = 0; i < 5; i++) {
//            one[i][0] = ("a" + i);
//        }
//        for (int i = 0; i < 5; i++) {
//            one[i][1] = Integer.toString(25 - i*i);
//        }
//        for (int i = 0; i < 5; i++) {
//            one[i][2] = ("b" + i);
//        }
//        for (int i = 0; i < 5; i++) {
//            System.out.println(one[i][0] + " " + one[i][1] + " " + one[i][2]);
//        }
//        int a[] = new int[5];
//        for (int i = 0; i < 5; i++) {
//            a[i] = Integer.parseInt(one[i][1]);
//        }
//        for (int i = 0; i < 5; i++) {
//            System.out.println(a[i] + " ");
//        }
//
//        quicksort(a,0,4,one);
//        for (int i = 0; i < 5; i++) {
//            System.out.println(one[i][0] + " " + one[i][1] + " " + one[i][2]);
//        }
////        String x = "a";
////        System.out.println(x + 1);
    }
//    static int partirion(int arr[], int l, int h, String orig[][]){
//        int pivot = arr[h];
//        int i = l - 1;
//        for(int j = l; j < h; j++){
//            if(arr[j] < pivot){
//                i++;
//                int temp = arr[i];
//                arr[i] = arr[j];
//                arr[j] = temp;
//                for (int k = 0; k < 3; k++) {
//                    String temps = orig[i][k];
//                    orig[i][k] = orig[j][k];
//                    orig[j][k] = temps;
//                }
//            }
//        }
//        int temp = arr[h];
//        arr[h] = arr[i+1];
//        arr[i+1] = temp;
//        for (int k = 0; k < 3; k++) {
//            String temps = orig[h][k];
//            orig[h][k] = orig[i+1][k];
//            orig[i+1][k] = temps;
//        }
//        return i + 1;
//    }
//
//    static void quicksort(int arr[], int l, int h, String orig[][]){
//        if(l < h) {
//            int p = partirion(arr,l,h,orig);
//            quicksort(arr,l,p - 1,orig);
//            quicksort(arr,p + 1, h,orig);
//        }
//    }
}

