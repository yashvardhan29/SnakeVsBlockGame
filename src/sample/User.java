package sample;

import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String name,password;
    private int coins;
    ArrayList<Integer> scores;
    ArrayList<Color> unlockedThemes, unlockedSkins;

    User(String n, String p){
        name = n;
        password = p;
        coins = 0;
        scores = new ArrayList<>();
        unlockedSkins = new ArrayList<>();
        unlockedThemes = new ArrayList<>();
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

    public ArrayList<Color> getUnlockedSkins() {
        return unlockedSkins;
    }

    public ArrayList<Color> getUnlockedThemes() {
        return unlockedThemes;
    }

    public void addSkins(Color c){
        unlockedSkins.add(c);
    }

    public void addThemes(Color c){
        unlockedThemes.add(c);
    }

    public void addScores(int i){
        scores.add(i);
    }
}
