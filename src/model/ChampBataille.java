package model;
import java.util.ArrayList;

// La classe ChampBataille permet de définir un champ de bataille avec un tableau de cases comme plateau, et une liste de bateau pour ces bateaux.
public class ChampBataille{
  // Les variables sont donc un tableau de Case, et une liste de bateaux.
  private Case[][] plateau;
  private ArrayList<Bateau> bateaux;
  // Le constructeur va créer un tableau de 10 par 10 cases possédant les bonnes coordonnées, et crée une ArrayList vide pour les futurs bateaux.
  public ChampBataille(){
    this.plateau = new Case[10][10];
    this.bateaux = new ArrayList<>();
    for(int x = 0; x < 10; x++){
      for(int y = 0; y < 10; y++){
        this.plateau[x][y] = new Case(x,y);
      }
    }
  }
  // Accesseurs ---------------------------------
  public Case getCase(int x,int y){
    return this.plateau[x][y];
  }
  public ArrayList<Bateau> getBateaux(){
    return this.bateaux;
  }
  public Case[][] getPlateau(){
    return this.plateau;
  }
  // --------------------------------------------
  // La méthode isOver permet de vérifier si tous les bateaux du champ de bataille sont coulés, auquel cas il renvoie true.
  public boolean isOver(){
    for(Bateau bateau : this.bateaux){
      if(!(bateau.getCoule())){
        return false;
      }
    }
    return true;
  }
  // La méthode placer permet de placer un bateau sur le champ de bataille.
  public void placer(int taille, int x, int y, int direction) {
    // Pour cela elle vérifie la direction que doit prendre le bateau.
    if(direction == 0) {
      // Puis elle regarde si le bateau peut être placé à cette position (à l'aide de notre méthode placementPossible).
      if(placementPossible(taille, x, y, direction)){
        // Finalement si c'est possible, elle construit un nouveau bateau, et le place aux bons endroits.
        Bateau bateau = new Bateau(taille);
        for(int i = 0; i < taille; i++){
          this.plateau[x][y+i].placer(bateau);
        }
        this.bateaux.add(bateau);
      }
      // Sinon elle renvoie un message d'erreur car le bateau ne peut pas être placer.
      else {
        System.out.println("Placement impossible.");
      }
    }
    // Elle fait la même chose pour l'autre direction.
    else {
      if(placementPossible(taille, x, y, direction)){
        Bateau bateau = new Bateau(taille);
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
  // Notre méthode placementPossible permet de vérifier si, pour des informations données, le placement d'un bateau est possible ou non.
  public boolean placementPossible(int taille, int x, int y, int direction) {
    boolean placementPossible = false;
    if(direction == 0) { // Si le bateau est vertical.
      for (int yCase = y; yCase != y + taille; yCase++) { // Boucle qui vérifie, en fonction de la direction du bateau et de sa taille si le placement est possible.
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
    else { // Si le bateau est horizontal (mêmes vérifications que si le bateau est vertical, avec quelques changements par rapport aux coordonnées).
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
  // Notre méthode annulerPlacementBateau permet de supprimer un bateau présent sur le champ de bataille.
  public void annulerPlacementBateau(int x, int y, Bateau bateau) {
    getBateaux().remove(bateau); // Permet d'enlever le bateau de la liste de bateau.
    getCase(x, y).annulerBateau(); // Permet d'enlever le bateau sur la case de coordonnées (x,y).
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
  // La méthode visée permet de lancer un Obus sur une position.
  public void viser(int x,int y,Obus obus){
    this.getCase(x,y).viser(obus);
  }

}
