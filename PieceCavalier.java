package SunWongParis13.Piece;

public class PieceCavalier extends Piece{
	 
	public PieceCavalier(boolean couleur, int ligne, int colonne){
		super(Piece.CAVALIER, couleur, ligne, colonne);
	}
	
	public boolean mouvementCavalier(int l, int c){
		if(deplacementValide(l,c) && mouvementValide(l,c) && !(mouvementIdentique(l,c)))
			return true;
		return false;
	}
	
	public boolean deplacementValide(int l, int c){
		return (this.getLigne()-2==l && this.getColonne()+1==c) || 
				(this.getLigne()-1==l && this.getColonne()+2==c) || 
				(this.getLigne()+1==l && this.getColonne()+2==c) ||
				(this.getLigne()+2==l && this.getColonne()+1==c) ||
				(this.getLigne()+2==l && this.getColonne()-1==c) ||
				(this.getLigne()+1==l && this.getColonne()-2==c) ||
				(this.getLigne()-1==l && this.getColonne()-2==c) ||
				(this.getLigne()-2==l && this.getColonne()-1==c)				
				;
	}
}

