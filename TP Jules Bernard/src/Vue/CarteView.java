package Vue;

import Model.Cartes;
import Model.Highscores;
import Model.JeuCartes;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static Vue.Music.playClickSound;
import static Vue.Music.playWinSound;

/**
 * La classe CarteView contient le swing UI et implémente l'interface JeuCartes. Les 20 cartes sont enregistrées dans
 * le tableau "cartes". Lorsque deux cartes sont clickées par l'utilisateur, elles sont mises dans le tableau
 * "carteToCompare". Lorsqu'une paire de carte est trouvée, L'Integer "foundPairs" augmente et la partie se termine
 * quand il atteint 10. Quand la partie est terminée, un JOptionPane apparait et l'utilisateur peut rejouer ou quitter.
 */

public class CarteView extends JFrame implements JeuCartes {
    private final JFrame frame = new JFrame("Jeux de mémoire");
    private JPanel panel = new JPanel();
    private JPanel panelTimer = new JPanel();
    private final Cartes[] cartes = new Cartes[20];
    private final Cartes[] carteToCompare = new Cartes[2];
    private Integer foundPairs;
    private final Object[] options ={"Rejouer","Quitter"};
    Stopwatch stopwatch = new Stopwatch(panelTimer);
    ArrayList<Highscores> highscoresArrayList = new ArrayList<>();


