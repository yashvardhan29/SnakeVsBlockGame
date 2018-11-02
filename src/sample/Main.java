package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException{
//        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
//        Scene scene = new Scene(root);
//        Button startgame = (Button) root.lookup("#game");
//        Button lb = (Button) root.lookup("#lb");
//        Button store = (Button) root.lookup("#store");
//        Button lo = (Button) root.lookup("#logout");
//        Button exit = (Button) root.lookup("#exit");
//
//        startgame.setOnAction(e -> {
//            Scene scene1 = startGame();
//            primaryStage.setScene(scene1);
//            primaryStage.show();
//        });
//
//        lb.setOnAction(e -> {
//            try{
//                Scene scene1 = startLB();
//                primaryStage.setScene(scene1);
//                primaryStage.show();
//            }
//            catch (Exception as){}
//        });
//
//        store.setOnAction(e -> {
//            try{
//                Scene scene1 = startStore();
//                primaryStage.setScene(scene1);
//                primaryStage.show();
//            }
//            catch (Exception as){}
//        });
//
//        lo.setOnAction(e -> {
//            try{
//                Scene scene1 = logout();
//                primaryStage.setScene(scene1);
//                primaryStage.show();
//            }
//            catch (Exception as){}
//        });
//
//        exit.setOnAction(e -> {
//            exitGame();
//        });

        Controller Admin = new Controller(); //Instantiation of controller object

        Scene scene = new Scene(Admin.getRoot(), 500, 500, Color.BLACK);

        //KeyHandler for KeyPresses
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case LEFT:
                        Admin.xvel = -1;
                        Admin.yvel = 0;
                        break;
                    case RIGHT:
                        Admin.xvel = 1;
                        Admin.yvel = 0;
                        break;
                    case P:
                        Admin.snake.incLength(5);
                }
            }
        });

        //KeyHandler for KeyReleases
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case LEFT:
                        Admin.xvel = 0;
                        break;
                    case RIGHT:
                        Admin.xvel = 0;
                        break;
                }
            }
        });

//        Scene scene = startGame();
        primaryStage.setTitle("Snake Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Scene startStore() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Store.fxml"));
        return new Scene(root);
    }

    public Scene logout(){return null;}
    public void exitGame(){}

    public Scene startLB() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LeaderBoard.fxml"));
        return new Scene(root);
    }

    public Scene startGame(){
        Controller Admin = new Controller(); //Instantiation of controller object

        Scene scene = new Scene(Admin.getRoot(), 500, 500, Color.BLACK);

        //KeyHandler for KeyPresses
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case LEFT:
                        Admin.xvel = -1;
                        Admin.yvel = 0;
                        break;
                    case RIGHT:
                        Admin.xvel = 1;
                        Admin.yvel = 0;
                        break;
                    case P:
                        Admin.snake.incLength(5);
                }
            }
        });

        //KeyHandler for KeyReleases
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case LEFT:
                        Admin.xvel = 0;
                        break;
                    case RIGHT:
                        Admin.xvel = 0;
                        break;
                }
            }
        });

        return scene;
    }


    public static void main(String[] args) {
        launch(args);
    }
}

