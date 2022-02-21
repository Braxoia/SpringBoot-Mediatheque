package fr.ibralogan.mediatheque.mediatek2022;

import java.util.List;

public interface PersistentMediatheque {
	List<Document> tousLesDocumentsDisponibles();
	Document getDocument(int numDocument);
	Utilisateur getUser(String login, String password);
	void ajoutDocument(int type, Object... args );

}
