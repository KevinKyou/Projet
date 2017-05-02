package SunWongParis13.Echiquier;

import SunWongParis13.Piece.Piece;

public class Case {
	private Piece piece;
	private int ligne;
	private int colonne;
	private boolean caseRemplie;
	private boolean accessible;
	
	public Case(int l, int c){
		this.setPiece(null);
		this.setLigne(l);
		this.setColonne(c);
		this.setCaseRemplie(false);
		this.setAccessible(false);
	}
	
	/**
	 * 
	 * Constructeur champ à champ
	 * @param p : Piece à mettre dans la case
	 * @param l : Ligne de la case
	 * @param c : Colonne de la case
	 */
	public Case(Piece p, int l, int c){
		this.setPiece(p);
		this.setLigne(l);
		this.setColonne(c);
		this.setCaseRemplie(true);
		this.setAccessible(false);
	}
	
	/**
	 * 
	 * @return Type de la piece
	 */
	public Piece getPiece() {
		return this.piece;
	}
	
	/**
	 * 
	 * @return Ligne de la case
	 */
	public int getLigne() {
		return this.ligne;
	}
	
	/**
	 * 
	 * @return Colonne de la case
	 */
	public int getColonne() {
		return this.colonne;
	}
	
	/**
	 * 
	 * @return Verifie si la case est occupée
	 */
	public boolean getCaseRemplie() {
		return this.caseRemplie;
	}
	
	/**
	 * 
	 * @return Verifie une piece peut arriver dans cette case
	 */
	public boolean getAccessible() {
		return accessible;
	}
	
	/**
	 * 
	 * @param piece : Type de la piece à mettre dans la case
	 */
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	/**
	 * 
	 * @param ligne : Ligne de la case
	 */
	public void setLigne(int ligne) {
		this.ligne = ligne;
	}
	
	/**
	 * 
	 * @param colonne : Colonne de la case
	 */
	public void setColonne(int colonne) {
		this.colonne = colonne;
	}
	
	/**
	 * 
	 * @param remplir : Met ou enleve une piece de la case
	 */
	public void setCaseRemplie(boolean remplir) {
		this.caseRemplie = remplir;
	}
	
	/**
	 * 
	 * @param accessible : Les cases où peuvent aller la piece
	 */
	public void setAccessible(boolean accessible) {
		this.accessible = accessible;
	}
	
	/**
	 * Libere la case, apres le deplacement d'une piece
	 */
	public void libererCase(){
		this.setPiece(null);
		this.setCaseRemplie(false);
	}
	
	/**
	 * On affiche le nom de la piece + la couleur de la piece
	 */
	public String toString(){
		if(this.piece instanceof Piece) //Object
			return piece.toString();
		return "  ";
	}
}
