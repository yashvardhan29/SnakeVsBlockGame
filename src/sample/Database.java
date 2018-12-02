package sample;

import java.io.*;
import java.util.HashMap;

/**
 * Database class.
 */
public class Database implements Serializable {
    /**
     * static database instance
     */
    private static Database database = null;
    /**
     * Stores list of users using username as key
     */
    private HashMap<String,User> users;
    /**
     * current user
     */
    private User currentUser;
    /**
     * current controller
     */
    private Controller controller;
    /*
    * list of global top ten scores
     */
    private String[][] topTenScores;
    /**
     * no of scores if less than 10
     */
    private int ttsLength;
    /**
     * stores if previous game was complete or not
     */
    private boolean showResumeButton;

    public static Database getInstance(){
        if(database == null) database = new Database();
        return database;
    }

    private Database(){
        users = new HashMap<>();
        currentUser = new User("Guest","");
        users.put("Guest", currentUser);
        topTenScores = new String[10][3];
        ttsLength = 0;
    }

    /**
     * returns showResumeButton
     * @return
     */
    public boolean isShowResumeButton() {
        return showResumeButton;
    }

    /**
     * mutator method for showResumeButton
     * @param b
     */
    public void setShowResumeButton(boolean b){
        showResumeButton = b;
    }

    /**
     * checks if new score is greater than any score in the top ten scores
     * and replaces the least if yes
     * @param name of user
     * @param score of user
     * @param date and time
     */
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

    /**
     * returns topTenScores
     * @return
     */
    public String[][] getTopTenScores() {
        return topTenScores;
    }

    /**
     * return ttsLength
     * @return
     */
    public int getTtsLength() {
        return ttsLength;
    }

    /**
     * Used to Partition array for quicksort
     * @param arr array to partioan
     * @param l lower index
     * @param h upper inde
     * @param orig String array to partition
     * @return
     */
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

    /**
     * sorts array using quicksort on basis of score
     * @param arr array to sort
     * @param l lower index
     * @param h upper index
     * @param orig original string array
     */
    void quicksort(int arr[], int l, int h, String orig[][]){
        if(l < h) {
            int p = partirion(arr,l,h,orig);
            quicksort(arr,l,p - 1,orig);
            quicksort(arr,p + 1, h,orig);
        }
    }

    /**
     * Used to login to a profile or create one
     * @param n username
     * @param p password
     */
    public void login(String n, String p){
        if(users.containsKey(n) && users.get(n).getPassword().equals(p)) currentUser = users.get(n);
        else {
            currentUser = new User(n,p);
            users.put(n,currentUser);
        }
    }

    /**
     * returns controller
     * @return
     */
    public Controller getController() {
        return controller;
    }

    /**
     * returns currentUser
     * @return
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * mutator method for controller
     * @param c controller for game gui
     */
    public void setController(Controller c) {
        controller = c;
        c.setDatabase(this);
    }
}
