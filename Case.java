package SunWongParis13.Echiquier;


import SunWongParis13.Piece.*;

public class Case {
	private Piece piece;
	private int ligne;
	private int colonne;
	private boolean caseRemplie;
	private boolean caseAutorise;

	public Case(int l, int c){
		this.setLigne(l);
		this.setColonne(c);
		this.setCaseRemplie(false);
		this.setPiece(null);
		this.setCaseAutorise(false);
	}
	
	public Case(Piece p, int l, int c/*, int couleur*/){
		this.setLigne(l);
		this.setColonne(c);
		this.setCaseRemplie(true);
		this.setPiece(p);
		this.setCaseAutorise(false);
	}
	
	public void libererCase(){
		this.setPiece(null);
		this.setCaseRemplie(false);
		this.setCaseAutorise(false);
	}
	
	public Piece getPiece() {
		return this.piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	public int getLigne() {
		return this.ligne;
	}
	/**
	 * Ligne de la case
	 * @param ligne
	 */
	public void setLigne(int ligne) {
		this.ligne = ligne;
	}

	public int getColonne() {
		return this.colonne;
	}

	/**
	 * Colonne de la case
	 * @param colonne
	 */
	public void setColonne(int colonne) {
		this.colonne = colonne;
	}

	public boolean getCaseRemplie() {
		return this.caseRemplie;
	}

	public void setCaseRemplie(boolean b) {
		this.caseRemplie = b;
	}
	
	public boolean getCaseAutorise() {
		return caseAutorise;
	}

	public void setCaseAutorise(boolean caseAutorise) {
		this.caseAutorise = caseAutorise;
	}
	
	public String toString(){
		if(this.piece instanceof Piece) //Object
			return piece.toString();
		return "  ";
	}
}
