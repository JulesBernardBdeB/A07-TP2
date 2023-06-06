package Vue;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * un simple timer dont l'affichage est 00:00:00
 */
public class Stopwatch extends JLabel{

    JLabel timeLabel = new JLabel();
    int elapsedTime = 0;
    int seconds =0;
    int minutes =0;
    int hours =0;
    String seconds_string = String.format("%02d", seconds);
    String minutes_string = String.format("%02d", minutes);
    String hours_string = String.format("%02d", hours);

    Timer timer = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {

            elapsedTime=elapsedTime+1000;
            hours = (elapsedTime/3600000);
            minutes = (elapsedTime/60000) % 60;
            seconds = (elapsedTime/1000) % 60;
            seconds_string = String.format("%02d", seconds);
            minutes_string = String.format("%02d", minutes);
            hours_string = String.format("%02d", hours);
            timeLabel.setText(hours_string+":"+minutes_string+":"+seconds_string);
        }
    });
    public Stopwatch(JPanel jPanel){
        timeLabel.setText(hours_string+":"+minutes_string+":"+seconds_string);
        timeLabel.setBounds(50,50,100,50);
        timeLabel.setFont(new Font("Verdana",Font.PLAIN,15));
        timeLabel.setBorder(BorderFactory.createBevelBorder(1));
        timeLabel.setOpaque(true);
        timeLabel.setHorizontalAlignment(JTextField.CENTER);
        jPanel.add(timeLabel);
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void reset() {
        timer.stop();
        elapsedTime=0;
        seconds =0;
        minutes=0;
        hours=0;
        seconds_string = String.format("%02d", seconds);
        minutes_string = String.format("%02d", minutes);
        hours_string = String.format("%02d", hours);       timeLabel.setText(hours_string+":"+minutes_string+":"+seconds_string);
    }
    public String getTimeString(){
        return hours_string+":"+minutes_string+":"+seconds_string;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }
}
