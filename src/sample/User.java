package sample;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * User class.
 */
public class User implements Serializable {
    /**
     * username and password for user
     */
    private String name,password;
    /**
     * number of coins user has
     */
    private int coins;
    /**
     * keeps track of skins user owns
     */
    ArrayList<Integer> unlockedSkins;
    /**
     * list of top ten scores of user
     */
    private String[][] topTenScores;
    /**
     * number of top ten scores if less than 10
     */
    private int ttsLength;

    User(String n, String p){
        name = n;
        password = p;
        coins = 50000;
        unlockedSkins = new ArrayList<>();
        topTenScores = new String[10][3];
        ttsLength = 0;
    }

    /**
     *
     * @param name
     * @param score
     * @param date
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
        addCoins(Integer.parseInt(score));
        int[] numero = new int[ttsLength];
        for (int i = 0; i < ttsLength; i++) numero[i] = Integer.parseInt(topTenScores[i][1]);
        quicksort(numero,0,ttsLength - 1,topTenScores);
    }

    /**
     * returns list of top ten scores of user
     * @return
     */
    public String[][] getTopTenScores() {
        return topTenScores;
    }

    /**
     * returns number of scores if less than 10
     * @return
     */
    public int getTtsLength() {
        return ttsLength;
    }

    /**
     * used to partition array for quicksort
     * @param arr array to partition
     * @param l lower index
     * @param h higher index
     * @param orig original array
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
     * Used to sort list of top ten scores using quicksort
     * @param arr
     * @param l
     * @param h
     * @param orig
     */
    void quicksort(int arr[], int l, int h, String orig[][]){
        if(l < h) {
            int p = partirion(arr,l,h,orig);
            quicksort(arr,l,p - 1,orig);
            quicksort(arr,p + 1, h,orig);
        }
    }

    /**
     * getter function for password
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * getter function for name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * getter function for coins
     * @return
     */
    public int getCoins() {
        return coins;
    }

    /**
     * mutator function for coins
     * @param coins
     */
    public void addCoins(int coins) {
        this.coins += coins;
    }

    /**
     * getting function for unlockedSkins
     * @return
     */
    public ArrayList<Integer> getUnlockedSkins() {
        return unlockedSkins;
    }

    /**
     * used to add skins to user
     * @param c
     */
    public void addSkins(int c){
        coins -= 500;
        unlockedSkins.add(c);
    }

}
