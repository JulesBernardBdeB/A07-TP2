package Vue;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.net.URL;

public class Music {

    private static final File themeSong = new File("src/Vue/sounds/theme_song.wav");
    private static final File clickSound = new File("src/Vue/sounds/clicksound.wav");
    private static final File winSound = new File("src/Vue/sounds/win.wav");
    public static void playThemeSong(){

        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(themeSong);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch(Exception musicError){
            musicError.printStackTrace();
        }
    }

    public static void playClickSound(){

        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(clickSound);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
        catch(Exception musicError){
            musicError.printStackTrace();
        }
    }
    public static void playWinSound(){

        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(winSound);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
        catch(Exception musicError){
            musicError.printStackTrace();
        }
    }

}
