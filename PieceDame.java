package SunWongParis13.Piece;

public class PieceDame extends Piece{
	
	public PieceDame(boolean couleur, int ligne, int colonne){
		super(Piece.DAME, couleur, ligne, colonne);
	}

	public boolean mouvementDame(int l, int c){
		if(deplacementValide(l,c) && mouvementValide(l,c) && !(mouvementIdentique(l,c)))
			return true;
		return false;
	}
	
	
	public boolean deplacementValide(int l, int c){
		if(this.getLigne()>l && this.getColonne()>c){ //hg
			return (this.getLigne() - l) == (this.getColonne() - c);
		}
		else if(this.getLigne()>l && this.getColonne()<c){ //hd
			return (this.getLigne() - l) == (c - this.getColonne());
		}
		else if(this.getLigne()<l && this.getColonne()>c){ //bg
			return (l - this.getLigne()) == (this.getColonne() - c);
		}
		else if(this.getLigne()<l && this.getColonne()<c){ //bd
			return (l - this.getLigne()) == (c - this.getColonne());
		}
		return (this.getLigne()>=0 && this.getLigne()<=7 && this.getColonne()==c) ||
				(this.getLigne()==l && this.getColonne()>=0 && this.getColonne()<=7);
	}
	
	public static void main(String [] args){
		PieceDame f = new PieceDame(true, 0, 0);
		if(f.deplacementValide(7,7))
			System.out.println("bingo");
		else
			System.out.println("bango");
	}
}
