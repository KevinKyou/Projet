package SunWongParis13.Echiquier;

import SunWongParis13.Piece.PieceCavalier;
import SunWongParis13.Piece.PieceDame;
import SunWongParis13.Piece.PieceFou;
import SunWongParis13.Piece.PiecePion;
import SunWongParis13.Piece.PieceRoi;
import SunWongParis13.Piece.PieceTour;

public class Echiquier {

	private Case[][] grille;
	
	public Echiquier(){
		this.grille = new Case[8][8];
		
		for(int l=0; l<8; l++)
			for(int c=0; c<8; c++)
				this.grille[l][c] = new Case(l,c);
		
	}
	
	public void initialiseEchiquier(){
		
		for(int i=0; i<8; i++){			
			if(i==0 || i==7){
				this.setGrille(new Case(new PieceTour(false, 0, i), 0, i));
				this.setGrille(new Case(new PieceTour(true, 7, i), 7, i));
			}
			else if(i==1 || i==6){
				this.setGrille(new Case(new PieceCavalier(false, 0, i), 0, i));
				this.setGrille(new Case(new PieceCavalier(true, 7, i), 7, i));
			}
			else if(i==2 || i==5){
				this.setGrille(new Case(new PieceFou(false, 0, i), 0, i));
				this.setGrille(new Case(new PieceFou(true, 7, i), 7, i));
			}
			else if(i==3){
				this.setGrille(new Case(new PieceDame(false, 0, i), 0, i));
				this.setGrille(new Case(new PieceDame(true, 7, i), 7, i));
			}
			else{
				this.setGrille(new Case(new PieceRoi(false, 0, i), 0, i));
				this.setGrille(new Case(new PieceRoi(true, 7, i), 7, i));
			}
			//this.grille[1][i] = new Case(new PiecePion(false, 1, i), 1, i);
			//this.grille[6][i] = new Case(new PiecePion(true, 6, i), 6, i);
		}
			//this.setGrille(new Case(new PieceCavalier(false, 5, 3), 5, 3));
		
		this.setGrille(new Case(new PieceTour(false, 2, 4), 2, 4));
		this.setGrille(new Case(new PieceTour(true, 5, 1), 5, 1));
		
	}
	
	public Case getGrille(int l, int c) {
		return grille[l][c];
	}

	public void setGrille(Case piece) {
		this.grille[piece.getLigne()][piece.getColonne()] = piece;
	}
	
	public String toString(){
		StringBuilder s = new StringBuilder("       1        2        3        4        5        6        7        8   ").append(System.lineSeparator());
		s.append("  ┌────────┬────────┬────────┬────────┬────────┬────────┬────────┬────────┐").append(System.lineSeparator());
		int c = 65;
		
		
		for(int i=0; i<8; i++){
			s.append("  │");
			for(int j=0; j<8; j++){
					if(this.grille[i][j].getCaseAutorise())
						s.append(" ┌────┐ │"); //c1
					else
						s.append("        │");						
			}
			s.append(System.lineSeparator());
			s.append((char)(c+i) + " │");	 //c2
			for(int j=0; j<8; j++){
				if(this.grille[i][j].getCaseRemplie())
					if(this.grille[i][j].getCaseAutorise())
						s.append(" │ " + this.grille[i][j]).append(" │ │");//c2
					else
						s.append("   " + this.grille[i][j]).append("   │");//c2
				else
					if(this.grille[i][j].getCaseAutorise())
						s.append(" │  ").append("  │ │");//c2
					else
						s.append("    ").append("    │");//c2
			}
			s.append(System.lineSeparator());
			s.append("  │");
			for(int j=0; j<8; j++){
					if(this.grille[i][j].getCaseAutorise())
						s.append(" └────┘ │"); //c1
					else
						s.append("        │");						
			}
			s.append(System.lineSeparator());
			if(i<7)
				s.append("  ├────────┼────────┼────────┼────────┼────────┼────────┼────────┼────────┤").append(System.lineSeparator());
			else
				s.append("  └────────┴────────┴────────┴────────┴────────┴────────┴────────┴────────┘").append(System.lineSeparator());
		}
		return s.toString();
	}
}








