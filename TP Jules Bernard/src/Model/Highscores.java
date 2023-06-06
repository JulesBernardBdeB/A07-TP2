package Model;

import java.io.*;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Classe qui représente un temps record et permet de le sauvegarder dans highscores.txt
 */

public class Highscores implements Serializable{
    private String playerName;
    private int elapsedTime;

    public Highscores(String playerName, int elapsedTime) {
        this.playerName = playerName;
        this.elapsedTime = elapsedTime;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }
    public void saveHighScore(Highscores highscore){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("highscores.txt"))) {
            oos.writeObject(highscore);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Highscores> loadHighScores(String filename){
        ArrayList<Highscores> highScores = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            highScores = (ArrayList<Highscores>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return highScores;
    }

}
