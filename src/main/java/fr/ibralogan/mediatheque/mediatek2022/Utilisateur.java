package fr.ibralogan.mediatheque.mediatek2022;

public interface Utilisateur {
	String name();
	boolean isBibliothecaire();
	/*
	 * data() permet de récupérer les informations qui seraient nécessaires à des
	 * controles sur l'abonné (age, abonnement désactivé,...)
	 */	
	Object[] data();
}
