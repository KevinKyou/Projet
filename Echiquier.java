package SunWongParis13.Echiquier;

import java.util.Scanner;

import SunWongParis13.Piece.*;

public class Echiquier {
	
	private Case[][] grille;
	
	/**
	 * Constructeur par défaut de l'échiquier
	 */
	public Echiquier(){
		this.setGrille(8,8);
		
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				this.grille[i][j] = new Case(i,j);
			}
		}
	}
	
	/**
	 * Initialisation des piÃ¨ces sur l'échiquier
	 */
	public void initialiseEchiquier(){
		for(int i=0; i<8; i++){			
			if(i==0 || i==7){
				this.grille[0][i] = new Case(new PieceTour(false, 0, i), 0, i);
				this.grille[7][i] = new Case(new PieceTour(true, 7, i), 7, i);
			}
			else if(i==1 || i==6){
				this.grille[0][i] = new Case(new PieceCavalier(false, 0, i), 0, i);
				this.grille[7][i] = new Case(new PieceCavalier(true, 7, i), 7, i);
			}
			else if(i==2 || i==5){
				this.grille[0][i] = new Case(new PieceFou(false, 0, i), 0, i);
				this.grille[7][i] = new Case(new PieceFou(true, 7, i), 7, i);
			}
			else if(i==3){
				this.grille[0][i] = new Case(new PieceDame(false, 0, i), 0, i);
				this.grille[7][i] = new Case(new PieceDame(true, 7, i), 7, i);
			}
			else{
				this.grille[0][i] = new Case(new PieceRoi(false, 0, i), 0, i);
				this.grille[7][i] = new Case(new PieceRoi(true, 7, i), 7, i);
			}
			this.grille[1][i] = new Case(new PiecePion(false, 1, i), 1, i);
			this.grille[6][i] = new Case(new PiecePion(true, 6, i), 6, i);
		}
	}
	
	/**
	 * 
	 * @param l : Ligne de l'échiquier
	 * @param c : Colonne de l'échiquier
	 */
	public void setGrille(int l, int c){
		this.grille = new Case[l][c];
	}
	
	/**
	 * 
	 * Si toute les conditions sont reunis, on deplace la piece (case, ligne, colonne, mouvement, liberer la case)
	 * @param grille : Grille de l'échiquier
	 * @param p : Piece de l'échiquier
	 * @param g_l : Ligne de départ
	 * @param g_c : Colonne de départ
	 * @param l : Ligne d'arrivée 
	 * @param c : Colonne d'arrivée
	 */
	public void deplacePieceIf(Case[][] grille, Piece p, int g_l, int g_c, int l, int c){
		grille[l][c] = new Case(p, l, c);
		grille[l][c].getPiece().setLigne(l);
		grille[l][c].getPiece().setColonne(c);
		grille[l][c].getPiece().setMouvement();
		grille[g_l][g_c].libererCase();
	}
	
	/**
	 * 
	 * Le pion arrive au fond de l'échiquier, il peut donc se transformer en Dame, Tour, Fou, Cavalier
	 * @param grille : Grille de l'échiquier
	 * @param p : Piece de l'échiquier
	 * @param g_l : Ligne de départ
	 * @param g_c : Colonne de départ
	 * @param l : Ligne d'arrivée 
	 * @param c : Colonne d'arrivée
	 */
	public void evolutionPion(Case[][] grille, Piece p, int g_l, int g_c, int l, int c){
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
			p1 = new PieceDame(p.getMouvement(), p.getLigne(), p.getColonne());
		else if(a==2)
			p1 = new PieceTour(p.getMouvement(), p.getLigne(), p.getColonne());
		else if(a==3)
			p1 = new PieceFou(p.getMouvement(), p.getLigne(), p.getColonne());
		else
			p1 = new PieceCavalier(p.getMouvement(), p.getLigne(), p.getColonne());
		deplacePieceIf(grille, p1, g_l, g_c, l, c);
	}
	
	/**
	 * 
	 * @param l : Ligne de l'échiquier
	 * @param c : Colonne de l'échiquier
	 * @return Verifie si on est bien dans la grille
	 */
	public boolean saisieValide(int l, int c){
		return l>=0 && l<8 && c>=0 && c<8;
	}
	
	/**
	 * 
	 * On saisit les coordonnes pour faire le mouvement
	 */
	public void init(){
		String ch1 = new String();
		String ch2 = new String();
		
		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
		
		boolean valide = false;

		do{
			do{
				System.out.println("Saisir les coordonnes de la piece : ");
				ch1 = s.nextLine();
				System.out.println("Saisir les coordonnes du deplacement : ");
				ch2 = s.nextLine();			
			}while(ch1.length()!=2 || ch2.length()!=2);
			if(valide==false)
				valide = saisieValide(ch1.charAt(0)-65, ch1.charAt(1)-49) && saisieValide(ch2.charAt(0)-65, ch2.charAt(1)-49);
		}while(!valide);
		deplacerPiece(this.grille, ch1.charAt(0)-65, ch1.charAt(1)-49, ch2.charAt(0)-65, ch2.charAt(1)-49);
	}
	
	/**
	 * 
	 * @param p : Piece de l'échiquier
	 * @param l : Ligne de l'échiquier
	 * @param c : Colonne de l'échiquier
	 * @return Verifie si les couleurs sont différentes entre les deux pièces
	 */
	public boolean couleurDifferente(Piece p, int l, int c){
		return this.grille[l][c].getPiece().getCouleurPiece()!=p.getCouleurPiece();
	}
	
	
	public void deplacerPiece(Case[][] grille, int g_l, int g_c, int l, int c){
		if(saisieValide(g_l, g_c) && saisieValide(l,c)){
			if(grille[g_l][g_c]==null)
				System.out.println("Pas de piece dans cette case");
			else if(grille[g_l][g_c].getPiece() instanceof PieceRoi){
				PieceRoi p = (PieceRoi)grille[g_l][g_c].getPiece();
				if(p.deplacementValide(l, c) && (!grille[l][c].getCaseRemplie() || !(grille[l][c].getPiece() instanceof PieceRoi) && couleurDifferente(p, l, c))){
					//roi grand roque
					if(g_c-2==c && grille[g_l][g_c].getPiece().getMouvement()==false){
						if(!grille[g_l][g_c-1].getCaseRemplie() && !grille[g_l][g_c-2].getCaseRemplie() && !grille[g_l][g_c-3].getCaseRemplie()){
							if(grille[g_l][g_c-4].getCaseRemplie()){
								if(grille[g_l][g_c-4].getPiece() instanceof PieceTour && grille[g_l][g_c-4].getPiece().getMouvement()==false){
									PieceTour t = (PieceTour)grille[g_l][g_c-4].getPiece();
									deplacePieceIf(grille, p, g_l, g_c, l, g_c-2);
									deplacePieceIf(grille, t, g_l, g_c-4, l, g_c-1);
								}
							}
						}
						else
							System.out.println("Aucun mouvement");
					}
					//roi petit roque
					else if(g_c+2==c && grille[g_l][g_c].getPiece().getMouvement()==false){
						if(!grille[g_l][g_c+1].getCaseRemplie() && !grille[g_l][g_c+2].getCaseRemplie()){
							if(grille[g_l][g_c+3].getCaseRemplie()){
								if(grille[g_l][g_c+3].getPiece() instanceof PieceTour && grille[g_l][g_c+3].getPiece().getMouvement()==false){
									PieceTour t = (PieceTour)grille[g_l][g_c+3].getPiece();
									deplacePieceIf(grille, p, g_l, g_c, l, g_c+2);
									deplacePieceIf(grille, t, g_l, g_c+3, l, g_c+1);
								}
							}
						}
						else
							System.out.println("Aucun mouvement");
					}
					//roi deplacement +1 autour de lui
					else if(!deplacementRoiEchec(grille, g_l, g_c, l, c)){
						deplacePieceIf(grille, p, g_l, g_c, l, c);
						System.out.println("Deplacement : " + grille[l][c].getLigne() + "," + grille[l][c].getColonne());
					}
				}
				else
					System.out.println("Aucun mouvement");
			}
			else if(grille[g_l][g_c].getPiece() instanceof PiecePion){
				PiecePion p = (PiecePion)grille[g_l][g_c].getPiece();
				if(p.getCouleurPiece()==true){
					// + 2 haut blanc ou + 1 haut blanc
					if(p.deplacementValide(l, c)){
						if(!grille[g_l-1][g_c].getCaseRemplie() && l==0){
							evolutionPion(grille, p, g_l, g_c, l, c);
							System.out.println("Deplacement : " + (char)(65+grille[l][c].getLigne()) + "," + (grille[l][c].getColonne()+1));
						}
						if(!grille[g_l-1][g_c].getCaseRemplie() && !grille[g_l-2][g_c].getCaseRemplie() && l==g_l-2){
							deplacePieceIf(grille, p, g_l, g_c, l, c);
							System.out.println("Deplacement : " + (char)(65+grille[l][c].getLigne()) + "," + (grille[l][c].getColonne()+1));
						}
						else if(!grille[g_l-1][g_c].getCaseRemplie() && l==g_l-1){
							deplacePieceIf(grille, p, g_l, g_c, l, c);
							System.out.println("Deplacement : " + (char)(65+grille[l][c].getLigne()) + "," + (grille[l][c].getColonne()+1));
						}
						else
							System.out.println("Aucun mouvement");
					}
					// + 1 diagonale gauche blanc ou 1 diagonale droite blanc
					else if(p.deplacementValideVersusBlanc(l, c)){
						if(l==g_l-1 && g_c-1==c){
							if(l==0){
								evolutionPion(grille, p, g_l, g_c, l, c);
								System.out.println("Deplacement : " + (char)(65+grille[l][c].getLigne()) + "," + (grille[l][c].getColonne()+1));
							}
							else if(grille[g_l-1][g_c-1].getCaseRemplie()){
								if((grille[g_l-1][g_c-1].getPiece() instanceof Piece && !(grille[g_l-1][g_c-1].getPiece() instanceof PieceRoi)) && couleurDifferente(p, l, c)){
									deplacePieceIf(grille, p, g_l, g_c, l, c);
									System.out.println("Deplacement : " + (char)(65+grille[l][c].getLigne()) + "," + (grille[l][c].getColonne()+1));
								}
							}
							else
								System.out.println("Aucun mouvement");
						}
						else if(l==g_l-1 && g_c+1==c){
							if(l==0){
								evolutionPion(grille, p, g_l, g_c, l, c);
								System.out.println("Deplacement : " + (char)(65+grille[l][c].getLigne()) + "," + (grille[l][c].getColonne()+1));
							}
							else if(grille[g_l-1][g_c+1].getCaseRemplie()){
								if((grille[g_l-1][g_c+1].getPiece() instanceof Piece && !(grille[g_l-1][g_c+1].getPiece() instanceof PieceRoi)) && couleurDifferente(p, l, c)){
									deplacePieceIf(grille, p, g_l, g_c, l, c);
									System.out.println("Deplacement : " + (char)(65+grille[l][c].getLigne()) + "," + (grille[l][c].getColonne()+1));
								}
							}
							else
								System.out.println("Aucun mouvement");
						}
						else
							System.out.println("Aucun mouvement");
					}
					else
						System.out.println("Aucun mouvement");
				}
				else{
					// 2 bas noir ou 1 bas noir
					if(p.deplacementValide(l, c)){
						if(!grille[g_l+1][g_c].getCaseRemplie() && l==7){
							evolutionPion(grille, p, g_l, g_c, l, c);
							System.out.println("Deplacement : " + (char)(65+grille[l][c].getLigne()) + "," + (grille[l][c].getColonne()+1));
						}
						else if(!grille[g_l+1][g_c].getCaseRemplie() && !grille[g_l+2][g_c].getCaseRemplie() && l==g_l+2){
							deplacePieceIf(grille, p, g_l, g_c, l, c);
							System.out.println("Deplacement : " + (char)(65+grille[l][c].getLigne()) + "," + (grille[l][c].getColonne()+1));
						}
						else if(!grille[g_l+1][g_c].getCaseRemplie() && l==g_l+1){
							deplacePieceIf(grille, p, g_l, g_c, l, c);
							System.out.println("Deplacement : " + (char)(65+grille[l][c].getLigne()) + "," + (grille[l][c].getColonne()+1));
						}
						else
							System.out.println("Aucun mouvement");
					}
					// 1 diagonale gauche noir ou diagonale droite noir 
					else if(p.deplacementValideVersusNoir(l, c)){
						if(g_l+1==l && g_c-1==c){
							if(l==7){
								evolutionPion(grille, p, g_l, g_c, l, c);
								System.out.println("Deplacement : " + (char)(65+grille[l][c].getLigne()) + "," + (grille[l][c].getColonne()+1));
							}
							else if(grille[g_l+1][g_c-1].getCaseRemplie()){
								if(grille[g_l+1][g_c-1].getPiece() instanceof Piece && !(grille[g_l+1][g_c-1].getPiece() instanceof PieceRoi )){
									if(couleurDifferente(p, l, c)){
										deplacePieceIf(grille, p, g_l, g_c, l, c);
										System.out.println("Deplacement : " + (char)(65+grille[l][c].getLigne()) + "," + (grille[l][c].getColonne()+1));
									}
								}
							}
						}
						else if(g_l+1==l && g_c+1==c){
							if(l==7){
								evolutionPion(grille, p, g_l, g_c, l, c);
								System.out.println("Deplacement : " + (char)(65+grille[l][c].getLigne()) + "," + (grille[l][c].getColonne()+1));
							}
							else if(grille[g_l+1][g_c+1].getCaseRemplie()){
								if(grille[g_l+1][g_c+1].getPiece() instanceof Piece && !(grille[g_l+1][g_c+1].getPiece() instanceof PieceRoi)){
									if(couleurDifferente(p, l, c)){
										deplacePieceIf(grille, p, g_l, g_c, l, c);
										System.out.println("Deplacement : " + (char)(65+grille[l][c].getLigne()) + "," + (grille[l][c].getColonne()+1));
									}
								}
							}
						}
						else
							System.out.println("Aucun mouvement");
					}
					else
						System.out.println("Aucun mouvement");
				}
			}
			else if(grille[g_l][g_c].getPiece() instanceof PieceCavalier){
				PieceCavalier p = (PieceCavalier)grille[g_l][g_c].getPiece();
				if(p.deplacementValide(l, c) && (!grille[l][c].getCaseRemplie() || !(grille[l][c].getPiece() instanceof PieceRoi) && couleurDifferente(p, l, c)) ){
					deplacePieceIf(grille, p, g_l, g_c, l, c);
					System.out.println("Deplacement : " + (char)(65+grille[l][c].getLigne()) + "," + (grille[l][c].getColonne()+1));
				}
				else
					System.out.println("Aucun mouvement");
			}
			else if(grille[g_l][g_c].getPiece() instanceof PieceTour){
				PieceTour p = (PieceTour)grille[g_l][g_c].getPiece();
				boolean vide = true;
				int i=1;
				if(p.deplacementValide(l, c) && (!grille[l][c].getCaseRemplie() || !(grille[l][c].getPiece() instanceof PieceRoi) && couleurDifferente(p, l, c)) ){
					if(grille[g_l][g_c].getPiece().getLigne()==l){
						int difference = g_c-c;
						while(i<Math.abs(difference) && vide==true){
							if(g_c<c)
								if(grille[g_l][g_c+i].getCaseRemplie())
									vide = false;
							else
								if(grille[g_l][g_c-i].getCaseRemplie())
									vide = false;
							i++;
						}
					}
					else{
						int difference = g_l-l;
						while(i<Math.abs(difference) && vide==true){
							if(g_l<l)
								if(grille[g_l+i][g_c].getCaseRemplie())
									vide = false;
							else
								if(grille[g_l-i][g_c].getCaseRemplie())
									vide = false;
							i++;
						}
					}
					
					if(vide==true){
						deplacePieceIf(grille, p, g_l, g_c, l, c);
						System.out.println("Deplacement : " + (char)(65+grille[l][c].getLigne()) + "," + (grille[l][c].getColonne()+1));
					}
					else
						System.out.println("Aucun mouvement");
				}
				else
					System.out.println("Aucun mouvement");
			}
			else if(grille[g_l][g_c].getPiece() instanceof PieceFou){
				PieceFou p = (PieceFou)grille[g_l][g_c].getPiece();
				boolean vide = true;
				int i=1;
				if(p.deplacementValide(l, c) && (!grille[l][c].getCaseRemplie() || !(grille[l][c].getPiece() instanceof PieceRoi) && couleurDifferente(p, l, c)) ){
					if(g_l>l && g_c>c)
						while(i<g_l-l && vide){
							if(grille[g_l-i][g_c-i].getCaseRemplie())
								vide = false;
							i++;
						}
					else if(g_l>l && g_c<c)
						while(i<g_l-l && vide){
							if(grille[g_l-i][g_c+i].getCaseRemplie())
								vide = false;
							i++;
						}
					else if(g_l<l && g_c>c)
						while(i<l-g_l && vide){
							if(grille[g_l+i][g_c-i].getCaseRemplie())
								vide = false;
							i++;
						}
					else if(g_l<l && g_c<c)
						while(i<l-g_l && vide){
							if(grille[g_l+i][g_c+i].getCaseRemplie())
								vide = false;
							i++;
						}
					if(vide==true){
						deplacePieceIf(grille, p, g_l, g_c, l, c);
						System.out.println("Deplacement : " + (char)(65+grille[l][c].getLigne()) + "," + (grille[l][c].getColonne()+1));
					}
					else
						System.out.println("Aucun mouvement");
				}
				else
					System.out.println("Aucun mouvement");
			}
			else if(grille[g_l][g_c].getPiece() instanceof PieceDame){
				PieceDame p = (PieceDame)grille[g_l][g_c].getPiece();
				boolean vide = true;
				int i=1, difference;
				
				if(p.deplacementValide(l, c) && (!grille[l][c].getCaseRemplie() || !(grille[l][c].getPiece() instanceof PieceRoi) && couleurDifferente(p, l, c)) ){
					if(grille[g_l][g_c].getPiece().getLigne()==l){
						difference = g_c-c;
						while(i<Math.abs(difference) && vide==true){
							if(g_c<c)
								if(grille[g_l][g_c+i].getCaseRemplie())
									vide = false;
							else
								if(grille[g_l][g_c-i].getCaseRemplie())
									vide = false;
							i++;
						}
					}
					else{
						difference = g_l-l;
						while(i<Math.abs(difference) && vide==true){
							if(g_l<l)
								if(grille[g_l+i][g_c].getCaseRemplie())
									vide = false;
							else
								if(grille[g_l-i][g_c].getCaseRemplie())
									vide = false;
							i++;
						}
					}
					if(g_l>l && g_c>c)
						while(i<g_l-l && vide){
							if(grille[g_l-i][g_c-i].getCaseRemplie())
								vide = false;
							i++;
						}
					else if(g_l>l && g_c<c)
						while(i<g_l-l && vide){
							if(grille[g_l-i][g_c+i].getCaseRemplie())
								vide = false;
							i++;
						}
					else if(g_l<l && g_c>c)
						while(i<l-g_l && vide){
							if(grille[g_l+i][g_c-i].getCaseRemplie())
								vide = false;
							i++;
						}
					else if(g_l<l && g_c<c)
						while(i<l-g_l && vide){
							if(grille[g_l+i][g_c+i].getCaseRemplie())
								vide = false;
							i++;
						}
					
					if(vide==true){
						deplacePieceIf(grille, p, g_l, g_c, l, c);
						System.out.println("Deplacement : " + (char)(65+grille[l][c].getLigne()) + "," + (grille[l][c].getColonne()+1));
					}
					else
						System.out.println("Aucun mouvement");
				}
			}
			else
				System.out.println("Aucun mouvement");
		}
		else
			System.out.println("Vous n'etes pas dans la grille");
	}
	
	
	
	public boolean deplacementRoiEchec(Case[][] grille, int g_l, int g_c, int l, int c){
		PieceRoi p = (PieceRoi) grille[g_l][g_c].getPiece();
		boolean echec = false, bloque = false;
		int i = 1;
		
		/*
		 * TOUR - TOUR - TOUR - DAME
		 */
		
		
		while(l-i>=0 && !echec && !bloque){
			if(grille[l-i][c].getCaseRemplie()){
				bloque=true;
				if(couleurDifferente(p, l-i, c))
					if(grille[l-i][c].getPiece() instanceof PieceTour || grille[l-i][c].getPiece() instanceof PieceDame  ||  (i==1 && grille[l-i][c].getPiece() instanceof PieceRoi))
						echec = true;
			}
			i++;
		}
		i=1;
		bloque=false;
		while(l+i<=7 && !echec && !bloque){
			if(grille[l+i][c].getCaseRemplie()){
				bloque=true;
				if(couleurDifferente(p, l+i, c))
					if(grille[l+i][c].getPiece() instanceof PieceTour || grille[l+i][c].getPiece() instanceof PieceDame  || (i==1 && grille[l+i][c].getPiece() instanceof PieceRoi))
						echec = true;
			}
			i++;
		}
		i=1;
		bloque = false;
		while(c-i>=0 && !echec && !bloque){
			if(grille[l][c-i].getCaseRemplie()){
				bloque=true;
				if(couleurDifferente(p, l, c-i))
					if(grille[l][c-i].getPiece() instanceof PieceTour || grille[l][c-i].getPiece() instanceof PieceDame || (i==1 && grille[l][c-i].getPiece() instanceof PieceRoi))
						echec = true;
			}
			i++;
		}
		i=1;
		bloque = false;
		while(c+i<=7 && !echec && !bloque){
			if(grille[l][c+i].getCaseRemplie()){
				bloque=true;
				if(couleurDifferente(p, l, c+i))
					if(grille[l][c+i].getPiece() instanceof PieceTour || grille[l][c+i].getPiece() instanceof PieceDame || (i==1 && grille[l][c+i].getPiece() instanceof PieceRoi))
						echec = true;
			}
			i++;
		}
		
		/*
		 * FOU - FOU - FOU - DAME - PION(1)
		 */
			
		int f=1;
		bloque=false;
		
		while(l-f>=0 && c-f>=0 && !echec && !bloque){
			if(grille[l-f][c-f].getCaseRemplie()){
				bloque=true;
				if(couleurDifferente(p, l-f, c-f))
					if(grille[l-f][c-f].getPiece() instanceof PieceFou || grille[l-f][c-f].getPiece() instanceof PieceDame  || grille[l-f][c-f].getPiece() instanceof PieceRoi || (f==1 && grille[l-f][c-f].getPiece() instanceof PiecePion))
						echec = true;
			}
			f++;
		}
		
		f=1;
		bloque=false;
		while(l-f>=0 && c+f<=7 && !echec && !bloque){
			if(grille[l-f][c+f].getCaseRemplie()){
				bloque=true;
				if(couleurDifferente(p, l-f, c+f))
					if(grille[l-f][c+f].getPiece() instanceof PieceFou || grille[l-f][c+f].getPiece() instanceof PieceDame  || grille[l-f][c+f].getPiece() instanceof PieceRoi || (f==1 && grille[l-f][c+f].getPiece() instanceof PiecePion))
						echec = true;
			}
			f++;
		}
		
		f=1;
		bloque=false;
		while(l+f<=7 && c-f>=0 && !echec && !bloque){
			if(grille[l+f][c-f].getCaseRemplie()){
				bloque=true;
				if(couleurDifferente(p, l+f, c-f))
					if(grille[l+f][c-f].getPiece() instanceof PieceFou || grille[l+f][c-f].getPiece() instanceof PieceDame  || grille[l+f][c-f].getPiece() instanceof PieceRoi || (f==1 && grille[l+f][c-f].getPiece() instanceof PiecePion))
						echec = true;
			}
			f++;
		}
		
		f=1;
		bloque=false;
		while(l+f<=7 && c+f<=7 && !echec && !bloque){
			if(grille[l+f][c+f].getCaseRemplie()){
				bloque=true;
				if(couleurDifferente(p, l+f, c+f))
					if(grille[l+f][c+f].getPiece() instanceof PieceFou || grille[l+f][c+f].getPiece() instanceof PieceDame  || grille[l+f][c+f].getPiece() instanceof PieceRoi || (f==1 && grille[l+f][c+f].getPiece() instanceof PiecePion))
						echec = true;
			}
			f++;
		}

		/*
		 * CAVALIER - CAVALIER - CAVALIER
		 */
		if(l-1>=0 && c-2>=0 && !echec)
			if(grille[l-1][c-2].getCaseRemplie())
				if(couleurDifferente(p, l-1, c-2))
					if(grille[l-1][c-2].getPiece() instanceof PieceCavalier)
						echec=true;
		if(l-2>=0 && c-1>=0 && !echec)
			if(grille[l-2][c-1].getCaseRemplie())
				if(couleurDifferente(p, l-2, c-1))
					if(grille[l-2][c-1].getPiece() instanceof PieceCavalier)
						echec=true;
		if(l-2>=0 && c+1<=7 && !echec)
			if(grille[l-2][c+1].getCaseRemplie())
				if(couleurDifferente(p, l-2, c+1))
					if(grille[l-2][c+1].getPiece() instanceof PieceCavalier)
						echec=true;
		if(l-1>=0 && c+2<=7 && !echec)
			if(grille[l-1][c+2].getCaseRemplie())
				if(couleurDifferente(p, l-1, c+2))
					if(grille[l-1][c+2].getPiece() instanceof PieceCavalier)
						echec=true;
		if(l+1<=7 && c+2<=7 && !echec)
			if(grille[l+1][c+2].getCaseRemplie())
				if(couleurDifferente(p, l+1, c+2))
					if(grille[l+1][c+2].getPiece() instanceof PieceCavalier)
						echec=true;
		if(l+2<=7 && c+1<=7 && !echec)
			if(grille[l+2][c+1].getCaseRemplie())
				if(couleurDifferente(p, l+2, c+1))
					if(grille[l+2][c+1].getPiece() instanceof PieceCavalier)
						echec=true;
		if(l+2<=7 && c-1>=0 && !echec)
			if(grille[l+2][c-1].getCaseRemplie())
				if(couleurDifferente(p, l+2, c-1))
					if(grille[l+2][c-1].getPiece() instanceof PieceCavalier)
						echec=true;
		if(l+1<=7 && c-2>=0 && !echec)
			if(grille[l+1][c-2].getCaseRemplie())
				if(couleurDifferente(p, l+1, c-2))
					if(grille[l+1][c-2].getPiece() instanceof PieceCavalier)
						echec=true;
		
		if(echec)
			System.out.println("Impossible le Roi peut être capturé !");
		
		return echec;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * 
	 * On affiche l'échiquier
	 */
	public String toString(){
		StringBuilder s = new StringBuilder("     1    2    3    4    5    6    7    8   ").append(System.lineSeparator());
		s.append("  ┌────┬────┬────┬────┬────┬────┬────┬────┐").append(System.lineSeparator());
		int c = 65;
		for(int i=0; i<8; i++){
			s.append((char)(c+i) + " │ ");
			for(int j=0; j<8; j++){
				if(this.grille[i][j].getCaseRemplie())
					s.append(this.grille[i][j]).append(" │ ");
				else
					s.append("  ").append(" │ ");
			}
			s.append(System.lineSeparator());
			if(i<7)
				s.append("  ├────┼────┼────┼────┼────┼────┼────┼────┤").append(System.lineSeparator());
			else
				s.append("  └────┴────┴────┴────┴────┴────┴────┴────┘").append(System.lineSeparator());
		}
		return s.toString();
	}
	
	
	
	
}
