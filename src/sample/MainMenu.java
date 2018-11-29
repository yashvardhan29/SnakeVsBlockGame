package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.beans.PropertyChangeSupport;
import java.io.*;


public class MainMenu {

    public Button game;
    public Button resume;
    public Button store;
    public Button lb;
    public Button profile;
    public Button exit;

    private Database database;
    protected PropertyChangeSupport observer;


    @FXML
    protected void StartGame(ActionEvent event) throws IOException{
        Controller Admin = new Controller();
        loadState();
        database.setController(Admin);
        Scene scene = new Scene(Admin.getRoot(), 500, 800, Color.BLACK);
        Admin.startAnimationTimers();
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Snake Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event1 -> {
            try {
                saveState();
            }
            catch (IOException e){
                System.out.println("I/O exception while serialising");
            }
        });

//
//        while (Admin.grid.isAlive){
//
//        }
        System.out.println("Hello");

        Stage stage = (Stage) store.getScene().getWindow();
        stage.close();

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
    }

    @FXML
    protected void ShowLeaderboard(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LeaderBoard.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Snake Game");
        primaryStage.setScene(new Scene(root1));
        primaryStage.show();
        Stage stage = (Stage) store.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void OpenStore(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Store.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Snake Game");
        primaryStage.setScene(new Scene(root1));
        primaryStage.show();
        Stage stage = (Stage) store.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void OpenProfile(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Profile.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Snake Game");
        primaryStage.setScene(new Scene(root1));
        primaryStage.show();
        Stage stage = (Stage) store.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void Exit(ActionEvent event) throws IOException{
        System.exit(0);
    }

    @FXML
    protected void Resume(ActionEvent event) throws IOException{

    }

    public void loadState() throws IOException {
        ObjectInputStream in = null;

        try {
            in = new ObjectInputStream(new FileInputStream("data.txt"));
            database = (Database) in.readObject();
        }
        catch (Exception e) {
            System.out.println("Exception raised while deserialising");
        }
        finally {
            in.close();
        }

    }

    public void saveState() throws IOException {
        ObjectOutputStream out = null;
        try{

            out = new ObjectOutputStream(new FileOutputStream("data.txt"));
            out.writeObject(database);
            out.close();
        }
        catch (Exception e){
            System.out.println("Exception raised while serialising");
        }
        finally {
            if(out != null) out.close();
        }
    }

}