    /**
     * Construit notre frame pour le jeu et appelle la methode qui initialise le jeu
     */
    public CarteView() {
        frame.setSize(1300, 1050);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(createMenu());
        panelTimer.setPreferredSize(new Dimension(20,30));
        frame.add(panelTimer, BorderLayout.PAGE_START);
        panelTimer.setVisible(true);
        initialiser();
        Music.playThemeSong();
    }
    private JMenuBar createMenu(){
        JMenuBar menuBar = new JMenuBar();

        JMenu menuJeu = new JMenu("Jeu");
        JMenuItem itemNouveau = new JMenuItem("Redémarrer");
        itemNouveau.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        itemNouveau.addActionListener(this::newGameListener);
        menuJeu.add(itemNouveau);

        JMenuItem newItemQuit = new JMenuItem("Quitter");
        newItemQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK));
        newItemQuit.addActionListener(this::quitListener);
        menuJeu.add(newItemQuit);
        menuBar.add(menuJeu);

        JMenu menuAide = new JMenu("Aide");
        menuAide.setMnemonic('A');
        JMenuItem itemInstruction = new JMenuItem("Instructions");
        itemInstruction.setMnemonic('I');
        itemInstruction.addActionListener(this::instructionListener);
        menuAide.add(itemInstruction);

        JMenuItem itemAPropos = new JMenuItem("À propos");
        itemAPropos.addActionListener(this::instructionAPropos);
        menuAide.add(itemAPropos);
        menuBar.add(menuAide);

        JMenu menuHighscores = new JMenu("Temps record");
        menuHighscores.addActionListener(this::highscoresListener);
        menuBar.add(menuHighscores);

        return menuBar;
    }

    /**
     * Ce MessageDialog ne s'affiche pas et malheureusement je manque de temps pour régler le problème.
     * @param actionEvent
     */
    private void highscoresListener(ActionEvent actionEvent) {
        Collections.sort(highscoresArrayList, Comparator.comparingInt(Highscores::getElapsedTime));
        StringBuilder highScoresText = new StringBuilder();
        for (int i = 0; i < highscoresArrayList.size(); i++) {
            Highscores score = highscoresArrayList.get(i);
            highScoresText.append("Rank ").append(i + 1).append(": ")
                    .append(score.getPlayerName()).append(" - ")
                    .append(score.getElapsedTime());
        }
        JOptionPane.showMessageDialog(null,highScoresText.toString(),"Temps records",JOptionPane.INFORMATION_MESSAGE);
    }

    private void instructionAPropos(ActionEvent actionEvent) {
        String message = "Réalisé par Jules Bernard";
        JOptionPane.showMessageDialog(this,message,"Instructions",JOptionPane.INFORMATION_MESSAGE);
    }

    private void instructionListener(ActionEvent actionEvent) {
        String message = "Vous devez trouver les dix paires correspondantes."+"" +
                "\nSimplement clicker sur les cartes pour les retournées.";
        JOptionPane.showMessageDialog(this,message,"Instructions",JOptionPane.INFORMATION_MESSAGE);
    }

    private void quitListener(ActionEvent actionEvent) {
        System.exit(0);
    }

    private void newGameListener(ActionEvent actionEvent) {
        nouvellePartie();
    }

    /**
     * Methode qui initialise le jeu. Initialise les paires trouvées à 0 et instance un nouveau panel avec 20 cartes.
     */
    @Override
    public void initialiser() {
        foundPairs = 0;
        panel.setLayout(new GridLayout(4, 5));
        for (int i = 0; i < 20; i++) {
            cartes[i] = new Cartes();
            cartes[i].setTurned(false);
            panel.add(cartes[i]);
            int finalI = i;
            cartes[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    {
                        playClickSound();
                        retournerCarte(cartes[finalI]);
                    }
                }
            });
        }
        frame.add(panel,BorderLayout.CENTER);
        stopwatch.reset();
        stopwatch.start();
        frame.setVisible(true);
        //panel.setVisible(true);
    }

    /**
     * methode qui supprime les cartes du tableau et reinitialise le jeu.
     */
    @Override
    public void nouvellePartie() {
        for (int i = 0; i < 20; i++) {
            cartes[i] = null;
        }
        panel.removeAll();
        initialiser();

    }

    /**
     * Retourne la carte et la met dans le tableau pour comparer les cartes. Quand le tableau a les deux cartes, elles
     * sont comparées avec la méthode ".equals".
     */
    @Override
    public void retournerCarte(Cartes carte) {
        carte.setTurned(true);
        if (carteToCompare[0] == null) {
            carteToCompare[0] = carte;
        } else if (carteToCompare[1] == null) {
            carteToCompare[1] = carte;
            comparerCarte(carteToCompare[0],carteToCompare[1]);
        }
    }
    /**
     * Compare les deux cartes. Si elles ont les mêmes Id, le nombre de paires trouvées augmente jusqu'à ce que toutes
     * les paires soient trouvées. Lorsqu'elles sont toutes trouvées, un JOptionPane s'ouvre et l'utilisateur peut
     * recommencer une partie ou quitter. Si les cartes ne sont pas les mêmes, après 0.5 seconde, elles sont retournées
     * et enlevées du tableau qui compare les cartes.
     */
    public void comparerCarte(Cartes carte1, Cartes carte2) {
        if (carte1.equals(carte2)){
            carte1.setFound(true);
            carte2.setFound(true);
            carteToCompare[0] = null;
            carteToCompare[1] = null;
            foundPairs++;
            if (estTermine()){
               messageToWinner();
            }
        }
        else { Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carte1.setTurned(false);
                carte2.setTurned(false);
                carteToCompare[0] = null;
                carteToCompare[1] = null;
            }
        });
            timer.setRepeats(false);
            timer.start();
        }
    }

    /**
     * @return retourne vrai quand les paires trouvées sont égales à 10.
     */
    @Override
    public boolean estTermine () {
        return foundPairs == 10;
    }

    /**
     * Cette méthode affiche un message lorsque la partie se termine. Malheureusement, mon JDialog personnalisé ne
     * s'affiche pas et je manque de temps pour régler le problème.
     */
    public void messageToWinner(){
        stopwatch.stop();
        playWinSound();
       /* if(highscoresArrayList.isEmpty()){
            TextInputDialog textInputDialog = new TextInputDialog(frame);
            Highscores newHighScore = new Highscores(textInputDialog.getText(),stopwatch.getElapsedTime());
            highscoresArrayList.add(newHighScore);
            newHighScore.saveHighScore(newHighScore);
        } else if (stopwatch.getElapsedTime() < highscoresArrayList.get(0).getElapsedTime()) {
            TextInputDialog textInputDialog = new TextInputDialog(frame);
            Highscores newHighScore = new Highscores(textInputDialog.getText(),stopwatch.getElapsedTime());
            highscoresArrayList.add(newHighScore);
            newHighScore.saveHighScore(newHighScore);
        }
        else { */
            int choix = JOptionPane.showOptionDialog(this, "Bravo! Vous avez gagné!",
                    "Félicitation!", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options,
                    options[0]);
            if (choix == JOptionPane.YES_OPTION) {
                nouvellePartie();
            } else if (choix == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
        //}
    }
}

