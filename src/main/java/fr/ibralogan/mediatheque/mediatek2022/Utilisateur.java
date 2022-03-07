package fr.ibralogan.mediatheque.mediatek2022;

public interface Utilisateur {
	//changement du nom de la méthode pour faciliter SpringBoot à nous renvoyer le username en JSON par introspection
	String getUsername();
	boolean isBibliothecaire();
	/*
	 * data() permet de récupérer les informations qui seraient nécessaires à des
	 * controles sur l'abonné (age, abonnement désactivé,...)
	 */	
	Object[] data();
}
