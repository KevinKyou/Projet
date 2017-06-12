package SunWongParis13.Echiquier;

public class Echiquier {

	private Case[][] grille;
	
	/**
	 * Creation de l'echiquier
	 */
	public Echiquier(){
		this.grille = new Case[8][8];//64 case en tout
		
		for(int l=0; l<8; l++)
			for(int c=0; c<8; c++)
				this.grille[l][c] = new Case(l,c);//ici la creation de chaque case en appelent la fonction case
		
	}
	
	/**
	 * Initialisation de l'echiquier avec ses 32 pieces dont 16 blanches et 16 noires
	 */
	public void initialiseEchiquier(){
		for(int i=0; i<8; i++){			
			if(i==0 || i==7){// ici lorsque l'on arrive au case de la tour
				this.setGrille(new Case(new PieceTour(false, 0, i), 0, i));//on set la couleur et sont emplacement la ligne puis la colonne
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
			
			this.setGrille(new Case(new PiecePion(false, 1, i), 1, i));
			this.setGrille(new Case(new PiecePion(true, 6, i), 6, i));
		}
	}

	public Case getGrille(int l, int c) {
		return grille[l][c];
	}

	public void setGrille(Case c1) {
		this.grille[c1.getLigne()][c1.getColonne()] = c1;
	}
	
	public String toString(){
		StringBuilder s = new StringBuilder("       A        B        C        D        E        F        G        H   ").append(System.lineSeparator());
		s.append("  ┌────────┬────────┬────────┬────────┬────────┬────────┬────────┬────────┐").append(System.lineSeparator());
		
		for(int i=0; i<8; i++){
			s.append("  │");
			for(int j=0; j<8; j++){
					if(this.grille[i][j].getCaseAutorise())
						s.append(" ┌────┐ │"); //c1
					else
						s.append("        │");						
			}
			s.append(System.lineSeparator());
			s.append((i+1) + " │");	 //c2
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
						s.append(" └────┘ │"); //c3
					else
						s.append("        │");	//c3				
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

