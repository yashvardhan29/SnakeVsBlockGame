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

    Parent root;

    @Override
    public void start(Stage primaryStage) throws IOException{
        root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(root);
        Button startgame = (Button) root.lookup("#game");
        Button lb = (Button) root.lookup("#lb");
        Button store = (Button) root.lookup("#store");
        Button profile = (Button) root.lookup("#profile");
        Button exit = (Button) root.lookup("#exit");

        startgame.setOnAction(e -> {
            Scene scene1 = startGame();
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

