package fr.ibralogan.mediatheque.mediatek2022;
import java.util.List;

/**
 cette classe repr�sente la mediatheque du point de vue du domaine
 cette classe est un singleton
 elle a un attribut qui fait le lien avec les donn�es persistantes

 LES SERVLETS DOIVENT S'ADRESSER A CETTE CLASSE EXCLUSIVEMENT
 POUR INTERROGER LES DONNEES

 beaucoup des m�thodes de Mediatheque sont d�l�gu�es � l'attribut de persistance
 qui devra r�percuter ces op�rations sur les donn�es persistantes

 */
/**
 * @author jfbrette
 *
 */
public class Mediatheque {
// Jean-Fran�ois Brette 01/01/2022

    // singleton standard ========================
    static {
        instance = new Mediatheque();
    }
    private static Mediatheque instance;
    public static Mediatheque getInstance() {
        return instance;
    }
    private Mediatheque () {}
// fin - singleton standard ==================

    // lien avec les donn�es persistantes +++++++++++++++
    private PersistentMediatheque data;

    public void setData(PersistentMediatheque data) {
        if (this.data == null) this.data = data;
    }
// fin - lien avec les donn�es persistantes +++++++++

// ********** action sur le document ***********************

    // enregistre l'emprunt par l'abonn� a du document d)

    public void emprunt (Document d, Utilisateur a) throws Exception {
        d.emprunt(a);
    }

    // enregistre la r�servation par l'abonn� a du document d)



    //enregistre le retour du document d)

    public void retour (Document d, Utilisateur a) throws Exception {
        d.retour();
    }

// *********************** d�l�gation **********************

    // renvoie la liste de tous les documents de la biblioth�que

    public List<Document> tousLesDocumentsDisponibles() {
        return data.tousLesDocumentsDisponibles();
    }

    // renvoie le user de login et passwd
    // si pas trouv�, renvoie null

    public Utilisateur getUser (String login, String password) {
        return data.getUser(login, password);
    }

    // renvoie le document de num�ro numDocument
    // si pas trouv�, renvoie null

    public Document getDocument(int numDocument) {
        return data.getDocument(numDocument);
    }

    // ajoute un nouveau document

    public void ajoutDocument(int type, Object... args ) {
        data.ajoutDocument(type, args);
    };

}
