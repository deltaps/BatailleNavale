package model;
import java.util.ArrayList;

public class ChampBataille{

  private Case[][] plateau;
  private ArrayList<Bateau> bateaux;

  public ChampBataille(){
    this.plateau = new Case[10][10];
    this.bateaux = new ArrayList<>();
    for(int x = 0; x < 10; x++){
      for(int y = 0; y < 10; y++){
        this.plateau[x][y] = new Case(x,y);
      }
    }
  }

  public Case getCase(int x,int y){
    return this.plateau[x][y];
  }
  public ArrayList<Bateau> getBateaux(){
    return this.bateaux;
  }
  public Case[][] getPlateau(){
    return this.plateau;
  }
  public boolean isOver(){
    for(Bateau bateau : this.bateaux){
      if(!(bateau.getCoule())){
        return false;
      }
    }
    return true;
  }
/*
  public void placer(Bateau bateau, int x, int y, int direction){
    boolean pasErreur = true;
    if(direction == 0){
      if(y + bateau.getTaille() > 10){
        System.out.println("Placement impossible");
        pasErreur = false;
      }
      if(pasErreur){
        for(int i = 0; i < bateau.getTaille(); i++){
          this.plateau[x][y+i].placer(bateau);
        }
        this.bateaux.add(bateau);
      }
    }
    else{
      if(x + bateau.getTaille() > 10){
        System.out.println("Placement impossible");
        pasErreur = false;
      }
      if(pasErreur) {
        for(int i = 0; i < bateau.getTaille(); i++){
          this.plateau[x+i][y].placer(bateau);
        }
        this.bateaux.add(bateau);
      }
    }
  }
*/
  public void placer(int taille, int x, int y, int direction) {
    Bateau bateau = new Bateau(taille);
    if(direction == 0) {
      if(placementPossible(taille, x, y, direction)) {
        for(int i = 0; i < taille; i++){
          this.plateau[x][y+i].placer(bateau);
        }
        this.bateaux.add(bateau);
      }
      else {
        System.out.println("Placement impossible.");
      }
    }

    else {
      if(placementPossible(taille, x, y, direction)) {
        for(int i = 0; i < taille; i++){
          this.plateau[x+i][y].placer(bateau);
        }
        this.bateaux.add(bateau);
      }
      else {
        System.out.println("Placement impossible.");
      }
    }
  }

  public boolean placementPossible(int taille, int x, int y, int direction) {
    boolean placementPossible = false;

    if(direction == 0) { // Si le bateau est vertical.
      for (int yCase = y; yCase != y + taille; yCase++) { // Boucle qui vérifie, on fonction de la direction du bateau et de sa taille si le placement est possible.
        if(yCase <= 9) { // Le placement n'est pas possible si une case du bateau dépasse du plateau.
          if(getCase(x, yCase).bateauHere() == null) { // Le placement n'est pas possible si une case du bateau superpose un autre bateau.
            placementPossible = true;
          }
          else {
            return false;
          }
        }
        else {
          return false;
        }
      }
    }
    else { // Si le bateau est horizontal (mêmes vérifications que si le bateau est vertical, avec quelques changement par rapport aux coordonnées).
      for (int xCase = x; xCase != x + taille; xCase++) {
        if(xCase <= 9) {
          if(getCase(xCase, y).bateauHere() == null) {
            placementPossible = true;
          }
          else {
            return false;
          }
        }
        else {
          return false;
        }
      }
    }
    return placementPossible;
  }

  public void annulerPlacementBateau(int x, int y, Bateau bateau) {
    getBateaux().remove(bateau); // Permet d'enlever le bateau de la liste de bateau.

    getCase(x, y).annulerBateau(); // Permet d'enlever la bateau sur la case de coordonnées (x,y).
    for (int i = 1; i < bateau.getTaille(); i++) { // Boucle qui vérifie toutes les cases alentour de celle cliquée, si elles contiennent le bateau cliqué.
      if (x + i <= 9) { // Si on ne dépasse pas les bords du terrain.
        if (getCase(x + i, y).bateauHere() == bateau) {
          getCase(x + i, y).annulerBateau();
        }
      }
      if (x - i >= 0) {
        if (getCase(x - i, y).bateauHere() == bateau) {
          getCase(x - i, y).annulerBateau();
        }
      }
      if (y + i <= 9) {
        if (getCase(x, y + i).bateauHere() == bateau) {
          getCase(x, y + i).annulerBateau();
        }
      }
      if (y - i >= 0) {
        if (getCase(x, y - i).bateauHere() == bateau) {
          getCase(x, y - i).annulerBateau();
        }
      }
    }
  }

  public void viser(int x,int y,Obus obus){
    this.getCase(x,y).viser(obus);
  }

}
