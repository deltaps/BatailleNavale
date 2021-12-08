package model;
// La classe joueur permet de créer un joueur possédant un champ de bataille.
public class Joueur{
  // La seule variable que possède le joueur est donc son champ de bataille.
  private ChampBataille champBataille;
  // Le constructeur prend un champ de bataille et l'ajoute à notre variable.
  public Joueur(ChampBataille champBataille){
    this.champBataille = champBataille;
  }
  // Accesseurs ------------------------------------
  public ChampBataille getChampBataille(){
    return this.champBataille;
  }
  // -----------------------------------------------
  // La méthode placer permet de placer un bateau sur le champ de bataille.
  public void placer(int taille, int x, int y, int direction){
    this.champBataille.placer(taille,x,y,direction);
  }
  // La méthode visée permet de lancer un obus sur une position du champ de bataille.
  public void viser(int x,int y, ChampBataille champBataille){
    Obus obus = new Obus();
    champBataille.viser(x,y,obus);
  }
}
