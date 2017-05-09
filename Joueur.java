package SunWongParis13.Echiquier;

public class Joueur {
	private String nom;
	private boolean couleur;
	
	public Joueur(String n, boolean c){
		this.nom = n;
		this.couleur = c;
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public boolean getCouleur() {
		return couleur;
	}

	public void setCouleur(boolean couleur) {
		this.couleur = couleur;
	}
	
}
