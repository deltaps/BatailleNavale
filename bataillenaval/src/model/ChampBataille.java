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

  public void placer(Bateau bateau, int x, int y, int direction){
    boolean pasErreur = true;
    if(direction == 0){
      if(y + bateau.getTaille() > 9){
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
      if(x + bateau.getTaille() > 9){
        System.out.println("Placement impossible");
        pasErreur = false;
      }
      if(pasErreur){
        for(int i = 0; i < bateau.getTaille(); i++){
          this.plateau[x+i][y].placer(bateau);
        }
        this.bateaux.add(bateau);
      }
    }
  }

  public void viser(int x,int y,Obus obus){
    this.getCase(x,y).viser(obus);
  }

}
