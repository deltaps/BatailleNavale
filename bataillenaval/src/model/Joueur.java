package model;

public class Joueur{

  private ChampBataille champBataille;

  public Joueur(ChampBataille champBataille){
    this.champBataille = champBataille;
  }

  public ChampBataille getChampBataille(){
    return this.champBataille;
  }
  public void placer(Bateau bateau, int x, int y, int direction){
    this.champBataille.placer(bateau,x,y,direction);
  }
  public void viser(int x,int y, ChampBataille champBataille){
    Obus obus = new Obus();
    champBataille.viser(x,y,obus);
  }
}
