package SunWongParis13.Piece;

public abstract class Piece { //abstraite ? 
	
	public static final String BLANC = "b";
	public static final String NOIR = "n";
	
	public static final String ROI = "R";
	public static final String DAME = "D";
	public static final String TOUR = "T";
	public static final String FOU = "F";
	public static final String CAVALIER = "C";
	public static final String PION = "P";
	
	private String nom;
	private boolean couleurPiece;
	private int ligne;
	private int colonne;
	private boolean mouvement;
	
	public Piece(String p, boolean couleur, int l, int c)
	{
		this.setNom(p);
		this.setCouleurPiece(couleur);
		this.setLigne(l);
		this.setColonne(c);
		this.setMouvement(false);
	}
	
	//on regarde si le mouvement est correct
	public boolean mouvementValide(int l, int c){
		return (l>=0 && l<8 && c>=0 && c<8);
	}
	
	public void mouvementPiece(int l, int c){
		if(mouvementValide(l,c) && mouvementIdentique(l,c)){
			this.setLigne(l);
			this.setColonne(c);
			this.setMouvement(true);
		}
	}
	
	public boolean mouvementIdentique(int l, int c){
		return this.getLigne()==l && this.getColonne()==c;
	}
	
	//protected abstract void mouvement(int l, int c);
	
	//retourne le type de la piece
	public String getNom() {
		return this.nom;
	}
	
	public void setNom(String piece) {
		this.nom = piece;
	}
	
	public boolean getCouleurPiece() {
		return this.couleurPiece;
	}

	public void setCouleurPiece(boolean b) {
		this.couleurPiece = b;
	}
	
	public int getLigne(){
		return this.ligne;
	}

	public void setLigne(int ligne){
		this.ligne = ligne;
	}

	public int getColonne(){
		return this.colonne;
	}

	public void setColonne(int colonne){
		this.colonne = colonne;
	}
	
	//on regarde si la piece s'est deplace
	public boolean getMouvement(){
		return this.mouvement;
	}
	
	//on utilise la fonction apres un mouvement
	public void setMouvement(boolean b){
		this.mouvement = b;
	}
	
	public String toString(){
		if(this.couleurPiece == true)
			return this.getNom() + BLANC;
		return this.getNom() + NOIR;
	}
}
