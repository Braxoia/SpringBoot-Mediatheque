package fr.ibralogan.mediatheque.persistance.dtos;

/**
 * Classe me permettant de représenter le payload de ma requête me permettant de créer un nouveau Document
 * Nous avons été forcé de le faire car nous avions des erreurs quant à la récupération de l'emprunteur (problème de désérialisation)
 */
public class CreateDocumentDTO {
    private String titre;
    private int typeDocument;
    private int reserveur;

    public CreateDocumentDTO(){
    }

    public CreateDocumentDTO(String titre, int typeDocument, int reserveur) {
        this.titre = titre;
        this.typeDocument = typeDocument;
        this.reserveur = reserveur;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getTypeDocument() {
        return typeDocument;
    }

    public void setTypeDocument(int typeDocument) {
        this.typeDocument = typeDocument;
    }

    public int getReserveur() {
        return reserveur;
    }

    public void setReserveur(int reserveur) {
        this.reserveur = reserveur;
    }
}
