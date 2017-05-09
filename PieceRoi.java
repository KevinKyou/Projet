package SunWongParis13.Piece;


public class PieceRoi extends Piece{

	public PieceRoi(boolean couleur, int ligne, int colonne){
		super(Piece.ROI, couleur, ligne, colonne);
	}

	public boolean mouvementRoi(int l, int c){
		if(deplacementValide(l, c) && mouvementValide(l,c) && !(mouvementIdentique(l,c))) //une case autour de lui, dep case 1 a 6, !faux si les piece n'ont pas bouge
			return true;
		return false;
	}
	
	public boolean deplacementValide(int l, int c){
		if(this.getMouvement() == false)
			return (this.getLigne()-1==l || this.getLigne()+1==l) && (this.getColonne()-1==c || this.getColonne()==c || this.getColonne()+1==c);
		else
			return (this.getLigne()-1==l || this.getLigne()+1==l) && this.getColonne()==c;
	}
}
