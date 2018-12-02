package sample;

import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String name,password;
    private int coins;
    ArrayList<Integer> scores;
    ArrayList<Integer> unlockedThemes, unlockedSkins;
    private String[][] topTenScores;
    private int ttsLength;

    User(String n, String p){
        name = n;
        password = p;
        coins = 50000;
        scores = new ArrayList<>();
        unlockedSkins = new ArrayList<>();
        unlockedThemes = new ArrayList<>();
        topTenScores = new String[10][3];
        ttsLength = 0;
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

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Integer> getScores() {
        return scores;
    }

    public int getCoins() {
        return coins;
    }

    public void addCoins(int coins) {
        this.coins += coins;
    }

    public ArrayList<Integer> getUnlockedSkins() {
        return unlockedSkins;
    }

    public ArrayList<Integer> getUnlockedThemes() {
        return unlockedThemes;
    }

    public void addSkins(int c){
        coins -= 500;
        unlockedSkins.add(c);
    }

    public void addThemes(int c){
        unlockedThemes.add(c);
    }

    public void addScores(int i){
        scores.add(i);
    }
}
