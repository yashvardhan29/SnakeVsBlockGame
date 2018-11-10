package sample;

import javax.imageio.IIOException;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Database implements Serializable {
    private HashMap<String,User> users;
    private User currentUser;
    private Controller controller;
    private Grid grid;
    private Snake snake;

    Database(Controller c){
        users = new HashMap<>();
        currentUser = null;
        controller = c;
        c.setDatabase(this);
        grid = controller.getGrid();
        snake = grid.getSnake();
    }

    public User login(String n, String p){
        if(users.containsKey(n) && users.get(n).getPassword().equals(p)) {
            currentUser = users.get(n);
            return currentUser;
        }
        else return null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
