package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Store {

    @FXML
    public Button store_mm;

    @FXML
    protected void MainMenu(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Snake Game");
        primaryStage.setScene(new Scene(root1));
        primaryStage.show();
        Stage stage = (Stage) store_mm.getScene().getWindow();
        stage.close();
    }
}
