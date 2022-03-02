package fr.ibralogan.mediatheque.mediatek2022;

/**
 * @author jfbrette
 *
 */
public interface Document {
    boolean disponible();
    void emprunt(Utilisateur u) throws Exception;
    void retour();
    // l'affichage d'un document utilisera toString()
}
