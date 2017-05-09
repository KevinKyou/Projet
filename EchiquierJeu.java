package SunWongParis13.Echiquier;

import java.util.Scanner;

import SunWongParis13.Piece.Piece;
import SunWongParis13.Piece.PieceCavalier;
import SunWongParis13.Piece.PieceDame;
import SunWongParis13.Piece.PieceFou;
import SunWongParis13.Piece.PiecePion;
import SunWongParis13.Piece.PieceRoi;
import SunWongParis13.Piece.PieceTour;

public class EchiquierJeu {
	
	private Echiquier jeu;
	private boolean tourJoueur;
	private int nbMouvement;
	
	public EchiquierJeu() {
		this.jeu = new Echiquier();
		this.jeu.initialiseEchiquier();
		this.setTourJoueur(true);
		this.nbMouvement=0;
	}
	
	public boolean getTourJoueur() {
		return tourJoueur;
	}

	public void setTourJoueur(boolean couleur) {
		this.tourJoueur = couleur;
	}
	
	public int getNbMouvement() {
		return nbMouvement;
	}

	public void setNbMouvement() {
		this.nbMouvement = this.getNbMouvement()+1;
	}
	
	public boolean saisieValide(int l, int c){
		return l>=0 && l<8 && c>=0 && c<8;//CHANGER EN EXCEPTION
	}
	
	public int[] saisieDepart(){
		String ch1 = new String();
		boolean valide = false;
		int[] nb = new int[2];
		
		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
		try{
			do{
				do{
					do{
						System.out.println("Saisir les coordonnes de la piece : ");
						ch1 = s.nextLine();
					}while(ch1.length()!=2);
					if(valide==false)
						valide = saisieValide(ch1.charAt(0)-65, ch1.charAt(1)-49)
								&& jeu.getGrille(ch1.charAt(0)-65, ch1.charAt(1)-49).getCaseRemplie();
				}while(!valide);
			}while(jeu.getGrille(ch1.charAt(0)-65, ch1.charAt(1)-49).getPiece().getCouleurPiece()!=this.getTourJoueur());
		}
		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(e.toString());
		}
		
		autorise(ch1.charAt(0)-65, ch1.charAt(1)-49);
		nb[0]=ch1.charAt(0)-65;
		nb[1]=ch1.charAt(1)-49;

