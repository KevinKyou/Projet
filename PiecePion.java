package SunWongParis13.Piece;

public class PiecePion extends Piece{

	public PiecePion(boolean couleur, int ligne, int colonne){
		super(Piece.PION, couleur, ligne, colonne);
	}

	public boolean mouvementPion(int l, int c){
		if(mouvementValide(l,c) && !(mouvementIdentique(l,c)))
			return true;
		return false;
	}

	public boolean deplacementValideBlanc(int l, int c){
		if(this.getMouvement() == false)
			return (this.getLigne()-2==l || this.getLigne()-1==l) && this.getColonne()==c;
		else
			return this.getLigne()-1==l && this.getColonne()==c;
	}
	
	public boolean deplacementValideVersusBlanc(int l, int c){
		if(this.getMouvement() == false)
			return (this.getLigne()-2==l || this.getLigne()-1==l) && (this.getColonne()==c || this.getColonne()-1==c || this.getColonne()+1==c);
		else
			return this.getLigne()-1==l && (this.getColonne()==c || this.getColonne()-1==c || this.getColonne()+1==c) ;
	}
	
	public boolean deplacementValideNoir(int l, int c){
		if(this.getMouvement() == false)
			return (this.getLigne()+2==l || this.getLigne()+1==l) && this.getColonne()==c;
		else
			return this.getLigne()+1==l && this.getColonne()==c;
	}
	
	public boolean deplacementValideVersusNoir(int l, int c){
		if(this.getMouvement() == false)
			return (this.getLigne()+2==l || this.getLigne()+1==l) && (this.getColonne()==c || this.getColonne()-1==c || this.getColonne()+1==c);
		else
			return this.getLigne()+1==l && (this.getColonne()==c || this.getColonne()-1==c || this.getColonne()+1==c) ;
	}
}
