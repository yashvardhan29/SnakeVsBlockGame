package sample;

import javax.imageio.IIOException;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Database implements Serializable {
    private HashMap<String,User> users;
    private User currentUser;
    private Controller controller;
    private String[][] topTenScores;
    private int ttsLength;
    private boolean showResumeButton;

    Database(){
        users = new HashMap<>();
        currentUser = new User("Guest","");
        users.put("Guest", currentUser);
        topTenScores = new String[10][3];
        ttsLength = 0;
//        for (int i = 0; i < 10; i++) for (int j = 0; j < 3; j++) topTenScores[i][j] = "";
    }

    public boolean isShowResumeButton() {
        return showResumeButton;
    }

    public void setShowResumeButton(boolean b){
        showResumeButton = b;
    }

    public void updateTopTenScores(String name, String score, String date){
        if(ttsLength == 0){
            topTenScores[0][0] = name;
            topTenScores[0][1] = score;
            topTenScores[0][2] = date;
            ttsLength++;
        }
        else if(ttsLength < 10) {
            for (int i = 9; i > -1; i--) {
                if (topTenScores[i][1] != null){
                    topTenScores[i+1][0] = name;
                    topTenScores[i+1][1] = score;
                    topTenScores[i+1][2] = date;
                    ttsLength++;
                    break;
                }
            }
        }
        else{
            if(Integer.parseInt(topTenScores[0][2]) < Integer.parseInt(score)){
                topTenScores[0][0] = name;
                topTenScores[0][1] = score;
                topTenScores[0][2] = date;
            }
        }
        int[] numero = new int[ttsLength];
        for (int i = 0; i < ttsLength; i++) numero[i] = Integer.parseInt(topTenScores[i][1]);
        quicksort(numero,0,ttsLength - 1,topTenScores);
    }

    public String[][] getTopTenScores() {
        return topTenScores;
    }

    public int getTtsLength() {
        return ttsLength;
    }

    int partirion(int arr[], int l, int h, String orig[][]){
        int pivot = arr[h];
        int i = l - 1;
        for(int j = l; j < h; j++){
            if(arr[j] < pivot){
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                for (int k = 0; k < 3; k++) {
                    String temps = orig[i][k];
                    orig[i][k] = orig[j][k];
                    orig[j][k] = temps;
                }
            }
        }
        int temp = arr[h];
        arr[h] = arr[i+1];
        arr[i+1] = temp;
        for (int k = 0; k < 3; k++) {
            String temps = orig[h][k];
            orig[h][k] = orig[i+1][k];
            orig[i+1][k] = temps;
        }
        return i + 1;
    }

    void quicksort(int arr[], int l, int h, String orig[][]){
        if(l < h) {
            int p = partirion(arr,l,h,orig);
            quicksort(arr,l,p - 1,orig);
            quicksort(arr,p + 1, h,orig);
        }
    }

    public void login(String n, String p){
        if(users.containsKey(n) && users.get(n).getPassword().equals(p)) currentUser = users.get(n);
        else {
            currentUser = new User(n,p);
            users.put(n,currentUser);
        }
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
//        grid = controller.getGrid();
    }
}
