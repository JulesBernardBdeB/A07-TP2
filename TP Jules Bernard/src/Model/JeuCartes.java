package Model;

public interface JeuCartes {
    void initialiser();
    void nouvellePartie();
    void retournerCarte(Cartes carte);
    boolean estTermine();
}