		return nb;
	}
	
	public void saisieArrive(int g_l, int g_c){
		String ch1 = new String();
		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
		boolean valide = false;
		try{
			do{
				do{
					System.out.println("Saisir les coordonnes du deplacement : ");
					ch1 = s.nextLine();			
				}while(ch1.length()!=2 || ch1.length()!=2);
				if(valide==false)
					valide = saisieValide(ch1.charAt(0)-65, ch1.charAt(1)-49);
			}while(!valide);
			deplacerPiece(g_l, g_c, ch1.charAt(0)-65, ch1.charAt(1)-49);
		}
		catch(ArrayIndexOutOfBoundsException e){
			System.out.println(e.toString());
		}
	}
	
	public boolean couleurDifferente(Piece p, int l, int c){
		return jeu.getGrille(l, c).getPiece().getCouleurPiece()!=p.getCouleurPiece();
	}
	
	public void promotionPion(Piece p, int g_l, int g_c, int l, int c){
		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
		int a=0;
		System.out.println("Votre pion devient : \n1-Dame\n2-Tour\n3-Fou\n4-Cavalier");
		do{
			System.out.println("Choisissez :");
			a = s.nextInt();
		}while(a<0 || a>4);
		
		Piece p1;
		if(a==1)
			p1 = new PieceDame(p.getCouleurPiece(), p.getLigne(), p.getColonne());
		else if(a==2)
			p1 = new PieceTour(p.getCouleurPiece(), p.getLigne(), p.getColonne());
		else if(a==3)
			p1 = new PieceFou(p.getCouleurPiece(), p.getLigne(), p.getColonne());
		else
			p1 = new PieceCavalier(p.getCouleurPiece(), p.getLigne(), p.getColonne());
		deplacePieceIf(p1, g_l, g_c, l, c);
	}
	
	public void roiGrandRoque(int l, int c){
		boolean estVide = true, estEchec=false;
		try{
			if(jeu.getGrille(l, c-4).getPiece() instanceof PieceTour && jeu.getGrille(l, c).getPiece().getCouleurPiece()==jeu.getGrille(l, c-4).getPiece().getCouleurPiece()){
				for(int i=1; i<=3; i++){
					if(jeu.getGrille(l, c-i).getCaseRemplie())
						estVide = false;
					else
						if(i<3 && deplacementRoiEchec(l, c, l, c-i))
							estEchec = true;
				}
				if(estVide && !estEchec)
					jeu.getGrille(l, c-2).setCaseAutorise(true);
			}
		}
		catch(ArrayIndexOutOfBoundsException e){
			System.out.println(e.toString());
		}
	}
	
	public void roiPetitRoque(int l, int c){
		boolean estVide = true, estEchec=false;
		try{
			if(jeu.getGrille(l, c+3).getPiece() instanceof PieceTour && jeu.getGrille(l, c).getPiece().getCouleurPiece()==jeu.getGrille(l, c+3).getPiece().getCouleurPiece()){
				for(int i=1; i<=2; i++){
					if(jeu.getGrille(l, c+i).getCaseRemplie())
						estVide = false;
					else
						if(i==1 && deplacementRoiEchec(l, c, l, c+i))
							estEchec = true;
				}
				if(estVide && !estEchec)
					jeu.getGrille(l, c+2).setCaseAutorise(true);
			}
		}
		catch(ArrayIndexOutOfBoundsException e){
			System.out.println(e.toString());
		}
	}
	
	public boolean deplacementRoiEchec(int g_l, int g_c, int l, int c){
		Piece p = jeu.getGrille(g_l, g_c).getPiece();
		jeu.getGrille(g_l, g_c).libererCase();
		boolean echec = false, bloque = false;
		int i = 1;
		
		/*
		 * TOUR - TOUR - TOUR - DAME
		 */
		try{
			while(l-i>=0 && !echec && !bloque){
				if(jeu.getGrille(l-i, c).getCaseRemplie()){
					bloque=true;
					if(p.getCouleurPiece()!=jeu.getGrille(l-i, c).getPiece().getCouleurPiece())
						if(jeu.getGrille(l-i, c).getPiece() instanceof PieceTour || jeu.getGrille(l-i, c).getPiece() instanceof PieceDame  || (i==1 && jeu.getGrille(l-i, c).getPiece() instanceof PieceRoi))
							echec = true;
				}
				i++;
			}
			
			i=1;
			bloque = false;
			while(c+i<=7 && !echec && !bloque){
				if(jeu.getGrille(l, c+i).getCaseRemplie()){
					bloque=true;
					if(p.getCouleurPiece()!=jeu.getGrille(l, c+i).getPiece().getCouleurPiece())
						if(jeu.getGrille(l, c+i).getPiece() instanceof PieceTour || jeu.getGrille(l, c+i).getPiece() instanceof PieceDame || (i==1 && jeu.getGrille(l, c+i).getPiece() instanceof PieceRoi))
							echec=true;
				}
				i++;
			}
			
			i=1;
			bloque = false;
			while(l+i<=7 && !echec && !bloque){
				if(jeu.getGrille(l+i, c).getCaseRemplie()){
					bloque=true;
					if(p.getCouleurPiece()!=jeu.getGrille(l+i, c).getPiece().getCouleurPiece())
						if(jeu.getGrille(l+i, c).getPiece() instanceof PieceTour || jeu.getGrille(l+i, c).getPiece() instanceof PieceDame  || (i==1 && jeu.getGrille(l+i, c).getPiece() instanceof PieceRoi))
							echec = true;
				}
				i++;
			}
			
			i=1;
			bloque = false;
			while(c-i>=0 && !echec && !bloque){
				if(jeu.getGrille(l, c-i).getCaseRemplie()){
					bloque=true;
					if(p.getCouleurPiece()!=jeu.getGrille(l, c-i).getPiece().getCouleurPiece())
						if(jeu.getGrille(l, c-i).getPiece() instanceof PieceTour || jeu.getGrille(l, c-i).getPiece() instanceof PieceDame || (i==1 && jeu.getGrille(l, c-i).getPiece() instanceof PieceRoi))
							echec = true;
				}
				i++;
			}
		}
		catch(ArrayIndexOutOfBoundsException e){
			System.out.println(e.toString());
		}

		/*
		 * FOU - FOU
		 */
		try{
			int f=1;
			bloque=false;
			while(l-f>=0 && c-f>=0 && !echec && !bloque){
				if(jeu.getGrille(l-f, c-f).getCaseRemplie()){
					bloque=true;
					if(p.getCouleurPiece()!=jeu.getGrille(l-f, c-f).getPiece().getCouleurPiece()){
						if(jeu.getGrille(l-f, c-f).getPiece() instanceof PieceFou || jeu.getGrille(l-f, c-f).getPiece() instanceof PieceDame  || jeu.getGrille(l-f, c-f).getPiece() instanceof PieceRoi)
							echec=true;
						else if(f==1 && jeu.getGrille(l-f, c-f).getPiece() instanceof PiecePion)
							if(p.getCouleurPiece()==true && jeu.getGrille(l-f, c-f).getPiece().getCouleurPiece()==false)
								echec = true;
					}
				}
				f++;
			}
			
			f=1;
			bloque=false;
			while(l-f>=0 && c+f<=7 && !echec && !bloque){
				if(jeu.getGrille(l-f, c+f).getCaseRemplie()){
					bloque=true;
					if(p.getCouleurPiece()!=jeu.getGrille(l-f, c+f).getPiece().getCouleurPiece()){
						if(jeu.getGrille(l-f, c+f).getPiece() instanceof PieceFou || jeu.getGrille(l-f, c+f).getPiece() instanceof PieceDame  || jeu.getGrille(l-f, c+f).getPiece() instanceof PieceRoi)
							echec = true;
						else if(f==1 && jeu.getGrille(l-f, c+f).getPiece() instanceof PiecePion)
							if(p.getCouleurPiece()==true && jeu.getGrille(l-f, c+f).getPiece().getCouleurPiece()==false)
								echec = true;
					}
				}
				f++;
			}
			
			f=1;
			bloque=false;
			while(l+f<=7 && c-f>=0 && !echec && !bloque){
				if(jeu.getGrille(l+f, c-f).getCaseRemplie()){
					bloque=true;
					if(p.getCouleurPiece()!=jeu.getGrille(l+f, c-f).getPiece().getCouleurPiece()){
						if(jeu.getGrille(l+f, c-f).getPiece() instanceof PieceFou || jeu.getGrille(l+f, c-f).getPiece() instanceof PieceDame  || jeu.getGrille(l+f, c-f).getPiece() instanceof PieceRoi)
							echec = true;
						else if(f==1 && jeu.getGrille(l+f, c-f).getPiece() instanceof PiecePion)
							if(p.getCouleurPiece()==false && jeu.getGrille(l+f, c-f).getPiece().getCouleurPiece()==true)
								echec = true;
					}
				}
				f++;
			}
			
			f=1;
			bloque=false;
			while(l+f<=7 && c+f<=7 && !echec && !bloque){
				if(jeu.getGrille(l+f, c+f).getCaseRemplie()){
					bloque=true;
					if(p.getCouleurPiece()!=jeu.getGrille(l+f, c+f).getPiece().getCouleurPiece()){
						if(jeu.getGrille(l+f, c+f).getPiece() instanceof PieceFou || jeu.getGrille(l+f, c+f).getPiece() instanceof PieceDame  || jeu.getGrille(l+f, c+f).getPiece() instanceof PieceRoi)
							echec = true;
						else if(f==1 && jeu.getGrille(l+f, c+f).getPiece() instanceof PiecePion)
							if(p.getCouleurPiece()==false && jeu.getGrille(l+f, c+f).getPiece().getCouleurPiece()==true)
								echec = true;
					}
				}
				f++;
			}
		}
		catch(ArrayIndexOutOfBoundsException e){
			System.out.println(e.toString());
		}

		/*
		 * CAVALIER - CAVALIER
		 */
		
		try{
			if(l-1>=0 && c-2>=0 && !echec)	
				if(jeu.getGrille(l-1, c-2).getCaseRemplie())
					if(p.getCouleurPiece() != jeu.getGrille(l-1, c-2).getPiece().getCouleurPiece())
						if(jeu.getGrille(l-1, c-2).getPiece() instanceof PieceCavalier)
							echec=true;
	
			if(l-2>=0 && c-1>=0 && !echec)
				if(jeu.getGrille(l-2, c-1).getCaseRemplie())
					if(p.getCouleurPiece() != jeu.getGrille(l-2, c-1).getPiece().getCouleurPiece())
						if(jeu.getGrille(l-2, c-1).getPiece() instanceof PieceCavalier)
							echec=true;
			
			if(l-2>=0 && c+1<=7 && !echec)
				if(jeu.getGrille(l-2, c+1).getCaseRemplie())
					if(p.getCouleurPiece() != jeu.getGrille(l-2, c+1).getPiece().getCouleurPiece())
						if(jeu.getGrille(l-2, c+1).getPiece() instanceof PieceCavalier)
							echec=true;
			
			if(l-1>=0 && c+2<=7 && !echec)
				if(jeu.getGrille(l-1, c+2).getCaseRemplie())
					if(p.getCouleurPiece() != jeu.getGrille(l-1, c+2).getPiece().getCouleurPiece())
						if(jeu.getGrille(l-1, c+2).getPiece() instanceof PieceCavalier)
							echec=true;
			
			if(l+1<=7 && c+2<=7 && !echec)
				if(jeu.getGrille(l+1, c+2).getCaseRemplie())
					if(p.getCouleurPiece() != jeu.getGrille(l+1, c+2).getPiece().getCouleurPiece())
						if(jeu.getGrille(l+1, c+2).getPiece() instanceof PieceCavalier)
							echec=true;
			
			if(l+2<=7 && c+1<=7 && !echec)
				if(jeu.getGrille(l+2, c+1).getCaseRemplie())
					if(p.getCouleurPiece() != jeu.getGrille(l+2, c+1).getPiece().getCouleurPiece())
						if(jeu.getGrille(l+2, c+1).getPiece() instanceof PieceCavalier)
							echec=true;
			
			if(l+2<=7 && c-1>=0 && !echec)
				if(jeu.getGrille(l+2, c-1).getCaseRemplie())
					if(p.getCouleurPiece() != jeu.getGrille(l+2, c-1).getPiece().getCouleurPiece())
						if(jeu.getGrille(l+2, c-1).getPiece() instanceof PieceCavalier)
							echec=true;
			
			if(l+1<=7 && c-2>=0 && !echec)
				if(jeu.getGrille(l+1, c-2).getCaseRemplie())
					if(p.getCouleurPiece() != jeu.getGrille(l+1, c-2).getPiece().getCouleurPiece())
						if(jeu.getGrille(l+1, c-2).getPiece() instanceof PieceCavalier)
							echec=true;
		}
		catch(ArrayIndexOutOfBoundsException e){
			System.out.println(e.toString());
		}
						
		jeu.setGrille(new Case(p, g_l, g_c));
		
		return echec;
	}
	
	public void deplacePieceIf(Piece p, int g_l, int g_c, int l, int c){
		jeu.setGrille(new Case(p, l, c));
		jeu.getGrille(l, c).getPiece().setLigne(l);
		jeu.getGrille(l, c).getPiece().setColonne(c);
		jeu.getGrille(l, c).getPiece().setMouvement();
		System.out.println("Nombre de mouvement : " + jeu.getGrille(l, c).getPiece().getMouvement());
		jeu.getGrille(g_l, g_c).libererCase();
	}
	
	public void deplacePiecePrisePassant(Piece p, int g_l, int g_c, int l, int c){
		deplacePieceIf(p, g_l, g_c, l, c);
		if(g_l-1==l || g_l+1==l){
			if(g_c-1==c)
				jeu.getGrille(g_l, g_c-1).libererCase();
			else if(g_c+1==c)
				jeu.getGrille(g_l, g_c+1).libererCase();
		}
	}
	
	public void libererCaseAutorise(){
		for(int l=0; l<8; l++)
			for(int c=0; c<8; c++)
				jeu.getGrille(l, c).setCaseAutorise(false);
	}
	
	public void deplacerPiece(int g_l, int g_c, int l, int c){
		try{
			if(jeu.getGrille(g_l, g_c).getCaseRemplie()==false)
				System.out.println("Pas de piece dans cette case");
			else if(jeu.getGrille(g_l, g_c).getPiece() instanceof PieceRoi){
				PieceRoi p = (PieceRoi)jeu.getGrille(g_l, g_c).getPiece();
				if(p.deplacementValide(l, c)){
					if(jeu.getGrille(l, c).getCaseAutorise()){
						if(g_c-2==c){
							PieceTour t1 = (PieceTour)jeu.getGrille(g_l, g_c-4).getPiece();
							deplacePieceIf(t1, g_l, g_c-4, g_l, g_c-1);
						}
						else if(g_c+2==c){
							PieceTour t2 = (PieceTour)jeu.getGrille(g_l, g_c+3).getPiece();
							deplacePieceIf(t2, g_l, g_c+3, g_l, g_c+1);
						}
						deplacePieceIf(p, g_l, g_c, l, c);
					}
					else
						System.out.println("Impossible le Roi peut être capturé !");
				}
				else
					System.out.println("Mouvement impossible pour le Roi");
			}
			else if(jeu.getGrille(g_l, g_c).getPiece() instanceof PieceCavalier){
				PieceCavalier p = (PieceCavalier)jeu.getGrille(g_l, g_c).getPiece();
				if(p.deplacementValide(l, c) && jeu.getGrille(l,c).getCaseAutorise())
					deplacePieceIf(p, g_l, g_c, l, c);
				else
					System.out.println("Mouvement impossible pour le Cavalier");
			}
			else if(jeu.getGrille(g_l, g_c).getPiece() instanceof PieceFou){
				PieceFou p = (PieceFou)jeu.getGrille(g_l, g_c).getPiece();
				if(p.deplacementValide(l, c) && jeu.getGrille(l,c).getCaseAutorise())
					deplacePieceIf(p, g_l, g_c, l, c);
				else
					System.out.println("Mouvement impossible pour le Fou");
			}
			else if(jeu.getGrille(g_l, g_c).getPiece() instanceof PieceDame){
				PieceDame p = (PieceDame)jeu.getGrille(g_l, g_c).getPiece();
				if(p.deplacementValide(l, c) && jeu.getGrille(l,c).getCaseAutorise())
					deplacePieceIf(p, g_l, g_c, l, c);
				else
					System.out.println("Mouvement impossible pour la Dame");
			}
			else if(jeu.getGrille(g_l, g_c).getPiece() instanceof PiecePion){
				PiecePion p = (PiecePion)jeu.getGrille(g_l, g_c).getPiece();
				if((p.deplacementValide(l, c) || p.deplacementValideVersus(l, c)) && jeu.getGrille(l, c).getCaseAutorise()){
					if(jeu.getGrille(g_l, g_c).getPiece().getCouleurPiece() && l==0)
						promotionPion(p, g_l, g_c, l, c);
					else if(!jeu.getGrille(g_l, g_c).getPiece().getCouleurPiece() && l==7)
						promotionPion(p, g_l, g_c, l, c);
					else if(g_l==3 && g_c-1>=0 && g_c-1==c && jeu.getGrille(g_l, g_c-1).getPiece() instanceof PiecePion && jeu.getGrille(g_l, g_c-1).getPiece().getMouvement()==1){
						if(couleurDifferente(jeu.getGrille(g_l, g_c).getPiece(), g_l, g_c-1))
							deplacePiecePrisePassant(p, g_l, g_c, l, c);
					}
					else if(g_l==3 && g_c+1<=7 && g_c+1==c && jeu.getGrille(g_l, g_c+1).getPiece() instanceof PiecePion && jeu.getGrille(g_l, g_c+1).getPiece().getMouvement()==1){
						if(couleurDifferente(jeu.getGrille(g_l, g_c).getPiece(), g_l, g_c+1))
							deplacePiecePrisePassant(p, g_l, g_c, l, c);
					}
					else if(g_l==4 && g_c-1>=0 && g_c-1==c && jeu.getGrille(g_l, g_c-1).getPiece() instanceof PiecePion && jeu.getGrille(g_l, g_c-1).getPiece().getMouvement()==1){
						if(couleurDifferente(jeu.getGrille(g_l, g_c).getPiece(), g_l, g_c-1))
							deplacePiecePrisePassant(p, g_l, g_c, l, c);
					}
					else if(g_l==4 && g_c+1<=7 && g_c+1==c && jeu.getGrille(g_l, g_c+1).getPiece() instanceof PiecePion && jeu.getGrille(g_l, g_c+1).getPiece().getMouvement()==1){
						if(couleurDifferente(jeu.getGrille(g_l, g_c).getPiece(), g_l, g_c+1))
							deplacePiecePrisePassant(p, g_l, g_c, l, c);
					}
					else
						deplacePieceIf(p, g_l, g_c, l, c);
				}
				else
					System.out.println("Mouvement impossible pour le Pion");
			}
			// ROQUE DE LA TOUR ???
			else if(jeu.getGrille(g_l, g_c).getPiece() instanceof PieceTour){
				PieceTour p = (PieceTour)jeu.getGrille(g_l, g_c).getPiece();
				if(p.deplacementValide(l, c))
					if(jeu.getGrille(l,c).getCaseAutorise()){/*
						if(g_c+3==c){
							if(jeu.getGrille(g_l, g_c).getPiece().getMouvement()==0 && jeu.getGrille(g_l, g_c+4).getPiece().getMouvement()==0){
								boolean estEchec = false;			
								for(int i=1; i<=3; i++)
									if(deplacementRoiEchec(g_l, g_c, g_l, g_c+i))
										estEchec = true;
								if(!estEchec){
									PieceRoi r1 = (PieceRoi)jeu.getGrille(g_l, g_c+4).getPiece();
									deplacePieceIf(r1, g_l, g_c+4, g_l, g_c+2);
								}
							}
						}
						else if(g_c-2==c){
							if(jeu.getGrille(g_l, g_c).getPiece().getMouvement()==0 && jeu.getGrille(g_l, g_c-3).getPiece().getMouvement()==0){
								boolean estEchec = false;			
								for(int i=1; i<=2; i++)
									if(deplacementRoiEchec(g_l, g_c, g_l, g_c-i))
										estEchec = true;
								if(!estEchec){
									PieceRoi r1 = (PieceRoi)jeu.getGrille(g_l, g_c-3).getPiece();
									deplacePieceIf(r1, g_l, g_c-3, g_l, g_c-1);
								}
							}
						}*/
						deplacePieceIf(p, g_l, g_c, l, c);
					}
				else
					System.out.println("Mouvement impossible pour la Tour");
			}
			else
				System.out.println("Aucun mouvement");
				
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Vous n'etes pas dans la grille");
		}
	}
	
	public void autorise(int l, int c){
		if(saisieValide(l, c)){
			if(jeu.getGrille(l, c).getPiece() instanceof PieceRoi){
				if(jeu.getGrille(l, c).getPiece().getMouvement()==0){
					if(l==jeu.getGrille(l,c).getLigne() && c-4>=0)
						roiGrandRoque(l, c);
					if(l==jeu.getGrille(l,c).getLigne() && c+3<=7)
						roiPetitRoque(l, c);
				}
				if(l-1>=0 && c-1>=0)
					if(!deplacementRoiEchec(l, c, l-1, c-1))
						if(!jeu.getGrille(l-1,c-1).getCaseRemplie() || (!(jeu.getGrille(l-1,c-1).getPiece() instanceof PieceRoi) && couleurDifferente(jeu.getGrille(l,c).getPiece(), l-1, c-1) ))
							jeu.getGrille(l-1,c-1).setCaseAutorise(true);
				if(l-1>=0 && c==jeu.getGrille(l,c).getColonne())
					if(!deplacementRoiEchec(l, c, l-1, c))
						if(!jeu.getGrille(l-1,c).getCaseRemplie() || (!(jeu.getGrille(l-1,c).getPiece() instanceof PieceRoi) && couleurDifferente(jeu.getGrille(l,c).getPiece(), l-1, c) ))
							jeu.getGrille(l-1,c).setCaseAutorise(true);
				if(l-1>=0 && c+1<=7)
					if(!deplacementRoiEchec(l, c, l-1, c+1))
						if(!jeu.getGrille(l-1,c+1).getCaseRemplie() || (!(jeu.getGrille(l-1,c+1).getPiece() instanceof PieceRoi) && couleurDifferente(jeu.getGrille(l,c).getPiece(), l-1, c+1) ))
							jeu.getGrille(l-1,c+1).setCaseAutorise(true);
				
				if(l==jeu.getGrille(l,c).getLigne() && c-1>=0)
					if(!deplacementRoiEchec(l, c, l, c-1))
						if(!jeu.getGrille(l,c-1).getCaseRemplie() || (!(jeu.getGrille(l,c-1).getPiece() instanceof PieceRoi) && couleurDifferente(jeu.getGrille(l,c).getPiece(), l, c-1) ))
							jeu.getGrille(l,c-1).setCaseAutorise(true);
				if(l==jeu.getGrille(l,c).getLigne() && c+1<=7)
					if(!deplacementRoiEchec(l, c, l, c+1))
						if(!jeu.getGrille(l,c+1).getCaseRemplie() || (!(jeu.getGrille(l,c+1).getPiece() instanceof PieceRoi) && couleurDifferente(jeu.getGrille(l,c).getPiece(), l, c+1) ))
							jeu.getGrille(l,c+1).setCaseAutorise(true);
				
				if(l+1<=7 && c+1<=7)
					if(!deplacementRoiEchec(l, c, l+1, c+1))
						if(!jeu.getGrille(l+1,c+1).getCaseRemplie() || (!(jeu.getGrille(l+1,c+1).getPiece() instanceof PieceRoi) && couleurDifferente(jeu.getGrille(l,c).getPiece(), l+1, c+1) ))
							jeu.getGrille(l+1,c+1).setCaseAutorise(true);
				if(l+1<=7 && c==jeu.getGrille(l,c).getColonne())
					if(!deplacementRoiEchec(l, c, l+1, c))
						if(!jeu.getGrille(l+1,c).getCaseRemplie() || (!(jeu.getGrille(l+1,c).getPiece() instanceof PieceRoi) && couleurDifferente(jeu.getGrille(l,c).getPiece(), l+1, c) ))
							jeu.getGrille(l+1,c).setCaseAutorise(true);
				if(l+1<=7 && c-1>=0)
					if(!deplacementRoiEchec(l, c, l+1, c-1))
						if(!jeu.getGrille(l+1,c-1).getCaseRemplie() || (!(jeu.getGrille(l+1,c-1).getPiece() instanceof PieceRoi) && couleurDifferente(jeu.getGrille(l,c).getPiece(), l+1, c-1) ))
							jeu.getGrille(l+1,c-1).setCaseAutorise(true);
			}
			else if(jeu.getGrille(l,c).getPiece() instanceof PieceCavalier){
				if(l-1>=0 && c-2>=0)
					if(!jeu.getGrille(l-1,c-2).getCaseRemplie())
						jeu.getGrille(l-1,c-2).setCaseAutorise(true);
					else if(!(jeu.getGrille(l-1,c-2).getPiece() instanceof PieceRoi))
						if(couleurDifferente(jeu.getGrille(l,c).getPiece(), l-1, c-2))
							jeu.getGrille(l-1,c-2).setCaseAutorise(true);
				
				if(l-2>=0 && c-1>=0)
					if(!jeu.getGrille(l-2,c-1).getCaseRemplie())
						jeu.getGrille(l-2,c-1).setCaseAutorise(true);
					else if(!(jeu.getGrille(l-2,c-1).getPiece() instanceof PieceRoi))
						if(couleurDifferente(jeu.getGrille(l,c).getPiece(), l-2, c-1))
							jeu.getGrille(l-2,c-1).setCaseAutorise(true);
				
				if(l-2>=0 && c+1<=7)
					if(!jeu.getGrille(l-2,c+1).getCaseRemplie())
						jeu.getGrille(l-2,c+1).setCaseAutorise(true);
					else if(!(jeu.getGrille(l-2,c+1).getPiece() instanceof PieceRoi))
						if(couleurDifferente(jeu.getGrille(l,c).getPiece(), l-2, c+1))
							jeu.getGrille(l-2,c+1).setCaseAutorise(true);
				
				if(l-1>=0 && c+2<=7)
					if(!jeu.getGrille(l-1,c+2).getCaseRemplie())
						jeu.getGrille(l-1,c+2).setCaseAutorise(true);
					else if(!(jeu.getGrille(l-1,c+2).getPiece() instanceof PieceRoi))
						if(couleurDifferente(jeu.getGrille(l,c).getPiece(), l-1, c+2))
							jeu.getGrille(l-1,c+2).setCaseAutorise(true);
				
				if(l+1<=7 && c+2<=7)
					if(!jeu.getGrille(l+1,c+2).getCaseRemplie())
						jeu.getGrille(l+1,c+2).setCaseAutorise(true);
					else if(!(jeu.getGrille(l+1,c+2).getPiece() instanceof PieceRoi))
						if(couleurDifferente(jeu.getGrille(l,c).getPiece(), l+1, c+2))
							jeu.getGrille(l+1,c+2).setCaseAutorise(true);
				
				if(l+2<=7 && c+1<=7)
					if(!jeu.getGrille(l+2,c+1).getCaseRemplie())
						jeu.getGrille(l+2,c+1).setCaseAutorise(true);
					else if(!(jeu.getGrille(l+2,c+1).getPiece() instanceof PieceRoi))
						if(couleurDifferente(jeu.getGrille(l,c).getPiece(), l+2, c+1))
							jeu.getGrille(l+2,c+1).setCaseAutorise(true);
				
				if(l+2<=7 && c-1>=0)
					if(!jeu.getGrille(l+2,c-1).getCaseRemplie())
						jeu.getGrille(l+2,c-1).setCaseAutorise(true);
					else if(!(jeu.getGrille(l+2,c-1).getPiece() instanceof PieceRoi))
						if(couleurDifferente(jeu.getGrille(l,c).getPiece(), l+2, c-1))
							jeu.getGrille(l+2,c-1).setCaseAutorise(true);
				
				if(l+1<=7 && c-2>=0)
					if(!jeu.getGrille(l+1,c-2).getCaseRemplie())
						jeu.getGrille(l+1,c-2).setCaseAutorise(true);
					else if(!(jeu.getGrille(l+1,c-2).getPiece() instanceof PieceRoi))
						if(couleurDifferente(jeu.getGrille(l,c).getPiece(), l+1, c-2))
							jeu.getGrille(l+1,c-2).setCaseAutorise(true);
			}
			else if(jeu.getGrille(l,c).getPiece() instanceof PieceFou){
				int i = 1;
				boolean vide = true;
				
				while(l-i>=0 && c-i>=0 && vide){
					if(!jeu.getGrille(l-i,c-i).getCaseRemplie())
						jeu.getGrille(l-i,c-i).setCaseAutorise(true);
					else if(!(jeu.getGrille(l-i,c-i).getPiece() instanceof PieceRoi)){
						if(couleurDifferente(jeu.getGrille(l,c).getPiece(), l-i, c-i))
							jeu.getGrille(l-i,c-i).setCaseAutorise(true);
						vide=false;
					}
					else
						vide=false;
					i++;
				}
				i=1;
				vide = true;
				while(l-i>=0 && c+i<=7 && vide){
					if(!jeu.getGrille(l-i,c+i).getCaseRemplie())
						jeu.getGrille(l-i,c+i).setCaseAutorise(true);
					else if(!(jeu.getGrille(l-i,c+i).getPiece() instanceof PieceRoi)){
						if(couleurDifferente(jeu.getGrille(l,c).getPiece(), l-i, c+i))
							jeu.getGrille(l-i,c+i).setCaseAutorise(true);
						vide=false;
					}
					else
						vide=false;
					i++;
				}
				i=1;
				vide = true;
				while(l+i<=7 && c-i>=0 && vide){
					if(!jeu.getGrille(l+i,c-i).getCaseRemplie())
						jeu.getGrille(l+i,c-i).setCaseAutorise(true);
					else if(!(jeu.getGrille(l+i,c-i).getPiece() instanceof PieceRoi)){
						if(couleurDifferente(jeu.getGrille(l,c).getPiece(), l+i, c-i))
							jeu.getGrille(l+i,c-i).setCaseAutorise(true);
						vide=false;
					}
					else
						vide=false;
					i++;
				}
				i=1;
				vide = true;
				while(l+i<=7 && c+i<=7 && vide){
					if(!jeu.getGrille(l+i,c+i).getCaseRemplie())
						jeu.getGrille(l+i,c+i).setCaseAutorise(true);
					else if(!(jeu.getGrille(l+i,c+i).getPiece() instanceof PieceRoi)){
						if(couleurDifferente(jeu.getGrille(l,c).getPiece(), l+i, c+i))
							jeu.getGrille(l+i,c+i).setCaseAutorise(true);
						vide=false;
					}
					else
						vide=false;
					i++;
				}
			}
			else if(jeu.getGrille(l,c).getPiece() instanceof PieceTour){
				int i = 1;
				boolean vide = true;
				while(l-i>=0 && vide){
					if(!jeu.getGrille(l-i,c).getCaseRemplie())
						jeu.getGrille(l-i,c).setCaseAutorise(true);
					else if(!(jeu.getGrille(l-i,c).getPiece() instanceof PieceRoi)){
						if(couleurDifferente(jeu.getGrille(l,c).getPiece(), l-i, c))
							jeu.getGrille(l-i,c).setCaseAutorise(true);
						vide=false;
					}
					else
						vide=false;
					i++;
				}
				i=1;
				vide = true;
				while(l+i<=7 && vide){
					if(!jeu.getGrille(l+i,c).getCaseRemplie())
						jeu.getGrille(l+i,c).setCaseAutorise(true);
					else if(!(jeu.getGrille(l+i,c).getPiece() instanceof PieceRoi)){
						if(couleurDifferente(jeu.getGrille(l,c).getPiece(), l+i, c))
							jeu.getGrille(l+i,c).setCaseAutorise(true);
						vide=false;
					}
					else
						vide=false;
					i++;
				}
				i=1;
				vide = true;
				while(c-i>=0 && vide){
					if(!jeu.getGrille(l,c-i).getCaseRemplie())
						jeu.getGrille(l,c-i).setCaseAutorise(true);
					else if(!(jeu.getGrille(l,c-i).getPiece() instanceof PieceRoi)){
						if(couleurDifferente(jeu.getGrille(l,c).getPiece(), l, c-i))
							jeu.getGrille(l,c-i).setCaseAutorise(true);
						vide=false;
					}
					else
						vide=false;
					i++;
				}
			
				i=1;
				vide = true;
				while(c+i<=7 && vide){
					if(!jeu.getGrille(l,c+i).getCaseRemplie())
						jeu.getGrille(l,c+i).setCaseAutorise(true);
					else if(!(jeu.getGrille(l,c+i).getPiece() instanceof PieceRoi)){
						if(couleurDifferente(jeu.getGrille(l,c).getPiece(), l, c+i))
							jeu.getGrille(l,c+i).setCaseAutorise(true);
						vide=false;
					}
					else
						vide=false;
					i++;
				}
			}
			else if(jeu.getGrille(l,c).getPiece() instanceof PieceDame){
				int i = 1;
				boolean vide = true;
				/*
				 * TOUR
				 */
				while(l-i>=0 && vide){
					if(!jeu.getGrille(l-i,c).getCaseRemplie())
						jeu.getGrille(l-i,c).setCaseAutorise(true);
					else if(!(jeu.getGrille(l-i,c).getPiece() instanceof PieceRoi)){
						if(couleurDifferente(jeu.getGrille(l,c).getPiece(), l-i, c))
							jeu.getGrille(l-i,c).setCaseAutorise(true);
						vide=false;
					}
					else
						vide=false;
					i++;
				}
				i=1;
				vide = true;
				while(l+i<=7 && vide){
					if(!jeu.getGrille(l+i,c).getCaseRemplie())
						jeu.getGrille(l+i,c).setCaseAutorise(true);
					else if(!(jeu.getGrille(l+i,c).getPiece() instanceof PieceRoi)){
						if(couleurDifferente(jeu.getGrille(l,c).getPiece(), l+i, c))
							jeu.getGrille(l+i,c).setCaseAutorise(true);
						vide=false;
					}
					else
						vide=false;
					i++;
				}
				i=1;
				vide = true;
				while(c-i>=0 && vide){
					if(!jeu.getGrille(l,c-i).getCaseRemplie())
						jeu.getGrille(l,c-i).setCaseAutorise(true);
					else if(!(jeu.getGrille(l,c-i).getPiece() instanceof PieceRoi)){
						if(couleurDifferente(jeu.getGrille(l,c).getPiece(), l, c-i))
							jeu.getGrille(l,c-i).setCaseAutorise(true);
						vide=false;
					}
					else
						vide=false;
					i++;
				}
			
				i=1;
				vide = true;
				while(c+i<=7 && vide){
					if(!jeu.getGrille(l,c+i).getCaseRemplie())
						jeu.getGrille(l,c+i).setCaseAutorise(true);
					else if(!(jeu.getGrille(l,c+i).getPiece() instanceof PieceRoi)){
						if(couleurDifferente(jeu.getGrille(l,c).getPiece(), l, c+i))
							jeu.getGrille(l,c+i).setCaseAutorise(true);
						vide=false;
					}
					else
						vide=false;
					i++;
				}
				
				/*
				 * FOU				
				 */
				i=1;
				vide = true;
				while(l-i>=0 && c-i>=0 && vide){
					if(!jeu.getGrille(l-i,c-i).getCaseRemplie())
						jeu.getGrille(l-i,c-i).setCaseAutorise(true);
					else if(!(jeu.getGrille(l-i,c-i).getPiece() instanceof PieceRoi)){
						if(couleurDifferente(jeu.getGrille(l,c).getPiece(), l-i, c-i))
							jeu.getGrille(l-i,c-i).setCaseAutorise(true);
						vide=false;
					}
					else
						vide=false;
					i++;
				}
				i=1;
				vide = true;
				while(l-i>=0 && c+i<=7 && vide){
					if(!jeu.getGrille(l-i,c+i).getCaseRemplie())
						jeu.getGrille(l-i,c+i).setCaseAutorise(true);
					else if(!(jeu.getGrille(l-i,c+i).getPiece() instanceof PieceRoi)){
						if(couleurDifferente(jeu.getGrille(l,c).getPiece(), l-i, c+i))
							jeu.getGrille(l-i,c+i).setCaseAutorise(true);
						vide=false;
					}
					else
						vide=false;
					i++;
				}
				i=1;
				vide = true;
				while(l+i<=7 && c-i>=0 && vide){
					if(!jeu.getGrille(l+i,c-i).getCaseRemplie())
						jeu.getGrille(l+i,c-i).setCaseAutorise(true);
					else if(!(jeu.getGrille(l+i,c-i).getPiece() instanceof PieceRoi)){
						if(couleurDifferente(jeu.getGrille(l,c).getPiece(), l+i, c-i))
							jeu.getGrille(l+i,c-i).setCaseAutorise(true);
						vide=false;
					}
					else
						vide=false;
					i++;
				}
				i=1;
				vide = true;
				while(l+i<=7 && c+i<=7 && vide){
					if(!jeu.getGrille(l+i,c+i).getCaseRemplie())
						jeu.getGrille(l+i,c+i).setCaseAutorise(true);
					else if(!(jeu.getGrille(l+i,c+i).getPiece() instanceof PieceRoi)){
						if(couleurDifferente(jeu.getGrille(l,c).getPiece(), l+i, c+i))
							jeu.getGrille(l+i,c+i).setCaseAutorise(true);
						vide=false;
					}
					else
						vide=false;
					i++;
				}
			}
			else if(jeu.getGrille(l,c).getPiece() instanceof PiecePion){
				int i = 1;
				boolean vide = true;
				//AUCUN MOUVEMENT
				if(jeu.getGrille(l,c).getPiece().getMouvement()==0){
					while(i<=2 && vide){
						//BLANC
						if(jeu.getGrille(l,c).getPiece().getCouleurPiece()){
							if(!jeu.getGrille(l-i,c).getCaseRemplie())
								jeu.getGrille(l-i,c).setCaseAutorise(true);
							if(c-1>=0 && i==1)
								if(jeu.getGrille(l-1,c-1).getCaseRemplie() && couleurDifferente(jeu.getGrille(l,c).getPiece(), l-1, c-1))
									if(!(jeu.getGrille(l-1,c-1).getPiece() instanceof PieceRoi))
										jeu.getGrille(l-i,c-1).setCaseAutorise(true);
							if(c+1<=7 && i==1)
								if(jeu.getGrille(l-1,c+1).getCaseRemplie() && couleurDifferente(jeu.getGrille(l,c).getPiece(), l-1, c+1))
									if(!(jeu.getGrille(l-1,c+1).getPiece() instanceof PieceRoi))
										jeu.getGrille(l-i,c+1).setCaseAutorise(true);
						}
						//NOIR
						else{
							if(!jeu.getGrille(l+i,c).getCaseRemplie())
								jeu.getGrille(l+i,c).setCaseAutorise(true);
							if(c-1>=0 && i==1)
								if(jeu.getGrille(l+1,c-1).getCaseRemplie() && couleurDifferente(jeu.getGrille(l,c).getPiece(), l+1, c-1))
									if(!(jeu.getGrille(l+1,c-1).getPiece() instanceof PieceRoi))
										jeu.getGrille(l+i,c-1).setCaseAutorise(true);
							if(c+1<=7 && i==1)
								if(jeu.getGrille(l+1,c+1).getCaseRemplie() && couleurDifferente(jeu.getGrille(l,c).getPiece(), l+1, c+1))
									if(!(jeu.getGrille(l+1,c+1).getPiece() instanceof PieceRoi))
										jeu.getGrille(l+i,c+1).setCaseAutorise(true);
						}
						
						i++;
					}
				}
				//APRES MOUVEMENT
				else{
					if(jeu.getGrille(l,c).getPiece().getCouleurPiece()==true){
						if(l-1>=0 && !jeu.getGrille(l-1,c).getCaseRemplie())
							jeu.getGrille(l-1,c).setCaseAutorise(true);
						if(l-1>=0 && c-1>=0 && jeu.getGrille(l-1,c-1).getCaseRemplie() && couleurDifferente(jeu.getGrille(l,c).getPiece(), l-1, c-1))
							if(!(jeu.getGrille(l-1,c-1).getPiece() instanceof PieceRoi))
								jeu.getGrille(l-1,c-1).setCaseAutorise(true);
						if(l-1>=0 && c+1<=7 && jeu.getGrille(l-1,c+1).getCaseRemplie() && couleurDifferente(jeu.getGrille(l,c).getPiece(), l-1, c+1))
							if(!(jeu.getGrille(l-1,c+1).getPiece() instanceof PieceRoi))
								jeu.getGrille(l-1,c+1).setCaseAutorise(true);
						if(l==3){
							if(c-1>=0 && jeu.getGrille(l, c-1).getCaseRemplie()){
								if(jeu.getGrille(l, c-1).getPiece() instanceof PiecePion && couleurDifferente(jeu.getGrille(l, c).getPiece(), l, c-1))
									if(jeu.getGrille(l, c-1).getPiece().getMouvement()==1)
										if(!jeu.getGrille(l-1, c-1).getCaseRemplie())
											jeu.getGrille(l-1, c-1).setCaseAutorise(true);
							}
							if(c+1<=7 && jeu.getGrille(l, c+1).getCaseRemplie()){
								if(jeu.getGrille(l, c+1).getPiece() instanceof PiecePion && couleurDifferente(jeu.getGrille(l, c).getPiece(), l, c+1))
									if(jeu.getGrille(l, c+1).getPiece().getMouvement()==1)
										if(!jeu.getGrille(l-1, c+1).getCaseRemplie())
											jeu.getGrille(l-1, c+1).setCaseAutorise(true);
							}
							
						}
					}
					else{
						if(l+1<=7 && !jeu.getGrille(l+1,c).getCaseRemplie())
							jeu.getGrille(l+1,c).setCaseAutorise(true);
						if(l+1<=7 && c-1>=0 && jeu.getGrille(l+1,c-1).getCaseRemplie() && couleurDifferente(jeu.getGrille(l,c).getPiece(), l+1, c-1))
							if(!(jeu.getGrille(l+1,c-1).getPiece() instanceof PieceRoi))
								jeu.getGrille(l+1,c-1).setCaseAutorise(true);
						if(l+1<=7 && c+1<=7 && jeu.getGrille(l+1,c+1).getCaseRemplie() && couleurDifferente(jeu.getGrille(l,c).getPiece(), l+1, c+1))
							if(!(jeu.getGrille(l+1,c+1).getPiece() instanceof PieceRoi))
								jeu.getGrille(l+1,c+1).setCaseAutorise(true);
						if(l==4){
							if(c-1>=0 && jeu.getGrille(l, c-1).getCaseRemplie()){
								if(jeu.getGrille(l, c-1).getPiece() instanceof PiecePion && couleurDifferente(jeu.getGrille(l, c).getPiece(), l, c-1))
									if(jeu.getGrille(l, c-1).getPiece().getMouvement()==1)
										if(!jeu.getGrille(l, c-1).getCaseRemplie())
											jeu.getGrille(l, c-1).setCaseAutorise(true);
							}
							if(c+1<=7 && jeu.getGrille(l, c+1).getCaseRemplie()){
								if(jeu.getGrille(l, c+1).getPiece() instanceof PiecePion && couleurDifferente(jeu.getGrille(l, c).getPiece(), l, c+1))
									if(jeu.getGrille(l, c+1).getPiece().getMouvement()==1)
										if(!jeu.getGrille(l+1, c+1).getCaseRemplie())
											jeu.getGrille(l+1, c+1).setCaseAutorise(true);
							}
						}
					}
				}
				
				
			}
		}
	}
	
	public void jouerJeu(){
		EchiquierJeu jouer = new EchiquierJeu();
		System.out.println(jouer);
		
		Joueur j1 = new Joueur("JoueurBlanc", true);
		Joueur j2 = new Joueur("JoueurNoir", false);
		int[] tab = new int[2];
		
		do{
			if(this.getTourJoueur())
				System.out.println("Tour de : " + j1.getNom());
			else
				System.out.println("Tour de : " + j2.getNom());
			tab = jouer.saisieDepart();
			System.out.println(jouer);
			jouer.saisieArrive(tab[0], tab[1]);
			jouer.libererCaseAutorise();
			System.out.println(jouer);
			this.setTourJoueur(!this.getTourJoueur());
		}while(true);
	}
	
	public String toString(){
		return jeu.toString();
	}
}
