package SunWongParis13.Echiquier;

public class EchiquierJeu{
	
	private Echiquier jeu;
  
  public EchiquierJeu() {
		this.jeu = new Echiquier();
		this.jeu.initialiseEchiquier();
		tour = true;
	}
  
  public boolean saisieValide(int g_l, int g_c){
		return g_l>=0 && g_l<=7 && g_c>=0 && g_c<=7;
	}
  
  /*
  *
  *
  * FONCTION
  *
  */
  
  
  
  public String toString(){
		return jeu.toString();
	}
