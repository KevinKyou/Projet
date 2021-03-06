package jeuEchec;

public class echec {
    public static void main (String [] args) {    
    
    int[][] grille = {
    		{0,2,3,4,5,6,7,8},
    		{1,2,3,4,5,6,7,8},
    		{2,2,3,4,5,6,7,8},
    		{3,2,3,4,5,6,7,8},
    		{4,2,3,4,5,6,7,8},
    		{5,2,3,4,5,6,7,8},
    		{6,2,3,4,5,6,7,8},
    		{7,2,3,4,5,6,7,8}   
    };
    
    System.out.print("    ");
	for(int num=65; num<=72; num++){
		System.out.print("  " + (char)num + "   ");
	}
	System.out.println();
    
    
    for(int i=0; i<grille.length; i++){    	
    	System.out.print("   *");
    	for(int a=0; a<grille.length; a++){
    		System.out.print("-----*");
    	}
    	System.out.println();
    	System.out.print(" " + (8-i) + " |");
    	for(int j=0; j<grille.length; j++){
    		System.out.print("  " + grille[i][j] + "  |"); //System.out.print("  " + grille[i][j] + " |"); apres chargement 2 lettres
    	}
    	System.out.println(" " + (8-i));
    }
    
    System.out.print("   *");
    for(int a=0; a<grille.length; a++){
		System.out.print("-----*");
	}
	System.out.println();
    
	System.out.print("    ");
	for(int num=65; num<=72; num++){
		System.out.print("  " + (char)num + "   ");
	}
	System.out.println();
    
    
    
    }
    
}








package echequier.test.personnage;

import java.util.ArrayList;
import java.util.List;

public class programme {
	public static void main (String [] args) {    
	    
	    String[][] grille = new String[][]{
	    	{"Tb","Cb","Fb","Rb","rb","Fb","Cb","Tb"},
	    	{"Pb","Pb","Pb","Pb","Pb","Pb","Pb","Pb"},
	    	{"  ","  ","  ","  ","  ","  ","  ","  "},
	    	{"  ","  ","  ","  ","  ","  ","  ","  "},
	    	{"  ","  ","  ","  ","  ","  ","  ","  "},
	    	{"  ","  ","  ","  ","  ","  ","  ","  "},
	    	{"Pn","Pn","Pn","Pn","Pn","Pn","Pn","Pn"},
	    	{"Tn","Cn","Fn","Rn","rn","Fn","Cn","Tn"}
	    };
	    //LETTRE
	    System.out.print("    ");
		for(int num=65; num<=72; num++){
			System.out.print("  " + (char)num + "   ");
		}
		System.out.println();
	    
	    
	    for(int i=(grille.length-1); i>=0; i--){
	    	//PREMIERE LIGNE DE CHAQUE CASE
	    	System.out.print("   *");
	    	for(int a=0; a<grille.length; a++){
	    		System.out.print("-----*");
	    	}
	    	System.out.println();
	    	//DEUXIEME LIGNE DE CHAQUE CASE
	    	System.out.print(" " + (i+1) + " |");
	    	for(int j=0; j<grille.length; j++){
	    		System.out.print(" " + grille[i][j] + "  |");
	    	}
	    	System.out.println(" " + (i+1));
	    }
	    //DERNIERE LIGNE
	    System.out.print("   *");
	    for(int a=0; a<grille.length; a++){
			System.out.print("-----*");
		}
		System.out.println();
	    
		//LETTRE DERNIERE LIGNE
		System.out.print("    ");
		for(int num=65; num<=72; num++){
			System.out.print("  " + (char)num + "   ");
		}
		System.out.println();
	    
		if(grille[3][3]!=null)
			grille[3][3]="65";
		
		piece roi = new pieceRoi();
		piece reine = new pieceReine();
		piece tourelle = new pieceTourelle();
		piece cavalier = new pieceCavalier();
		piece fou = new pieceFou();
		piece pion = new piecePion();
		
		List<piece> pieceEchequier = new ArrayList<piece>();
		pieceEchequier.add(roi);
		pieceEchequier.add(reine);
		pieceEchequier.add(tourelle);
		pieceEchequier.add(cavalier);
		pieceEchequier.add(fou);
		pieceEchequier.add(pion);
		
		
		for (piece e : pieceEchequier)
			System.out.println(e.nomPiece);
	    }
}


TEST
