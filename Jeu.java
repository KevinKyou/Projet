package SunWongParis13.Jeu;

import SunWongParis13.Echiquier.Echiquier;

public class Jeu {
	public static void main(String [] args){
		Echiquier jeu = new Echiquier();
		jeu.initialiseEchiquier();
		System.out.println(jeu);
		int i=0;
		while(i<1000){
			jeu.init();
			System.out.println(jeu);
			i++;
		}
	}
}