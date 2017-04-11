package SunWongParis13.Piece;

public class PieceFou extends Piece{
	
	public PieceFou(boolean couleur, int ligne, int colonne){
		super(Piece.FOU, couleur, ligne, colonne);
	}

	public boolean mouvementFou(int l, int c){
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
		return false;
	}
	
	public static void main(String [] args){
		PieceFou f = new PieceFou(true, 5, 3);
		if(f.deplacementValide(7,1))
			System.out.println("bingo");
		else
			System.out.println("bango");
	}
	
}
