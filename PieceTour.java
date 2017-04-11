package SunWongParis13.Piece;

public class PieceTour extends Piece{
	
	public PieceTour(boolean couleur, int ligne, int colonne){
		super(Piece.TOUR, couleur, ligne, colonne);
	}
	
	public boolean mouvementTour(int l, int c){
		if(deplacementValide(l,c) && mouvementValide(l,c) && !(mouvementIdentique(l,c)))
			return true;
		return false;
	}
	
	public boolean deplacementValide(int l, int c){
		return (this.getLigne()>=0 && this.getLigne()<=7 && this.getColonne()==c) ||
				(this.getLigne()==l && this.getColonne()>=0 && this.getColonne()<=7);
	}

}
