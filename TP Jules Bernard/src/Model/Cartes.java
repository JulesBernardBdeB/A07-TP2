package Model;
import javax.swing.*;
import java.util.*;

/**
 * Cette classe représente une carte dans le jeu. Les cartes sont des boutons avec un Id, une image et deux boolean.
 * Les Id sont distribués aléatoirement et les boolean sont faux par défaut.
 */
public class Cartes extends JButton {

    private static final ArrayList<Integer> id = new ArrayList<>();
    private final Integer idGenerated;
    private final ImageIcon[] images;
    private final ImageIcon defaultImage =  new ImageIcon(getClass().getResource("/Vue/images/default.png"));
    private boolean turned;
    private boolean found;

    public Cartes() {
        this.idGenerated = Cartes.getNextId();
        this.turned = false;
        this.found = false;
        this.images = new ImageIcon[]{
                new ImageIcon(getClass().getResource("/Vue/images/8ball2.png")),
                new ImageIcon(getClass().getResource("/Vue/images/badger2.png")),
                new ImageIcon(getClass().getResource("/Vue/images/bomb2.png")),
                new ImageIcon(getClass().getResource("/Vue/images/dices2.png")),
                new ImageIcon(getClass().getResource("/Vue/images/glasses2.png")),
                new ImageIcon(getClass().getResource("/Vue/images/duck2.png")),
                new ImageIcon(getClass().getResource("/Vue/images/owl2.png")),
                new ImageIcon(getClass().getResource("/Vue/images/police2.png")),
                new ImageIcon(getClass().getResource("/Vue/images/skateboard2.png")),
                new ImageIcon(getClass().getResource("/Vue/images/wolf2.png")),
                new ImageIcon(getClass().getResource("/Vue/images/8ball2.png")),
                new ImageIcon(getClass().getResource("/Vue/images/badger2.png")),
                new ImageIcon(getClass().getResource("/Vue/images/bomb2.png")),
                new ImageIcon(getClass().getResource("/Vue/images/dices2.png")),
                new ImageIcon(getClass().getResource("/Vue/images/glasses2.png")),
                new ImageIcon(getClass().getResource("/Vue/images/duck2.png")),
                new ImageIcon(getClass().getResource("/Vue/images/owl2.png")),
                new ImageIcon(getClass().getResource("/Vue/images/police2.png")),
                new ImageIcon(getClass().getResource("/Vue/images/skateboard2.png")),
                new ImageIcon(getClass().getResource("/Vue/images/wolf2.png")),
        };
    }

    /**
     * Cette methode génère dix paires de int, puis ils sont mélangés pour qu'ils soient dans un ordre aléatoire
     * différent à chaque fois qu'ils sont générés.
     */
    public static void GenerateIds() {
        for (int i = 0; i < 10; i++) {
            id.add(i);
            id.add(i);
        }
        Collections.shuffle(id);
    }

    /**
     * Cette methode est utilisé lorsque le constructeur construit une carte. La méthode s'assure que les Id sont
     * générés une seule fois, puis, donne l'id à l'index 0 à la carte construite. L'index 0 est supprimé par la suite.
     */
    public static Integer getNextId() {
        if(id.isEmpty()){
            GenerateIds();
        }
        int nextId = id.get(0);
        id.remove(0);
        return nextId;
    }

    /**
     * Retourne l'Id de la carte
     * @return id
     */
    public Integer getId() {
        return this.idGenerated;
    }

    /**
     * Change l'état de la carte. Quand le boolean est vrai, la carte est retournée et désactivée pour qu'elle ne
     * puisse pas être clickée à nouveau.
     * @param turned
     */
    public void setTurned(boolean turned) {
        this.turned = turned;
        if(this.turned){
            this.setIcon(images[this.getId()]);
            this.setEnabled(false);
        }
        if(!this.turned){
            this.setIcon(defaultImage);
            this.setEnabled(true);
        }
    }

    /**
     * Change l'état de la carte et désactive le bouton. Quand le boolean est vrai, la carte est désactivée pour qu'elle
     * ne puisse pas être clickée à nouveau.
     * @param found
     */
    public void setFound(boolean found) {
        this.found = found;
        this.setEnabled(false);
    }

    /**
     * compare deux id
     * @param o
     * @return retourne vrai si ce sont les mêmes Id
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cartes cartes = (Cartes) o;
        return Objects.equals(idGenerated, cartes.idGenerated);
    }
}
