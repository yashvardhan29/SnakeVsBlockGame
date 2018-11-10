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

    Database(){
        users = new HashMap<>();
        currentUser = null;

    }

    public User login(String n, String p){
        if(users.containsKey(n) && users.get(n).getPassword().equals(p)) {
            currentUser = users.get(n);
            return currentUser;
        }
        else return null;
    }

    public Controller getController() {
        return controller;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setController(Controller c) {
        controller = c;
        c.setDatabase(this);
        grid = controller.getGrid();
    }
}
